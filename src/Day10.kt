import kotlin.system.measureTimeMillis

fun main() {
    val closeMap = mapOf(
        Char(40) to Char(41),
        Char(60) to Char(62),
        Char(91) to Char(93),
        Char(123) to Char(125)
    )

    val scores = mapOf(
        Char(41) to 3,
        Char(93) to 57,
        Char(125) to 1197,
        Char(62) to 25137
    )

    val p2scores = mapOf(
        Char(41) to 1L,
        Char(93) to 2L,
        Char(125) to 3L,
        Char(62) to 4L
    )

    val openingBraces = closeMap.keys.toList()

    fun part1(input: List<String>): Int {
        val list = input.map {
            val expectedClosingCharacter = ArrayDeque<Char>()
            for (i in it.indices) {
                val character = it[i]
                if (openingBraces.contains(character)) {
                    expectedClosingCharacter.addLast(closeMap[character]!!)
                } else if (character == expectedClosingCharacter.last()) {
                    expectedClosingCharacter.removeLast()
                } else {
                    return@map character
                }
            }
            return@map null
        }.filterNotNull()

        println(list)

        return list.fold(0) { acc, char -> acc + (scores[char] ?: 0) }
    }

    fun part2(input: List<String>): Long {
        val list =  input.map {
            val expectedClosingCharacter = ArrayDeque<Char>()
            for (i in it.indices) {
                val character = it[i]
                if (openingBraces.contains(character)) {
                    expectedClosingCharacter.addLast(closeMap[character]!!)
                } else if (character == expectedClosingCharacter.last()) {
                    expectedClosingCharacter.removeLast()
                } else {
                    return@map null
                }
            }
            return@map expectedClosingCharacter.joinToString("").reversed()
        }.filterNotNull()

        val theScores = list.map {
            it.fold(0L) { acc, char -> acc*5 + (p2scores[char] ?: 0L) }
        }.sorted()

        return theScores[((theScores.size + 1)/2) - 1]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 executed in $part2Measure ms")
}
