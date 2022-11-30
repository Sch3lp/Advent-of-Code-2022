package be.fgov.sfpd.kata.aoc22

fun readFile(fileName: String): String =
    {}::class.java.classLoader.getResourceAsStream(fileName)?.reader()?.readText() ?: error("Could not load $fileName")


data class Point(val x: Int, val y: Int) {
    val neighbours: Set<Point>
        get() = listOf(
            Point(-1, -1), Point(0, -1), Point(1, -1),
            Point(-1, 0), Point(1, 0),
            Point(-1, 1), Point(0, 1), Point(1, 1))
            .map { vector -> this + vector }
            .toSet()

    operator fun plus(vector: Point) = Point(this.x + vector.x, this.y + vector.y)
} 