package id.my.mufidz.mealrecipe.screen.meals

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.base.component.LoadingContent
import id.my.mufidz.mealrecipe.data.network.MealType
import id.my.mufidz.mealrecipe.screen.destinations.DetailScreenDestination

@Destination
@Composable
fun MealsScreen(navigator: DestinationsNavigator, type: MealType, query: String) {
    val mealsViewModel = hiltViewModel<MealsViewModel>()

    mealsViewModel.execute(MealsAction.LoadMeals(type, query))

    MealsScreenContent(mealsViewModel, navigator, query)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MealsScreenContent(
    mealsViewModel: MealsViewModel, navigator: DestinationsNavigator, query: String
) {
    val state = mealsViewModel.viewState.collectAsState().value
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "Meal by $query") }, navigationIcon = {
            IconButton(onClick = { navigator.navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(state.meals) {
                    ItemMeal(meal = it) {
                        navigator.navigate(DetailScreenDestination(it))
                    }
                }
            }
        }
    }
}