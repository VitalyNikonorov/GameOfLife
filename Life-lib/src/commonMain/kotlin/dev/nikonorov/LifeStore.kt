package dev.nikonorov

import dev.nikonorov.entity.GameOfLifeState
import dev.nikonorov.observable.Disposable
import dev.nikonorov.observable.Observable
import dev.nikonorov.reducer.GameOfLifeReducer

/**
 * Simple implementation of Redux Store.
 * Attention: class is thread unsafe
 */
class LifeStore: Redux.Store<BaseAction, GameOfLifeState> {
    private val reducer = GameOfLifeReducer()

    override val state: Observable<GameOfLifeState> = Observable(GameOfLifeState())
    override fun subscribe(callback: (GameOfLifeState) -> Unit): Disposable {
        return state.subscribe(callback)
    }

    override fun dispatch(action: BaseAction) {
        state.value = reducer.reduce(action, state.value)
    }
}
