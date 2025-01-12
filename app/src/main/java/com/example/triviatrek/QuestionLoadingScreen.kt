package com.example.triviatrek

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun QuestionLoadingScreen(
    navController: NavHostController,
    questionList: MutableList<QuestionFormat>,
    token: String,
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
        val db = Firebase.firestore

        var times by rememberSaveable { mutableIntStateOf(0) }

        var hashMap : HashMap<String, QuestionFormat> = HashMap()

        var flag by rememberSaveable { mutableStateOf(true) }

        var size by rememberSaveable { mutableIntStateOf(0) }
        Text(text = "For token = $token Size of questionList = ${hashMap.size} And loop run for $times")

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.datafetching))
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        var stored by rememberSaveable { mutableStateOf(false) }


        db.collection("Quiz's Token").document("Token")
            .collection(token)
            .get()
            .addOnSuccessListener { result ->
                if(flag) {
                    for (document in result) {
                        Toast.makeText(
                            context,
                            "Success: ${document.id}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "${document.id} => ${document.data}}")
                        times++
                        if (document.id == "99999") {
                            stored = move(navController, questionList, hashMap)
                            Toast.makeText(
                                context,
                                "Completed Successfully testing hai bhai",
                                Toast.LENGTH_SHORT
                            ).show()
                            flag = false
                            return@addOnSuccessListener
                        }

                        var temp = QuestionFormat()
                        document.data.forEach { (key, value) ->
                            if (key == "question") {
                                temp.question = value.toString()
                            } else if (key == "option1") {
                                temp.option1 = value.toString()
                            } else if (key == "option2") {
                                temp.option2 = value.toString()
                            } else if (key == "option3") {
                                temp.option3 = value.toString()
                            } else if (key == "option4") {
                                temp.option4 = value.toString()
                            } else if (key == "answer") {
                                temp.answer = value.toString()
                            }
                        }
                        hashMap.put(document.id, temp)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Failure : $exception",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "Error getting documents: ", exception)
            }

        Button(onClick = {
            if(stored){
                navController.navigate(QuestionScreen)
            }
            else{
                Toast.makeText(
                    context,
                    "Please wait for data to be fetched",
                    Toast.LENGTH_LONG
                ).show()
            }
        }) {
            Text(text = "Start Quiz")
        }
    }
}

fun move(
    navController: NavHostController,
    questionList: MutableList<QuestionFormat>,
    hashMap : HashMap<String, QuestionFormat>,
):Boolean{
    questionList.clear()

    for (k in hashMap.keys){
        hashMap[k]?.let { questionList.add(it) }
    }

    return true
}