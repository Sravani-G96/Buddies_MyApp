package com.example.buddies.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.buddies.item_view.ThreadItem
import com.example.buddies.navigation.Routes
import com.example.buddies.utils.SharedPref
import com.example.buddies.viewmodel.AuthViewModel
import com.example.buddies.viewmodel.HomeViewModel
import com.example.buddies.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun OtherUsers(navHostController: NavHostController,uid: String) {
    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState(null)
    val users by userViewModel.users.observeAsState(null)
    val followerList by userViewModel.followerList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    val homeViewModel: HomeViewModel = viewModel()
    val threadAndUsers by homeViewModel.threadsAndUsers.observeAsState(null)

    userViewModel.fetchThreads(uid)
    userViewModel.fetchUser(uid)
    userViewModel.getFollowers(uid)
    userViewModel.getFollowing(uid)

    var currentUserId = ""

    if (FirebaseAuth.getInstance().currentUser != null)
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid


    LaunchedEffect(firebaseUser) {
        if (firebaseUser == null) {
            navHostController.navigate(Routes.Login.routes) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }


    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                val (text,
                    logo,
                    userName,
                    bio,
                    followers,
                    following,
                    button) = createRefs()

                Text(text = users!!.name, style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                ), modifier = Modifier.constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })

                Image(painter = rememberAsyncImagePainter(model = users!!.imageUrl),
                    contentDescription = "userimage",
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop)

                Text(text = users!!.userName,
                    style = TextStyle(
                        fontSize = 20.sp
                    ), modifier = Modifier.constrainAs(userName) {
                        top.linkTo(text.bottom)
                        start.linkTo(parent.start)
                    })

                Text(text = users!!.bio,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4,
                    modifier = Modifier.constrainAs(bio) {
                        top.linkTo(userName.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(logo.start)
                        bottom.linkTo(followers.top)
                        width = Dimension.fillToConstraints
                    })

                Text(text = "${followerList?.size} Followers",
                    style = TextStyle(
                        fontSize = 20.sp
                    ), modifier = Modifier.constrainAs(followers) {
                        top.linkTo(bio.bottom)
                        start.linkTo(parent.start)
                    })

                Text(text = " ${followingList?.size} Following",
                    style = TextStyle(
                        fontSize = 20.sp
                    ), modifier = Modifier.constrainAs(following) {
                        top.linkTo(followers.bottom)
                        start.linkTo(parent.start)
                    })

                ElevatedButton(onClick = {
                    if (currentUserId != "")
                        userViewModel.followUsers(uid, currentUserId)
                }, modifier = Modifier.constrainAs(button) {
                    top.linkTo(following.bottom)
                    start.linkTo(parent.start)
                }) {
                    Text(
                        text = if (followerList != null && followerList!!.isNotEmpty() && followerList!!.contains(
                                currentUserId
                            )
                        ) "Following" else "Follow"
                    )
                }

            }
        }

        /*if (buddies!= null && users!= null) {
            items(buddies ?: emptyList()) { pair ->
                ThreadItem(
                    thread = pair, users = users!!,
                    navHostController = navHostController, userId = SharedPref.getUserName(context)
                )
            }
        }*/

        if (threads != null && users != null) {
            if (threads!!.isEmpty()) {
                item {
                    Text(
                        text = "User has not posted anything",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    )
                }
            } else {

                items(threads ?: emptyList()) { pair ->
                    Divider(color = Color.Black, thickness = 1.dp)
                    val threadId = pair.thread
                    threadAndUsers?.let {
                        ThreadItem(
                            thread = pair,
                            users = users!!,
                            navHostController = navHostController,
                            userId = SharedPref.getUserName(context),
                            //threadId = threadId,
                            // homeViewModel = homeViewModel,
                            // threadAndUsers = it
                        )
                    }
                }
            }


        } else {
            item {
                Text(
                    text = "Loading...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}