package ru.job4j.companystructure;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест CompanyStructure.
 */
public class CompanyStructureTest {

    /**
     * Тест на прямую сортировку.
     */
    @Test
    public void whenSetArrayThenGetSortedIncreaseSetArray() {
        CompanyStructure companyStructure = new CompanyStructure();

        String[] strings = new String[10];
        int i = 0;
        strings[i++] = "K2\\SK1\\SSK2";
        strings[i++] = "K2\\SK1\\SSK1";
        strings[i++] = "K1\\SK2";
        strings[i++] = "";
        strings[i++] = "";
        strings[i++] = "K1\\SK1\\SSK1";
        strings[i++] = "K1\\SK1\\SSK2\\SSSK1\\SSSSK1";
        strings[i++] = "K1\\SK1\\SSK2\\SSSK1\\SSSSK1";
        strings[i++] = "K2";
        strings[i++] = "K2\\SK1\\SSK2";

        StringBuilder stringBuilder = new StringBuilder();
        for (String s: companyStructure.sortIncrease(strings)) {
            stringBuilder.append(s);
        }

        String result = stringBuilder.toString();

        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("K1");
        stringBuilder1.append("K1\\SK1");
        stringBuilder1.append("K1\\SK1\\SSK1");
        stringBuilder1.append("K1\\SK1\\SSK2");
        stringBuilder1.append("K1\\SK1\\SSK2\\SSSK1");
        stringBuilder1.append("K1\\SK1\\SSK2\\SSSK1\\SSSSK1");
        stringBuilder1.append("K1\\SK2");
        stringBuilder1.append("K2");
        stringBuilder1.append("K2\\SK1");
        stringBuilder1.append("K2\\SK1\\SSK1");
        stringBuilder1.append("K2\\SK1\\SSK2");

        String expected = stringBuilder1.toString();

        assertThat(result, is(expected));
    }


    /**
     * Тест на обратную сортировку.
     */
    @Test
    public void whenSetArrayThenGetSortedDecreaseSetArray() {
        CompanyStructure companyStructure = new CompanyStructure();

        String[] strings = new String[10];
        int i = 0;
        strings[i++] = "K2\\SK1\\SSK2";
        strings[i++] = "K2\\SK1\\SSK1";
        strings[i++] = "K1\\SK2";
        strings[i++] = "";
        strings[i++] = "";
        strings[i++] = "K1\\SK1\\SSK1";
        strings[i++] = "K1\\SK1\\SSK2\\SSSK1\\SSSSK1";
        strings[i++] = "K1\\SK1\\SSK2\\SSSK1\\SSSSK1";
        strings[i++] = "K2";
        strings[i++] = "K2\\SK1\\SSK2";

        StringBuilder stringBuilder = new StringBuilder();
        for (String s: companyStructure.sortDecrease(strings)) {
            stringBuilder.append(s);
        }

        String result = stringBuilder.toString();

        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("K2");
        stringBuilder1.append("K2\\SK1");
        stringBuilder1.append("K2\\SK1\\SSK2");
        stringBuilder1.append("K2\\SK1\\SSK1");
        stringBuilder1.append("K1");
        stringBuilder1.append("K1\\SK2");
        stringBuilder1.append("K1\\SK1");
        stringBuilder1.append("K1\\SK1\\SSK2");
        stringBuilder1.append("K1\\SK1\\SSK2\\SSSK1");
        stringBuilder1.append("K1\\SK1\\SSK2\\SSSK1\\SSSSK1");
        stringBuilder1.append("K1\\SK1\\SSK1");

        String expected = stringBuilder1.toString();

        assertThat(result, is(expected));
    }



}
