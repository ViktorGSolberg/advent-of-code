interface Task<T> {
    fun parseInput(): T

    fun doPart1(input: T): Int

    fun doPart2(input: T): Int

    fun doTask(): Unit
}