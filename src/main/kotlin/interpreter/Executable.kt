package interpreter

interface Executable {
    fun execute(env: Environment): Any
}
