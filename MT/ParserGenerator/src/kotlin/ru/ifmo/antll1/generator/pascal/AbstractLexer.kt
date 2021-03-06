package ru.ifmo.antll1.generator.pascal

import java.util.regex.Pattern

abstract class AbstractLexer(protected var input: String) : Lexer {
    override fun context() = tokens[tokenPosition].second
    override fun token(): Token = currentToken

    protected val tokens = mutableListOf<Pair<Token, String>>()
    protected var currentPosition = 0
    protected var tokenPosition = 0
    protected var context = ""
    protected lateinit var ignore: Pattern
    protected val matcher = Pattern.compile("").matcher("");

    protected val currentChar: Char
        get() = input[currentPosition]

    protected val currentToken: Token
        get() = tokens[tokenPosition].first

    init {
        input = input.trim()
    }

    override fun next(): Token {
        tokenPosition++
        return currentToken
    }

    override fun tokenDescription() = "${currentToken.title} in =( position"
}
