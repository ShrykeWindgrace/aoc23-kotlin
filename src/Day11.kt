import kotlin.math.abs

fun main() {
    val readInput = readInput("input_day11")
    shortest(parseExtend(readInput, 2)).println()
    shortest(parseExtend(readInput, 1_000_000)).println()
}


fun manhattan(x: Pair<Long, Long>, y: Pair<Long, Long>): Long {
    return abs(x.first - y.first) + abs(x.second - y.second)
}


fun shortest(s: Set<Pair<Long, Long>>): Long {
    var acc: Long = 0
    for (left in s) {
        for (right in s) {
            if (left != right) {
                acc += manhattan(left, right)
            }
        }
    }
    return acc / 2
}

/*
fun parse(lines: List<String>): Set<Pair<Int, Int>> {
    val res: MutableSet<Pair<Int, Int>> = mutableSetOf()
    lines.forEachIndexed { y: Int, s: String ->
        s.forEachIndexed { x: Int, c: Char ->
            if (c == '#') {
                res.add(Pair(x, y))
            }
        }
    }
    return res
}


fun extend(lines: List<String>): List<String> {
    val extendRows: List<String> = lines.flatMap {
        if (allDot(it)) {
            listOf(it, it)
        } else {
            listOf(it)
        }
    }
    return transposeStrings(extendRows).flatMap {
        if (allDot(it)) {
            listOf(it, it)
        } else {
            listOf(it)
        }
    }
}
*/

fun reindex(list: List<String>, extension: Long): List<Long> {
    var ind: Long = 0
    val res: MutableList<Long> = mutableListOf()
    for (s in list) {
        res.add(ind)
        if (allDot(s)) {
            ind += extension
        } else {
            ind++
        }
    }
    return res
}

fun parseExtend(list: List<String>, extension: Long): Set<Pair<Long, Long>> {
    val ys = reindex(list, extension)
    val xs = reindex(transposeStrings(list), extension)

    val acc: MutableSet<Pair<Long, Long>> = mutableSetOf()

    list.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c == '#') {
                acc.add(Pair(xs[x], ys[y]))
            }
        }
    }
    return acc
}

fun allDot(x: String): Boolean {
    return x.all { it == '.' }
}
