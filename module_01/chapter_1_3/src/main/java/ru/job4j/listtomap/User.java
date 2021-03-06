package ru.job4j.listtomap;

/**
 * .
 */
public class User {
    /**
     * .
     */
    private Integer id;
    /**
     * .
     */
    private String name;
    /**
     * .
     */
    private String city;

    /**
     *
     * @param id id.
     * @param name name.
     * @param city city.
     */
    public User(Integer id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    /**
     *
     * @return Integer.
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return String.
     */
    public String getCity() {
        return city;
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

        if (id != null ? !id.equals(user.id) : user.id != null) {
            return false;
        }
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        return city != null ? city.equals(user.city) : user.city == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
