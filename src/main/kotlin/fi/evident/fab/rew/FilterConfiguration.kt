package fi.evident.fab.rew

import fi.evident.fab.Hz
import fi.evident.fab.dB

class FilterConfiguration(val slope: FilterSlope, val placement: FilterPlacement, val filters: List<Filter>) {

    override fun toString(): String {

        val result = StringBuilder().apply {
            append("Filter configuration (").append(filters.size).append(")\n")
            append("====================\n")
            append("Placement: ").append(placement).append("\n")
            append("Slope: ").append(slope).append("\n")
        }

        for (filter in filters) {
            result.append("Filter: ").append(filter).append("\n")
        }

        return result.toString()
    }
}

enum class FilterSlope {
    dB_oct12,
    dB_oct24
}

enum class FilterPlacement {
    Left,
    Right,
    Stereo
}

class Filter internal constructor(val isEnabled: Boolean,
                                  val type: Type,
                                  val frequency: Hz,
                                  val gain: dB,
                                  val q: Double) {

    enum class Type {
        PK, // PK for a peaking (parametric) filter
        LP, // LP for a 12dB/octave Low Pass filter (Q=0.7071)
        HP, // HP for a 12dB/octave High Pass filter (Q=0.7071)
        LS, // LS for a Low Shelf filter
        HS, // HS for a High Shelf filter
        NO, // NO for a notch filter
        MO, // Modal for a Modal filter

        // The Generic and DCX2496 also have shelving filters implemented per the DCX2496
        LS6dB, // LS 6dB for a 6dB/octave Low Shelf filter
        HS6dB, // HS 6dB for a 6dB/octave High Shelf filter
        LS12dB, // LS 12dB for a 12dB/octave Low Shelf filter
        HS12dB, // HS 12dB for a 12dB/octave High Shelf filter

        // The Generic equaliser setting also has
        LPQ, // LPQ, a 12dB/octave Low Pass filter with adjustable Q
        HPQ  // HPQ, a 12dB/octave High Pass filter with adjustable Q
    }

    override fun toString() = "$type $frequency Hz $gain dB Q: $q"
}