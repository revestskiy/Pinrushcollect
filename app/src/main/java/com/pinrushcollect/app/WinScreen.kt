package com.pinrushcollect.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun WinScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.app_ic_light),
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
            Image(
                painter = painterResource(id = R.drawable.app_bg_balance),
                contentDescription = "Balance",
                modifier = Modifier.padding(top = 209.dp)
                    .size(112.dp, 40.dp)
            )

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
                        .clickable { /* TODO: Handle click */ }
                )
                // Next button
                Image(
                    painter = painterResource(id = R.drawable.game_btn_next),
                    contentDescription = "Next Button",
                    modifier = Modifier
                        .size(100.dp, 40.dp)
                        .clickable { /* TODO: Handle click */ }
                )
            }
        }
    }
}