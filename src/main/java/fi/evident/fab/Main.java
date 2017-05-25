package fi.evident.fab;

import fi.evident.fab.proq2.PresetWriter;
import fi.evident.fab.proq2.GlobalPresetParameters;
import fi.evident.fab.rew.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static fi.evident.fab.rew.FilterPlacement.Left;
import static fi.evident.fab.rew.FilterPlacement.Right;
import static fi.evident.fab.rew.FilterPlacement.Stereo;
import static fi.evident.fab.rew.FilterSlope.dB_oct24;
import static fi.evident.fab.rew.Parser.parseFilterFile;

public class Main {

    public static void main(String[] args) throws IOException {

        String outputFile = null;
        List<FilterConfiguration> configurations = new ArrayList<>();

        for (int i = 0; i < args.length; i = i+2) {

            String command = args[i].trim();

            if (i + 1 >= args.length) {
                terminate("Command must be followed by a parameter");
            }

            String parameter = args[i+1].trim();

            switch (command) {
                case "-stereo":
                    configurations.add(new FilterConfiguration(dB_oct24, Stereo, parseFilterFile(parameter)));
                    break;
                case "-left":
                    configurations.add(new FilterConfiguration(dB_oct24, Left, parseFilterFile(parameter)));
                    break;
                case "-right":
                    configurations.add(new FilterConfiguration(dB_oct24, Right, parseFilterFile(parameter)));
                    break;
                case "-out":
                    if (outputFile != null) {
                        terminate("Output cannot be defined multiple times");
                    } else {
                        outputFile = parameter;
                    }
                    break;
                default:
                    terminate("Unsupported command: " + command);
            }
        }

        if (outputFile == null) {
            terminate("Output file must be defined");
        } else {
            GlobalPresetParameters globalParameters = new GlobalPresetParameters();
            configurations.forEach(System.out::println);

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                PresetWriter.writePreset(configurations, globalParameters, fos);
            }

            System.out.println("done");
        }
    }

    private static void terminate(String reason) {
        System.out.println(reason);
        System.exit(-1);
    }
}
