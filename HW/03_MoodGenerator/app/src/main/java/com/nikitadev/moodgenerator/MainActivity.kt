package com.nikitadev.moodgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikitadev.moodgenerator.ui.theme._03_MoodGeneratorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _03_MoodGeneratorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MoodGeneratorScreen() {
    // List of drawable resources
    val images = listOf(
        R.drawable.funny_cat,
        R.drawable.happy_dog,
        R.drawable.motivation
    )

    // Get string array from resources
    val messages = stringArrayResource(R.array.mood_messages)

    // State for current image and message
    var currentImage by remember { mutableStateOf(images.random()) }
    var currentMessage by remember { mutableStateOf(messages.random()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Image display
        Image(
            painter = painterResource(id = currentImage),
            contentDescription = "Mood image",
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 16.dp)
        )

        // Message display
        Text(
            text = currentMessage,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Button to generate new mood
        Button(
            onClick = {
                currentImage = images.random()
                currentMessage = messages.random()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Boost My Mood!")
        }
    }
}