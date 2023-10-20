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
import com.example.daydiet.model.entity.RecipeEntity
import com.example.daydiet.ui.navigation.Destinations

@Composable
fun RecipeItem(
    recipe: RecipeEntity,
    navHostController: NavHostController
) {

    val constrainSet = ConstraintSet {
        val name = createRefFor("name")
        val material = createRefFor("material")
        val image = createRefFor("image")
        val divider = createRefFor("divider")

        constrain(image){
            start.linkTo(parent.start, margin = 8.dp)
            centerVerticallyTo(parent)
            width = Dimension.value(180.dp)
        }

        constrain(name){
            start.linkTo(image.end, margin = 8.dp)
            top.linkTo(image.top, margin = 8.dp)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(material){
            start.linkTo(name.start)
            bottom.linkTo(image.bottom, margin = 8.dp)
        }

        constrain(divider){
            top.linkTo(image.bottom, margin = 8.dp)
        }
    }

    ConstraintLayout(
        constrainSet,
        modifier = Modifier.fillMaxWidth().clickable {
            navHostController.navigate("${Destinations.RecipeDetailScreen.route}/${recipe.rec_id}")
        },
    ) {

        AsyncImage(
            model = recipe.rec_url, contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .layoutId("image")
                .aspectRatio(16/9f)
                .clip(RoundedCornerShape(8.dp))
        )

        Text(
            text = recipe.rec_name,
            fontSize = 18.sp,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("name")
        )

        Text(
            text = recipe.rec_main,
            fontSize = 13.sp,
            color = Color(0xFF999999),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("material")
        )


        Divider(
            modifier = Modifier.layoutId("divider")
        )
    }
}