package fi.evident.fab.proq2

import fi.evident.fab.rew.FilterConfiguration
import java.io.IOException
import java.io.OutputStream

object PresetWriter {

    private val maximumNumberOfFilters = 24

    @Throws(IOException::class)
    fun writePreset(filterConfigurations: List<FilterConfiguration>, globalParameters: GlobalPresetParameters, outputStream: OutputStream) {

        val numberOfFilters = filterConfigurations.stream().mapToInt { x -> x.getFilters().size }.sum()

        if (numberOfFilters > maximumNumberOfFilters)
            throw IllegalArgumentException("Filters are limited to 24, was: " + numberOfFilters)

        val writer = LittleEndianBinaryStreamWriter(outputStream)

        writer.writeBytes("FQ2p".toByteArray())
        writer.writeInt(2)
        writer.writeInt(190)

        for (filterConfiguration in filterConfigurations) {

            val slope = BandSlope.of(filterConfiguration.slope)
            val placement = BandPlacement.of(filterConfiguration.placement)

            for (filter in filterConfiguration.getFilters())
                Band.fromRewFilter(filter, slope, placement).write(writer)
        }

        for (i in 0..maximumNumberOfFilters - numberOfFilters - 1)
            Band.unusedBand.write(writer)

        globalParameters.write(writer)
    }
}