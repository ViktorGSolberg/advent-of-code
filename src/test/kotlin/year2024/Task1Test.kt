package year2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import solutionsByYear

class Task1Test {

    @Test
    fun `task 1 - part 1`() {
        val task = Task1("src/main/resources/input/input_task1.txt")

        val parsedInput = task.parseInput()
        val result = task.doPart1(parsedInput)

        val solution = solutionsByYear[2024]?.get(1) ?: throw Error("Missing 2024 solution for task 1")

        assertEquals(solution.first, result)
    }

    @Test
    fun `task 1 - part 2`() {
        val task = Task1("src/main/resources/input/input_task1.txt")

        val parsedInput = task.parseInput()
        val result = task.doPart2(parsedInput)

        val solution = solutionsByYear[2024]?.get(1) ?: throw Error("Missing 2024 solution for task 1")

        assertEquals(solution.second, result)
    }
}