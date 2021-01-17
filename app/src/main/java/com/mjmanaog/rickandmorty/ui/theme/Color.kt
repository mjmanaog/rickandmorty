package com.mjmanaog.rickandmorty.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val dark200 = Color(0xFF2e2e2e)
val dark500 = Color(0xFF212121)
val dark700 = Color(0xFF1f1f1f)
val teal200 = Color(0xFF03DAC5)

val aliveGreen = Color(0xFF3ce84b)
val deadRed = Color(0xFFe84d3c)

@Composable
val Colors.lightGreen: Color
    get()=  aliveGreen

@Composable
val Colors.lightRed: Color
    get()=  deadRed