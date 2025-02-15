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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ppa_sem1.MainActivity.Companion.item
import com.example.ppa_sem1.ui.theme.PPA_SEM1Theme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var item : MutableState<List<String>>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PPA_SEM1Theme {
                item = remember { mutableStateOf(listOf("")) }
                MainApp()
            }
        }
    }
}

data class buyingItem(val name: String, val price: Double, val quantity: Int)
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
fun writeBuyingJsonToFile(context: Context, data: List<buyingItem>, filename: String) {
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

    val file = File(context.filesDir, "users.json")

    if (!file.exists()) file.createNewFile()

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

    val file = File(context.filesDir, "buying.json")

    if (!file.exists()) {file.createNewFile()}

    if (file.length() == 0L) writeBuyingJsonToFile(LocalContext.current, arrayListOf(buyingItem("", 0.0, 0)) ,"buying.json")

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
        composable("mainpage") {MainPage(navController, "cool cs kids")}
        composable("loginpage") { LoginPage(navController) }
        composable("paymentpage") { PaymentPage(navController) }
        composable("itempage/{name}/{price}/{id}",
            listOf(navArgument("name"){NavType.StringType},
                navArgument("price"){NavType.FloatType},
                navArgument("id"){NavType.IntType})
        ) {backStackEntry->
            val name = backStackEntry.arguments?.getString("name")
            val price = backStackEntry.arguments?.getDouble("price")
            val id = backStackEntry.arguments?.getInt("id")
            if (name != null && price != null && id != null) {
                ItemPage(Modifier.padding(12.dp), navController, item, name, price, id)
            //call navController.navigate("itempage/${name}/${price}/${id}")
            }
        }
        composable("paidpage") { PaidPage(Modifier.padding(12.dp), navController) }
        composable("info") { Info(navController) }
        composable("signup") { SignUpPage(navController) }
        composable("contact") { Contact(navController)  }
        composable("shoppingcart") {ShoppingCart(navController)}
    }
}

@Composable
fun ShoppingCart(navController: NavController) {

    var context = LocalContext.current
    var cart by remember { mutableStateOf(arrayListOf(buyingItem("", 0.0, 0))) }
    cart = readBuyingJsonFromFile(context, "buying.json")

    Column (Modifier.fillMaxSize()){
        LazyColumn (){
            for (item in cart) {
                item {
                    Card (Modifier.fillMaxSize()){
                        Row {
                            Column (Modifier.fillMaxSize()){
                                Text(item.name)
                                Text(item.price.toString())
                                Text(item.quantity.toString())
                            }
                        }
                    }
                }
            }
        }
        Button(onClick ={
            navController.navigate("paymentpage")
        }, Modifier.fillMaxWidth().defaultMinSize(60.dp)) { Text("pay now") }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainPage(navController: NavController, name: String) {

    val context = LocalContext.current

    var search by remember { mutableStateOf("") }

    var itemList by remember { mutableStateOf(listOf(clothes("testing", 30.00)))}

    itemList = parseJsonList(readJsonFromAssets(context))

    var rating by remember { mutableStateOf(4f) } //default rating will be 1

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
                    .background(Color.LightGray)
            ) {
                item {
                    Text("Where Performance Meets Peak",
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                }
                stickyHeader {
                    TextField(value = search, onValueChange = {search = it},
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search by keyword")},
                        leadingIcon = {Icon(Icons.Default.Search, contentDescription = "Search")})
                }
                for (element in itemList) {
                    item {
                        val imageResourceID = LocalContext.current.resources.getIdentifier(element.name, "drawable", LocalContext.current.packageName)
                        ElevatedCard(
                            onClick = {

                                item.value = listOf(element.name, element.price.toString())

                                navController.navigate("itempage/${element.name}/${element.price}/${imageResourceID}")
                                      },
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
                                Image(
                                    painterResource(imageResourceID), "item 1",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .background(Color.White)
                                        .padding(20.dp)
                                )
                                Column (Modifier.fillMaxHeight()){
                                    Text(
                                        text = element.name,
                                        modifier = Modifier
                                            .padding(16.dp),
                                        textAlign = TextAlign.Center,
                                        fontSize = 40.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )

                                    Text(
                                        text = "\$${element.price}",
                                        modifier = Modifier
                                            .padding(16.dp),
                                        textAlign = TextAlign.Center,
                                    )

                                    StarRatingBar(
                                        maxStars = 5,
                                        rating = rating,
                                        onRatingChanged = {
                                            rating = it
                                        }
                                    )
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
                containerColor = Color.Black)  { Icon((Icons.Filled.Info), contentDescription = "LEAVE REVIEW")}
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Find out more about us!") },
                        onClick = { navController.navigate("info")}
                    )
                    DropdownMenuItem(
                        text = { Text("Contact Us") },
                        onClick = {navController.navigate("contact") }
                    )
                }
            }
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
fun ItemPage(modifier: Modifier, navController: NavController, item: MutableState<List<String>>, name:String, price: Double, id:Int) {
    val context = LocalContext.current
    var cart by remember { mutableStateOf(arrayListOf(buyingItem("", 0.0, 0))) }
    var quantity by remember { mutableStateOf(1) }

    cart = readBuyingJsonFromFile(context, "buying.json")

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
            painter = painterResource(LocalContext.current.resources.getIdentifier(name, "drawable", LocalContext.current.packageName)),
            contentDescription = "Item",
            modifier = Modifier
                .size(300.dp)
        )

        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = name,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = "$$price",
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.padding(15.dp))

        Row {
            Text(text = "QUANTITY ")
            Button(onClick = {quantity += 1}) { Icon(Icons.Default.ArrowUpward, "add")}
            Button(onClick = {quantity += -1}) { Icon(Icons.Default.ArrowDownward, "minus")}
        }


        Spacer(modifier = Modifier.padding(15.dp))
        Button(
            onClick = {
                cart += buyingItem(name, price, quantity)
                writeBuyingJsonToFile(context, cart, "buying.json")
                navController.navigate("mainpage")
                Toast.makeText(context, "Item added to cart!", Toast.LENGTH_SHORT)
            },
            modifier = Modifier
                .size(width = 150.dp, height = 50.dp),
            content = {
                Text(text = "Add to Cart")
            }
        )
    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0x20FFFFFF)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contact(navController: NavController){
    Scaffold(
        topBar = { TopAppBar(
            title = {},
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
                .fillMaxSize()) {
                Spacer(modifier = Modifier.height(200.dp))
                Text("Contact Us", fontSize = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp))
                Text("Phone number: XXXXXXXX", fontSize = 20.sp, modifier = Modifier.padding(2.dp))
                Text("Email: csppa@nushigh.edu.sg", fontSize = 20.sp, modifier = Modifier.padding(2.dp))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ContactPreview(){
    PPA_SEM1Theme {
        Contact(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagepreview() {
    PPA_SEM1Theme {
        val navController = rememberNavController()
        MainPage( navController, "cool cs kids")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagepreview() {
    PPA_SEM1Theme {
        val navController = rememberNavController()
        LoginPage(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentPagepreview() {
    PPA_SEM1Theme {
        val navController = rememberNavController()
        PaymentPage(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPagepreview() {
    PPA_SEM1Theme {
        val name:String ="shorts"
        val price:Double = 0.0
        val id:Int=0
        val navController = rememberNavController()
        ItemPage(Modifier.padding(12.dp), navController, item, name,price,id)
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

@Preview(showBackground = true)
@Composable
fun Infopreview() {
    PPA_SEM1Theme {
        val navController = rememberNavController()
        Info(navController)
    }
}


@Preview(showBackground = true)
@Composable
fun SignUppreview() {
    PPA_SEM1Theme {
        val navController = rememberNavController()
        SignUpPage(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingCartpreview() {
    PPA_SEM1Theme {
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