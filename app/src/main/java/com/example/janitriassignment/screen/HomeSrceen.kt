package com.example.janitriassignment.screen


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.janitriassignment.R
import com.example.janitriassignment.model.UserDetails
import com.example.janitriassignment.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat", "ResourceAsColor")
@Composable
fun Home(homeViewModel: HomeViewModel) {
    val data by homeViewModel.data.observeAsState(emptyList())

    var showDialog = remember { mutableStateOf(false) }
    var sysBp by remember { mutableStateOf("0") }
    var diaBp by remember { mutableStateOf("0") }
    var weight by remember { mutableStateOf(0) }
    var babyKicks by remember { mutableStateOf(0) }
    var containerWidth = LocalConfiguration.current.screenWidthDp.dp

    if (containerWidth > 500.dp) {
        containerWidth = 500.dp
    }

    LaunchedEffect(Unit) {
        homeViewModel.scheduleNotification()
    }
    val textEditWidth = containerWidth / 8f
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Track My Pregnancy")
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.topBar_color))
        )
    }, content = {
        it

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
                .padding(10.dp)
        ) {
            if (showDialog.value == true) {
                DialogB(onDismiss = { showDialog.value = false }, homeViewModel)
            }
            LazyColumn {
                items(data ?: emptyList()) { it ->
                    LazyItem(it)
                }
            }
        }
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog.value = true
                },
                shape = CircleShape,
                containerColor = colorResource(R.color.card_bottom_color),
                modifier = Modifier.padding(end = 15.dp, bottom = 25.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        })


}


fun getFormattedDate(): String {
    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy hh:mm a", Locale.ENGLISH)
    return dateFormat.format(Date())
}

@SuppressLint("ResourceAsColor")
@Composable
fun LazyItem(data: UserDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.card_color)
        )
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(modifier = Modifier) {
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.heart),
                            modifier = Modifier.size(25.dp),
                            tint = Color.Black,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "${data.sysBp} bpm",
                            style = TextStyle(
                                color = colorResource(R.color.black),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.scale),
                            modifier = Modifier.size(22.dp),
                            tint = Color.Black,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "${data.weight} kg",
                            style = TextStyle(
                                color = colorResource(R.color.black),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
                Column(modifier = Modifier) {
                    Row(modifier = Modifier) {
                        Icon(
                            painter = painterResource(R.drawable.blood_pressure),
                            modifier = Modifier.size(25.dp),
                            tint = Color.Black,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "${data.diaBp} mmHg",
                            style = TextStyle(
                                color = colorResource(R.color.black),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.new_born),
                            modifier = Modifier.size(25.dp),
                            tint = Color.Black,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "${data.babyKicks} kicks",
                            style = TextStyle(
                                color = colorResource(R.color.black),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }


            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.card_bottom_color))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = data.time)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogB(onDismiss: () -> Unit, homeViewModel: HomeViewModel) {
    var sysBp by remember { mutableStateOf("") }
    var diaBp by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var babyKicks by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    Dialog(onDismissRequest = {
        onDismiss.invoke()
    }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Add Vitals",
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedTextField(
                        value = sysBp,
                        onValueChange = {
                            sysBp = it
                        },
                        textStyle = TextStyle(color = colorResource(R.color.black)),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = Color.Black
                        ),
                        placeholder = { Text("Sys BP") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = diaBp,
                        onValueChange = { diaBp = it },
                        textStyle = TextStyle(color = colorResource(R.color.black)),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = Color.Black
                        ),
                        placeholder = { Text("Dia BP") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(color = colorResource(R.color.black)),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Black,
                        cursorColor = Color.Black
                    ),
                    placeholder = { Text(text = "Weight ( in kg )") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = babyKicks,
                    onValueChange = { babyKicks = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(color = colorResource(R.color.black)),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Black,
                        cursorColor = Color.Black
                    ),
                    placeholder = { Text(text = "Baby Kicks") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(80.dp))


                Button(
                    onClick = {
                        val data = UserDetails(
                            sysBp = sysBp,
                            diaBp = diaBp,
                            weight = weight,
                            babyKicks = babyKicks,
                            time = getFormattedDate()
                        )

                        coroutineScope.launch {
                            homeViewModel.insertData(data)
                        }

                        onDismiss.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.card_bottom_color)),
                    shape = RectangleShape,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "Submit",
                        style = TextStyle(
                            color = colorResource(R.color.white),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        ),
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun HomePrev() {
    //DialogB()
}