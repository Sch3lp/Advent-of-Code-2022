package be.fgov.sfpd.kata.aoc22

fun readFile(fileName: String): String =
    {}::class.java.classLoader.getResourceAsStream(fileName)?.reader()?.readText() ?: error("Could not load $fileName")


data class Point(val x: Int, val y: Int) {
    //@formatter:off
    val neighbours: Set<Point>
        get() = listOf(
            Point(-1, -1),  Point(0, -1),   Point(1, -1),
            Point(-1, 0),                   Point(1, 0),
            Point(-1, 1),   Point(0, 1),    Point(1, 1),
        ).map { vector -> this + vector }
         .toSet()

    val orthogonalNeighbours: Set<Point>
        get() = listOf(
                            Point(0, -1),
            Point(-1, 0),                   Point(1, 0),
                            Point(0, 1),
        ).map { vector -> this + vector }
         .toSet()
    //@formatter:on

    operator fun plus(vector: Point) = Point(this.x + vector.x, this.y + vector.y)
    operator fun rangeTo(point: Point): Set<Point> = when {
        this.x < point.x && this.y == point.y -> (this.x..point.x).map { Point(it, this.y) }
        this.x > point.x && this.y == point.y -> (this.x.until(point.x)).map { Point(it, this.y) }
        this.x == point.x && this.y < point.y -> (this.y..point.y).map { Point(this.x, it) }
        this.x == point.x && this.y > point.y -> (this.y.until(point.y)).map { Point(this.x, it) }
        else -> error("")
    }.toSet()
} 