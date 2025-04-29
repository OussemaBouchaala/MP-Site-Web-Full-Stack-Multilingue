-- Pre-populate the database with some sample books

-- Note: User data will be handled via registration, not pre-populated here.
-- Order data will be generated through application usage.

INSERT INTO books (title, author, price) VALUES
('The Lord of the Rings', 'J.R.R. Tolkien', 19.99),
('Pride and Prejudice', 'Jane Austen', 12.50),
('To Kill a Mockingbird', 'Harper Lee', 14.00),
('1984', 'George Orwell', 11.99),
('The Great Gatsby', 'F. Scott Fitzgerald', 10.50),
('Moby Dick', 'Herman Melville', 15.75),
('War and Peace', 'Leo Tolstoy', 22.00),
('The Catcher in the Rye', 'J.D. Salinger', 9.95),
('Brave New World', 'Aldous Huxley', 13.25),
('One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 16.50);

