package fi.evident.fab

import fi.evident.fab.proq2.GlobalPresetParameters
import fi.evident.fab.proq2.PresetWriter
import fi.evident.fab.rew.FilterConfigurations
import fi.evident.fab.rew.FilterPlacement.*
import fi.evident.fab.rew.FilterSlope.dB_oct24
import fi.evident.fab.rew.Parser.parseFilterFile
import java.io.FileOutputStream

fun main(args: Array<String>) {

    var outputFile: String? = null
    val configurations = FilterConfigurations()

    var i = 0
    while (i < args.size) {

        val command = args[i++].trim { it <= ' ' }

        if (i >= args.size)
            terminate("Command must be followed by a parameter")

        val parameter = args[i++].trim { it <= ' ' }

        when (command) {
            "-stereo" -> configurations.add(dB_oct24, Stereo, parseFilterFile(parameter))
            "-left" -> configurations.add(dB_oct24, Left, parseFilterFile(parameter))
            "-right" -> configurations.add(dB_oct24, Right, parseFilterFile(parameter))
            "-out" -> {
                if (outputFile != null)
                    terminate("Output cannot be defined multiple times")

                outputFile = parameter
            }
            else -> terminate("Unsupported command: " + command)
        }
    }

    if (outputFile == null)
        terminate("Output file must be defined")

    configurations.printDebugInfo()

    FileOutputStream(outputFile).use { fos ->
        PresetWriter.writePreset(configurations, GlobalPresetParameters(), fos)
    }

    println("done")
}

private fun terminate(reason: String) {
    println(reason)
    System.exit(-1)
}

