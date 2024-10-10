package com.pinrushcollect.app.presentation.view

import android.content.Context
import android.content.SharedPreferences
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ancient.flow.game.presentation.navigation.Screen
import com.pinrushcollect.app.R
import com.pinrushcollect.app.data.Prefs
import com.pinrushcollect.app.presentation.navigation.OutlinedText
import com.pinrushcollect.app.presentation.viewModel.DailyBonusManager
import kotlinx.coroutines.delay


@Composable
fun DailyBonusItem(day: Int, isClaimed: Boolean, canClaim: Boolean, onClaim: () -> Unit) {
    // Выбор соответствующих изображений для каждого дня в зависимости от состояния
    val imageResource = when {
        isClaimed -> when (day) {
            1 -> R.drawable.day1_collected_image
            2 -> R.drawable.day2_collected_image
            3 -> R.drawable.day3_collected_image
            4 -> R.drawable.day4_collected_image
            5 -> R.drawable.day5_collected_image
            6 -> R.drawable.day6_collected_image
            7 -> R.drawable.day7_available_image
            else -> R.drawable.app_ic_pig // На всякий случай дефолтное значение
        }

        canClaim -> when (day) {
            1 -> R.drawable.day1_available_image
            2 -> R.drawable.day2_available_image
            3 -> R.drawable.day3_available_image
            4 -> R.drawable.day4_available_image
            5 -> R.drawable.day5_available_image
            6 -> R.drawable.day6_available_image
            7 -> R.drawable.day7_available_image
            else -> R.drawable.app_ic_pig // На всякий случай дефолтное значение
        }

        else -> when (day) {
            2 -> R.drawable.day2_locked_image
            3 -> R.drawable.day3_locked_image
            4 -> R.drawable.day4_locked_image
            5 -> R.drawable.day5_locked_image
            6 -> R.drawable.day6_locked_image
            7 -> R.drawable.day7_locked_image
            else -> R.drawable.app_ic_pig // На всякий случай дефолтное значение
        }
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .clickable(enabled = canClaim && !isClaimed) {
                onClaim()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Bonus Day $day",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun WelcomeBonusScreen(onNext: (Screen) -> Unit,bonusManager: DailyBonusManager) {
    val context = LocalContext.current
    val dailyBonusManager = remember { DailyBonusManager(context) }

    var claimedDays by remember { mutableStateOf(dailyBonusManager.getClaimedDays()) }
    var canClaimDay by remember { mutableStateOf((claimedDays.size + 1).coerceAtMost(7)) } // Например, доступен 3-й день
    var lastBonusTime by remember { mutableStateOf(dailyBonusManager.getLastBonusTime()) }
    var isBonusClaimedToday by remember { mutableStateOf(dailyBonusManager.isBonusClaimedToday()) }
    var isBonusClaimed by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (dailyBonusManager.canClaimBonus()) {
            canClaimDay =
                (claimedDays.size + 1).coerceAtMost(7) // Определяем следующий доступный день

        }
    }
    BackHandler {

    }
    LaunchedEffect(isBonusClaimed) {
        if (isBonusClaimed) {
            delay(2000L)
            onNext(Screen.LevelScreen)
        }
    }
    val coin =when(canClaimDay){
        1->50
        2->100
        3->150
        4->200
        5->275
        6->350
        7->500
        else->0
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF2A2B88), Color(0xFF51187E))))
            .paint(
                painter = painterResource(id = R.drawable.app_bg_main),
                contentScale = ContentScale.Crop
            )
            .padding(top = 0.dp, start = 20.dp, end = 20.dp),
        contentAlignment = Alignment.Center
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.7f)
                .paint(
                    painter = painterResource(id = R.drawable.app_bg_bonus),
                    contentScale = ContentScale.FillWidth
                )
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            for (row in 0 until 3) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (col in 0 until if (row == 2) 1 else 3) {
                        val day = row * 3 + col + 1
                        if (row != 2) {
                            DailyBonusItem(
                                day = day,
                                isClaimed = claimedDays.contains(day),
                                canClaim = day == canClaimDay && !isBonusClaimedToday,
                                onClaim = {


                                }
                            )
                        } else {
                            DailyBonusItem(
                                day = day,
                                isClaimed = claimedDays.contains(day),
                                canClaim = day == canClaimDay && !isBonusClaimedToday,
                                onClaim = {


                                }
                            )
                            Column(
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(100.dp)
                                    .padding(top = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween

                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    OutlinedText(
                                        text = "LOG IN EVERY",
                                        outlineColor = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    OutlinedText(
                                        text = "DAY TO GET",
                                        outlineColor = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    OutlinedText(
                                        text = "MORE COINS",
                                        outlineColor = Color.White,
                                        fontSize = 14.sp
                                    )
                                }

                            }

                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(
                                        Color.Green,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clickable {
                                        if (!bonusManager.isBonusClaimedToday()) {
                                            Prefs.coin += coin
                                            claimedDays = claimedDays + canClaimDay
                                            lastBonusTime = System.currentTimeMillis()
                                            dailyBonusManager.saveClaimedDays(claimedDays)
                                            dailyBonusManager.saveLastBonusTime(lastBonusTime)
                                            isBonusClaimedToday = true // Обновляем состояние
                                            isBonusClaimed = true

                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                OutlinedText(
                                    text = "COLLECT",
                                    fontSize = 20.sp,
                                    outlineColor = Color.White


                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }


        }
    }
}






