fun main() {
    val readInput: List<Pair<Int, Int>> = readInput("input_day04.txt").map(::parse)
//    val readInput: List<String> = readInput("input_day04.txt.test")
    readInput.map(::worth).sum().println()

    val n = readInput.size
    val cardCount: MutableMap<Int, Int> = mutableMapOf()

    for (card in 1..n) {
        cardCount[card] = 1
    }
    readInput.forEach { p ->
        val copies = cardCount.getOrDefault(p.first, 0)
        for (key in p.first + 1..p.first + p.second) {
            cardCount.merge(key, copies, Integer::sum)
        }
    }
    cardCount.toList().sumOf(Pair<Int, Int>::second).println()
}


fun parse(s: String): Pair<Int, Int> {
    val colonIndex = s.indexOf(':')
    val pipeIndex = s.indexOf('|')
    val gameId = Integer.parseInt(s.substring(5, colonIndex).trim())
    val left: Set<Int> = s.substring(colonIndex + 1, pipeIndex)
        .split(' ')
        .filter(String::isNotBlank)
        .map(Integer::parseInt)
        .toSet()
    val right: Set<Int> = s.substring(pipeIndex + 1)
        .split(' ')
        .filter(String::isNotBlank)
        .map(Integer::parseInt)
        .toSet()
    return Pair(gameId, left.intersect(right).size)
}

fun worth(lr: Pair<Int, Int>): Int {
    return if (lr.second == 0) {
        0
    } else {
        1 shl (lr.second - 1)
    }
}

