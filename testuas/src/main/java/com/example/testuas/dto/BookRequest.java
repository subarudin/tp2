package com.example.testuas.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) untuk request data buku
 * Digunakan untuk menerima data dari client saat membuat atau mengupdate buku
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    
    @NotBlank(message = "Judul buku tidak boleh kosong")
    @Size(min = 1, max = 255, message = "Judul buku harus antara 1-255 karakter")
    private String title;
    
    @NotBlank(message = "Nama penulis tidak boleh kosong")
    @Size(min = 1, max = 255, message = "Nama penulis harus antara 1-255 karakter")
    private String author;
    
    @Pattern(regexp = "^(?:[0-9]{10}|[0-9]{13})$", 
             message = "ISBN harus berupa 10 atau 13 digit angka")
    private String isbn;
    
    @Min(value = 1000, message = "Tahun terbit tidak valid")
    @Max(value = 2025, message = "Tahun terbit tidak boleh lebih dari 2025")
    private Integer publicationYear;
    
    @Size(max = 100, message = "Kategori tidak boleh lebih dari 100 karakter")
    private String category;
    
    @Size(max = 2000, message = "Deskripsi tidak boleh lebih dari 2000 karakter")
    private String description;
    
    @PositiveOrZero(message = "Jumlah stok tidak boleh negatif")
    private Integer stockQuantity = 0;
    
    @DecimalMin(value = "0.0", message = "Harga tidak boleh negatif")
    private Double price;
} 