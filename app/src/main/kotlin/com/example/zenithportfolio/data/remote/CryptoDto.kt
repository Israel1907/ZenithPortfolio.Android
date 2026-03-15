package com.example.zenithportfolio.data.remote

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

/**
 * Handles CoinGecko's `image` field which is a plain URL string from /coins/markets
 * but an object {"thumb":"…","small":"…","large":"…"} from /coins/{id}.
 */
internal object ImageUrlSerializer : KSerializer<String> {
    override val descriptor = PrimitiveSerialDescriptor("ImageUrl", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): String {
        val jsonDecoder = decoder as JsonDecoder
        return when (val element = jsonDecoder.decodeJsonElement()) {
            is JsonPrimitive -> element.content
            is JsonObject -> element["large"]?.jsonPrimitive?.content
                ?: element["small"]?.jsonPrimitive?.content
                ?: element["thumb"]?.jsonPrimitive?.content
                ?: ""
            else -> ""
        }
    }

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }
}

@Serializable
data class CryptoDto(
    val id: String,
    val name: String,
    val symbol: String,
    @SerialName("current_price") val currentPrice: Double = 0.0,
    @SerialName("price_change_percentage_24h") val priceChangePercentage24h: Double? = null,
    @Serializable(with = ImageUrlSerializer::class)
    @SerialName("image") val imageUrl: String = "",
    @SerialName("market_cap") val marketCap: Long = 0L,
    @SerialName("market_cap_rank") val marketCapRank: Int? = null
)

/**
 * DTO for /coins/{id} endpoint which nests price data under market_data.
 */
@Serializable
data class CoinDetailDto(
    val id: String,
    val name: String,
    val symbol: String,
    @Serializable(with = ImageUrlSerializer::class)
    @SerialName("image") val imageUrl: String = "",
    @SerialName("market_cap_rank") val marketCapRank: Int? = null,
    @SerialName("market_data") val marketData: MarketDataDto? = null
)

@Serializable
data class MarketDataDto(
    @SerialName("current_price") val currentPrice: Map<String, Double> = emptyMap(),
    @SerialName("market_cap") val marketCap: Map<String, Double> = emptyMap(),
    @SerialName("price_change_percentage_24h") val priceChangePercentage24h: Double? = null
)
