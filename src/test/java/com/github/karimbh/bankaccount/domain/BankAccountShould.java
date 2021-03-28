package com.github.karimbh.bankaccount.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.karimbh.bankaccount.domain.OperationType.DEPOSIT;
import static com.github.karimbh.bankaccount.domain.OperationType.WITHDRAWAL;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountShould {

    @Nested
    @DisplayName("Deposit")
    class DepositUseCases {
        @Test
        void make_deposit_operation() {
            BankAccount bankAccount = new BankAccount();
            MonetaryAmount initialBalance = MonetaryAmount.ZERO;
            MonetaryAmount depositAmount = MonetaryAmount.of(100);

            Operation depositOperation = bankAccount.deposit(depositAmount);

            assertNotNull(depositOperation);
            assertNotNull(depositOperation.getDate());
            assertEquals(DEPOSIT, depositOperation.getType());
            assertEquals(depositAmount, depositOperation.getAmount());
            assertEquals(initialBalance.add(depositAmount), depositOperation.getRunningBalance());
        }

        @Test
        void add_deposit_operation_to_list_of_operations_after_deposit_performed() {
            BankAccount bankAccount = new BankAccount();
            MonetaryAmount depositAmount = MonetaryAmount.of(100);

            bankAccount.deposit(depositAmount);

            List<Operation> operations = bankAccount.listOperations();
            assertNotNull(operations);
            assertEquals(1, operations.size());
            assertEquals(DEPOSIT, operations.get(0).getType());
            assertEquals(depositAmount, operations.get(0).getAmount());
        }
    }

    @Nested
    @DisplayName("Withdrawal")
    class WithdrawalUseCases {
        @Test
        void make_withdrawal_operation() {
            BankAccount bankAccount = new BankAccount();
            MonetaryAmount initialBalance = MonetaryAmount.ZERO;
            MonetaryAmount withdrawalAmount = MonetaryAmount.of(100);

            Operation withdrawalOperation = bankAccount.withdraw(withdrawalAmount);

            assertNotNull(withdrawalOperation);
            assertNotNull(withdrawalOperation.getDate());
            assertEquals(WITHDRAWAL, withdrawalOperation.getType());
            assertEquals(withdrawalAmount, withdrawalOperation.getAmount());
            assertEquals(initialBalance.subtract(withdrawalAmount), withdrawalOperation.getRunningBalance());
        }

        @Test
        void add_withdraw_operation_to_list_of_operations_after_withdraw_performed() {
            BankAccount bankAccount = new BankAccount();
            MonetaryAmount withdrawAmount = MonetaryAmount.of(100);

            bankAccount.withdraw(withdrawAmount);

            List<Operation> operations = bankAccount.listOperations();
            assertNotNull(operations);
            assertEquals(1, operations.size());
            assertEquals(WITHDRAWAL, operations.get(0).getType());
            assertEquals(withdrawAmount, operations.get(0).getAmount());
        }
    }


    @Nested
    @DisplayName("Check operations")
    class CheckOperationsUseCases {

        @Test
        void have_no_operations_upon_account_creation() {
            BankAccount bankAccount = new BankAccount();

            assertTrue(bankAccount.listOperations().isEmpty());
        }

        @Test
        void return_the_list_operations_in_order_with_proper_type_amount_and_running_balance() {
            BankAccount bankAccount = new BankAccount();
            bankAccount.deposit(MonetaryAmount.of(1000));
            bankAccount.deposit(MonetaryAmount.of(200.99));
            bankAccount.withdraw(MonetaryAmount.of(99.99));

            List<Operation> operations = bankAccount.listOperations();

            assertNotNull(operations);
            assertEquals(3, operations.size());
            Operation operation1 = operations.get(0);
            assertNotNull(operation1.getDate());
            assertEquals(DEPOSIT, operation1.getType());
            assertEquals(MonetaryAmount.of(1000), operation1.getAmount());
            assertEquals(MonetaryAmount.of(1000), operation1.getRunningBalance());
            Operation operation2 = operations.get(1);
            assertNotNull(operation2.getDate());
            assertEquals(DEPOSIT, operation2.getType());
            assertEquals(MonetaryAmount.of(200.99), operation2.getAmount());
            assertEquals(MonetaryAmount.of(1200.99), operation2.getRunningBalance());
            Operation operation3 = operations.get(2);
            assertNotNull(operation3.getDate());
            assertEquals(WITHDRAWAL, operation3.getType());
            assertEquals(MonetaryAmount.of(99.99), operation3.getAmount());
            assertEquals(MonetaryAmount.of(1101), operation3.getRunningBalance());
        }

    }
}