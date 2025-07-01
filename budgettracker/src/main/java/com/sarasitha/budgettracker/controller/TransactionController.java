package com.sarasitha.budgettracker.controller;

import com.sarasitha.budgettracker.model.Transaction;
import com.sarasitha.budgettracker.model.Category;
import com.sarasitha.budgettracker.repository.TransactionRepository;
import com.sarasitha.budgettracker.model.Income;
import com.sarasitha.budgettracker.repository.IncomeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Transaction> transactions = transactionRepository.findAll();
        YearMonth currentMonth = YearMonth.now();
        double monthlyTotal = transactions.stream()
                .filter(t -> t.getDate() != null && YearMonth.from(t.getDate()).equals(currentMonth))
                .mapToDouble(Transaction::getAmount)
                .sum();
        List<Income> incomes = incomeRepository.findAll();
        double totalEarnings = incomes.stream()
                .filter(i -> i.getDate() != null && YearMonth.from(i.getDate()).equals(currentMonth))
                .mapToDouble(Income::getAmount)
                .sum();
        double netCashflow = totalEarnings - monthlyTotal;
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("income", new Income());
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", Category.values());
        model.addAttribute("monthlyTotal", monthlyTotal);
        model.addAttribute("totalEarnings", totalEarnings);
        model.addAttribute("netCashflow", netCashflow);
        // Category-wise totals
        Map<Category, Double> categoryTotals = new HashMap<>();
        for (Transaction t : transactions) {
            if (t.getCategory() != null) {
                categoryTotals.put(t.getCategory(), categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }
        java.util.List<String> categoryLabels = categoryTotals.keySet().stream().map(Enum::name).toList();
        java.util.List<Double> categoryValues = categoryTotals.values().stream().toList();
        model.addAttribute("categoryLabels", categoryLabels);
        model.addAttribute("categoryValues", categoryValues);
        java.util.List<String> categoryGradients = java.util.List.of(
            "#6366f1", "#10b981", "#f59e42", "#ef4444", "#eab308",
            "#a78bfa", "#f472b6", "#38bdf8", "#34d399", "#f87171"
        );
        model.addAttribute("categoryGradients", categoryGradients);
        return "home";
    }

    @PostMapping("/add")
    public String addTransaction(@ModelAttribute Transaction transaction) {
        if (transaction.getDate() == null) {
            transaction.setDate(LocalDate.now());
        }
        transactionRepository.save(transaction);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.csv");
        List<Transaction> transactions = transactionRepository.findAll();
        try (PrintWriter writer = response.getWriter()) {
            writer.println("Title,Amount,Date,Category,Description");
            for (Transaction t : transactions) {
                writer.printf("\"%s\",%s,%s,%s,\"%s\"\n",
                        t.getTitle() != null ? t.getTitle().replace("\"", "\"\"") : "",
                        t.getAmount(),
                        t.getDate() != null ? t.getDate() : "",
                        t.getCategory() != null ? t.getCategory() : "",
                        t.getDescription() != null ? t.getDescription().replace("\"", "\"\"") : "");
            }
        }
    }

    @PostMapping("/income")
    public String addIncome(@ModelAttribute Income income) {
        if (income.getDate() == null) {
            income.setDate(LocalDate.now());
        }
        incomeRepository.save(income);
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Transaction> transactions = transactionRepository.findAll();
        YearMonth currentMonth = YearMonth.now();
        double monthlyTotal = transactions.stream()
                .filter(t -> t.getDate() != null && YearMonth.from(t.getDate()).equals(currentMonth))
                .mapToDouble(Transaction::getAmount)
                .sum();
        List<Income> incomes = incomeRepository.findAll();
        double totalEarnings = incomes.stream()
                .filter(i -> i.getDate() != null && YearMonth.from(i.getDate()).equals(currentMonth))
                .mapToDouble(Income::getAmount)
                .sum();
        double netCashflow = totalEarnings - monthlyTotal;
        model.addAttribute("transactions", transactions);
        model.addAttribute("monthlyTotal", monthlyTotal);
        model.addAttribute("totalEarnings", totalEarnings);
        model.addAttribute("netCashflow", netCashflow);
        return "dashboard";
    }

    @GetMapping("/transaction/{id}")
    @ResponseBody
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        return transactionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/transaction/edit")
    public String editTransaction(@ModelAttribute Transaction transaction) {
        if (transaction.getId() != null && transactionRepository.existsById(transaction.getId())) {
            transactionRepository.save(transaction);
        }
        return "redirect:/";
    }

    @PostMapping("/transaction/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteTransactionAjax(@PathVariable Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/income/{id}")
    @ResponseBody
    public ResponseEntity<Income> getIncome(@PathVariable Long id) {
        return incomeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/income/edit")
    public String editIncome(@ModelAttribute Income income) {
        if (income.getId() != null && incomeRepository.existsById(income.getId())) {
            incomeRepository.save(income);
        }
        return "redirect:/";
    }

    @PostMapping("/income/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteIncomeAjax(@PathVariable Long id) {
        if (incomeRepository.existsById(id)) {
            incomeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
