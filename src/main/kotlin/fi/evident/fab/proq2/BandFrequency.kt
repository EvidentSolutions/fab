package fi.evident.fab.proq2

import java.io.IOException

internal class BandFrequency

/**
 * @param frequency range 10.0 -> 30000.0 Hz
 */
(private val frequency: Double) {

    init {

        if (frequency < 10 || frequency > 30000)
            throw IllegalArgumentException("Frequency not in limits 10 .. 30000, was: " + frequency)
    }

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        // =LOG(A1)/LOG(2)
        writer.writeFloat((Math.log10(frequency) / Math.log10(2.0)).toFloat())
    }
}
