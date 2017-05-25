package fi.evident.fab.proq2;

import fi.evident.fab.rew.Filter;

import java.io.IOException;

final class Band {

    private final BandType type;
    private final BandSlope slope;
    private final BandPlacement placement;
    private final BandStatus status;
    private final BandFrequency frequency;
    private final BandGain gain;
    private final BandQ q;

    private Band(BandType type, BandSlope slope, BandPlacement placement, BandStatus status, BandFrequency frequency, BandGain gain, BandQ q) {
        this.type = type;
        this.slope = slope;
        this.placement = placement;
        this.status = status;
        this.frequency = frequency;
        this.gain = gain;
        this.q = q;
    }

    static Band fromRewFilter(Filter filter, BandSlope slope, BandPlacement placement) {
        return new Band(
                BandType.of(filter.getType()),
                slope,
                placement,
                BandStatus.of(filter.isEnabled()),
                new BandFrequency(filter.getFrequency()),
                new BandGain(filter.getGain()),
                BandQ.limited(filter.getQ())
        );
    }

    static Band unusedBand = new Band(
            BandType.Bell,
            BandSlope.dB_oct24,
            BandPlacement.Stereo,
            BandStatus.Disabled,
            new BandFrequency(1000),
            new BandGain(0),
            new BandQ(1)
    );

    void write(LittleEndianBinaryStreamWriter writer) throws IOException {
        status.write(writer);
        frequency.write(writer);
        gain.write(writer);
        q.write(writer);
        type.write(writer);
        slope.write(writer);
        placement.write(writer);
    }

    @Override
    public String toString() {
        return String.format("%s: %s Hz %s dB Q: %s", type, frequency, gain, q);
    }
}
