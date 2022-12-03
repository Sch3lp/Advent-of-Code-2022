package be.fgov.sfpd.kata.aoc22.day3

typealias Priority = Int
typealias Group = List<Rucksack>
class Rucksacks(input: String) {
    private val rucksacks: List<Rucksack> = input.lines().map { Rucksack(it) }
    val totalPriority: Int get() = rucksacks.sumOf { rucksack -> rucksack.priority }
    val totalPriorityOfBadges: Int get() = findGroupBadges().sumOf { it.priority }

    fun findGroupBadges(): List<ItemType> {
        val groups: List<Group> = rucksacks.chunked(3)
        return groups.map { group -> group.findBadge() }
    }

    private fun Group.findBadge(): ItemType {
        val allUniqueItems = this.flatMap { rucksack -> rucksack.items.toList().distinct() }
        val itemCounts = allUniqueItems.groupBy { it }.mapValues { (_, items) -> items.count() }
        return ItemType(itemCounts.filterValues { count -> count == 3 }.keys.first())
    }

}

class Rucksack(val items: String) {
    private val compartments = items.toList().chunked(items.length / 2)

    val priority get(): Priority {
        val (compartment1, compartment2) = compartments
        val duplicateItem = ItemType(compartment1.first { itemType -> itemType in compartment2 })
        return duplicateItem.priority
    }
}

@JvmInline value class ItemType(private val item: Char){
    val priority get() = priorityMap[item] ?: 0
}

private val priorityMap: Map<Char, Priority> =
    (('a'..'z') + ('A'..'Z'))
        .mapIndexed { idx, char -> char to idx + 1 }
        .toMap()