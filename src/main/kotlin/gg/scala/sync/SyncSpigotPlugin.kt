package gg.scala.sync

import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.sync.command.LinkCommand
import gg.scala.sync.handler.LinkHandler
import net.evilblock.cubed.command.manager.CubedCommandManager

/**
 * @author GrowlyX
 * @since 11/8/2021
 */
class SyncSpigotPlugin : ExtendedScalaPlugin()
{
    override fun enable()
    {
        val manager = CubedCommandManager(this)
        LinkHandler.initialLoad()

        manager.registerCommand(LinkCommand())
    }
}
