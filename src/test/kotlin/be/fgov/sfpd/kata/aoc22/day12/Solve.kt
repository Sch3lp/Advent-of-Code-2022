package be.fgov.sfpd.kata.aoc22.day12

import be.fgov.sfpd.kata.aoc22.Debugging.withDebugging
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day12/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(31)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day12/input.txt")
        assertThat(solve1(input)).isEqualTo(497)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day12/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(29)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day12/input.txt")
        assertThat(solve2(input))
            .isLessThan(504)
            .isEqualTo(492)
    }

    private fun solve1(input: String): Int = withDebugging(false) { HeightMap(input).bfs() }

    private fun solve2(input: String): Int {
        val heightMap = HeightMap(input)
        return heightMap.bfs(to = Elevation('a'))
    }
}