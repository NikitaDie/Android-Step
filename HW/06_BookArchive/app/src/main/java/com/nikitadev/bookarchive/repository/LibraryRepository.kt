package com.nikitadev.bookarchive.repository

import androidx.lifecycle.LiveData
import com.nikitadev.bookarchive.data.LibraryDao
import com.nikitadev.bookarchive.data.model.Author
import com.nikitadev.bookarchive.data.model.Book


class LibraryRepository(private val libraryDao: LibraryDao) {
    val allAuthors: LiveData<List<Author>> = libraryDao.getAllAuthors()

    fun getBooksByAuthor(authorId: Long): LiveData<List<Book>> {
        return libraryDao.getBooksByAuthor(authorId)
    }

    suspend fun insertAuthor(author: Author) {
        libraryDao.insertAuthor(author)
    }

    suspend fun insertBook(book: Book) {
        libraryDao.insertBook(book)
    }

    suspend fun updateBook(book: Book) {
        libraryDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        libraryDao.deleteBook(book)
    }
}