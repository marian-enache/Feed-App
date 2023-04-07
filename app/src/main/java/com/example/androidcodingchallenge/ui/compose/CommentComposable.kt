package com.example.androidcodingchallenge.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.androidcodingchallenge.R
import com.example.androidcodingchallenge.data.models.CommentModel

@Preview
@Composable
fun CommentComposable(commentModel: CommentModel = mockComment) {
    Column(Modifier.padding(start = 40.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)) {
        Text(
            text = commentModel.name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(
                    colorResource(R.color.top_comments_background),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 12.dp, vertical = 2.dp)
        )
        Text(
            text = commentModel.body,
            fontSize = TextUnit(14f, TextUnitType.Sp),
            lineHeight = TextUnit(16f, TextUnitType.Sp),
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}

val mockComment = CommentModel(postId = 0, id = 0, name = "name", body = "body body")