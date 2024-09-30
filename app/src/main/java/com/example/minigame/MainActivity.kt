package com.example.minigame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minigame.ui.theme.MiniGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniGameTheme {
                Scaffold(
                    content = { paddingValues ->
                        Greeting(modifier = Modifier.padding(paddingValues))
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var isInitialButtonVisible by remember { mutableStateOf(true) }
    var areAdditionalButtonsVisible by remember { mutableStateOf(false) }

    // Позиции машинок
    var redCarPosition by remember { mutableStateOf(140f) }
    var orangeCarPosition by remember { mutableStateOf(140f) }

    // Определение победы
    var gameEnded by remember { mutableStateOf(false) }
    var winnerText by remember { mutableStateOf("") }

    // Обработчик для перезапуска игры
    fun resetGame() {
        redCarPosition = 140f
        orangeCarPosition = 140f
        gameEnded = false
        winnerText = ""
        isInitialButtonVisible = true
        areAdditionalButtonsVisible = false
    }

    Image(
        painter = painterResource(id = R.drawable.road),
        contentDescription = "road",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
                rotationZ = 90f,
                scaleY = 2f
            ),
        contentScale = ContentScale.Fit
    )

    Image(
        painter = painterResource(id = R.drawable.red_car),
        contentDescription = "red_car",
        modifier = Modifier
            .size(130.dp)
            .graphicsLayer(
                translationX = redCarPosition,
                translationY = 550f
            )
    )

    Image(
        painter = painterResource(id = R.drawable.orange_car),
        contentDescription = "orange_car",
        modifier = Modifier
            .size(130.dp)
            .graphicsLayer(
                translationX = orangeCarPosition,
                translationY = 120f
            )
    )

    Image(
        painter = painterResource(id = R.drawable.finish),
        contentDescription = "finish",
        modifier = Modifier
            .size(500.dp)
            .graphicsLayer(
                translationX = 1500f,
                translationY = 0f
            )
    )

    if (isInitialButtonVisible) {
        Button(
            onClick = {
                isInitialButtonVisible = false
                areAdditionalButtonsVisible = true
            },
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .padding(16.dp)
                .graphicsLayer(
                    translationY = 850f,
                    translationX = 1000f,
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00FF4D),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF007C08))
        ) {
            Text(
                text = "Start",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }

    if (areAdditionalButtonsVisible && !gameEnded) {

        // Кнопка для движения красной машинки
        Button(
            onClick = {
                if (redCarPosition < 1800f) {
                    redCarPosition += 20f
                }
                if (redCarPosition >= 1800f) {
                    gameEnded = true
                    winnerText = "Red Car Wins!"
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .padding(20.dp)
                .graphicsLayer(
                    translationY = 850f,
                    translationX = 140f,
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF0000),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, Color(0xFF732500)),
            enabled = !gameEnded
        ) {
            Text("TAP")
        }

        // Кнопка для движения оранжевой машинки
        Button(
            onClick = {
                if (orangeCarPosition < 1800f) {
                    orangeCarPosition += 20f
                }
                if (orangeCarPosition >= 1800f) {
                    gameEnded = true
                    winnerText = "Orange Car Wins!"
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .padding(20.dp)
                .graphicsLayer(
                    translationY = 0f,
                    translationX = 140f,
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFE6014),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, Color(0xFF630000)),
            enabled = !gameEnded
        ) {
            Text("TAP")
        }
    }

    // Отображение экрана завершения игры
    if (gameEnded) {
        GameEndScreen(winnerText) {
            resetGame()
        }
    }
}

@Composable
fun GameEndScreen(winnerText: String, onRestart: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = winnerText,
                color = Color.White,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRestart,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00FF4D),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Restart Game")
            }
        }
    }
}
