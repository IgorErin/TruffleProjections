package parser.statements

import parser.expressions.Expression

interface Visitor<T> {
    fun <T> visitExpStatement(exp: Expression): T

    fun <T> visitReturnStatement(): T
}