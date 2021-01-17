package com.mjmanaog.rickandmorty.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.mjmanaog.rickandmorty.network.model.Characters
import dev.chrisbanes.accompanist.coil.CoilImage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MainViewModel()
        viewModel.getCharacters({
            setContent {
                MainScreen(viewModel)
            }
        },{})

    }

}
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val characters = viewModel.characterList.observeAsState("")
    Scaffold(topBar = { AppBar(title = "Rick and Morty", icon = Icons.Default.ExitToApp){} }){
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn{
                items(characters.value as List<Characters.Character>){ character->
                    CharacterCard(character = character, clickAction = { /*TODO*/ })
                }
            }

        }
    }
}

@Composable
fun CharacterCard(character: Characters.Character, clickAction: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .clickable(onClick = clickAction),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            CharacterImg(character.image,  72.dp)
            CharacterContent(character.name, character.status, Alignment.Start)
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
        Text(text = name, style = MaterialTheme.typography.h5)
        Providers(AmbientContentAlpha provides (ContentAlpha.medium)) {
            Text(
                text = status,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}
@Composable
fun AppBar(title: String, icon: ImageVector,iconClickAction: () -> Unit){
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

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    RickAndMortyTheme {
//        MainScreen()
//    }
//}
