package fi.evident.fab.proq2

import java.io.IOException
import java.io.OutputStream

class LittleEndianBinaryStreamWriter internal constructor(private val os: OutputStream) {

    @Throws(IOException::class)
    internal fun writeBytes(bytes: ByteArray) {
        os.write(bytes)
    }

    @Throws(IOException::class)
    internal fun writeInt(i: Int) {
        writeBytes(byteArrayOf((i and 0xFF).toByte(), (i shr 8 and 0xFF).toByte(), (i shr 16 and 0xFF).toByte(), (i shr 24 and 0xFF).toByte()))
    }

    @Throws(IOException::class)
    internal fun writeFloat(value: Float) {
        writeInt(java.lang.Float.floatToIntBits(value))
    }
}
