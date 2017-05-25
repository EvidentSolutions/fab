package fi.evident.fab.proq2;

import fi.evident.fab.rew.Filter;
import fi.evident.fab.rew.FilterConfiguration;

import java.io.*;
import java.util.List;

public class PresetWriter {

    private static final int maximumNumberOfFilters = 24;

    public static void writePreset(List<FilterConfiguration> filterConfigurations, GlobalPresetParameters globalParameters, OutputStream outputStream) throws IOException {

        int numberOfFilters = filterConfigurations.stream().mapToInt(x -> x.getFilters().size()).sum();

        if (numberOfFilters > maximumNumberOfFilters)
            throw new IllegalArgumentException("Filters are limited to 24, was: " + numberOfFilters);

        LittleEndianBinaryStreamWriter writer = new LittleEndianBinaryStreamWriter(outputStream);

        writer.writeBytes("FQ2p".getBytes());
        writer.writeInt(2);
        writer.writeInt(190);

        for (FilterConfiguration filterConfiguration : filterConfigurations) {

            BandSlope slope = BandSlope.of(filterConfiguration.getSlope());
            BandPlacement placement = BandPlacement.of(filterConfiguration.getPlacement());

            for (Filter filter : filterConfiguration.getFilters())
                Band.fromRewFilter(filter, slope, placement).write(writer);
        }

        for (int i = 0; i < (maximumNumberOfFilters - numberOfFilters); i++)
            Band.unusedBand.write(writer);

        globalParameters.write(writer);
    }
}
