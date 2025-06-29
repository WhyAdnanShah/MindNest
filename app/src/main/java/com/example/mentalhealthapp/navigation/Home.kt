package com.example.mentalhealthapp.navigation

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.destinations.MoodScreen
import com.example.mentalhealthapp.destinations.SettingsScreen
import com.example.mentalhealthapp.ui.theme.MentalHealthAppTheme
import com.example.mentalhealthapp.viewModel.MoodViewModel
import com.example.mentalhealthapp.viewModel.MoodViewModelFactory

class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentalHealthAppTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.safeDrawing.asPaddingValues())
                )
                { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    //MoodScreen View Model
    /* This is being done because there was a problem regarding the Instance of the View Model.
       The MoodViewModel was being initiated in two different places in the MoodScreen which created issues.
       The best way is to pass ONE instance of the MoodViewModel to the MoodScreen.
    */
    val context = LocalContext.current.applicationContext as Application
    val moodViewModel : MoodViewModel = viewModel(factory = MoodViewModelFactory(context))



    //For Navigation Controller
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Journal,
        BottomNavItem.Zen,
        BottomNavItem.Vent,
        BottomNavItem.Settings
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.cropped_icon_image),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("MindNest", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, items)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable(BottomNavItem.Home.route) { MoodScreen(
                moodViewModel = moodViewModel
            ) }
            composable(BottomNavItem.Journal.route) { JournalScreen() }
            composable(BottomNavItem.Zen.route) { ZenModeScreen() }
            composable(BottomNavItem.Vent.route) { VentScreen() }
            composable(BottomNavItem.Settings.route) { SettingsScreen() }
        }
    }
}




@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<BottomNavItem>) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    NavigationBar (
        containerColor = colorResource(R.color.slate_gray)
    ){
        items.forEach {
                item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                },
                icon = {
                    Icon(painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(25.dp),
                        tint = Color.Unspecified
                    )
                },
                label = {
                    Text(text = item.label)
                },
            )
        }
    }
}

//@Preview
@Composable fun JournalScreen() { CenteredText("Journal", 20.sp) }
//@Preview
@Composable fun ZenModeScreen() { CenteredText("Zen Mode", 20.sp) }
//@Preview
@Composable fun VentScreen() { CenteredText("AI Venting Chat", 20.sp) }
//@Preview
//@Composable fun SettingsScreen() { CenteredText("Settings", 20.sp) }

//@Preview
@Composable
fun CenteredText(text: String, fontSize: TextUnit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text, fontSize = fontSize, fontWeight = FontWeight.Bold)
    }
}



