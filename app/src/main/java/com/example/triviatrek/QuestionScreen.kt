package com.example.triviatrek

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun QuestionScreen(
    QuestionList : MutableList<QuestionFormat>,
    navController: NavHostController,
    token : String,
    name:String
){

    val isImeVisible by rememberImeState()
    val animationSpec by animateFloatAsState(
        targetValue = if (isImeVisible) 0.615f else 1f,
        label = "Ime Animation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(animationSpec)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        val context = LocalContext.current

        var index by rememberSaveable { mutableIntStateOf(0) }

        var score by rememberSaveable { mutableIntStateOf(0) }

        var selectedOption by rememberSaveable { mutableIntStateOf(-1) }

        Text(text = "For Token : $token", fontSize = 20.sp)

        Spacer(modifier = Modifier.padding(25.dp))

        Text(text = "Selected Option $selectedOption ")
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Score : $score")
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Index : ${index}")
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "QuestionList size : ${QuestionList.size}")
        Spacer(modifier = Modifier.padding(16.dp))


        Text(text = QuestionList[index].question, fontSize = 20.sp)
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = {
            selectedOption = 1
        }) {
            Text(text = QuestionList[index].option1)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = {
            selectedOption = 2
        }) {
            Text(text = QuestionList[index].option2)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = {
            selectedOption = 3
        }) {
            Text(text = QuestionList[index].option3)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = {
            selectedOption = 4
        }) {
            Text(text = QuestionList[index].option4)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(onClick = {
                if (QuestionList[index].answer.toInt() == selectedOption) {
                    score += 1
                }
                selectedOption = -1
                if (index + 1 == QuestionList.size) {
                    Toast.makeText(
                        context,
                        "Quiz Finished With Score $score",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("ResultScreen/$score")
                } else {
                    index++
                }
            })
            {
                Text(text = "Next")
            }
            Button(onClick = {
                if (QuestionList[index].answer.toInt() == selectedOption) {
                    score += 1
                }
                Toast.makeText(
                    context,
                    "Quiz Finished With Score $score",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate("ResultScreen/$score")
            })
            {
                Text(text = "Submit")
            }
        }
    }
}