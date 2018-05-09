package handlers;

public class HttpException extends RuntimeException {
    private int status;


    public HttpException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
