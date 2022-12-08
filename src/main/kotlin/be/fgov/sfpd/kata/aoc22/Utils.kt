package be.fgov.sfpd.kata.aoc22

fun readFile(fileName: String): String =
    {}::class.java.classLoader.getResourceAsStream(fileName)?.reader()?.readText() ?: error("Could not load $fileName")


data class Point(val x: Int, val y: Int) {
    val northNeighbour: Point get() = this + Point(0,-1)
    val southNeighbour: Point get() = this + Point(0, 1)
    val eastNeighbour: Point get() = this + Point(1,0)
    val westNeighbour: Point get() = this + Point(-1,0)

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
        this.x < point.x && this.y == point.y -> (this.x..point.x).map { copy(x = it) }
        this.x > point.x && this.y == point.y -> (this.x.downTo(point.x)).map { copy(x = it) }
        this.x == point.x && this.y < point.y -> (this.y..point.y).map { copy(y = it) }
        this.x == point.x && this.y > point.y -> (this.y.downTo(point.y)).map { copy(y = it) }
        this == point -> emptySet()
        else -> error("No diagonal/manhattan ranges supported yet. Given: $this..$point")
    }.toSet()
}

object Debugging {
    private var debugEnabled = false
    fun <T> T.debug(block: (it: T) -> String) = if(debugEnabled) this.also { println(block(this)) } else this
    fun enable() { debugEnabled = true }
    fun disable() { debugEnabled = true }
    fun <T> withDebugging(block: () -> T) : T {
        enable()
        return block().also { disable() }
    }
}
