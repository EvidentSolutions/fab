package fi.evident.fab.rew;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

    private static Pattern digitsAndDecimalSeparatorOnly = Pattern.compile("[^\\d\\.]");

    // Filter  1: ON  PK       Fc    63,8 Hz  Gain  -5,0 dB  Q  8,06
    private static Pattern GenericFilterPattern = Pattern.compile("^Filter\\s+\\d+:\\s(\\w+)\\s+(\\w+)\\s+Fc ([\\D\\d\\.]+) Hz  Gain ([\\s\\d\\.\\-]+) dB  Q ([\\s\\d\\.]+)$");

    public static List<Filter> parseFilterFile(String filePath) throws IOException {
        return parseFilterLines(Files.lines(Paths.get(filePath)));
    }

    private static List<Filter> parseFilterLines(Stream<String> lines) {
        return lines.filter(Parser::isDefinedFilterLine)
                .map(Parser::lineToBand)
                .collect(Collectors.toList());
    }

    private static boolean isDefinedFilterLine(String line) {
        boolean isFilterLine = line.matches("^Filter\\s+\\d+:.*$");
        boolean isDefined = !line.matches("^Filter\\s+\\d+:\\s+ON\\s+None.*$");
        return isFilterLine && isDefined;
    }

    private static Filter lineToBand(String line) {

        // remove any non breaking spaces
        Matcher matcher = GenericFilterPattern.matcher(line.replaceAll("\\xA0", ""));

        if (matcher.matches()) {

            String enabled = matcher.group(1).trim();
            String type = matcher.group(2).trim();
            String freq = digitsAndDecimalSeparatorOnly.matcher(matcher.group(3).trim()).replaceAll("");
            String gain = matcher.group(4).trim();
            String q = matcher.group(5).trim();

            if (!type.equals("PK"))
                throw new IllegalArgumentException("Only PK filters are supported, was: " + type);

            return new Filter(
                    enabled.equals("ON"),
                    Filter.Type.PK,
                    Double.parseDouble(freq),
                    Double.parseDouble(gain),
                    Double.parseDouble(q)
            );

        } else {
            throw new IllegalArgumentException("Could not parse line: " + line);
        }
    }
}
