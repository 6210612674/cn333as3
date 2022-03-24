package com.example.numberguessinggamecompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.numberguessinggamecompose.ui.theme.NumberGuessingGameComposeTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NumberGuessingGame(this)
                }
            }
        }
    }
}

fun randomNumber(): Int {
    val r = Random()
    val num = r.nextInt(1000)
    return num
}

@Composable
fun NumberGuessingGame(context: Context) {
    var r by rememberSaveable { mutableStateOf(randomNumber()) }
    var life = remember { mutableStateOf(0) }
    var input by remember { mutableStateOf("") }
    var text by rememberSaveable { mutableStateOf("") }
    var ans by rememberSaveable { mutableStateOf(false) }

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
        TextField(
            value = input,
            onValueChange = {
                input = it
            },
            label = { Text("Enter your answer") },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Number
            ),
            textStyle = TextStyle(textAlign = TextAlign.Center),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextButton(
                string = "submit",
                onClick = {
                    if (!input.isDigitsOnly()) {
                        Toast.makeText(context,"Please enter number", Toast.LENGTH_LONG).show();
                    } else {
                        when {
                            input.toInt() == r -> {
                                ans = true
                                text = "Congratulations!"
                                Toast.makeText(context,"Correct!", Toast.LENGTH_LONG).show()
                            }
                            input.toInt() > r -> {
                                text = "Hint: It's higher!"
                                Toast.makeText(context,"Wrong!", Toast.LENGTH_LONG).show();
                                life.value--
                            }
                            input.toInt() < r -> {
                                text = "Hint: It's lower!"
                                Toast.makeText(context,"Wrong!", Toast.LENGTH_LONG).show();
                                life.value--
                            }
                        }
                    }
                })
            TextButton(string = "reset", onClick = {})
            TextButton(string = "surrender", onClick = {})
        }
        if (ans) {
            Text(
                text = text,
                fontSize = 16.sp,
            )
        } else {
            Text(
                text = text,
                fontSize = 16.sp,
            )
        }
        if (life.value == 0) {
            Text(
                text = "Game Over",
                fontSize = 16.sp,
            )
            Text(
                text = "The Correct Answer is $r",
                fontSize = 16.sp,
            )
        }
        Text(
            text = "Life: $life",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
fun TextButton(string: String, onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = "$string", fontSize = 14.sp)
    }
}