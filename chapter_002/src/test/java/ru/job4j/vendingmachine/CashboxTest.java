package ru.job4j.vendingmachine;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test VendingMachine.
 */
public class CashboxTest {


    /**
     * Test.
     */
    @Test
    public void whenNotEnoughMoneyThenException() {
        Cashbox cashbox = new Cashbox();
        cashbox.addCoins(new Coin(Nominal.TEN, 10), new Coin(Nominal.FIVE, 10));
        String result = "";
        try {
            cashbox.change(10, 100);
        } catch (NotEnoughCoinsException e) {
            result = e.toString();
        } catch (NotEnoughMoneyException e) {
            result = e.toString();
        } catch (NotEnoughCoinsForExchangeException e) {
            result = e.toString();
        }
        String expected = new NotEnoughMoneyException("Недостаточно денег для покупки").toString();
        assertThat(result, is(expected));
    }

    /**
     * Test.
     */
    @Test
    public void whenNotEnoughCoinsThenException() {
        Cashbox cashbox = new Cashbox();
        cashbox.addCoins(new Coin(Nominal.TEN, 10), new Coin(Nominal.FIVE, 10));
        String result = "";
        try {
            cashbox.change(1000, 100);
        } catch (NotEnoughCoinsException e) {
            result = e.toString();
        } catch (NotEnoughMoneyException e) {
            result = e.toString();
        } catch (NotEnoughCoinsForExchangeException e) {
            result = e.toString();
        }
        String expected = new NotEnoughCoinsException("Недостаточно монет для сдачи").toString();
        assertThat(result, is(expected));
    }

    /**
     * Test.
     */
    @Test
    public void whenNotEnoughCoinsForExchangeThenException() {
        Cashbox cashbox = new Cashbox();
        cashbox.addCoins(new Coin(Nominal.TEN, 10), new Coin(Nominal.FIVE, 10));
        String result = "";
        try {
            cashbox.change(111, 100);
        } catch (NotEnoughCoinsException e) {
            result = e.toString();
        } catch (NotEnoughMoneyException e) {
            result = e.toString();
        } catch (NotEnoughCoinsForExchangeException e) {
            result = e.toString();
        }
        String expected = new NotEnoughCoinsForExchangeException("Недостаточно монет для размена").toString();
        assertThat(result, is(expected));
    }

    /**
     * Test.
     */
    @Test
    public void whenEnoughCoinsThenChange() {
        Cashbox cashbox = new Cashbox();
        cashbox.addCoins(
                new Coin(Nominal.TEN, 10),
                new Coin(Nominal.FIVE, 10),
                new Coin(Nominal.TWO, 10),
                new Coin(Nominal.ONE, 10)
        );
        String result = "";

        try {
            Coin[] coins = cashbox.change(139, 100);
            StringBuilder stringBuilder = new StringBuilder();
            for (Coin coin: coins) {
                if (coin != null) {
                    stringBuilder.append(coin);
                    stringBuilder.append(" ");
                }
            }
            result = stringBuilder.toString();

        } catch (NotEnoughCoinsException e) {
            result = e.toString();
        } catch (NotEnoughMoneyException e) {
            result = e.toString();
        } catch (NotEnoughCoinsForExchangeException e) {
            result = e.toString();
        }
        String expected = "TEN-3 FIVE-1 TWO-2 ";
        assertThat(result, is(expected));
    }
}
