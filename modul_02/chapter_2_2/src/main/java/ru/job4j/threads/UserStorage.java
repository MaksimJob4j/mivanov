package ru.job4j.threads;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {

    @GuardedBy("users")
    private Map<Integer, User> users  = new HashMap<>();

    /**
     * Добавляет User в UserStorage.
     * User добавляется если данного id User нет в UserStorage.
     * @param user User.
     * @return true если User добавлен.
     */
    @GuardedBy(value = "users")
    synchronized boolean add(User user) {
        boolean result = false;
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            result = true;
        }
        return result;
    };

    /**
     * Обновляет User в UserStorage.
     * User обновляется если данный id User есть в UserStorage.
     * @param user User.
     * @return true если User обновлен.
     */
    @GuardedBy(value = "users")
    synchronized boolean update(User user) {
        boolean result = false;
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            result = true;
        }
        return result;
    };

    /**
     * Удаляет User из UserStorage если он там есть.
     * @param user User.
     * @return true если User удален.
     */
    @GuardedBy(value = "users")
    synchronized boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    /**
     * Перевод между денег пользователями.
     * @param fromId ID отправителя.
     * @param toId ID получателя.
     * @param amount сумма.
     * @return true если перевод состояля (если в базе есть такие ID и хватило денег).
     */
    @GuardedBy(value = "users")
    synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (users.get(fromId) != null && users.get(toId) != null && users.get(fromId).getAmount() >= amount) {
            users.put(fromId, new User(fromId, users.get(fromId).getAmount() - amount));
            users.put(toId, new User(toId, users.get(fromId).getAmount() + amount));
            result = true;
        }
        return result;
    }


}
