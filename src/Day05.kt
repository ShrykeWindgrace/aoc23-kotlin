fun main() {
    val readInput = readInput("input_day05")
//    val readInput = readInput("input_day05.test")
    val seeds = readInput.first().drop("seeds: ".length).split(' ').map(String::toLong)

    val seq = readInput.drop(2)
        .splitPredicate { t -> t.isBlank() || t.isEmpty() }
        .map { l -> l.drop(1) }
        .map(::parseTweaks)

    seeds.minOfOrNull { fullApply(seq, it) }.println()

}


fun parseTweaks(lines: List<String>): List<Triple<Long, Long, Long>> {
    return lines.map(::parseTweak)
}

fun parseTweak(line: String): Triple<Long, Long, Long> {
    val ints = line.split(' ').map(String::toLong)
    return Triple(ints[1], ints[2], ints[0])
}

fun applyTweaks(tweaks: List<Triple<Long, Long, Long>>, x: Long): Long {
    for (tweak in tweaks)
        if (tweak.first <= x && x < tweak.second + tweak.first) {
            return x + tweak.third - tweak.first
        }
    return x
}

fun fullApply(tweakss: List<List<Triple<Long, Long, Long>>>, x: Long): Long {
    var y = x
    for (tweaks in tweakss) {
        y = applyTweaks(tweaks, y)
    }
    return y
}

