package com.example.bookstore.repositories;

import com.example.bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom query methods can be added here if needed
}

