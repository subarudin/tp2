// Global variables
let books = [];
let currentBookId = null;
let currentPage = 1;
const booksPerPage = 10;

// API Base URL
const API_BASE_URL = '/api/books';

// DOM Elements
const bookTableBody = document.getElementById('bookTableBody');
const addBookForm = document.getElementById('addBookForm');
const themeToggle = document.getElementById('themeToggle');
const mobileMenuToggle = document.getElementById('mobileMenuToggle');
const sidebar = document.getElementById('sidebar');

// Theme Management
function initTheme() {
    const savedTheme = localStorage.getItem('theme') || 'light';
    document.documentElement.setAttribute('data-theme', savedTheme);
    updateThemeIcon(savedTheme);
}

function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    
    document.documentElement.setAttribute('data-theme', newTheme);
    localStorage.setItem('theme', newTheme);
    updateThemeIcon(newTheme);
}

function updateThemeIcon(theme) {
    const icon = themeToggle.querySelector('i');
    if (theme === 'dark') {
        icon.className = 'fas fa-sun';
        themeToggle.title = 'Switch to Light Mode';
    } else {
        icon.className = 'fas fa-moon';
        themeToggle.title = 'Switch to Dark Mode';
    }
}

// Load and display books
async function loadBooks() {
    try {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) throw new Error('Gagal mengambil data');
        const books = await response.json();
        displayBooks(books);
    } catch (err) {
        bookTableBody.innerHTML = `<tr><td colspan="10">Gagal memuat data buku</td></tr>`;
    }
}

function displayBooks(books) {
    bookTableBody.innerHTML = '';
    if (!books.length) {
        bookTableBody.innerHTML = `<tr><td colspan="10">Tidak ada data buku</td></tr>`;
        return;
    }
    books.forEach(book => {
        bookTableBody.innerHTML += `
            <tr>
                <td>${book.id}</td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.isbn || ''}</td>
                <td>${book.publicationYear || ''}</td>
                <td>${book.category || ''}</td>
                <td>${book.stockQuantity}</td>
                <td>${book.price}</td>
                <td>${book.status}</td>
                <td>
                    <button onclick="editBook(${book.id})">Edit</button>
                    <button onclick="deleteBook(${book.id})">Hapus</button>
                </td>
            </tr>
        `;
    });
}

// Add book
addBookForm.addEventListener('submit', async function(e) {
    e.preventDefault();
    const formData = new FormData(addBookForm);
    const bookData = {
        title: formData.get('title'),
        author: formData.get('author'),
        isbn: formData.get('isbn'),
        publicationYear: formData.get('publicationYear'),
        category: formData.get('category'),
        description: formData.get('description'),
        stockQuantity: formData.get('stockQuantity'),
        price: formData.get('price'),
        status: formData.get('status')
    };
    try {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(bookData)
        });
        if (!response.ok) throw new Error('Gagal menambah buku');
        addBookForm.reset();
        loadBooks();
    } catch (err) {
        alert('Gagal menambah buku');
    }
});

// Delete book
async function deleteBook(id) {
    if (!confirm('Yakin hapus buku ini?')) return;
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`, { method: 'DELETE' });
        if (!response.ok) throw new Error('Gagal menghapus buku');
        loadBooks();
    } catch (err) {
        alert('Gagal menghapus buku');
    }
}

// Edit book (placeholder, implementasi modal/form edit bisa ditambah nanti)
function editBook(id) {
    alert('Fitur edit belum diimplementasikan.');
}

// Mobile Menu Toggle
function toggleMobileMenu() {
    sidebar.classList.toggle('active');
}

// Event Listeners
themeToggle.addEventListener('click', toggleTheme);
mobileMenuToggle.addEventListener('click', toggleMobileMenu);

// Close sidebar when clicking outside on mobile
document.addEventListener('click', function(e) {
    if (window.innerWidth <= 768) {
        if (!sidebar.contains(e.target) && !mobileMenuToggle.contains(e.target)) {
            sidebar.classList.remove('active');
        }
    }
});

document.addEventListener('DOMContentLoaded', function() {
    initTheme();
    loadBooks();
}); 