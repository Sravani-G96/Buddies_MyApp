package com.example.buddies.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.buddies.item_view.ThreadItem
import com.example.buddies.model.ThreadModel
import com.example.buddies.model.UserModel
import com.example.buddies.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User

@Composable
fun Home(navHostController : NavHostController){

    val context = LocalContext.current
    val homeViewModel : HomeViewModel = viewModel()
    val threadAndUsers by homeViewModel.threadsAndUsers.observeAsState(null)

    LazyColumn{
        items(threadAndUsers ?: emptyList<Pair<Thread, User>>()){pairs ->
            ThreadItem(
                thread = pairs.first as ThreadModel,
                users = pairs.second as UserModel,
                navHostController,
                FirebaseAuth.getInstance().currentUser!!.uid)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview(){
   // Home()
}