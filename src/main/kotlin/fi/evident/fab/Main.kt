package fi.evident.fab

import fi.evident.fab.proq2.GlobalPresetParameters
import fi.evident.fab.proq2.PresetWriter
import fi.evident.fab.rew.FilterConfiguration
import fi.evident.fab.rew.FilterPlacement.*
import fi.evident.fab.rew.FilterSlope.dB_oct24
import fi.evident.fab.rew.Parser.parseFilterFile
import java.io.FileOutputStream
import java.util.*

fun main(args: Array<String>) {

    var outputFile: String? = null
    val configurations = ArrayList<FilterConfiguration>()

    var i = 0
    while (i < args.size) {

        val command = args[i].trim { it <= ' ' }

        if (i + 1 >= args.size) {
            terminate("Command must be followed by a parameter")
        }

        val parameter = args[i + 1].trim { it <= ' ' }

        when (command) {
            "-stereo" -> configurations.add(FilterConfiguration(dB_oct24, Stereo, parseFilterFile(parameter)))
            "-left" -> configurations.add(FilterConfiguration(dB_oct24, Left, parseFilterFile(parameter)))
            "-right" -> configurations.add(FilterConfiguration(dB_oct24, Right, parseFilterFile(parameter)))
            "-out" -> {
                if (outputFile != null) {
                    terminate("Output cannot be defined multiple times")
                } else {
                    outputFile = parameter
                }
            }
            else -> terminate("Unsupported command: " + command)
        }
        i += 2
    }

    if (outputFile == null) {
        terminate("Output file must be defined")
    } else {
        val globalParameters = GlobalPresetParameters()
        configurations.forEach(::println)

        FileOutputStream(outputFile).use { fos -> PresetWriter.writePreset(configurations, globalParameters, fos) }

        println("done")
    }
}

private fun terminate(reason: String) {
    println(reason)
    System.exit(-1)
}