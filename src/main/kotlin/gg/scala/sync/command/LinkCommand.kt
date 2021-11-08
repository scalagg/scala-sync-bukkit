package gg.scala.sync.command

import gg.scala.sync.handler.LinkHandler
import gg.scala.sync.randomize.RandomizedPhrase
import net.evilblock.cubed.acf.BaseCommand
import net.evilblock.cubed.acf.annotation.CommandAlias
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.FancyMessage
import net.md_5.bungee.api.chat.ClickEvent
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
        player.sendMessage("${CC.GRAY}Attempting code generation...")

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

    fun attemptLinkWithCode(player: Player, code: String, persist: Boolean)
    {
        val lambda: () -> Unit = {
            val fancyMessage = FancyMessage()

            fancyMessage.withMessage(
                "",
                " ${CC.B_PRI}Discord Sync",
                " ${CC.GRAY}Your code has been generated!",
                ""
            )
            fancyMessage.withMessage(
                " ${CC.I_WHITE}Click this message to view the command!"
            )
            fancyMessage.andCommandOf(
                ClickEvent.Action.SUGGEST_COMMAND, "-sync $code"
            )
            fancyMessage.withMessage(
                "",
                " ${CC.PRI}Follow the steps below to sync:",
                "  ${CC.WHITE}1. ${CC.YELLOW}Join our discord, ${CC.WHITE}discord.gg/pvpbar${CC.YELLOW}.",
                "  ${CC.WHITE}2. ${CC.YELLOW}Click on the ${CC.WHITE}#sync${CC.YELLOW} channel.",
                "  ${CC.WHITE}3. ${CC.YELLOW}Execute the ${CC.WHITE}-sync $code${CC.YELLOW} command!",
                "",
                "${CC.I_WHITE}If you encounter any issues, create",
                "${CC.I_WHITE}a support ticket in our discord!",
            )

            fancyMessage.sendToPlayer(player)
        }

        if (persist)
        {
            LinkHandler.codes.saveEntry(player.uniqueId.toString(), code).thenRun {
                lambda.invoke()
            }
        } else
        {
            lambda.invoke()
        }
    }
}
