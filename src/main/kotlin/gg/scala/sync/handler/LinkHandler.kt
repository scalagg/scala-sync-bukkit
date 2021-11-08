package gg.scala.sync.handler

import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer
import gg.scala.lemon.Lemon

/**
 * @author GrowlyX
 * @since 11/8/2021
 */
object LinkHandler
{
    lateinit var linked: RedisStorageLayer<String>
    lateinit var codes: RedisStorageLayer<String>

    fun initialLoad()
    {
        linked = RedisStorageLayer(
            Lemon.instance.redisConnection,
            "scala:sync:linked",
            String::class.java
        )
        codes = RedisStorageLayer(
            Lemon.instance.redisConnection,
            "scala:sync:codes",
            String::class.java
        )
    }

}
