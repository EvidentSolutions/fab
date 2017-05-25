package fi.evident.fab.rew;

public class Filter {

    public enum Type {
        PK, // PK for a peaking (parametric) filter
        LP, // LP for a 12dB/octave Low Pass filter (Q=0.7071)
        HP, // HP for a 12dB/octave High Pass filter (Q=0.7071)
        LS, // LS for a Low Shelf filter
        HS, // HS for a High Shelf filter
        NO, // NO for a notch filter
        MO, // Modal for a Modal filter

        // The Generic and DCX2496 also have shelving filters implemented per the DCX2496
        LS6dB,  // LS 6dB for a 6dB/octave Low Shelf filter
        HS6dB,  // HS 6dB for a 6dB/octave High Shelf filter
        LS12dB, // LS 12dB for a 12dB/octave Low Shelf filter
        HS12dB, // HS 12dB for a 12dB/octave High Shelf filter

        // The Generic equaliser setting also has
        LPQ, // LPQ, a 12dB/octave Low Pass filter with adjustable Q
        HPQ  // HPQ, a 12dB/octave High Pass filter with adjustable Q
    }

    private final boolean enabled;
    private final Type type;
    private final double frequency;	// Hz
    private final double gain;		// dB
    private final double q;

    Filter(boolean enabled, Type type, double frequency, double gain, double q) {
        this.enabled = enabled;
        this.type = type;
        this.frequency = frequency;
        this.gain = gain;
        this.q = q;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Type getType() {
        return type;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getGain() {
        return gain;
    }

    public double getQ() {
        return q;
    }

    @Override
    public String toString() {
        return String.format("%s %s Hz %s dB Q: %s", type, frequency, gain, q);
    }
}
