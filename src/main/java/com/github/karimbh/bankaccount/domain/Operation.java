package com.github.karimbh.bankaccount.domain;

import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

public class Operation {

    private Date date;
    private OperationType type;
    private MonetaryAmount amount;
    private Supplier<MonetaryAmount> priorBalance;

    Operation(OperationType type, Supplier<MonetaryAmount> priorBalance, MonetaryAmount amount) {
        this.type = type;
        this.priorBalance = priorBalance;
        this.amount = amount;
        this.date = new Date();
    }

    public MonetaryAmount getRunningBalance() {
        if (type == OperationType.DEPOSIT) {
            return priorBalance.get().add(amount);
        }
        return priorBalance.get().subtract(amount);
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public OperationType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation that = (Operation) o;
        return date.equals(that.date) &&
                type == that.type &&
                amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, type, amount);
    }
}
