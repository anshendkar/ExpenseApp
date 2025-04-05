package com.expense.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@JsonIgnoreProperties({"userdata"})  // Prevents circular JSON serialization issues
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique primary key for DB reference

    private Long expenseId;  // Unique per user (not globally unique)

    private String description;
    private Double amount;
    private String category;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] upload;

    private Date date;

    private String filename;
    private String contentType;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonProperty("userdata")
    private Userdata userdata;  // Foreign key reference to Userdata

    public Expense() {}

    public Expense(Userdata userdata, Long expenseId, String description, Double amount, String category, byte[] upload, Date date, String filename, String contentType, String url) {
        this.userdata = userdata;
        this.expenseId = expenseId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.upload = upload;
        this.date = date;
        this.filename = filename;
        this.contentType = contentType;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public Long getExpenseId() { return expenseId; }

    public void setExpenseId(Long expenseId) { this.expenseId = expenseId; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Double getAmount() { return amount; }

    public void setAmount(Double amount) { this.amount = amount; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public byte[] getUpload() { return upload; }

    public void setUpload(byte[] upload) { this.upload = upload; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public String getContentType() { return contentType; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public Userdata getUser() { return userdata; }

    public void setUser(Userdata user) { this.userdata = user; }  // Fixed incorrect reference
}
