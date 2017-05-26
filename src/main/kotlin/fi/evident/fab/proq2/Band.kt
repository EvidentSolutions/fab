package fi.evident.fab.proq2

import fi.evident.fab.proq2.BandPlacement.Stereo
import fi.evident.fab.proq2.BandSlope.dB_oct24
import fi.evident.fab.proq2.BandStatus.Disabled
import fi.evident.fab.proq2.BandType.Bell
import fi.evident.fab.rew.Filter
import fi.evident.fab.rew.FilterConfigurations
import java.io.IOException

class Band private constructor(private val type: BandType,
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

        private fun fromRewFilter(filter: Filter, slope: BandSlope, placement: BandPlacement): Band {
            return Band(BandType.of(filter.type), slope, placement,
                    BandStatus.of(filter.isEnabled),
                    BandFrequency(filter.frequency),
                    BandGain(filter.gain),
                    BandQ.limited(filter.q))
        }

        fun fromRewFilters(filters: FilterConfigurations): List<Band> {
            return filters.configurations.flatMap { conf ->
                val slope = BandSlope.of(conf.slope)
                val placement = BandPlacement.of(conf.placement)
                conf.filters.map { Band.fromRewFilter(it, slope, placement) }
            }
        }

        val unusedBand = Band(Bell, dB_oct24, Stereo, Disabled, BandFrequency(1000.0), BandGain(0.0), BandQ(1.0))
    }
}
