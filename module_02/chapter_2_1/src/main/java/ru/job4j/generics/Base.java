package ru.job4j.generics;


/**
 * Base.
 */
public abstract class Base {
    /**
     * id.
     */
    private String id;

    /**
     * getId.
     * @return String.
     */
    String getId() {
        return id;
    }

    /**
     * setId.
     * @param id String.
     */
    void setId(String id) {
        this.id = id;
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Base base = (Base) o;

        return id.equals(base.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
