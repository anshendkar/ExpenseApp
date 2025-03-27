package com.security.jwt.service;

import com.security.jwt.DTO.ExpenseDTO;
import com.security.jwt.DTO.UserDTO;
import com.security.jwt.entity.Expense;
import com.security.jwt.entity.Userdata;
import com.security.jwt.repo.ExpenseRepository;
import com.security.jwt.repo.Userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private Userrepository userRepository;

    public List<ExpenseDTO> getExpenseByUser(String username) {
        Userdata user = userRepository.findByemail(username);
        if (user == null) {
            System.out.println("User not found");
        }
        List<Expense> expenses = expenseRepository.findByUserdata(user);

        List<ExpenseDTO> expenseDTOs = new ArrayList<>();
        // Loop through each expense and manually map it to an ExpenseDTO
        for (Expense expense : expenses) {
            // Manually map Expense to ExpenseDTO
            UserDTO userdataDTO = new UserDTO(
                    expense.getUser().getUserid(),
                    expense.getUser().getFirstname(),
                    expense.getUser().getLastname()
            );

            // Create ExpenseDTO and add it to the list
            ExpenseDTO expenseDTO = new ExpenseDTO(
                    expense.getExpenseId(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getCategory(),
                    expense.getUpload(),
                    expense.getDate(),
                    expense.getFilename(),
                    expense.getContentType(),
                    userdataDTO
            );

            expenseDTOs.add(expenseDTO);
        }

        return expenseDTOs;
                //expenseRepository.findByUserdata(user);

    }

    public String save(String username, String description, Double amount, String category, MultipartFile file) throws IOException {
        Userdata user = userRepository.findByemail(username);
        Expense expense = new Expense();

        Long lastExpenseId = expenseRepository.findMaxExpenseIdByUserId(user.getUserid());
        Long newExpenseId = lastExpenseId + 1;

        expense.setUser(user);
        expense.setExpenseId(newExpenseId);
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setUpload(file.getBytes());
        expense.setDate(new Date());
        expense.setContentType(file.getContentType());
        expense.setFilename(file.getOriginalFilename());
        expenseRepository.save(expense);

        return "Expense saved successfully";
    }

    public Expense getExpenseByuserId(Long expenseId, Long userid) {
        return expenseRepository.findByExpenseIdAndUserdata_Userid(expenseId, userid);
    }

    public Expense updateExpense(Long expenseId, Long userid, String description ,double amount, String category , MultipartFile file) throws IOException {
          Expense expense = getExpenseByuserId(expenseId, userid);
          expense.setDescription(description);
          expense.setAmount(amount);
          expense.setCategory(category);
          if(file != null) {
              expense.setUpload(file.getBytes());
              expense.setFilename(file.getOriginalFilename());
              expense.setContentType(file.getContentType());
          }
        return expenseRepository.save(expense);
    }

    public Map<String, Object> getUserExpenseSummary(Long userId) {
        long expenseCount = expenseRepository.countExpensesByUserId(userId);
        double totalAmount = expenseRepository.getTotalAmountSpentByUserId(userId);

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalExpenses", expenseCount);
        summary.put("totalAmountSpent", totalAmount);
        return summary;
    }

    public void deleteExpense(Long expenseId ,Long userid) {
        expenseRepository.deleteExpenseById(expenseId , userid);
    }
}
