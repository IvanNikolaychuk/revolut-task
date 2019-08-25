package com.revolut.money.transfer.controller.account;

import com.google.gson.Gson;

public class TransferResponseDto {
    private boolean successful;
    private String details;

    public TransferResponseDto() {
    }

    TransferResponseDto(boolean successful, String details) {
        this.successful = successful;
        this.details = details;
    }

    public static TransferResponseDto fromJson(String json) {
        return new Gson().fromJson(json, TransferResponseDto.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
