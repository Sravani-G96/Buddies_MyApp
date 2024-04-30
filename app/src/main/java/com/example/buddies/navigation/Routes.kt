package com.example.buddies.navigation

sealed class Routes(val routes: String) {
    object Home : Routes("home")

    object AddThreads : Routes("add_threads")
    object Profile : Routes("profile")
    object Search : Routes("search")
    object Splash : Routes("splash")
    object BottomNav : Routes("bottom_nav")
    object Login : Routes("login")
    object Register : Routes("register")
    object OtherUsers : Routes("other_users/{data}")
}