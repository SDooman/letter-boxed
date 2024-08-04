class Solver(dictionary: Set<String>, private val game: Game) {

    private val trie = Trie(dictionary = dictionary)

    private val possibleWords = trie.getPossibleWords(game)

    private val orderedByLength =
        possibleWords
            .sortedByDescending { it.length }

    private val groupedByLetter =
        possibleWords
            .groupBy { it.first() }

    fun solve(): List<String>? {
        for (word in orderedByLength) {
            val solution = solveInternal(sol = mutableListOf(word))

            if (solution != null) {
                return solution
            }
        }

        return null
    }

    private fun solveInternal(sol: MutableList<String>): List<String>? {
        if (isSolved(sol)) {
            return sol
        }

        val mostRecentWord = sol.last()

        val coveredChars =
            sol
                .flatMap { it.toCharArray().toSet() }
                .toSet()

        val missingChars = game.allChars.minus(coveredChars)

        val candidates =
            groupedByLetter[mostRecentWord.last()]
                ?.sortedByDescending {
                    it.toCharArray().toSet().intersect(missingChars).size
                }
                ?: listOf()

        if (sol.size < game.maxWords) {
            for (candidate in candidates) {
                sol.add(candidate)
                println("Trying ${sol.joinToString(",")}")
                val result = solveInternal(sol)

                if (result != null) {
                    return result
                } else {
                    sol.removeLast()
                }
            }
        }

        return null
    }

    private fun isSolved(words: List<String>) =
        words
            .flatMap { it.toCharArray().toList() }
            .toSet() == game.allChars
}