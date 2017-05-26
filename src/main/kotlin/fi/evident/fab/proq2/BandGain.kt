package fi.evident.fab.proq2

import fi.evident.fab.assertContains
import fi.evident.fab.dB
import java.io.IOException

class BandGain(private val gain: dB) {

    init {
        (-30.0..30.0).assertContains(gain, "Gain")
    }

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        writer.writeFloat(gain.toFloat())
    }
}
