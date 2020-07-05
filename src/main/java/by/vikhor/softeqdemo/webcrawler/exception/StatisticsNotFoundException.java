package by.vikhor.softeqdemo.webcrawler.exception;

public class StatisticsNotFoundException extends RuntimeException {
    public StatisticsNotFoundException() {
    }

    public StatisticsNotFoundException(String message) {
        super(message);
    }

    public StatisticsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
