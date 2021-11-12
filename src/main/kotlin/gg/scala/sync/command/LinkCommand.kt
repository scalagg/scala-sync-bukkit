package gg.scala.sync.command

import gg.scala.sync.handler.LinkHandler
import gg.scala.sync.randomize.RandomizedPhrase
import net.evilblock.cubed.acf.BaseCommand
import net.evilblock.cubed.acf.annotation.CommandAlias
import net.evilblock.cubed.util.CC
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 11/8/2021
 */
class LinkCommand : BaseCommand()
{
    @CommandAlias("link|sync")
    fun onLink(player: Player)
    {
        val uniqueId = player.uniqueId.toString()

        LinkHandler.linked.fetchEntryByKey(uniqueId).thenAccept {
            if (it != null)
            {
                player.sendMessage("${CC.RED}Your account is already linked to ${CC.BOLD}$it${CC.RED}.")
                return@thenAccept
            }

            // We're going to check for a pre-existing code before
            // possibly overriding the existing code in our cache.
            LinkHandler.codes.fetchEntryByKey(uniqueId).thenAccept { code ->
                attemptLinkWithCode(
                    player = player,
                    code = code ?: RandomizedPhrase.create(),
                    persist = code == null
                )
            }
        }
    }

    private fun attemptLinkWithCode(player: Player, code: String, persist: Boolean)
    {
        val lambda: () -> Unit = {
            player.sendMessage(
                arrayOf(
                    "",
                    " ${CC.B_PRI}Discord Sync",
                    " ${CC.GRAY}Your code has been generated!",
                    "",
                    " ${CC.PRI}Follow the steps below to sync:",
                    "  ${CC.GRAY}1. ${CC.WHITE}Join our discord, ${CC.YELLOW}discord.gg/pvpbar${CC.WHITE}.",
                    "  ${CC.GRAY}2. ${CC.WHITE}Click on the ${CC.YELLOW}#discord-sync${CC.WHITE} channel.",
                    "  ${CC.GRAY}3. ${CC.WHITE}Execute the ${CC.YELLOW}-sync $code${CC.WHITE} command!",
                    "",
                    " ${CC.I_GRAY}If you encounter any issues, create",
                    " ${CC.I_GRAY}a support ticket in our discord!",

                )
            )
        }

        if (persist)
        {
            LinkHandler.codes.saveEntry(
                player.uniqueId.toString(), code
            ).thenRun { lambda.invoke() }
        } else
        {
            lambda.invoke()
        }
    }
}
