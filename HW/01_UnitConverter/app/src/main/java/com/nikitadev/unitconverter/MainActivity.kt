package com.nikitadev.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikitadev.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverterScreen()
                }
            }
        }
    }
}

@Composable
fun UnitConverterScreen() {
    var inputValue by remember { mutableStateOf("") }
    var selectedConversion by remember { mutableIntStateOf(0) }
    var result by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val conversionOptions = listOf(
        "Кілометри → Милі",
        "Милі → Кілометри",
        "Кілограми → Фунти",
        "Фунти → Кілограми",
        "Цельсій → Фаренгейт",
        "Фаренгейт → Цельсій"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Result text
        Text(
            text = result,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        )

        // Input field for the value
        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Введіть значення") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Dropdown menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = conversionOptions[selectedConversion],
                onValueChange = {},
                readOnly = true,
                label = { Text("Тип конвертації") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                conversionOptions.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedConversion = index
                            expanded = false
                        }
                    )
                }
            }
        }

        // Convert button
        Button(
            onClick = {
                result = convertUnits(inputValue, selectedConversion)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Конвертувати")
        }
    }
}

fun convertUnits(inputStr: String, selectedConversion: Int): String {
    if (inputStr.isEmpty()) {
        return "Будь ласка, введіть значення"
    }

    val input = inputStr.toDoubleOrNull() ?: return "Неправильне значення"

    val result: Double
    val resultText: String

    when (selectedConversion) {
        0 -> { // Кілометри → Милі
            result = input * 0.621371
            resultText = String.format("%.2f км = %.2f миль", input, result)
        }
        1 -> { // Милі → Кілометри
            result = input * 1.60934
            resultText = String.format("%.2f миль = %.2f км", input, result)
        }
        2 -> { // Кілограми → Фунти
            result = input * 2.20462
            resultText = String.format("%.2f кг = %.2f фунтів", input, result)
        }
        3 -> { // Фунти → Кілограми
            result = input * 0.453592
            resultText = String.format("%.2f фунтів = %.2f кг", input, result)
        }
        4 -> { // Цельсій → Фаренгейт
            result = (input * 9/5) + 32
            resultText = String.format("%.2f°C = %.2f°F", input, result)
        }
        5 -> { // Фаренгейт → Цельсій
            result = (input - 32) * 5/9
            resultText = String.format("%.2f°F = %.2f°C", input, result)
        }
        else -> resultText = "Помилка конвертації"
    }

    return resultText
}