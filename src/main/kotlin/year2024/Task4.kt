package year2024

import Task
import getColumns
import getDiagonals
import toPair
import java.io.File

class Task4(private val dataInputPath: String) : Task<List<String>> {
    override fun parseInput(isPart2: Boolean): List<String> {
        return emptyList()
    }

    override fun doPart1(input: List<String>): Int {
        val matrix = File(dataInputPath).readLines().map { it.toList() }

        val horizontal = matrix.map { it.joinToString(separator = "") }
        val horizontalReversed = horizontal.map { it.reversed() }

        val vertical = matrix.getColumns().map { it.joinToString(separator = "") }
        val verticalReversed = vertical.map { it.reversed() }

        val diagonalsDownRight = matrix.map {it.joinToString(separator = "") }.toTypedArray().getDiagonals()
        val diagonalsDownRightReversed = diagonalsDownRight.map { it.reversed() }

        val diagonalsDownLeft = matrix.map {it.reversed().joinToString(separator = "") }.toTypedArray().getDiagonals()
        val diagonalsDownLeftReversed = diagonalsDownLeft.map { it.reversed() }

        val strings = horizontal + horizontalReversed + vertical + verticalReversed + diagonalsDownRight + diagonalsDownRightReversed + diagonalsDownLeft + diagonalsDownLeftReversed

        return strings.sumOf { string -> string.windowed("XMAS".length) { it == "XMAS"}.count{it} }
    }


    override fun doPart2(input: List<String>): Int {
        val matrix = File(dataInputPath).readLines().map { it.toList() }

        val candidatePairs = matrix.mapIndexedNotNull { listIndex, list ->
            if (0 < listIndex && listIndex < matrix.size - 1) {
                return@mapIndexedNotNull list.mapIndexedNotNull{characterIndex, character ->
                    if (0 < characterIndex && characterIndex < list.size - 1 && character == 'A') {
                        val diagnoalDownRight = matrix[listIndex-1][characterIndex-1].toString() + matrix[listIndex][characterIndex] + matrix[listIndex+1][characterIndex+1]
                        val diagnoalDownLeft = matrix[listIndex-1][characterIndex+1].toString() + matrix[listIndex][characterIndex] + matrix[listIndex+1][characterIndex-1]
                        return@mapIndexedNotNull listOf(diagnoalDownLeft, diagnoalDownRight).toPair()
                    }
                    return@mapIndexedNotNull null
                }
            }
            return@mapIndexedNotNull null
        }.flatten()

        val validStrings = listOf("MAS", "SAM")
        val matchingPairs = candidatePairs.filter { pair -> pair.first in validStrings && pair.second in validStrings }

        return matchingPairs.size
    }
}