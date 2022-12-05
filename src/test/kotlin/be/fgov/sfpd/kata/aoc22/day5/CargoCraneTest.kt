package be.fgov.sfpd.kata.aoc22.day5

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CargoCraneTest {

    @Nested
    inner class ParsingTests {

        @Test
        fun `Can parse from input to crate stacks`() {
            val input = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3
 
 move 1 from 2 to 1
 move 3 from 1 to 3
 move 2 from 2 to 1
 move 1 from 1 to 2
        """.trimIndent()

            val stacks = parseToStacks(input)
            assertThat(stacks).containsExactly(
                entry(1, listOf("N", "Z")),
                entry(2, listOf("D", "C", "M")),
                entry(3, listOf("P")),
            )
        }

        @Test
        fun `Can parse from input to rearrangement procedure`() {
            val input = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3
 
 move 1 from 2 to 1
 move 3 from 1 to 3
 move 2 from 2 to 1
 move 1 from 1 to 2"""

            val procedure = parseToRearrangementProcedure(input)
            assertThat(procedure).containsExactly(
                Rearrange(1, 2, 1),
                Rearrange(3, 1, 3),
                Rearrange(2, 2, 1),
                Rearrange(1, 1, 2),
            )
        }
    }

    @Nested
    inner class RearrangementTests {
        @Test
        fun `moving 1 crate to an empty stack`() {
            val ship : Map<ID, Stack<Crate>> = mapOf(1 to listOf("D"), 2 to listOf())

            val actual : Map<ID, Stack<Crate>> = execute(ship, listOf(Rearrange(1, 1, 2)))

            assertThat(actual).isEqualTo(mapOf(1 to listOf(), 2 to listOf("D")))
        }

        @Test
        fun `moving 0 crate to a stack with 1 crate, moves nothing`() {
            val map : Map<ID, Stack<Crate>> = mapOf(1 to listOf("D"), 2 to listOf())

            val actual : Map<ID, Stack<Crate>> = execute(map, listOf(Rearrange(1, 2, 1)))

            assertThat(actual).isEqualTo(mapOf(1 to listOf("D"), 2 to listOf()))
        }
    }
}

private fun execute(ship: Map<ID, Stack<Crate>>, procedure: List<Rearrange>): Map<ID, Stack<Crate>> {
    return procedure.fold(ship) { acc, rearrange ->
        val originStack = ship.getValue(rearrange.origin)
        val destinationStack = ship.getValue(rearrange.destination)
        val cratesInTransit = originStack.take(rearrange.amountOfCrates)
        val remainingOrigin = originStack.drop(rearrange.amountOfCrates)
        ship.mapValues { (k,v) ->
            when (k) {
                rearrange.origin -> remainingOrigin
                rearrange.destination -> destinationStack + cratesInTransit.reversed()
                else -> v
            }
        }
    }
}

data class Rearrange(val amountOfCrates: Int, val origin: ID, val destination: ID)

fun parseToRearrangementProcedure(input: String): List<Rearrange> {
    return input.lines().mapNotNull { line ->
        val match = Regex(""".*move (\d) from (\d) to (\d)""").matchEntire(line)
        match?.groupValues?.let { (_, amount, origin, destination) ->
            Rearrange(amount.toInt(), origin.toInt(), destination.toInt())
        }
    }
}

fun parseToStacks(input: String): Map<ID, Stack<Crate>> {
    return input.lines().map { line ->
        line.chunked(4)
            .mapIndexedNotNull { idx, crate ->
                parseCrateOrNull(crate)?.let { (idx + 1) to it }
            }
    }.flatten()
        .groupBy(Pair<Int, String>::first) { it.second }
        .mapValues { (_,v) -> v.toMutableList() }
        .toSortedMap()
}

fun parseCrateOrNull(crate: String): String? {
    return if (Regex("""\[\w].*""").matches(crate)) crate.trim().drop(1).dropLast(1)
    else null
}


typealias ID = Int
typealias Stack<T> = List<T>
typealias Crate = String

