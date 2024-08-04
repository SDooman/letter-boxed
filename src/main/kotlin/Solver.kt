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

        val missingChars =
            game
                .allChars
                .minus(
                    sol
                        .flatMap { it.toCharArray().toSet() }
                        .toSet()
                )

        val candidates =
            sol
                .lastOrNull()
                ?.let { mostRecentWord ->
                    groupedByFirstLetter[mostRecentWord.last()]
                        ?.sortedByDescending {
                            objectiveFunction(word = it, missingChars = missingChars)
                        }
                        ?: listOf()
                }
                ?: possibleWords.sortedByDescending {
                    objectiveFunction(word = it, missingChars = missingChars)
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

    private fun objectiveFunction(word: String, missingChars: Set<Char>) =
        word.toCharArray().toSet().intersect(missingChars).size

    private fun isSolved(words: List<String>) =
        words
            .flatMap { it.toCharArray().toList() }
            .toSet() == game.allChars
}