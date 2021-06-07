package dev.nikonorov.entity

import dev.nikonorov.Redux

data class GameOfLifeState(
    val field: List<List<Cell>> = emptyList(),
    val phase: GameOfLifePhase = GameOfLifePhase.INITIALIZING
) : Redux.State {
    constructor(width: Int, height: Int) : this(
        field = List(height) {
            List(width) {
                Cell()
            }
        })
}
