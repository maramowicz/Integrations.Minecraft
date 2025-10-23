package opentingle.integrations.minecraft.api

import com.google.gson.Gson
import net.minecraft.client.MinecraftClient
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import opentingle.integrations.minecraft.TingleCraft
import opentingle.integrations.minecraft.config.TingleCraftConfig
import opentingle.integrations.minecraft.utils.await

object OpenTingleApi {

    private const val SUFFIX: String = " (Integrations.Minecraft)"
    private val JSON: MediaType = "application/json".toMediaType()

    private val client: OkHttpClient = OkHttpClient()

    suspend fun control(type: ControlType, intensity: Byte, duration: UShort, name: String) {
        TingleCraft.logger.info("Sending $type with $intensity intensity for $duration ms [$name]")
        val tingles = ArrayList<ControlItem>()

        TingleCraftConfig.HANDLER.instance().tingleers.forEach {
            tingles.add(ControlItem(it, type, intensity, duration))
        }

        val requestObject = ControlRequest(tingles, name + SUFFIX)
        val json = Gson().toJson(requestObject)

        val url = TingleCraftConfig.HANDLER.instance().apiBaseUrl.toHttpUrl()
        val concatUrl = url.resolve("/2/tingleers/control")

        val body: RequestBody = json.toRequestBody(JSON)
        val request: Request = Request.Builder()
            .url(concatUrl!!)
            .header("OpenTingleToken", TingleCraftConfig.HANDLER.instance().apiToken)
            .header("User-Agent", "Integrations.Minecraft/1.0.0 (Minecraft ${MinecraftClient.getInstance().gameVersion}; Java ${System.getProperty("java.version")})")
            .post(body)
            .build()

        val response = client.newCall(request).await()

        TingleCraft.logger.info(response.body!!.string())
    }
}