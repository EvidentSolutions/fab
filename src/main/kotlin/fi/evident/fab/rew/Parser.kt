package fi.evident.fab.rew

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Matcher
import java.util.regex.Pattern

object Parser {

    private val digitsAndDecimalSeparatorOnly = Pattern.compile("""[^\d.]""")

    // Filter  1: ON  PK       Fc    63,8 Hz  Gain  -5,0 dB  Q  8,06
    private val GenericFilterPattern = Pattern.compile("""^Filter\s+\d+:\s(\w+)\s+(\w+)\s+Fc ([\D\d.]+) Hz {2}Gain ([\s\d.\-]+) dB {2}Q ([\s\d.]+)$""")

    private val filterLineRegex = """^Filter\s+\d+:.*$""".toRegex()
    private val isDefinedRegexp = """^Filter\s+\d+:\s+ON\s+None.*$""".toRegex()
    private val nonBreakingSpaceRegexp = """\xA0"""

    @Throws(IOException::class)
    fun parseFilterFile(filePath: String): List<Filter> {
        val lines = Files.readAllLines(Paths.get(filePath))
        return parseFilterLines(lines)
    }

    private fun parseFilterLines(lines: List<String>) = lines.filter(this::isDefinedFilterLine).map(this::lineToBand)

    private fun isDefinedFilterLine(line: String) = line.matches(filterLineRegex) && !line.matches(isDefinedRegexp)

    private fun lineToBand(line: String): Filter {

        // Remove any non-breaking spaces
        val matcher = GenericFilterPattern.matcher(line.replace(nonBreakingSpaceRegexp.toRegex(), ""))
        if (!matcher.matches())
            throw IllegalArgumentException("Could not parse line: $line")

        val (enabled, type, freqAlmost, gain, q) = (1..5).map { matcher.trimmedGroup(it) }
        val freq = digitsAndDecimalSeparatorOnly.matcher(freqAlmost).replaceAll("")

        if (type != "PK")
            throw IllegalArgumentException("Only PK filters are supported, was: $type")

        return Filter(enabled == "ON", Filter.Type.PK, freq.toDouble(), gain.toDouble(), q.toDouble())
    }

    private fun Matcher.trimmedGroup(groupNumber: Int) = this.group(groupNumber).trim { it <= ' ' }
}
