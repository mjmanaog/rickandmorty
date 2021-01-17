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


/**
 * MainActivity - this is the screen where the rick and morty list will populate
 * @ExperimentalFoundationApi - is for the LazyVerticalGrid
 */
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

/**
 * MainScreen method - will be the one whose responsible to populate our character list into grid
 * @Composable - to mark our method as composable
 * @param viewModel to pass the initialized viewmodel
 */
@ExperimentalFoundationApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val characters = viewModel.characterList.observeAsState("")
    Scaffold(topBar = { AppBar(title = "Rick and Morty", icon = Icons.Default.ExitToApp) {} }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            /**
             * LazyVerticalGrid - still experimental. GridCells.Fixed will determine how many grids vertically
             */
            LazyVerticalGrid(cells = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp)) {
                items(characters.value as List<Characters.Character>) { character ->
                    CharacterCard(character = character, onClicked = { /*TODO*/ })
                }
            }

        }
    }
}

/**
 * CharacterCard method is responsible to create the main container of the card that will composed of details
 * @param character to pass our Character dataclass.
 * @param onClicked this is a lambda expression to trigger something when this card was clicked
 * @see Characters
 */
@Composable
fun CharacterCard(character: Characters.Character, onClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(200.dp)
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .clickable(onClick = onClicked),
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

/**
 * CharacterImg method is responsible to create the image layout in our card
 * @param imgURL - we need to pass the url of the image to display it
 * @param imgSize - to make the size dynamic, we declare it as a param so we can reuse this on other screen(s)
 */
@Composable
fun CharacterImg(imgURL: String, imgSize: Dp) {
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
            data = imgURL,
            requestBuilder = { transformations(CircleCropTransformation()) })
    }
}

/**
 * CharacterContent method is reponsible for creating the details of the character
 * @param name of the character
 * @param status of the character (Alive, Dead, Unknown)
 * @param alignment to make the alignment dynamic we add it to the parameter
 */
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

/**
 * AppBar method is responsible to create the layout of the Appbar /Toolbar
 * To make the appbar reusable this is recommendent
 * @param title of the appbar
 * @param icon of the appbar (left)
 * @param onIconClicked is a lambda function to trigger an event incase the icon was clicked
 */
@Composable
fun AppBar(title: String, icon: ImageVector, onIconClicked: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            Icon(
                icon,
                Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(onClick = { onIconClicked.invoke() })
            )
        },
        title = { Text(text = title) }
    )
}

/**
 * DefaultPreview Method is used to make the layout viewable ->
 */
@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RickAndMortyTheme {
        MainScreen(viewModel = MainViewModel())
    }
}
