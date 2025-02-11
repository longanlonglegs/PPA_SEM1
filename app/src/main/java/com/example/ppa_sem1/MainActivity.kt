package com.example.ppa_sem1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ppa_sem1.ui.theme.PPA_SEM1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PPA_SEM1Theme {
                mainApp()
            }
        }
    }
}

@Composable
fun mainApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainpage") {
        composable("mainpage") {MainPage(Modifier.padding(12.dp), navController, "cool cs kids")}
        composable("loginpage") { LoginPage(Modifier.padding(12.dp), navController) }
        composable("paymentpage") { PaymentPage(Modifier.padding(12.dp), navController) }
        composable("itempage") { ItemPage(Modifier.padding(12.dp), navController) }
        composable("paidpage") { PaidPage(Modifier.padding(12.dp), navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainPage(padding: Modifier, navController: NavController, name: String) {

    var search by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically){ Image(painterResource(R.drawable.ic_launcher_background),null, modifier = Modifier.size(70.dp).padding(vertical = 10.dp))
                        Text("Peak Performance Gear", modifier = Modifier.padding(horizontal = 10.dp))}
                },
                colors = TopAppBarColors(
                    Color.hsl(207f, 0.26f, 0.13f),
                    scrolledContainerColor = Color.White,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate("paymentpage")
                    }) {Icon(Icons.Default.ShoppingCart, contentDescription = "Payment")}
                    IconButton(onClick = {
                        navController.navigate("loginpage")
                    }) {Icon(Icons.Default.AccountCircle, contentDescription = "login")}
                }
            )
        }, content = { paddingValues ->
            // Main content with padding applied correctly

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                stickyHeader {
                    TextField(value = search, onValueChange = {search = it},
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search by keyword")},
                        leadingIcon = {Icon(Icons.Default.Search, contentDescription = "Search")})
                }
                repeat(20) {
                    item {
                        ElevatedCard(
                            onClick = {navController.navigate("itempage")},
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            colors = CardColors(
                                contentColor = Color.White,
                                containerColor = Color.DarkGray,
                                disabledContentColor = Color.White,
                                disabledContainerColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(5.dp)
                        ) {
                            Image(
                                painterResource(R.drawable.ic_launcher_background), "item 1",
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                            Text(
                                text = "Item 1",
                                modifier = Modifier
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                            )

                        }
                    }
                }
            }
        },
        bottomBar = {
            Row (modifier = Modifier.fillMaxWidth().height(70.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = { navController.navigate("Login") }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp).padding(horizontal = 7.dp)
                    )
                }
                Text(name)
            }
        }
    )
}

@Composable
fun LoginPage(padding: Modifier, navController: NavController) {
    TODO("Not yet implemented")
}

@Composable
fun PaymentPage(padding: Modifier, navController: NavController) {
    Scaffold (content = {paddingValues ->
        Column(Modifier.padding(paddingValues).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround ,content = {
            Text("\$Money", fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
            Text("Thank you for shopping with us!", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(Modifier.padding(10.dp))
            Box(Modifier.fillMaxWidth().background(Color.Cyan).align(Alignment.CenterHorizontally),content = {Text("Pay with card", Modifier.align(
                Alignment.Center))})
            TextField(label = { Text("Email")}, value = "", onValueChange = {})
            TextField(label = { Text("Card Number")}, value = "", onValueChange = {})
            Row (Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.Absolute.SpaceAround) {
                TextField(label = { Text("MM/YY") }, value = "", onValueChange = {})
                Spacer(Modifier.padding(5.dp))
                TextField(label = { Text("CVC") }, value = "", onValueChange = {})
            }
            TextField(label = { Text("Home Address")}, value = "", onValueChange = {})
            Spacer(Modifier.padding(5.dp))
            Button(onClick = {navController.navigate("paidpage")}, content = {Text("Pay")})

        })
    })
}

@Composable
fun PaidPage(padding: Modifier, navController: NavController) {
    Column(Modifier.fillMaxSize(), content = {
        Text("Thanks for the purchase!\n we hope you enjoy your clothes")
        Button(onClick = {navController.navigate("mainpage")}) { Text("Home") }
    })
}

@Composable
fun ItemPage(modifier: Modifier, navController: NavController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                this.alpha = 0.5f
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(15.dp))
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Item",
            modifier = Modifier
                .size(300.dp)
        )

        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = "ADD ITEM NAME HERE",
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = "ADD PRICE HERE",
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = "QUANTITY ")

        Spacer(modifier = Modifier.padding(15.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .size(width = 150.dp, height = 50.dp),
            content = {
                Text(text = "Add to Cart")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagepreview() {
    PPA_SEM1Theme {
        val navController = rememberNavController()
        MainPage(Modifier.padding(12.dp), navController, "cool cs kids")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagepreview() {
    PPA_SEM1Theme {
        val navController = rememberNavController()
        LoginPage(Modifier.padding(12.dp), navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentPagepreview() {
    PPA_SEM1Theme {
        val navController = rememberNavController()
        PaymentPage(Modifier.padding(12.dp), navController)
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPagepreview() {
    PPA_SEM1Theme {
        val navController = rememberNavController()
        ItemPage(Modifier.padding(12.dp), navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PaidPagepreview() {
    PPA_SEM1Theme {
        val navController = rememberNavController()
        PaidPage(Modifier.padding(12.dp), navController)
    }
}



