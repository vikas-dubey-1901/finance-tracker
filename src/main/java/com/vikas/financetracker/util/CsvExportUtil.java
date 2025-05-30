package com.vikas.financetracker.util;

import com.example.reactivefinancetracker.domain.Transaction;
import com.example.reactivefinancetracker.dto.BudgetResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.math.RoundingMode;
import java.util.List;

public class CsvExportUtil {

    public static byte[] exportTransactions(List<Transaction> transactions) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(out), CSVFormat.DEFAULT
                     .withHeader("Date", "Category", "Amount", "Type", "Description"))) {

            for (Transaction tx : transactions) {
                csvPrinter.printRecord(
                        tx.getTimestamp().toString(),
                        tx.getCategory(),
                        tx.getAmount().setScale(2, RoundingMode.HALF_UP),
                        tx.getType().name(),
                        tx.getDescription()
                );
            }
            csvPrinter.flush();
            return out.toByteArray();
        }
    }

    public static byte[] exportBudgetSummary(List<BudgetResponse> summaries) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(out), CSVFormat.DEFAULT
                     .withHeader("Category", "Year", "Month", "Budgeted", "Spent"))) {

            for (BudgetResponse br : summaries) {
                csvPrinter.printRecord(
                        br.getCategory(),
                        br.getYear(),
                        br.getMonth(),
                        br.getBudgeted().setScale(2, RoundingMode.HALF_UP),
                        br.getSpent().setScale(2, RoundingMode.HALF_UP)
                );
            }
            csvPrinter.flush();
            return out.toByteArray();
        }
    }
}

