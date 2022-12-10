package be.fgov.sfpd.kata.aoc22.day9

import be.fgov.sfpd.kata.aoc22.Point
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KFunction1

class RopeSimulatorTest {

    @Test
    fun `can parse to move commands`() {
        val input = """
        U 1
        D 5
        L 10
        R 42""".trimIndent()
        val commands: List<PullCommand> = PullCommand.fromLines(input)

        assertThat(commands).containsExactly(
            Up(1),
            Down(5),
            Left(10),
            Right(42),
        )
    }

    @Test
    fun `A rope can be pulled with pull commands`() {
        val rope = Rope(head = Point(0, 0), tail = Point(0, 0))
        assertThat(rope.pull(Up(1)).head).isEqualTo(Point(0, 1))
        assertThat(rope.pull(Down(1)).head).isEqualTo(Point(0, -1))
        assertThat(rope.pull(Right(11)).head).isEqualTo(Point(11, 0))
        assertThat(rope.pull(Left(21)).head).isEqualTo(Point(-21, 0))

        assertThat(rope.pull(listOf(Up(12), Right(23))).head).isEqualTo(Point(23, 12))
    }

    @Test
    fun `The tail remains in place when the head moves to a neighbouring point`() {
        val rope = Rope(head = Point(0, 0), tail = Point(0, 0))
        assertThat(rope.pull(Up(1))).isEqualTo(Rope(head = Point(0, 1), tail = Point(0, 0)))
        assertThat(rope.pull(Down(1))).isEqualTo(Rope(head = Point(0, -1), tail = Point(0, 0)))
        assertThat(rope.pull(Right(1))).isEqualTo(Rope(head = Point(1, 0), tail = Point(0, 0)))
        assertThat(rope.pull(Left(1))).isEqualTo(Rope(head = Point(-1, 0), tail = Point(0, 0)))
        assertThat(rope.pull(listOf(Up(1), Left(1)))).isEqualTo(Rope(head = Point(-1, 1), tail = Point(0, 0)))
        assertThat(rope.pull(listOf(Up(1), Right(1)))).isEqualTo(Rope(head = Point(1, 1), tail = Point(0, 0)))
        assertThat(rope.pull(listOf(Down(1), Left(1)))).isEqualTo(Rope(head = Point(-1, -1), tail = Point(0, 0)))
        assertThat(rope.pull(listOf(Down(1), Right(1)))).isEqualTo(Rope(head = Point(1, -1), tail = Point(0, 0)))
    }

    @Test
    fun `The tail remains in place when the head moves over it`() {
        val rope = Rope(head = Point(1, 0), tail = Point(0, 0))
        assertThat(rope.pull(Left(1))).isEqualTo(Rope(head = Point(0, 0), tail = Point(0, 0)))
    }

    @Test
    fun `The tail follows the head on one axis, by staying one point behind it`() {
        Rope(Point(1, 0), Point(0, 0))
            .pull(Right(1)).also { assertThat(it).isEqualTo(Rope(Point(2, 0), Point(1, 0))) }
            .pull(Right(2)).also { assertThat(it).isEqualTo(Rope(Point(4, 0), Point(3, 0))) }
        Rope(Point(-1, 0), Point(0, 0))
            .pull(Left(1)).also { assertThat(it).isEqualTo(Rope(Point(-2, 0), Point(-1, 0))) }
            .pull(Left(2)).also { assertThat(it).isEqualTo(Rope(Point(-4, 0), Point(-3, 0))) }
        Rope(Point(0, 1), Point(0, 0))
            .pull(Up(1)).also { assertThat(it).isEqualTo(Rope(Point(0, 2), Point(0, 1))) }
            .pull(Up(2)).also { assertThat(it).isEqualTo(Rope(Point(0, 4), Point(0, 3))) }
        Rope(Point(0, -1), Point(0, 0))
            .pull(Down(1)).also { assertThat(it).isEqualTo(Rope(Point(0, -2), Point(0, -1))) }
            .pull(Down(2)).also { assertThat(it).isEqualTo(Rope(Point(0, -4), Point(0, -3))) }
    }

    @Test
    fun `The tail prefers to align on the axis before it follows the head on one axis`() {
        //drawing a square, clockwise, starting top middle
        Rope(Point(0, 0), Point(0, 0))
            .pull(Up(1)).also { assertThat(it).isEqualTo(Rope(Point(0, 1), Point(0, 0))) } //start
            .pull(Right(2)).also { assertThat(it).isEqualTo(Rope(Point(2, 1), Point(1, 1))) }
            .pull(Down(2)).also { assertThat(it).isEqualTo(Rope(Point(2, -1), Point(2, 0))) }
            .pull(Left(2)).also { assertThat(it).isEqualTo(Rope(Point(0, -1), Point(1, -1))) }
            .pull(Up(2)).also { assertThat(it).isEqualTo(Rope(Point(0, 1), Point(0, 0))) } //end
    }

    @Test
    fun `The tail follows the head diagonally, by staying one point behind it`() {
        Rope(Point(-1, 1), Point(0, 0)).pull(Up(3))
            .also { assertThat(it).isEqualTo(Rope(Point(-1, 4), Point(-1, 3))) }
        Rope(Point(1, 1), Point(0, 0)).pull(Up(5))
            .also { assertThat(it).isEqualTo(Rope(Point(1, 6), Point(1, 5))) }
        Rope(Point(1, 1), Point(0, 0)).pull(Right(1))
            .also { assertThat(it).isEqualTo(Rope(Point(2, 1), Point(1, 1))) }
        Rope(Point(1, -1), Point(0, 0)).pull(Right(1))
            .also { assertThat(it).isEqualTo(Rope(Point(2, -1), Point(1, -1))) }
        Rope(Point(1, -1), Point(0, 0)).pull(Down(1))
            .also { assertThat(it).isEqualTo(Rope(Point(1, -2), Point(1, -1))) }
        Rope(Point(-1, -1), Point(0, 0)).pull(Down(1))
            .also { assertThat(it).isEqualTo(Rope(Point(-1, -2), Point(-1, -1))) }
        Rope(Point(-1, -1), Point(0, 0)).pull(Left(1))
            .also { assertThat(it).isEqualTo(Rope(Point(-2, -1), Point(-1, -1))) }
        Rope(Point(-1, 1), Point(0, 0)).pull(Left(1))
            .also { assertThat(it).isEqualTo(Rope(Point(-2, 1), Point(-1, 1))) }
    }

    @Test
    fun `Tail movement gets broadcast every time it changes`() {
        val broadcastTailPoints = mutableSetOf<Point>()
        Rope(head = Point(0, 0), tail = Point(0, 0), broadcastTailPoints::add)
            .pull(Up(1)).also { assertThat(broadcastTailPoints).isEmpty() } //start
            .pull(Right(2)).also { assertThat(broadcastTailPoints).containsExactly(Point(1, 1)) }
            .pull(Down(2)).also {
                assertThat(broadcastTailPoints).containsExactly(
                    Point(1, 1),
                    Point(2, 0),
                )
            }
            .pull(Left(2)).also {
                assertThat(broadcastTailPoints).containsExactly(
                    Point(1, 1),
                    Point(2, 0),
                    Point(1, -1),
                )
            }
            .pull(Up(2)).also {
                assertThat(broadcastTailPoints).containsExactly(
                    Point(1, 1),
                    Point(2, 0),
                    Point(1, -1),
                    Point(0, 0),
                )
            } //end
    }

    @Test
    fun `Tail movement does not get broadcast if the tail remains in place`() {
        val broadcastTailPoints = mutableSetOf<Point>()
        Rope(head = Point(0, 0), tail = Point(0, 0), broadcastTailPoints::add)
            .pull(Up(1))
            .pull(Right(1))
            .pull(Down(2))
            .pull(Left(2))
            .pull(Up(2))
            .pull(Right(2))
            .also { assertThat(broadcastTailPoints).isEmpty() }
    }

    @Test
    fun reproTest() {
        val tailMovements = mutableSetOf<Point>()
        val broadcaster: (Point) -> Unit = { tailMovements.add(it) }

        Rope(Point(1,3), Point(2,4), broadcaster).pull(Right(4))
            .also { assertThat(it).isEqualTo(Rope(Point(5,3), Point(4,3), broadcaster)) }
            .also { assertThat(tailMovements).containsExactly(
                Point(3,3), Point(4,3)
            ) }

    }

    @Test
    fun `end position with example input`() {
        val tailMovements = mutableListOf<Point>()
        val partialTailMovements = mutableListOf<Point>()
        val broadcaster: (Point) -> Unit = {
            partialTailMovements.add(it)
            tailMovements.add(it)
        }

        Rope(broadcast = broadcaster)
            .pull(Right(4)).also { assertThat(partialTailMovements).containsExactly(Point(0,0), Point(1,0), Point(2,0), Point(3,0)); partialTailMovements.clear() }
            .pull(Up(4)).also { assertThat(partialTailMovements).containsExactly(Point(4,1), Point(4,2), Point(4,3)); partialTailMovements.clear() }
            .pull(Left(3)).also { assertThat(partialTailMovements).containsExactly(Point(3,4), Point(2,4)); partialTailMovements.clear() }
            .pull(Down(1)).also { assertThat(partialTailMovements).isEmpty(); partialTailMovements.clear() }
            .pull(Right(4)).also { assertThat(partialTailMovements).containsExactly(Point(3,3), Point(4,3)); partialTailMovements.clear() }
            .pull(Down(1)).also { assertThat(partialTailMovements).isEmpty(); partialTailMovements.clear() }
            .pull(Left(5)).also { assertThat(partialTailMovements).containsExactly(Point(3,2), Point(2,2), Point(1,2)); partialTailMovements.clear() }
            .pull(Right(2)).also { assertThat(partialTailMovements).isEmpty(); partialTailMovements.clear() }
            .also { ended -> assertThat(ended).isEqualTo(Rope(Point(2,2), Point(1,2), broadcaster)) }
            .also {
                assertThat(tailMovements).containsExactly(
                    Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0), // R4
                    Point(4, 1), Point(4, 2), Point(4, 3), // U4
                    Point(3, 4), Point(2, 4), // L3
                    // D1
                    Point(3, 3), Point(4, 3), // R4
                    // D1
                    Point(3, 2), Point(2, 2), Point(1, 2), // L5
                    // R 2
                )
            }
    }
}
