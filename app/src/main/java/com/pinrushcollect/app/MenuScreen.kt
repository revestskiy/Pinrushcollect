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
fun DifficultySelectionScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(painter = painterResource(id = R.drawable.app_bg_main), contentScale = ContentScale.Crop)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Background image at the top
            Image(
                painter = painterResource(id = R.drawable.app_bg_choose),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 72.dp)
                    .size(300.dp, 50.dp),
                contentScale = ContentScale.Fit
            )

            // Difficulty buttons grid
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.app_btn_easy),
                        contentDescription = "Easy Button",
                        modifier = Modifier
                            .weight(1f)
                            .height(170.dp)
                            .padding(16.dp)
                            .clickable {

                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.app_btn_medium),
                        contentDescription = "Medium Button",
                        modifier = Modifier
                            .weight(1f)
                            .height(170.dp)
                            .padding(16.dp)
                            .clickable {

                            }
                    )
                }
                Row(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.app_btn_hard),
                        contentDescription = "Hard Button",
                        modifier = Modifier
                            .weight(1f)
                            .height(170.dp)
                            .padding(16.dp)
                            .clickable {

                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.app_btn_extreme),
                        contentDescription = "Extreme Button",
                        modifier = Modifier
                            .weight(1f)
                            .height(170.dp)
                            .padding(16.dp)
                            .clickable {

                            }
                    )
                }
            }

            // Daily bonus button
            Image(
                painter = painterResource(id = R.drawable.app_btn_bonus),
                contentDescription = "Daily Bonus",
                modifier = Modifier
                    .padding(top = 72.dp)
                    .size(360.dp, 130.dp)
            )

            // Bottom row with info, balance, and settings buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_btn_info),
                    contentDescription = "Info Button",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {

                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.app_bg_balance),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .size(120.dp, 50.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.app_btn_settings),
                    contentDescription = "Settings Button",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {

                        }
                )
            }
        }
    }
}