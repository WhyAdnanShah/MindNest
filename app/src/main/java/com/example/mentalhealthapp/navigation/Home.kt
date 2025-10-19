package com.example.mentalhealthapp.navigation

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.destinations.JournalScreen
import com.example.mentalhealthapp.destinations.MoodScreen
import com.example.mentalhealthapp.destinations.SettingsScreen
import com.example.mentalhealthapp.destinations.ZenModeScreen
import com.example.mentalhealthapp.ui.theme.MentalHealthAppTheme
import com.example.mentalhealthapp.viewModel.JournalViewModel
import com.example.mentalhealthapp.viewModel.JournalViewModelFactory
import com.example.mentalhealthapp.viewModel.MoodViewModel
import com.example.mentalhealthapp.viewModel.MoodViewModelFactory

class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentalHealthAppTheme {
                HomeScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen() {
    val context = LocalContext.current.applicationContext as Application

    //MoodScreen View Model
    val moodViewModel : MoodViewModel = viewModel(factory = MoodViewModelFactory(context))
    //JournalScreen View Model
    val journalViewModel : JournalViewModel = viewModel(factory = JournalViewModelFactory(context))


    //For Navigation Controller
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Journal,
        BottomNavItem.Zen,
        BottomNavItem.Settings
    )


    Scaffold(
        topBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route != "makeNote" && navController.currentBackStackEntryAsState().value?.destination?.route != "GuidedBreathingScreen/{title}/{rememberChipIndex}"){
                TopAppBar(
                    title = {
                        Row(modifier = Modifier
                            .wrapContentSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start) {
                            Image(
                                painter = painterResource(id = R.drawable.cropped_icon_image),
                                contentDescription = "Icon Image",
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            when (navController.currentBackStackEntryAsState().value?.destination?.route) {
                                BottomNavItem.Home.route -> {
                                    Text("MindNest", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                }
                                BottomNavItem.Journal.route -> {
                                    Text("Your Journal", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                }
                                BottomNavItem.Zen.route -> {
                                    Text("Zen", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                }
                                BottomNavItem.Settings.route -> {
                                    Text("Settings", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                )
            }

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

            composable(BottomNavItem.Home.route)
            {
                MoodScreen(
                    moodViewModel = moodViewModel
                )
            }

            composable(BottomNavItem.Journal.route)
            {
                JournalScreen(
                    journalViewModel = journalViewModel
                )
            }

            composable(BottomNavItem.Zen.route) { ZenModeScreen(navController) }

            composable(BottomNavItem.Settings.route) { SettingsScreen() }

        }
    }
}




@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<BottomNavItem>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar (
        containerColor = Color.Transparent,
    ){
        items.forEach {
                item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(BottomNavItem.Home.route){
                            inclusive = false
                            saveState = true
                        }
                    }
                },
                icon = {
                    Icon(painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp),
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