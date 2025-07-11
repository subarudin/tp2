package com.example.testuas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.testuas.entity.Book;

/**
 * Repository untuk entitas Book
 * 
 * Spring Data JPA Repository menyediakan implementasi otomatis untuk operasi CRUD dasar:
 * - save() - Menyimpan atau mengupdate entitas
 * - findById() - Mencari berdasarkan ID
 * - findAll() - Mengambil semua data
 * - delete() - Menghapus entitas
 * - count() - Menghitung jumlah data
 * 
 * Method query dapat dibuat berdasarkan nama method (Query Method) atau menggunakan @Query
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    /**
     * Mencari buku berdasarkan judul (case-insensitive)
     * Method ini akan otomatis diimplementasikan oleh Spring Data JPA
     * berdasarkan nama method: findBy + Title + Containing + IgnoreCase
     */
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Mencari buku berdasarkan penulis (case-insensitive)
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    /**
     * Mencari buku berdasarkan ISBN
     */
    Optional<Book> findByIsbn(String isbn);
    
    /**
     * Mencari buku berdasarkan kategori
     */
    List<Book> findByCategoryIgnoreCase(String category);
    
    /**
     * Mencari buku berdasarkan tahun terbit
     */
    List<Book> findByPublicationYear(Integer publicationYear);
    
    /**
     * Mencari buku berdasarkan status
     */
    List<Book> findByStatus(Book.BookStatus status);
    
    /**
     * Mencari buku yang tersedia (stok > 0)
     */
    List<Book> findByStockQuantityGreaterThan(Integer quantity);
    
    /**
     * Mencari buku berdasarkan range harga
     */
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);
    
    /**
     * Mencari buku berdasarkan judul dan penulis
     */
    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(
        String title, String author);
    
    /**
     * Query custom menggunakan JPQL (Java Persistence Query Language)
     * Mencari buku berdasarkan keyword di judul, penulis, atau kategori
     */
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchByKeyword(@Param("keyword") String keyword);
    
    /**
     * Query custom untuk menghitung total stok buku
     */
    @Query("SELECT SUM(b.stockQuantity) FROM Book b")
    Long getTotalStockQuantity();
    
    /**
     * Query custom untuk menghitung jumlah buku berdasarkan status
     */
    @Query("SELECT COUNT(b) FROM Book b WHERE b.status = :status")
    Long countByStatus(@Param("status") Book.BookStatus status);
    
    /**
     * Query custom untuk mencari buku dengan stok rendah (<= 5)
     */
    @Query("SELECT b FROM Book b WHERE b.stockQuantity <= 5 ORDER BY b.stockQuantity ASC")
    List<Book> findBooksWithLowStock();
    
    /**
     * Query custom untuk mencari buku terbaru (berdasarkan tahun terbit)
     */
    @Query("SELECT b FROM Book b WHERE b.publicationYear >= :year ORDER BY b.publicationYear DESC")
    List<Book> findRecentBooks(@Param("year") Integer year);
} 