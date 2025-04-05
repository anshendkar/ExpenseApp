package com.expense.api.controller;

import com.expense.api.DTO.ExpenseDTO;
import com.expense.api.entity.Expense;
import com.expense.api.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @PostMapping()
    public ResponseEntity<?> save(
            @RequestParam String description,
            @RequestParam Double amount,
            @RequestParam String category,
            @RequestParam(required = false) MultipartFile file
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            String response = expenseService.save(username, description, amount, category, file);
            logger.info(response);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("response", response);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            logger.error("Error adding expense: " + e.getMessage());
            return ResponseEntity.status(500).body("Error adding expense");
        }
    }
    @GetMapping("/")
    public List<ExpenseDTO> getExpenseByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return expenseService.getExpenseByUser(username);
    }

    @PutMapping("/{expenseId}/{userid}")
    public ResponseEntity<?>  update(@PathVariable Long expenseId , @PathVariable Long userid,
                                     @RequestParam String description , @RequestParam Double amount , @RequestParam String category , @RequestParam(required = false) MultipartFile file
                                     ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            Expense response = expenseService.updateExpense(expenseId,userid,description,amount,category,file);
            return ResponseEntity.ok(response);

        }catch (Exception e){
            return ResponseEntity.status(500).body("error updating expense");
        }
    }

    @GetMapping("/{expenseId}/{userid}/download")
    public ResponseEntity<?> download(@PathVariable Long expenseId , @PathVariable Long userid){

        Expense expense = expenseService.getExpenseByuserId(expenseId , userid);

        if(expense == null || expense.getUpload() == null){
            return ResponseEntity.notFound().build();
        }

        if(expense.getContentType() == null){
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ByteArrayResource("File type is not supported".getBytes()));
        }
        MediaType mediaType = MediaType.parseMediaType(expense.getContentType() != null ? expense.getContentType() : "application/octet-stream");

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=\"" + expense.getFilename() + "\" ")
                        .body(new ByteArrayResource(expense.getUpload()));

    }

    @GetMapping("/{expenseId}/{userid}/view")
    public ResponseEntity<?> view(@PathVariable Long expenseId , @PathVariable Long userid){
        Expense expense = expenseService.getExpenseByuserId(expenseId , userid);
        if(expense == null || expense.getUpload() == null){
            return ResponseEntity.notFound().build();
        }

        if(expense.getContentType() == null){
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ByteArrayResource("File type is not supported".getBytes()));
        }
        MediaType mediaType = MediaType.parseMediaType(expense.getContentType() != null ? expense.getContentType() : "application/octet-stream");

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION , "inline; filename=\"" + expense.getFilename() + "\"")
                .body(new ByteArrayResource(expense.getUpload()));

    }

//    @GetMapping("/{expenseId}")
//    public ResponseEntity<?> getExpenseById(@PathVariable Long expenseId) {
//        Expense expense = expenseService.getExpenseById(expenseId);
//        return ResponseEntity.ok(expense);
//    }

    @GetMapping("summary/{userid}")
    public ResponseEntity<Map<String , Object>>  getSummaryByUser(@PathVariable Long userid){
        Map<String, Object> summary = expenseService.getUserExpenseSummary(userid);
        return ResponseEntity.ok(summary);
    }
    @GetMapping("/summary/by-category/{userid}")
    public ResponseEntity<Map<String, Double>> getExpenseSummaryForDonutChart(@PathVariable Long userid) {
        Map<String, Double> categorySummary = expenseService.getCategoryWiseExpenseSummary(userid);
        return ResponseEntity.ok(categorySummary);
    }


    @GetMapping("/{expenseId}/{userid}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long expenseId, @PathVariable Long userid) {
        try {
            Expense expense = expenseService.getExpenseByuserId(expenseId, userid);
            return ResponseEntity.ok(expense);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Expense not found");
        }
    }

    @DeleteMapping("/{expenseId}/{userid}")
    public ResponseEntity<Map<String , String>> deleteExpense(@PathVariable Long expenseId , @PathVariable Long userid){
        try{
            Expense expense  = expenseService.getExpenseByuserId(expenseId, userid);
            if(expense != null){
                expenseService.deleteExpense(expenseId ,userid);
                Map<String , String> response = new HashMap<>();
                response
                        .put("message" , "Expense deleted successsfully");
                return ResponseEntity.ok(response);

            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Expense not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to delete expense"));
        }

        }
    }
