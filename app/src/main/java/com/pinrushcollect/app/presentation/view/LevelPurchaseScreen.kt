package com.pinrushcollect.app.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ancient.flow.game.presentation.navigation.Screen
import com.ancient.flow.game.presentation.navigation.navigateSingleTop
import com.pinrushcollect.app.R
import com.pinrushcollect.app.data.Prefs

@Composable
fun LevelPurchaseScreen(
    painterResourceId: Int,
    levelCost: Int,
    userCoins: Int,
    onBuyClick: () -> Unit,
    navController: NavHostController
) {
    // Load the level image based on the painterResourceId passed
    val levelImage: Painter = painterResource(id = painterResourceId)
    var coinString by remember { mutableStateOf(Prefs.coin.toString()) }
    // Check if the user has enough coins
    val hasEnoughCoins = userCoins >= levelCost
    BackHandler {
        navController.navigateSingleTop(Screen.LevelScreen.route)
    }
    LaunchedEffect(Prefs.coin) {
        coinString = Prefs.coin.toString()

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.app_bg_main),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)  // Adjust size if needed
                    .padding(start = 0.dp, bottom = 0.dp),

                contentAlignment = Alignment.Center
            ) {
                // Background image (placed first to be the bottom layer)
                Image(
                    painter = levelImage, // Background image
                    contentDescription = "Background Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds // Ensure the background image fills the Box
                )

                // Piggy Bank image (placed on top of the background)
                Image(
                    painter = painterResource(id = R.drawable.app_ic_pig),
                    contentDescription = "Piggy Bank Image",
                    modifier = Modifier
                        .size(150.dp)
                        .offset(0.dp, -100.dp),  // Offset the piggy bank image to position it as needed
                    contentScale = ContentScale.Fit
                )

                // Box for displaying the coin balance at the bottom
                Box(
                    modifier = Modifier
                        .padding(start = 0.dp, bottom = 10.dp)
                        .size(140.dp, 50.dp)
                        .align(Alignment.BottomCenter), // Align balance box at the bottom
                    contentAlignment = Alignment.Center
                ) {
                    // Background image for the balance section
                    Image(
                        painter = painterResource(id = R.drawable.app_bg_balance), // Another background image for balance
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Text displaying the coin count
                    Text(
                        text = levelCost.toString(), // Coin count
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

// Buy button placed outside the Box
            TextButton(
                onClick = onBuyClick,
                enabled = hasEnoughCoins, // Button enabled only if user has enough coins
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (hasEnoughCoins) Color.Green else Color.Gray // Button color depending on coin availability
                ),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text(text = if (hasEnoughCoins) "Buy" else "Not enough coins", color = Color.White)
            }





        }
        Image(
            painter = painterResource(id = R.drawable.app_btn_back),
            contentDescription = "Back Button",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 32.dp)
                .size(48.dp)
                .clickable { navController.navigateSingleTop(Screen.LevelScreen.route) }
        )
        Box(
            modifier = Modifier
                .padding(start = 0.dp, bottom = 32.dp)
                .size(140.dp, 50.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            // Фоновое изображение
            Image(
                painter = painterResource(id = R.drawable.app_bg_balance),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Текст с количеством монет
            Text(
                text = coinString,
                fontSize = 20.sp,
                color = Color.White,

                )
        }
    }
}
