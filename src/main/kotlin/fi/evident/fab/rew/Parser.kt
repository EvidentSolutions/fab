package fi.evident.fab.rew

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern
import java.util.stream.Collectors.toList

object Parser {

    private val digitsAndDecimalSeparatorOnly = Pattern.compile("[^\\d.]")

    // Filter  1: ON  PK       Fc    63,8 Hz  Gain  -5,0 dB  Q  8,06
    private val GenericFilterPattern = Pattern.compile("^Filter\\s+\\d+:\\s(\\w+)\\s+(\\w+)\\s+Fc ([\\D\\d.]+) Hz {2}Gain ([\\s\\d.\\-]+) dB {2}Q ([\\s\\d.]+)$")

    @Throws(IOException::class)
    fun parseFilterFile(filePath: String): List<Filter> {
        return parseFilterLines(Files.lines(Paths.get(filePath)).collect(toList()))
    }

    private fun parseFilterLines(lines: List<String>): List<Filter> {
        return lines.filter { isDefinedFilterLine(it) }.map { lineToBand(it) }
    }

    private fun isDefinedFilterLine(line: String): Boolean {
        val isFilterLine = line.matches("^Filter\\s+\\d+:.*$".toRegex())
        val isDefined = !line.matches("^Filter\\s+\\d+:\\s+ON\\s+None.*$".toRegex())
        return isFilterLine && isDefined
    }

    private fun lineToBand(line: String): Filter {

        // remove any non breaking spaces
        val matcher = GenericFilterPattern.matcher(line.replace("\\xA0".toRegex(), ""))

        if (matcher.matches()) {

            val enabled = matcher.group(1).trim { it <= ' ' }
            val type = matcher.group(2).trim { it <= ' ' }
            val freq = digitsAndDecimalSeparatorOnly.matcher(matcher.group(3).trim { it <= ' ' }).replaceAll("")
            val gain = matcher.group(4).trim { it <= ' ' }
            val q = matcher.group(5).trim { it <= ' ' }

            if (type != "PK")
                throw IllegalArgumentException("Only PK filters are supported, was: " + type)

            return Filter(
                    enabled == "ON",
                    Filter.Type.PK,
                    java.lang.Double.parseDouble(freq),
                    java.lang.Double.parseDouble(gain),
                    java.lang.Double.parseDouble(q)
            )

        } else {
            throw IllegalArgumentException("Could not parse line: " + line)
        }
    }
}
