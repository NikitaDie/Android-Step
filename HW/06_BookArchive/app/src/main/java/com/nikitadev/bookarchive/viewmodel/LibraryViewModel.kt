package com.nikitadev.bookarchive.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitadev.bookarchive.data.model.Author
import com.nikitadev.bookarchive.data.model.Book
import com.nikitadev.bookarchive.repository.LibraryRepository
import kotlinx.coroutines.launch

class LibraryViewModel(private val repository: LibraryRepository) : ViewModel() {
    val allAuthors: LiveData<List<Author>> = repository.allAuthors

    fun getBooksByAuthor(authorId: Long): LiveData<List<Book>> {
        return repository.getBooksByAuthor(authorId)
    }

    fun insertAuthor(author: Author) = viewModelScope.launch {
        repository.insertAuthor(author)
    }

    fun insertBook(book: Book) = viewModelScope.launch {
        repository.insertBook(book)
    }

    fun updateBook(book: Book) = viewModelScope.launch {
        repository.updateBook(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch {
        repository.deleteBook(book)
    }
}