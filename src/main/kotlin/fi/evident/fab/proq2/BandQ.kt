package fi.evident.fab.proq2

import fi.evident.fab.Q
import fi.evident.fab.assertContains
import java.io.IOException
import java.lang.Math.*

class BandQ(private val q: Q) {

    init {
        (minValue..maxValue).assertContains(q, "Q")
    }

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        // =LOG(F1)*0,312098175+0,5 (default = 1)
        writer.writeFloat((log10(q) * 0.312098175 + 0.5).toFloat())
    }

    companion object {
        val minValue = 0.025
        val maxValue = 40.0

        fun limited(q: Q): BandQ {
            return BandQ(max(BandQ.minValue, min(maxValue, q)))
        }
    }
}
