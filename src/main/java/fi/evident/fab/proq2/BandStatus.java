package fi.evident.fab.proq2;

import java.io.IOException;

public enum BandStatus {

    Bypass(0),
    Enabled(1),
    Disabled(2);

    private final float value;

    BandStatus(float value) {
        this.value = value;
    }

    static BandStatus of(boolean enabled) {
        return enabled ? BandStatus.Enabled : BandStatus.Disabled;
    }

    public void write(LittleEndianBinaryStreamWriter writer) throws IOException {
        writer.writeFloat(value);
    }
}
