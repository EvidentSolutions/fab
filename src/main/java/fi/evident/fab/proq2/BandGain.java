package fi.evident.fab.proq2;

import java.io.IOException;

public final class BandGain {

    private final double gain;

    /**
     * @param gain range +-30dB
     */
    BandGain(double gain) {

        if (gain < -30 || gain > 30)
            throw new IllegalArgumentException("Gain db not in limits -30 .. 30, was: " + gain);

        this.gain = gain;
    }

    public void write(LittleEndianBinaryStreamWriter writer) throws IOException {
        writer.writeFloat((float) gain);
    }
}
