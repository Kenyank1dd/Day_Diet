package com.example.daydiet.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.daydiet.compositionLocal.LocalUserViewModel
//import com.example.daydiet.ui.camera.CameraScreen
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.ui.screens.ArticalDetailScreen
import com.example.daydiet.ui.screens.LoginScreen
import com.example.daydiet.ui.screens.MainFrame
import com.example.daydiet.ui.screens.RegisterScreen
import com.example.daydiet.ui.secondscreens.*
import com.example.daydiet.viewmodel.DetectResultViewModel
import com.example.daydiet.viewmodel.RecipeViewModel
import com.example.daydiet.viewmodel.UserViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


// 导航控制器
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NaviHostApp() {

    val navController = rememberAnimatedNavController()
    val scaffoldState = rememberScaffoldState()
    val drviewModel: DetectResultViewModel = viewModel()
    val recipeviewModel: RecipeViewModel = viewModel()

    CompositionLocalProvider(LocalUserViewModel provides UserViewModel(LocalContext.current)) {

        val userViewModel = LocalUserViewModel.current

        Log.d("+++666777", userViewModel.net_ok.toString())

        if(userViewModel.net_ok == false){
            userViewModel.setnet_ok(true)
            Toast.makeText(LocalContext.current, "服务器发生错误", Toast.LENGTH_SHORT).show()
        }

        AnimatedNavHost(
            navController = navController,
//            startDestination = Destinations.HomeFrame.route
            startDestination = Destinations.LoginScreen.route
        ){
            // 首页大框架
            composable(
                Destinations.HomeFrame.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                MainFrame(
                    recipeviewModel = recipeviewModel,
                    drviewmodel = drviewModel,
                    scaffoldState = scaffoldState,
                    navHostController = navController,
                )
            }

            // 登录界面
            composable(
                Destinations.LoginScreen.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                }
            ){
                LoginScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 百度识别结果界面
            composable(
                Destinations.BaiduResultScreen.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                }
            ){
                BaiduResultScreen(
                    userViewModel = userViewModel,
                    recipeviewModel = recipeviewModel,
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 注册界面
            composable(
                Destinations.RegisterScreen.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                }
            ){
                RegisterScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 文章详情页
            composable(
//            Destinations.ArticalDetail.route,
                "${Destinations.ArticalDetail.route}/{articalid}",
                arguments = listOf(navArgument("articalid" ){ type = NavType.IntType }),
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){ it ->
                it.arguments?.getInt("articalid")?.let {
                    ArticalDetailScreen(
                        it,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            // 我的收藏详情页
            composable(
                Destinations.MyFavorite.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                MyFavoriteScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 饮食目标详情页
            composable(
                Destinations.DietGoalScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                DietGoalScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 个人信息详情页
            composable(
                Destinations.PersonalInfoScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                PersonalInfoScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 关于DayDiet详情页
            composable(
                Destinations.AboutDayDietScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                AboutDayDietScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 对话机器人
            composable(
                Destinations.RobotScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                RobotScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 饮食目标详情页
            composable(
                Destinations.RecommendSetting.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                RecommendSetting(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 浏览历史详情页
            composable(
                Destinations.MyHistory.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                MyHistoryScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 搜索详情页
            composable(
                Destinations.SearchDetail.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                SearchDetailScreen(
                    recipeViewModel = recipeviewModel,
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 搜索结果页
            composable(
                Destinations.SearchResult.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                SearchResultScreen(
                    recipeViewModel = recipeviewModel,
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 排行详情页
            composable(
                Destinations.Rank.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                RankScreen(
                    recipeViewModel = recipeviewModel,
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        // 相机目标检测
        composable(
            Destinations.RecordCameraScreen.route,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(300)) },
            exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(300)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(300)) }
        ){
            RecordCameraScreen(
                navHostController = navController,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
            // 目标检测结果
            composable(
                Destinations.DetectResultScreen.route,
//            arguments = listOf(
//                navArgument("d-result"){
//                    type = NavType.StringArrayType
//                    nullable = true
//                }
//            ),
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
//            val FoodResult = it.arguments?.getStringArray("dresult")
//
//            if(FoodResult != null){
//                FoodResult.forEach {
//                    Log.d("ddddnavhostapppp", it)
//                }
//            }
//            else{
//                Log.d("ddddnavhostapp", "null")
//            }
//            val StringTest = it.arguments?.getString("dresult") ?: null
//            if (StringTest != null) {
//                Log.d("ddddnavhostapp",StringTest)
//            }
                DetectResultScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    },
                    drviewmodel =  drviewModel,
                    recipeViewModel = recipeviewModel
//                foodresult = FoodResult
                )
            }
            // 推荐结果详情
            composable(
                Destinations.RecommendScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                RecommendScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    },
                    recipeViewModel = recipeviewModel
                )
            }

            // 排行详情页
            composable(
                "${Destinations.RecipeDetailScreen.route}/{recipeid}",
                arguments = listOf(navArgument("recipeid" ){ type = NavType.IntType }),
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){ it ->
                it.arguments?.getInt("recipeid")?.let {
                    RecipeDetialScreen(
                        it,
                        recipe = recipeviewModel,
                        navHostController = navController,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            // 排行详情页
            composable(
                "${Destinations.RecipeCultureScreen.route}/{recipeid}",
                arguments = listOf(navArgument("recipeid" ){ type = NavType.IntType }),
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){ it ->
                it.arguments?.getInt("recipeid")?.let {
                    RecipeCultureScreen(
                        it,
                        recipe = recipeviewModel,
                        navHostController = navController,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            // 创意摆盘二级界面
            composable(
                Destinations.PlateScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                PlateScreen(
                    recipeViewModel = recipeviewModel,
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
            // 创意摆盘三级界面
            composable(
                Destinations.PlateDetailScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                PlateDetailScreen(
                    recipeViewModel = recipeviewModel,
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 黑暗料理二级界面
            composable(
                Destinations.DarkScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                DarkScreen(
                    recipeViewModel = recipeviewModel,
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
            // 黑暗料理三级界面
            composable(
                Destinations.DarkDetailScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                DarkDetailScreen(
                    recipeViewModel = recipeviewModel,
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 家庭成员详情页
            composable(
                Destinations.FamilyScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                FamilyScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 邀请详情页
            composable(
                Destinations.InvestScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                InvestScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 接收请求详情页
            composable(
                Destinations.RequestScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                RequestScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 近日饮食详情页
            composable(
                Destinations.RecentDietScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                RecentDietScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 喝水记录详情页
            composable(
                Destinations.DrinkScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                DrinkScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 体重记录详情页
            composable(
                Destinations.WeightScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                WeightScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 联系我们详情页
            composable(
                Destinations.CallUsScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                CallUsScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // 设置详情页
            composable(
                Destinations.SettingScreen.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)) }
            ){
                SettingScreen(
                    navHostController = navController,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
