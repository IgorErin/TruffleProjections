package environments

import com.oracle.truffle.api.frame.VirtualFrame

class EnvironmentForVar  {
    private val env = mutableMapOf<String, Int>()

    fun assign(name: String, value: Int) {
        env[name] = value
    }

    fun get(name: String): Int? {
        return env[name]
    }
}