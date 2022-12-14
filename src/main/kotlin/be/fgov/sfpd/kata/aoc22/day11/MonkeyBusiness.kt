package be.fgov.sfpd.kata.aoc22.day11

import be.fgov.sfpd.kata.aoc22.Debugging.debug

class MonkeyInspector(
    private val inspectedItemsPerMonkey: MutableMap<MonkeyId, Long> = mutableMapOf()
) {
    fun inspect(monkeyId: MonkeyId, inspectedItems: Long) {
        val updatedInspectedItems = inspectedItemsPerMonkey[monkeyId]?.let { it + inspectedItems } ?: inspectedItems
        inspectedItemsPerMonkey += monkeyId to updatedInspectedItems
    }

    fun top(top: Int): List<Long> =
        inspectedItemsPerMonkey.debug { "inspectedItems per monkey: $it" }.values.sorted().takeLast(top).reversed()

    fun print(round: Int): String {
        return "== After round $round ==\n" +
                inspectedItemsPerMonkey.entries
                    .joinToString("\n") { (monkey, items) -> "Monkey $monkey inspected items $items times." }
    }
}

class MonkeyBusiness private constructor(
    private val worryCompensator: Boolean
) {
    private val monkeys: MutableList<Monkey> = mutableListOf()

    fun play(rounds: Int, monkeyInspector: MonkeyInspector) =
        repeat(rounds) { round ->
            playRound(monkeyInspector)
            if (round in listOf(1, 20) + (1..10).map { it * 1000 }) {
                monkeyInspector.debug { it.print(round)+"\n" }
            }
        }

    private fun playRound(monkeyInspector: MonkeyInspector) {
        monkeys.forEach {
            val itemsThrownAtMonkeys: List<Pair<MonkeyId, Long>> = it.inspectAndThrowItems(monkeyInspector)
            itemsThrownAtMonkeys.forEach { (monkeyId, item) ->
                monkeys[monkeyId].catch(item)
            }
        }
    }

    companion object {
        fun monkeyBusiness(worryCompensator: Boolean = true, setup: MonkeyBusiness.() -> Unit) =
            MonkeyBusiness(worryCompensator).apply { setup() }

        fun MonkeyBusiness.monkey(
            id: MonkeyId,
            startingItems: List<Int>,
            operation: (Long) -> Long,
            test: (Long) -> Boolean,
            onTrue: MonkeyId,
            onFalse: MonkeyId,
        ) {
            monkeys.add(
                Monkey(
                    id,
                    startingItems.map { it.toLong() }.toMutableList(),
                    operation,
                    test,
                    onTrue,
                    onFalse,
                    worryCompensator,
                ).debug { "added Monkey ${it.id} with worryCompensator = $worryCompensator\n" }
            )
        }
    }
}

typealias MonkeyId = Int

data class Monkey(
    val id: MonkeyId,
    val items: MutableList<Long>,
    val worryLevelOperation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val trueMonkey: MonkeyId,
    val falseMonkey: MonkeyId,
    val worryCompensator: Boolean,
) {

    fun inspectAndThrowItems(monkeyInspector: MonkeyInspector): List<Pair<MonkeyId, Long>> =
        items.map { item ->
            monkeyInspector.inspect(id, 1)
            val newWorryLevel = if (worryCompensator) worryLevelOperation(item) / 3 else worryLevelOperation(item)
            if (test(newWorryLevel)) newWorryLevel throwTo trueMonkey
            else newWorryLevel throwTo falseMonkey
        }.also { items.clear() }

    fun catch(item: Long) = items.add(item)

    private infix fun Long.throwTo(monkeyId: MonkeyId) =
        monkeyId to this
}

infix fun Long.isDivisibleBy(other: Int) = this % other == 0L
