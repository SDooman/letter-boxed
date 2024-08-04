class GameBoard(private val charSets: List<Set<Char>>) {

    val allChars = charSets.flatten().toSet()

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
        allChars
            .minus(getSiblings(char))
            .minus(char)
}