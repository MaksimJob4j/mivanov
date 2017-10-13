package ru.job4j.companystructure;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * Структура компании.
 */
public class CompanyStructure {
    /**
     * Множество отделов.
     */
    private TreeSet<Division> divisions;

    /**
     * Прямая сортировка.
     * @param strings Входящие сроки.
     * @return отсортированые строки.
     */
    public String[] sortIncrease(String[] strings) {
        this.divisions = new TreeSet<>();
        for (String string: strings) {
            Division division = new Division();
            String div = "";
            for (char c: string.toCharArray()) {
                if (c == '\\') {
                    division.getNames().add(div);
                    div = "";
                } else {
                    div += c;
                }
            }
            if (!div.isEmpty()) {
                division.getNames().add(div);
                this.divisions.add(division);
            }
        }

        Division division = this.divisions.first();
        while (division != null) {
            if (!check(this.divisions.lower(division), division)) {
                this.divisions.add(new Division(division.getNames().subList(0, division.getNames().size() - 1)));
                division = this.divisions.lower(division);
            } else {
                division = this.divisions.higher(division);
            }
        }

        return divisionSetToStringArray(this.divisions);
    }

    /**
     * Проверка, что не пропущена строка с отделом.
     * @param first первый отдел.
     * @param second следующий отдел.
     * @return boolean.
     */
    private boolean check(Division first, Division second) {
        boolean result = true;
        if (first == null) {
            if (second.getNames().size() != 1) {
                result = false;
            }
        } else  if (second.getNames().size() - first.getNames().size() > 1) {
            result = false;
        } else {
            for (int i = 0; i < second.getNames().size() - 1; i++) {
                if (!first.getNames().get(i).equals(second.getNames().get(i))) {
                    result = false;
                }
            }

        }
        return result;
    }

    /**
     * Перевод множества отделов в массив строк.
     * @param divisions отделы.
     * @return строки отделов.
     */
    private String[] divisionSetToStringArray(TreeSet<Division> divisions) {
        String[] returnArray = new String[divisions.size()];
        int i = 0;
        for (Division div: divisions) {
            returnArray[i++] = div.toString();
        }
        return returnArray;
    }

    /**
     * Обратная сортировка.
     * @param strings Входящие сроки.
     * @return отсортированые строки.
     */
    public String[] sortDecrease(String[] strings) {
        sortIncrease(strings);
        TreeSet<Division> set = new TreeSet<Division>(decreaseComparator);
        set.addAll(this.divisions);
        return divisionSetToStringArray(set);
    }

    /**
     * decreaseComparator.
     */
    private Comparator<Division> decreaseComparator = new Comparator<Division>() {
        @Override
        public int compare(Division o1, Division o2) {

            int rsl = 0;

            int o1size = o1.getNames().size();
            int o2size = o2.getNames().size();

            int i = 0;
            while (rsl == 0 && i < Math.min(o1size, o2size)) {
                rsl = -o1.getNames().get(i).compareTo(o2.getNames().get(i));
                i++;
            }

            if (rsl == 0) {
                rsl = o1.getNames().size() - o2.getNames().size();
            }
            return rsl;
        }
    };
}
