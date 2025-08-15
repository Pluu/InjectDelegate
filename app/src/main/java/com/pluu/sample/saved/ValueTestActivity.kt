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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pluu.sample.saved.model.UserDto
import com.pluu.sample.saved.ui.theme.SavedSampleTheme
import com.pluu.sample.saved.utils.savedInject
import com.pluu.sample.saved.utils.savedInjectNonNull
import com.pluu.utils.startActivity
import timber.log.Timber

class ValueTestActivity : ComponentActivity() {
    private val requiredUser by savedInjectNonNull<UserDto>()
    private val requiredUserLambda by savedInjectNonNull<UserDto> {
        UserDto(id = 2, name = "lambda")
    }

    private val optionUser by savedInject<UserDto>()
    private val optionUserLambda by savedInject<UserDto> {
        UserDto(id = 3, name = "option lambda")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("Before")

        enableEdgeToEdge()
        setContent {
            SavedSampleTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Sample(
                        title = "Required",
                        value = "$requiredUser"
                    )
                    Sample(
                        title = "Required Lambda (default)",
                        value = "$requiredUserLambda"
                    )
                    Sample(
                        title = "Optional",
                        value = "$optionUser"
                    )
                    Sample(
                        title = "Optional Lambda (default)",
                        value = "$optionUserLambda"
                    )
                }
            }
        }
    }

    @Composable
    private fun Sample(title: String, value: String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = value,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity<ValueTestActivity>(
                "requiredUser" to UserDto(id = 1, name = "required")
            )
        }
    }
}