package com.example.baguiosiglat.referenceclasses;

public class Notification {
    private String from;
    private String email;
    private String number;
    private String message;
    private boolean read;
    private String notificationType;

    private String dateSent;

    public Notification(){}

    public Notification(String from, String email, String number, String message, boolean read, String notificationType, String dateSent) {
        this.from = from;
        this.email = email;
        this.number = number;
        this.message = message;
        this.read = read;
        this.notificationType = notificationType;
        this.dateSent = dateSent;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

}
