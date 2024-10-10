package com.ancient.flow.game.presentation.navigation


sealed class Screen(
    val screenRoute: String,
) {
    open val route: String = screenRoute

    data object SplashScreen : Screen("splash_screen")
    data object MainMenuScreen : Screen("main_menu_screen")
    data object GameScreen : Screen("game_screen") {
//        override val route = "$screenRoute/{level}"
//        fun getLevel(navBackStackEntry: NavBackStackEntry): Level {
//            return navBackStackEntry.arguments?.getString("level")
//                ?.let { Level.valueOf(it) } ?: Level.LEVEL_1
//        }
    }

    data object SettingsScreen : Screen("settings_screen")
    data object LevelScreen : Screen("level_screen")
    data object GameEndScreen : Screen("game_end_screen/{level}/{isVictory}")
    data object DailyBonusScreen : Screen("daily_bonus_screen")
    data object WelcomeBonusScreen : Screen("welcome_bonus_screen")
    data object InfoScreen : Screen("info_screen")
    data object PurchaseScreen : Screen("purchase_screen") {
        fun createRoute(levelKey: String, painterResourceId: Int, levelCost: Int): String {
            return "$route/$levelKey/$painterResourceId/$levelCost"
        }
    }
    data object WinScreen : Screen("win_screen/{level}")
    data object LoseScreen : Screen("lose_screen/{level}")
    data object WebView: Screen("web_view/{url}")


}