package com.example.triviatrek

import android.graphics.drawable.Icon
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginActivity(
    navController:NavHostController,
    setName:(String)->Unit
){

    var context = LocalContext.current

    lateinit var auth: FirebaseAuth
    // Initialize Firebase Auth
    auth = Firebase.auth

//    onStart(navController)

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val isImeVisible by rememberImeState()

    val animationSpec by animateFloatAsState(
        targetValue = if (isImeVisible) 0.615f else 1f,
        label = "Ime Animation"
    )

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(animationSpec)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
            Image(
                painter = painterResource(id = R.drawable.triviatrek_login_screen),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
            )
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.welcome))
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.padding(bottom = 12.dp).size(200.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email=it },
                label = { Text(text = "Email Address") },
            )

            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = password, onValueChange = { password=it },
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Button(onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            var user = auth.currentUser
                            setName(email)
                            navController.navigate(QuizScreen)
//                            navController.navigate(QuizDetailScreen)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(onClick = { navController.navigate(SignUP) }) {
                Text(text = "Create Account")
            }

            Spacer(modifier = Modifier.size(10.dp))

            TextButton(onClick = {
                auth.signInAnonymously()
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success")
                            val user = auth.currentUser
                            setName("Anonymous User")
                            navController.navigate(QuizScreen)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            navController.navigate(QuizScreen)
//                            navController.navigate(QuizDetailScreen)                        }
                        }
                    }
            }) {
                Text(text = "Sign in Anonymously")
            }
    }
}

fun onStart(navController: NavHostController) {
    lateinit var auth: FirebaseAuth
    // Initialize Firebase Auth
    auth = Firebase.auth

    // Check if user is signed in (non-null) and update UI accordingly.
    val currentUser = auth.currentUser
    if (currentUser != null) {
        navController.navigate(SignUP)
    }
}