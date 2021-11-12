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
    private val RANDOM = ThreadLocalRandom.current()

    @JvmStatic
    @JvmOverloads
    fun create(size: Int = 9): String
    {
        var finalForm = ""

        while (finalForm.length < size)
        {
            val index = RANDOM.nextFloat() * LENGTH
            finalForm += CHARACTERS[index.toInt()]
        }

        return finalForm
    }
}
