package com.nikitadev.bookarchive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.nikitadev.bookarchive.data.LibraryDatabase
import com.nikitadev.bookarchive.repository.LibraryRepository
import com.nikitadev.bookarchive.ui.theme._06_BookArchiveTheme
import com.nikitadev.bookarchive.ui.view.LibraryApp
import com.nikitadev.bookarchive.viewmodel.LibraryViewModel
import com.nikitadev.bookarchive.viewmodel.LibraryViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: LibraryViewModel by viewModels {
        val database = LibraryDatabase.getDatabase(this)
        val repository = LibraryRepository(database.libraryDao())
        LibraryViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            _06_BookArchiveTheme {
                LibraryApp(viewModel)
            }
        }
    }
}