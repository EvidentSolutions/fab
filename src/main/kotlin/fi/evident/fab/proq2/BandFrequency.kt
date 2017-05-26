package fi.evident.fab.proq2

import fi.evident.fab.Hz
import java.io.IOException
import java.lang.Math.log10

/**
 * @param frequency range 10.0 -> 30000.0 Hz
 */
internal class BandFrequency(private val frequency: Hz) {

    init {
        if (frequency < 10 || frequency > 30000)
            throw IllegalArgumentException("Frequency not in limits 10 .. 30000, was: " + frequency)
    }

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        // =LOG(A1)/LOG(2)
        writer.writeFloat((log10(frequency) / log10(2.0)).toFloat())
    }
}
