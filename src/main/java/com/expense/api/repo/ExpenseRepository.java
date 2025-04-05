package com.expense.api.repo;

import com.expense.api.entity.Expense;
import com.expense.api.entity.Userdata;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT COALESCE(MAX(e.expenseId), 0) FROM Expense e WHERE e.userdata.id = :userId")
    Long findMaxExpenseIdByUserId(Long userId);

    Expense findByExpenseIdAndUserdata_Userid(Long expenseId, Long userid);

    @Query("SELECT COUNT(e) FROM Expense e WHERE e.userdata.userid = :userId")
    long countExpensesByUserId(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.userdata.userid = :userId")
    double getTotalAmountSpentByUserId(@Param("userId") Long userId);

    @Query("SELECT e.category, SUM(e.amount) FROM Expense e WHERE e.userdata.userid = :userId GROUP BY e.category")
    List<Object[]> getCategoryWiseSummary(@Param("userId") Long userId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Expense e WHERE e.id = :expenseId AND e.userdata.id = :userId")
    void deleteExpenseById(@Param("expenseId") Long expenseId, @Param("userId") Long userId);





    List<Expense> findByUserdata(Userdata user);
}
