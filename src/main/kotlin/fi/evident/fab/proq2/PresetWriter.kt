package fi.evident.fab.proq2

import fi.evident.fab.rew.FilterConfigurations
import java.io.IOException
import java.io.OutputStream

object PresetWriter {

    private val maximumNumberOfFilters = 24

    @Throws(IOException::class)
    fun writePreset(filterConfigurations: FilterConfigurations,
                    globalParameters: GlobalPresetParameters,
                    outputStream: OutputStream) {

        val numberOfFilters = filterConfigurations.totalNumberOfFilters

        if (numberOfFilters > maximumNumberOfFilters)
            throw IllegalArgumentException("Filters are limited to 24, was: $numberOfFilters")

        val writer = LittleEndianBinaryStreamWriter(outputStream)

        writer.writeBytes("FQ2p".toByteArray())
        writer.writeInt(2)
        writer.writeInt(190)

        Band.fromRewFilters(filterConfigurations).forEach{
            it.write(writer)
        }

        repeat(maximumNumberOfFilters - numberOfFilters) {
            Band.unusedBand.write(writer)
        }

        globalParameters.write(writer)
    }
}
