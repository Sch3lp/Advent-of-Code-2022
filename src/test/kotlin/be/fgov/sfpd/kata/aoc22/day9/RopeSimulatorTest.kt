package be.fgov.sfpd.kata.aoc22.day9

import be.fgov.sfpd.kata.aoc22.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
    fun `The tail remains in place when the head moves to a neighbouring point`() {
        Rope(head = Point(0, 0), tail = Point(0, 0)).apply {
            pull(Up(1))
            assertThat(this).isEqualTo(Rope(Point(0, 1), Point(0, 0)))
        }
        Rope(head = Point(0, 0), tail = Point(0, 0)).apply {
            pull(Down(1))
            assertThat(this).isEqualTo(Rope(Point(0, -1), Point(0, 0)))
        }
        Rope(head = Point(0, 0), tail = Point(0, 0)).apply {
            pull(Right(1))
            assertThat(this).isEqualTo(Rope(Point(1, 0), Point(0, 0)))
        }
        Rope(head = Point(0, 0), tail = Point(0, 0)).apply {
            pull(Left(1))
            assertThat(this).isEqualTo(Rope(Point(-1, 0), Point(0, 0)))
        }
        Rope(head = Point(0, 0), tail = Point(0, 0)).apply {
            pull(listOf(Up(1), Left(1)))
            assertThat(this).isEqualTo(Rope(Point(-1, 1), Point(0, 0)))
        }
        Rope(head = Point(0, 0), tail = Point(0, 0)).apply {
            pull(listOf(Up(1), Right(1)))
            assertThat(this).isEqualTo(Rope(Point(1, 1), Point(0, 0)))
        }
        Rope(head = Point(0, 0), tail = Point(0, 0)).apply {
            pull(listOf(Down(1), Left(1)))
            assertThat(this).isEqualTo(Rope(Point(-1, -1), Point(0, 0)))
        }
        Rope(head = Point(0, 0), tail = Point(0, 0)).apply {
            pull(listOf(Down(1), Right(1)))
            assertThat(this).isEqualTo(Rope(Point(1, -1), Point(0, 0)))
        }
    }

    @Test
    fun `The tail remains in place when the head moves over it`() {
        Rope(head = Point(1, 0), tail = Point(0, 0)).apply {
            pull(Left(1)).also { assertThat(this).isEqualTo(Rope(head = Point(0, 0), tail = Point(0, 0))) }
        }
    }

    @Test
    fun `The tail follows the head on one axis, by staying one point behind it`() {
        Rope(Point(1, 0), Point(0, 0)).apply {
            pull(Right(1)).also { assertThat(this).isEqualTo(Rope(Point(2, 0), Point(1, 0))) }
            pull(Right(2)).also { assertThat(this).isEqualTo(Rope(Point(4, 0), Point(3, 0))) }
        }
        Rope(Point(-1, 0), Point(0, 0)).apply {
            pull(Left(1)).also { assertThat(this).isEqualTo(Rope(Point(-2, 0), Point(-1, 0))) }
            pull(Left(2)).also { assertThat(this).isEqualTo(Rope(Point(-4, 0), Point(-3, 0))) }
        }
        Rope(Point(0, 1), Point(0, 0)).apply {
            pull(Up(1)).also { assertThat(this).isEqualTo(Rope(Point(0, 2), Point(0, 1))) }
            pull(Up(2)).also { assertThat(this).isEqualTo(Rope(Point(0, 4), Point(0, 3))) }
        }
        Rope(Point(0, -1), Point(0, 0)).apply {
            pull(Down(1)).also { assertThat(this).isEqualTo(Rope(Point(0, -2), Point(0, -1))) }
            pull(Down(2)).also { assertThat(this).isEqualTo(Rope(Point(0, -4), Point(0, -3))) }
        }
    }

    @Test
    fun `The tail prefers to align on the axis before it follows the head on one axis`() {
        //drawing a square, clockwise, starting top middle
        Rope(Point(0, 0), Point(0, 0)).apply {
            pull(Up(1)).also { assertThat(this).isEqualTo(Rope(Point(0, 1), Point(0, 0))) } //start
            pull(Right(2)).also { assertThat(this).isEqualTo(Rope(Point(2, 1), Point(1, 1))) }
            pull(Down(2)).also { assertThat(this).isEqualTo(Rope(Point(2, -1), Point(2, 0))) }
            pull(Left(2)).also { assertThat(this).isEqualTo(Rope(Point(0, -1), Point(1, -1))) }
            pull(Up(2)).also { assertThat(this).isEqualTo(Rope(Point(0, 1), Point(0, 0))) } //end
        }
    }

    @Test
    fun `The tail follows the head diagonally, by staying one point behind it`() {
        Rope(Point(-1, 1), Point(0, 0)).apply {
            pull(Up(3)).also { assertThat(this).isEqualTo(Rope(Point(-1, 4), Point(-1, 3))) }
        }
        Rope(Point(1, 1), Point(0, 0)).apply {
            pull(Up(5)).also { assertThat(this).isEqualTo(Rope(Point(1, 6), Point(1, 5))) }
        }
        Rope(Point(1, 1), Point(0, 0)).apply {
            pull(Right(1)).also { assertThat(this).isEqualTo(Rope(Point(2, 1), Point(1, 1))) }
        }
        Rope(Point(1, -1), Point(0, 0)).apply {
            pull(Right(1)).also { assertThat(this).isEqualTo(Rope(Point(2, -1), Point(1, -1))) }
        }
        Rope(Point(1, -1), Point(0, 0)).apply {
            pull(Down(1)).also { assertThat(this).isEqualTo(Rope(Point(1, -2), Point(1, -1))) }
        }
        Rope(Point(-1, -1), Point(0, 0)).apply {
            pull(Down(1)).also { assertThat(this).isEqualTo(Rope(Point(-1, -2), Point(-1, -1))) }
        }
        Rope(Point(-1, -1), Point(0, 0)).apply {
            pull(Left(1)).also { assertThat(this).isEqualTo(Rope(Point(-2, -1), Point(-1, -1))) }
        }
        Rope(Point(-1, 1), Point(0, 0)).apply {
            pull(Left(1)).also { assertThat(this).isEqualTo(Rope(Point(-2, 1), Point(-1, 1))) }
        }
    }

    @Test
    fun `Tail movement gets broadcast every time it changes`() {
        val broadcastTailPoints = mutableSetOf<Point>()
        Rope(head = Point(0, 0), tail = Point(0, 0), broadcastTailPoints::add).apply {
            pull(Up(1)).also { assertThat(broadcastTailPoints).isEmpty() } //start
            pull(Right(2)).also { assertThat(broadcastTailPoints).containsExactly(Point(1, 1)) }
            pull(Down(2)).also {
                assertThat(broadcastTailPoints).containsExactly(
                    Point(1, 1),
                    Point(2, 0),
                )
            }
            pull(Left(2)).also {
                assertThat(broadcastTailPoints).containsExactly(
                    Point(1, 1),
                    Point(2, 0),
                    Point(1, -1),
                )
            }
            pull(Up(2)).also {
                assertThat(broadcastTailPoints).containsExactly(
                    Point(1, 1),
                    Point(2, 0),
                    Point(1, -1),
                    Point(0, 0),
                )
            } //end
        }
    }

    @Test
    fun `Tail movement does not get broadcast if the tail remains in place`() {
        val broadcastTailPoints = mutableSetOf<Point>()
        Rope(head = Point(0, 0), tail = Point(0, 0), broadcastTailPoints::add).apply {
            pull(Up(1))
            pull(Right(1))
            pull(Down(2))
            pull(Left(2))
            pull(Up(2))
            pull(Right(2))
        }
        assertThat(broadcastTailPoints).isEmpty()
    }

    @Test
    fun `end position part 1 with example input`() {
        val tailMovements = mutableListOf<Point>(Point(0, 0))
        val partialTailMovements = mutableListOf<Point>(Point(0, 0))
        val broadcaster: (Point) -> Unit = {
            partialTailMovements.add(it)
            tailMovements.add(it)
        }

        Rope(broadcast = broadcaster).apply {
            pull(Right(4)).also {
                assertThat(partialTailMovements).containsExactlyElementsOf(Point(0, 0)..Point(3, 0))
                partialTailMovements.clear()
            }
            pull(Up(4)).also {
                assertThat(partialTailMovements).containsExactlyElementsOf(Point(4, 1)..Point(4, 3))
                partialTailMovements.clear()
            }
            pull(Left(3)).also {
                assertThat(partialTailMovements).containsExactly(Point(3, 4), Point(2, 4))
                partialTailMovements.clear()
            }
            pull(Down(1)).also {
                assertThat(partialTailMovements).isEmpty()
                partialTailMovements.clear()
            }
            pull(Right(4)).also {
                assertThat(partialTailMovements).containsExactly(Point(3, 3), Point(4, 3))
                partialTailMovements.clear()
            }
            pull(Down(1)).also {
                assertThat(partialTailMovements).isEmpty()
                partialTailMovements.clear()
            }
            pull(Left(5)).also {
                assertThat(partialTailMovements).containsExactlyElementsOf(Point(3, 2)..Point(1, 2))
                partialTailMovements.clear()
            }
            pull(Right(2)).also {
                assertThat(partialTailMovements).isEmpty()
                partialTailMovements.clear()
            }
                .also { assertThat(this).isEqualTo(Rope(Point(2, 2), Point(1, 2), broadcaster)) }
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

    @Test
    fun `diagonal with following knots is working`() {
        val tailMovements = mutableSetOf<Point>()
        val tail = Rope(Point(0, 0), Point(0, 0), tailMovements::add, "T")
        val knot8 = Rope(Point(0, 0), Point(0, 0), tail::follow, "8")
        val knot7 = Rope(Point(0, 0), Point(0, 0), knot8::follow, "7")
        val knot6 = Rope(Point(0, 0), Point(0, 0), knot7::follow, "6")
        val knot5 = Rope(Point(0, 0), Point(0, 0), knot6::follow, "5")
        val knot4 = Rope(Point(1, 0), Point(0, 0), knot5::follow, "4")
        val knot3 = Rope(Point(2, 0), Point(1, 0), knot4::follow, "3")
        val knot2 = Rope(Point(3, 0), Point(2, 0), knot3::follow, "2")
        val knot1 = Rope(Point(4, 0), Point(3, 0), knot2::follow, "1")
        val head = Rope(Point(5, 0), Point(4, 0), knot1::follow, "H")

        head.pull(Up(8))
        assertThat(head).isEqualTo(head.copy(head = Point(5, 8), tail = Point(5, 7)))
        assertThat(knot1).isEqualTo(knot1.copy(head = Point(5, 7), tail = Point(5, 6)))
        assertThat(knot2).isEqualTo(knot2.copy(head = Point(5, 6), tail = Point(5, 5)))
        assertThat(knot3).isEqualTo(knot3.copy(head = Point(5, 5), tail = Point(5, 4)))
        assertThat(knot4).isEqualTo(knot4.copy(head = Point(5, 4), tail = Point(4, 4)))
        assertThat(knot5).isEqualTo(knot5.copy(head = Point(4, 4), tail = Point(3, 3)))
        assertThat(knot6).isEqualTo(knot6.copy(head = Point(3, 3), tail = Point(2, 2)))
        assertThat(knot7).isEqualTo(knot7.copy(head = Point(2, 2), tail = Point(1, 1)))
        assertThat(knot8).isEqualTo(knot8.copy(head = Point(1, 1), tail = Point(0, 0)))
        assertThat(tail).isEqualTo(tail.copy(head = Point(0, 0), tail = Point(0, 0)))
    }

    @Test
    fun `part 2 visualized`() {
        val tailMovements = mutableSetOf<Point>()
        val tail = Rope(Point(0, 0), Point(0, 0), tailMovements::add, "9")
        val knot8 = Rope(Point(0, 0), Point(0, 0), tail::follow, "8")
        val knot7 = Rope(Point(0, 0), Point(0, 0), knot8::follow, "7")
        val knot6 = Rope(Point(0, 0), Point(0, 0), knot7::follow, "6")
        val knot5 = Rope(Point(0, 0), Point(0, 0), knot6::follow, "5")
        val knot4 = Rope(Point(0, 0), Point(0, 0), knot5::follow, "4")
        val knot3 = Rope(Point(0, 0), Point(0, 0), knot4::follow, "3")
        val knot2 = Rope(Point(0, 0), Point(0, 0), knot3::follow, "2")
        val knot1 = Rope(Point(0, 0), Point(0, 0), knot2::follow, "1")
        val head = Rope(Point(0, 0), Point(0, 0), knot1::follow, "H")

        val rope = listOf(head, knot1, knot2, knot3, knot4, knot5, knot5, knot6, knot7, knot8, tail)
        assertThat(rope.visualize()).isEqualTo("""
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        ...........H..............
        ..........................
        ..........................
        ..........................
        ..........................
        ..........................
        """.trimIndent())


        head.pull(Right(5))
        assertThat(rope.visualize()).isEqualTo("""
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ...........54321H.........
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
        """.trimIndent())

        head.pull(Up(8))

        assertThat(rope.visualize()).isEqualTo("""
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
            ................H.........
            ................1.........
            ................2.........
            ................3.........
            ...............54.........
            ..............6...........
            .............7............
            ............8.............
            ...........9..............
            ..........................
            ..........................
            ..........................
            ..........................
            ..........................
        """.trimIndent())

    }
}


fun List<Rope>.visualize(): String {
    return (15 downTo -5).joinToString("\n") { y ->
        (-11 .. 14).joinToString("") { x ->
            firstOrNull { it.start == Point(x, y) }?.id ?: "."
//                ?.let { firstOrNull { it.end == Point(x, y) }?.id }

        }
    }
}