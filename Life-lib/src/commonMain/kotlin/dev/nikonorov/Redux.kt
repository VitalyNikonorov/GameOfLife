package dev.nikonorov

import dev.nikonorov.observable.Disposable
import dev.nikonorov.observable.Observable

interface Redux {
    interface Action
    interface State
    interface Reducer<A : Action, S : State> {
        fun reduce(action: A, oldState: S): S
    }

    interface Store<A : Action, S : State> {
        val state: Observable<S>
        fun subscribe(callback: (S) -> Unit): Disposable
        fun dispatch(action: A)
    }
}
