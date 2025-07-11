-- ===========================================
-- SCRIPT DATA SEEDING UNTUK TABEL BOOKS
-- ===========================================
-- File ini akan otomatis dijalankan saat aplikasi start
-- karena menggunakan spring.jpa.hibernate.ddl-auto=create-drop

-- Catatan: Script ini dijalankan setelah tabel dibuat oleh Hibernate
-- Tidak perlu DELETE karena tabel baru dibuat setiap kali aplikasi start

-- ===========================================
-- INSERT DATA BUKU SAMPLE
-- ===========================================

-- Buku 1: Programming
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Clean Code: A Handbook of Agile Software Craftsmanship',
    'Robert C. Martin',
    '9780132350884',
    2008,
    'Programming',
    'Buku klasik tentang menulis kode yang bersih dan mudah dipelihara. Berisi prinsip-prinsip dan praktik terbaik untuk menghasilkan kode yang berkualitas tinggi.',
    15,
    750000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 2: Programming
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Design Patterns: Elements of Reusable Object-Oriented Software',
    'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides',
    '9780201633610',
    1994,
    'Programming',
    'Buku yang memperkenalkan 23 design patterns fundamental dalam pengembangan software. Sangat penting untuk memahami arsitektur software.',
    8,
    850000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 3: Programming
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Effective Java',
    'Joshua Bloch',
    '9780134685991',
    2017,
    'Programming',
    'Edisi ketiga dari buku klasik Joshua Bloch yang berisi 90 praktik terbaik untuk pemrograman Java. Wajib dibaca untuk Java developer.',
    12,
    650000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 4: Programming
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Spring in Action',
    'Craig Walls',
    '9781617294945',
    2019,
    'Programming',
    'Panduan lengkap untuk menggunakan Spring Framework. Mencakup Spring Boot, Spring Security, dan fitur-fitur Spring lainnya.',
    10,
    800000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 5: Programming
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Head First Design Patterns',
    'Eric Freeman, Elisabeth Robson',
    '9780596007126',
    2004,
    'Programming',
    'Buku yang menjelaskan design patterns dengan cara yang menyenangkan dan mudah dipahami. Menggunakan pendekatan visual dan interaktif.',
    6,
    700000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 6: Programming
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Refactoring: Improving the Design of Existing Code',
    'Martin Fowler',
    '9780134757599',
    2018,
    'Programming',
    'Edisi kedua dari buku klasik tentang refactoring. Berisi teknik-teknik untuk memperbaiki struktur kode tanpa mengubah perilakunya.',
    4,
    900000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 7: Programming
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Test Driven Development: By Example',
    'Kent Beck',
    '9780321146533',
    2002,
    'Programming',
    'Buku yang memperkenalkan konsep Test Driven Development (TDD). Menjelaskan bagaimana menulis test sebelum menulis kode produksi.',
    3,
    550000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 8: Programming
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'The Pragmatic Programmer: Your Journey to Mastery',
    'Andrew Hunt, David Thomas',
    '9780135957059',
    2019,
    'Programming',
    'Edisi kedua dari buku yang mengajarkan filosofi dan praktik menjadi programmer yang pragmatis. Berisi tips dan trik dari pengalaman praktis.',
    7,
    750000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 9: Programming
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Domain-Driven Design: Tackling Complexity in the Heart of Software',
    'Eric Evans',
    '9780321125217',
    2003,
    'Programming',
    'Buku yang memperkenalkan konsep Domain-Driven Design (DDD). Menjelaskan bagaimana merancang software berdasarkan domain bisnis.',
    5,
    950000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 10: Programming
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Clean Architecture: A Craftsman''s Guide to Software Structure and Design',
    'Robert C. Martin',
    '9780134494166',
    2017,
    'Programming',
    'Buku yang menjelaskan prinsip-prinsip arsitektur software yang bersih. Mencakup SOLID principles dan dependency rules.',
    9,
    850000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 11: Programming (Stok rendah untuk testing)
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Microservices Patterns: With Examples in Java',
    'Chris Richardson',
    '9781617294549',
    2018,
    'Programming',
    'Buku yang menjelaskan berbagai pattern untuk mengembangkan aplikasi microservices. Berisi contoh implementasi dalam Java.',
    2,
    800000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 12: Programming (Stok rendah untuk testing)
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Building Microservices: Designing Fine-Grained Systems',
    'Sam Newman',
    '9781491950357',
    2021,
    'Programming',
    'Edisi kedua dari buku yang menjelaskan konsep dan praktik membangun sistem microservices. Berisi case study dan best practices.',
    1,
    750000.00,
    'AVAILABLE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 13: Programming (Dipinjam untuk testing)
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Kubernetes: Up and Running',
    'Kelsey Hightower, Brendan Burns, Joe Beda',
    '9781491935675',
    2019,
    'Programming',
    'Buku yang menjelaskan cara menggunakan Kubernetes untuk container orchestration. Berisi tutorial dan best practices.',
    0,
    700000.00,
    'BORROWED',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 14: Programming (Dipinjam untuk testing)
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'Docker in Action',
    'Jeff Nickoloff',
    '9781633430235',
    2019,
    'Programming',
    'Buku yang menjelaskan konsep dan praktik menggunakan Docker untuk containerization. Berisi tutorial step-by-step.',
    0,
    650000.00,
    'BORROWED',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Buku 15: Programming (Dipesan untuk testing)
INSERT INTO books (title, author, isbn, publication_year, category, description, stock_quantity, price, status, created_at, updated_at) 
VALUES (
    'The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win',
    'Gene Kim, Kevin Behr, George Spafford',
    '9781942788294',
    2018,
    'Programming',
    'Novel yang menjelaskan konsep DevOps melalui cerita fiksi. Menjelaskan bagaimana IT dapat membantu bisnis berkembang.',
    5,
    600000.00,
    'RESERVED',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ===========================================
-- KOMENTAR TENTANG DATA SEEDING
-- ===========================================
-- 
-- Data ini mencakup berbagai skenario untuk testing:
-- 1. Buku dengan stok normal (5-15)
-- 2. Buku dengan stok rendah (1-2) untuk testing fitur low stock
-- 3. Buku dengan status BORROWED (stok 0)
-- 4. Buku dengan status RESERVED
-- 5. Berbagai kategori dan tahun terbit
-- 6. ISBN yang valid (10 dan 13 digit)
-- 7. Harga yang bervariasi
-- 
-- Total: 15 buku dengan berbagai karakteristik untuk testing menyeluruh 