package pt.isec.pa.tinypac.model.data.exceptions;

public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String fileName) {
        super("Invalid maze format for file " + fileName);
    }
}
