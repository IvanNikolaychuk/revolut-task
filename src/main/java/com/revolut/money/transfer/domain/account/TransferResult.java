package com.revolut.money.transfer.domain.account;

public class TransferResult {
    private final String requestId;
    private final boolean successful;
    private final String details;

    private TransferResult(String requestId, boolean successful, String details) {
        this.requestId = requestId;
        this.successful = successful;
        this.details = details;
    }

    static TransferResult success(String requestId) {
        return new TransferResult(requestId, true, "");
    }

    public static TransferResult failed(String requestId, String details) {
        return new TransferResult(requestId, false, details);
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getDetails() {
        return details;
    }

    String getRequestId() {
        return requestId;
    }
}
