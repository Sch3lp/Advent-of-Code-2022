package be.fgov.sfpd.kata.aoc22.day8

import be.fgov.sfpd.kata.aoc22.Point

class Forest(trees: Set<Tree>) {
    private val treeGrid: Map<Point, Tree> = trees.associateBy { it.at }
    private val westEdge = 0
    private val northEdge = 0
    private val eastEdge = trees.maxOf { it.at.x }
    private val southEdge = trees.maxOf { it.at.y }

    val highestScenicScore: Int
        get() =
            treeGrid.maxOf { (at, _) -> scenicScoreFor(at) }

    fun scenicScoreFor(at: Point): Int {
        val tree = treeGrid.at(at)
        val treeHeight = tree.height
        val treesNorthWithinView =
            at.treesNorthOf().takeWhile { it.isLowerThan(treeHeight) }.count() + countNorthernSameOrTallerHeightOnce(
                tree
            )
        val treesSouthWithinView =
            at.treesSouthOf().takeWhile { it.isLowerThan(treeHeight) }.count() + countSouthernSameOrTallerHeightOnce(
                tree
            )
        val treesEastWithinView =
            at.treesEastOf().takeWhile { it.isLowerThan(treeHeight) }.count() + countEasternSameOrTallerHeightOnce(tree)
        val treesWestWithinView =
            at.treesWestOf().takeWhile { it.isLowerThan(treeHeight) }.count() + countWesternSameOrTallerHeightOnce(tree)

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

    private fun countNorthernSameOrTallerHeightOnce(tree: Tree): Int =
        tree.at.treesNorthOf().firstOrNull { it.isTallerThanOrEqualTo(tree.height) }?.let { 1 } ?: 0

    private fun countSouthernSameOrTallerHeightOnce(tree: Tree): Int =
        tree.at.treesSouthOf().firstOrNull { it.isTallerThanOrEqualTo(tree.height) }?.let { 1 } ?: 0

    private fun countEasternSameOrTallerHeightOnce(tree: Tree): Int =
        tree.at.treesEastOf().firstOrNull { it.isTallerThanOrEqualTo(tree.height) }?.let { 1 } ?: 0

    private fun countWesternSameOrTallerHeightOnce(tree: Tree): Int =
        tree.at.treesWestOf().firstOrNull { it.isTallerThanOrEqualTo(tree.height) }?.let { 1 } ?: 0

    private fun Sequence<Tree>.areLowerThan(height: Height): Boolean =
        all { it.isLowerThan(height) } //TODO: use any{} so we don't consume the full sequence

    private fun Tree.isLowerThan(height: Height) = this.height < height
    private fun Tree.isTallerThanOrEqualTo(height: Height) = this.height >= height

    private fun Point.treesNorthOf() =
        ((this..Point(this.x, northEdge)) - this).mapNotNull { treeGrid[it] }.asSequence()

    private fun Point.treesSouthOf() =
        ((this..Point(this.x, southEdge)) - this).mapNotNull { treeGrid[it] }.asSequence()

    private fun Point.treesEastOf() = ((this..Point(eastEdge, this.y)) - this).mapNotNull { treeGrid[it] }.asSequence()
    private fun Point.treesWestOf() = ((this..Point(westEdge, this.y)) - this).mapNotNull { treeGrid[it] }.asSequence()

    private fun Map<Point, Tree>.at(point: Point) = getValue(point)

    private fun Point.isAtEdge() =
        this.x == westEdge || this.x == eastEdge || this.y == northEdge || this.y == southEdge
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