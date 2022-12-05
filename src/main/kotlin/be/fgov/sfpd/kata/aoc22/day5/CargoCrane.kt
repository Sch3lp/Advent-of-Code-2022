package be.fgov.sfpd.kata.aoc22.day5

data class Ship(
    val loadingDeck: Map<ID, Stack<Crate>>,
    private val crateMover: CrateMover = CrateMover9000,
) {
    fun topCrates(): String =
        loadingDeck.map { (_, crates) -> crates.firstOrNull() ?: " " }.joinToString("")

    fun execute(procedure: List<Rearrange>): Ship =
        procedure.foldIndexed(also { println(it.visualize()) }) { idx, (loadingDeck), rearrange ->
            println("Step: ${idx + 1}")
            println("####### Moving ${rearrange.amountOfCrates} from ${rearrange.origin} to ${rearrange.destination} #######")
            val originStack = loadingDeck.getValue(rearrange.origin)
            val destinationStack = loadingDeck.getValue(rearrange.destination)
            val cratesInTransit = originStack.take(rearrange.amountOfCrates)
            val remainingOrigin = originStack.drop(rearrange.amountOfCrates)
            copy(loadingDeck = loadingDeck.mapValues { (k, v) ->
                when (k) {
                    rearrange.origin -> remainingOrigin
                    rearrange.destination -> listOf(crateMover(cratesInTransit), destinationStack).flatten()
                    else -> v
                }
            }).also { println(it.visualize()) }
        }

    fun visualize(): String {
        val biggestStackSize = loadingDeck.values.maxOf { it.size }
        return (0..biggestStackSize).reversed().map { level ->
            loadingDeck.map { (_, stack) ->
                stack.reversed().getOrNull(level)?.let { "[$it]" } ?: "   "
            }.joinToString(" ")
        }.joinToString("\n") + "\n" + loadingDeck.keys.joinToString(" ") { " $it " }
    }
}

data class Rearrange(val amountOfCrates: Int, val origin: ID, val destination: ID)

object Parsing {
    fun parseToRearrangementProcedure(input: String): RearrangementProcedure {
        return input.lines().mapNotNull { line ->
            val match = Regex(""".*move (\d+) from (\d) to (\d)""").matchEntire(line)
            match?.groupValues?.let { (_, amount, origin, destination) ->
                Rearrange(amount.toInt(), origin.toInt(), destination.toInt())
            }
        }
    }

    fun parseToShip(input: String, crateMover9000: CrateMover): Ship {
        val loadingDeck = input.lines().map { line ->
            line.chunked(4)
                .mapIndexedNotNull { idx, crate ->
                    parseCrateOrNull(crate)?.let { (idx + 1) to it }
                }
        }.flatten()
            .groupBy(Pair<Int, String>::first) { it.second }
            .mapValues { (_, v) -> v.toMutableList() }
            .toSortedMap()
        return Ship(loadingDeck, crateMover9000)
    }

    private fun parseCrateOrNull(crate: String): String? {
        return if (Regex("""\[\w].*""").matches(crate)) crate.trim().drop(1).dropLast(1)
        else null
    }

}
val CrateMover9000: CrateMover = { cratesInTransit -> cratesInTransit.reversed() }
val CrateMover9001: CrateMover = { cratesInTransit -> cratesInTransit }

typealias CrateMover = (List<Crate>) -> List<Crate>

typealias ID = Int
typealias Crate = String
typealias Stack<T> = List<T>
typealias RearrangementProcedure = List<Rearrange>