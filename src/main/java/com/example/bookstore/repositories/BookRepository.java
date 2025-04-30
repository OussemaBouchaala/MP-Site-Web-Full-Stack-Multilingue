package com.example.bookstore.repositories;

import com.example.bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom query methods can be added here if needed
    //    List<Book> findByTitleContaining(String title);
    //    List<Book> findByAuthor(String author);
}

