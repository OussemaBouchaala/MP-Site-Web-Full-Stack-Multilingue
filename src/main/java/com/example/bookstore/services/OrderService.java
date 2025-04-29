package com.example.bookstore.services;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Order;
import com.example.bookstore.models.OrderItem;
import com.example.bookstore.models.User;
import com.example.bookstore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Transactional
    public Order createOrder() {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get current cart
        Cart cart = cartService.getCart();
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot create order with empty cart");
        }

        // Create new order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(cart.getTotal());

        // Add items to order
        for (Cart.CartItem cartItem : cart.getItems().values()) {
            Book book = cartItem.getBook();
            OrderItem orderItem = new OrderItem(book, cartItem.getQuantity(), book.getPrice());
            order.addOrderItem(orderItem);
        }

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Clear cart after successful order
        cartService.clearCart();

        return savedOrder;
    }

    public List<Order> getUserOrders() {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get user orders
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
