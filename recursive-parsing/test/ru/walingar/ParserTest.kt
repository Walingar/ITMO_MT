package ru.walingar

import org.junit.Test

import org.junit.Assert.*
import java.text.ParseException

class ParserTest {

    private val parser by lazy {
        Parser()
    }

    private fun testSet(data: Collection<String>) =
            data.forEach {
                println(parser.parse(it))
            }

    private fun testSetFail(data: Collection<String>) =
            data.forEach {
                try {
                    println(parser.parse(it))
                    fail(it)
                } catch (e: ParseException) {
                    println()
                }
            }

    @Test
    fun `sample test`() {
        val testString = "((abc*b|a)*ab(aa|b*)b)*"
        val tree = parser.parse(testString)
        print(tree)
    }

    @Test
    fun `test concatenation`() =
            testSet(listOf(
                    "",
                    "aa",
                    "aaa",
                    "aaaa"
            ))

    @Test
    fun `test group`() =
            testSet(listOf(
                    "()",
                    "()()",
                    "(())",
                    "(a)",
                    "(aaa)",
                    "(sad)dfg(as)",
                    "aa(aa)aa",
                    "aa(aa)aa(aa)",
                    "(aa)(aa)",
                    "(aa(aa)aa)"
            ))

    @Test
    fun `test selection`() =
            testSet(listOf(
                    "|",
                    "a|",
                    "|a",
                    "a|a",
                    "(a|aa)",
                    "(sa|d)df|g(a|s)",
                    "aa(a|a)aa",
                    "aaaa|aaaa(aaa|aaaaaa)aa(aa|a)aaaa",
                    "(aa)||(aa)",
                    "(aa|(aa)|aa)"
            ))


    @Test
    fun `test star`() =
            testSet(listOf(
                    "()*",
                    "()*()*",
                    "(()*)*",
                    "(a*)*",
                    "(aa*a)",
                    "(sa*d)*df*g(a*s)*",
                    "a*a(aa*)*aa*",
                    "a*a(|aa*)*a|a*(aa*)",
                    "(a*|a*)*|(a*|a)",
                    "(a*a*(a*a*)a*a*)*"
            ))


    @Test
    fun `test failure`() =
            testSetFail(listOf(
                    "*",
                    "*|",
                    "*|*",
                    "a**",
                    "(a)**",
                    "(sa*d()*df*g(a*s)*",
                    "a*a()))))aa*)aa*",
                    "((((((",
                    "())))))",
                    "()()0000))))0))0))0)))",
                    "lol fail",
                    "here|too|*fail|"
            ))

    @Test
    fun `random tests`() {
        for (i in 1..100) {
            val regex = RegexBuilder.build()
            println(regex)
            parser.parse(regex)
        }
    }

    @Test
    fun `random stress test`() {
        for (i in 1..1000_000) {
            parser.parse(RegexBuilder.build())
        }
    }

    @Test
    fun `simple test`() {
        parser.parse("()")
    }

    @Test
    fun `simple test with draw`() {
        val tree = parser.parse("a*aa|aaaa(aaa|aaa)*")
        Painter.draw(tree, "Tree")
    }
}