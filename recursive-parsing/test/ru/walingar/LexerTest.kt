package ru.walingar

import org.junit.Test

import org.junit.Assert.*

class LexerTest {

    @Test
    fun `nextToken without failures`() {
        val testString = buildString {
            val alphabet = mutableListOf('(', ')', '*', '|').apply { addAll('a'..'z') }
            for (i in 0..1000) {
                append(alphabet.random())
            }
        }
        println(testString)

        var size = 0
        Lexer(testString).let {
            while (it.nextToken() != Token.END) {
                assertTrue("A lot of tokens", size <= testString.length)
                size++
            }
        }
    }
}