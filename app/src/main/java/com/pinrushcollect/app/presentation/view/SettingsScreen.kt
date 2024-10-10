package com.pinrushcollect.app.presentation.view

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ancient.flow.game.presentation.navigation.Screen
import com.pinrushcollect.app.R


@Composable
fun SettingsScreen(onNext: (Screen) -> Unit,navController: NavHostController) {
    val context = LocalContext.current
    BackHandler {

    }
    Box(
        modifier = Modifier
            .fillMaxSize()

            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF2A2B88), Color(0xFF51187E))))
            .paint(
                painter = painterResource(id = R.drawable.app_bg_main),
                contentScale = ContentScale.Crop
            )
    ) {
        // Background at the bottom


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Share App button
            Image(
                painter = painterResource(id = R.drawable.app_btn_share_app),
                contentDescription = "Share App Button",
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(300.dp, 40.dp)
                    .clickable { /* TODO: Handle click */ }
            )
            // Contact Us button
            Image(
                painter = painterResource(id = R.drawable.app_btn_contact),
                contentDescription = "Contact Us Button",
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(300.dp, 40.dp)
                    .clickable {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:developer@pinoinvest-apps.com") // Email адрес
                        }
                        context.startActivity(
                            Intent.createChooser(
                                emailIntent,
                                "Отправить письмо через"
                            )
                        )
                    }
            )
            // Private Policy button
            Image(
                painter = painterResource(id = R.drawable.app_btn_private),
                contentDescription = "Private Policy Button",
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(300.dp, 40.dp)
                    .clickable {
                        val url =
                            "https://docs.google.com/document/d/143C77GFCBvpFsd9NPbi-IuzyOvn3s97K9tpJHiX7OQc/edit" // Ваша ссылка
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
            )
        }

        // Back button at the bottom left
        Image(
            painter = painterResource(id = R.drawable.app_btn_back),
            contentDescription = "Back Button",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 32.dp, bottom = 32.dp)
                .size(48.dp)
                .clickable { onNext(Screen.LevelScreen) }
        )
    }
}


