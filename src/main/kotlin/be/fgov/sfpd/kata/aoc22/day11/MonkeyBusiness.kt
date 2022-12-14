package be.fgov.sfpd.kata.aoc22.day11

import be.fgov.sfpd.kata.aoc22.Debugging.debug

class MonkeyInspector(
    private val inspectedItemsPerMonkey: MutableMap<MonkeyId, Int> = mutableMapOf()
) {
    fun inspect(monkeyId: MonkeyId, inspectedItems: Int) {
        val updatedInspectedItems = inspectedItemsPerMonkey[monkeyId]?.let { it + inspectedItems } ?: inspectedItems
        inspectedItemsPerMonkey += monkeyId to updatedInspectedItems
    }

    fun top(top: Int): List<Int> = inspectedItemsPerMonkey.debug { "inspectedItems per monkey: $it" }.values.sorted().takeLast(top).reversed()
}

class MonkeyBusiness private constructor() {
    private val monkeys: MutableList<Monkey> = mutableListOf()

    fun play(rounds: Int, monkeyInspector: MonkeyInspector) =
        repeat(rounds) { round(monkeyInspector) }

    private fun round(monkeyInspector: MonkeyInspector) {
        monkeys.forEach {
            val itemsThrownAtMonkeys: List<Pair<MonkeyId, Int>> = it.inspectAndThrowItems(monkeyInspector)
            itemsThrownAtMonkeys.forEach { (monkeyId, item) ->
                monkeys[monkeyId].catch(item)
            }
        }
    }

    companion object {
        fun monkeyBusiness(setup: MonkeyBusiness.() -> Unit) =
            MonkeyBusiness().apply { setup() }

        fun MonkeyBusiness.monkey(
            id: MonkeyId,
            startingItems: List<Int>,
            operation: (Int) -> Int,
            test: (Int) -> Boolean,
            onTrue: MonkeyId,
            onFalse: MonkeyId,
        ) {
            monkeys.add(Monkey(id, startingItems.toMutableList(), operation, test, onTrue, onFalse))
        }
    }
}

typealias MonkeyId = Int

data class Monkey(
    val id: MonkeyId,
    val items: MutableList<Int>,
    val worryLevelOperation: (Int) -> Int,
    val test: (Int) -> Boolean,
    val trueMonkey: MonkeyId,
    val falseMonkey: MonkeyId,
) {
    fun inspectAndThrowItems(monkeyInspector: MonkeyInspector): List<Pair<MonkeyId, Int>> =
        items.map { item ->
            monkeyInspector.inspect(id, 1)
            val newWorryLevel = worryLevelOperation(item) / 3
            if (test(newWorryLevel)) newWorryLevel throwTo trueMonkey
            else newWorryLevel throwTo falseMonkey
        }.also { items.clear() }

    fun catch(item: Int) = items.add(item)

    private infix fun Int.throwTo(monkeyId: MonkeyId) =
        monkeyId to this
}

infix fun Int.isDivisibleBy(other: Int) = this % other == 0
