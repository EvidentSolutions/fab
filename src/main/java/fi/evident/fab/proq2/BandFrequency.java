package fi.evident.fab.proq2;

import java.io.IOException;

public class BandFrequency {

    private final double frequency;

    /**
     * @param frequency range 10.0 -> 30000.0 Hz
     */
    BandFrequency(double frequency) {

        if (frequency < 10 || frequency > 30000)
            throw new IllegalArgumentException("Frequency not in limits 10 .. 30000, was: " + frequency);

        this.frequency = frequency;
    }

    public void write(LittleEndianBinaryStreamWriter writer) throws IOException {
        // =LOG(A1)/LOG(2)
        writer.writeFloat((float) (Math.log10(frequency) / Math.log10(2)));
    }
}
