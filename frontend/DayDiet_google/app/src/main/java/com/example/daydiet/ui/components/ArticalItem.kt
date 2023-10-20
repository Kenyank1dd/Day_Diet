package com.example.daydiet.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.daydiet.model.entity.ArticalEntity
import com.example.daydiet.ui.navigation.Destinations
import com.example.daydiet.viewmodel.ArticalViewModel
import kotlinx.coroutines.launch

@Composable
fun ArticalItem(
    artical: ArticalEntity,
    navHostController: NavHostController,
    articalViewModel: ArticalViewModel = viewModel()
) {

    val constrainSet = ConstraintSet {
        val title = createRefFor("title")
        val cover = createRefFor("cover")
        val from = createRefFor("from")
        val date = createRefFor("date")
        val divider = createRefFor("divider")

        constrain(cover){
            start.linkTo(parent.start)
            centerVerticallyTo(parent)
            width = Dimension.value(160.dp)
        }

        constrain(title){
            start.linkTo(cover.end, margin = 8.dp)
            top.linkTo(cover.top)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(from){
            start.linkTo(title.start)
            bottom.linkTo(cover.bottom)
        }

        constrain(date){
            end.linkTo(parent.end, margin = 10.dp)
            bottom.linkTo(cover.bottom)
        }

        constrain(divider){
            top.linkTo(cover.bottom, margin = 8.dp)
        }
    }

    ConstraintLayout(
        constrainSet,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navHostController.navigate("${Destinations.ArticalDetail.route}/${artical.id}")
            },
    ) {

        AsyncImage(
            model = artical.imageUrl, contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .layoutId("cover")
                .aspectRatio(16/9f)
                .clip(RoundedCornerShape(8.dp))
        )

        Text(
            text = artical.title,
            fontSize = 18.sp,
            color = Color(0xFF111111),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("title")
        )

        Text(
            text = artical.from,
            fontSize = 13.sp,
            color = Color(0xFF999999),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("from")
        )

        Text(
            text = artical.date,
            fontSize = 13.sp,
            color = Color(0xFF999999),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("date")
        )

        Divider(
            modifier = Modifier.layoutId("divider")
        )
    }

}