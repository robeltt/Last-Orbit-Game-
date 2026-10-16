package lastorbit.exceptions;


public class GameException extends Exception {

    public GameException(String message) {
        super(message);// chains up to the throwable class constructor to set the message
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);  
    }
}