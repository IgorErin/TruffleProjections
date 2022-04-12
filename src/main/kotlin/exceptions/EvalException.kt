package exceptions

class EvalException(message: String, name: String, line: Int) : Exception("$message invalid value '$name' in $line ")