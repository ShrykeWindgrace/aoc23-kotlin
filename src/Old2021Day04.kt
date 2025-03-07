import java.util.regex.Pattern

fun main() {
    val inp = readInput("old2021\\input_day04").splitPredicate { it.isEmpty() }

    val flow = inp[0][0].split(',').map { it.toInt() }

    val game: List<Board> = inp.drop(1).map { list ->
        val b = Board()
        val ints = list.joinToString(" ").trim().split(Pattern.compile("\\s+")).map { it.toInt() }
        b.fill(ints)
        b
    }

    /*
    // part 1
    for (entry in flow) {
        for (g in game) {
            if (g.mark(entry)){
                (g.unmarked() * entry).println()
                return
            }
        }
    }*/

    val mutableGame = game.toMutableList()
    for (entry in flow) {
        for (g in mutableGame) {
            if (g.mark(entry)) {
                (g.unmarked() * entry).println()
            }
        }
        mutableGame.removeIf { !it.mark(entry) }
    }

}

private class Board {
    private val marked: IntArray = IntArray(25)
    private val numbers: IntArray = IntArray(25)

    fun fill(data: List<Int>) {
        data.forEachIndexed { index, d -> numbers[index] = d }
    }

    fun mark(x: Int): Boolean {
        val index = numbers.indexOf(x)
        if (-1 == index) {
            return false
        }
        marked[index] = 1

        return check()
    }

    private fun check(): Boolean {
        // rows
        for (i in 0..4) {
            var mark = 1
            for (j in 5 * i..5 * i + 4) {
                mark *= marked[j]
            }
            if (mark == 1) {
                return true
            }
        }
        // columns
        for (i in 0..4) {
            var mark = 1
            for (j in 0..4) {
                mark *= marked[5 * j + i]
            }
            if (mark == 1) {
                return true
            }
        }
        return false
    }

    fun unmarked(): Int {
        return marked.zip(numbers) { m, n -> (1 - m) * n }.sum()
    }
}