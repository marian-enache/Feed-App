package com.example.androidcodingchallenge.ui.compose

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidcodingchallenge.R


@Preview
@Composable
fun ProgressBarComposable() {
    MaterialTheme {
        CircularProgressIndicator(
            color = colorResource(id = R.color.teal_200),
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
        )
    }
}