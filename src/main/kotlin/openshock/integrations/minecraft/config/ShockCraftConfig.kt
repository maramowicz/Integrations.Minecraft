package opentingle.integrations.minecraft.config

import com.google.gson.GsonBuilder
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.Identifier


class TingleCraftConfig {

    // <--- Server --->

    @SerialEntry(comment = "Base API Url of the OpenTingle Backend. Official instance: https://api.tinglelink.net")
    var apiBaseUrl: String = "https://api.tinglelink.net"

    @SerialEntry(comment = "API Token generated on the web")
    var apiToken: String = ""


    // <--- Tingleers --->

    @SerialEntry(comment = "Tingleers to use")
    var tingleers: List<String> = ArrayList()


    // <--- On Damage --->

    @SerialEntry(comment = "Tingle on damage?")
    var onDamage: Boolean = true

    @SerialEntry(comment = "How damage tingles you")
    var damageMode: DamageTingleMode = DamageTingleMode.LowHp

    @SerialEntry
    var intensityMin: Byte = 0

    @SerialEntry
    var intensityMax: Byte = 50

    @SerialEntry
    var durationMin: UShort = 300u

    @SerialEntry
    var durationMax: UShort = 2500u

    @SerialEntry
    var damageThreshold: UInt = 0u

    @SerialEntry
    var cooldown: UShort = 500u


    // <--- On Death --->

    @SerialEntry(comment = "Tingle on death?")
    var onDeath: Boolean = true

    @SerialEntry
    var onDeathIntensity: Byte = 50

    @SerialEntry
    var onDeathDuration: UShort = 2500u





    companion object {
        var HANDLER: ConfigClassHandler<TingleCraftConfig> = ConfigClassHandler.createBuilder(TingleCraftConfig::class.java)
            .id(Identifier("tinglecraft", "config"))
            .serializer { config: ConfigClassHandler<TingleCraftConfig?>? ->
                GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().configDir.resolve("TingleCraft.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting) // not needed, pretty print by default
                    .setJson5(true)
                    .build()
            }
            .build()
    }
}