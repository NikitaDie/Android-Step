package com.nikitadev.bookarchive.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikitadev.bookarchive.data.model.Book
import com.nikitadev.bookarchive.ui.view.components.AddAuthorDialog
import com.nikitadev.bookarchive.ui.view.components.AddBookDialog
import com.nikitadev.bookarchive.ui.view.components.AuthorItem
import com.nikitadev.bookarchive.ui.view.components.BookItem
import com.nikitadev.bookarchive.ui.view.components.EditBookDialog
import com.nikitadev.bookarchive.ui.view.components.SectionHeader
import com.nikitadev.bookarchive.viewmodel.LibraryViewModel

@Composable
fun LibraryApp(viewModel: LibraryViewModel) {
    var selectedAuthorId by remember { mutableStateOf<Long?>(null) }
    var showAddAuthorDialog by remember { mutableStateOf(false) }
    var showAddBookDialog by remember { mutableStateOf(false) }
    var showEditBookDialog by remember { mutableStateOf<Book?>(null) }

    val authors by viewModel.allAuthors.observeAsState(emptyList())
    val books = selectedAuthorId?.let { viewModel.getBooksByAuthor(it).observeAsState(emptyList()).value } ?: emptyList()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Section: Author Header & Add
        SectionHeader("Authors", onAdd = { showAddAuthorDialog = true })

        // Authors List
        LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
            items(authors) { author ->
                val isSelected = author.authorId == selectedAuthorId
                AuthorItem(author = author, isSelected = isSelected) {
                    selectedAuthorId = author.authorId
                }
            }
        }

        // Section: Books
        selectedAuthorId?.let {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader("Books", onAdd = { showAddBookDialog = true })

            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(books) { book ->
                    BookItem(
                        book = book,
                        onEdit = { showEditBookDialog = book },
                        onDelete = { viewModel.deleteBook(book) }
                    )
                }
            }
        }
    }

    // Dialogs
    if (showAddAuthorDialog) AddAuthorDialog(
        onDismiss = { showAddAuthorDialog = false },
        onAdd = { viewModel.insertAuthor(it); showAddAuthorDialog = false }
    )

    if (showAddBookDialog && selectedAuthorId != null) AddBookDialog(
        authorId = selectedAuthorId!!,
        onDismiss = { showAddBookDialog = false },
        onAdd = { viewModel.insertBook(it); showAddBookDialog = false }
    )

    showEditBookDialog?.let { book ->
        EditBookDialog(
            book = book,
            onDismiss = { showEditBookDialog = null },
            onSave = {
                viewModel.updateBook(it)
                showEditBookDialog = null
            }
        )
    }
}