package com.pinrushcollect.app.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ancient.flow.game.presentation.navigation.Screen
import com.ancient.flow.game.presentation.navigation.navigateSingleTop
import com.pinrushcollect.app.R
import kotlinx.coroutines.delay
import kotlin.random.Random


@Composable
fun GameScreen(difficulty: String, navController: NavHostController) {
    // Determine grid size, max sum, and time based on difficulty level
    val (gridSize, maxSum, time) = when (difficulty) {
        "level_easy" -> Triple(2, 5, 60)
        "level_medium" -> Triple(3, 10, 120)
        "level_hard" -> Triple(4, 15, 180)
        "level_extreme" -> Triple(5, 20, 240)
        else -> Triple(3, 10, 90)
    }

    var grid by remember { mutableStateOf(generateGrid(gridSize)) }
    var rowSums by remember { mutableStateOf(generateSolvableSums(gridSize, maxSum)) }
    var columnSums by remember { mutableStateOf(generateSolvableSums(gridSize, maxSum)) }
    var isGameOver by remember { mutableStateOf(false) }
    var isSolvable by remember { mutableStateOf(checkSolvability(rowSums, columnSums)) }
    var countdown by remember { mutableStateOf(time) }
    var isSettings by remember { mutableStateOf(false) }
    val minutes = countdown / 60
    val seconds = countdown % 60
    val countdownFormatted = String.format("%02d:%02d", minutes, seconds)

    BackHandler {
        navController.navigateSingleTop(Screen.LevelScreen.route)
    }

    // Timer effect
    LaunchedEffect(countdown) {
        while (countdown > 0 && !isGameOver) {
            delay(1000L)
            countdown--
            if (countdown == 0) {
                isGameOver = true
                val playerWins = checkGameEnd(grid, rowSums, columnSums)
                if (playerWins) {
                    // Navigate to the win screen
                    navController.navigateSingleTop("${Screen.WinScreen.route}/$difficulty")
                } else {
                    // Navigate to the lose screen
                    navController.navigateSingleTop("${Screen.LoseScreen.route}/$difficulty")
                }
            }
        }

    }

    // Ensure the level is solvable by regenerating sums if not solvable
    LaunchedEffect(rowSums, columnSums) {
        while (!isSolvable) {
            rowSums = generateSolvableSums(gridSize, maxSum)
            columnSums = generateSolvableSums(gridSize, maxSum)
            isSolvable = checkSolvability(rowSums, columnSums)
        }
    }
    if (isSettings) {
        BackHandler { isSettings = false }
        SettingsScreen(onNext = { isSettings = false },navController)
    } else {
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
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                GridWithSums(grid, rowSums, columnSums, gridSize) { x, y ->
                    if (!isGameOver) {
                        grid = increaseNumberAt(grid, x, y)
                        isGameOver = checkGameEnd(grid, rowSums, columnSums)
                        if (isGameOver) {
                            val playerWins = checkGameEnd(grid, rowSums, columnSums)
                            if (playerWins) {
                                navController.navigateSingleTop("${Screen.WinScreen.route}/$difficulty")
                            } else {
                                navController.navigateSingleTop("${Screen.LoseScreen.route}/$difficulty")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(if (difficulty == "level_extreme") 50.dp else 100.dp))

                Image(
                    painter = painterResource(id = R.drawable.game_btn_spin),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                        .height(80.dp)
                        .clickable {
                            grid = generateGrid(gridSize)
                            rowSums = generateSolvableSums(gridSize, maxSum)
                            columnSums = generateSolvableSums(gridSize, maxSum)
                            isSolvable = checkSolvability(rowSums, columnSums)
                            countdown = time
                            isGameOver = false
                        }
                    //.offset(0.dp, 100.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_btn_home),
                        contentDescription = "Info Button",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                navController.navigateSingleTop(Screen.LevelScreen.route)
                            }
                    )
                    Box(
                        modifier = Modifier

                            .size(140.dp, 50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Фоновое изображение
                        Image(
                            painter = painterResource(id = R.drawable.app_bg_timer),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Текст с количеством монет
                        Text(
                            text = countdownFormatted.toString(),
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
                                isSettings = true
                            }
                    )
                }

            }

        }
    }
}

@Composable
fun GridWithSums(
    grid: List<List<Int>>,
    rowSums: List<Int>,
    columnSums: List<Int>,
    gridSize: Int,
    onTap: (Int, Int) -> Unit
) {
    Column {
        // Display column sums at the top
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.size(60.dp)) // Empty space for alignment
            columnSums.forEach { sum ->
                SumBox(sum, gridSize)
            }
        }

        // Display grid with row sums
        grid.forEachIndexed { rowIndex, row ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                // Display the row sum on the left
                SumBox(rowSums[rowIndex], gridSize)
                row.forEachIndexed { columnIndex, number ->
                    NumberImageBox(number = number, onClick = {
                        onTap(rowIndex, columnIndex)
                    }, gridSize)
                }
            }
        }
    }
}

// Sum Box to display row/column sums
@Composable
fun SumBox(sum: Int, gridSize: Int) {
    // Map the sum value to its corresponding renamed image resource
    val imageResource: Painter = when (sum) {
        2 -> painterResource(id = R.drawable.game_ic_number_green_2)  // Replace with actual renamed images
        3 -> painterResource(id = R.drawable.game_ic_number_green_3)
        4 -> painterResource(id = R.drawable.game_ic_number_green_4)
        5 -> painterResource(id = R.drawable.game_ic_number_green_5)
        6 -> painterResource(id = R.drawable.game_ic_number_green_6)
        7 -> painterResource(id = R.drawable.game_ic_number_green_7)
        8 -> painterResource(id = R.drawable.game_ic_number_green_8)
        9 -> painterResource(id = R.drawable.game_ic_number_green_9)
        10 -> painterResource(id = R.drawable.game_ic_number_green_10)
        11 -> painterResource(id = R.drawable.game_ic_number_green_11)
        12 -> painterResource(id = R.drawable.game_ic_number_green_12)
        13 -> painterResource(id = R.drawable.game_ic_number_green_13)
        14 -> painterResource(id = R.drawable.game_ic_number_green_14)
        15 -> painterResource(id = R.drawable.game_ic_number_green_15)
        16 -> painterResource(id = R.drawable.game_ic_number_green_16)
        17 -> painterResource(id = R.drawable.game_ic_number_green_17)
        18 -> painterResource(id = R.drawable.game_ic_number_green_18)
        19 -> painterResource(id = R.drawable.game_ic_number_green_19)
        20 -> painterResource(id = R.drawable.game_ic_number_green_20)
        else -> painterResource(id = R.drawable.game_ic_number_green_2) // Default image if the sum is outside the range
    }

    Box(
        modifier = Modifier
            .size(if (gridSize == 5) 50.dp else 60.dp), // Adjust size accordingly
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = imageResource,
            contentDescription = "Sum Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(if (gridSize == 5) 50.dp else 60.dp)
        )
    }
}

// NumberImageBox to display the images
@Composable
fun NumberImageBox(number: Int, onClick: () -> Unit, gridSize: Int) {
    val imageResource: Painter = when (number) {
        1 -> painterResource(id = R.drawable.game_ic_number_1) // Use your actual resource ID here
        2 -> painterResource(id = R.drawable.game_ic_number_2)
        3 -> painterResource(id = R.drawable.game_ic_number_3)
        4 -> painterResource(id = R.drawable.game_ic_number_4)
        5 -> painterResource(id = R.drawable.game_ic_number_5)
        else -> painterResource(id = R.drawable.game_ic_number_1) // Fallback in case of other numbers
    }

    Box(
        modifier = Modifier
            .size(if (gridSize == 5) 50.dp else 60.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = imageResource,
            contentDescription = "Number Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(if (gridSize == 5) 50.dp else 60.dp)
        )
    }
}

// Logic for generating a grid based on grid size
fun generateGrid(gridSize: Int): List<List<Int>> {
    return List(gridSize) { List(gridSize) { 1 } } // Fill with 1 initially
}

// Logic for generating random target sums for rows/columns based on difficulty
fun generateSolvableSums(gridSize: Int, maxSum: Int): List<Int> {
    return List(gridSize) { Random.nextInt(2, maxSum) }
}

// Logic for increasing a number in the grid
fun increaseNumberAt(grid: List<List<Int>>, x: Int, y: Int): List<List<Int>> {
    val mutableGrid = grid.map { it.toMutableList() }.toMutableList()
    mutableGrid[x][y] = (mutableGrid[x][y] % 5) + 1 // Cycle numbers from 1 to 5
    return mutableGrid
}

// Logic for checking if the grid sums match the target sums
fun checkGameEnd(grid: List<List<Int>>, rowSums: List<Int>, columnSums: List<Int>): Boolean {
    // Check row sums
    for (i in grid.indices) {
        if (grid[i].sum() != rowSums[i]) return false
    }
    // Check column sums
    for (i in grid[0].indices) {
        val columnSum = grid.sumBy { row -> row[i] }
        if (columnSum != columnSums[i]) return false
    }
    return true
}

// Logic to check if the puzzle is solvable
fun checkSolvability(rowSums: List<Int>, columnSums: List<Int>): Boolean {
    // Check if total row sum matches total column sum
    val totalRowSum = rowSums.sum()
    val totalColumnSum = columnSums.sum()

    // If they match, then the puzzle can be solved by rearranging the grid
    return totalRowSum == totalColumnSum
}

@Preview
@Composable
fun GameScreen() {
    val navController = rememberNavController()
    GameScreen("level_easy", navController = navController)
}


