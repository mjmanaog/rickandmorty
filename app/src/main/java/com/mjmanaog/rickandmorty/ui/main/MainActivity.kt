package com.mjmanaog.rickandmorty.ui.main

import android.R
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.transform.CircleCropTransformation
import com.mjmanaog.rickandmorty.network.model.Characters
import com.mjmanaog.rickandmorty.ui.theme.RickAndMortyTheme
import com.mjmanaog.rickandmorty.ui.theme.lightGreen
import com.mjmanaog.rickandmorty.ui.theme.lightRed
import dev.chrisbanes.accompanist.coil.CoilImage


@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window: Window = this.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black))
        val viewModel = MainViewModel()
        viewModel.getCharacters({
            setContent {
                RickAndMortyTheme {
                    MainScreen(viewModel)
                }
            }
        }, {})

    }

}
@ExperimentalFoundationApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val characters = viewModel.characterList.observeAsState("")
    Scaffold(topBar = { AppBar(title = "Rick and Morty", icon = Icons.Default.ExitToApp) {} }){
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(cells = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp)){
                items(characters.value as List<Characters.Character>){ character->
                    CharacterCard(character = character, clickAction = { /*TODO*/ })
                }
            }
//            LazyColumn{
//                items(characters.value as List<Characters.Character>){ character->
//                    CharacterCard(character = character, clickAction = { /*TODO*/ })
//                }
//            }

        }
    }
}

@Composable
fun CharacterCard(character: Characters.Character, clickAction: () -> Unit){
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(200.dp)
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .clickable(onClick = clickAction),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CharacterImg(character.image, 72.dp)
            CharacterContent(character.name, character.status, Alignment.CenterHorizontally)
        }
    }
}


@Composable
fun CharacterImg(drawableId: String, imgSize: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color = Color.White
        ),
        modifier = Modifier
            .padding(16.dp)
            .size(imgSize),
        elevation = 4.dp
    ) {
        CoilImage(
            data = drawableId,
            requestBuilder = { transformations(CircleCropTransformation()) })
    }
}


@Composable
fun CharacterContent(name: String, status: String, alignment: Alignment.Horizontal) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = alignment
    ) {
        Text(text = name, style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
        Text(
            text = status,
            style = MaterialTheme.typography.body2,
            color = when (status) {
                "Alive" -> {
                    MaterialTheme.colors.lightGreen
                }
                "Dead" -> {
                    MaterialTheme.colors.lightRed
                }
                else -> {
                    MaterialTheme.colors.primary
                }
            }
        )
    }
}
@Composable
fun AppBar(title: String, icon: ImageVector, iconClickAction: () -> Unit){
    TopAppBar(
        navigationIcon = {
            Icon(
                icon,
                Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(onClick = { iconClickAction.invoke() })
            )
        },
        title = { Text(text = title) }
    )
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RickAndMortyTheme {
        MainScreen(viewModel = MainViewModel())
    }
}
