package be.fgov.sfpd.kata.aoc22.day11

import be.fgov.sfpd.kata.aoc22.Debugging
import be.fgov.sfpd.kata.aoc22.day11.MonkeyBusiness.Companion.monkey
import be.fgov.sfpd.kata.aoc22.day11.MonkeyBusiness.Companion.monkeyBusiness
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = monkeyBusiness {
            monkey(
                id = 0,
                startingItems = listOf(79, 98),
                operation = { old -> old * 19 },
                test = { it isDivisibleBy 23 },
                onTrue = 2,
                onFalse = 3,
            )
            monkey(
                id = 1,
                startingItems = listOf(54, 65, 75, 74),
                operation = { old -> old + 6 },
                test = { it isDivisibleBy 19 },
                onTrue = 2,
                onFalse = 0,
            )
            monkey(
                id = 2,
                startingItems = listOf(79, 60, 97),
                operation = { old -> old * old },
                test = { it isDivisibleBy 13 },
                onTrue = 1,
                onFalse = 3,
            )
            monkey(
                id = 3,
                startingItems = listOf(74),
                operation = { old -> old + 3 },
                test = { it isDivisibleBy 17 },
                onTrue = 0,
                onFalse = 1,
            )
        }
        Debugging.enable()
        assertThat(solve1(input)).isEqualTo(10605)
        Debugging.disable()
    }

    @Test
    fun `actual input part 1`() {
        val input = monkeyBusiness {
            monkey(
                id = 0,
                startingItems = listOf(79, 98),
                operation = { old -> old * 19 },
                test = { it isDivisibleBy 23 },
                onTrue = 2,
                onFalse = 3,
            )
            monkey(
                id = 1,
                startingItems = listOf(54, 65, 75, 74),
                operation = { old -> old + 6 },
                test = { it isDivisibleBy 19 },
                onTrue = 2,
                onFalse = 0,
            )
            monkey(
                id = 2,
                startingItems = listOf(79, 60, 97),
                operation = { old -> old * old },
                test = { it isDivisibleBy 13 },
                onTrue = 1,
                onFalse = 3,
            )
            monkey(
                id = 3,
                startingItems = listOf(74),
                operation = { old -> old + 3 },
                test = { it isDivisibleBy 17 },
                onTrue = 0,
                onFalse = 1,
            )
        }

        assertThat(solve1(input)).isEqualTo(11820)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day11/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(11)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day11/input.txt")
        assertThat(solve2(input)).isEqualTo(11)
    }

    private fun solve1(monkeyBusiness: MonkeyBusiness): Int {
        val monkeyInspector = MonkeyInspector()
        monkeyBusiness.play(20, monkeyInspector)
        val (mostActiveMonkey, secondMostActiveMonkey) = monkeyInspector.top(2)
        return mostActiveMonkey * secondMostActiveMonkey
    }

    private fun solve2(input: String): Int = TODO()
}