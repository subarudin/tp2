package com.example.testuas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.testuas.dto.BookRequest;
import com.example.testuas.dto.BookResponse;
import com.example.testuas.entity.Book;
import com.example.testuas.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service layer untuk logika bisnis manajemen buku
 * 
 * Dependency Injection (DI) bekerja di sini:
 * 1. @Service - Menandai class sebagai service component
 * 2. @RequiredArgsConstructor - Lombok: generate constructor untuk final fields
 * 3. BookRepository diinjeksi melalui constructor
 * 
 * Arsitektur tiga lapis:
 * Controller -> Service -> Repository
 * 
 * Service layer berisi logika bisnis seperti:
 * - Validasi data sebelum disimpan
 * - Transformasi data (DTO <-> Entity)
 * - Business rules (aturan bisnis)
 * - Error handling
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookService {
    
    private final BookRepository bookRepository;
    
    /**
     * Menyimpan buku baru
     * @Transactional - Memastikan operasi database dalam satu transaksi
     */
    @Transactional
    public BookResponse createBook(BookRequest request) {
        log.info("Creating new book: {}", request.getTitle());
        
        // Validasi ISBN unik
        if (request.getIsbn() != null && bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN sudah terdaftar: " + request.getIsbn());
        }
        
        // Membuat entitas Book dari request
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setCategory(request.getCategory());
        book.setDescription(request.getDescription());
        book.setStockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0);
        book.setPrice(request.getPrice());
        
        // Menyimpan ke database
        Book savedBook = bookRepository.save(book);
        log.info("Book created successfully with ID: {}", savedBook.getId());
        
        return BookResponse.fromEntity(savedBook);
    }
    
    /**
     * Mengambil semua buku
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        log.info("Fetching all books");
        return bookRepository.findAll()
                .stream()
                .map(BookResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Mengambil buku berdasarkan ID
     */
    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        log.info("Fetching book with ID: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Buku tidak ditemukan dengan ID: " + id));
        return BookResponse.fromEntity(book);
    }
    
    /**
     * Mengupdate buku berdasarkan ID
     */
    @Transactional
    public BookResponse updateBook(Long id, BookRequest request) {
        log.info("Updating book with ID: {}", id);
        
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Buku tidak ditemukan dengan ID: " + id));
        
        // Validasi ISBN unik (kecuali untuk buku yang sedang diupdate)
        if (request.getIsbn() != null && !request.getIsbn().equals(existingBook.getIsbn())) {
            Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(request.getIsbn());
            if (bookWithSameIsbn.isPresent()) {
                throw new IllegalArgumentException("ISBN sudah terdaftar: " + request.getIsbn());
            }
        }
        
        // Update data buku
        existingBook.setTitle(request.getTitle());
        existingBook.setAuthor(request.getAuthor());
        existingBook.setIsbn(request.getIsbn());
        existingBook.setPublicationYear(request.getPublicationYear());
        existingBook.setCategory(request.getCategory());
        existingBook.setDescription(request.getDescription());
        existingBook.setStockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : existingBook.getStockQuantity());
        existingBook.setPrice(request.getPrice());
        
        Book updatedBook = bookRepository.save(existingBook);
        log.info("Book updated successfully with ID: {}", updatedBook.getId());
        
        return BookResponse.fromEntity(updatedBook);
    }
    
    /**
     * Menghapus buku berdasarkan ID
     */
    @Transactional
    public void deleteBook(Long id) {
        log.info("Deleting book with ID: {}", id);
        
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Buku tidak ditemukan dengan ID: " + id);
        }
        
        bookRepository.deleteById(id);
        log.info("Book deleted successfully with ID: {}", id);
    }
    
    /**
     * Mencari buku berdasarkan keyword
     */
    @Transactional(readOnly = true)
    public List<BookResponse> searchBooks(String keyword) {
        log.info("Searching books with keyword: {}", keyword);
        return bookRepository.searchByKeyword(keyword)
                .stream()
                .map(BookResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Mencari buku berdasarkan judul
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByTitle(String title) {
        log.info("Fetching books by title: {}", title);
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(BookResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Mencari buku berdasarkan penulis
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByAuthor(String author) {
        log.info("Fetching books by author: {}", author);
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(BookResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Mencari buku berdasarkan kategori
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByCategory(String category) {
        log.info("Fetching books by category: {}", category);
        return bookRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(BookResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Mencari buku berdasarkan status
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByStatus(Book.BookStatus status) {
        log.info("Fetching books by status: {}", status);
        return bookRepository.findByStatus(status)
                .stream()
                .map(BookResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Mencari buku dengan stok rendah
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getBooksWithLowStock() {
        log.info("Fetching books with low stock");
        return bookRepository.findBooksWithLowStock()
                .stream()
                .map(BookResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Menambah stok buku
     */
    @Transactional
    public BookResponse addStock(Long bookId, Integer quantity) {
        log.info("Adding stock for book ID: {} with quantity: {}", bookId, quantity);
        
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Buku tidak ditemukan dengan ID: " + bookId));
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Jumlah stok harus lebih dari 0");
        }
        
        book.addStock(quantity);
        Book updatedBook = bookRepository.save(book);
        
        log.info("Stock added successfully for book ID: {}", bookId);
        return BookResponse.fromEntity(updatedBook);
    }
    
    /**
     * Mengurangi stok buku
     */
    @Transactional
    public BookResponse reduceStock(Long bookId, Integer quantity) {
        log.info("Reducing stock for book ID: {} with quantity: {}", bookId, quantity);
        
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Buku tidak ditemukan dengan ID: " + bookId));
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Jumlah stok harus lebih dari 0");
        }
        
        if (book.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Stok tidak mencukupi. Stok tersedia: " + book.getStockQuantity());
        }
        
        book.reduceStock(quantity);
        Book updatedBook = bookRepository.save(book);
        
        log.info("Stock reduced successfully for book ID: {}", bookId);
        return BookResponse.fromEntity(updatedBook);
    }
    
    /**
     * Mendapatkan statistik buku
     */
    @Transactional(readOnly = true)
    public BookStatistics getBookStatistics() {
        log.info("Fetching book statistics");
        
        Long totalBooks = bookRepository.count();
        Long totalStock = bookRepository.getTotalStockQuantity();
        Long availableBooks = bookRepository.countByStatus(Book.BookStatus.AVAILABLE);
        Long borrowedBooks = bookRepository.countByStatus(Book.BookStatus.BORROWED);
        
        return new BookStatistics(totalBooks, totalStock, availableBooks, borrowedBooks);
    }
    
    /**
     * Inner class untuk statistik buku
     */
    public static class BookStatistics {
        private final Long totalBooks;
        private final Long totalStock;
        private final Long availableBooks;
        private final Long borrowedBooks;
        
        public BookStatistics(Long totalBooks, Long totalStock, Long availableBooks, Long borrowedBooks) {
            this.totalBooks = totalBooks;
            this.totalStock = totalStock;
            this.availableBooks = availableBooks;
            this.borrowedBooks = borrowedBooks;
        }
        
        // Getters
        public Long getTotalBooks() { return totalBooks; }
        public Long getTotalStock() { return totalStock; }
        public Long getAvailableBooks() { return availableBooks; }
        public Long getBorrowedBooks() { return borrowedBooks; }
    }
} 