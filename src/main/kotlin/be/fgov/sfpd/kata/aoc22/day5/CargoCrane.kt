package be.fgov.sfpd.kata.aoc22.day5

fun Ship.topCrates(): String =
    map { (_, crates) -> crates.firstOrNull() ?: " " }.joinToString("")

fun execute(ship: Ship, procedure: List<Rearrange>, crateMover: CrateMover = CrateMover9000): Ship =
    procedure.foldIndexed(ship.also { println(it.visualize()) }) { idx, acc, rearrange ->
        println("Step: ${idx+1}")
        println("####### Moving ${rearrange.amountOfCrates} from ${rearrange.origin} to ${rearrange.destination} #######")
        val originStack = acc.getValue(rearrange.origin)
        val destinationStack = acc.getValue(rearrange.destination)
        val cratesInTransit = originStack.take(rearrange.amountOfCrates)
        val remainingOrigin = originStack.drop(rearrange.amountOfCrates)
        acc.mapValues { (k, v) ->
            when (k) {
                rearrange.origin -> remainingOrigin
                rearrange.destination -> listOf(crateMover(cratesInTransit), destinationStack).flatten()
                else -> v
            }
        }.also { println(it.visualize()) }
    }

data class Rearrange(val amountOfCrates: Int, val origin: ID, val destination: ID)

fun parseToRearrangementProcedure(input: String): List<Rearrange> {
    return input.lines().mapNotNull { line ->
        val match = Regex(""".*move (\d+) from (\d) to (\d)""").matchEntire(line)
        match?.groupValues?.let { (_, amount, origin, destination) ->
            Rearrange(amount.toInt(), origin.toInt(), destination.toInt())
        }
    }
}

fun parseToShip(input: String): Ship {
    return input.lines().map { line ->
        line.chunked(4)
            .mapIndexedNotNull { idx, crate ->
                parseCrateOrNull(crate)?.let { (idx + 1) to it }
            }
    }.flatten()
        .groupBy(Pair<Int, String>::first) { it.second }
        .mapValues { (_, v) -> v.toMutableList() }
        .toSortedMap()
}

fun parseCrateOrNull(crate: String): String? {
    return if (Regex("""\[\w].*""").matches(crate)) crate.trim().drop(1).dropLast(1)
    else null
}

fun Ship.visualize(): String {
    val biggestStackSize = values.maxOf { it.size }
    return (0..biggestStackSize).reversed().map { level ->
        map { (_, stack) ->
            stack.reversed().getOrNull(level)?.let { "[$it]" } ?: "   "
        }.joinToString(" ")
    }.joinToString("\n") + "\n"+keys.joinToString(" "){ " $it "}
}

typealias CrateMover = (List<Crate>) -> List<Crate>
val CrateMover9000 : CrateMover = { cratesInTransit -> cratesInTransit.reversed() }
val CrateMover9001 : CrateMover = { cratesInTransit -> cratesInTransit }

typealias ID = Int
typealias Crate = String
typealias Stack<T> = List<T>
typealias Ship = Map<ID, Stack<Crate>>
