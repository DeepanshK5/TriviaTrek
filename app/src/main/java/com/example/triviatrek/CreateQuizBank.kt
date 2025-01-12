package com.example.triviatrek

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Integer.parseInt

var questionNumber by mutableStateOf(1)

@Composable
fun CreateQuizBank(
    navController: NavHostController,
    token : String,
    name:String
) {

    val context = LocalContext.current

    val db = Firebase.firestore


    var Question by rememberSaveable { mutableStateOf("") }
    var option1 by rememberSaveable { mutableStateOf("") }
    var option2 by rememberSaveable { mutableStateOf("") }
    var option3 by rememberSaveable { mutableStateOf("") }
    var option4 by rememberSaveable { mutableStateOf("") }
    var answer by rememberSaveable { mutableStateOf("") }

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

        Card(border = BorderStroke(2.dp, color = Color.Black)){
            Text(
                modifier = Modifier.padding(start = 30.dp, end = 30.dp,
                    top = 15.dp, bottom = 15.dp),
                text = "For Token ' $token ' \nQuestion Number : $questionNumber", fontSize = 20.sp,
                fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic
            )
        }

        Spacer(modifier = Modifier.padding(25.dp))

        OutlinedTextField(
            value = Question,
            onValueChange = { Question = it },
            label = {
                Text(
                    text = "Enter Question Here", fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic
                )
            }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(value = option1, onValueChange = {
            option1 = it
        },label = {
            Text(
                text = "Enter Option 1 Here", fontSize = 20.sp,
                fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic
            )
        })
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(value = option2, onValueChange = {
            option2 = it
        },label = {
            Text(text = "Enter Option 2 Here", fontSize = 20.sp,
                fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
        })
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(value = option3, onValueChange = {
            option3 = it
        },label = {
            Text(text = "Enter Option 3 Here", fontSize = 20.sp,
                fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)})
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(value = option4, onValueChange = {
            option4 = it
        },label = {
            Text(text = "Enter Option 4 Here", fontSize = 20.sp,
                fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)})
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(value = answer, onValueChange = {
            answer = it },keyboardOptions = KeyboardOptions.Default.
            copy(keyboardType = KeyboardType.Number),
            label = {
                Text(text = "Enter Answer Number Here", fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)}
        )
        Spacer(modifier = Modifier.padding(16.dp))

        var isCorrect : Boolean by rememberSaveable { mutableStateOf(true) }

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(onClick = {

                if(option1.isEmpty() || option2.isEmpty() ||
                    option3.isEmpty() || option4.isEmpty() ||
                    Question.isEmpty() || answer.isEmpty()){

                    Toast.makeText(
                        context,
                        "Please Fill All Fields",
                     Toast.LENGTH_LONG
                    ).show()

                    isCorrect = false
                }

                try {
                    val num = parseInt(answer)
                    if(num !in 1..4){
                        isCorrect = false
                        Toast.makeText(
                            context,
                            "Please Enter Correct Answer (1,2,3,4)",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: NumberFormatException) {
                    isCorrect = false
                    Toast.makeText(
                        context,
                        "Please Enter Correct Answer (1,2,3,4)",
                     Toast.LENGTH_LONG
                    ).show()
                }

                if(isCorrect){
                    Toast.makeText(
                        context,
                        "Submitted Successfully",
                     Toast.LENGTH_SHORT
                    ).show()

                    // Add Question to Database Here

                    val question = QuestionFormat(Question, option1, option2,
                        option3, option4, answer)

                    db.collection("Quiz's Token").document("Token")
                    .collection(token)
                        .document(questionNumber.toString())
                        .set(question)
                        .addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "${questionNumber-1} successfully written!",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d(TAG, "$questionNumber successfully written!")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error writing document", e)
                        }

                    //Increment Question Number
                    questionNumber++
                }
                else{
                    Toast.makeText(
                        context,
                        "Error Occurred, Please Try Again",
                        Toast.LENGTH_LONG
                    ).show()
                }

                //Reset CreateQuizBank Screen
                Question = ""
                option1 = ""
                option2 = ""
                option3 = ""
                option4 = ""
                answer = ""
                isCorrect = true

            }) {
                Text(text = "Add This")
            }
            Button(onClick = {
                Question = ""
                option1 = ""
                option2 = ""
                option3 = ""
                option4 = ""
                answer = ""
                isCorrect = true
                questionNumber = 99999

                val question = QuestionFormat(
                    Question, option1, option2,
                    option3, option4, answer
                )

                db.collection("Quiz's Token")
                    .document("Token")
                    .collection(token)
                    .document(questionNumber.toString())
                    .set(question)
                    .addOnCompleteListener {
                        Toast.makeText(
                            context,
                            "Completed Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d(TAG, "Completed Successfully")
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            context,
                            "Error Occurred, Please Try Again",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                db.collection("Quiz Creator")
                    .document(name)
                    .set(hashMapOf(token to true), SetOptions.merge())
                    .addOnCompleteListener {
                        Toast.makeText(
                            context,
                            "Name Added Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .addOnFailureListener{
                            Toast.makeText(
                                context,
                                "Error Occurred, Please Try Again",
                                Toast.LENGTH_LONG).show()
                    }

                //Navigate to QuizScreen
                navController.navigate(QuizScreen)
                }
            ){
                    Text(text = "Submit")
            }
        }
    }
}