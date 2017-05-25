package fi.evident.fab.proq2;

import java.io.IOException;

public final class BandQ {

    private final double q;

    /**
     * @param q range 0.025 -> 40.00
     */
    BandQ(double q) {

        if (q < 0.025 || q > 40)
            throw new IllegalArgumentException("Q value not in limits 0.025 .. 40, was: " + q);

        this.q = q;
    }

    static BandQ limited(double q) {
        return new BandQ(Math.max(0.025, Math.min(40, q)));
    }

    public void write(LittleEndianBinaryStreamWriter writer) throws IOException {
        // =LOG(F1)*0,312098175+0,5 (default = 1)
        writer.writeFloat((float) (Math.log10(q) * 0.312098175 + 0.5));
    }
}
