package com.expense.api.DTO;

import java.util.Date;

public class ExpenseDTO {
    private Long expenseId;
    private String description;
    private Double amount;
    private String category;
    private byte[] upload;
    private Date date;
    private String filename;
    private String contentType;
    private UserDTO userdata;

    public ExpenseDTO(Long expenseId, String description, Double amount, String category, byte[] upload, Date date, String filename, String contentType, UserDTO userdata) {
        this.expenseId = expenseId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.upload = upload;
        this.date = date;
        this.filename = filename;
        this.contentType = contentType;
        this.userdata = userdata;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getUpload() {
        return upload;
    }

    public void setUpload(byte[] upload) {
        this.upload = upload;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public UserDTO getUserdata() {
        return userdata;
    }

    public void setUserdata(UserDTO userdata) {
        this.userdata= userdata;
    }
}
