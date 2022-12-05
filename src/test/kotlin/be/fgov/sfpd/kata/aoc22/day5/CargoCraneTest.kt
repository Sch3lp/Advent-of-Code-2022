package be.fgov.sfpd.kata.aoc22.day5

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
}

private fun parseToStacks(input: String): Map<ID, Stack<Crate>> {
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

