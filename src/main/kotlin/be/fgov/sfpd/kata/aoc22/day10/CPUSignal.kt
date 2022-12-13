package be.fgov.sfpd.kata.aoc22.day10

typealias Cycle = Long
typealias X = Long

fun cycles(input: String): Map<Cycle, X> {
    var x = 1L
    var cycles = 1L
    val cycleMap = mutableMapOf<Cycle, X>()

    fun cycle() {
        cycleMap[cycles] = x
        cycles++
    }

    input.lines().forEach { line ->
        if (line == "noop") {
            cycle()
        } else {
            cycle(); cycle()
            val inc = line.substringAfter(" ").toInt()
            x += inc
        }
    }

    return cycleMap
}