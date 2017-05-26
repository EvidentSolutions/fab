package fi.evident.fab

import fi.evident.fab.proq2.GlobalPresetParameters
import fi.evident.fab.proq2.PresetWriter
import fi.evident.fab.rew.FilterConfigurations
import fi.evident.fab.rew.FilterPlacement
import fi.evident.fab.rew.FilterPlacement.*
import fi.evident.fab.rew.FilterSlope
import fi.evident.fab.rew.FilterSlope.dB_oct24
import fi.evident.fab.rew.Parser.parseFilterFile
import java.io.FileOutputStream

fun main(args: Array<String>) {

    var outputFile: String? = null
    val configurations = FilterConfigurations()

    fun trimmedArgument(n: Int) = args[n].trim { it <= ' ' }

    fun addConfiguration(slope: FilterSlope, placement: FilterPlacement, parameter: String) {
        configurations.add(slope, placement, parseFilterFile(parameter))
    }

    for (i in 0 until args.size step 2) {
        val command = trimmedArgument(i)

        if (i+1 >= args.size)
            terminate("Command $command must be followed by a parameter")

        val parameter = trimmedArgument(i+1)

        when (command) {
            "-stereo" -> addConfiguration(dB_oct24, Stereo, parameter)
            "-left" -> addConfiguration(dB_oct24, Left, parameter)
            "-right" -> addConfiguration(dB_oct24, Right, parameter)
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

    println("Done")
}

private fun terminate(reason: String) {
    println(reason)
    System.exit(-1)
}

