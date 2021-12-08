import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {

    fun pairsInRange(a: Pair<Int, Int>, b: Pair<Int, Int>, diagonal: Boolean): List<Pair<Int, Int>> {
        return if (a.first == b.first) {
            val range = if (a.second < b.second) {
                a.second..b.second
            } else {
                b.second..a.second
            }
            range.map { Pair(a.first, it) }
        } else if (a.second == b.second) {
            val range = if (a.first < b.first) {
                a.first..b.first
            } else {
                b.first..a.first
            }
            range.map { Pair(it, a.second) }
        } else if (diagonal && abs(a.first - b.first) == abs(a.second - b.second)) {
            if (a.first < b.first && a.second < b.second) {
                (a.first..b.first) zip (a.second..b.second)
            } else if (a.first < b.first && a.second > b.second) {
                (a.first..b.first) zip (a.second downTo b.second)
            } else if (a.first > b.first && a.second < b.second) {
                (a.first downTo b.first) zip (a.second..b.second)
            } else if (a.first > b.first && a.second > b.second) {
                (a.first downTo b.first) zip (a.second downTo b.second)
            } else {
                listOf()
            }
        } else {
            listOf()
        }
    }

    fun coordPair(input: List<String>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
        return input
            .map { it.split(" ").slice(setOf(0, 2)) }
            .map { line ->
                val j = line.map { pair ->
                    val i = pair.split(",")
                        .map { it.toInt() }
                    Pair(i[0], i[1])
                }
                Pair(j[0], j[1])
            }
    }

    fun part1(input: List<String>): Int {
        val coordPairs = coordPair(input)
        println(coordPairs.joinToString())

        val array = Array(1000) { IntArray(1000) }

        coordPairs.map {
            pairsInRange(it.first, it.second, false)
        }.forEach { range ->
            range.forEach {
                array[it.second][it.first] += 1
            }
        }

        return array.fold(0) { acc, row -> acc + row.count { it > 1 } }
    }

    fun part2(input: List<String>): Int {
        val coordPairs = coordPair(input)
        println(coordPairs.joinToString())

        val array = Array(1000) { IntArray(1000) }

        coordPairs.map {
            pairsInRange(it.first, it.second, true)
        }.forEach { range ->
            range.forEach {
                array[it.second][it.first] += 1
            }
        }

        return array.fold(0) { acc, row -> acc + row.count { it > 1 } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 executed in $part2Measure ms")
}
