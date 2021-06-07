package dev.nikonorov.reducer

import dev.nikonorov.BaseAction
import dev.nikonorov.Redux
import dev.nikonorov.entity.Cell
import dev.nikonorov.entity.GameOfLifePhase
import dev.nikonorov.entity.GameOfLifeState

class GameOfLifeReducer : Redux.Reducer<BaseAction, GameOfLifeState> {

    override fun reduce(action: BaseAction, oldState: GameOfLifeState): GameOfLifeState {
        return when (action) {
            is BaseAction.SetupField -> GameOfLifeState(width = action.width, height = action.height)
            is BaseAction.TogglePoint -> togglePoint(action, oldState)
            BaseAction.SimulateStep -> simulateStep(oldState)
        }
    }

    private fun togglePoint(
        action: BaseAction.TogglePoint,
        oldState: GameOfLifeState
    ): GameOfLifeState {
        return if (oldState.phase == GameOfLifePhase.INITIALIZING) {
            val newField = oldState.field.mapIndexed { row, list ->
                if (row == action.row) {
                    list.mapIndexed { col, cell ->
                        if (col == action.col) {
                            Cell(!cell.isAlive)
                        } else {
                            cell
                        }
                    }
                } else {
                    list
                }
            }
            oldState.copy(
                field = newField
            )
        } else {
            oldState
        }
    }

    private fun simulateStep(
        oldState: GameOfLifeState
    ): GameOfLifeState {
        val oldField = oldState.field
        val newField = mutableListOf<MutableList<Cell>>()
        for (row in oldField.indices) {
            newField.add(mutableListOf())
            for (col in oldField[row].indices) {
                val alive = when(aliveNeighbors(row, col, oldState.field)) {
                    3 -> true
                    2 -> oldField[row][col].isAlive
                    else -> false
                }
                newField[row].add(Cell(alive))
            }
        }
        return oldState.copy(
            field = newField,
            phase = GameOfLifePhase.STARTED
        )
    }

    private fun aliveNeighbors(row: Int, col: Int, field: List<List<Cell>>): Int {
        var aliveNeighborsCount = 0
        for (r in row - 1 .. row + 1) {
            for (c in col - 1 .. col + 1) {
                if (r >= 0 && r < field.size && c >= 0 && c < field[r].size) {
                    if ((r != row || c != col) && field[r][c].isAlive) {
                        aliveNeighborsCount++
                    }
                }
            }
        }

        return aliveNeighborsCount
    }
}
