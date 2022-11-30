package be.fgov.sfpd.kata.aoc22.day1

import be.fgov.sfpd.kata.aoc22.day1.DepthChange.INCREASED
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SonarSweepTest {
   
    @Test
    fun `given a sweep report, we measure the depth changes`() {
        val report = listOf(1, 2, 3)
        val result = report.measure()
        assertThat(result).containsExactly(INCREASED, INCREASED)
    }

    @Test
    fun `given a sweep report, we can count the amount of increases`() {
        val report = listOf(1, 2, 3)

        val result = report.measure().countNumberOfDepthIncreases()

        assertThat(result).isEqualTo(2)
    }
    
    @Test
    fun `given an input file (actual report) we can create a list of depths`() {
        val report: String = readFile("day1/exampleInput.txt")
        
        val actual: List<Int> = report.toDepths()

        assertThat(actual).containsExactly(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
    }
}
