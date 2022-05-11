package exceptions

class EvalException(message: String, name: String) : Exception("$message invalid value '$name'")