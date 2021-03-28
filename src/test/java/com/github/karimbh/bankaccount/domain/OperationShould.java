package com.github.karimbh.bankaccount.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OperationShould {


    @Test
    void have_a_date_upon_creation() {
        Operation operation = new Operation(OperationType.DEPOSIT, () -> MonetaryAmount.of(900), MonetaryAmount.of(100));

        assertNotNull(operation.getDate());
    }


    @Test
    void have_running_balance_1000_when_prior_balance_is_900_and_operation_is_100_deposit() {
        Operation operation = new Operation(OperationType.DEPOSIT, () -> MonetaryAmount.of(900), MonetaryAmount.of(100));

        assertEquals(MonetaryAmount.of(1000), operation.getRunningBalance());
    }

    @Test
    void have_running_balance_400_when_prior_balance_is_1200_and_operation_is_800_withdrawal() {
        Operation operation = new Operation(OperationType.WITHDRAWAL, () -> MonetaryAmount.of(1200), MonetaryAmount.of(800));

        assertEquals(MonetaryAmount.of(400), operation.getRunningBalance());
    }

}