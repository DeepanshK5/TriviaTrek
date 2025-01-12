package com.example.triviatrek

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun QuizResultScreen(
    name : String
){

    val context = LocalContext.current
    val db = Firebase.firestore
    var tokens : MutableList<String> by rememberSaveable { mutableStateOf(mutableListOf()) }
    var OurResultData: MutableList<ResultData> = mutableListOf()

    db.collection("Quiz Creator")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Toast.makeText(
                    context,
                    "${document.id} => ${document.data}",
                    Toast.LENGTH_LONG
                ).show()
                if(document.id == name){
                    Toast.makeText(
                        context,
                        "Token Found ${document.data.keys}",
                        Toast.LENGTH_LONG
                    ).show()

                    tokens = document.data.keys.toMutableList()

//                    if(tokens.size != 0){
//                        //make this to navigate screen to another file bcoz composable hun=m
//                        // sirf composable se hi call kar sakte hain aur iske liye tumhe
//                        // dusra scree(loading) banana padegajo jo loaf karega chal
//                        // bhai bye aj ke liye itna hi ab next time kam karte hue  isko
//                        // complete karne ka try karna till then jai bajrang bali om namah shiway
//                        MakeResult(context,tokens,OurResultData)
//                    }

                    Toast.makeText(
                        context,
                        "Token's size ${tokens.size}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        .addOnFailureListener { exception ->
            println("Error getting tokens: $exception")
        }

    if(tokens.size != 0){
        MakeResult(context, tokens, OurResultData)
    }


    Toast.makeText(
        context,
        "Outer Size of tokens is ${tokens.size}",
        Toast.LENGTH_LONG
    ).show()

}



@Composable
fun LazyHeader(
    tokenName: String
){
    Text(
        text = tokenName,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    )
}

@Composable
fun LazyItems(
    OurResultItem : Pair<String,Int>
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = OurResultItem.first
        )
        Text(
            text = OurResultItem.second.toString()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyFunc(
    OurResult : List<ResultData>
){
    Text(text = "Working hai Bro", modifier = Modifier.padding(top = 100.dp))
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        userScrollEnabled = true,
    ){
        item{ Text(text = "Size of OurResult is ${OurResult.size}") }
        OurResult.forEach { result->
            stickyHeader{
                LazyHeader(tokenName = result.tokenName)
            }
            items(result.results){ item->
                LazyItems(OurResultItem = item)
            }
        }
    }
}


@Composable
fun MakeResult(
    context : Context,
    tokens : MutableList<String>,
    OurResultData: MutableList<ResultData>
){

    val db = Firebase.firestore

    Text(text = "calling makeresult successful",
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
            .padding(top = 20.dp)
    )

    var username : List<String>
    var userscore : List<Int>
    var OurResultList : List<Pair<String,Int>>
    for(token in tokens){
        username = listOf()
        userscore = listOf()
        OurResultList = listOf()
        Toast.makeText(
            context,
            "Now in Token $token",
            Toast.LENGTH_LONG
        ).show()
        db.collection("Quiz's Score")
            .document("Quiz")
            .collection(token)
            .document("User's Score")
            .get()
            .addOnSuccessListener { scoredata ->
                if (scoredata != null) {
                    username = scoredata.data?.keys?.toList()!!
                    userscore = (scoredata.data?.values?.toList() as List<Int>?)!!
                    OurResultList = List(username.size) {
                        Pair(username[it], userscore[it])
                    }
                    val TempData = ResultData(token, OurResultList)
                    OurResultData.add(TempData)
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting tokens: $exception")
            }
        Toast.makeText(
            context,
            "Size of OurResultData is ${OurResultData.size}",
            Toast.LENGTH_LONG
        ).show()
    }
}