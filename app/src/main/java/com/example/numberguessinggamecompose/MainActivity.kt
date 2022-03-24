package com.example.numberguessinggamecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numberguessinggamecompose.ui.theme.NumberGuessingGameComposeTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGame()
        }
    }
}

fun randomNumber(): Int {
    val r = Random()
    val num = r.nextInt(1000)
    return num
}

@Composable
fun NumberGuessingGame() {
    var points = remember { mutableStateOf(0) } // สร้างตัวแปรที่เก็บสถานะ ui ที่สามารถเปลี่ยนค่าได้
    var isCorrect = remember { mutableStateOf(true) }
    var life = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Number Guessing Game",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
        )
        Text(
            "Try to guess the number I'm thinking of from 1-1000!",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
        TextInput()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextButton(string = "submit", onClick = {})
            TextButton(string = "reset", onClick = {})
            TextButton(string = "surrender", onClick = {})
        }
        Text(
            text = "Points: 0",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
fun TextInput() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = {
            text = it
        },
        placeholder = {
            Text(
                text = "Enter your answer",
                textAlign = TextAlign.Center)
        },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number
        ),
        textStyle = TextStyle(textAlign = TextAlign.Center)
    )
}

@Composable
fun TextButton(string: String, onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = "$string", fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NumberGuessingGameComposeTheme {
        NumberGuessingGame()
    }
}