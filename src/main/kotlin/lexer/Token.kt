package lexer

data class Token(var value: SemanticValue, var name: String, var line: Int)
