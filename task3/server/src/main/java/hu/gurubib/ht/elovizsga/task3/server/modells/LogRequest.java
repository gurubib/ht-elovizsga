package hu.gurubib.ht.elovizsga.task3.server.modells;

public class LogRequest {

    private String message;

    public LogRequest() { }

    public LogRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
