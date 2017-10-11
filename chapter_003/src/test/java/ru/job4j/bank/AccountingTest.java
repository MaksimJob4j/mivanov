package ru.job4j.bank;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * AccountingTest.
 */
public class AccountingTest {

    /**
     * Test addUser.
     */
    @Test
    public void whenAddUserThenMapHasUser() {
        Accounting accounting = new Accounting();
        User user = new User("Max", "12345qwe");
        accounting.addUser(user);
        boolean result = accounting.getUsers().contains(user);
        boolean expected = true;
        assertThat(result, is(expected));
    }

    /**
     * Test deleteUser.
     */
    @Test
    public void whenDeleteUserThenMapHasNoUser() {
        Accounting accounting = new Accounting();
        User user = new User("Max", "12345qwe");
        accounting.addUser(user);
        accounting.deleteUser(user);
        boolean result = accounting.getUsers().contains(user);
        boolean expected = false;
        assertThat(result, is(expected));
    }

    /**
     * Test addAccountToUser.
     */
    @Test
    public void whenAddAccountToUserThenMapHasUserWithAccount() {
        Accounting accounting = new Accounting();
        User user = new User("Max", "12345qwe");
        Account account = new Account("asdfg");
        accounting.addAccountToUser(user, account);
        boolean result = accounting.containsUserWithAccount(user, account);
        boolean expected = true;
        assertThat(result, is(expected));
    }

    /**
     * Test deleteAccountFromUser.
     */
    @Test
    public void whenDeleteAccountFromUserThenMapHasUserWithoutThisAccount() {
        Accounting accounting = new Accounting();
        User user = new User("Max", "12345qwe");
        Account account = new Account("asdfg");
        accounting.addAccountToUser(user, account);
        accounting.deleteAccountFromUser(user, account);
        boolean result = accounting.getUserAccounts(user).contains(account);
        boolean expected = false;
        assertThat(result, is(expected));
    }

    /**
     * Test getUserAccounts.
     */
    @Test
    public void whenGetUserAccountsThenAccountsInIt() {
        Accounting accounting = new Accounting();
        User user = new User("Max", "12345qwe");
        Account account1 = new Account("asdfg1");
        Account account2 = new Account("asdfg2");
        accounting.addAccountToUser(user, account1);
        accounting.addAccountToUser(user, account2);
        accounting.getUserAccounts(user);
        List<Account> list = accounting.getUserAccounts(user);
        Boolean result = list.contains(account1) && list.contains(account2) && list.size() == 2;
        Boolean expected = true;
        assertThat(result, is(expected));
    }


    /**
     * Test transferMoney.
     */
    @Test

    public void whenTransferMoneyToUserThenUserHasMoney() {
        Accounting accounting = new Accounting();

        User max = new User("Max", "12345qwe");
        Account maxAccount = new Account("asdfg1");
        maxAccount.addValue(1000);
        accounting.addAccountToUser(max, maxAccount);

        User petr = new User("Петр", "12345asd");
        Account petrAccount = new Account("asdfg1ljpo");

        accounting.transferMoney(max, maxAccount, petr, petrAccount, 100);
        accounting.transferMoney(max, maxAccount, petr, maxAccount, 1);
        accounting.transferMoney(max, petrAccount, petr, petrAccount, 2);
        accounting.transferMoney(max, maxAccount, petr, petrAccount, -4);
        accounting.transferMoney(null, maxAccount, petr, petrAccount, 8);
        accounting.transferMoney(max, null, petr, petrAccount, 16);

        double result = petrAccount.getValue();
        System.out.println(maxAccount.getValue());
        double expected = 100d;

        assertThat(result, is(expected));
    }
}