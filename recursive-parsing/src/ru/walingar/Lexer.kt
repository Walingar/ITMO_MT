package ru.walingar

import java.io.IOException
import java.io.Reader
import java.text.ParseException

class Lexer(private val stream: Reader) {
    constructor(line: String) : this(line.reader())

    var curPosition = -1

    private fun nextChar(): Int {
        try {
            return stream.read()
        } catch (e: IOException) {
            throw ParseException(e.message, curPosition + 1)
        } finally {
            curPosition++
        }
    }

    fun nextToken(): Token {
        val currentChar = nextChar()
        // print(currentChar.toChar())
        if (currentChar == -1) {
            return Token.END
        }

        return when (currentChar.toChar()) {
            '(' -> Token.OPEN_BRACKET
            ')' -> Token.CLOSE_BRACKET
            '*' -> Token.STAR
            '|' -> Token.OR
            in 'a'..'z' -> Token.LETTER
            else -> throw ParseException("Illegal character: ${currentChar.toChar()}", curPosition)
        }
    }
}