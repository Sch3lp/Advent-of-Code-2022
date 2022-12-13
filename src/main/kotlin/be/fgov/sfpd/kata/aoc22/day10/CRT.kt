package be.fgov.sfpd.kata.aoc22.day10

import be.fgov.sfpd.kata.aoc22.Debugging.debug
import be.fgov.sfpd.kata.aoc22.Point

typealias Cycle = Int
typealias X = Int

object CRT {

    fun signalStrength(input: String): Int {
        return cycles(input)
            .debug { "$it" }
            .mapValues { (k, v) -> v * k }
            .filterKeys { cycle -> cycle in listOf(20, 60, 100, 140, 180, 220) }
            .debug { "$it" }
            .values.sum()
    }


    fun cycles(input: String): Map<Cycle, X> {
        var x = 1
        var cycles = 1
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

    fun drawImage(input: String): String {
        val cycles = cycles(input).debug { "cycles: $it" }
        val pixelsToDraw = cycles.filter { (cycle, x) -> (cycle-1)%40 in (x-1)..(x+1) }.keys
        return draw(pixelsToDraw)
    }

    private fun draw(pixelsToDraw: Set<Cycle>): String {
        val pixelsAsPoints = pixelsToDraw.map { pixel ->
            when (pixel) {
                in 1..40 -> Point(pixel, 1)
                in 41..80 -> Point(pixel - 40, 2)
                in 81..120 -> Point(pixel - 80, 3)
                in 121..160 -> Point(pixel - 120, 4)
                in 161..200 -> Point(pixel - 160, 5)
                in 201..240 -> Point(pixel - 200, 6)
                else -> error("pixel $pixel is out of CRT boundary")
            }
        }.debug { "pixelsAsPoints: $it" }
        return (1..6).joinToString("\n") { y ->
            (1..40).joinToString("") { x ->
                pixelsAsPoints.firstOrNull { it == Point(x, y) }
                    ?.let { "⚪️" }
                    ?: "⚫️"
            }
        }
    }
}
