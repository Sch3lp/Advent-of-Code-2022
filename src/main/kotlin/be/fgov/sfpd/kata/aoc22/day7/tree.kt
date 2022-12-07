package be.fgov.sfpd.kata.aoc22.day7

sealed class Node<T>(val data: T, val children: List<Node<T>>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node<*>

        if (data != other.data) return false
        if (children != other.children) return false

        return true
    }
    override fun hashCode(): Int {
        var result = data?.hashCode() ?: 0
        result = 31 * result + children.hashCode()
        return result
    }
    override fun toString(): String = "node: $data, children: $children"
}
class Root<T>(data: T, children: List<Node<T>>): Node<T>(data, children)
class Branch<T>(data: T, children: List<Node<T>>) : Node<T>(data, children)
class Leaf<T>(data: T) : Node<T>(data, emptyList())
