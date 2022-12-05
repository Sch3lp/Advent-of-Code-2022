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

            val ship = Parsing.parseToShip(input, CrateMover9000)
            assertThat(ship.loadingDeck).containsExactly(
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

            val procedure = Parsing.parseToRearrangementProcedure(input)
            assertThat(procedure).containsExactly(
                Rearrange(1, 2, 1),
                Rearrange(3, 1, 3),
                Rearrange(2, 2, 1),
                Rearrange(1, 1, 2),
            )
        }
    }

    @Nested
    inner class VisualizationTests {
        @Test
        fun `can visualize a ship`() {
            val shipInput = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3"""
            assertThat(Parsing.parseToShip(shipInput, CrateMover9000).visualize()).isEqualToIgnoringWhitespace(shipInput)
        }
    }

    @Nested
    inner class RearrangementTests {
        @Test
        fun `moving 1 crate to an empty stack`() {
            val ship = Ship(mapOf(1 to listOf("D"), 2 to listOf()))
            val actual = ship.execute(listOf(Rearrange(1, 1, 2)))

            assertThat(actual).isEqualTo(Ship(mapOf(1 to listOf(), 2 to listOf("D"))))
        }

        @Test
        fun `moving 1 crate of an empty stack to a stack with 1 crate, moves nothing`() {
            val ship = Ship(mapOf(1 to listOf("D"), 2 to listOf()))
            val actual = ship.execute(listOf(Rearrange(1, 2, 1)))

            assertThat(actual).isEqualTo(Ship(mapOf(1 to listOf("D"), 2 to listOf())))
        }
    }
}