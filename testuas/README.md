# Sistem Manajemen Buku Perpustakaan - Spring Boot

## 📋 Deskripsi Proyek

Sistem manajemen buku perpustakaan yang dibangun menggunakan Spring Boot dengan arsitektur tiga lapis (3-Tier Architecture). Sistem ini menyediakan RESTful API untuk operasi CRUD buku, pencarian, manajemen stok, dan statistik perpustakaan.

## 🏗️ Arsitektur Sistem

### Arsitektur Tiga Lapis (3-Tier Architecture)

```
┌─────────────────┐
│   Controller    │ ← Layer Presentasi (REST API)
├─────────────────┤
│    Service      │ ← Layer Bisnis (Business Logic)
├─────────────────┤
│   Repository    │ ← Layer Data (Database Access)
└─────────────────┘
```

### Dependency Injection (DI) Flow

```
BookController → BookService → BookRepository
     ↓              ↓              ↓
  @RestController  @Service    @Repository
  @Valid          @Transactional  JpaRepository
  HTTP Response   Business Logic  Database
```

## 🛠️ Teknologi yang Digunakan

- **Spring Boot 3.5.3** - Framework utama
- **Spring Data JPA** - ORM dan database access
- **H2 Database** - Database in-memory untuk development
- **Spring Validation** - Validasi data input
- **Lombok** - Mengurangi boilerplate code
- **Maven** - Build tool dan dependency management

## 📁 Struktur Proyek

```
src/main/java/com/example/testuas/
├── entity/
│   └── Book.java                    # Entitas JPA
├── dto/
│   ├── BookRequest.java             # DTO untuk request
│   └── BookResponse.java            # DTO untuk response
├── repository/
│   └── BookRepository.java          # Data access layer
├── service/
│   └── BookService.java             # Business logic layer
├── controller/
│   └── BookController.java          # REST API layer
└── exception/
    └── GlobalExceptionHandler.java  # Error handling

src/main/resources/
├── application.properties           # Konfigurasi aplikasi
└── data.sql                        # Data seeding
```

## 🚀 Cara Menjalankan Aplikasi

### Prerequisites
- Java 21 atau lebih tinggi
- Maven 3.6 atau lebih tinggi

### Langkah-langkah

1. **Clone dan masuk ke direktori proyek**
   ```bash
   cd testuas
   ```

2. **Build proyek**
   ```bash
   mvn clean install
   ```

3. **Jalankan aplikasi**
   ```bash
   mvn spring-boot:run
   ```

4. **Akses aplikasi**
   - Aplikasi berjalan di: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`

## 🗄️ Konfigurasi Database H2

### Detail Koneksi H2 Console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`
- **Driver Class**: `org.h2.Driver`

### Properti Konfigurasi Penting

```properties
# Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console Configuration
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true
```

## 📊 Entitas Buku (Book Entity)

### Anotasi JPA yang Digunakan

| Anotasi | Fungsi | Penjelasan |
|---------|--------|------------|
| `@Entity` | Menandai class sebagai entitas JPA | Class akan dipetakan ke tabel database |
| `@Table(name = "books")` | Menentukan nama tabel | Tabel akan bernama "books" |
| `@Id` | Menandai field sebagai primary key | Field yang unik untuk identifikasi |
| `@GeneratedValue` | Auto-generation untuk primary key | ID akan otomatis bertambah |
| `@Column` | Konfigurasi mapping kolom | Mengatur nama, nullable, length, dll |
| `@Enumerated` | Mapping enum ke database | Enum disimpan sebagai string |
| `@CreationTimestamp` | Timestamp otomatis saat create | Mengisi waktu pembuatan |
| `@UpdateTimestamp` | Timestamp otomatis saat update | Mengisi waktu update |

### Validasi Data Input

| Anotasi | Fungsi | Contoh |
|---------|--------|--------|
| `@NotBlank` | String tidak boleh kosong | `@NotBlank(message = "Judul tidak boleh kosong")` |
| `@Size` | Validasi panjang string | `@Size(min = 1, max = 255)` |
| `@Pattern` | Validasi dengan regex | `@Pattern(regexp = "^(?:[0-9]{10}\|[0-9]{13})$")` |
| `@Min/@Max` | Validasi nilai numerik | `@Min(1000) @Max(2025)` |
| `@PositiveOrZero` | Nilai positif atau nol | `@PositiveOrZero` |
| `@DecimalMin` | Nilai decimal minimal | `@DecimalMin("0.0")` |

## 🔗 RESTful API Endpoints

### Base URL: `http://localhost:8080/api/books`

### 1. CRUD Operations

#### POST - Menambah Buku Baru
```http
POST /api/books
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "9780132350884",
  "publicationYear": 2008,
  "category": "Programming",
  "description": "Buku tentang menulis kode yang bersih",
  "stockQuantity": 10,
  "price": 750000.00
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "9780132350884",
  "publicationYear": 2008,
  "category": "Programming",
  "description": "Buku tentang menulis kode yang bersih",
  "stockQuantity": 10,
  "price": 750000.00,
  "status": "AVAILABLE",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### GET - Mengambil Semua Buku
```http
GET /api/books
```

#### GET - Mengambil Buku berdasarkan ID
```http
GET /api/books/{id}
```

#### PUT - Mengupdate Buku
```http
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Clean Code Updated",
  "author": "Robert C. Martin",
  "isbn": "9780132350884",
  "publicationYear": 2008,
  "category": "Programming",
  "description": "Buku tentang menulis kode yang bersih (Updated)",
  "stockQuantity": 15,
  "price": 800000.00
}
```

#### DELETE - Menghapus Buku
```http
DELETE /api/books/{id}
```

### 2. Search Operations

#### GET - Pencarian berdasarkan Keyword
```http
GET /api/books/search?keyword=clean
```

#### GET - Pencarian berdasarkan Judul
```http
GET /api/books/title/{title}
```

#### GET - Pencarian berdasarkan Penulis
```http
GET /api/books/author/{author}
```

#### GET - Pencarian berdasarkan Kategori
```http
GET /api/books/category/{category}
```

#### GET - Pencarian berdasarkan Status
```http
GET /api/books/status/{status}
```

### 3. Stock Management

#### POST - Menambah Stok
```http
POST /api/books/{id}/add-stock?quantity=5
```

#### POST - Mengurangi Stok
```http
POST /api/books/{id}/reduce-stock?quantity=2
```

### 4. Special Queries

#### GET - Buku dengan Stok Rendah
```http
GET /api/books/low-stock
```

#### GET - Statistik Buku
```http
GET /api/books/statistics
```

**Response:**
```json
{
  "totalBooks": 15,
  "totalStock": 120,
  "availableBooks": 12,
  "borrowedBooks": 2
}
```

## 🔍 Testing API

### Menggunakan cURL

#### 1. Menambah Buku Baru
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Book",
    "author": "Test Author",
    "isbn": "9781234567890",
    "publicationYear": 2024,
    "category": "Testing",
    "description": "Buku untuk testing",
    "stockQuantity": 5,
    "price": 500000.00
  }'
```

#### 2. Mengambil Semua Buku
```bash
curl -X GET http://localhost:8080/api/books
```

#### 3. Mencari Buku
```bash
curl -X GET "http://localhost:8080/api/books/search?keyword=clean"
```

### Menggunakan Postman

1. **Import Collection**: Buat collection baru di Postman
2. **Base URL**: Set `{{baseUrl}}` variable ke `http://localhost:8080/api/books`
3. **Test Endpoints**: Gunakan endpoint yang sudah didokumentasikan di atas

## 🗄️ Database Schema

### Tabel `books`

| Kolom | Tipe | Nullable | Deskripsi |
|-------|------|----------|------------|
| `id` | BIGINT | NO | Primary Key, Auto Increment |
| `title` | VARCHAR(255) | NO | Judul buku |
| `author` | VARCHAR(255) | NO | Nama penulis |
| `isbn` | VARCHAR(20) | YES | ISBN buku (unique) |
| `publication_year` | INT | YES | Tahun terbit |
| `category` | VARCHAR(100) | YES | Kategori/genre |
| `description` | TEXT | YES | Deskripsi buku |
| `stock_quantity` | INT | NO | Jumlah stok (default: 0) |
| `price` | DECIMAL(10,2) | YES | Harga buku |
| `status` | VARCHAR(20) | NO | Status buku (default: AVAILABLE) |
| `created_at` | TIMESTAMP | NO | Waktu pembuatan |
| `updated_at` | TIMESTAMP | NO | Waktu update terakhir |

### Enum BookStatus

- `AVAILABLE` - Tersedia
- `BORROWED` - Dipinjam
- `RESERVED` - Dipesan
- `LOST` - Hilang
- `DAMAGED` - Rusak

## 🔧 Konfigurasi Development

### Logging
```properties
# SQL Query Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### CORS (Cross-Origin Resource Sharing)
```java
@CrossOrigin(origins = "*") // Untuk development
```

### Error Handling
- **400 Bad Request**: Data tidak valid
- **404 Not Found**: Resource tidak ditemukan
- **500 Internal Server Error**: Error server

## 📈 Fitur Utama

### 1. Validasi Data
- Validasi input menggunakan Jakarta Validation
- Custom error messages dalam bahasa Indonesia
- Global exception handling

### 2. Pencarian Canggih
- Pencarian berdasarkan keyword (judul, penulis, kategori)
- Pencarian berdasarkan status buku
- Pencarian buku dengan stok rendah

### 3. Manajemen Stok
- Penambahan dan pengurangan stok
- Validasi stok sebelum operasi
- Update status otomatis berdasarkan stok

### 4. Statistik
- Total buku dan stok
- Jumlah buku berdasarkan status
- Monitoring stok rendah

## 🧪 Testing

### Data Seeding
File `data.sql` berisi 15 buku sample dengan berbagai karakteristik:
- Buku dengan stok normal (5-15)
- Buku dengan stok rendah (1-2)
- Buku dengan status BORROWED (stok 0)
- Buku dengan status RESERVED
- Berbagai kategori dan tahun terbit

### Test Cases
1. **CRUD Operations**: Create, Read, Update, Delete
2. **Validation**: Test validasi input
3. **Search**: Test berbagai jenis pencarian
4. **Stock Management**: Test penambahan/pengurangan stok
5. **Error Handling**: Test error scenarios

## 🚀 Deployment

### Production Configuration
1. Ganti H2 dengan database production (MySQL, PostgreSQL)
2. Update `application.properties` untuk production
3. Set `spring.jpa.hibernate.ddl-auto=validate`
4. Konfigurasi logging level yang sesuai
5. Setup CORS yang proper

### Docker Deployment
```dockerfile
FROM openjdk:21-jdk-slim
COPY target/testuas-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📚 Referensi

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database](https://www.h2database.com/)
- [Jakarta Validation](https://beanvalidation.org/)
- [RESTful API Design](https://restfulapi.net/)

## 🤝 Kontribusi

1. Fork proyek
2. Buat feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push ke branch (`git push origin feature/AmazingFeature`)
5. Buat Pull Request

## 📄 License

Proyek ini dilisensikan di bawah MIT License - lihat file [LICENSE](LICENSE) untuk detail.

---

**Dibuat dengan ❤️ menggunakan Spring Boot** 