package ru.job4j.vendingmachine;

/**
 * Стопка монет одного номинала.
 */
public class Coin {
    /**
     * Номинал стопки.
     */
    private Nominal nominal;

    /**
     * Количество монет стопки.
     */
    private int amount;

    /**
     * Конструктор стопки монет.
     * @param nominal .
     * @param amount .
     */
    Coin(Nominal nominal, int amount) {
        this.nominal = nominal;
        this.amount = amount;
    }

    /**
     * Номинал стопки монет.
     * @return .
     */
    public Nominal getNominal() {
        return nominal;
    }

    /**
     * Количество монет в стопке.
     * @return .
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Добавить монеты в стопку.
     * @param amount .
     */
    public void addAmount(int amount) {
        this.amount += amount;
    }

    /**
     * Взять часть монет из стопки.
     * @param amount .
     */
    public void takeAmount(int amount) {
        this.amount -= amount;
    }

    /**
     * Сумма стопки монет.
     * @return .
     */
    public int getSum() {
        return this.amount * this.nominal.getValue();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.nominal.name());
        stringBuilder.append("-");
        stringBuilder.append(this.amount);

        return stringBuilder.toString();
    }

}
