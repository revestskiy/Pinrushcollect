package com.pinrushcollect.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun InformationScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.app_bg_information),
                contentScale = ContentScale.Crop
            )
    ) {
        // Back button at the bottom left
        Image(
            painter = painterResource(id = R.drawable.app_btn_back),
            contentDescription = "Back Button",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .size(48.dp)
                .clickable {

                }
        )
    }
}