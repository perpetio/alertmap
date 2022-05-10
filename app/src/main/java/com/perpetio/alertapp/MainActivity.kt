package com.perpetio.alertapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.perpetio.alertapp.ui.theme.AlertAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlertAppTheme {
                AppUi()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlertAppTheme {
        AppUi()
    }
}

@Composable
fun AppUi() {

    val image = ImageBitmap.imageResource(R.drawable.ukraine)
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawCircle(
            SolidColor(Color.LightGray),
            size.width / 2,
            style = Stroke(35f)
        )
        drawImage(
            image = image,
            topLeft = Offset(x = 0f, y = 0f)
        )
        drawImage(
            image = image,
            topLeft = Offset(x = 5f, y = 1f)
        )
    }
}