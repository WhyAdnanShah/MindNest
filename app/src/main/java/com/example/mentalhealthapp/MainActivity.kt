package com.example.mentalhealthapp


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.mentalhealthapp.navigation.Home
import com.example.mentalhealthapp.ui.theme.MentalHealthAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentalHealthAppTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.safeDrawing.asPaddingValues())
                )
                { innerPadding ->
                    LoginPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var context = LocalContext.current

    val loginStatus = remember {  context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE) }
    val isLoggedIn = loginStatus.getBoolean("LoginStatus", false)

    val guestStatus = remember { context.getSharedPreferences("GuestStatus", Context.MODE_PRIVATE) }
    val isGuest = guestStatus.getBoolean("GuestStatus", false)

    if (isGuest){
        val isguest = Intent(context, Home::class.java)
        context.startActivity(isguest)
        (context as? MainActivity)?.finish()
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isCardVisible by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Skip Login
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 0.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            SkipLogin(
                context = context,
                onSkipClick = { isCardVisible = true },
            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        Text(text = "Login to MindNest",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.height(80.dp))

        //Login Fields
        LoginField(
            value = username,
            onValueChange = { username = it },
            Lable  = "Username",
            PlaceHolder = "Enter Username"
        )
        LoginField(
            value = password,
            onValueChange = { password = it },
            Lable  = "Password",
            PlaceHolder = "Enter Password"
        )
        Spacer(Modifier.height(30.dp))

        //Login Button
        LoginButton(
            context = context,
            username = username,
            password = password
        )
        Spacer(Modifier.height(20.dp))
        Text(text = "or",
            fontSize = 20.sp)
        Spacer(Modifier.height(20.dp))

        //Login With Google
        Card (Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(16.dp, 0.dp, 16.dp, 0.dp),
            elevation = CardDefaults.cardElevation(10.dp)
        )
        {
            LoginWithGoogle(
                context = context
            )
        }

        //Signup
        RegisterPrompt()

        if (isCardVisible) {
            GuestCardDialog(
                onDismiss = { isCardVisible = false }
            )
        }
    }

}

@Composable
fun GuestCardDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        val context = LocalContext.current
        Card(
            modifier = Modifier.size(300.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GuestDialogContent()
                GuestDialogAction(onDismiss, context = context)
            }
        }
    }
}

//Dialog Titles
@Composable
fun GuestDialogContent() {
    Text("Login as a Guest", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    Text("*Your data will be saved locally and won't sync across devices.\n\nAre you sure you want to continue?", fontSize = 15.sp, fontWeight = FontWeight.Light, color = colorResource(R.color.indian_red))
}

@Composable
fun GuestDialogAction(onDismiss: () -> Unit, context: Context) {
    Row (modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    )
    {
        DialogButton(
            text = "Back",
            onClick = onDismiss,
            color = colorResource(R.color.slate_gray)
        )
        DialogButton(
            text = "Continue",
            onClick = {
                val guestStatus = context.getSharedPreferences("GuestStatus", Context.MODE_PRIVATE)
                guestStatus.edit().putBoolean("GuestStatus", true).apply()

                context.startActivity(Intent(context, Home::class.java))
                onDismiss()
                (context as? MainActivity)?.finish()
            },
            color = colorResource(R.color.blue_sky)
        )

    }
}

//Dialog Button
@Composable
fun DialogButton(text: String, onClick: () -> Unit, color: Color) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(120.dp, 45.dp),
        colors = ButtonDefaults.buttonColors(color),
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.buttonElevation(10.dp)
    )
    {
        Text(text)
    }
}

//Skip Login as a Guest
@Composable
fun SkipLogin(context: Context, onSkipClick: () -> Unit) {
    Text(modifier = Modifier
        .clickable {
            onSkipClick()
        },
        text = "Skip Login",
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Monospace,
        fontStyle = FontStyle.Italic)

    Image(painter = painterResource(R.drawable.arrow)
        ,contentDescription = null,
        modifier = Modifier.size(13.dp))
}



//Login Fields
@Composable
fun LoginField(Lable: String, PlaceHolder: String, value: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(Lable) },
        placeholder = {Text(PlaceHolder) } ,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 0.dp),
        shape = RoundedCornerShape(20.dp)
    )
    Spacer(modifier = Modifier.height(25.dp))
}

//Login Btn
@Composable
fun LoginButton(context: Context, username: String, password: String) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(16.dp, 0.dp, 16.dp, 0.dp),
        enabled = true,
        colors = ButtonDefaults.buttonColors(colorResource(R.color.wheat)),
        onClick = {
            if (username == "" || password == ""){
                Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "User $username Pass : $password", Toast.LENGTH_SHORT).show()

                val loginStatus = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE)
                loginStatus.edit().putBoolean("LoginStatus", true).apply()

            }
        },
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.buttonElevation(10.dp)
    ){
        Text(text = "Login")
    }
}

//Login With Google
@Composable
fun LoginWithGoogle(context: Context) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
            },
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(painter = painterResource(R.drawable.google)
            ,contentDescription = null,
            modifier = Modifier.size(30.dp))
        Spacer(Modifier.width(20.dp))
        Text(
            text = "Login with Google",
            fontSize = 17.sp
        )

    }

}

//Register Prompt
@Composable
fun RegisterPrompt() {

}

