package be.fgov.sfpd.kata.aoc22.day1

fun elfCalories(input: String) = sequence {
    val currentCalories = mutableListOf<Int>()
    input.lines().forEach {
        if (it.isBlank()) {
            this.yield(currentCalories.toList())
            currentCalories.clear()
        } else currentCalories.add(it.toInt())
    }
}.toList()

fun maxCaloriesCarried(input: String) = elfCalories(input).maxOf { it.sum() }
fun top3MaxCaloriesCarried(input: String) : Int = elfCalories(input).map { it.sum() }.sortedDescending().take(3).sum()