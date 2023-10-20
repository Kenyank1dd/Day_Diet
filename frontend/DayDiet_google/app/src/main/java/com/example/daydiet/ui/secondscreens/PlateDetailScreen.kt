package com.example.daydiet.ui.secondscreens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.daydiet.R
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.accompanist.insets.statusBarsPadding
import okhttp3.internal.wait

@Composable
fun PlateDetailScreen(
    recipeViewModel: RecipeViewModel,
    navHostController: NavHostController,
    onBack: ()->Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "生成结果",
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .clickable { onBack() }
                            .padding(8.dp)
                    )
                },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) {
        if(recipeViewModel.net_bad.value == true) {
            recipeViewModel.net_bad.value = false
            Toast.makeText(LocalContext.current, "服务器发生错误", Toast.LENGTH_SHORT).show()
        }

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.creativeplating),
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(95.dp)
                    .padding(top = 10.dp, bottom = 10.dp),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
            Text(text = recipeViewModel.recipename.value, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFFFFF),
                                Color(0x3333CCFF)
                            )
                        )
                    )
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "http://124.221.166.194:8080" + recipeViewModel.createurl.value,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 15.dp)
//                        .fillMaxSize()
                        .aspectRatio(1/1f)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}