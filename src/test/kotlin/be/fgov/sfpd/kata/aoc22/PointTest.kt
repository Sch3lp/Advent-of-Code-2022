package be.fgov.sfpd.kata.aoc22

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class PointTest {

    @Test
    fun `given two points - we can add them together`() {
        val origin = Point(0, 0)
        val vector = Point(-1, 1)

        val actual = origin + vector

        assertThat(actual).isEqualTo(Point(-1, 1))
    }

    @Test
    fun `return neighbouring points of point`() {
        val point = Point(0, 0)

        val actual = point.neighbours

        assertThat(actual).containsExactlyInAnyOrder(
            Point(-1, -1), Point(0, -1), Point(1, -1),
            Point(-1, 0), Point(1, 0),
            Point(-1, 1), Point(0, 1), Point(1, 1)
        )
    }

    @Test
    fun `return orthogonal neighbouring points of point`() {
        val point = Point(0, 0)

        val actual = point.orthogonalNeighbours

        assertThat(actual).containsExactlyInAnyOrder(
            Point(0, -1),
            Point(-1, 0), Point(1, 0),
            Point(0, 1)
        )
    }

    @Test
    fun `rangeTo tests`(softly: SoftAssertions) {
        softly.assertThat(Point(0,0)..Point(0,5)).containsExactly(Point(0,0),Point(0,1),Point(0,2),Point(0,3),Point(0,4),Point(0,5))
        softly.assertThat(Point(0,5)..Point(0,0)).containsExactly(Point(0,5), Point(0,4), Point(0,3), Point(0,2), Point(0,1), Point(0,0))
    }
}