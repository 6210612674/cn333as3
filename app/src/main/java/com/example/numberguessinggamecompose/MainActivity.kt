package com.example.numberguessinggamecompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
import kotlin.random.Random.Default.nextInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    NumberGuessingGame(this)
                }
            }
        }
    }
}

fun randomNumber(): Int {
    val num = nextInt(1, 1000)
    return num
}

@Composable
fun NumberGuessingGame(context: Context) {
    var r = remember { mutableStateOf(randomNumber()) }
    var life = remember { mutableStateOf(20) }
    var input by remember { mutableStateOf("") }
    var text by rememberSaveable { mutableStateOf("") }
    var ans by rememberSaveable { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(Color.Blue, Color.Red)
                ),
                alpha = 0.25F
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Number Guessing Game",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(
                    brush = SolidColor(Color.Yellow),
                    shape = RoundedCornerShape(24.dp),
                    alpha = 0.5F
                ),
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
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            TextButton(
                string = "submit",
                onClick = {
                    if (!input.isDigitsOnly()) {
                        Toast.makeText(context,"Please enter number", Toast.LENGTH_LONG).show();
                    } else {
                        when {
                            input.toInt() == r.value -> {
                                ans = "win"
                                text = "Congratulations! Your answer is correct."
                                Toast.makeText(context,"Correct!", Toast.LENGTH_LONG).show()
                                r.value = nextInt(1, 1000)
                                life.value = 20
                                input = ""
                            }
                            input.toInt() > r.value -> {
                                ans = "wrong"
                                text = "Hint: It's lower!"
                                Toast.makeText(context,"Wrong!", Toast.LENGTH_LONG).show();
                                life.value--
                            }
                            input.toInt() < r.value -> {
                                ans = "wrong"
                                text = "Hint: It's higher!"
                                Toast.makeText(context,"Wrong!", Toast.LENGTH_LONG).show();
                                life.value--
                            }
                        }
                    }
                    if (life.value == 0) {
                        ans = "over"
                        r.value = nextInt(1, 1000)
                        life.value = 20
                        input = ""
                    }
                })
            TextButton(
                string = "reset",
                onClick = {
                    ans = "reset"
                    r.value = nextInt(1, 1000)
                    life.value = 20
                    input = ""
                })
            TextButton(
                string = "surrender",
                onClick = {
                    ans = "sur"
                    r.value = nextInt(1, 1000)
                    life.value = 20
                    input = ""
                })
        }
        if (ans == "win") {
            Text(
                text = text,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        } else if (ans == "wrong") {
            Text(
                text = text,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        } else if (ans == "sur") {
            Text(
                text = "You Lose, Reset to restart.",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Text(
                text = "The Correct Answer is ${r.value}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        } else if (ans == "over") {
            Text(
                text = "Game Over",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Text(
                text = "The Correct Answer is ${r.value}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        Text(
            text = "Life: ${life.value}",
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
        modifier = Modifier
            .padding(all=0.dp),
        colors= ButtonDefaults.buttonColors(backgroundColor = Color.White),
        onClick = onClick
    ) {
        Text(text = "$string", fontSize = 14.sp)
    }
}