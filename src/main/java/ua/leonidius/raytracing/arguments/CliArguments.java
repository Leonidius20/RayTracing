package ua.leonidius.raytracing.arguments;

import org.apache.commons.cli.*;
import ua.leonidius.raytracing.IProgramArguments;

public class CliArguments implements IProgramArguments {

    private final String inputFile;
    private final String outputFile;

    public CliArguments(String[] args) throws ParseException, MissingCliParameterException {
        // parsing CLI arguments
        var options = new Options();

        var outputOption = Option.builder("output")
                .argName("file")
                .hasArg()
                .desc("output file path")
                .build();

        var inputOption = Option.builder("input")
                .argName("file")
                .hasArg()
                .desc("input file path")
                .build();

        options.addOption(outputOption);
        options.addOption(inputOption);

        var parser = new GnuParser();
        CommandLine line;
        line = parser.parse(options, args);

        if (line.hasOption("output")) {
            outputFile = line.getOptionValue("output");
        } else {
            throw new MissingCliParameterException(options);
        }

        if (line.hasOption("input")) {
            inputFile = line.getOptionValue("input");
        } else {
            throw new MissingCliParameterException(options);
        }
    }

    @Override
    public String inputFile() {
        return inputFile;
    }

    @Override
    public String outputFile() {
        return outputFile;
    }

}
