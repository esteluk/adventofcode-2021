import kotlin.system.measureTimeMillis

class Graph {
    val adjacencyMap: HashMap<String, HashSet<String>> = HashMap()

    fun addEdge(source: String, destination: String) {
        adjacencyMap.computeIfAbsent(source) { HashSet() }
            .add(destination)

        adjacencyMap.computeIfAbsent(destination) { HashSet() }
            .add(source)
    }

    fun calculate(start: String, end: String, twoVisits: String): Set<List<String>> {
        fun pathsFrom(path: List<String>): Set<List<String>> {
            if (path.last() == end) {
                return setOf(path)
            }
            val connectedNodes = adjacencyMap[path.last()]
                ?.filter { node ->
                    node.uppercase() == node
                            || !path.contains(node)
                            || (node == twoVisits && path.count { it == node} < 2)
                }
            return connectedNodes!!.map { path + it }
                .map { pathsFrom(it) }.flatten().toSet()
        }

        return pathsFrom(listOf(start))
    }

    fun possibleTwoVisits(): List<String> {
        return adjacencyMap.keys.filter { it.lowercase() == it }
    }

    override fun toString(): String = StringBuffer().apply {
        for (key in adjacencyMap.keys) {
            append("$key -> ")
            append(adjacencyMap[key]?.joinToString(", ", "[", "]\n"))
        }
    }.toString()
}

fun main() {
    fun part1(input: List<String>): Int {
        val graph = Graph()

        input.map { it.split("-") }
            .forEach { row ->
                graph.addEdge(row[0], row[1])
            }

        val paths = graph.calculate("start", "end", "")
        return paths.size
    }

    fun part2(input: List<String>): Int {
        val graph = Graph()

        input.map { it.split("-") }
            .forEach { row ->
                graph.addEdge(row[0], row[1])
            }

        val paths = graph.possibleTwoVisits()
            .filter { it != "start" && it != "end" }
            .map { graph.calculate("start", "end", it) }
            .flatten().toSet()

//        println(paths.joinToString("\n"))
//        println(paths.size)
        return paths.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test1")
    val testInput2 = readInput("Day12_test2")
    val testInput3 = readInput("Day12_test3")
    check(part1(testInput) == 10)
    check(part1(testInput2) == 19)
    check(part1(testInput3) == 226)

    check(part2(testInput) == 36)
    check(part2(testInput2) == 103)
    check(part2(testInput3) == 3509)

    val input = readInput("Day12")
    val part1Measure = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 executed in $part1Measure ms")

    val part2Measure = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 executed in $part2Measure ms")
}
