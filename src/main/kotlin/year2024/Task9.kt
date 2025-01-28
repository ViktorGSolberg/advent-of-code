package year2024

import Task
import swap
import toPair
import java.io.File

class Task9(private val dataInputPath: String) : Task<List<Int>> {
    override fun parseInput(isPart2: Boolean): List<Int> =
        File(dataInputPath).readLines().flatMap { it.split("") }
            .filter { it.isNotEmpty() }.map { it.toInt() }

    // 2126508822 - too low
    override fun doPart1(input: List<Int>): Int {
        val (files, freeSpace) = input.windowed(2,2).map { it.toPair() }.unzip()
        val lastFile = if (input.size % 2 == 1) listOf(input.last()) else emptyList()

        println("indexing")
        val indexedFiles = (files+lastFile).mapIndexed { index, number -> Pair(index, number) }
        val indexedFreeSpace = freeSpace.mapIndexed { index, number -> Pair(index, number) }

        println("blocking")
        val blocks = toBlocks(indexedFiles, indexedFreeSpace)

        println("sorting blocks")
        val sortedBlocks = sortBlocks(blocks)

        println("calculating checksum")
        val checkSum = sortedBlocks.filter{it != "."}.map { it.toInt() }.mapIndexed{index, number ->
            index * number
        }.sum()


        println(sortedBlocks)
        return checkSum
    }


    override fun doPart2(input: List<Int>): Int {
        return 0
    }

    private fun toBlocks(indexedFiles: List<Pair<Int, Int>>, indexedFreeSpace: List<Pair<Int, Int>>): List<String> {
        val lastFile = if (indexedFiles.size != indexedFreeSpace.size) listOf((indexedFiles.last().first.toString()+",").repeat(indexedFiles.first().second)) else emptyList()
        return (indexedFiles.zip(indexedFreeSpace) { file, space -> (file.first.toString()+",").repeat(file.second) + ".".repeat(space.second) } + lastFile).flatMap { it.split(",") }.flatMap { if(it.contains(".")) it.split("") else listOf(it) }.filter { it.isNotEmpty() }
    }

    private fun sortBlocks(blocks: List<String>): List<String> {
        val mutableBLocks = blocks.toMutableList()
        val usedBlocksSize = mutableBLocks.filter { it != "." }.size

        println("used blocks: $usedBlocksSize")

        val sortedBlocks = mutableBLocks.foldRightIndexed(mutableBLocks) { index, block, acc ->
            if (index % 1000 == 0) {
                println(index)
            }
            if (acc.indexOfFirst { it == "." } == usedBlocksSize) {
                return acc
            }
            if (block != ".") {
                val firstFreeSpaceIndex = acc.indexOfFirst { it == "." }
                acc.swap(firstFreeSpaceIndex, index)
            }

            acc
        }
        return sortedBlocks.toList()
    }

}