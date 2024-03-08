package biz.belcorp.salesforce.core.utils

import androidx.emoji.text.EmojiCompat

private fun getEmojiCompat() = try {
    EmojiCompat.get()
} catch (e: Exception) {
    Logger.e(e)
    null
}

fun createEmoji(emoji: String): String = getEmojiCompat()?.process(emoji)?.toString().orEmpty()
