package be.fgov.sfpd.kata.aoc22.day5

import be.fgov.sfpd.kata.aoc22.day5.ProcedureCommand.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test

class CargoCraneTest {

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

sealed class ProcedureCommand(val amountOfCrates: Int, val origin: Int, val destination: Int) {
    class Rearrange(amountOfCrates: Int, origin: Int, destination: Int) : ProcedureCommand(amountOfCrates, origin, destination)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProcedureCommand

        if (amountOfCrates != other.amountOfCrates) return false
        if (origin != other.origin) return false
        if (destination != other.destination) return false

        return true
    }
    override fun hashCode(): Int {
        var result = amountOfCrates
        result = 31 * result + origin
        result = 31 * result + destination
        return result
    }
    override fun toString(): String = this::class.simpleName.toString()+"($amountOfCrates, $origin, $destination)"
}

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
        .groupBy(Pair<Int,String>::first) { it.second }
        .toSortedMap()
}

fun parseCrateOrNull(crate: String) =
    if (Regex("""\[\w\].*""").matches(crate)) crate.trim().drop(1).dropLast(1)
    else null


typealias ID = Int
typealias Stack<T> = List<T>
typealias Crate = String

