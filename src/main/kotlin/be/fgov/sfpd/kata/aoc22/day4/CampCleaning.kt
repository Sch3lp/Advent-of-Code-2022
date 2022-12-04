package be.fgov.sfpd.kata.aoc22.day4

object CampCleaning {
    fun fullyContainedPairs(input: String): Int = input.lines().map { line ->
        val (elf1, elf2) = line.split(",").map { assignments ->
            val (assignment1, assignment2) = assignments.split("-")
            (assignment1.toInt()..assignment2.toInt()).toList()
        }
        if (elf1.containsAll(elf2) || elf2.containsAll(elf1)) 1 else 0
    }.sum()

    fun partiallyContainedPairs(input: String): Int {
        return input.lines().map { line ->
            val (elf1, elf2) = line.split(",").map { assignments ->
                val (assignment1, assignment2) = assignments.split("-")
                (assignment1.toInt()..assignment2.toInt()).toList()
            }
            if (elf1.any { elf2.contains(it) } || elf2.any { elf1.contains(it) }) 1 else 0
        }.sum()
    }
}