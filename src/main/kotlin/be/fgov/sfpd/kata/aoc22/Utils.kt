package be.fgov.sfpd.kata.aoc22

fun readFile(fileName: String): String {
    return {}::class.java.classLoader.getResourceAsStream(fileName)?.reader()?.readText() ?: error("Could not load $fileName")
}