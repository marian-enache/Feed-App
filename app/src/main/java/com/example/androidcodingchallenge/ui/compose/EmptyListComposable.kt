package com.example.androidcodingchallenge.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.androidcodingchallenge.R


@Preview
@Composable
fun EmptyListComposable() {
    MaterialTheme {
        Text(
            text = stringResource(id = R.string.error_message_feed_items),
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = TextUnit(18f, TextUnitType.Sp),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxSize()
                .wrapContentHeight()
                .wrapContentWidth()
        )
    }
}