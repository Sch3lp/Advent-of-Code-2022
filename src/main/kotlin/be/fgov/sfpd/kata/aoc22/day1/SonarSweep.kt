package be.fgov.sfpd.kata.aoc22.day1

import be.fgov.sfpd.kata.aoc22.day1.DepthChange.*

fun solution1(input: String) = input.toDepths().measure().countNumberOfDepthIncreases()

fun String.toDepths(): List<Int> = lines().map { depth -> depth.toInt() }
fun List<DepthChange>.countNumberOfDepthIncreases(): Int = count { it == INCREASED }
fun List<Int>.measure(): List<DepthChange> =
    zipWithNext { previous, current -> if (current > previous) INCREASED else DECREASED }

enum class DepthChange {
    INCREASED,
    DECREASED
}