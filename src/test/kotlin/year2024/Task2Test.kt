package year2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import solutionsByYear

class Task2Test {

    @Test
    fun `task 2 - part 1`() {
        val task = Task2("src/main/resources/input/input_task2.txt")

        val parsedInput = task.parseInput()
        val result = task.doPart1(parsedInput)

        val solution = solutionsByYear[2024]?.get(2) ?: throw Error("Missing 2024 solution for task 2")

        assertEquals(solution.first, result)
    }

    @Test
    fun `task 2 - part 2`() {
        val task = Task2("src/main/resources/input/input_task2.txt")

        val parsedInput = task.parseInput()
        val result = task.doPart2(parsedInput)

        val solution = solutionsByYear[2024]?.get(2) ?: throw Error("Missing 2024 solution for task 2")

        assertEquals(solution.second, result)
    }
}