package be.fgov.sfpd.kata.aoc22.day9

import be.fgov.sfpd.kata.aoc22.Point
import kotlin.math.sign

data class Rope(
    private var head: Point = Point(0, 0),
    private var tail: Point = Point(0, 0),
    private val broadcast: (Point) -> Unit = {},
    val id: String = "H",
) {
    val start get() = head
    val end get() = tail

    fun pull(commands: List<PullCommand>): Unit =
        commands.forEach { cmd -> pull(cmd) }

    fun pull(pullCommand: PullCommand) {
        pullCommand.normalized().forEach { command ->
            val newHead = head + command.toVector()
            follow(newHead)
        }
    }

    fun follow(pointToFollow: Point) {
        head = pointToFollow
        if (tail != head && tail !in pointToFollow.neighbours) {
            val vectorToPoint = if (tail.x == pointToFollow.x) Point(0, (pointToFollow.y - tail.y).sign)
            else if (tail.y == pointToFollow.y) Point((pointToFollow.x - tail.x).sign, 0)
            else Point((pointToFollow.x - tail.x).sign, (pointToFollow.y - tail.y).sign)
            tail += vectorToPoint
            broadcast(tail)
        }
    }
}

sealed interface PullCommand {
    val steps: Int
    fun toVector() = when (this) {
        is Up -> Point(0, 1)
        is Down -> Point(0, -1)
        is Right -> Point(1, 0)
        is Left -> Point(-1, 0)
    } * steps

    fun normalized(): List<PullCommand> = (1..steps).map {
        when (this) {
            is Up -> Up(1)
            is Down -> Down(1)
            is Left -> Left(1)
            is Right -> Right(1)
        }
    }

    companion object {
        fun fromLines(input: String) = input.lines().map { line -> fromLine(line) }
        private fun fromLine(line: String) = line.split(" ").let { (command, steps) ->
            when (command) {
                "U" -> Up(steps.toInt())
                "D" -> Down(steps.toInt())
                "L" -> Left(steps.toInt())
                "R" -> Right(steps.toInt())
                else -> error("Could not parse $line to a MoveCommand")
            }
        }
    }
}

data class Up(override val steps: Int) : PullCommand
data class Down(override val steps: Int) : PullCommand
data class Left(override val steps: Int) : PullCommand
data class Right(override val steps: Int) : PullCommand