package fi.evident.fab.proq2

import fi.evident.fab.rew.Filter

import java.io.IOException

internal class Band private constructor(private val type: BandType,
                                        private val slope: BandSlope,
                                        private val placement: BandPlacement,
                                        private val status: BandStatus,
                                        private val frequency: BandFrequency,
                                        private val gain: BandGain,
                                        private val q: BandQ) {

    @Throws(IOException::class)
    fun write(writer: LittleEndianBinaryStreamWriter) {
        status.write(writer)
        frequency.write(writer)
        gain.write(writer)
        q.write(writer)
        type.write(writer)
        slope.write(writer)
        placement.write(writer)
    }

    override fun toString() = "$type: $frequency Hz $gain dB Q: $q"

    companion object {

        fun fromRewFilter(filter: Filter, slope: BandSlope, placement: BandPlacement): Band {
            return Band(
                    BandType.of(filter.type),
                    slope,
                    placement,
                    BandStatus.of(filter.isEnabled),
                    BandFrequency(filter.frequency),
                    BandGain(filter.gain),
                    BandQ.limited(filter.q)
            )
        }

        val unusedBand = Band(
                BandType.Bell,
                BandSlope.dB_oct24,
                BandPlacement.Stereo,
                BandStatus.Disabled,
                BandFrequency(1000f),
                BandGain(0f),
                BandQ(1.0)
        )
    }
}
