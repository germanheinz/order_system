package com.order.system.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {

    private final BigDecimal amount;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean iGreaterThanZero(){
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean iGreaterThan(Money money){
        return this.amount != null && this.amount.compareTo(money.getAmount()) > 0;
    }

    public Money add(Money money){
        return new Money(setScale(this.amount.add(money.getAmount())));
    }

    public Money substract(Money money){
        return new Money(setScale(this.amount.subtract(money.getAmount())));
    }

    public Money multiply(int multiplier){
        return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }

    private BigDecimal setScale(BigDecimal input){
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
