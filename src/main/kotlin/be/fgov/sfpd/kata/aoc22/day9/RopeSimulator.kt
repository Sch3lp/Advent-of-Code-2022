package be.fgov.sfpd.kata.aoc22.day9

import be.fgov.sfpd.kata.aoc22.Point


data class Rope(
    val head: Point = Point(0,0),
    private val tail: Point = Point(0,0),
    private val broadcast: (Point) -> Unit = {}
) {
    fun pull(commands: List<PullCommand>): Rope =
        commands.fold(this) { acc, cmd -> acc.pull(cmd) }

    fun pull(pullCommand: PullCommand): Rope {
        val newHead = head + pullCommand.toVector()
        return if (newHead == tail || tail in newHead.neighbours) {
            copy(head = newHead)
        } else {
            val headMovements = (head until newHead)
            broadcastTail(headMovements)
            val newTail = headMovements.last()
            copy(head = newHead, tail = newTail)
        }
    }

    private fun broadcastTail(headMovements: Set<Point>) {
        val movementsToNotBroadcast = tail.neighbours.intersect(headMovements).size - 1
        headMovements.drop(movementsToNotBroadcast).onEach(broadcast)
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