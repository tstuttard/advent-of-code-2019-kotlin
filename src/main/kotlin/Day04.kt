class Day04 {

    fun isValidPassword(password: String): Boolean {
        if (password.length != 6) {
            return false
        }

        var isSameOrHasIncreased = true

        val groupedCharacters = mutableListOf<GroupedCharacterCount>()
        var currentListPointer = 0

        for ((index, character) in password.withIndex()) {

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


            if (character < password[index - 1]) {
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
