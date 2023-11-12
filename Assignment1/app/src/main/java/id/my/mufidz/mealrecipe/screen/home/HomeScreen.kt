package id.my.mufidz.mealrecipe.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.my.mufidz.mealrecipe.R
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.base.component.LoadingContent
import id.my.mufidz.mealrecipe.base.component.items
import id.my.mufidz.mealrecipe.base.component.itemsGridFlow
import id.my.mufidz.mealrecipe.base.component.visibilityContent
import id.my.mufidz.mealrecipe.data.network.MealType
import id.my.mufidz.mealrecipe.screen.destinations.DetailScreenDestination
import id.my.mufidz.mealrecipe.screen.destinations.MealsScreenDestination
import id.my.mufidz.mealrecipe.screen.destinations.SearchScreenDestination
import id.my.mufidz.mealrecipe.screen.home.components.ItemCategory
import id.my.mufidz.mealrecipe.screen.home.components.ItemTitle
import id.my.mufidz.mealrecipe.screen.meals.ItemMeal
import id.my.mufidz.mealrecipe.utils.fillWidthOfParent
import timber.log.Timber

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    homeViewModel.execute(HomeAction.LoadHome)

    HomeScreenContent(homeViewModel, navigator)
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class
)
@Composable
private fun HomeScreenContent(homeViewModel: HomeViewModel, navigator: DestinationsNavigator) {
    val state = homeViewModel.viewState.collectAsState().value

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
                IconButton(onClick = { navigator.navigate(SearchScreenDestination) }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
                }
            })
    }) { paddingValues ->
        if (state.state == State.Loading) {
            LoadingContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
            ) {
                visibilityContent(state.savedMeals) { meals ->
                    item {
                        Row(Modifier.fillMaxWidth()) {
                            ItemTitle(title = "My Meals", "Saved meal by you", showAll = true) {
                                navigator.navigate(
                                    MealsScreenDestination(
                                        MealType.SAVED, "Me"
                                    )
                                )
                            }

                        }
                    }
                    item {
                        LazyRow(
                            Modifier.fillWidthOfParent(16.dp), contentPadding = PaddingValues(8.dp)
                        ) {
                            items(meals) {
                                ItemMeal(meal = it) {
                                    navigator.navigate(DetailScreenDestination(it))
                                }
                            }
                        }
                    }
                }
                visibilityContent(state.areas) { areas ->
                    stickyHeader {
                        ItemTitle(title = "Area", "Recipe by Area")
                    }
                    item {
                        FlowRow(
                            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            items(areas) {
                                SuggestionChip(onClick = {
                                    navigator.navigate(
                                        MealsScreenDestination(
                                            MealType.AREA, it.name
                                        )
                                    )
                                }, label = { Text(text = it.name) })
                            }
                        }
                    }
                }
                visibilityContent(state.categories) { categories ->
                    stickyHeader {
                        ItemTitle(title = "Categories", "Recipe by Category")
                    }
                    itemsGridFlow(2, categories, modifier = Modifier.fillMaxWidth()) {
                        ItemCategory(category = it) {
                            navigator.navigate(
                                MealsScreenDestination(
                                    MealType.CATEGORY, it.category
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}