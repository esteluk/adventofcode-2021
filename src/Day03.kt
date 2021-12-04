fun main() {
    fun part1(input: List<String>): Int {
        val length = input[0].length
        val cutoff = input.size / 2
        var oneCounts = IntArray(length)

        val one = Char(49)
        for (binary in input) {
            val charArray = binary.toCharArray()
            for (i in charArray.indices) {
                if (charArray[i] == one) {
                    oneCounts[i] += 1
                }
            }
        }

        val gamma = oneCounts.map { if (it > cutoff) 1 else 0 }
            .joinToString("")
            .toInt(2)

        val epsilon = oneCounts.map { if (it > cutoff) 0 else 1 }
            .joinToString("")
            .toInt(2)

        return gamma*epsilon
    }

    fun filter(matrix: List<CharArray>, column: Int, isOxygen: Boolean) : List<CharArray> {
        val zero = Char(48)
        val one = Char(49)

        val onesCount = matrix.map { it[column] }.count { it == one }
        val filter = if (onesCount >= (matrix.size - onesCount)) {
            if (isOxygen) one else zero
        } else {
            if (isOxygen) zero else one
        }
        return matrix.filter { it[column] == filter }
    }

    fun part2(input: List<String>): Int {

        var column = 0
        var oxygenChars = input.map { it.toCharArray() }

        while (oxygenChars.size > 1) {
            oxygenChars = filter(oxygenChars, column, true)
            column += 1
        }

        println(oxygenChars.first().joinToString())
        val oxygenRating = oxygenChars.first().joinToString("").toInt(2)

        column = 0
        var co2Chars = input.map { it.toCharArray() }
        while (co2Chars.size > 1) {
            co2Chars = filter(co2Chars, column, false)
            column += 1
        }

        println(co2Chars.first().joinToString())

        val co2Rating = co2Chars.first().joinToString("").toInt(2)

        return oxygenRating*co2Rating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
