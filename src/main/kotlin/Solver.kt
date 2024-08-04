class Solver(dictionary: Set<String>, private val game: Game) {

    private val trie = Trie(dictionary = dictionary)

    private val possibleWords = trie.getPossibleWords(game)

    private val groupedByFirstLetter =
        possibleWords
            .groupBy { it.first() }

    fun solve() = solveInternal(sol = mutableListOf())

    private fun solveInternal(sol: MutableList<String>): List<String>? {
        if (isSolved(sol)) {
            return sol
        }

        val coveredChars =
            sol
                .flatMap { it.toCharArray().toSet() }
                .toSet()

        val missingChars = game.allChars.minus(coveredChars)

        val candidates =
            sol
                .lastOrNull()
                ?.let { mostRecentWord ->
                    groupedByFirstLetter[mostRecentWord.last()]
                        ?.sortedByDescending {
                            it.toCharArray().toSet().intersect(missingChars).size
                        }
                        ?: listOf()
                }
                ?: possibleWords.sortedByDescending {
                    it.toCharArray().toSet().intersect(missingChars).size
                }

        if (sol.size < game.maxWords) {
            for (candidate in candidates) {
                sol.add(candidate)
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