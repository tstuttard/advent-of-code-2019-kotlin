class Day04 {
    fun isValidPassword(possiblePassword: String): Boolean {


        if (possiblePassword.length != 6) {
            return false
        }

        var isSameOrHasIncreased = true

        val groupedCharacters = mutableListOf<Pair<Char, Int>>()
        var currentListPointer = 0

        for ((index, character) in possiblePassword.withIndex()) {

            if (index == 0) {
                groupedCharacters.add(Pair(character, 1))
                continue
            }

            if (character == groupedCharacters[currentListPointer].first) {
                val characterCount = groupedCharacters[currentListPointer].second + 1
                groupedCharacters[currentListPointer] = Pair(character, characterCount)
            } else {
                groupedCharacters.add(Pair(character, 1))
                currentListPointer++
            }


            if (character.toInt() < possiblePassword[index-1].toInt()) {
                isSameOrHasIncreased = false
            }
        }

        return groupedCharacters.count { it.second == 2 } > 0 && isSameOrHasIncreased

    }

    fun findValidPasswords(passwordRange: String): List<String> {
        val passwordStart = passwordRange.split('-').first()
        val passwordEnd = passwordRange.split('-').last()


        val passwords = (passwordStart.toInt()..passwordEnd.toInt()).map { it.toString() }

        return passwords.filter { isValidPassword(it) }
    }

}
