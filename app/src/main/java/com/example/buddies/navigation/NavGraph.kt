package com.example.buddies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.buddies.screens.AddThreads
import com.example.buddies.screens.BottomNav
import com.example.buddies.screens.Home
import com.example.buddies.screens.Login

import com.example.buddies.screens.OtherUsers
import com.example.buddies.screens.Profile
import com.example.buddies.screens.Register
import com.example.buddies.screens.Search
import com.example.buddies.screens.Splash


@Composable
fun NavGraph(navController: NavHostController){

    NavHost(navController = navController,
        startDestination = Routes.Splash.routes){

        composable(Routes.Splash.routes){
            Splash(navController)
        }

        composable(Routes.AddThreads.routes){
            AddThreads(navController)
        }

        composable(Routes.Home.routes){
            Home(navController)
        }

        composable(Routes.Profile.routes){
            Profile(navController)
        }

        composable(Routes.Search.routes){
            Search(navController)
        }

        composable(Routes.BottomNav.routes){
            BottomNav(navController)
        }

        composable(Routes.Login.routes){
            Login(navController)
        }

        composable(Routes.Register.routes){
            Register(navController)
        }

        composable(Routes.OtherUsers.routes){
            val data = it.arguments!!.getString("data")
            OtherUsers(navController, data!!)
        }
    }

}