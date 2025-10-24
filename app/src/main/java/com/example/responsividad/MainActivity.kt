package com.example.responsividad

import android.os.Bundle
import android.util.Log // <-- 1. IMPORT NECESARIO AÑADIDO
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.responsividad.ui.theme.ResponsividadTheme
import java.util.UUID
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.ExperimentalFoundationApi

data class Fruta(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val descripcion: String
)

@Composable
fun FrutaItem(fruta: Fruta, modifier: Modifier = Modifier) {
    // 2. LOG AÑADIDO (El más importante)
    // Se llamará por CADA ítem de la LazyColumn que se componga.
    Log.d("MiAppTabs", "    Componiendo FrutaItem: ${fruta.nombre}")

    Column(modifier.padding(vertical = 8.dp)) {
        Text(text = fruta.nombre, style = MaterialTheme.typography.titleMedium)
        Text(text = fruta.descripcion, style = MaterialTheme.typography.bodyMedium)
    }
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResponsividadTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppConTabs(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppConTabs(modifier: Modifier = Modifier) {

    val pagerState = rememberPagerState(pageCount = { 2 })
    val titulosTabs = listOf("Inicio", "Lista de Frutas")
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxSize()) {

        TabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            titulosTabs.forEachIndexed { index, titulo ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = titulo) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            when (pageIndex) {
                0 -> PantallaInicio()
                1 -> PantallaListaDeFrutas()
            }
        }
    }
}

@Composable
fun PantallaInicio() {
    // 3. LOG AÑADIDO
    // Se llamará cuando la Pestaña 1 se componga.
    Log.d("MiAppTabs", "--- Componiendo PantallaInicio ---")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Bienvenido!", style = MaterialTheme.typography.headlineMedium)
        Text("Desliza o pulsa 'Lista de Frutas' para ver la lista.", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun PantallaListaDeFrutas() {
    // 4. LOG AÑADIDO
    // Se llamará cuando la Pestaña 2 se componga.
    Log.d("MiAppTabs", ">>> Componiendo PantallaListaDeFrutas (LazyColumn)")

    val listaDeFrutas = remember {
        listOf(
            Fruta(nombre = "Manzana", descripcion = "Roja y dulce"),
            Fruta(nombre = "Banana", descripcion = "Amarilla y suave"),
            Fruta(nombre = "Naranja", descripcion = "Llena de vitamina C"),
            Fruta(nombre = "Uva", descripcion = "Pequeña y jugosa"),
            Fruta(nombre = "Kiwi", descripcion = "Verde y ácido"),
            Fruta(nombre = "Mango", descripcion = "Tropical"),
            Fruta(nombre = "Pera", descripcion = "Verde y arenosa"),
            Fruta(nombre = "Piña", descripcion = "Con corona"),
            Fruta(nombre = "Fresa", descripcion = "Roja y con semillas"),
            Fruta(nombre = "Sandía", descripcion = "Grande y refrescante"),
            Fruta(nombre = "Melón", descripcion = "Naranja y dulce"),
            Fruta(nombre = "Limón", descripcion = "Amarillo y agrio"),
            Fruta(nombre = "Cereza", descripcion = "Roja y pequeña"),
            Fruta(nombre = "Papaya", descripcion = "Naranja y tropical"),
            Fruta(nombre = "Coco", descripcion = "Marrón y duro"),
            Fruta(nombre = "Arándano", descripcion = "Azul y antioxidante"),
            Fruta(nombre = "Frambuesa", descripcion = "Roja y delicada"),
            Fruta(nombre = "Maracuyá", descripcion = "Fruta de la pasión")
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(items = listaDeFrutas, key = { it.id }) { fruta ->
            FrutaItem(
                fruta = fruta,
                modifier = Modifier.fillMaxWidth()
            )
            Divider()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppConTabsPreview() {
    ResponsividadTheme {
        AppConTabs()
    }
}