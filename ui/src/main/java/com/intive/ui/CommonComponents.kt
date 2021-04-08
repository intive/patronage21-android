package com.intive.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TitleText(
    text: String,
    modifier: Modifier,
    style: TextStyle = MaterialTheme.typography.h5,
    color: Color = MaterialTheme.colors.secondary
){
    Text(
        text = text,
        style = style,
        color = color,
        modifier = modifier
    )
}

@Composable
fun SectionHeader(
    content: @Composable ()->Unit,
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .alpha(2f)
            .background(MaterialTheme.colors.secondaryVariant),
        verticalAlignment = Alignment.CenterVertically

    ){
        content()
    }
}

@Preview
@Composable
fun SectionHeaderPreview(){
    SectionHeader(){
        Row(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
                .wrapContentWidth(Alignment.Start)
        ) {
            Text(text = "Lista")
            Text(text = "5")
        }
        Row(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                .wrapContentWidth(Alignment.End)
        ) {

        }
    }
}

@Composable
fun TextSplited(modifier: Modifier = Modifier, text1: String, text2: String){
    Row(modifier = modifier) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )

        Text(
            modifier = Modifier
                .weight(1f)

                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),

            text = text2
        )
    }
}