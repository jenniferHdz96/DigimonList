package com.example.digimon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.compose.rememberAsyncImagePainter
import com.example.digimon.data.model.Digimon
import com.example.digimon.ui.theme.DarkBlue
import com.example.digimon.ui.theme.DigimonTheme
import com.example.digimon.ui.theme.LightBlue
import com.example.digimon.ui.viewModel.DigimonViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val digimonViewModel: DigimonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                digimonViewModel.loading.value
            }
        }
        setContent {
            val textState = remember { mutableStateOf(TextFieldValue("")) }

            DigimonTheme {
                Surface(color = LightBlue, modifier = Modifier.fillMaxHeight(1f)) {
                    Column{
                        SearchView(textState)
                        RecyclerView(digimonViewModel, textState)
                    }
                }

            }
        }
    }
}

@Composable
fun ListItem(digimon: Digimon){
    Surface(color = DarkBlue, shape = RoundedCornerShape(8.dp), modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp )
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            ) {
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
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    ))
                    Text(text = digimon.level, style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    ))
                }
            }
        }
    }
}

@Composable
fun RecyclerView(digimonViewModel: DigimonViewModel, state: MutableState<TextFieldValue>){
    val context = LocalContext.current
    
    LaunchedEffect(Unit, block = {
        digimonViewModel.getDigimonList()
    })
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp )){
        val searchedText = state.value.text
        val originalList = digimonViewModel.digimonList

        val filteredDigimon: List<Digimon> = if (searchedText.isEmpty()) originalList
            else {
                val resultList = ArrayList<Digimon>()
                for (item in originalList){
                    if (item.name.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(item)
                    }
                }

                resultList
            }

        items(items = filteredDigimon){ digimon ->
            ListItem(digimon)
        }
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>){
    Surface(color = DarkBlue, shape = RoundedCornerShape(8.dp), modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        TextField(value = state.value, onValueChange = { value ->
            state.value = value
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp),
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")){
                    IconButton(
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp),
                        onClick = {
                            state.value = TextFieldValue("")
                    }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = ""
                        )
                    }
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = DarkBlue,
                unfocusedContainerColor = DarkBlue,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedLeadingIconColor = Color.White,
                unfocusedLeadingIconColor = Color.White,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DigimonTheme {
        val digimon = Digimon("Koromon","https://digimon.shadowsmith.com/img/koromon.jpg","In Training")
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        
        Column {
            SearchView(textState)
            ListItem(digimon)
        }
    }
}