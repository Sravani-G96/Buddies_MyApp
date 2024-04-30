package com.example.buddies.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.buddies.R
import com.example.buddies.item_view.ThreadItem
import com.example.buddies.model.ThreadModel
import com.example.buddies.model.UserModel
import com.example.buddies.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User

@Composable
fun Home(navHostController : NavHostController) {

    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel()
    val threadAndUsers by homeViewModel.threadsAndUsers.observeAsState(null)

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically

        ) {
            /*Text(
                text = "Buddies",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight(12),
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal =2.dp))*/
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(top = 5.dp)
            )

            Text(
                text = "Home",
               // style = MaterialTheme.typography.headlineLarge,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 110.dp)
            )
        }
        //Spacer(modifier = Modifier.padding(vertical = 10.dp))

        LazyColumn {
            items(threadAndUsers ?: emptyList<Pair<Thread, User>>()) {
                pairs ->
                ThreadItem(
                    thread = pairs.first as ThreadModel,
                    users = pairs.second as UserModel,
                    navHostController,
                    FirebaseAuth.getInstance().currentUser!!.uid
                )
            }
        }

    }
}



/*
@Preview(showBackground = true)
@Composable
fun HomePreview(){
   // Home()
}*/