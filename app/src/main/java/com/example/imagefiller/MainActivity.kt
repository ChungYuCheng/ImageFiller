package com.example.imagefiller

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imagefiller.ui.theme.ImageFillerTheme
import com.google.accompanist.glide.rememberGlidePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageFillerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier) {
                    Column {
                        Demo(true, 1)
                        Spacer(modifier = Modifier.height(5.dp))
                        Demo(true, 2)
                        Spacer(modifier = Modifier.height(5.dp))
                        Demo(true, 3)
                        Spacer(modifier = Modifier.height(5.dp))
                        Demo(false)
                    }
                }
            }
        }
    }
}

@Composable
fun Demo(isNotFull: Boolean = false, imgCount: Int = 0) {

    val eachImg = with(LocalDensity.current) { (54 + 20).dp.toPx() } // 每張圖大小
    var blockSize by remember { mutableStateOf(0) } // 整個row
    val space = with(LocalDensity.current) { 72.dp.toPx() } // 預留空間

    val maxImgNum = remember {
        derivedStateOf { if (isNotFull) imgCount else ((blockSize - space) / eachImg).toInt() }
    }
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.LightGray)
            .padding(3.dp)
            .onSizeChanged { blockSize = it.width }
    ) {

        for (i in 1..maxImgNum.value) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
            ) {
                val painter =
                    rememberGlidePainter(
                        request = "https://doqvf81n9htmm.cloudfront.net/data/crop_article/135361/%E6%88%AA%E5%9C%96%202022-10-08%2020.04.53.jpg_1140x855.jpg",
                        requestBuilder = { centerCrop() }
                    )
                Image(
                    painter = painter,
                    contentDescription = "media",
                    modifier = Modifier.size(54.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(3.dp)) {
            Text(text = if (isNotFull && maxImgNum.value == 1) "商品補貨了，數量有限，\n立即前往選購！" else "共40件商品",
                modifier = if (isNotFull) Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 5.dp) else Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImageFillerTheme {
        Demo()
    }
}