package com.example.bookstore.controllers;

import com.example.bookstore.models.Cart;
import com.example.bookstore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String viewCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("bookId") Long bookId, 
                          @RequestParam(value = "quantity", defaultValue = "1") int quantity, 
                          RedirectAttributes redirectAttributes) {
        try {
            cartService.addItemToCart(bookId, quantity);
            // Optionally add a success message
            // redirectAttributes.addFlashAttribute("cartMessage", "Book added to cart!");
        } catch (Exception e) {
            // Optionally add an error message
            // redirectAttributes.addFlashAttribute("cartError", "Error adding book to cart: " + e.getMessage());
        }
        // Redirect back to the books page or the cart page
        return "redirect:/books"; // Or redirect:/cart
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("bookId") Long bookId, 
                               @RequestParam("quantity") int quantity,
                               RedirectAttributes redirectAttributes) {
        try {
            cartService.updateItemQuantity(bookId, quantity);
        } catch (Exception e) {
            // Handle error
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("bookId") Long bookId,
                                 RedirectAttributes redirectAttributes) {
        try {
            cartService.removeItemFromCart(bookId);
        } catch (Exception e) {
            // Handle error
        }
        return "redirect:/cart";
    }


}
