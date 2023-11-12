package id.my.mufidz.mealrecipe.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.my.mufidz.mealrecipe.base.component.NetworkImage
import id.my.mufidz.mealrecipe.model.Ingredient

@Composable
fun ItemIngredient(ingredient: Ingredient) {
    val (_, name, measure, image) = ingredient
    Column(Modifier.width(60.dp).padding(8.dp)) {
        NetworkImage(url = image, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$measure $name", fontSize = 14.sp)
    }
}