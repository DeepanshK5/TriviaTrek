package com.example.triviatrek

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ResultScreen(
    navController: NavHostController,
    name: String,
    token: String,
    score: Int
) {
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

        db.collection("Quiz's Score").document("Quiz")
            .collection(token)
            .document("User's Score")
            .set(hashMapOf(name to score), SetOptions.merge())
            .addOnCompleteListener {
                Toast.makeText(
                    context,
                    "Score Submitted Successfully",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Error Occurred, Score Not Submitted",
                    Toast.LENGTH_LONG
                ).show()
            }

        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                )
                .border(2.dp, Color.Black, shape = MaterialTheme.shapes.medium)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ){
            Text(text = "Score : $score", fontSize = TextUnit(20f, TextUnitType.Sp), color = Color.Black,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.padding(25.dp))
        Row {
            Button(onClick = {
                navController.navigate(QuizScreen)
            }) {
                Text(text = "Go to Home")
            }
            Button(onClick = {
                navController.navigate(EnterTokenScreen)
            }) {
                Text(text = "Retry")
            }
        }
    }
}