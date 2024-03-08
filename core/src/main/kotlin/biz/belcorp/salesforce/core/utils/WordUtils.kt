package biz.belcorp.salesforce.core.utils

object WordUtils {

    fun capitalizeFully(value: String?, vararg delimiters: Char): String {
        var str = value.orEmpty()
        if (str.isEmpty()) {
            return str
        }
        str = str.toLowerCase()
        return capitalize(str, *delimiters)
    }

    fun capitalize(str: String?, vararg delimiters: Char): String {
        if (str.isNullOrEmpty()) {
            return str.orEmpty()
        }
        val delimiterSet = generateDelimiterSet(*delimiters)
        val strLen = str.length
        val newCodePoints = IntArray(strLen)
        var outOffset = 0

        var capitalizeNext = true
        var index = 0
        while (index < strLen) {
            val codePoint = str.codePointAt(index)
            when {
                delimiterSet.contains(codePoint) -> {
                    capitalizeNext = true
                    newCodePoints[outOffset++] = codePoint
                    index += Character.charCount(codePoint)
                }
                capitalizeNext -> {
                    val titleCaseCodePoint = Character.toTitleCase(codePoint)
                    newCodePoints[outOffset++] = titleCaseCodePoint
                    index += Character.charCount(titleCaseCodePoint)
                    capitalizeNext = false
                }
                else -> {
                    newCodePoints[outOffset++] = codePoint
                    index += Character.charCount(codePoint)
                }
            }
        }
        return String(newCodePoints, 0, outOffset)
    }

    private fun generateDelimiterSet(vararg delimiters: Char): Set<Int> {
        val delimiterHashSet = hashSetOf<Int>()
        if (delimiters.isEmpty()) {
            delimiterHashSet.add(Character.codePointAt(charArrayOf(' '), 0))
            return delimiterHashSet
        }
        for (index in delimiters.indices) {
            delimiterHashSet.add(Character.codePointAt(delimiters, index))
        }
        return delimiterHashSet
    }

}
