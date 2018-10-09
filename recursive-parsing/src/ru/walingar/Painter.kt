package ru.walingar

import java.io.File

object Painter {
    private fun draw(name: String) {
        ProcessBuilder("dot", "-Tpng", "$name.dot")
                .redirectOutput(File("$name.png"))
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor()
    }

    fun draw(tree: Tree, name: String = "Tree") {
        TreePrinter.printToFile(tree, name)
        draw(name)
    }
}