package by.vikhor.softeqdemo.webcrawler.exception;

public class FileWritingException extends Exception {
    public FileWritingException() {
    }

    public FileWritingException(String message) {
        super(message);
    }

    public FileWritingException(String message, Throwable cause) {
        super(message, cause);
    }
}
