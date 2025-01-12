package com.example.triviatrek


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun QuizScreen(
    navController: NavHostController,
    name:String
){
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
        Image(painter = painterResource(id = R.drawable.triviatrek_login_screen), contentDescription = null,
            modifier = Modifier.size(300.dp))

        Spacer(modifier = Modifier.padding(50.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    navController.navigate(CreateToken)
                }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Card (
                modifier = Modifier.size(50.dp)
            ){
                Icon(Icons.Outlined.Add, contentDescription = null,modifier = Modifier.size(50.dp))
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "Create A Quiz",fontSize = 25.sp, fontStyle = FontStyle.Italic)
        }


        Spacer(modifier = Modifier.padding(30.dp))


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    navController.navigate(EnterTokenScreen)
                }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Card (
                modifier = Modifier.size(50.dp)
            ){
                Icon(Icons.Filled.Done, contentDescription = null,modifier = Modifier.size(50.dp))
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "Join A Quiz",fontSize = 25.sp, fontStyle = FontStyle.Italic)
        }

//        Spacer(modifier = Modifier.padding(30.dp))
//
//        Row (
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable(onClick = {
//                    navController.navigate(QuizResultScreen)
//                }),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ){
//            Card (
//                modifier = Modifier.size(50.dp)
//            ){
//                Icon(Icons.Filled.Done, contentDescription = null,modifier = Modifier.size(50.dp))
//            }
//            Spacer(modifier = Modifier.padding(16.dp))
//            Text(text = "Get Result",fontSize = 25.sp, fontStyle = FontStyle.Italic)
//        }
    }
}