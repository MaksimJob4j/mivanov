package ru.job4j.symbols;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SymbolsTest {
    @Test
    public void compareStringsTest() throws Exception {
        Symbols symbols = new Symbols();
        assertThat(symbols.compareStrings(null, null), is(true));
        assertThat(symbols.compareStrings(null, ""), is(false));
        assertThat(symbols.compareStrings("", ""), is(true));
        assertThat(symbols.compareStrings("a", ""), is(false));
        assertThat(symbols.compareStrings("a", "A"), is(false));
        assertThat(symbols.compareStrings("a", "a"), is(true));
        assertThat(symbols.compareStrings("add", "dad"), is(true));
        assertThat(symbols.compareStrings("addd", "dad"), is(false));
        assertThat(symbols.compareStrings("add", "dadd"), is(false));
        assertThat(symbols.compareStrings("ada", "dad"), is(false));
        assertThat(symbols.compareStrings("ada", "adad"), is(false));
        assertThat(symbols.compareStrings("ada", "adaq"), is(false));
        assertThat(symbols.compareStrings("adaq", "ada"), is(false));
    }

}