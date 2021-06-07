package dev.nikonorov

sealed class BaseAction : Redux.Action {
    data class SetupField(
        val width: Int,
        val height: Int
    ) : BaseAction()

    data class TogglePoint(
        val row: Int,
        val col: Int
    ) : BaseAction()

    object SimulateStep : BaseAction()
}
