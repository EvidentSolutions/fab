package fi.evident.fab.proq2;

import java.io.IOException;

public final class GlobalPresetParameters {

    static class ProcessMode {

        enum Phase {

            Low(0),
            Medium(1),
            High(2),
            VeryHigh(3),
            Max(4);

            private final float value;

            Phase(float value) {
                this.value = value;
            }

            void write(LittleEndianBinaryStreamWriter writer) throws IOException {
                writer.writeFloat(value);
            }
        }

        private final float mode;
        private final Phase phase; // affects only linear phase mode

        private ProcessMode(float mode, Phase phase) {
            this.mode = mode;
            this.phase = phase;
        }

        static ProcessMode zeroLatency() {
            return new ProcessMode(0, Phase.Medium);
        }

        static ProcessMode naturalPhase() {
            return new ProcessMode(1, Phase.Medium);
        }

        static ProcessMode linearPhase(Phase phase) {
            return new ProcessMode(2, phase);
        }

        void write(LittleEndianBinaryStreamWriter writer) throws IOException {
            writer.writeFloat(mode);
            phase.write(writer);
        }
    }

    enum ChannelMode {

        LeftRight(0),
        MidSide(1);

        private final float value;

        ChannelMode(float value) {
            this.value = value;
        }

        void write(LittleEndianBinaryStreamWriter writer) throws IOException {
            writer.writeFloat(value);
        }
    }

    static class Gain {

        private final float db;
        private final float scale;

        /**
         * @param db - Infinity to +36 dB , 0 = 0 dB
         * @param scale 0 to 2, 1 = 100%, 2 = 200%
         */
        Gain(float db, float scale) {

            if (db < -1 || db > 1) {
                throw new IllegalArgumentException("Gain db not in limits -1 .. 1, was: " + db);
            }

            if (scale < 0 || scale > 2) {
                throw new IllegalArgumentException("Gain scale not in limits 0 .. 2, was: " + scale);
            }

            this.db = db;
            this.scale = scale;
        }

        void write(LittleEndianBinaryStreamWriter writer) throws IOException {
            writer.writeFloat(scale);
            writer.writeFloat(db);
        }
    }

    static class OutputPan {

        private final float value;

        /**
         * @param value -1 .. 1 where 0 is middle
         */
        OutputPan(float value) {
            this.value = value;

            if (value < -1 || value > 1) {
                throw new IllegalArgumentException("Pan not in limits -1 .. 1, was: " + value);
            }
        }

        void write(LittleEndianBinaryStreamWriter writer) throws IOException {
            writer.writeFloat(value);
        }
    }

    static class Analyzer {

        enum Range {

            dB60(0),
            dB90(1),
            db120(2);

            private final float value;

            Range(float value) {
                this.value = value;
            }

            void write(LittleEndianBinaryStreamWriter writer) throws IOException {
                writer.writeFloat(value);
            }
        }

        enum Resolution {

            Low(0),
            Medium(1),
            High(2),
            Maximum(3);

            private final float value;

            Resolution(float value) {
                this.value = value;
            }

            void write(LittleEndianBinaryStreamWriter writer) throws IOException {
                writer.writeFloat(value);
            }
        }

        enum Speed {

            VerySlow(0),
            Slow(1),
            Medium(2),
            Fast(3),
            VeryFast(4);

            private final float value;

            Speed(float value) {
                this.value = value;
            }

            void write(LittleEndianBinaryStreamWriter writer) throws IOException {
                writer.writeFloat(value);
            }
        }

        enum Tilt {

            dB_oct0(0),
            dB_oct1point5(1),
            dB_oct3(2),
            dB_oct4point5(3),
            dB_oct6(4);

            private final float value;

            Tilt(float value) {
                this.value = value;
            }

            void write(LittleEndianBinaryStreamWriter writer) throws IOException {
                writer.writeFloat(value);
            }
        }

        private boolean pre = true;
        private boolean post = true;
        private boolean sideChain = true;
        private Range range = Range.dB90;
        private Resolution resolution = Resolution.Medium;
        private Speed speed = Speed.Medium;
        private Tilt tilt = Tilt.dB_oct4point5;
        private boolean freeze = false;
        private boolean spectrumGrab = true;

        void write(LittleEndianBinaryStreamWriter writer) throws IOException {
            writer.writeFloat(pre ? 1 : 0);
            writer.writeFloat(post ? 1 : 0);
            writer.writeFloat(sideChain ? 1 : 0);
            range.write(writer);
            resolution.write(writer);
            speed.write(writer);
            tilt.write(writer);
            writer.writeFloat(freeze ? 1 : 0);
            writer.writeFloat(spectrumGrab ? 1 : 0);
        }
    }

    ProcessMode processMode = ProcessMode.linearPhase(ProcessMode.Phase.Medium);
    ChannelMode channelMode = ChannelMode.LeftRight;
    Gain gain = new Gain(0, 1);
    OutputPan pan = new OutputPan(0);
    boolean bypass = false;
    boolean phaseInvert = false;
    boolean autoGain = false;
    Analyzer analyzer = new Analyzer();
    float unknown1 = 2;
    boolean midiLearn = false;
    float unknown2 = -1; // solo band ??
    float unknown3 = 0;

    public void write(LittleEndianBinaryStreamWriter writer) throws IOException {
        processMode.write(writer);
        channelMode.write(writer);
        gain.write(writer);
        pan.write(writer);
        writer.writeFloat(bypass ? 1 : 0);
        writer.writeFloat(phaseInvert ? 1 : 0);
        writer.writeFloat(autoGain ? 2 /* enabled value actually seems to be 2 */ : 0);
        analyzer.write(writer);
        writer.writeFloat(unknown1);
        writer.writeFloat(midiLearn ? 1 : 0);
        writer.writeFloat(unknown2);
        writer.writeFloat(unknown3);
    }
}
