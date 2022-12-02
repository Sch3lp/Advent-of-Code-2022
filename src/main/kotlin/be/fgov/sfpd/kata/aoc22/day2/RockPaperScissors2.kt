package be.fgov.sfpd.kata.aoc22.day2

object RockPaperScissors2 {
    fun totalScore(input: String) = input.lines().toBattles().sumOf { it.fight() }

    private fun List<String>.toBattles() =
        map { line -> line.split(" ").let { (opponent, outcome) -> Battle(opponent, outcome) } }

    private data class Battle(private val opponent: Hand, private val outcome: Outcome) {
        constructor(opponent: String, outcome: String) : this(Hand.from(opponent), Outcome.from(outcome))

        fun fight(): Int = opponent + outcome.chooseHand(opponent)
    }

    private enum class Outcome {
        Lose, Draw, Win;

        fun chooseHand(opponent: Hand): Hand = opponent.counterWith(this)

        companion object {
            fun from(letter: String) = when (letter) {
                "X" -> Lose
                "Y" -> Draw
                "Z" -> Win
                else -> error("Cannot parse $letter to an Outcome")
            }
        }
    }

    private enum class HandType {
        Rock,Paper,Scissors
    }

    private sealed class Hand(
        private val winsFrom: HandType,
        private val losesFrom: HandType,
    ) {
        object Rock : Hand(winsFrom = HandType.Scissors, losesFrom = HandType.Paper)
        object Paper : Hand(winsFrom = HandType.Rock, losesFrom = HandType.Scissors)
        object Scissors : Hand(winsFrom = HandType.Paper, losesFrom = HandType.Rock)

        operator fun plus(strategy: Hand): Int {
            return mapOf(
                Rock to Rock to 4,
                Rock to Paper to 8,
                Rock to Scissors to 3,
                Paper to Rock to 1,
                Paper to Paper to 5,
                Paper to Scissors to 9,
                Scissors to Rock to 7,
                Scissors to Paper to 2,
                Scissors to Scissors to 6,
            )[this to strategy] ?: error("could not find result for $this + $strategy")
        }

        fun counterWith(outcome: Outcome): Hand = when (outcome) {
            Outcome.Lose -> winsFrom.hand
            Outcome.Draw -> this
            Outcome.Win -> losesFrom.hand
        }

        val HandType.hand get() = when(this) {
            HandType.Rock -> Rock
            HandType.Paper -> Paper
            HandType.Scissors -> Scissors
        }

        companion object {
            fun from(letter: String): Hand = when (letter) {
                "A" -> Rock
                "B" -> Paper
                "C" -> Scissors
                else -> error("Cannot parse $letter to a Hand")
            }
        }
    }
}