package fi.evident.fab.proq2

import fi.evident.fab.Q
import java.io.IOException
import java.lang.Math.*

/**
 * @param q range 0.025 -> 40.00
 */
class BandQ(private val q: Q) {

    init {
        if (q < 0.025 || q > 40)
            throw IllegalArgumentException("Q value not in limits 0.025 .. 40, was: $q")
    }

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        // =LOG(F1)*0,312098175+0,5 (default = 1)
        writer.writeFloat((log10(q) * 0.312098175 + 0.5).toFloat())
    }

    companion object {

        fun limited(q: Q): BandQ {
            return BandQ(max(0.025, min(40.0, q)))
        }
    }
}
