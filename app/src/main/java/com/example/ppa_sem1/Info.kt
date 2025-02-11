package com.example.ppa_sem1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

class Info {
    companion object{
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun Info(navController: NavController){
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Who are we") },
                        navigationIcon = {
                            IconButton(onClick = {navController.popBackStack()}) {
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                },
                content = {
                    paddingValues ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ){
                        Spacer(Modifier.height(30.dp))
                        Image(
                            painter = painterResource(R.drawable.todo),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        Text("blah blah")
                    }
                }
            )
        }

        @Preview(showBackground = true)
        @Composable
        fun PreviewInfo(){
            Info()
        }
    }
}