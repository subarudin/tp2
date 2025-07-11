package com.example.testuas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.testuas.dto.BookRequest;
import com.example.testuas.dto.BookResponse;
import com.example.testuas.entity.Book;
import com.example.testuas.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller untuk manajemen buku
 * 
 * Dependency Injection (DI) bekerja di sini:
 * 1. @RestController - Menandai class sebagai REST controller
 * 2. @RequiredArgsConstructor - Lombok: generate constructor untuk final fields
 * 3. BookService diinjeksi melalui constructor
 * 
 * Arsitektur tiga lapis:
 * Client -> Controller -> Service -> Repository
 * 
 * Controller layer bertanggung jawab untuk:
 * - Menerima HTTP requests
 * - Validasi input menggunakan @Valid
 * - Memanggil service layer
 * - Mengembalikan HTTP responses
 * - Error handling
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*") // Mengizinkan CORS untuk development
public class BookController {
    
    private final BookService bookService;
    
    /**
     * POST /api/books - Menambah buku baru
     * 
     * @Valid - Mengaktifkan validasi pada BookRequest
     * Validasi akan memeriksa anotasi seperti @NotBlank, @Size, dll.
     * Jika validasi gagal, akan throw MethodArgumentNotValidException
     */
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        log.info("Received request to create book: {}", request.getTitle());
        
        try {
            BookResponse response = bookService.createBook(request);
            log.info("Book created successfully with ID: {}", response.getId());
            
            // HTTP 201 Created - Resource berhasil dibuat
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            log.error("Failed to create book: {}", e.getMessage());
            // HTTP 400 Bad Request - Request tidak valid
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Unexpected error while creating book: {}", e.getMessage());
            // HTTP 500 Internal Server Error - Error server
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/books - Mengambil semua buku
     */
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.info("Received request to get all books");
        
        try {
            List<BookResponse> books = bookService.getAllBooks();
            log.info("Retrieved {} books", books.size());
            
            // HTTP 200 OK - Request berhasil
            return ResponseEntity.ok(books);
            
        } catch (Exception e) {
            log.error("Error while fetching all books: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/books/{id} - Mengambil buku berdasarkan ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        log.info("Received request to get book with ID: {}", id);
        
        try {
            BookResponse book = bookService.getBookById(id);
            log.info("Retrieved book: {}", book.getTitle());
            
            return ResponseEntity.ok(book);
            
        } catch (IllegalArgumentException e) {
            log.error("Book not found with ID: {}", id);
            // HTTP 404 Not Found - Resource tidak ditemukan
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error while fetching book with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT /api/books/{id} - Mengupdate buku berdasarkan ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, 
                                                 @Valid @RequestBody BookRequest request) {
        log.info("Received request to update book with ID: {}", id);
        
        try {
            BookResponse response = bookService.updateBook(id, request);
            log.info("Book updated successfully with ID: {}", id);
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.error("Failed to update book with ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while updating book with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * DELETE /api/books/{id} - Menghapus buku berdasarkan ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.info("Received request to delete book with ID: {}", id);
        
        try {
            bookService.deleteBook(id);
            log.info("Book deleted successfully with ID: {}", id);
            
            // HTTP 204 No Content - Resource berhasil dihapus
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            log.error("Failed to delete book with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error while deleting book with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/books/search?keyword={keyword} - Mencari buku berdasarkan keyword
     */
    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(@RequestParam String keyword) {
        log.info("Received request to search books with keyword: {}", keyword);
        
        try {
            List<BookResponse> books = bookService.searchBooks(keyword);
            log.info("Found {} books matching keyword: {}", books.size(), keyword);
            
            return ResponseEntity.ok(books);
            
        } catch (Exception e) {
            log.error("Error while searching books: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/books/title/{title} - Mencari buku berdasarkan judul
     */
    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookResponse>> getBooksByTitle(@PathVariable String title) {
        log.info("Received request to get books by title: {}", title);
        
        try {
            List<BookResponse> books = bookService.getBooksByTitle(title);
            log.info("Found {} books with title: {}", books.size(), title);
            
            return ResponseEntity.ok(books);
            
        } catch (Exception e) {
            log.error("Error while fetching books by title: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/books/author/{author} - Mencari buku berdasarkan penulis
     */
    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable String author) {
        log.info("Received request to get books by author: {}", author);
        
        try {
            List<BookResponse> books = bookService.getBooksByAuthor(author);
            log.info("Found {} books by author: {}", books.size(), author);
            
            return ResponseEntity.ok(books);
            
        } catch (Exception e) {
            log.error("Error while fetching books by author: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/books/category/{category} - Mencari buku berdasarkan kategori
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookResponse>> getBooksByCategory(@PathVariable String category) {
        log.info("Received request to get books by category: {}", category);
        
        try {
            List<BookResponse> books = bookService.getBooksByCategory(category);
            log.info("Found {} books in category: {}", books.size(), category);
            
            return ResponseEntity.ok(books);
            
        } catch (Exception e) {
            log.error("Error while fetching books by category: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/books/status/{status} - Mencari buku berdasarkan status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookResponse>> getBooksByStatus(@PathVariable Book.BookStatus status) {
        log.info("Received request to get books by status: {}", status);
        
        try {
            List<BookResponse> books = bookService.getBooksByStatus(status);
            log.info("Found {} books with status: {}", books.size(), status);
            
            return ResponseEntity.ok(books);
            
        } catch (Exception e) {
            log.error("Error while fetching books by status: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/books/low-stock - Mencari buku dengan stok rendah
     */
    @GetMapping("/low-stock")
    public ResponseEntity<List<BookResponse>> getBooksWithLowStock() {
        log.info("Received request to get books with low stock");
        
        try {
            List<BookResponse> books = bookService.getBooksWithLowStock();
            log.info("Found {} books with low stock", books.size());
            
            return ResponseEntity.ok(books);
            
        } catch (Exception e) {
            log.error("Error while fetching books with low stock: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * POST /api/books/{id}/add-stock - Menambah stok buku
     */
    @PostMapping("/{id}/add-stock")
    public ResponseEntity<BookResponse> addStock(@PathVariable Long id, 
                                               @RequestParam Integer quantity) {
        log.info("Received request to add stock for book ID: {} with quantity: {}", id, quantity);
        
        try {
            BookResponse response = bookService.addStock(id, quantity);
            log.info("Stock added successfully for book ID: {}", id);
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.error("Failed to add stock for book ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while adding stock for book ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * POST /api/books/{id}/reduce-stock - Mengurangi stok buku
     */
    @PostMapping("/{id}/reduce-stock")
    public ResponseEntity<BookResponse> reduceStock(@PathVariable Long id, 
                                                  @RequestParam Integer quantity) {
        log.info("Received request to reduce stock for book ID: {} with quantity: {}", id, quantity);
        
        try {
            BookResponse response = bookService.reduceStock(id, quantity);
            log.info("Stock reduced successfully for book ID: {}", id);
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.error("Failed to reduce stock for book ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while reducing stock for book ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/books/statistics - Mendapatkan statistik buku
     */
    @GetMapping("/statistics")
    public ResponseEntity<BookService.BookStatistics> getBookStatistics() {
        log.info("Received request to get book statistics");
        
        try {
            BookService.BookStatistics statistics = bookService.getBookStatistics();
            log.info("Retrieved book statistics: total books={}, total stock={}", 
                    statistics.getTotalBooks(), statistics.getTotalStock());
            
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            log.error("Error while fetching book statistics: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 