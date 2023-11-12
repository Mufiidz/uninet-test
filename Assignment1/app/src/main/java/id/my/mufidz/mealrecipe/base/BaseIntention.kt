package id.my.mufidz.mealrecipe.base

enum class State {
    Idle, Loading, Success, Failed
}

interface ViewState {
    val state: State
    val message: String
}

interface ViewAction

interface ActionResult