package be.fgov.sfpd.kata.aoc22.day12

import be.fgov.sfpd.kata.aoc22.Debugging.withDebugging
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HeightMapTest {
    @Test
    fun `example input's bfs returns 31`() {
        val input = readFile("day12/exampleInput.txt")
        withDebugging {
            val actual = HeightMap(input).bfs()
            assertThat(actual).isEqualTo(31)
        }
    }
}