package com.pinrushcollect.app.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ancient.flow.game.presentation.navigation.Screen
import com.ancient.flow.game.presentation.navigation.navigateSingleTop
import com.pinrushcollect.app.R
import com.pinrushcollect.app.data.Prefs

@Composable
fun DifficultySelectionScreen(
    onNext: (Screen) -> Unit,
    onLevelClick: (String) -> Unit,
    navController: NavHostController
) {

    val context = LocalContext.current
    var userCoins by remember { mutableStateOf(Prefs.coin) }
    var coinString = Prefs.coin.toString()
    LaunchedEffect(Prefs.coin) {
        coinString = Prefs.coin.toString()
        userCoins = Prefs.coin

    }

    var isPrefsInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Prefs.init(context)
        isPrefsInitialized = true
    }
    if (isPrefsInitialized) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.app_bg_main),
                    contentScale = ContentScale.Crop
                )
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
                        .padding(top = 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val levels = listOf(
                        LevelData(
                            painterResourceId = R.drawable.app_btn_easy,
                            levelKey = Prefs.LEVEL_EASY,
                            contentDescription = "Easy Button",
                            levelCost = 0, // Easy level всегда разблокирован
                            isLocked = false
                        ),
                        LevelData(
                            painterResourceId = R.drawable.app_btn_medium,
                            levelKey = Prefs.LEVEL_MEDIUM,
                            contentDescription = "Medium Button",
                            levelCost = 1050, // Стоимость среднего уровня
                            isLocked = !Prefs.isLevelUnlocked(Prefs.LEVEL_MEDIUM)
                        ),
                        LevelData(
                            painterResourceId = R.drawable.app_btn_hard,
                            levelKey = Prefs.LEVEL_HARD,
                            contentDescription = "Hard Button",
                            levelCost = 2000, // Стоимость сложного уровня
                            isLocked = !Prefs.isLevelUnlocked(Prefs.LEVEL_HARD)
                        ),
                        LevelData(
                            painterResourceId = R.drawable.app_btn_extreme,
                            levelKey = Prefs.LEVEL_EXTREME,
                            contentDescription = "Extreme Button",
                            levelCost = 3000, // Стоимость экстремального уровня
                            isLocked = !Prefs.isLevelUnlocked(Prefs.LEVEL_EXTREME)
                        )
                    )

                    levels.chunked(2).forEach { rowLevels ->
                        Row(modifier = Modifier.padding(8.dp)) {
                            rowLevels.forEach { levelData ->
                                DifficultyLevelButton(
                                    painter = painterResource(id = levelData.painterResourceId),
                                    isLocked = levelData.isLocked,
                                    contentDescription = levelData.contentDescription,
                                    levelCost = levelData.levelCost,
                                    userCoins = userCoins
                                ) {
                                    if (levelData.isLocked) {
                                        // Navigate to the purchase screen if the level is locked
                                        navController.navigateSingleTop(
                                            route = "${Screen.PurchaseScreen.route}/${levelData.levelKey}/${levelData.painterResourceId}/${levelData.levelCost}"
                                        )


                                    } else {
                                        // Otherwise, open the level directly
                                        onLevelClick(levelData.levelKey)
                                    }
                                }
//                                DifficultyLevelButton(
//                                    painter = painterResource(id = levelData.painterResourceId),
//                                    isLocked = levelData.isLocked,
//                                    contentDescription = levelData.contentDescription,
//                                    levelCost = levelData.levelCost,
//                                    userCoins = userCoins
//                                ) {
//                                    if (levelData.isLocked) {
//                                        if (userCoins >= levelData.levelCost) {
//                                            // Разблокировать уровень, если монет хватает
//                                            Prefs.unlockLevel(levelData.levelKey)
//                                            Prefs.coin -= levelData.levelCost
//                                        } else {
//
//                                            // Сообщить, что не хватает монет
//                                            println("Недостаточно монет для покупки уровня")
//                                        }
//                                    } else {
//                                        // Открыть уровень
//                                        onLevelClick(levelData.levelKey)
//                                    }
//                                }
                            }
                        }
                    }
                }

                // Daily bonus button
                Image(
                    painter = painterResource(id = R.drawable.app_btn_bonus),
                    contentDescription = "Daily Bonus",
                    modifier = Modifier
                        .padding(top = 72.dp)
                        .size(360.dp, 130.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {

                            onNext(Screen.DailyBonusScreen)
                        }
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
                                onNext(Screen.InfoScreen)
                            }
                    )
                    Box(
                        modifier = Modifier

                            .size(140.dp, 50.dp),
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


                    Image(
                        painter = painterResource(id = R.drawable.app_btn_settings),
                        contentDescription = "Settings Button",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                onNext(Screen.SettingsScreen)
                            }
                    )
                }
            }

        }
    }
}

// Вспомогательный класс для описания данных уровня
data class LevelData(
    val painterResourceId: Int,
    val levelKey: String,
    val contentDescription: String,
    val levelCost: Int, // Добавлена стоимость уровня
    val isLocked: Boolean
)

@Composable
fun DifficultyLevelButton(
    painter: Painter,
    isLocked: Boolean,
    contentDescription: String,
    modifier: Modifier = Modifier,
    levelCost: Int, // Стоимость уровня
    userCoins: Int, // Количество монет у пользователя
    onClick: () -> Unit // Коллбэк на покупку или открытие уровня
) {
    Box(
        modifier = modifier
            .size(165.dp)
            .padding(16.dp)
            .clickable {
                onClick() // Вызов обработчика при нажатии
            },
        contentAlignment = Alignment.Center
    ) {
        // Основная кнопка уровня
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Наложение изображения замка, если уровень заблокирован
        if (isLocked) {
            Image(
                painter = painterResource(id = R.drawable.app_ic_lock), // Замок
                contentDescription = "Locked",
                modifier = Modifier
                    .size(165.dp)
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}





