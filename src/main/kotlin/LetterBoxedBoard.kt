class GameBoard(private val charSets: List<Set<Char>>) {

    private val siblingChars =
        charSets
            .flatMap { set ->
                set.map {
                    it to set.minus(it)
                }
            }
            .toMap()

    private fun getSiblings(char: Char) =
        siblingChars[char] ?: setOf()

    fun getValidNextChars(char: Char) =
        charSets
            .flatten()
            .toSet()
            .minus(getSiblings(char))
            .minus(char)
}