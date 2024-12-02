import java.io.File

class Task2(private val dataInputPath: String) : Task<List<List<Int>>> {

    override fun parseInput(): List<List<Int>> =
        File(dataInputPath).readLines()
            .map { line ->
                line.split(" ")
                    .map { number -> number.toInt() }
            }

    override fun doPart1(input: List<List<Int>>): Int {
        val safeReportsInc = inferSafeReports(input, listOf(1, 2, 3))
        val safeReportsDesc = inferSafeReports(input, listOf(-1, -2, -3))

        return safeReportsInc.size + safeReportsDesc.size
    }

    override fun doPart2(input: List<List<Int>>): Int {
        val legalSumsInc = listOf(1, 2, 3)
        val legalSumsDesc = listOf(-1, -2, -3)

        val safeReportsInc = inferSafeReports(removeFirstIllegalNumber(input, legalSumsInc), legalSumsInc)
        val safeReportsDesc = inferSafeReports(removeFirstIllegalNumber(input, legalSumsDesc), legalSumsDesc)

        return safeReportsInc.size + safeReportsDesc.size
    }

    private fun removeFirstIllegalNumber(reports: List<List<Int>>, legalSums: List<Int>) = reports.map { report ->
        val illegalNumber = findIllegalNumber(report, legalSums)
        if (illegalNumber !== null) {
            val index = report.indexOf(illegalNumber)
            report.take(index) + report.drop(index +1)
        } else {
            report
        }
    }

    private fun findIllegalNumber(report: List<Int>, sums: List<Int>): Int? {
        if (!satifiesConstraints(report.take(2), sums)) {
            return if (satifiesConstraints(listOf(report[1], report[2]), sums)) {
                report.first()
            } else {
                report[1]
            }
        }

        report.windowed(2, 1).forEach { levelPair ->
            if (!satifiesConstraints(levelPair, sums)) {
                return levelPair[1]
            }
        }

        return null
    }

    private fun inferSafeReports(reports: List<List<Int>>, legalSums: List<Int>) =
        reports.filter { report ->
            report.windowed(2, 1)
                .all { levelPair -> satifiesConstraints(levelPair, legalSums) }
        }

    private fun satifiesConstraints(levelPair: List<Int>, sums: List<Int>): Boolean =
        levelPair[1] - levelPair[0] in sums
}