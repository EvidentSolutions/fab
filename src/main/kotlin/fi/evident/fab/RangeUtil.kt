package fi.evident.fab

fun <T: Comparable<T>>ClosedRange<T>.assertContains(value: T, name: String) {
    if (!this.contains(value))
        throw IllegalArgumentException("$name not in limits $this, was: $value")
}