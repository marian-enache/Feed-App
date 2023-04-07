package com.example.androidcodingchallenge.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.androidcodingchallenge.R
import com.example.androidcodingchallenge.data.models.PhotoModel

@Preview
@Composable
fun ImageComposable(photo: PhotoModel = mockPhoto) {
    Column(Modifier.padding(8.dp)) {
        Text(
            text = photo.title,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.title_text_color),
            modifier = Modifier
                .padding(start = 32.dp)
        )

        AsyncImage(
            model = photo.url,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            fallback = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(start = 56.dp, end = 56.dp, top = 8.dp)
                .wrapContentHeight()
                .wrapContentWidth()
        )
    }
}


val mockPhoto = PhotoModel(
    id = 0,
    title = "name",
    url = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
)