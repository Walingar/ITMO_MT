package ru.walingar

import java.util.*

object RegexBuilder {

    private fun getProbability() = Random().nextInt(100 + 1)

    private fun generateREPride(): String =
            if (getProbability() > 90) {
                "|${build()}"
            } else {
                ""
            }

    private fun generateConcat(): String = "${generatePart()}${build()}"

    private fun generatePart(): String = "${generateGroup()}${generateGroupPride()}"

    private fun generateGroup(): String =
            if (getProbability() > 90) {
                "(${build()})"
            } else {
                "a"
            }

    private fun generateGroupPride() =
            if (getProbability() > 50) {
                "*"
            } else {
                ""
            }

    fun build() = if (getProbability() > 70) {
        generateREPride()
    } else {
        "${generateConcat()}${generateREPride()}"
    }


}