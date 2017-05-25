package fi.evident.fab.proq2;

import fi.evident.fab.rew.FilterSlope;

import java.io.IOException;

public enum BandSlope {

    db_oct12(1),
    dB_oct24(3),
    dB_oct36(5),
    dB_oct48(6),
    dB_oct72(7),
    dB_oct96(8);

    private final float value;

    BandSlope(float value) {
        this.value = value;
    }

    public static BandSlope of(FilterSlope slope) {
        switch (slope) {
            case dB_oct12:
                return db_oct12;
            case dB_oct24:
                return dB_oct24;
            default:
                throw new IllegalArgumentException("Unsupported filter slope: " + slope);
        }
    }

    public void write(LittleEndianBinaryStreamWriter writer) throws IOException {
        writer.writeFloat(value);
    }
}
