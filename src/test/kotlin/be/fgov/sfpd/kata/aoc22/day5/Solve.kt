package be.fgov.sfpd.kata.aoc22.day5

import be.fgov.sfpd.kata.aoc22.day5.Parsing.parseToRearrangementProcedure
import be.fgov.sfpd.kata.aoc22.day5.Parsing.parseToShip
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day5/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo("CMZ")
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day5/input.txt")
        assertThat(solve1(input))
            .isNotEqualTo("RHBNBHWD ")
            .isNotEqualTo("QNRMVDDR ")
            .isEqualTo("GRTSWNJHH")
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day5/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo("MCD")
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day5/input.txt")
        assertThat(solve2(input)).isEqualTo("QLFQDBBHM")
    }

    private fun solve1(input: String): String {
        val ship = parseToShip(input, CrateMover9000)
        val procedure = parseToRearrangementProcedure(input)
        return ship.execute(procedure).topCrates()
    }

    private fun solve2(input: String): String {
        val ship = parseToShip(input, CrateMover9001)
        val procedure = parseToRearrangementProcedure(input)
        return ship.execute(procedure).topCrates()
    }
}



