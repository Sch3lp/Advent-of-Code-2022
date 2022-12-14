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
                startingItems = listOf(66, 71, 94),
                operation = { old -> old * 5 },
                test = { it isDivisibleBy 3 },
                onTrue = 7,
                onFalse = 4,
            )

            monkey(
                id = 1,
                startingItems = listOf(70),
                operation = { old -> old + 6 },
                test = { it isDivisibleBy 17 },
                onTrue = 3,
                onFalse = 0,
            )

            monkey(
                id = 2,
                startingItems = listOf(62, 68, 56, 65, 94, 78),
                operation = { old -> old + 5 },
                test = { it isDivisibleBy 2 },
                onTrue = 3,
                onFalse = 1,
            )

            monkey(
                id = 3,
                startingItems = listOf(89, 94, 94, 67),
                operation = { old -> old + 2 },
                test = { it isDivisibleBy 19 },
                onTrue = 7,
                onFalse = 0,
            )

            monkey(
                id = 4,
                startingItems = listOf(71, 61, 73, 65, 98, 98, 63),
                operation = { old -> old * 7 },
                test = { it isDivisibleBy 11 },
                onTrue = 5,
                onFalse = 6,
            )

            monkey(
                id = 5,
                startingItems = listOf(55, 62, 68, 61, 60),
                operation = { old -> old + 7 },
                test = { it isDivisibleBy 5 },
                onTrue = 2,
                onFalse = 1,
            )

            monkey(
                id = 6,
                startingItems = listOf(93, 91, 69, 64, 72, 89, 50, 71),
                operation = { old -> old + 1 },
                test = { it isDivisibleBy 13 },
                onTrue = 5,
                onFalse = 2,
            )

            monkey(
                id = 7,
                startingItems = listOf(76, 50),
                operation = { old -> old * old },
                test = { it isDivisibleBy 7 },
                onTrue = 4,
                onFalse = 6,
            )
        }

        assertThat(solve1(input)).isEqualTo(55944)
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