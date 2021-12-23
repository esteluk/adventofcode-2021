import kotlin.math.max
import kotlin.system.measureTimeMillis

fun main() {

    fun fold(graph: MutableSet<Pair<Int, Int>>, direction: String, value: Int) {
        val initialGraph = graph.toSet()
        if (direction == "x") {
            for (element in initialGraph) {
                if (element.first > value) {
                    graph.add(Pair(2*value - element.first, element.second))
                    graph.remove(element)
                }
            }
        } else if (direction == "y") {
            for (element in initialGraph) {
                if (element.second > value) {
                    graph.add(Pair(element.first, 2*value - element.second))
                    graph.remove(element)
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val graph = mutableSetOf<Pair<Int, Int>>()
        val splitIndex = input.indexOf("")
        val coordinates = input.take(splitIndex)
        val instructions = input.takeLast(input.lastIndex-splitIndex)

        coordinates.map { it.split(",") }
            .forEach {
                graph.add(Pair(it[0].toInt(), it[1].toInt()))
            }

        val instruction = instructions
            .first()
            .split(" ")
            .takeLast(1)[0]
            .split("=")

        val value = instruction[1].toInt()

        fold(graph, instruction[0], value)

        return graph.size
    }

    fun part2(input: List<String>): Int {

        val graph = mutableSetOf<Pair<Int, Int>>()
        val splitIndex = input.indexOf("")
        val coordinates = input.take(splitIndex)
        val instructions = input.takeLast(input.lastIndex-splitIndex)

        coordinates.map { it.split(",") }
            .forEach {
                graph.add(Pair(it[0].toInt(), it[1].toInt()))
            }

        instructions.map { it.split(" ").takeLast(1)[0].split("=") }
            .forEach {
                val value = it[1].toInt()
                fold(graph, it[0], value)
            }

        val maxX = graph.fold(0) { acc, node -> max(acc, node.first) }
        val maxY = graph.fold(0) { acc, node -> max(acc, node.second) }

        val picture = mutableListOf<String>()
        for (row in 0..maxY) {
            val builder = StringBuilder()
            for (index in 0..maxX) {
                if (graph.contains(Pair(index, row))) {
                    builder.append("#")
                } else {
                    builder.append(" ")
                }
            }
            picture.add(builder.toString())
        }

        println(picture.joinToString("\n"))

        return graph.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
//    check(part2(testInput) == 36)

    val input = readInput("Day13")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 executed in $part2Measure ms")
}
