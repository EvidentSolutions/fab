package fi.evident.fab.proq2

import fi.evident.fab.dB
import java.io.IOException

internal class BandGain

/**
 * @param gain range +-30dB
 */
(private val gain: dB) {

    init {

        if (gain < -30 || gain > 30)
            throw IllegalArgumentException("Gain db not in limits -30 .. 30, was: " + gain)
    }

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        writer.writeFloat(gain.toFloat())
    }
}
