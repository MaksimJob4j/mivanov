package ru.job4j.map;

import java.util.Calendar;

/**
 * User.
 */
public class User {

    /**
     * name.
     */
    private String name;
    /**
     * children.
     */
    private int children;
    /**
     * birthday.
     */
    private Calendar birthday;

    /**
     * get.
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     * set.
     * @param name String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get.
     * @return int.
     */
    public int getChildren() {
        return children;
    }

    /**
     * set.
     * @param children int.
     */
    public void setChildren(int children) {
        this.children = children;
    }

    /**
     * get.
     * @return Calendar.
     */
    public Calendar getBirthday() {
        return birthday;
    }

    /**
     * set.
     * @param birthday Calendar.
     */
    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    /**
     * Конструктор.
     * @param name String.
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Конструктор.
     * @param name String.
     * @param birthday Calendar.
     */
    public User(String name, Calendar birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    /**
     * Конструктор.
     * @param name String.
     * @param birthday Calendar.
     * @param children int.
     */
    public User(String name, Calendar birthday, int children) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
