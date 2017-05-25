package fi.evident.fab.proq2

import fi.evident.fab.rew.Filter

import java.io.IOException

internal enum class BandType constructor(private val value: Float) {

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

        fun of(type: Filter.Type): BandType {
            when (type) {
                Filter.Type.PK -> return BandType.Bell
                Filter.Type.LP -> return BandType.HighCut
                Filter.Type.HP -> return BandType.LowCut
                Filter.Type.LS -> return BandType.LowShelf
                Filter.Type.HS -> return BandType.HighShelf
                else -> throw IllegalArgumentException("Unsupported filter type: " + type)
            }
        }
    }
}
