package com.example.galeria_imagenes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.galeria_imagenes.ui.theme.Galeria_imagenesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Galeria_imagenesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class ImageItem(
    val id: Int,
    val imageRes: Int,
    val title: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryApp() {
    val imageList = listOf(
        ImageItem(1, R.drawable.manzana, "Manzana"),
        ImageItem(2, R.drawable.kiwi, "Kiwi"),
        ImageItem(3, R.drawable.pera, "Pera")
    )

    var selectedTitle by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    LaunchedEffect(selectedTitle) {
        selectedTitle?.let {
            Toast.makeText(context, "Imagen seleccionada: $it", Toast.LENGTH_SHORT).show()
            selectedTitle = null
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Galería de Frutas") }
            )
        },
        content = { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(innerPadding)
            ) {
                items(imageList.size) { index ->
                    val imageItem = imageList[index]
                    GalleryItem(imageItem) { title ->
                        selectedTitle = title
                    }
                }
            }
        }
    )
}

@Composable
fun GalleryItem(imageItem: ImageItem, onImageClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentSize(Alignment.Center)
            .clickable { onImageClick(imageItem.title) }
    ) {
        Image(
            painter = painterResource(id = imageItem.imageRes),
            contentDescription = imageItem.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = imageItem.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryAppPreview() {
    Galeria_imagenesTheme {
        GalleryApp()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    GalleryApp()
}