package be.fgov.sfpd.kata.aoc22

fun readFile(fileName: String): String {
    return {}::class.java.classLoader.getResourceAsStream(fileName)?.reader()?.readText() ?: error("Could not load $fileName")
}


data class Point(val x: Int, val y: Int) {
    operator fun plus(vector: Point) = Point(this.x + vector.x, this.y + vector.y)

} 