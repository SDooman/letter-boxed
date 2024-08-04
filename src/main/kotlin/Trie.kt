class Trie(dictionary: Set<String>) {

    private val root = TrieNode(char = '*')

    init {
        dictionary.forEach {
            root.insert(it.lowercase(), 0)
        }
    }

    fun contains(word: String) = root.contains(word.lowercase())

    fun getPossibleWords(gameBoard: GameBoard): Set<String> {
        val accumulator = mutableSetOf<String>()

        for (char in gameBoard.allChars) {
            root.getPossibleWords(char, gameBoard, accumulator)
        }

        return accumulator
    }
}

private class TrieNode(
    private val char: Char,
    private var endsWord: Boolean = false,
    private val parent: TrieNode? = null
) {

    private val children: MutableMap<Char, TrieNode> = mutableMapOf()

    val word: String?
        get() =
            if (endsWord)
                StringBuilder().let {
                    var currentNode = this
                    while (currentNode.parent != null) {
                        it.append(currentNode.char)
                        currentNode = currentNode.parent!!
                    }
                    return it.reverse().toString()
                }
            else
                null

    fun insert(chars: String, index: Int) {
        val currentChar = chars[index]

        val child =
            children[currentChar]
                ?: TrieNode(
                    char = currentChar,
                    parent = this
                ).also {
                    children[currentChar] = it
                }

        if (index + 1 == chars.length) {
            child.endsWord = true
        } else {
            child.insert(chars, index + 1)
        }
    }

    fun contains(word: String) = contains(word, 0)

    private fun contains(word: String, index: Int): Boolean =
        children[word[index]]?.let { child ->
            if (index == word.length - 1)
                child.endsWord
            else
                child.contains(word, index + 1)
        } ?: false

    fun getPossibleWords(char: Char, gameBoard: GameBoard, acc: MutableSet<String>) {
        children[char]?.let { child ->
            child.word?.let {
                acc.add(it)
            }

            for (nextChar in gameBoard.getValidNextChars(char)) {
                child.getPossibleWords(nextChar, gameBoard, acc)
            }
        }
    }
}
