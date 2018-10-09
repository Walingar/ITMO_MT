package ru.walingar

import java.io.File

object TreePrinter {

    private fun createIdLine(tree: Tree, id: Int): String = "$id " +
            "[label=\"${tree.value}\"" +
            (if (tree.children.isEmpty()) ", color=red" else "") +
            "];"

    private fun getId(tree: Tree) = System.identityHashCode(tree)

    private fun getChildrenLine(tree: Tree) = buildString {
        tree.children.forEach {
            append(getId(it))
            if (it != tree.children.last()) {
                append(", ")
            }
        }
    }

    private fun dfs(tree: Tree): String = buildString {
        val id = getId(tree)
        appendln(createIdLine(tree, id))
        appendln("$id -> {${getChildrenLine(tree)}};")
        tree.children.forEach {
            append(dfs(it))
        }
    }

    fun print(tree: Tree): String {
        return dfs(tree)
    }

    fun printToFile(tree: Tree, name: String = "Tree") {
        val file = File("$name.dot")
        file.createNewFile()
        file.writeText("digraph $name {${System.lineSeparator()}" +
                print(tree) +
                "${System.lineSeparator()}}"
        )
    }
}