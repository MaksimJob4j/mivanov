package ru.job4j.vendingmachine;

/**
 * Касса.
 */
public class Cashbox {
    /**
     * Монеты в кассе.
     */
    private Coin[] cash = new Coin[Nominal.values().length];

    /**
     * Доавление монет в кассу.
     * @param coins ю
     */
    public void addCoins(Coin ... coins) {
        for (int i = 0; i < Nominal.values().length; i++) {
            for (Coin coin: coins) {
                if (coin != null && Nominal.values()[i].getValue()
                        == coin.getNominal().getValue()) {
                    if (cash[i] != null) {
                        cash[i].addAmount(coin.getAmount());
                    } else {
                        cash[i] = coin;
                    }
                }
            }
        }
    }

    /**
     * Расчет монет для возврата.
     * @param money money.
     * @param price price.
     * @return String.
     * @throws NotEnoughCoinsException Недостаточно денег в кассе.
     * @throws NotEnoughMoneyException Недостаточно денег для покупки.
     * @throws NotEnoughCoinsForExchangeException Недостаточно монет для размена.
     */
    public Coin[] change(int money, int price) throws NotEnoughCoinsException, NotEnoughMoneyException, NotEnoughCoinsForExchangeException {


        if (money < price) {
            throw new NotEnoughMoneyException("Недостаточно денег для покупки");
        }

        int moneyToReturn = money - price;

        if (moneyToReturn > this.sumCash()) {
            throw new NotEnoughCoinsException("Недостаточно монет для сдачи");
        }

        Coin[] returnCoins = new Coin[Nominal.values().length];

        for (int i = 0; i < cash.length; i++) {
            if (cash[i] != null && cash[i].getAmount() > 0) {
                int coinsAmount = moneyToReturn / cash[i].getNominal().getValue();
                if (coinsAmount == 0) {
                    continue;
                }
                if (coinsAmount < cash[i].getAmount()) {
                    returnCoins[i] = new Coin(cash[i].getNominal(), coinsAmount);
                    moneyToReturn -= returnCoins[i].getSum();
                    cash[i].takeAmount(coinsAmount);
                } else {
                    returnCoins[i] = new Coin(cash[i].getNominal(), cash[i].getAmount());
                    moneyToReturn -= returnCoins[i].getSum();
                    cash[i].takeAmount(cash[i].getAmount());
                }
            }

        }

        if (moneyToReturn > 0) {
            addCoins(returnCoins);
            throw new NotEnoughCoinsForExchangeException("Недостаточно монет для размена");
        }

        return returnCoins;

    }

    /**
     * Сумма в кассе.
     * @return int.
     */
    private int sumCash() {
        int sum = 0;
        for (Coin coin : this.cash) {
            if (coin != null) {
                sum += coin.getSum();
            }
        }
        return sum;
    }

}
