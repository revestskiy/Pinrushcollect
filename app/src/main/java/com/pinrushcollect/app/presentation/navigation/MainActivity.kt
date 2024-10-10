package com.pinrushcollect.app.presentation.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ancient.flow.game.presentation.navigation.Screen
import com.ancient.flow.game.presentation.navigation.navigatePopUpInclusive
import com.ancient.flow.game.presentation.navigation.navigateSingleTop
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.pinrushcollect.app.data.Prefs
import com.pinrushcollect.app.presentation.view.DailyBonusScreen
import com.pinrushcollect.app.presentation.view.DifficultySelectionScreen
import com.pinrushcollect.app.presentation.view.GameScreen
import com.pinrushcollect.app.presentation.view.InformationScreen
import com.pinrushcollect.app.presentation.view.LevelPurchaseScreen
import com.pinrushcollect.app.presentation.view.LoseScreen
import com.pinrushcollect.app.presentation.view.SettingsScreen
import com.pinrushcollect.app.presentation.view.WInfoScreen
import com.pinrushcollect.app.presentation.view.WelcomeBonusScreen
import com.pinrushcollect.app.presentation.view.WinScreen
import com.pinrushcollect.app.presentation.viewModel.DailyBonusManager
import com.pinrushcollect.app.ui.theme.PinrushcollectTheme
import com.pinrushcollect.app.ui.theme.myfont
import kotlinx.coroutines.delay
import java.net.URLDecoder
import java.net.URLEncoder

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    fillColor: Color = Color.Unspecified,
    outlineColor: Color,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    outlineDrawStyle: Stroke = Stroke(
        width = 20f,
    ),
) {
    Box(modifier = modifier) {
        Text(
            text = text,
            modifier = Modifier.semantics { invisibleToUser() },
            color = outlineColor,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = myfont,
            letterSpacing = letterSpacing,
            textDecoration = null,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style.copy(
                shadow = null,
                drawStyle = outlineDrawStyle,
            ),
        )

        Text(
            text = text,
            color = fillColor,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = myfont,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style,
        )
    }
}


class MainActivity : ComponentActivity() {
    private val remoteConfig by lazy {
        Firebase.remoteConfig
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Prefs.init(applicationContext)
        val bonusManager = DailyBonusManager(applicationContext)
        val settings =
            FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build()
        remoteConfig.setConfigSettingsAsync(settings)

        setContent {
            PinrushcollectTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.SplashScreen.route
                ) {
                    composable(Screen.SplashScreen.route) {
                        LoadingScreen(
                            { getUrlFromConfig(
                                launchPlaceholder = {
                                    navController.navigatePopUpInclusive(it)
                                },
                                setWebView = { link ->
                                    navController.navigateSingleTop(Screen.WebView.route.replace(
                                        "{url}",
                                        URLEncoder.encode(link, "UTF-8")
                                    ))
                                }
                            )
                            },
                            bonusManager
                        )
                    }

                    composable(Screen.WebView.route) {
                        val url = it.arguments?.getString("url")?.let {
                            URLDecoder.decode(it, "UTF-8")
                        }

                        WInfoScreen(url!!)
                    }


                    composable(route = Screen.WelcomeBonusScreen.route) {
                        WelcomeBonusScreen(navController::navigatePopUpInclusive, bonusManager)

                    }
                    composable(route = Screen.DailyBonusScreen.route) {
                        DailyBonusScreen(
                            onBack = navController::navigatePopUpInclusive,
                            bonusManager
                        )
                    }
                    composable(route = Screen.SettingsScreen.route) {
                        SettingsScreen(navController::navigatePopUpInclusive, navController )
                    }
                    composable(route = Screen.InfoScreen.route) {
                        InformationScreen(navController::navigatePopUpInclusive,navController)
                    }

                    composable(route = Screen.LevelScreen.route) {
                        DifficultySelectionScreen(
                            navController::navigatePopUpInclusive,
                            onLevelClick = { level ->
                                navController.navigateSingleTop(
                                    route = "${Screen.GameScreen.route}/$level"
                                )
                            },
                            navController
                        )
                    }
                    composable(
                        route = "${Screen.GameScreen.route}/{level}",
                        arguments = listOf(navArgument("level") { type = NavType.StringType })
                    ) { backStackEntry ->
                        // Retrieve the level from the arguments
                        val level = backStackEntry.arguments?.getString("level")
                            ?: "Medium" // Default to "Medium" if not provided

                        // Pass the level to GameScreen
                        GameScreen(level, navController)
                    }
                    composable(
                        route = "${Screen.PurchaseScreen.route}/{levelKey}/{painterResourceId}/{levelCost}",
                        arguments = listOf(
                            navArgument("levelKey") {
                                type = NavType.StringType
                            },         // Level key (e.g., "level_hard")
                            navArgument("painterResourceId") {
                                type = NavType.IntType
                            },   // Image resource ID (e.g., 2130968589)
                            navArgument("levelCost") {
                                type = NavType.IntType
                            }            // Cost of the level (e.g., 2000)
                        )
                    ) { backStackEntry ->
                        val levelKey = backStackEntry.arguments?.getString("levelKey") ?: ""
                        val painterResourceId =
                            backStackEntry.arguments?.getInt("painterResourceId") ?: 0
                        val levelCost = backStackEntry.arguments?.getInt("levelCost") ?: 0

                        LevelPurchaseScreen(
                            painterResourceId = painterResourceId,
                            levelCost = levelCost,
                            userCoins = Prefs.coin,
                            onBuyClick = {
                                Prefs.unlockLevel(levelKey)
                                Prefs.coin -= levelCost
                                navController.navigatePopUpInclusive(Screen.LevelScreen)


                            },
                            navController
                        )
                    }
                    // Win Screen route
                    composable(
                        route = "${Screen.WinScreen.route}/{difficulty}",
                        arguments = listOf(navArgument("difficulty") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val difficulty = backStackEntry.arguments?.getString("difficulty") ?: ""
                        WinScreen(difficulty, navController)
                    }

// Lose Screen route
                    composable(
                        route = "${Screen.LoseScreen.route}/{difficulty}",
                        arguments = listOf(navArgument("difficulty") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val difficulty = backStackEntry.arguments?.getString("difficulty") ?: ""
                        LoseScreen(onRestart = {
                            // Navigate back to the GameScreen with the same difficulty to restart the level
                            navController.navigateSingleTop("${Screen.GameScreen.route}/$difficulty")
                        }, navController)
                    }


                }
            }
        }
    }
    private fun getUrlFromConfig(
        launchPlaceholder: () -> Unit,
        setWebView: (String) -> Unit
    ) {
        try {
            remoteConfig.fetchAndActivate().addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val url = remoteConfig.getString("pinrush")
                    if (url.isBlank()) {
                        launchPlaceholder()
                    }
                    else {
                        setWebView(url)
                    }
                }
                else {
                    launchPlaceholder()
                }
            }
        }
        catch (e: Exception) {
            launchPlaceholder()
        }
    }
}

@Composable
fun LoadingScreen(
    onNext: (Screen) -> Unit, bonusManager: DailyBonusManager
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNext(if (bonusManager.isBonusClaimedToday()) Screen.LevelScreen else Screen.WelcomeBonusScreen)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFD8BFD8), // Светлофиолетовый
                        Color(0xFF4B0082)  // Тёмнофиолетовый
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "LOADING...",
                color = Color.White,
                fontSize = 36.sp,
                fontFamily = myfont,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 8f
                    )
                )
            )
        }
    }
}