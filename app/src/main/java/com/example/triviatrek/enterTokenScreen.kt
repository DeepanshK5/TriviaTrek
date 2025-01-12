package com.example.triviatrek

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun enterTokenScreen(
    navController: NavHostController,
    name:String
) : String{

    var token by rememberSaveable { mutableStateOf("") }
    val isImeVisible by rememberImeState()
    val animationSpec by animateFloatAsState(
        targetValue = if (isImeVisible) 0.615f else 1f,
        label = "Ime Animation"
    )

    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(animationSpec)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        val context = LocalContext.current


        Image(painter = painterResource(id = R.drawable.triviatrek_login_screen), contentDescription = null,
            modifier = Modifier.size(300.dp))


        Text(text = "Enter Unique Customized Token", fontSize = 20.sp,
            fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(value = token, onValueChange = {
            token = it
        }, modifier = Modifier
            .padding(16.dp)
            .size(300.dp, 50.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(onClick = {
            Toast.makeText(
                context,
                "Generated Token Successfully",
                Toast.LENGTH_SHORT,
            ).show()
            navController.navigate(QuestionLoadingScreen)
        }) {
            Text(text = "Generate Token")
        }
    }
    return token
}