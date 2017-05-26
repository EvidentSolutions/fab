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
    fun parseFilterFile(filePath: String) = parseFilterLines(Files.readAllLines(Paths.get(filePath)))

    private fun parseFilterLines(lines: List<String>) = lines.filter(this::isDefinedFilterLine).map(this::lineToBand)

    private fun isDefinedFilterLine(line: String) = line.matches(filterLineRegex) && !line.matches(isDefinedRegexp)

    private fun lineToBand(line: String): Filter {

        // Remove any non-breaking spaces
        val matcher = GenericFilterPattern.matcher(line.replace(nonBreakingSpaceRegexp.toRegex(), ""))
        if (!matcher.matches())
            throw IllegalArgumentException("Could not parse line: " + line)

        // TODO: remove trims?
        val enabled = matcher.trimmedGroup(1)
        val type = matcher.trimmedGroup(2)
        val freq = digitsAndDecimalSeparatorOnly.matcher(matcher.trimmedGroup(3)).replaceAll("")
        val gain = matcher.trimmedGroup(4)
        val q = matcher.trimmedGroup(5)

        if (type != "PK")
            throw IllegalArgumentException("Only PK filters are supported, was: " + type)

        return Filter(enabled == "ON", Filter.Type.PK, freq.toDouble(), gain.toDouble(), q.toDouble())
    }

    private fun Matcher.trimmedGroup(groupNumber: Int) = this.group(groupNumber).trim { it <= ' ' }
}
