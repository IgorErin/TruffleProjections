package interpreter

class Environment {
    val env = mutableMapOf<String, Any>()

    fun getValue(name: String): Any {
        return env[name] ?: TODO("not bound var")
    }

    fun setValue(name: String, value: Any) {
        env[name] = value
    }
}