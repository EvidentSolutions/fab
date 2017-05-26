package fi.evident.fab.proq2

import java.io.IOException

enum class BandStatus constructor(private val value: Float) {

    Bypass(0f),
    Enabled(1f),
    Disabled(2f);

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        writer.writeFloat(value)
    }

    companion object {

        internal fun of(enabled: Boolean): BandStatus {
            return if (enabled) BandStatus.Enabled else BandStatus.Disabled
        }
    }
}
