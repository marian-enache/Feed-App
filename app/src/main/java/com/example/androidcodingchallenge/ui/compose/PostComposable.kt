package com.example.androidcodingchallenge.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.androidcodingchallenge.R
import com.example.androidcodingchallenge.data.models.PostModel

@Preview
@Composable
fun PostComposable(
    post: PostModel = mockPost,
    showCheckBox: Boolean = true,
    onPostClicked: ((PostModel) -> Unit)? = null,
    onPostMarked: ((PostModel, Boolean) -> Unit)? = null,
    ) {
    Row(Modifier.padding(8.dp)
        .clickable { onPostClicked?.invoke(post) }) {
//        val checkedState = remember { mutableStateOf(post.isFavorite) }
        if (showCheckBox) {
            Checkbox(
                checked = post.isFavorite,
                colors = CheckboxDefaults.colors(colorResource(id = R.color.orangeLight)),
                modifier = Modifier
                    .align(CenterVertically)
                    .wrapContentHeight()
                    .wrapContentWidth(),
                onCheckedChange = {
//                checkedState.value = it
                    onPostMarked?.invoke(post, it)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
        } else {
            Spacer(modifier = Modifier.width(32.dp))
        }
        Column {
            Text(
                text = post.title,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.title_text_color),
            )

            Text(
                text = post.body,
                fontSize = TextUnit(14f, TextUnitType.Sp),
                lineHeight = TextUnit(16f, TextUnitType.Sp),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

val mockPost = PostModel(
    postId = 0,
    title = "name",
    body = "text = post.body,\n" + "                fontSize = TextUnit(14f, TextUnitType.Sp),\n" + "                lineHeight = TextUnit(16f, TextUnitType.Sp),\n" + "                modifier = Modifier"
)