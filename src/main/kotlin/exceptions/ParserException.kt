package exceptions

class ParserException(message: String, line: Int) : Exception("parsing error: invalid token $message in line $line")
