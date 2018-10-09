package ru.walingar

import java.io.Reader
import java.text.ParseException

class Parser {
    lateinit var lexer: Lexer
    lateinit var curToken: Token

    private fun unexpectedLiteral(rule: String): Nothing =
            throw ParseException("Unexpected literal $curToken in rule $rule on pos: ${lexer.curPosition}", lexer.curPosition)

    private fun parseRE() =
            when (curToken) {
                Token.LETTER, Token.OPEN_BRACKET -> {
                    Tree("RE",
                            arrayListOf(
                                    parseConcat(),
                                    parseREPride()
                            )
                    )
                }
                Token.OR, Token.END, Token.CLOSE_BRACKET -> {
                    Tree("RE",
                            arrayListOf(
                                    parseREPride()
                            )
                    )
                }
                else -> unexpectedLiteral("RE")
            }

    private fun parseREPride(): Tree =
            when (curToken) {
                Token.OR -> {
                    curToken = lexer.nextToken()
                    Tree("RE'",
                            arrayListOf(
                                    Tree("|"),
                                    parseRE()
                            )
                    )
                }
                Token.END, Token.CLOSE_BRACKET -> Tree("RE'",
                        arrayListOf(
                                Tree("Eps")
                        )
                )
                else -> unexpectedLiteral("RE'")
            }

    private fun parseConcat(): Tree =
            when (curToken) {
                Token.OPEN_BRACKET, Token.LETTER ->
                    Tree("Concat",
                            arrayListOf(
                                    parsePart(),
                                    parseConcat()
                            )
                    )
                Token.CLOSE_BRACKET, Token.OR, Token.END ->
                    Tree("Concat",
                            arrayListOf(
                                    Tree("Eps")
                            )
                    )
                else -> unexpectedLiteral("Concat")
            }

    private fun parsePart(): Tree =
            when (curToken) {
                Token.OPEN_BRACKET, Token.LETTER -> Tree("Part",
                        arrayListOf(
                                parseGroup(),
                                parseGroupPride()
                        )
                )
                else -> unexpectedLiteral("Part")
            }

    private fun parseGroup(): Tree =
            when (curToken) {
                Token.LETTER -> {
                    curToken = lexer.nextToken()
                    Tree("Group", arrayListOf(Tree("Letter")))
                }
                Token.OPEN_BRACKET -> {
                    curToken = lexer.nextToken()
                    val treeRE = parseRE()
                    when (curToken) {
                        Token.CLOSE_BRACKET -> {
                            curToken = lexer.nextToken()
                            Tree("Group",
                                    arrayListOf(
                                            Tree("("),
                                            treeRE,
                                            Tree(")")
                                    )
                            )
                        }
                        else -> unexpectedLiteral("Group")
                    }
                }
                else -> unexpectedLiteral("Group")
            }

    private fun parseGroupPride() =
            when (curToken) {
                Token.STAR -> {
                    curToken = lexer.nextToken()
                    Tree("Group''",
                            arrayListOf(
                                    Tree("*")
                            )
                    )
                }
                else -> Tree("Group'",
                        arrayListOf(
                                Tree("Eps")
                        )
                )
            }

    private fun parse(): Tree {
        curToken = lexer.nextToken()
        val tree = parseRE()
        return if (curToken == Token.END) {
            tree
        } else {
            unexpectedLiteral("End of expression")
        }
    }

    fun parse(input: String): Tree {
        lexer = Lexer(input)
        return parse()
    }

    fun parse(input: Reader): Tree {
        lexer = Lexer(input)
        return parse()
    }
}