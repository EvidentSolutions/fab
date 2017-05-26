package fi.evident.fab.proq2

import java.io.IOException
import java.io.OutputStream

class LittleEndianBinaryStreamWriter(private val os: OutputStream) {

    @Throws(IOException::class)
    fun writeBytes(bytes: ByteArray) {
        os.write(bytes)
    }

    @Throws(IOException::class)
    fun writeInt(i: Int) {
        writeBytes(byteArrayOf(
                (i and 0xFF).toByte(),
                (i shr 8 and 0xFF).toByte(),
                (i shr 16 and 0xFF).toByte(),
                (i shr 24 and 0xFF).toByte()))
    }

    @Throws(IOException::class)
    fun writeFloat(value: Float) {
        writeInt(java.lang.Float.floatToIntBits(value))
    }
}
