package fi.evident.fab.proq2

import fi.evident.fab.dB
import java.io.IOException

/**
 * @param gain range +-30 dB
 */
class BandGain(private val gain: dB) {

    init {
        if (gain < -30 || gain > 30)
            throw IllegalArgumentException("Gain db not in limits -30 .. 30, was: $gain")
    }

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        writer.writeFloat(gain.toFloat())
    }
}
