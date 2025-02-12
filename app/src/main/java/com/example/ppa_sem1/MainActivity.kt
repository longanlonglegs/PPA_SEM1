package com.example.ppa_sem1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ppa_sem1.ui.theme.PPA_SEM1Theme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PPA_SEM1Theme {
                MainApp()
            }
        }
    }
}

data class clothes(val name: String, val price: Double)

@Composable
fun parseJsonList(json: String): List<clothes> {
    val gson = Gson()
    // TypeToken allows Gson to work with generic types (like List<MyData>)
    val type = object : TypeToken<List<clothes>>() {}.type
    return gson.fromJson(json, type)
}

@Composable
fun readJsonFromAssets(context: Context): String {
    // Open the file from the assets folder
    val assetManager = context.assets
    val inputStream: InputStream = assetManager.open("data.json")
    val inputStreamReader = InputStreamReader(inputStream, Charset.defaultCharset())
    return inputStreamReader.readText()
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainpage") {
        composable("mainpage") {MainPage(Modifier.padding(12.dp), navController, "cool cs kids")}
        composable("loginpage") { LoginPage(Modifier.padding(12.dp), navController) }
        composable("paymentpage") { PaymentPage(Modifier.padding(12.dp), navController) }
        composable("itempage") { ItemPage(Modifier.padding(12.dp), navController) }
        composable("paidpage") { PaidPage(Modifier.padding(12.dp), navController) }
        composable("info") { Info(navController) }
        composable("signup") { SignUpPage(navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainPage(padding: Modifier, navController: NavController, name: String) {

    val context = LocalContext.current

    var search by remember { mutableStateOf("") }

    var itemList by remember { mutableStateOf(listOf(clothes("testing", 30.00)))}

    itemList = parseJsonList(readJsonFromAssets(context))

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically){ Image(painterResource(R.drawable.ic_launcher_background),null, modifier = Modifier
                        .size(70.dp)
                        .padding(vertical = 10.dp))
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
                for (element in itemList) {
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
                            Row (Modifier.fillMaxSize()) {

                                val imageResourceID = LocalContext.current.resources.getIdentifier(element.name, "drawable", LocalContext.current.packageName)

                                Image(
                                    painterResource(imageResourceID), "item 1",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(200.dp)
                                )
                                Column (Modifier.fillMaxHeight()){
                                    Text(
                                        text = element.name,
                                        modifier = Modifier
                                            .padding(16.dp),
                                        textAlign = TextAlign.Center,
                                    )

                                    Text(
                                        text = "\$${element.price}",
                                        modifier = Modifier
                                            .padding(16.dp),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }

                        }
                    }
                }
            }
        },
        bottomBar = {
            Row (modifier = Modifier
                .fillMaxWidth()
                .height(70.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = { navController.navigate("Login") }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(horizontal = 7.dp)
                    )
                }
                Text(name)
            }
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                /*TODO: review page*/
            },
                containerColor = Color.Cyan)  { Icon((Icons.Default.MailOutline), contentDescription = "LEAVE REVIEW")}
        }
    )
}

@Composable
fun LoginPage(padding: Modifier, navController: NavController) {
    Column(horizontalAlignment = Alignment.Start) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(50.dp))
        Row (modifier = Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = { navController.navigate("mainpage") }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text("Login")
        }
        TextField(label = { Text("Username")}, value = username, onValueChange = {username=it}, modifier = Modifier.padding(10.dp), singleLine = true)
        TextField(label = { Text("Password")}, value = password, onValueChange = {password=it}, modifier = Modifier.padding(10.dp), singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            })
        Button(onClick = {/*TODO: check user json*/ }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("Don't have an account? Sign Up here", modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { navController.navigate("signup") }
                )
            }
            .padding(10.dp),
            textDecoration = TextDecoration.Underline)
    }
}

@Composable
fun SignUpPage(navController: NavController) {
    Column(horizontalAlignment = Alignment.Start) {
        val context = LocalContext.current
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var cpassword by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var cpasswordVisible by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(50.dp))
        Row(modifier = Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate("mainpage") }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text("Sign In")
        }
        TextField(label = { Text("Username")}, value = username, onValueChange = {username=it}, modifier = Modifier.padding(10.dp), singleLine = true)
        TextField(label = { Text("Password")}, value = password, onValueChange = {password=it}, modifier = Modifier.padding(10.dp), singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            })
        TextField(label = { Text("Confirm Password")}, value = cpassword, onValueChange = {cpassword=it}, modifier = Modifier.padding(10.dp), singleLine = true,
            visualTransformation = if (cpasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (cpasswordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (cpasswordVisible) "Hide password" else "Show password"

                IconButton(onClick = {cpasswordVisible = !cpasswordVisible}){
                    Icon(imageVector  = image, description)
                }
            })
        Button(onClick = {
            /*
                8-20 chars
                1 lowercase
                1 uppercase
                1 number
                optional special char
             */
            if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d!@#\$%^&*()_+\\-=\\[\\]{};':\",.<>?/|\\\\`~]{8,20}\$".toRegex())){
                if(password == cpassword){
                    /*TODO: add to user json*/
                }else{
                    Toast.makeText(context, "Password and confirm do not match",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "Password does not match requirements",Toast.LENGTH_SHORT).show()
            }
            }) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("Already have an account? Login here", modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { navController.navigate("loginpage") }
                )
            }
            .padding(10.dp),
            textDecoration = TextDecoration.Underline)
    }
}

@Composable
fun PaymentPage(padding: Modifier, navController: NavController) {
    Scaffold (content = {paddingValues ->
        Column(Modifier
            .padding(paddingValues)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround ,content = {
            Text("\$Money", fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
            Text("Thank you for shopping with us!", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(Modifier.padding(10.dp))
            Box(Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
                .align(Alignment.CenterHorizontally),content = {Text("Pay with card", Modifier.align(
                Alignment.Center))})
            TextField(label = { Text("Email")}, value = "", onValueChange = {})
            TextField(label = { Text("Card Number")}, value = "", onValueChange = {})
            Row (Modifier
                .fillMaxWidth()
                .padding(5.dp), horizontalArrangement = Arrangement.Absolute.SpaceAround) {
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