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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.nicos.liveupdatenotification.local_notification.LocalNotification
import com.nicos.liveupdatenotification.ui.theme.LiveUpdateNotificationTheme

class MainActivity : ComponentActivity() {

    private lateinit var localNotification: LocalNotification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        localNotification = LocalNotification(this)
        setContent {
            LiveUpdateNotificationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainUi(
                        modifier = Modifier.padding(innerPadding),
                        fcmNotificationClicked = {
                            getFCMToken()
                        },
                        localNotificationClicked = {
                            localNotification.showNotification()
                        }
                    )
                }
            }
        }
    }


    fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                if (BuildConfig.DEBUG) {
                    Log.w("FCM_TOKEN", "Fetching FCM registration token failed", task.exception)
                }
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and use your token
            if (BuildConfig.DEBUG) {
                Log.d("FCM_TOKEN", "FCM Registration Token: $token")
            }
        })
    }
}

@Composable
fun MainUi(
    modifier: Modifier = Modifier,
    fcmNotificationClicked: () -> Unit,
    localNotificationClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = fcmNotificationClicked
        ) {
            Text(stringResource(R.string.get_fcm_token))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = localNotificationClicked
        ) {
            Text(stringResource(R.string.local_notification))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    LiveUpdateNotificationTheme {
        MainUi(
            fcmNotificationClicked = {},
            localNotificationClicked = {}
        )
    }
}