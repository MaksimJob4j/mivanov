package ru.job4j.companystructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Отдел.
 */
public class Division implements Comparable<Division> {
    /**
     * Иерархия отдела.
     */
    private List<String> names = new ArrayList<>();

    /**
     * Конструктор.
     */
    public Division() {
    }

    /**
     * Конструктор.
     * @param names names.
     */
    public Division(List<String> names) {
        this.names = names;
    }

    /**
     * .
     * @return name.
     */
    public List<String> getNames() {
        return names;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.names.size() - 1; i++) {
            stringBuilder.append(this.names.get(i));
            stringBuilder.append("\\");
        }
        stringBuilder.append(this.names.get(this.names.size() - 1));

        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Division o) {
        int rsl = 0;

        int thisSize = this.names.size();
        int oSize = o.getNames().size();

        int i = 0;
        while (rsl == 0 && i < Math.min(thisSize, oSize)) {
            rsl = this.names.get(i).compareTo(o.getNames().get(i));
            i++;
        }

        if (rsl == 0) {
            rsl = this.names.size() - o.getNames().size();
        }
        return rsl;
    }

}
