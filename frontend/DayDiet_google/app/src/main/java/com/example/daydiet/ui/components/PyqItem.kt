package com.example.daydiet.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.daydiet.R
import com.example.daydiet.model.entity.Pyqmessage
import com.example.daydiet.ui.navigation.Destinations
import kotlin.math.max

@Composable
fun PyqItem(pyq: Pyqmessage) {
    
    Column(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 8.dp)
            .fillMaxWidth()
    ) {
        
        Row() {
//            Icon(
//                imageVector = Icons.Default.Person,
//                contentDescription = null,
//                Modifier.size(40.dp),
//                tint = Color(0xFF00C864)
//            )
            val randoms = (0..10).random()
            var iconcolor = Color(0x77A86AD7)
            if(randoms % 3 == 1){
                iconcolor = Color(0x77234AF7)
            }
            else if(randoms % 3 == 2){
                iconcolor = Color(0x77A8D76A)
            }
            Icon(
                painter = painterResource(id = R.drawable.userpicture),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .border(1.dp, iconcolor, CircleShape),
                tint = iconcolor
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column() {
                Text(
                    text = pyq.user,
                    color = Color(0xFF333333),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = pyq.timestamp,
                    color = Color(0xFF999999),
                    fontSize = 12.sp
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = pyq.msgcontent,
            color = Color(0xFF333333),
            fontSize = 16.sp,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.size(8.dp))
        AsyncImage(
            model = pyq.imageUrl, contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Row(
            modifier = Modifier.padding(top = 8.dp, end = 20.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Icon(
                painter = painterResource(id = R.drawable.praise),
                contentDescription = null,
                tint = Color(0xFF666666),
                modifier = Modifier.size(23.dp)
            )
        }
        Divider(
            color = Color(0xFFCCCCCC),
            modifier = Modifier.padding(8.dp)
        )
    }
    
}