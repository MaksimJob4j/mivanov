package ru.job4j.bank;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;


/**
 * Учет.
 */
public class Accounting {
    /**
     * Клиенты банка.
     */
    private Map<User, List<Account>> clients = new HashMap<>();

    /**
     * добавление пользователя.
     * @param user user.
     */
    public void addUser(User user) {
        if (user != null) {
            this.clients.put(user, new ArrayList<Account>());
        }
    }


    /**
     * Список юзеров.
     * @return Set<User>.
     */
    public Set<User> getUsers() {
        return this.clients.keySet();
    }

    /**
     * удаление пользователя.
     * @param user user.
     */
    public void deleteUser(User user) {
        this.clients.remove(user);
    }

    /**
     * добавить счёт пользователю.
     * @param user user.
     * @param account account.
     */
    public void addAccountToUser(User user, Account account) {
        if (user != null && account != null) {
            if (!this.clients.containsKey(user)) {
                this.clients.put(user, new ArrayList<Account>());
            }

            if (findUser(account) == null) {
                this.clients.get(user).add(account);
            }
        }
    }

    /**
     * Найти юзера по счеу.
     * @param account account.
     * @return user.
     */
    private User findUser(Account account) {
        User result = null;
        for (Map.Entry<User, List<Account>> entry: clients.entrySet()) {
            if (entry.getValue().contains(account)) {
                result = entry.getKey();
            }
        }
        return result;
    }

    /**
     * удалить один счёт пользователя.
     * @param user user.
     * @param account account.
     */
    public void deleteAccountFromUser(User user, Account account) {
        if (containsUserWithAccount(user, account)) {
            this.clients.get(user).remove(account);
        }
    }

    /**
     * получить список счетов для пользователя.
     * @param user user.
     * @return List<Accounts>.
     */
    public List<Account> getUserAccounts(User user) {
        return this.clients.get(user);
    }

    /**
     * метод для перечисления денег с одного счёта на другой счёт:
     * если счёт не найден или не хватает денег на счёте srcAccount (с которого переводят)
     * должен вернуть false.
     * @param srcUser srcUser.
     * @param srcAccount srcAccount.
     * @param dstUser dstUser.
     * @param dstAccount dstAccount.
     * @param amount amount.
     * @return boolean.
     */
    public boolean transferMoney(User srcUser, Account srcAccount, User dstUser, Account dstAccount, double amount) {
        boolean result = false;

        if (containsUserWithAccount(srcUser, srcAccount)) {
            if (!containsUserWithAccount(dstUser, dstAccount)) {
                addAccountToUser(dstUser, dstAccount);
            }
            if (containsUserWithAccount(dstUser, dstAccount) && srcAccount.withdrawValue(amount)) {
                result = dstAccount.addValue(amount);
            }
        }

        return result;
    }

    /**
     * Проверка нахождения клиента со счетом в базе.
     * @param user user.
     * @param account account.
     * @return boolean.
     */
    public boolean containsUserWithAccount(User user, Account account) {
        boolean result = false;
        if (this.clients.containsKey(user) && this.clients.get(user).contains(account)) {
            result = true;
        }
        return result;
    }




}
