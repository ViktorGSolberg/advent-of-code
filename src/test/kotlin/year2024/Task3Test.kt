package year2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import solutionsByYear

class Task3Test {

    @Test
    fun `task 3 - part 1`() {
        val task = Task3("src/main/resources/input/input_task3.txt")

        val parsedInput = task.parseInput()
        val result = task.doPart1(parsedInput)

        val solution = solutionsByYear[2024]?.get(3) ?: throw Error("Missing 2024 solution for task 3")

        assertEquals(solution.first, result)
    }

    @Test
    fun `task 3 - part 2`() {
        val task = Task3("src/main/resources/input/input_task3.txt")

        val parsedInput = task.parseInput()
        val result = task.doPart2(parsedInput)

        val solution = solutionsByYear[2024]?.get(3) ?: throw Error("Missing 2024 solution for task 3")

        assertEquals(solution.second, result)
    }
}