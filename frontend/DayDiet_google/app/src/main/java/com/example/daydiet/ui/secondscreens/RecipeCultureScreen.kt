package com.example.daydiet.ui.secondscreens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.daydiet.R
import com.example.daydiet.service.StringUtil
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun RecipeCultureScreen(
    recipeid: Int,
    recipe: RecipeViewModel,
    navHostController: NavController,
    onBack: ()->Unit
) {
    recipe.findDetail(recipeid)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "菜谱文化",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55EEEEEE)),
            horizontalAlignment = Alignment.Start,
            content = {
                // 菜系图片
                item {
                    AsyncImage(
                        model = recipe.recipedetail.value.type_url,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
//                            .aspectRatio(4 / 3f)
                    )
                }

                // 菜品介绍 + 图片
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Surface(
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(0.5.dp, Color(0xFF6FAC46)),
                            elevation = 1.dp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxSize()
                            ){
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = recipe.recipedetail.value.recipe_desc,
                                    color = Color(0xFF555555),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                                )
                                AsyncImage(
                                    model = recipe.recipedetail.value.recipe_url,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .padding(15.dp)
                                        .fillMaxWidth()
                                        .aspectRatio(4 / 3f)
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF1FFC16),
                            modifier = Modifier
                                .padding(start = 15.dp)
                        ) {
                            Text(
                                text = "菜品文化简介",
                                color = Color(0xFF333333),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // 菜系介绍
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Surface(
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(0.5.dp, Color(0xFF6FAC46)),
                            elevation = 1.dp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxSize()
                            ){
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = recipe.recipedetail.value.type_desc,
                                    color = Color(0xFF555555),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp)
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF1FFC16),
                            modifier = Modifier
                                .padding(start = 15.dp)
                        ) {
                            Text(
                                text = "菜系简介",
                                color = Color(0xFF333333),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        )
    }
}