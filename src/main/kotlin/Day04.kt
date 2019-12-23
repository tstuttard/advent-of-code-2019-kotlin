class Day04 {
    fun isValidPassword(possiblePassword: String): Boolean {


        if (possiblePassword.length != 6) {
            return false
        }

        var isSameOrHasIncreased = true

        val groupedCharacters = mutableListOf<GroupedCharacterCount>()
        var currentListPointer = 0

        for ((index, character) in possiblePassword.withIndex()) {

            if (index == 0) {
                groupedCharacters.add(GroupedCharacterCount(character))
                continue
            }

            if (character == groupedCharacters[currentListPointer].character) {
                groupedCharacters[currentListPointer].increaseCount()
            } else {
                groupedCharacters.add(GroupedCharacterCount(character))
                currentListPointer++
            }


            if (character.toInt() < possiblePassword[index - 1].toInt()) {
                isSameOrHasIncreased = false
            }
        }

        return groupedCharacters.count { it.count == 2 } > 0 && isSameOrHasIncreased

    }

    fun findValidPasswords(passwordRange: String): List<String> {
        val passwordStart = passwordRange.split('-').first()
        val passwordEnd = passwordRange.split('-').last()


        val passwords = (passwordStart.toInt()..passwordEnd.toInt()).map { it.toString() }

        return passwords.filter { isValidPassword(it) }
    }

}

data class GroupedCharacterCount(val character: Char) {

    var count = 1
        private set

    fun increaseCount() {
        count++
    }

}
