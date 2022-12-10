package be.fgov.sfpd.kata.aoc22

fun readFile(fileName: String): String =
    {}::class.java.classLoader.getResourceAsStream(fileName)?.reader()?.readText() ?: error("Could not load $fileName")


data class Point(val x: Int, val y: Int) {

    //@formatter:off
    val neighbours: Set<Point>
        get() = listOf(
            Point(-1, -1), Point(0, -1), Point(1, -1),
            Point(-1, 0), /*  this   */  Point(1, 0),
            Point(-1, 1), Point(0, 1), Point(1, 1),
        ).map { vector -> this + vector }
            .toSet()

    val orthogonalNeighbours: Set<Point>
        get() = listOf(
            Point(0, -1),
            Point(-1, 0), Point(1, 0),
            Point(0, 1),
        ).map { vector -> this + vector }
            .toSet()
    //@formatter:on

    val diagonalNeighbours: Set<Point>
        get() = listOf(
            Point(-1,-1), Point(1,-1),
            Point(-1,1), Point(1,1),
        ).map { vector -> this + vector }
            .toSet()

    fun isDiagonalTo(other: Point): Boolean = this in other.diagonalNeighbours

    operator fun plus(vector: Point) = Point(x + vector.x, this.y + vector.y)
    operator fun times(multiplier: Int) = Point(x = x * multiplier, y = y * multiplier)
    operator fun rangeTo(point: Point): Set<Point> = when {
        this == point -> emptySet()
        this.x < point.x && this.y == point.y -> (this.x..point.x).map { copy(x = it) }
        this.x > point.x && this.y == point.y -> (this.x downTo point.x).map { copy(x = it) }
        this.x == point.x && this.y < point.y -> (this.y..point.y).map { copy(y = it) }
        this.x == point.x && this.y > point.y -> (this.y downTo point.y).map { copy(y = it) }

        this.x < point.x && this.y < point.y -> { //this is bottomleft of point
            if ((point.x - this.x) > (point.y - this.y)) { //prefer x
                (this.x..point.x).flatMap { newX ->
                    (this.y until point.y).map { newY ->
                        Point(newX, newY)
                    }
                }
            } else { //prefer y
                (this.y..point.y).flatMap { newY ->
                    (this.x until point.x).map { newX ->
                        Point(newX, newY)
                    }
                }
            } + point
        }

        this.x > point.x && this.y > point.y -> { //this is topright of point
            if ((this.x - point.x) > (this.y - point.y)) { //prefer x
                (this.y downTo point.y + 1).flatMap { newY ->
                    (this.x downTo point.x).map { newX ->
                        Point(newX, newY)
                    }
                }
            } else { //prefer y
                (this.x downTo point.x + 1).flatMap { newX ->
                    (this.y downTo point.y).map { newY ->
                        Point(newX, newY)
                    }
                }
            } + point
        }

        this.x < point.x && this.y > point.y -> { //this is topleft of point
            if ((point.x - this.x) > (this.y - point.y)) { //prefer x
                (this.y downTo point.y + 1).flatMap { newY ->
                    (this.x..point.x).map { newX ->
                        Point(newX, newY)
                    }
                }
            } else { //prefer y
                (this.x until point.x).flatMap { newX ->
                    (this.y downTo point.y).map { newY ->
                        Point(newX, newY)
                    }
                }
            } + point
        }

        this.x > point.x && this.y < point.y -> { //this is bottomright of point
            if ((this.x - point.x) > (point.y - this.y)) { //prefer x
                (this.y until point.y).flatMap { newY ->
                    (this.x downTo point.x).map { newX ->
                        Point(newX, newY)
                    }
                }
            } else { //prefer y
                (this.x downTo point.x + 1).flatMap { newX ->
                    (this.y .. point.y).map { newY ->
                        Point(newX, newY)
                    }
                }
            } + point
        }

        else -> error("No diagonal/manhattan ranges supported yet. Given: $this..$point")
    }.toSet()

    infix fun until(other: Point) = (this..other) - other
}

object Debugging {
    private var debugEnabled = false
    fun <T> T.debug(block: (it: T) -> String) = if (debugEnabled) this.also { println(block(this)) } else this
    fun enable() {
        debugEnabled = true
    }

    fun disable() {
        debugEnabled = false
    }

    fun <T> withDebugging(block: () -> T): T {
        enable()
        return block().also { disable() }
    }
}