package com.example.daydiet.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.daydiet.R
import com.example.daydiet.compositionLocal.LocalUserViewModel
import com.example.daydiet.ui.screens.*
import com.example.daydiet.model.entity.NavigationItem
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.DetectResultViewModel
import com.example.daydiet.viewmodel.RecipeViewModel
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainFrame(
    recipeviewModel: RecipeViewModel,
    drviewmodel: DetectResultViewModel,
    scaffoldState: ScaffoldState,
    navHostController: NavHostController,
//    onNavigateToLogin: () -> Unit = {}
) {

    val navigationItems = listOf(
        NavigationItem(title = "首页", icon = Icons.Filled.Home),
        NavigationItem(title = "记录", icon = Icons.Filled.AccountBox),
        NavigationItem(title = "社区", icon = Icons.Filled.Favorite),
        NavigationItem(title = "我的", icon = Icons.Filled.Person)
    )
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val userViewModel = LocalUserViewModel.current

    var judgelogin by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier=Modifier.navigationBarsPadding(),
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                navigationItems.forEachIndexed { index, navigationItem ->
                    if(index == 1){
                        BottomNavigationItem(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch(Dispatchers.Main) {
                                    pagerState.scrollToPage(index)
                                }
                            },
                            icon = {
                                Icon(imageVector = navigationItem.icon, contentDescription = null)
                            },
                            label = {
                                Text(text = navigationItem.title)
                            },
                            selectedContentColor = Color(0xFF00C864),
                            unselectedContentColor = Color(0xFF999999)
                        )
                    }
                    else{
                        BottomNavigationItem(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch(Dispatchers.Main) {
                                    pagerState.scrollToPage(index)
                                }
                            },
                            icon = {
                                Icon(imageVector = navigationItem.icon, contentDescription = null)
                            },
                            label = {
                                Text(text = navigationItem.title)
                            },
                            selectedContentColor = Color(0xFF00C864),
                            unselectedContentColor = Color(0xFF999999)
                        )
                    }
                }
            }
        }
    ) {
        HorizontalPager(
            count = navigationItems.size,
            state = pagerState,
            modifier = Modifier.padding(it)
        ) { index ->
            when(index){
                0-> HomeScreen(drviewmodel = drviewmodel, navHostController = navHostController)
                1-> {
                    RecordScreen(recipeViewModel = recipeviewModel, navHostController = navHostController)
                }
                2-> CommunityScreen(navHostController = navHostController)
                3-> {
                    AccountScreen(pagerState = pagerState, navHostController = navHostController)
                }
            }
        }
    }
}
