package com.lab.model.dto;

import com.lab.model.model.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class DaysOffDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String message;
    private String userEmail;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
