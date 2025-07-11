package com.example.testuas.dto;

import com.example.testuas.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) untuk response data buku
 * Digunakan untuk mengirim data buku ke client
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publicationYear;
    private String category;
    private String description;
    private Integer stockQuantity;
    private Double price;
    private Book.BookStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Method untuk mengkonversi dari entitas Book ke BookResponse
     */
    public static BookResponse fromEntity(Book book) {
        return new BookResponse(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getPublicationYear(),
            book.getCategory(),
            book.getDescription(),
            book.getStockQuantity(),
            book.getPrice(),
            book.getStatus(),
            book.getCreatedAt(),
            book.getUpdatedAt()
        );
    }
} 