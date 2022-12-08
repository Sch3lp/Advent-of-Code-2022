package be.fgov.sfpd.kata.aoc22.day8

import be.fgov.sfpd.kata.aoc22.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TreeHutPlannerTest {
    private val input = """
30373
25512
65332
33549
35390
        """.trimIndent()

    @Test
    fun `parsing to trees`() {
        assertThat(parse(input)).containsExactly(
            Tree(Point(0, 0), 3), Tree(Point(1, 0), 0), Tree(Point(2, 0), 3), Tree(Point(3, 0), 7), Tree(Point(4, 0), 3),
            Tree(Point(0, 1), 2), Tree(Point(1, 1), 5), Tree(Point(2, 1), 5), Tree(Point(3, 1), 1), Tree(Point(4, 1), 2),
            Tree(Point(0, 2), 6), Tree(Point(1, 2), 5), Tree(Point(2, 2), 3), Tree(Point(3, 2), 3), Tree(Point(4, 2), 2),
            Tree(Point(0, 3), 3), Tree(Point(1, 3), 3), Tree(Point(2, 3), 5), Tree(Point(3, 3), 4), Tree(Point(4, 3), 9),
            Tree(Point(0, 4), 3), Tree(Point(1, 4), 5), Tree(Point(2, 4), 3), Tree(Point(3, 4), 9), Tree(Point(4, 4), 0),
        )
    }

    @Test
    fun `forest can identify visible trees`() {
        assertThat(Forest(parse(input)).isVisible(Point(0, 0))).isTrue
        assertThat(Forest(parse(input)).isVisible(Point(3, 1))).isFalse
    }

    @Test
    fun `forest can count amount of visible trees`() {
        assertThat(Forest(parse(input)).visibleTrees)
            .doesNotContainKeys(
                Point(3,1),
                Point(2,2),
                Point(1,3),Point(3,3),
            )
            .hasSize(21)
    }

    @Test
    fun `forest can discover a tree's scenic score`() {
        assertThat(Forest(parse(input)).scenicScoreFor(Point(2, 1))).isEqualTo(1 * 1 * 2 * 2)
        println("###")
        assertThat(Forest(parse(input)).scenicScoreFor(Point(2, 3))).isEqualTo(2 * 2 * 1 * 2)
    }

    @Test
    fun `forest can retrieve the tree with the highest scenic score`() {
        assertThat(Forest(parse(input)).highestScenicScore).isEqualTo(8)
    }
}

class Forest(trees: Set<Tree>) {
    private val treeGrid: Map<Point, Tree> = trees.associateBy { it.at }
    private val westEdge = 0
    private val northEdge = 0
    private val eastEdge = trees.maxOf { it.at.x }
    private val southEdge = trees.maxOf { it.at.y }

    val highestScenicScore: Int get() =
        treeGrid.maxOf { (at,_) -> scenicScoreFor(at) }

    fun scenicScoreFor(at: Point): Int {
        val tree = treeGrid.at(at)
        val treeHeight = tree.height
        val treesNorthWithinView = at.treesNorthOf().takeWhile { it.isLowerThan(treeHeight) }.count() + countNorthernSameOrTallerHeightOnce(tree)
        val treesSouthWithinView = at.treesSouthOf().takeWhile { it.isLowerThan(treeHeight) }.count() + countSouthernSameOrTallerHeightOnce(tree)
        val treesEastWithinView = at.treesEastOf().takeWhile { it.isLowerThan(treeHeight) }.count() + countEasternSameOrTallerHeightOnce(tree)
        val treesWestWithinView = at.treesWestOf().takeWhile { it.isLowerThan(treeHeight) }.count() + countWesternSameOrTallerHeightOnce(tree)

        return treesNorthWithinView *
                treesSouthWithinView *
                treesEastWithinView *
                treesWestWithinView
    }

    val visibleTrees get() = treeGrid.filter { (at, _) -> isVisible(at) }
    val visibleTreesTotal: Int get() = visibleTrees.count()
    fun isVisible(at: Point): Boolean {
        val treeHeight = treeGrid.at(at).height
        return at.isAtEdge()
                || at.treesNorthOf().areLowerThan(treeHeight)
                || at.treesSouthOf().areLowerThan(treeHeight)
                || at.treesEastOf().areLowerThan(treeHeight)
                || at.treesWestOf().areLowerThan(treeHeight)
    }

    private fun countNorthernSameOrTallerHeightOnce(tree: Tree): Int = tree.at.treesNorthOf().firstOrNull { it.isTallerThanOrEqualTo(tree.height) }?.let { 1 } ?: 0
    private fun countSouthernSameOrTallerHeightOnce(tree: Tree): Int = tree.at.treesSouthOf().firstOrNull { it.isTallerThanOrEqualTo(tree.height) }?.let { 1 } ?: 0
    private fun countEasternSameOrTallerHeightOnce(tree: Tree): Int = tree.at.treesEastOf().firstOrNull { it.isTallerThanOrEqualTo(tree.height) }?.let { 1 } ?: 0
    private fun countWesternSameOrTallerHeightOnce(tree: Tree): Int = tree.at.treesWestOf().firstOrNull { it.isTallerThanOrEqualTo(tree.height) }?.let { 1 } ?: 0

    private fun Sequence<Tree>.areLowerThan(height: Height): Boolean = all { it.isLowerThan(height) } //TODO: use any{} so we don't consume the full sequence 
    private fun Tree.isLowerThan(height: Height) = this.height < height
    private fun Tree.isTallerThanOrEqualTo(height: Height) = this.height >= height

    private fun Point.treesNorthOf() = ((this..Point(this.x, northEdge)) - this).mapNotNull { treeGrid[it] }.asSequence()
    private fun Point.treesSouthOf() = ((this..Point(this.x, southEdge)) - this).mapNotNull { treeGrid[it] }.asSequence()
    private fun Point.treesEastOf() = ((this..Point(eastEdge, this.y)) - this).mapNotNull { treeGrid[it] }.asSequence()
    private fun Point.treesWestOf() = ((this..Point(westEdge, this.y)) - this).mapNotNull { treeGrid[it] }.asSequence()

    private fun Map<Point,Tree>.at(point: Point) = getValue(point)

    private fun Point.isAtEdge() = this.x == westEdge || this.x == eastEdge || this.y == northEdge || this.y == southEdge
}

private fun Map<Point, Tree>.visualize(): String =
    toList().joinToString("\n") { (k, v) -> "${k.x},${k.y}=${v.height}" }

fun parse(input: String): Set<Tree> =
    input.lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, height ->
            Tree(Point(x, y), "$height".toInt())
        }
    }.toSet()

data class Tree(val at: Point, val height: Height)
typealias Height = Int