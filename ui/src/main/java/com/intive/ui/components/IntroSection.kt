package com.intive.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun IntroSection(title: String, text: String) {
    TitleText(text = title, modifier = Modifier.padding(bottom = 24.dp))
    Paragraph(text = text, modifier = Modifier.padding(bottom = 24.dp))
}
