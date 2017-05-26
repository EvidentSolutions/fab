package fi.evident.fab.proq2

import fi.evident.fab.rew.Filter

import java.io.IOException

enum class BandType constructor(private val value: Float) {

    Bell(0f),
    LowShelf(1f),
    LowCut(2f),
    HighShelf(3f),
    HighCut(4f),
    Notch(5f),
    BandPass(6f),
    TiltShelf(7f);

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        writer.writeFloat(value)
    }

    companion object {

        fun of(type: Filter.Type) =
            when (type) {
                Filter.Type.PK -> BandType.Bell
                Filter.Type.LP -> BandType.HighCut
                Filter.Type.HP -> BandType.LowCut
                Filter.Type.LS -> BandType.LowShelf
                Filter.Type.HS -> BandType.HighShelf
                else -> throw IllegalArgumentException("Unsupported filter type: $type")
            }
    }
}
