package be.fgov.sfpd.kata.aoc22.day7

import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.Map.entry

class TreeTest {

    @Nested
    inner class TreeConstructionTests {
        @Test
        fun `creating a file tree`() {
            val treeWithALeaf = Root("root", listOf(Leaf("leaf")))
            val branch1 = Branch("branch1", listOf(Leaf("leaf1")))
            val branch2 = Branch("branch2", listOf(Leaf("leaf2")))
            val treeWithABranchAndALeaf = Root("root", listOf(branch1, branch2))

            assertThat(treeWithALeaf.data).isEqualTo("root")
            assertThat(treeWithALeaf.children).containsExactly(Leaf("leaf"))
            assertThat(treeWithABranchAndALeaf.children.map { it.data }).containsExactly("branch1", "branch2")
            assertThat(treeWithABranchAndALeaf.children.flatMap { it.children }.map { it.data }).containsExactly(
                "leaf1",
                "leaf2"
            )
        }

        @Test
        fun `building a dir tree`() {
            val tree: Root<FSMeta> = rootDir(".")
                .add(file("rootFile", 12))
                .add(
                    dir("dir")
                        .add(file("file", 100))
                )
                .add(
                    dir("otherDir")
                        .add(file("otherFile", 20))
                        .add(dir("subDir").add(file("subDirFile", 42)))
                        .add(dir("emptyDir"))
                ).build()

            assertThat(tree.data.name).isEqualTo(".")
            assertThat(tree.children.map { it.data.name }).containsExactly("dir", "otherDir")
            assertThat(tree.children.first { it.data.name == "otherDir" }.children.map { it.data.name })
                .containsExactly("otherFile", "subDir", "emptyDir")

            val rebuiltTree: Root<FSMeta> = rootDir(".")
                .add(file("rootFile", 12))
                .add(
                    dir("dir")
                        .add(file("file", 100))
                )
                .add(
                    dir("otherDir")
                        .add(file("otherFile", 20))
                        .add(dir("subDir").add(file("subDirFile", 42)))
                        .add(dir("emptyDir"))
                ).build()
            val slightlyDifferentRebuiltTree: Root<FSMeta> = rootDir(".")
                .add(file("rootFile", 12))
                .add(
                    dir("dir")
                        .add(file("file", 100))
                )
                .add(
                    dir("otherDir")
                        .add(file("snarfFile", 20))
                        .add(dir("fart").add(file("subDirFile", 24)))
                        .add(dir("emptyDir"))
                ).build()
            assertThat(tree)
                .isEqualTo(rebuiltTree)
                .isNotEqualTo(slightlyDifferentRebuiltTree)
        }
    }

    @Nested
    inner class ParseTests {
        @Test
        fun `parsing to a tree`() {
            val input = readFile("day7/exampleInput.txt")
            val tree: Root<FSMeta> =
                rootDir(".")
                    .add(
                        dir("a")
                            .add(
                                dir("e")
                                    .add(
                                        file("i", 584)
                                    )
                            )
                            .add(file("f", 29116))
                            .add(file("g", 2557))
                            .add(file("h.lst", 62596))
                    ).add(file("b.txt", 14848514))
                    .add(file("c.dat", 8504156))
                    .add(
                        dir("d")
                            .add(file("j", 4060174))
                            .add(file("d.log", 8033020))
                            .add(file("d.ext", 5626152))
                            .add(file("k", 7214296))
                    )
                    .build()

            assertThat(parseTree(input)).isEqualTo(tree)
        }
    }

    @Nested
    inner class TreeOperationTests {
        @Test
        fun `getting the volume of each directory`() {
            val root: Root<FSMeta> = rootDir(".")
                .add(file("rootFile", 12))
                .add(dir("dir")
                    .add(file("file", 100))
                )
                .add(dir("otherDir")
                    .add(file("otherFile", 20))
                    .add(dir("subDir")
                        .add(file("subDirFile", 42)))
                    .add(dir("emptyDir"))
                ).build()

            assertThat(root["dir"].totalVolume).isEqualTo(100)
            assertThat(root["otherDir"].totalVolume).isEqualTo(20+42)
            assertThat(root["otherDir"]["subDir"].totalVolume).isEqualTo(42)
            assertThat(root["otherDir"]["emptyDir"].totalVolume).isEqualTo(0)
            assertThat(root.totalVolume).isEqualTo(root["dir"].totalVolume + root["otherDir"].totalVolume + 12)
        }

        @Test
        fun `getting volumes per dir`() {
            val tree = parseTree(readFile("day7/exampleInput.txt"))
            assertThat(tree.totalVolumePerDir)
                .contains(
                    entryOf("e" to 584),
                    entryOf("a" to 94853),
                    entryOf("d" to 24933642),
                    entryOf("." to 48381165),
                    )
        }
    }

    private fun <K,V> entryOf(pair : Pair<K,V>) = pair.let { (first,second) -> entry(first, second) }

}

