package fi.evident.fab.proq2

import fi.evident.fab.rew.FilterConfigurations
import java.io.IOException
import java.io.OutputStream

private val maximumNumberOfFilters = 24

@Throws(IOException::class)
fun writePreset(filters: FilterConfigurations,
                globalParameters: GlobalPresetParameters,
                outputStream: OutputStream) {

    if (filters.amount > maximumNumberOfFilters)
        throw IllegalArgumentException("Filters are limited to $maximumNumberOfFilters, was: $filters.amount")

    val bands = Band.fromRewFilters(filters)

    val writer = LittleEndianBinaryStreamWriter(outputStream)

    writer.writeBytes("FQ2p".toByteArray())
    writer.writeInt(2)
    writer.writeInt(190)

    bands.forEach{ it.write(writer) }

    repeat(maximumNumberOfFilters - filters.amount) {
        Band.unusedBand.write(writer)
    }

    globalParameters.write(writer)
}
