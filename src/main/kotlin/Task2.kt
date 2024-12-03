import java.io.File

class Task2(private val dataInputPath: String) : Task<List<List<Int>>> {

    override fun parseInput(isPart2: Boolean): List<List<Int>> =
        File(dataInputPath).readLines()
            .map { line ->
                line.split(" ")
                    .map { number -> number.toInt() }
            }

    override fun doPart1(input: List<List<Int>>): Int {
        val safeReportsInc = getSafeReports(input, listOf(1, 2, 3))
        val safeReportsDesc = getSafeReports(input, listOf(-1, -2, -3))

        return safeReportsInc.size + safeReportsDesc.size
    }

    override fun doPart2(input: List<List<Int>>): Int {
        val legalSumsInc = listOf(1, 2, 3)
        val legalSumsDesc = listOf(-1, -2, -3)

        val safeReportsInc = getSafeReports(removeFirstIllegalNumber(input, legalSumsInc), legalSumsInc)
        val safeReportsDesc = getSafeReports(removeFirstIllegalNumber(input, legalSumsDesc), legalSumsDesc)

        return safeReportsInc.size + safeReportsDesc.size
    }

    private fun removeFirstIllegalNumber(reports: List<List<Int>>, legalSums: List<Int>) = reports.map { report ->
        val illegalNumber = getFirstIllegalNumber(report, legalSums)
        if (illegalNumber !== null) {
            val index = report.indexOf(illegalNumber)
            report.take(index) + report.drop(index +1)
        } else {
            report
        }
    }

    private fun getFirstIllegalNumber(report: List<Int>, legalSums: List<Int>): Int? {
        if (!satisfiesConstraints(report.take(2), legalSums)) {
            return if (satisfiesConstraints(listOf(report[1], report[2]), legalSums)) {
                report.first()
            } else {
                report[1]
            }
        }

        report.windowed(2, 1).forEach { pair ->
            if (!satisfiesConstraints(pair, legalSums)) {
                return pair[1]
            }
        }

        return null
    }

    private fun getSafeReports(reports: List<List<Int>>, legalSums: List<Int>) =
        reports.filter { report ->
            report.windowed(2, 1)
                .all { pair -> satisfiesConstraints(pair, legalSums) }
        }

    private fun satisfiesConstraints(pair: List<Int>, legalSums: List<Int>): Boolean =
        pair[1] - pair[0] in legalSums
}