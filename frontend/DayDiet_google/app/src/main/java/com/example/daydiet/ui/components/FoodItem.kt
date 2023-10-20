package com.example.daydiet.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.daydiet.model.entity.FoodEntity
import com.example.daydiet.ui.navigation.Destinations

@Composable
fun FoodItem(food: FoodEntity, navHostController: NavHostController) {
    val constrainSet = ConstraintSet {
        val name = createRefFor("name")
        val calories = createRefFor("calories")
        val image = createRefFor("image")
        val divider = createRefFor("divider")

        constrain(image){
            start.linkTo(parent.start)
            centerVerticallyTo(parent)
            width = Dimension.value(115.5.dp)
        }

        constrain(name){
            start.linkTo(image.end, margin = 8.dp)
            top.linkTo(image.top, margin = 8.dp)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(calories){
            start.linkTo(name.start)
            bottom.linkTo(image.bottom, margin = 8.dp)
        }

        constrain(divider){
            top.linkTo(image.bottom, margin = 8.dp)
        }
    }

    ConstraintLayout(
        constrainSet,
        modifier = Modifier.fillMaxWidth().clickable { navHostController.navigate(Destinations.ArticalDetail.route) },
    ) {

        AsyncImage(
            model = food.imageUrl, contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .layoutId("image")
                .aspectRatio(16/9f)
                .clip(RoundedCornerShape(8.dp))
        )

        Text(
            text = food.name,
            fontSize = 18.sp,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("name")
        )

        Text(
            text = food.calories,
            fontSize = 15.sp,
            color = Color(0xFF999999),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("calories")
        )

        Divider(
            modifier = Modifier.layoutId("divider")
        )
    }
}