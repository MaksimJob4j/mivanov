package ru.job4j.bank;

/**
 * User.
 */
public class User {
    /**
     * name.
     */
    private String name;
    /**
     * passport.
     */
    private String passport;

    /**
     *
     * @param name name.
     * @param passport passport.
     */
    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    /**
     * getName.
     * @return name.
     */
    public String getName() {
        return name;
    }


    /**
     * getPassport.
     * @return passport.
     */
    public String getPassport() {
        return passport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!name.equals(user.name)) {
            return false;
        }
        return passport.equals(user.passport);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + passport.hashCode();
        return result;
    }
}
