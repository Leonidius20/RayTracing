package ua.leonidius.raytracing;

public interface IProgramArguments {

    String inputFile();

    String outputFile();

    /**
     * Defines whether to render the scene with and without acceleration structures
     * and compare the time.
     */
    boolean demoMode();

}
