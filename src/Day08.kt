import java.util.stream.Collectors

fun main() {
    val readInput = readInput("input_day08")

    val directions: Sequence<Char> = cycle(readInput[0])
    val directions2: Sequence<Char> = cycle(readInput[0])
    val iterator = directions.iterator()

    val mapping = buildMap(readInput.drop(2))

    var nodes = readInput.drop(2).map { it.take(3) }.filter { it.endsWith("A") }.take(4).toSet()

    var count = 0

    var current = "AAA"

    while (current != "ZZZ") {
        val look = mapping[current]
        if (look != null) {
            val next: Char = iterator.next()
            when (next) {
                'R' -> {
                    current = look.second
                    count++
                }
                'L' -> {
                    current = look.first
                    count++
                }
                else -> {
                    println("fail")
                }
            }
        } else {
            "lookup fail".println()
        }


    }
    count.println()

    "part 2".println()
    count = 0
    val iterator2 = directions2.iterator()
    while (!allZ(nodes)) {
        if (count % 10000000 == 0) {
            println("nodes size ${nodes.size}, $nodes, step $count")
        }
        val next: Char = iterator2.next()

        nodes = nodes.stream().map {
            val look = mapping[it]
            if (look != null) {
                when (next) {
                    'R' -> {
                        return@map look.second
                    }
                    'L' -> {
                        return@map look.first
                    }
                    else -> {
                        return@map "ZZZZ"
                    }
                }
            } else {
                "lookup fail".println()
                return@map "ZZZZ"
            }
        }.collect(Collectors.toSet())
        count++


    }
    count.println()


}

fun buildMap(lines: List<String>): Map<String, Pair<String, String>> {
    val res: MutableMap<String, Pair<String, String>> = mutableMapOf()

    lines.forEach {
        val key = it.take(3)
        val left = it.drop(7).take(3)
        val right = it.drop(12).take(3)
        res[key] = Pair(left, right)
    }
    return res
}

fun cycle(xs: String): Sequence<Char> {
    var i : Long = 0
    return generateSequence { xs[(i++ % xs.length).toInt()] }
}

fun allZ(nodes: Set<String>): Boolean {
    return nodes.stream().allMatch { it.endsWith('Z') }
}