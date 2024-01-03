package com.example.digimon

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.compose.rememberAsyncImagePainter
import com.example.digimon.data.model.Digimon
import com.example.digimon.ui.theme.DigimonTheme
import com.example.digimon.ui.viewModel.DigimonViewModel

class MainActivity : ComponentActivity() {
    private val digimonViewModel: DigimonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DigimonTheme {
                RecyclerView(digimonViewModel)
            }
        }
    }
}

@Composable
fun ListItem(digimon: Digimon){
    Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp )) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            Row{
                Image(
                    painter = rememberAsyncImagePainter(digimon.img),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = digimon.name, style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ))
                    Text(text = digimon.level, style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ))
                }
            }
        }
    }
}

@Composable
fun RecyclerView(digimonViewModel: DigimonViewModel){
    LaunchedEffect(Unit, block = {
        digimonViewModel.getDigimonList()
    })
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp )){
        items(items = digimonViewModel.digimonList){ digimon ->
            ListItem(digimon)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DigimonTheme {
        val digimon = Digimon("Koromon","https://digimon.shadowsmith.com/img/koromon.jpg","In Training")

        ListItem(digimon)
    }
}