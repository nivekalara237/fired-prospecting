package com.niveka.payload;

/**
 * Created by Nivek@lara on 31/05/2019.
 */

public class Notify{
    private int id;
    private String title;
    private String uniquid;
    private String content;
    private String createAdt;
    private String senderName;
    private boolean status;
    private int type;

    public Notify() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateAdt() {
        return createAdt;
    }

    public void setCreateAdt(String createAdt) {
        this.createAdt = createAdt;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUniquid() {
        return uniquid;
    }

    public void setUniquid(String uniquid) {
        this.uniquid = uniquid;
    }

    @Override
    public String toString() {
        return "Notify{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", createAdt='" + createAdt + '\'' +
            ", senderName='" + senderName + '\'' +
            ", type=" + type +
            ", status=" + status +
            ", uniquid=" + uniquid +
            '}';
    }
}
