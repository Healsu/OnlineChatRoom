package model;

import java.util.Date;

public class Messages {
    private String messName;
    private String messMessage;
    private Date messDate;

    public Messages(String messName, String messMessage, Date messDate) {
        this.messName = messName;
        this.messMessage = messMessage;
        this.messDate = messDate;
    }

    public String getMessName() {
        return messName;
    }

    public void setMessName(String messName) {
        this.messName = messName;
    }

    public String getMessMessage() {
        return messMessage;
    }

    public void setMessMessage(String messMessage) {
        this.messMessage = messMessage;
    }

    public Date getMessDate() {
        return messDate;
    }

    public void setMessDate(Date messDate) {
        this.messDate = messDate;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "messName='" + messName + '\'' +
                ", messMessage='" + messMessage + '\'' +
                ", messDate=" + messDate +
                '}';
    }
}
