package fi.evident.fab.proq2

import fi.evident.fab.rew.FilterSlope

import java.io.IOException

enum class BandSlope constructor(private val value: Float) {

    db_oct12(1f),
    dB_oct24(3f),
    dB_oct36(5f),
    dB_oct48(6f),
    dB_oct72(7f),
    dB_oct96(8f);

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        writer.writeFloat(value)
    }

    companion object {

        fun of(slope: FilterSlope): BandSlope {
            when (slope) {
                FilterSlope.dB_oct12 -> return db_oct12
                FilterSlope.dB_oct24 -> return dB_oct24
                else -> throw IllegalArgumentException("Unsupported filter slope: " + slope)
            }
        }
    }
}
