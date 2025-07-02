package com.example.mentalhealthapp.navigation

import com.example.mentalhealthapp.R

sealed class BottomNavItem (
    val route : String, val icon : Int, val label : String
)
{
    object Home : BottomNavItem(
        route = "mood",
        icon = R.drawable.mood_icon,
        label = "Mood"
    )
    object Journal : BottomNavItem(
        route = "journal",
        icon = R.drawable.book_icon,
        label = "Journal"
    )
    object Zen : BottomNavItem(
        route = "zen",
        icon = R.drawable.zen_icon,
        label = "Zen Mode"
    )
//    object Vent : BottomNavItem(
//        route = "vent",
//        icon = R.drawable.vent_icon,
//        label = "Vent"
//    )
    object Settings : BottomNavItem(
        route = "settings",
        icon = R.drawable.settings_icon,
        label = "Settings"
    )
}