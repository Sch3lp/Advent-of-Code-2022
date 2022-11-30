package be.fgov.sfpd.kata.aoc22

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PointTest {

    @Test
    fun `given two point - we can add them together`() {
        val origin = Point(0, 0)
        val vector = Point(-1, 1)

        val actual = origin + vector

        assertThat(actual).isEqualTo(Point(-1, 1))
    }
    

}