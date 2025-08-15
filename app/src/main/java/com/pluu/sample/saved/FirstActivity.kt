package com.pluu.sample.saved

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.pluu.sample.saved.ui.theme.SavedSampleTheme
import com.pluu.sample.utils.SampleLayout

class FirstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            SavedSampleTheme {
                SampleLayout(title = "Sample") {
                    ListItem(title = "Inject value") {
                        ValueTestActivity.start(context)
                    }
                    ListItem(title = "Inject variable") {
                        VariableTestActivity.start(context)
                    }
                }
            }
        }
    }
}