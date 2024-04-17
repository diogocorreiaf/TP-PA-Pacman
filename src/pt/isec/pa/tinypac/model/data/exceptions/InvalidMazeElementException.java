package pt.isec.pa.tinypac.model.data.exceptions;

public class InvalidMazeElementException extends Exception {
    public InvalidMazeElementException(char c) {
        super("Invalid character read '" + c + "' in maze");
    }
}
