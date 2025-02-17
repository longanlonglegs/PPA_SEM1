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
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ppa_sem1.MainActivity.Companion.item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.compose.AppTheme
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var item : MutableState<List<String>>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                item = remember { mutableStateOf(listOf("")) }
                MainApp()
            }
        }
    }
}

data class buyingItem(val name: String, val price: Double, val quantity: Int, val size: String)
data class clothes(val name: String, val price: Double)
data class users(val name: String, val password: String)

// Function to write JSON to a file
fun writeJsonToFile(context: Context, data: List<users>, filename: String) {
    val gson = Gson()
    val jsonData = gson.toJson(data)  // Serialize the MyData object to JSON

    try {
        val fileOutputStream: FileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
        fileOutputStream.write(jsonData.toByteArray())
        fileOutputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

// Function to write JSON to a file
fun writeBuyingJsonToFile(context: Context, data: ArrayList<buyingItem>, filename: String) {
    val gson = Gson()
    val jsonData = gson.toJson(data)  // Serialize the MyData object to JSON

    try {
        val fileOutputStream: FileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
        fileOutputStream.write(jsonData.toByteArray())
        fileOutputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

@Composable
// Function to read JSON from a file and deserialize it into a list of `users`
fun readJsonFromFile(context: Context, filename: String): ArrayList<users> {

    val file = File(context.filesDir, filename)

    if (!file.exists()) file.createNewFile()

    if (file.length() == 0L) writeJsonToFile(LocalContext.current, arrayListOf(users("test", "")) ,filename)


    // Open the file input stream
    val fileInputStream: FileInputStream = context.openFileInput(filename)

    // Read the file content as a string
    val jsonContent = fileInputStream.bufferedReader().use { it.readText() }
    fileInputStream.close()

    // Create Gson object
    val gson = Gson()

    // Use TypeToken to properly deserialize the generic List<users>
    val listType = object : TypeToken<List<users>>() {}.type
    return gson.fromJson(jsonContent, listType)  // Deserialize JSON back into List<users>
}

@Composable
// Function to read JSON from a file and deserialize it into a list of `users`
fun readBuyingJsonFromFile(context: Context, filename: String): ArrayList<buyingItem> {

    val file = File(context.filesDir, filename)

    if (!file.exists()) {file.createNewFile()}

    if (file.length() == 0L) writeBuyingJsonToFile(LocalContext.current, arrayListOf(buyingItem("logo", 0.0, 0, "s")) ,filename)

    // Open the file input stream
    val fileInputStream: FileInputStream = context.openFileInput(filename)

    // Read the file content as a string
    val jsonContent = fileInputStream.bufferedReader().use { it.readText() }
    fileInputStream.close()

    // Create Gson object
    val gson = Gson()

    // Use TypeToken to properly deserialize the generic List<users>
    val listType = object : TypeToken<List<buyingItem>>() {}.type
    return gson.fromJson(jsonContent, listType)  // Deserialize JSON back into List<users>
}

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
        composable("mainpage") {MainPage(navController)}
        composable("loginpage") { LoginPage(navController) }
        composable("paymentpage") { PaymentPage(navController) }
        composable("itempage/{name}/{price}",
            listOf(
                navArgument("name") { NavType.StringType },
                navArgument("price") { NavType.StringType },
            )
        ) {backStackEntry->
            val name = backStackEntry.arguments?.getString("name")
            val price = backStackEntry.arguments?.getString("price")
            if (name != null && price != null) {
                ItemPage(Modifier.padding(12.dp), navController, item, name, price)
                //call navController.navigate("itempage/${name}/${price}/")
            }
        }
        composable("paidpage") { PaidPage(navController) }
        composable("info") { Info(navController) }
        composable("signup") { SignUpPage(navController) }
        composable("contact") { Contact(navController)  }
        composable("shoppingcart") {ShoppingCart(navController)}
        composable("review") { ReviewPage(navController) }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCart(navController: NavController) {

    var context = LocalContext.current
    var cart by remember { mutableStateOf(arrayListOf(buyingItem("logo", 0.0, 0, "S"))) }
    cart = readBuyingJsonFromFile(context, "buying.json")

    Scaffold (
        topBar = {
            TopAppBar(title = { Text("Shopping Cart")},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("mainpage")
                    }){Icon(Icons.AutoMirrored.Filled.ArrowBack, "")}
                })
        },
        content = { paddingValues ->

            Column(
                Modifier.fillMaxSize().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    Modifier.height(400.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (product in cart) {
                        if(product.name!="logo") {
                            item {
                                Card(Modifier.fillMaxSize()) {
                                    Row {
                                        Image(
                                            painter = painterResource(
                                                context.resources.getIdentifier(
                                                    product.name,
                                                    "drawable",
                                                    context.packageName
                                                )
                                            ),
                                            contentDescription = "Item",
                                            modifier = Modifier
                                                .size(150.dp)
                                        )

                                        Column(
                                            Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.spacedBy(10.dp)
                                        ) {
                                            Text(product.name, fontWeight = FontWeight.Bold)
                                            Text(product.price.toString())
                                            Text(product.quantity.toString())
                                            Text(product.size)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            navController.navigate("paymentpage")
                        },
                    ) { Text("pay now") }
                    Button(
                        onClick = {
                            cart = arrayListOf(buyingItem("logo", 0.0, 0, "S"))
                            writeBuyingJsonToFile(context, cart, "buying.json")
                        },
                    ) { Text("Clear cart") }
                }
            }
        })
}

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainPage(navController: NavController) {

    val context = LocalContext.current

    var search by remember { mutableStateOf("") }

    var itemList by remember { mutableStateOf(listOf(clothes("testing", 30.00)))}

    itemList = parseJsonList(readJsonFromAssets(context))

    var rating = remember { mutableStateListOf<Float>().apply { addAll(List(itemList.size){0f}) } } //default rating will be 1

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                        Text("PPG Online Store", modifier = Modifier.padding(10.dp))
                },
                navigationIcon = {
                    Image(painterResource(R.drawable.logo), "icon", Modifier.padding(10.dp))
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("shoppingcart")
                    }) {Icon(Icons.Default.ShoppingCart, contentDescription = "shopping cart")}
                    IconButton(onClick = {
                        navController.navigate("loginpage")
                    }) {Icon(Icons.Default.AccountCircle, contentDescription = "login")}
                }
            )
        },
        content = { paddingValues ->
            // Main content with padding applied correctly

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                item {
                    Text("Where Performance Meets Peak",
                        fontWeight = FontWeight.ExtraBold,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                }
                stickyHeader {
                    TextField(value = search, onValueChange = {search = it},
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search by keyword...")},
                        leadingIcon = {Icon(Icons.Default.Search, contentDescription = "Search")})
                }

                val filteredList = itemList.filter { element ->
                    element.name.contains(search, ignoreCase = true)
                }

                for ((index, element) in filteredList.withIndex()) {
                    item {
                        val imageResourceID = LocalContext.current.resources.getIdentifier(element.name, "drawable", LocalContext.current.packageName)
                        ElevatedCard(
                            onClick = {
                                item.value = listOf(element.name, element.price.toString())
                                Log.d("valueCheck","${element.price}")
                                navController.navigate("itempage/${element.name}/${element.price}")
                            },
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(vertical = 5.dp, horizontal = 15.dp)
                        ) {
                            Row (Modifier.fillMaxSize().padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                                Image(
                                    painterResource(imageResourceID), "item 1",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(100.dp, 100.dp)
                                        .padding(10.dp)
                                )
                                Column (Modifier.fillMaxHeight().padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)){
                                    Text(
                                        text = element.name,
                                        textAlign = TextAlign.Center,
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    Text(
                                        text = "\$${element.price}",
                                        textAlign = TextAlign.Center,
                                    )
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                        RatingBar(
                                            value = rating[index],
                                            style = RatingBarStyle.Fill(),
                                            onValueChange = {
                                                rating[index] = it
                                            },
                                            stepSize = StepSize.HALF
                                        ){}
                                    }
                                }
                            }

                        }
                    }
                }
            }
        },

        floatingActionButton = {
            var expanded by remember { mutableStateOf(false) }
            FloatingActionButton(onClick = {
                expanded = !expanded
            },
               )  { Icon((Icons.Filled.Info), contentDescription = "LEAVE REVIEW")}
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("About") },
                        onClick = { navController.navigate("info")}
                    )
                    DropdownMenuItem(
                        text = { Text("Contact Us") },
                        onClick = {navController.navigate("contact") }
                    )
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    Column (Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                        Button(onClick = { navController.navigate("review") }) {
                            Text("Leave a review!", fontSize = 25.sp)

                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    )
}

@Composable
fun LoginPage(navController: NavController) {
    Column(horizontalAlignment = Alignment.Start) {
        var context = LocalContext.current
        var state by remember { mutableStateOf(false) }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var userList by remember { mutableStateOf((arrayListOf(users("test", "pw")))) }
        userList = readJsonFromFile(context, "users.json")

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
        Text("Hi there",
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp)

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
        Button(onClick = {
            for (user in userList) {
                if (user == users(username, password)) {
                    navController.navigate("mainpage")
                    Toast.makeText(context, "Logged in!", Toast.LENGTH_SHORT).show()
                    state = !state
                }
            }

            if (!state) {
                Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }

            state = false

        }) {
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
        var userList by remember { mutableStateOf((arrayListOf(users("test", "pw")))) }
        userList = readJsonFromFile(context, "users.json")

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
                    userList.add(users(username, password))
                    writeJsonToFile(context, userList, "users.json")
                    navController.navigate("mainpage")
                    Toast.makeText(context, "Sign up successful!", Toast.LENGTH_SHORT).show()
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
fun PaymentPage( navController: NavController) {

    val context = LocalContext.current
    var money by remember { mutableStateOf(0.0) }
    var cart by remember { mutableStateOf(arrayListOf(buyingItem("", 0.0, 0, ""))) }
    cart = readBuyingJsonFromFile(context, "buying.json")
    for (item in cart) money.plus(item.price)

    Scaffold (content = {paddingValues ->
        Column(Modifier
            .padding(paddingValues)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround ,content = {
            Text("\$$money", fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
            Text("Thank you for shopping with us!", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(Modifier.padding(10.dp))
            Box(Modifier
                .fillMaxWidth()
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
fun PaidPage(navController: NavController) {
    Column(Modifier.fillMaxSize().padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp), content = {
        Spacer(Modifier.padding(40.dp))
        Row (Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(R.drawable.logo), "logo")
            Text("Peak Performance Gear", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 50.sp, textAlign = TextAlign.Center)

        }
        Text("Thanks for the shopping with us!", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 50.sp, textAlign = TextAlign.Center)
        Button(onClick = {navController.navigate("mainpage")}) { Icon(Icons.Default.Home, "home") }
        Spacer(modifier = Modifier.height(200.dp))
    })
}

@Composable
fun ReviewPage(navController: NavController){
    val context = LocalContext.current
    var itemList by remember { mutableStateOf(listOf(clothes("testing", 30.00)))}
    itemList = parseJsonList(readJsonFromAssets(context))
    var itemBought by remember { mutableStateOf("") }
    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
        Spacer(modifier = Modifier.height(100.dp))
        var mExpanded by remember { mutableStateOf(false) }
        val icon = if (mExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown
        Box{
            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
            ) {
                itemList.forEach { label ->
                    DropdownMenuItem(onClick = {
                        itemBought = label.name
                        mExpanded = false
                    },
                        text = { Text(label.name) })
                }
            }
            TextField(
                value = itemBought,
                onValueChange = {itemBought = it},
                label = { Text("Item Bought") },
                trailingIcon = {

                    IconButton(onClick = {
                        mExpanded=true
                    }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null
                        )
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemPage(modifier: Modifier, navController: NavController, item: MutableState<List<String>>, name:String, priceS: String) {
    val context = LocalContext.current
    var cart by remember { mutableStateOf(arrayListOf(buyingItem("", 0.0, 0, ""))) }
    var quantity by remember { mutableStateOf(1) }
    val price = priceS.toDouble()
    var size by remember { mutableStateOf("") }
    var state by remember { mutableStateOf(1) }

    cart = readBuyingJsonFromFile(context, "buying.json")
    Scaffold (
        topBar = {
            TopAppBar(
                title = {Text("Details")},
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = {
            paddingValues->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Image(
                    painter = painterResource(
                        LocalContext.current.resources.getIdentifier(
                            name,
                            "drawable",
                            LocalContext.current.packageName
                        )
                    ),
                    contentDescription = "Item",
                    modifier = Modifier
                        .size(150.dp)
                )
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "$$price",
                    fontWeight = FontWeight.SemiBold
                )
                Column {
                    Text(text = "QUANTITY: $quantity")
                    Row {
                        Button(onClick = { quantity += 1 }) {
                            Icon(
                                Icons.Default.ArrowUpward,
                                "add"
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Button(onClick = { quantity += -1 }) {
                            Icon(
                                Icons.Default.ArrowDownward,
                                "minus"
                            )
                        }
                    }

                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                    Card (Modifier.padding(10.dp).defaultMinSize(minWidth = 40.dp)) {

                        Column (horizontalAlignment = Alignment.CenterHorizontally){

                            Text("S", textAlign = TextAlign.Center)
                            RadioButton(
                                selected = state == 1,
                                onClick = {
                                    state = 1
                                    size = "S"
                                })
                        }
                    }

                    Card (Modifier.padding(10.dp).defaultMinSize(minWidth = 70.dp)) {

                        Column  (horizontalAlignment = Alignment.CenterHorizontally){

                            Text("M", textAlign = TextAlign.Center)
                            RadioButton(
                                selected = state == 2,
                                onClick = {
                                    state = 2
                                    size = "M"
                                })
                        }
                    }

                    Card (Modifier.padding(10.dp).defaultMinSize(minWidth = 70.dp)) {

                        Column  (horizontalAlignment = Alignment.CenterHorizontally){

                            Text("L", textAlign = TextAlign.Center)
                            RadioButton(
                                selected = state == 3,
                                onClick = {
                                    state = 3
                                    size = "L"
                                })
                        }
                    }

                    Card (Modifier.padding(10.dp).defaultMinSize(minWidth = 70.dp)) {

                        Column  (horizontalAlignment = Alignment.CenterHorizontally){

                            Text("XL", textAlign = TextAlign.Center)
                            RadioButton(
                                selected = state == 4,
                                onClick = {
                                    state = 4
                                    size = "XL"
                                })
                        }
                    }


                }

                Text(size)

                Spacer(modifier = Modifier.padding(15.dp))
                Button(
                    onClick = {
                        cart += buyingItem(name, price, quantity, size)
                        writeBuyingJsonToFile(context, cart, "buying.json")
                        navController.navigate("mainpage")
                        Toast.makeText(context, "Item added to cart!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .size(width = 150.dp, height = 50.dp),
                    content = {
                        Text(text = "Add to Cart")
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contact(navController: NavController){
    Scaffold(
        topBar = { TopAppBar(
            title = {Text("Contact Information")},
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        ) },
        content = {
                paddingValues ->
            Column(horizontalAlignment = Alignment.CenterHorizontally ,modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Spacer(modifier = Modifier.height(200.dp))
                Image(painter = painterResource(R.drawable.logo), "")
                Row (Modifier.align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically){
                    Text(
                        "Contact Us!",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.dp)
                    )
                    Icon(Icons.Default.Call, "")
                }
                Text("Phone number: XXXXXXXX", fontSize = 20.sp, modifier = Modifier.padding(2.dp))
                Text("Email: csppa@nushigh.edu.sg", fontSize = 20.sp, modifier = Modifier.padding(2.dp))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ContactPreview(){
    AppTheme {
        Contact(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagepreview() {
    AppTheme {
        val navController = rememberNavController()
        MainPage( navController)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagepreview() {
    AppTheme {
        val navController = rememberNavController()
        LoginPage(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentPagepreview() {
    AppTheme {
        val navController = rememberNavController()
        PaymentPage(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPagepreview() {
    AppTheme {
        val name:String ="shorts"
        val price:String = ""
        val navController = rememberNavController()
        ItemPage(Modifier.padding(12.dp), navController, item, name,price)
    }
}

@Preview(showBackground = true)
@Composable
fun PaidPagepreview() {
    AppTheme {
        val navController = rememberNavController()
        PaidPage(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun Infopreview() {
    AppTheme {
        val navController = rememberNavController()
        Info(navController)
    }
}


@Preview(showBackground = true)
@Composable
fun SignUppreview() {
    AppTheme {
        val navController = rememberNavController()
        SignUpPage(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingCartpreview() {
    AppTheme {
        val navController = rememberNavController()
        ShoppingCart(navController)
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