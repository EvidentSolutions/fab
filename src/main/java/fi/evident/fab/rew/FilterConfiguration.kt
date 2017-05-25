package fi.evident.fab.rew

import java.util.Collections

class FilterConfiguration(val slope: FilterSlope, val placement: FilterPlacement, private val filters_: List<Filter>) {

    //TODO: remove unnecessary indirection?
    fun getFilters(): List<Filter> {
        return Collections.unmodifiableList(filters_)
    }

    override fun toString(): String {

        val result = StringBuilder()

        result.append("Filter configuration (").append(filters_.size).append(")\n")
        result.append("====================\n")
        result.append("Placement: ").append(placement).append("\n")
        result.append("Slope: ").append(slope).append("\n")

        for (filter in filters_) {
            result.append("Filter: ").append(filter).append("\n")
        }

        return result.toString()
    }
}
