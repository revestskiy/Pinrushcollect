package com.pinrushcollect.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background at the bottom
        Image(
            painter = painterResource(id = R.drawable.app_bg_down),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )

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
                    .clickable { /* TODO: Handle click */ }
            )
            // Private Policy button
            Image(
                painter = painterResource(id = R.drawable.app_btn_private),
                contentDescription = "Private Policy Button",
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(300.dp, 40.dp)
                    .clickable { /* TODO: Handle click */ }
            )
        }

        // Back button at the bottom left
        Image(
            painter = painterResource(id = R.drawable.app_btn_back),
            contentDescription = "Back Button",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .size(48.dp)
                .clickable { /* TODO: Handle click */ }
        )
    }
}