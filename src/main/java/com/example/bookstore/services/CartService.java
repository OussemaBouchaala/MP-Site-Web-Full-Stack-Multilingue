package com.example.bookstore.services;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Optional;

@Service
@SessionScope // Important: CartService needs to be session-scoped
public class CartService {

    private static final String SESSION_CART_KEY = "shoppingCart";

    @Autowired
    private BookService bookService;

    @Autowired
    private HttpSession session;

    // Get cart from session or create a new one
    public Cart getCart() {
        Cart cart = (Cart) session.getAttribute(SESSION_CART_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(SESSION_CART_KEY, cart);
        }
        return cart;
    }

    // Add item to cart
    public void addItemToCart(Long bookId, int quantity) {
        Optional<Book> bookOpt = bookService.findBookById(bookId);
        if (bookOpt.isPresent()) {
            Cart cart = getCart();
            cart.addItem(bookOpt.get(), quantity);
            session.setAttribute(SESSION_CART_KEY, cart); // Update session
        } else {
            // Handle book not found error
            throw new RuntimeException("Book not found with ID: " + bookId);
        }
    }

    // Remove item from cart
    public void removeItemFromCart(Long bookId) {
        Cart cart = getCart();
        cart.removeItem(bookId);
        session.setAttribute(SESSION_CART_KEY, cart); // Update session
    }

    // Update item quantity in cart
    public void updateItemQuantity(Long bookId, int quantity) {
        Cart cart = getCart();
        cart.updateQuantity(bookId, quantity);
        session.setAttribute(SESSION_CART_KEY, cart); // Update session
    }

    // Clear the cart
    public void clearCart() {
        Cart cart = getCart();
        cart.clear();
        session.setAttribute(SESSION_CART_KEY, cart); // Update session
        // Or simply remove the attribute: session.removeAttribute(SESSION_CART_KEY);
    }
}
