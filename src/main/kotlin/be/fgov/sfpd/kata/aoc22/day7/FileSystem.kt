package be.fgov.sfpd.kata.aoc22.day7

operator fun Node<FSMeta>.get(dir: String): Branch<FSMeta> {
    return this.children.filterIsInstance<Branch<FSMeta>>().first { it.data.name == dir }
}

val Node<FSMeta>.totalVolume: Int
    get() {
        val directFileSize = this.children.filterIsInstance<Leaf<FSMeta>>().sumOf { it.data.fileSize }
        val subDirFileSize = this.children.filterIsInstance<Branch<FSMeta>>().sumOf { it.totalVolume }
        return directFileSize + subDirFileSize
    }

fun Node<FSMeta>.dirsUpTo(maxVolume: Int): Map<String, Int> =
    totalVolumePerDir.filterValues { volume -> volume <= maxVolume }

val Node<FSMeta>.totalVolumePerDir : Map<String, Int>
    get() {
        val subDirTotalVolumes =
            children.filterIsInstance<Branch<FSMeta>>()
                .map { it.totalVolumePerDir }
                .flatMap { it.entries.toPairs() }
        val thisDirTotalVolume = this.data.name to this.totalVolume
        return (subDirTotalVolumes + thisDirTotalVolume).toMap()
    }

private fun <K,V> Set<Map.Entry<K,V>>.toPairs(): List<Pair<K, V>> = map { it.toPair() }

fun parseTree(input: String): Root<FSMeta> {
    val cdLines = input.split(" cd ").map { it.lines() }
    val dirs = cdLines
        .mapNotNull { line ->
            when (val possibleDir = line[0]) {
                "$" -> null
                "/" -> possibleDir to rootDir(".")
                else -> possibleDir to dir(possibleDir)
            }
        }.toMap()
    cdLines.forEach { cdline ->
        val possibleDir = dirs[cdline[0]]
        if (possibleDir != null) {
            val files: List<FSMeta> = cdline
                .filter { it.split(" ")[0].isInt() }
                .map { it.split(" ").let { (size, name) -> file(name, size.toInt()) } }
            files.forEach { file -> possibleDir.add(file) }
            cdline
                .filter { it.startsWith("dir ") }
                .map { it.substringAfter("dir ") }
                .forEach { dirKey ->
                    dirs[dirKey]?.let { possibleDir.add(it) }
                }
        }
    }
    return dirs["/"]?.build() ?: error("no root dir found")
}

private fun String.isInt(): Boolean = this.toIntOrNull() != null


data class FSMeta(val name: String, val fileSize: Int)

fun rootDir(name: String) = FSBuilder(name)
fun dir(name: String) = FSBuilder(name)
fun file(name: String, size: Int) = FSMeta(name, size)

class FSBuilder(private val name: String) {
    private val files = mutableListOf<Leaf<FSMeta>>()
    private val subDirs = mutableListOf<FSBuilder>()

    private fun buildBranch(): Branch<FSMeta> = Branch(
        data = FSMeta(name, 0),
        children = files + subDirs.map { it.also { println("building $name") }.buildBranch() }
    )

    fun build() = Root(
        data = FSMeta(name, 0),
        children = files + subDirs.map { it.buildBranch() }
    )


    fun add(file: FSMeta): FSBuilder {
        files.add(Leaf(file))
        return this
    }

    fun add(builder: FSBuilder): FSBuilder {
        subDirs.add(builder)
        return this
    }
}