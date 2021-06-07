package dev.nikonorov.observable

/**
 * Simple observable
 */
class Observable<T>(
    initialValue: T
) {
    var value: T = initialValue
        set(value) {
            field = value
            observers.forEach {
                it(value)
            }
        }

    private val observers = mutableSetOf<(T) -> Unit>()

    fun subscribe(callback: (T) -> Unit): Disposable {
        observers.add(callback)
        return object : Disposable {
            override fun dispose() {
                observers.remove(callback)
            }
        }
    }
}
