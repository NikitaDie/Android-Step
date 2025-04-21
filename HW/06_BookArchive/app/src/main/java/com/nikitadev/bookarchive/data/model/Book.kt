package com.nikitadev.bookarchive.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "books",
    foreignKeys = [
        ForeignKey(
            entity = Author::class,
            parentColumns = ["authorId"],
            childColumns = ["author_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Book(
    @PrimaryKey(autoGenerate = true) val bookId: Long = 0,
    val title: String,
    val genre: String,
    @ColumnInfo(name = "publication_year") val publicationYear: Int,
    @ColumnInfo(name = "author_id") val authorId: Long
)
