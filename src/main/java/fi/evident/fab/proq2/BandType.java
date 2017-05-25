package fi.evident.fab.proq2;

import fi.evident.fab.rew.Filter;

import java.io.IOException;

enum BandType {

    Bell(0),
    LowShelf(1),
    LowCut(2),
    HighShelf(3),
    HighCut(4),
    Notch(5),
    BandPass(6),
    TiltShelf(7);

    private final float value;

    BandType(float value) {
        this.value = value;
    }

    static BandType of(Filter.Type type) {
        switch (type) {
            case PK:
                return BandType.Bell;
            case LP:
                return BandType.HighCut;
            case HP:
                return BandType.LowCut;
            case LS:
                return BandType.LowShelf;
            case HS:
                return BandType.HighShelf;
            default:
                throw new IllegalArgumentException("Unsupported filter type: " + type);
        }
    }

    public void write(LittleEndianBinaryStreamWriter writer) throws IOException {
        writer.writeFloat(value);
    }
}
