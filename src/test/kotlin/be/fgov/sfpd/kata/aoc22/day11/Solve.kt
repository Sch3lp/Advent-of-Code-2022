package be.fgov.sfpd.kata.aoc22.day11

import be.fgov.sfpd.kata.aoc22.Debugging.withDebugging
import be.fgov.sfpd.kata.aoc22.day11.MonkeyBusiness.Companion.monkey
import be.fgov.sfpd.kata.aoc22.day11.MonkeyBusiness.Companion.monkeyBusiness
import be.fgov.sfpd.kata.aoc22.lcm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        withDebugging {
            assertThat(solve1(exampleInput)).isEqualTo(10605L)
        }
    }

    @Test
    fun `actual input part 1`() {
        assertThat(solve1(actualInput)).isEqualTo(55944L)
    }

    @Test
    fun `example input part 2`() {
        withDebugging {
            assertThat(solve2(exampleInput, listOf(23, 19, 13, 17).lcm())).isEqualTo(2713310158L)
        }
    }

    @Test
    fun `actual input part 2`() {
        assertThat(solve2(actualInput, listOf(3, 17, 2, 19, 11, 5, 13, 7).lcm())).isEqualTo(15117269860L)
    }

    private fun solve1(input: MonkeyBusiness.() -> Unit): Long {
        val monkeyInspector = MonkeyInspector()
        monkeyBusiness(setup = input).play(20, monkeyInspector)
        val (mostActiveMonkey, secondMostActiveMonkey) = monkeyInspector.top(2)
        return mostActiveMonkey * secondMostActiveMonkey
    }

    private fun solve2(input: MonkeyBusiness.() -> Unit, commonModulus: Int): Long {
        val monkeyInspector = MonkeyInspector()
        val commonModulusCompensator: (Long) -> Long = { it % commonModulus }
        monkeyBusiness(worryCompensator = commonModulusCompensator, setup = input).play(10_000, monkeyInspector)
        val (mostActiveMonkey, secondMostActiveMonkey) = monkeyInspector.top(2)
        return mostActiveMonkey * secondMostActiveMonkey
    }

    private val exampleInput: MonkeyBusiness.() -> Unit = {
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


    private val actualInput: MonkeyBusiness.() -> Unit = {
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

}