package com.pinrushcollect.app.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
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
fun WinScreen(difficulty: String,navController: NavHostController) {

    LaunchedEffect(Unit) {

        Prefs.coin += when (difficulty) {
            "level_easy" -> 50
            "level_medium" -> 100
            "level_hard" -> 200
            "level_extreme" -> 400
            else -> 0
        }

    }
    var coinString by remember { mutableStateOf("") }
    coinString = when (difficulty) {
        "level_easy" -> 50.toString()
        "level_medium" -> 100.toString()
        "level_hard" -> 200.toString()
        "level_extreme" -> 400.toString()
        else -> 0.toString()
    }

    BackHandler {
        navController.navigateSingleTop(Screen.LevelScreen.route)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.app_bg_main),
                contentScale = ContentScale.Crop
            )
    ) {
        // Win background at the center
        Image(
            painter = painterResource(id = R.drawable.game_bg_win),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(350.dp, 320.dp)
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Balance image

            Box(
                modifier = Modifier
                    .padding(top = 209.dp)
                    .size(112.dp, 40.dp),
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
                    color = Color.White
                )
            }

            // Row for Home and Next buttons
            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home button
                Image(
                    painter = painterResource(id = R.drawable.app_btn_home),
                    contentDescription = "Home Button",
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .size(40.dp)
                        .clickable { navController.navigateSingleTop(Screen.LevelScreen.route)}
                )
                // Next button
                Image(
                    painter = painterResource(id = R.drawable.game_btn_next),
                    contentDescription = "Next Button",
                    modifier = Modifier
                        .size(100.dp, 40.dp)
                        .clickable { navController.navigateSingleTop(Screen.LevelScreen.route)}
                )
            }
        }
    }
}