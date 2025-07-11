package com.example.testuas.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
 * Entitas Buku - Representasi tabel 'books' dalam database
 * 
 * Anotasi JPA yang digunakan:
 * @Entity - Menandai class sebagai entitas JPA yang akan dipetakan ke tabel database
 * @Table - Menentukan nama tabel dan konfigurasi tambahan
 * @Id - Menandai field sebagai primary key
 * @GeneratedValue - Mengatur strategi auto-generation untuk primary key
 * @Column - Mengatur mapping kolom dengan konfigurasi spesifik
 * @CreationTimestamp - Otomatis mengisi timestamp saat entitas dibuat
 * @UpdateTimestamp - Otomatis mengupdate timestamp saat entitas diubah
 */
@Entity
@Table(name = "books")
@Data                   // Lombok: generate getter, setter, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generate constructor tanpa parameter
@AllArgsConstructor     // Lombok: generate constructor dengan semua parameter
public class Book {
    
    /**
     * Primary Key - ID buku
     * @Id - Menandai field sebagai primary key
     * @GeneratedValue - Mengatur strategi auto-generation
     *   strategy = GenerationType.IDENTITY - Menggunakan auto-increment database
     *   strategy = GenerationType.SEQUENCE - Menggunakan sequence database
     *   strategy = GenerationType.TABLE - Menggunakan tabel terpisah untuk ID
     *   strategy = GenerationType.AUTO - Hibernate memilih strategi terbaik
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    
    /**
     * Judul buku
     * @Column - Mengatur mapping kolom
     *   name = "title" - Nama kolom di database
     *   nullable = false - Kolom tidak boleh null
     *   length = 255 - Panjang maksimal string
     * @NotBlank - Validasi: string tidak boleh kosong atau hanya whitespace
     * @Size - Validasi: panjang string minimal 1, maksimal 255 karakter
     */
    @Column(name = "title", nullable = false, length = 255)
    @NotBlank(message = "Judul buku tidak boleh kosong")
    @Size(min = 1, max = 255, message = "Judul buku harus antara 1-255 karakter")
    private String title;
    
    /**
     * Penulis buku
     */
    @Column(name = "author", nullable = false, length = 255)
    @NotBlank(message = "Nama penulis tidak boleh kosong")
    @Size(min = 1, max = 255, message = "Nama penulis harus antara 1-255 karakter")
    private String author;
    
    /**
     * ISBN buku
     * @Pattern - Validasi: harus sesuai dengan format ISBN (10 atau 13 digit)
     */
    @Column(name = "isbn", unique = true, length = 20)
    @Pattern(regexp = "^(?:[0-9]{10}|[0-9]{13})$", 
             message = "ISBN harus berupa 10 atau 13 digit angka")
    private String isbn;
    
    /**
     * Tahun terbit
     * @Min - Validasi: nilai minimal 1000 (tahun yang masuk akal)
     * @Max - Validasi: nilai maksimal tahun saat ini + 1
     */
    @Column(name = "publication_year")
    @Min(value = 1000, message = "Tahun terbit tidak valid")
    @Max(value = 2025, message = "Tahun terbit tidak boleh lebih dari 2025")
    private Integer publicationYear;
    
    /**
     * Kategori/genre buku
     */
    @Column(name = "category", length = 100)
    @Size(max = 100, message = "Kategori tidak boleh lebih dari 100 karakter")
    private String category;
    
    /**
     * Deskripsi buku
     * @Column(columnDefinition = "TEXT") - Menggunakan tipe TEXT di database
     */
    @Column(name = "description", columnDefinition = "TEXT")
    @Size(max = 2000, message = "Deskripsi tidak boleh lebih dari 2000 karakter")
    private String description;
    
    /**
     * Jumlah stok buku
     * @PositiveOrZero - Validasi: nilai harus positif atau nol
     */
    @Column(name = "stock_quantity", nullable = false)
    @PositiveOrZero(message = "Jumlah stok tidak boleh negatif")
    private Integer stockQuantity = 0;
    
    /**
     * Harga buku
     * @DecimalMin - Validasi: nilai minimal 0.0
     */
    @Column(name = "price")
    @DecimalMin(value = "0.0", message = "Harga tidak boleh negatif")
    private Double price;
    
    /**
     * Status ketersediaan buku
     * @Enumerated - Mengatur bagaimana enum disimpan di database
     *   EnumType.STRING - Disimpan sebagai string
     *   EnumType.ORDINAL - Disimpan sebagai angka (0, 1, 2, dst)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;
    
    /**
     * Timestamp saat entitas dibuat
     * @CreationTimestamp - Otomatis mengisi nilai saat entitas dibuat
     * @Column(updatable = false) - Tidak bisa diupdate setelah dibuat
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp saat entitas terakhir diupdate
     * @UpdateTimestamp - Otomatis mengupdate nilai saat entitas diubah
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * Enum untuk status buku
     */
    public enum BookStatus {
        AVAILABLE("Tersedia"),
        BORROWED("Dipinjam"),
        RESERVED("Dipesan"),
        LOST("Hilang"),
        DAMAGED("Rusak");
        
        private final String displayName;
        
        BookStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Method untuk menambah stok buku
     */
    public void addStock(int quantity) {
        if (quantity > 0) {
            this.stockQuantity += quantity;
            updateStatus();
        }
    }
    
    /**
     * Method untuk mengurangi stok buku
     */
    public void reduceStock(int quantity) {
        if (quantity > 0 && this.stockQuantity >= quantity) {
            this.stockQuantity -= quantity;
            updateStatus();
        }
    }
    
    /**
     * Method untuk mengupdate status berdasarkan stok
     */
    private void updateStatus() {
        if (this.stockQuantity > 0) {
            this.status = BookStatus.AVAILABLE;
        } else {
            this.status = BookStatus.BORROWED;
        }
    }
} 