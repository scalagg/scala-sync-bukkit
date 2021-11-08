package gg.scala.sync.randomize

import java.util.concurrent.ThreadLocalRandom

/**
 * @author GrowlyX
 * @since 11/8/2021
 */
object RandomizedPhrase
{
    private const val CHARACTERS = "abcdefghijklmnopqrstuvwxyz1234567890"
    private const val LENGTH = CHARACTERS.length.toFloat()

    @JvmStatic
    @JvmOverloads
    fun create(size: Int = 9): String
    {
        var finalForm = ""

        while (finalForm.length < size)
        {
            val index = (ThreadLocalRandom.current().nextFloat() * LENGTH).toInt()
            finalForm += CHARACTERS[index]
        }

        return finalForm
    }
}
