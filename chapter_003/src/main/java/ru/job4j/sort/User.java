package ru.job4j.sort;

/**
 * user.
 */
public class User implements Comparable<User> {
    /**
     * name.
     */
    private String name;
    /**
     * age.
     */
    private int age;
    /**
     * .
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * .
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * .
     * @return age.
     */
    public int getAge() {
        return age;
    }

    /**
     * .
     * @param age age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * .
     * @param name name.
     * @param age age.
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", age=" + age
                + '}';
    }

    @Override
    public int compareTo(User o) {
        int rsl;
        if (this == o) {
            rsl = 0;
        } else if (o == null) {
            rsl = 1;
        } else {
            rsl = this.age - o.age == 0 ? this.name.compareTo(o.name) : this.age - o.age;
        }
        return rsl;
    }
}



