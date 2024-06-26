package com.example.buddies.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.buddies.R
import com.example.buddies.navigation.Routes
import com.example.buddies.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun Login(navController: NavHostController){


    var emailError by remember { mutableStateOf(false) }

    var passwordVisibility by remember { mutableStateOf(false) }
    val loading = remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }


    val authViewModel : AuthViewModel = viewModel()
    val firebaseUser :FirebaseUser? by authViewModel.firebaseUser.observeAsState()
    //val error :String? by authViewModel.error.observeAsState()

    val error by authViewModel.error.observeAsState(null)
    // Regex pattern for email validation
    val emailRegex = Regex("^([a-zA-Z0-9._%+-]+)?@+(?:gmail|hotmail|outlook)\\.com\$")
    LaunchedEffect(firebaseUser ){
        if (firebaseUser != null){
            navController.navigate(Routes.BottomNav.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    var email :String by remember {
        mutableStateOf("")
    }

    var password :String by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

   error?.let {
        val errorMessage = it.toString()
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {
        Text(text = "Login", style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        ))
        Box(modifier = Modifier.height(50.dp))

        OutlinedTextField(value = email, onValueChange = {
            email = it
            emailError = !it.matches(emailRegex)
                                                         },
            isError = emailError,
            label = {
            Text(text = "Email")
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth())

        if (emailError) {
            Text(
                text = "Invalid email format",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        val icon = if (passwordVisibility) {
            painterResource(id = R.drawable.visible_icon)
        } else {
            painterResource(id = R.drawable.visibility_off_icon)
        }

        Box(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = password,
            onValueChange = {password = it},
            label = {Text(text = "Password")
        }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password),
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility }
                ) {
                    Icon(painter = icon, contentDescription = "Toggle visibility")
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth())


        Box(modifier = Modifier.height(30.dp))

        ElevatedButton(onClick = {
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(context, "Please provide all fields", Toast.LENGTH_SHORT).show()
            }else
                 authViewModel.login(email, password, context)
                                 },
            modifier = Modifier.fillMaxWidth()
            ) {
            Text(text = "Login", style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp),
                modifier =Modifier.padding(vertical = 6.dp))
        }
        Box(modifier = Modifier.height(10.dp))

        TextButton(onClick = {
            navController.navigate(Routes.Register.routes){
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        } },
            modifier = Modifier.fillMaxWidth()
            ) {
            Text(text = "New User? Create an account", style = TextStyle(
                fontSize = 16.sp)
                )
        }

    }

    }


@Preview(showBackground = true)
@Composable
fun LoginPreview(){
   // Login()
}