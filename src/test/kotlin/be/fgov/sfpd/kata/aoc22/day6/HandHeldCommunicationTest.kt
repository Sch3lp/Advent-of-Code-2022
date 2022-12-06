package be.fgov.sfpd.kata.aoc22.day6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class HandHeldCommunicationTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
mjqjpqmgbljsphdztnvjfqwrcgsmlb,7
bvwbjplbgvbhsrlpgdmjqwftvncz,5
nppdvjthqldpwncqszvftbrmjlhg,6
nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,10
zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,11""")
    fun exampleInput(datastream: CharSequence, expectedPosition: Int) {
        val (position,marker) = CommSystem().findStartOfPacketMarker(datastream.asSequence())
        assertThat(position).isEqualTo(expectedPosition)
    }
}