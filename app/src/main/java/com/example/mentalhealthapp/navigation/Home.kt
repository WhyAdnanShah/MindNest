package com.example.mentalhealthapp.navigation

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.destinations.JournalScreen
import com.example.mentalhealthapp.destinations.MoodScreen
import com.example.mentalhealthapp.destinations.SettingsScreen
import com.example.mentalhealthapp.destinations.ZenModeScreen
import com.example.mentalhealthapp.journalScreen.JournalEntry
import com.example.mentalhealthapp.ui.theme.MentalHealthAppTheme
import com.example.mentalhealthapp.viewModel.JournalViewModel
import com.example.mentalhealthapp.viewModel.JournalViewModelFactory
import com.example.mentalhealthapp.viewModel.MoodViewModel
import com.example.mentalhealthapp.viewModel.MoodViewModelFactory
import com.example.mentalhealthapp.zenModeScreen.GuidedBreathingScreen

class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentalHealthAppTheme {
                HomeScreen(
                    modifier = Modifier
                )
            }
        }
    }
}


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

    val journalViewModel : JournalViewModel = viewModel(factory = JournalViewModelFactory(context))


    //For Navigation Controller
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Journal,
        BottomNavItem.Zen,
//        BottomNavItem.Vent,
        BottomNavItem.Settings
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier
                        .wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start) {
                        Image(
                            painter = painterResource(id = R.drawable.cropped_icon_image),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("MindNest", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    }
                }
            )
        },
        bottomBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route != "makeNote"){
                BottomNavigationBar(navController, items)
            }
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
                    journalViewModel = journalViewModel,
                    navController = navController
                )
            }

            composable(BottomNavItem.Zen.route) { ZenModeScreen(navController) }

            composable(BottomNavItem.Settings.route) { SettingsScreen() }


            composable ("makeNote") { JournalEntry(
                navController,
                journalViewModel = journalViewModel
            )
            }


            composable ("GuidedBreathingScreen/{title}/{rememberChipIndex}",
                arguments = listOf(
                    navArgument("title") {
                        type = NavType.StringType
                    },
                    navArgument("rememberChipIndex") {
                        type = NavType.IntType
                    }
                )
            ) {backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val rememberChipIndex = backStackEntry.arguments?.getInt("rememberChipIndex") ?: -1
                GuidedBreathingScreen(navController = navController ,title, rememberChipIndex)
            }

//          composable(BottomNavItem.Vent.route) { VentScreen() }
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
                    if (currentRoute != item.route) {
                        navController.navigate(item.route){
                            popUpTo(BottomNavItem.Home.route){
                                inclusive = false
                            }
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


/*          This will be MY simple Centered Text composable that i can use anywhere in the app         */
@Composable
fun CenteredText(text: String, fontSize: TextUnit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text, fontSize = fontSize, modifier = Modifier)
    }
}
