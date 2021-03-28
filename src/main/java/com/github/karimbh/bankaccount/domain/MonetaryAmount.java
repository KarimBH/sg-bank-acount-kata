package com.github.karimbh.bankaccount.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A simplified and immutable representation of an amount of money with no currency support.
 * A JSR-354 implementation is required for a real world project
 */
public class MonetaryAmount {

    public static final MonetaryAmount ZERO = of(0);

    private BigDecimal amount;

    private MonetaryAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static MonetaryAmount of(BigDecimal amount) {
        return new MonetaryAmount(amount);
    }

    public static MonetaryAmount of(double amount) {
        return new MonetaryAmount(BigDecimal.valueOf(amount));
    }

    MonetaryAmount add(MonetaryAmount toBeAdded) {
        return new MonetaryAmount(
                amount.add(toBeAdded.amount)
        );
    }

    MonetaryAmount subtract(MonetaryAmount toBeSubstracted) {
        return new MonetaryAmount(
                amount.subtract(toBeSubstracted.amount)
        );
    }


    @Override
    public String toString() {
        return amount.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonetaryAmount that = (MonetaryAmount) o;
        // We compare the BigDecimal values using compareTo instead of equals. Equals includes the scale and
        // We are only interested in numeric value comparison in our case.
        return amount.compareTo(that.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
