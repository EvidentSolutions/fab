package fi.evident.fab.proq2

import java.io.IOException

internal class BandQ

/**
 * @param q range 0.025 -> 40.00
 */
(private val q: Double) {

    init {

        if (q < 0.025 || q > 40)
            throw IllegalArgumentException("Q value not in limits 0.025 .. 40, was: " + q)
    }

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        // =LOG(F1)*0,312098175+0,5 (default = 1)
        writer.writeFloat((Math.log10(q) * 0.312098175 + 0.5).toFloat())
    }

    companion object {

        fun limited(q: Double): BandQ {
            return BandQ(Math.max(0.025, Math.min(40.0, q)))
        }
    }
}
