package de.gmcs.builder;

/**
 * The exception is thrown if an error occurs while building and instance.
 */
public class GenericBuilderException extends RuntimeException {

    private static final long serialVersionUID = -440911810409000311L;

    public GenericBuilderException(Throwable t) {
        super(t);
    }
}
