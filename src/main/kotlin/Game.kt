class Game(charSets: List<Set<Char>>, val maxWords: Int) {

    private val charSetsLowerCase =
        charSets.map { row ->
            row.map {
                it.lowercaseChar()
            }.toSet()
        }

    val allChars = charSetsLowerCase.flatten().toSet()

    private val siblingChars =
        charSetsLowerCase
            .flatMap { set ->
                set.map {
                    it to set.minus(it)
                }
            }
            .toMap()

    private fun getSiblings(char: Char) =
        siblingChars[char] ?: setOf()

    fun getValidNextChars(char: Char) =
        allChars
            .minus(getSiblings(char))
            .minus(char)
}