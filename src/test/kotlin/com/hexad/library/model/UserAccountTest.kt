package com.hexad.library.model

import com.hexad.library.exeption.BookNotFoundException
import com.hexad.library.exeption.LimitIsExceededException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows

internal class UserAccountTest {
    @BeforeEach
    fun cleanAccount(){
        UserAccount.clean()
    }

    @Test
    fun addBookNewBook() {
        val book = "book1"
        UserAccount.addBook(book)
        assertEquals(1, UserAccount.getAllBooks().size)
        assertEquals(1, UserAccount.getAllBooks()[book])
    }

    @Test
    fun addSecondCopyOfBook() {
        val book = "book1"
        UserAccount.addBook(book)
        UserAccount.addBook(book)
        assertEquals(1, UserAccount.getAllBooks().size)
        assertEquals(2, UserAccount.getAllBooks()[book])
    }

    @Test
    fun addCopyOfBookBookMoreLimit() {
        val book = "book1"
        UserAccount.addBook(book)
        UserAccount.addBook(book)
        assertThrows<LimitIsExceededException> { UserAccount.addBook(book) }
        assertEquals(1, UserAccount.getAllBooks().size)
        assertEquals(2, UserAccount.getAllBooks()[book])
    }

    @Test
    fun addBookMoreLimit() {
        val book = "book1"
        UserAccount.addBook(book)
        UserAccount.addBook(book)
        assertThrows<LimitIsExceededException> { UserAccount.addBook(book + 1) }
        assertEquals(1, UserAccount.getAllBooks().size)
        assertEquals(2, UserAccount.getAllBooks()[book])
    }

    @Test
    fun addBookBookMoreLimit() {
        val book = "book1"
        UserAccount.addBook(book)
        UserAccount.addBook(book+1)
        assertThrows<LimitIsExceededException> { UserAccount.addBook(book + 2) }
        assertEquals(2, UserAccount.getAllBooks().size)
        assertEquals(1, UserAccount.getAllBooks()[book])
    }

    @Test
    fun returnBook() {
        val book = "book1"
        UserAccount.addBook(book)
        UserAccount.returnBook(book)
        assertEquals(0, UserAccount.getAllBooks().size)
        assertNull(UserAccount.getAllBooks()[book])
    }

    @Test
    fun returnCopyOfBook() {
        val book = "book1"
        UserAccount.addBook(book)
        UserAccount.addBook(book)
        UserAccount.returnBook(book)
        assertEquals(1, UserAccount.getAllBooks().size)
        assertEquals(1, UserAccount.getAllBooks()[book])
    }

    @Test
    fun returnBookNotBorrowedPreviously() {
        val book = "book1"
        UserAccount.addBook(book)
        UserAccount.returnBook(book)
        assertThrows<BookNotFoundException> { UserAccount.returnBook(book) }
        assertEquals(0, UserAccount.getAllBooks().size)
    }

    @Test
    fun returnTheSameBookTwice() {
        val book = "book1"
        assertThrows<BookNotFoundException> { UserAccount.returnBook(book) }
        assertEquals(0, UserAccount.getAllBooks().size)
    }

    @Test
    fun getAllBooks() {
        val book = "book1"
        UserAccount.addBook(book)
        val books = UserAccount.getAllBooks()
        assertEquals(1, books.size)
    }

    @Test
    fun clean() {
        val book = "book1"
        UserAccount.addBook(book)
        UserAccount.addBook(book+1)

        UserAccount.clean()

        assertTrue(UserAccount.getAllBooks().isEmpty())
    }

    @Test
    fun bookCanNotBeAdded() {
        val book = "book1"
        UserAccount.addBook(book)
        UserAccount.addBook(book + 1)

        assertThrows<LimitIsExceededException> { UserAccount.checkLimit() }
    }

    @Test
    fun bookCanBeAdded() {
        val book = "book1"
        UserAccount.addBook(book)
        UserAccount.checkLimit()
    }
}