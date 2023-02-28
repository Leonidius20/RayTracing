package ua.leonidius.raytracing.arguments;

import lombok.Getter;
import org.apache.commons.cli.Options;

public class MissingCliParameterException extends Exception {

    @Getter private final Options options;

    public MissingCliParameterException(Options options) {
        this.options = options;
    }

}
