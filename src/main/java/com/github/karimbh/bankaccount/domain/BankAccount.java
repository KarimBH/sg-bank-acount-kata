package com.github.karimbh.bankaccount.domain;

import java.util.ArrayList;
import java.util.List;

import static com.github.karimbh.bankaccount.domain.OperationType.DEPOSIT;
import static com.github.karimbh.bankaccount.domain.OperationType.WITHDRAWAL;

public class BankAccount {

    private List<Operation> operations;

    public BankAccount() {
        this.operations = new ArrayList<>();
    }

    public Operation deposit(MonetaryAmount amount) {
        return addOperation(DEPOSIT, amount);
    }

    public Operation withdraw(MonetaryAmount amount) {
        return addOperation(WITHDRAWAL, amount);
    }

    public List<Operation> listOperations() {
        return operations;
    }

    private Operation addOperation(OperationType type, MonetaryAmount amount) {
        if (operations.isEmpty()) {
            Operation operation = new Operation(type, () -> MonetaryAmount.ZERO, amount);
            operations.add(operation);
            return operation;
        }
        Operation operation = new Operation(type, getLastOperation()::getRunningBalance, amount);
        operations.add(operation);
        return operation;
    }

    private Operation getLastOperation() {
        return operations.get(operations.size() - 1);
    }
}
