package com.pluu.sample.saved

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pluu.sample.saved.model.UserDto
import com.pluu.sample.saved.ui.theme.SavedSampleTheme
import com.pluu.sample.saved.utils.savedInject
import com.pluu.utils.startActivity
import timber.log.Timber

class SecondActivity : ComponentActivity() {
    private lateinit var beforeUser: UserDto
    private var user by savedInject {
        UserDto(id = 100, name = "Default User")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("Before UserDto: %s", user)
        beforeUser = user

        if (savedInstanceState == null) {
            Timber.d("Update new data")
            user = UserDto(id = 200, name = "new user")
        }

        enableEdgeToEdge()
        setContent {
            SavedSampleTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Before UserDto\n$beforeUser",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "After UserDto\n$user",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity<SecondActivity>(
                "user" to UserDto(id = 1, name = "User")
            )
        }
    }
}