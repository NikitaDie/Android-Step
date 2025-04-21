package com.nikitadev.bookarchive.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nikitadev.bookarchive.data.model.*

@Dao
interface LibraryDao {
    @Insert
    suspend fun insertAuthor(author: Author)

    @Insert
    suspend fun insertBook(book: Book)

    @Query("SELECT * FROM authors")
    fun getAllAuthors(): LiveData<List<Author>>

    @Query("SELECT * FROM books WHERE author_id = :authorId")
    fun getBooksByAuthor(authorId: Long): LiveData<List<Book>>

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}