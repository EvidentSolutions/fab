package fi.evident.fab.proq2

import java.io.IOException

class GlobalPresetParameters {

    internal class ProcessMode private constructor(
            private val mode: Float,
            // affects only linear phase mode
            private val phase: ProcessMode.Phase) {

        internal enum class Phase constructor(private val value: Float) {

            Low(0f),
            Medium(1f),
            High(2f),
            VeryHigh(3f),
            Max(4f);

            @Throws(IOException::class)
            fun write(writer: LittleEndianBinaryStreamWriter) {
                writer.writeFloat(value)
            }
        }

        @Throws(IOException::class)
        fun write(writer: LittleEndianBinaryStreamWriter) {
            writer.writeFloat(mode)
            phase.write(writer)
        }

        companion object {

            @Suppress("unused")
            fun zeroLatency(): ProcessMode {
                return ProcessMode(0f, Phase.Medium)
            }

            @Suppress("unused")
            fun naturalPhase(): ProcessMode {
                return ProcessMode(1f, Phase.Medium)
            }

            fun linearPhase(phase: Phase): ProcessMode {
                return ProcessMode(2f, phase)
            }
        }
    }

    internal enum class ChannelMode constructor(private val value: Float) {

        LeftRight(0f),
        MidSide(1f);

        @Throws(IOException::class)
        fun write(writer: LittleEndianBinaryStreamWriter) {
            writer.writeFloat(value)
        }
    }

    /**
     * @param db - Infinity to +36 dB , 0 = 0 dB
     * @param scale 0 to 2, 1 = 100%, 2 = 200%
     */
    //TODO Float -> Double -> dB
    internal class Gain(private val db: Float, private val scale: Float) {

        init {

            if (db < -1 || db > 1) {
                throw IllegalArgumentException("Gain db not in limits -1 .. 1, was: " + db)
            }

            if (scale < 0 || scale > 2) {
                throw IllegalArgumentException("Gain scale not in limits 0 .. 2, was: " + scale)
            }
        }

        @Throws(IOException::class)
        fun write(writer: LittleEndianBinaryStreamWriter) {
            writer.writeFloat(scale)
            writer.writeFloat(db)
        }
    }

    /**
     * @param value -1 .. 1 where 0 is middle
     */
    internal class OutputPan(private val value: Float) {

        init {
            if (value < -1 || value > 1) {
                throw IllegalArgumentException("Pan not in limits -1 .. 1, was: " + value)
            }
        }

        @Throws(IOException::class)
        fun write(writer: LittleEndianBinaryStreamWriter) {
            writer.writeFloat(value)
        }
    }

    internal class Analyzer {

        internal enum class Range constructor(private val value: Float) {

            dB60(0f),
            dB90(1f),
            db120(2f);

            @Throws(IOException::class)
            fun write(writer: LittleEndianBinaryStreamWriter) {
                writer.writeFloat(value)
            }
        }

        internal enum class Resolution constructor(private val value: Float) {

            Low(0f),
            Medium(1f),
            High(2f),
            Maximum(3f);

            @Throws(IOException::class)
            fun write(writer: LittleEndianBinaryStreamWriter) {
                writer.writeFloat(value)
            }
        }

        internal enum class Speed constructor(private val value: Float) {

            VerySlow(0f),
            Slow(1f),
            Medium(2f),
            Fast(3f),
            VeryFast(4f);

            @Throws(IOException::class)
            fun write(writer: LittleEndianBinaryStreamWriter) {
                writer.writeFloat(value)
            }
        }

        internal enum class Tilt constructor(private val value: Float) {

            dB_oct0(0f),
            dB_oct1point5(1f),
            dB_oct3(2f),
            dB_oct4point5(3f),
            dB_oct6(4f);

            @Throws(IOException::class)
            fun write(writer: LittleEndianBinaryStreamWriter) {
                writer.writeFloat(value)
            }
        }

        private val pre = true
        private val post = true
        private val sideChain = true
        private val range = Range.dB90
        private val resolution = Resolution.Medium
        private val speed = Speed.Medium
        private val tilt = Tilt.dB_oct4point5
        private val freeze = false
        private val spectrumGrab = true

        @Throws(IOException::class)
        fun write(writer: LittleEndianBinaryStreamWriter) {
            writer.writeFloat((if (pre) 1 else 0).toFloat())
            writer.writeFloat((if (post) 1 else 0).toFloat())
            writer.writeFloat((if (sideChain) 1 else 0).toFloat())
            range.write(writer)
            resolution.write(writer)
            speed.write(writer)
            tilt.write(writer)
            writer.writeFloat((if (freeze) 1 else 0).toFloat())
            writer.writeFloat((if (spectrumGrab) 1 else 0).toFloat())
        }
    }

    private val processMode = ProcessMode.linearPhase(ProcessMode.Phase.Medium)
    private val channelMode = ChannelMode.LeftRight
    private val gain = Gain(0f, 1f)
    private val pan = OutputPan(0f)
    private val bypass = false
    private val phaseInvert = false
    private val autoGain = false
    private val analyzer = Analyzer()
    private val unknown1 = 2f
    private val midiLearn = false
    private val unknown2 = -1f // solo band ??
    private val unknown3 = 0f

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        processMode.write(writer)
        channelMode.write(writer)
        gain.write(writer)
        pan.write(writer)
        writer.writeFloat((if (bypass) 1 else 0).toFloat())
        writer.writeFloat((if (phaseInvert) 1 else 0).toFloat())
        writer.writeFloat((if (autoGain) 2 /* enabled value actually seems to be 2 */ else 0).toFloat())
        analyzer.write(writer)
        writer.writeFloat(unknown1)
        writer.writeFloat((if (midiLearn) 1 else 0).toFloat())
        writer.writeFloat(unknown2)
        writer.writeFloat(unknown3)
    }
}
