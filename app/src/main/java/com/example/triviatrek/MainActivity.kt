package com.example.triviatrek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var questionList : MutableList<QuestionFormat> = mutableListOf()
            val navController = rememberNavController()
            var token by remember { mutableStateOf("") }
            var name : String = "Anonymous User"

            NavHost(navController = navController,
                startDestination = LoginScreen
            ) {
                composable<LoginScreen>{
                    LoginActivity(navController) { newName ->
                        name = newName
                    }
                }
                composable<SignUP>{
                    SignUp(navController)
                }
                composable<QuizScreen>{
                    QuizScreen(navController,name)
                }
                composable<QuestionLoadingScreen>{
                    QuestionLoadingScreen(navController,questionList,token,name)
                }
                composable<CreateToken>{
                    token = CreateToken(navController,name)
                }
                composable<EnterTokenScreen>{
                    token = enterTokenScreen(navController,name)
                }
                composable<CreateQuizBank>{
                    CreateQuizBank(navController,token,name)
                }
                composable<QuestionScreen>{
                    QuestionScreen(questionList,navController,token,name)
                }
                composable("ResultScreen/{score}") { backStackEntry ->
                    val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
                    ResultScreen(navController, name, token, score)
                }
                composable<QuizResultScreen> {
                    QuizResultScreen(name)
                }
            }
        }
    }
}
