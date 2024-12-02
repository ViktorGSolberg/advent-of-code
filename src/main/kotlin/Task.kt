interface Task<T> {
    fun parseInput(): T

    fun doPart1(input: T): Int

    fun doPart2(input: T): Int

    fun doTask() {
        val parsedInput = parseInput()

        val solutionPart1 = doPart1(parsedInput)
        val solutionPart2 = doPart2(parsedInput)

        println("Solution for part 1")
        println(solutionPart1)
        println("=====")
        println("Solution for part 2")
        println(solutionPart2)
    }
}