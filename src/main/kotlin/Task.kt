interface Task<T> {
    fun parseInput(isPart2: Boolean = false): T

    fun doPart1(input: T): Int

    fun doPart2(input: T): Int

    fun doTask() {
        val inputPart1 = parseInput()
        val solutionPart1 = doPart1(inputPart1)

        val inputPart2 = parseInput(true)
        val solutionPart2 = doPart2(inputPart2)

        println("Solution for part 1")
        println(solutionPart1)
        println("=====")
        println("Solution for part 2")
        println(solutionPart2)
    }
}