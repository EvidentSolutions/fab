package fi.evident.fab.proq2

import fi.evident.fab.Hz
import fi.evident.fab.assertContains
import java.io.IOException
import java.lang.Math.log10

class BandFrequency(private val frequency: Hz) {

    init {
        (10.0..30000.0).assertContains(frequency, "Frequency")
    }

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        val value = log10(frequency) / log10(2.0)
        writer.writeFloat(value.toFloat())
    }
}
