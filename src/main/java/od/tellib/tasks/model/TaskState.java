package od.tellib.tasks.model;

public enum TaskState {
    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    private String value;

    TaskState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
