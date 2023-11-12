package id.my.mufidz.mealrecipe.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.base.component.BaseSnackbarHost
import id.my.mufidz.mealrecipe.base.component.CenterContent
import id.my.mufidz.mealrecipe.base.component.LoadingContent
import id.my.mufidz.mealrecipe.base.component.NetworkImage
import id.my.mufidz.mealrecipe.base.component.YouTubePlayer
import id.my.mufidz.mealrecipe.model.Meal
import id.my.mufidz.mealrecipe.utils.fillWidthOfParent

@Destination
@Composable
fun DetailScreen(navigator: DestinationsNavigator, meal: Meal) {
    val detailViewModel = hiltViewModel<DetailViewModel>()

    detailViewModel.execute(DetailAction.GetDetail(meal.id))

    DetailScreenContent(detailViewModel, navigator, meal)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScreenContent(
    detailViewModel: DetailViewModel, navigator: DestinationsNavigator, meal: Meal
) {
    val state = detailViewModel.viewState.collectAsState().value
    val snackbarHost = remember { SnackbarHostState() }
    val newMeal = state.meal
    val lifecycleOwner = LocalLifecycleOwner.current

    if (state.state == State.Failed || state.state == State.Success) {
        LaunchedEffect(snackbarHost) {
            snackbarHost.showSnackbar(state.message)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Detail ${meal.name}", maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }, navigationIcon = {
                IconButton(onClick = { navigator.navigateUp() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }, actions = {
                if (newMeal != null) {
                    IconButton(onClick = {
                        val action = if (state.isSaved) {
                            DetailAction.DeleteMeal(newMeal)
                        } else {
                            DetailAction.SaveMeal(newMeal)
                        }
                        detailViewModel.execute(action)
                    }) {
                        Icon(
                            imageVector = if (state.isSaved) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                            contentDescription = "Save Meal"
                        )
                    }
                }
            })
        },
        snackbarHost = { BaseSnackbarHost(snackbarHost, state.state) },
    ) { paddingValues ->

        when {
            state.state == State.Loading -> {
                LoadingContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            newMeal == null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Meal with id ${meal.id} Not Found")
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        NetworkImage(
                            url = newMeal.image,
                            modifier = Modifier.fillWidthOfParent(16.dp)
                        )
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        CenterContent(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = newMeal.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(text = "from ${newMeal.area}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                            )
                        }

                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp), elevation = CardDefaults.cardElevation(10.dp)
                        ) {
                            YouTubePlayer(
                                url = newMeal.youtube, lifecycleOwner = lifecycleOwner
                            )
                        }
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = "Ingredients",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start
                        )
                    }
                    items(newMeal.ingredients) {
                        ItemIngredient(ingredient = it)
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = "Recipe",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start
                        )
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = newMeal.instructions,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
        }
    }
}