package exceptions

class ParserException(message: String) : Exception("parsing error: invalid token $message")
