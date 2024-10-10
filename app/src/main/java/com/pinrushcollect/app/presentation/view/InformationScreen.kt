package com.pinrushcollect.app.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ancient.flow.game.presentation.navigation.Screen
import com.ancient.flow.game.presentation.navigation.navigateSingleTop
import com.pinrushcollect.app.R


@Composable
fun InformationScreen(onNext: (Screen) -> Unit,navController: NavHostController) {
    BackHandler {
        navController.navigateSingleTop(Screen.LevelScreen.route)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF2A2B88), Color(0xFF51187E))))
            .paint(
                painter = painterResource(id = R.drawable.app_bg_main),
                contentScale = ContentScale.Crop
            )
//            .paint(
//                painter = painterResource(id = R.drawable.app_bg_information),
//                contentScale = ContentScale.Crop
//            )
    ) {
        Image(painter = painterResource(id = R.drawable.app_bg_info), contentDescription = null,Modifier.fillMaxSize(0.8f)
            .align(Alignment.TopCenter))
        // Back button at the bottom left
        Image(
            painter = painterResource(id = R.drawable.app_btn_back),
            contentDescription = "Back Button",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .size(48.dp)
                .clickable {
                    onNext(Screen.LevelScreen)
                }
        )
    }
}