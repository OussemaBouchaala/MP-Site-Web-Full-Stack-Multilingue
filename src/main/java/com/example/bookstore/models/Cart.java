package com.example.bookstore.models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

// Note: This is not an entity as it will be stored in the session, not the database
public class Cart {

    private Map<Long, CartItem> items = new HashMap<>();
    private BigDecimal total = BigDecimal.ZERO;

    // Add a book to the cart
    public void addItem(Book book, int quantity) {
        Long bookId = book.getId();
        
        if (items.containsKey(bookId)) {
            // Update existing item
            CartItem existingItem = items.get(bookId);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Add new item
            items.put(bookId, new CartItem(book, quantity));
        }
        
        // Recalculate total
        calculateTotal();
    }
    
    // Remove a book from the cart
    public void removeItem(Long bookId) {
        items.remove(bookId);
        calculateTotal();
    }
    
    // Update quantity of a book
    public void updateQuantity(Long bookId, int quantity) {
        if (items.containsKey(bookId)) {
            if (quantity <= 0) {
                items.remove(bookId);
            } else {
                items.get(bookId).setQuantity(quantity);
            }
            calculateTotal();
        }
    }
    
    // Calculate total price
    private void calculateTotal() {
        total = BigDecimal.ZERO;
        for (CartItem item : items.values()) {
            total = total.add(item.getSubtotal());
        }
    }
    
    // Clear the cart
    public void clear() {
        items.clear();
        total = BigDecimal.ZERO;
    }
    
    // Getters and Setters
    public Map<Long, CartItem> getItems() {
        return items;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public int getItemCount() {
        return items.size();
    }
    
    // Inner class for cart items
    public static class CartItem {
        private Book book;
        private int quantity;
        
        public CartItem(Book book, int quantity) {
            this.book = book;
            this.quantity = quantity;
        }
        
        public Book getBook() {
            return book;
        }
        
        public void setBook(Book book) {
            this.book = book;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        
        public BigDecimal getSubtotal() {
            return book.getPrice().multiply(new BigDecimal(quantity));
        }
    }
}
