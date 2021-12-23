import kotlin.math.ceil
import kotlin.math.max
import kotlin.system.measureTimeMillis

fun main() {

    fun insertions(polymer: String, formula: Map<String, String>): String {
        val windows = polymer.windowed(2)
            .map {
                if (formula.contains(it)) {
                    val builder = StringBuilder(it)
                    builder.insert(1, formula[it])
                    return@map builder.toString()
                } else {
                    it
                }
            }

        val builder = StringBuilder()
        windows.forEachIndexed { index, window ->
            builder.append(window)
            if (index != windows.lastIndex) {
                builder.deleteCharAt(builder.lastIndex)
            }
        }
        return builder.toString()
    }

    fun part1(input: List<String>): Int {
        val template = input.first()
        val formula = input.takeLast(input.size - 2)
            .map { it.split(" -> ") }.associate { it[0] to it[1] }

        val iterations = 10
        var polymer = template
        for (i in 1..iterations) {
            polymer = insertions(polymer, formula)
        }

        val counts = polymer.toCharArray().asList().groupingBy { it }.eachCount()
        val pairs = counts.toList().sortedBy { it.second }

        return pairs.last().second - pairs.first().second
    }

    fun part2(input: List<String>, iterations: Int): Long {
        val template = input.first()
        val formula = input.takeLast(input.size - 2)
            .map { it.split(" -> ") }
            .associate { it[0] to it[1] }

        val pairCounts = HashMap<String, Long>()
        template.windowed(2)
            .forEach {
                pairCounts[it] = pairCounts.computeIfAbsent(it) { 0 } + 1
            }

        fun efficientInsertions() {
            val initialMap = pairCounts.toMap()
            initialMap.forEach { entry ->
                if (formula.contains(entry.key)) {
                    // Create two new key pairs
                    val char = formula[entry.key]
                    val key = entry.key.substring(0, 1) + char
                    val key2 = char + entry.key.substring(1, 2)
                    pairCounts[key] = pairCounts.computeIfAbsent(key) { 0 }
                        .plus(entry.value)
                    pairCounts[key2] = pairCounts.computeIfAbsent(key2) { 0 }
                        .plus(entry.value)
                    pairCounts[entry.key] = max(0, pairCounts[entry.key]!! - entry.value)
                }
            }
        }

        for (i in 1..iterations) {
            efficientInsertions()
        }

        val characterCounts = HashMap<Char, Long>()

        pairCounts.forEach { entry ->
            entry.key.toCharArray().forEach {
                characterCounts[it] = characterCounts.computeIfAbsent(it) { 0 }
                    .plus(entry.value)
            }
        }

        val sorted = characterCounts.toList()
            .sortedBy { it.second }
            .map { Pair(it.first, ceil(it.second / 2.0).toLong()) }

        return sorted.last().second - sorted.first().second
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
    check(part2(testInput, 10) == 1588L)

    val input = readInput("Day14")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input, 40))
    }
    println("Part 2 executed in $part2Measure ms")
}
