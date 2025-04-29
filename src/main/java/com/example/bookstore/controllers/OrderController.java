package com.example.bookstore.controllers;

import com.example.bookstore.models.Order;
import com.example.bookstore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Endpoint to initiate order creation (triggered from cart checkout)
    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()") // Ensure user is logged in
    public String createOrder(RedirectAttributes redirectAttributes, Model model) {
        try {
            Order order = orderService.createOrder();
            redirectAttributes.addFlashAttribute("order", order);
            return "redirect:/order/confirmation/" + order.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("orderError", "Error creating order: " + e.getMessage());
            return "redirect:/cart"; // Redirect back to cart if error
        }
    }

    // Endpoint to display order confirmation page
    @GetMapping("/confirmation/{id}")
    @PreAuthorize("isAuthenticated()")
    public String orderConfirmation(@PathVariable Long id, Model model) {
        // Check if the order object is passed via flash attributes
        if (!model.containsAttribute("order")) {
            // If not, fetch it from the service (e.g., if user refreshes the page)
            try {
                Order order = orderService.getOrderById(id);
                // Security check: Ensure the logged-in user owns this order (optional but recommended)
                // String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
                // if (!order.getUser().getUsername().equals(currentUsername)) {
                //     return "redirect:/error"; // Or some access denied page
                // }
                model.addAttribute("order", order);
            } catch (Exception e) {
                // Handle order not found or other errors
                return "redirect:/error"; // Redirect to a generic error page
            }
        }
        return "order"; // Renders order.html
    }

    // Endpoint to display user's order history
    @GetMapping("/history") // Changed from /orders to /order/history for clarity
    @PreAuthorize("isAuthenticated()")
    public String viewOrderHistory(Model model) {
        try {
            List<Order> orders = orderService.getUserOrders();
            model.addAttribute("orders", orders);
        } catch (Exception e) {
            // Handle error fetching orders
            model.addAttribute("orderHistoryError", "Error fetching order history: " + e.getMessage());
        }
        return "orders"; // Renders orders.html
    }
}
