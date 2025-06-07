package com.nicos.liveupdatenotification

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.nicos.liveupdatenotification.ui.theme.LiveUpdateNotificationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getFCMToken()
        setContent {
            LiveUpdateNotificationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainUi(
                        modifier = Modifier.padding(innerPadding),
                        fcmNotificationClicked = {
                            getFCMToken()
                        }
                    )
                }
            }
        }
    }


    fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM_TOKEN", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and use your token
            Log.d("FCM_TOKEN", "FCM Registration Token: $token")
        })
    }

}

@Composable
fun MainUi(modifier: Modifier = Modifier, fcmNotificationClicked: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // Add some padding around the column
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = fcmNotificationClicked
        ) {
            Text("Get FCM Token")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

            }
        ) {
            Text("Local Notification")
        }

        // You can add other UI elements here as needed
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    LiveUpdateNotificationTheme {
        MainUi(
            fcmNotificationClicked = {

            }
        )
    }
}