package africa.semicolon.goodreads.exception;

import lombok.Getter;

@Getter
public class GoodReadsException extends Exception{
    private int statusCode;
    public GoodReadsException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
