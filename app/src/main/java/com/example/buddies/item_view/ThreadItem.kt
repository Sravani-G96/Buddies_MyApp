package com.example.buddies.item_view

import android.os.RecoverySystem
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.buddies.R
import com.example.buddies.model.ThreadModel
import com.example.buddies.model.UserModel
import com.example.buddies.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ThreadItem(
    thread: ThreadModel,
    users : UserModel,
    navHostController: NavHostController,
    userId : String
) {

   Column {
       ConstraintLayout(modifier = Modifier
           .fillMaxWidth()
           .padding(16.dp)) {

           val (userImage, userName, date, time, title, image) = createRefs()

           Image(painter = rememberAsyncImagePainter(model = users.imageUrl),
               contentDescription = "close",
               modifier = Modifier
                   .constrainAs(userImage) {
                       top.linkTo(parent.top)
                       start.linkTo(parent.start)
                   }
                   .size(40.dp)
                   .clip(CircleShape),
               contentScale = ContentScale.Crop)

           Text(text = users.userName,
               style = TextStyle(
                   fontSize = 20.sp
               ), modifier = Modifier.constrainAs(userName){
                   top.linkTo(userImage.top)
                   start.linkTo(userImage.end, margin = 12.dp)
                   bottom.linkTo(userImage.bottom)
               })

           Text(text = thread.thread,
               style = TextStyle(
                   fontSize = 18.sp
               ), modifier = Modifier.constrainAs(title){
                   top.linkTo(userName.bottom, margin = 8.dp)
                   start.linkTo(userName.start)
               })

           if (thread.image != "") {
               Card(modifier = Modifier
                   .constrainAs(image) {
                       top.linkTo(title.bottom, margin = 8.dp)
                       start.linkTo(parent.start)
                       end.linkTo(parent.end)
                   }) {

                   Image(
                       painter = rememberAsyncImagePainter(model = thread.image),
                       contentDescription = "userimage",
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(200.dp),
                       contentScale = ContentScale.Crop
                   )
               }
           }

       }
   }

    Divider(color = Color.LightGray, thickness = 1.dp)
}




