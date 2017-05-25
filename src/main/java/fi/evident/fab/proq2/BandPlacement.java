package fi.evident.fab.proq2;

import fi.evident.fab.rew.FilterPlacement;

import java.io.IOException;

public enum BandPlacement {

    Left(0),
    Right(1),
    Stereo(2);

    private final float value;

    BandPlacement(float value) {
        this.value = value;
    }

    public static BandPlacement of(FilterPlacement placement) {
        switch (placement) {
            case Left:
                return Left;
            case Right:
                return Right;
            case Stereo:
                return Stereo;
            default:
                throw new IllegalArgumentException("Unsupported placement: " + placement);
        }
    }

    public void write(LittleEndianBinaryStreamWriter writer) throws IOException {
        writer.writeFloat(value);
    }
}
