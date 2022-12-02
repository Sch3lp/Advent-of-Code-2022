package be.fgov.sfpd.kata.aoc22.day2

fun totalScore(input: String) = input.lines().toBattles().sumOf { it.fight() }

fun List<String>.toBattles() =
    map { line -> line.split(" ").let { (opponent, strategy) -> Battle(opponent, strategy) } }

data class Battle(private val opponent: Hand, private val strategy: Hand) {
    constructor(opponent: String, strategy: String) : this(Hand.from(opponent), Hand.from(strategy))
    fun fight() = opponent + strategy
}

enum class Hand(
    private val letters: Pair<String, String>,
) {
    Rock("A" to "X"),
    Paper("B" to "Y"),
    Scissors("C" to "Z");

    private operator fun component1() = letters.first
    private operator fun component2() = letters.second

    operator fun plus(strategy: Hand): Int =
        mapOf(
            Rock to Rock to 4,
            Rock to Paper to 8,
            Rock to Scissors to 3,
            Paper to Rock to 1,
            Paper to Paper to 5,
            Paper to Scissors to 9,
            Scissors to Rock to 7,
            Scissors to Paper to 2,
            Scissors to Scissors to 6,
        )[this to strategy]!!

    companion object {
        fun from(s: String): Hand =
            values().first { (opponent, strategy) -> opponent == s || strategy == s }
    }
}
