package fi.evident.fab.proq2;

import java.io.IOException;
import java.io.OutputStream;

final class LittleEndianBinaryStreamWriter {

    private final OutputStream os;

    LittleEndianBinaryStreamWriter(OutputStream os) {
        this.os = os;
    }

    void writeBytes(byte[] bytes) throws IOException {
        os.write(bytes);
    }

    void writeInt(int i) throws IOException {
        writeBytes(new byte[] {
                (byte) (i & 0xFF),
                (byte) ((i >> 8) & 0xFF),
                (byte) ((i >> 16) & 0xFF),
                (byte) ((i >> 24) & 0xFF)
        });
    }

    void writeFloat(float value) throws IOException {
        writeInt(Float.floatToIntBits(value));
    }
}
