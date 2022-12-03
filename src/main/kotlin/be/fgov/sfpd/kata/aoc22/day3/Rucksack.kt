package be.fgov.sfpd.kata.aoc22.day3

typealias Rucksack = String
typealias ItemType = Char
typealias Priority = Int

val Rucksack.priority get(): Int {
    val (compartment1, compartment2) = compartments()
    val duplicateItem = compartment1.first { itemType -> itemType in compartment2 }
    return duplicateItem.priority()
}
private fun Rucksack.compartments() = toList().chunked(length / 2)
private fun ItemType.priority() = priorityMap[this] ?: 0
private val priorityMap: Map<ItemType, Priority> =
    (('a'..'z') + ('A'..'Z'))
        .mapIndexed { idx, char -> char to idx + 1 }
        .toMap()