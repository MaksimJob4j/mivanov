package ru.job4j.ood.templates;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleGeneratorRegExTest {

    @Test
    public void whenEverythingIsFineThenGetTheCorrectString() throws KeyTemplateException {
        Template generator = new SimpleGeneratorRegEx();
        Map<String, String> data = new HashMap<>();
        assertThat(generator.generate("", data), is(""));
        assertThat(generator.generate("ab ccd", data), is("ab ccd"));
        assertThat(generator.generate("ab ccd$", data), is("ab ccd$"));
        assertThat(generator.generate("ab c $cd", data), is("ab c $cd"));
        assertThat(generator.generate("ab c ${cd", data), is("ab c ${cd"));
        data.put("name", "Max");
        assertThat(generator.generate("${name}", data), is("Max"));
        assertThat(generator.generate("${name} ${name} ${name}", data), is("Max Max Max"));
        assertThat(generator.generate("My name is ${name}.", data), is("My name is Max."));
        data.put("age", "17");
        assertThat(generator.generate("${name}'s age is ${age}", data), is("Max's age is 17"));
    }

    @Test(expected = KeyTemplateException.class)
    public void whenThereIsNoKeyInTheDataThenExceptionIsExpected() throws KeyTemplateException {
        Template generator = new SimpleGeneratorRegEx();
        Map<String, String> data = new HashMap<>();
        assertThat(generator.generate("${name}", data), is("Max"));
    }

    @Test(expected = KeyTemplateException.class)
    public void whenThereAreExtraKeyInTheDataThenExceptionIsExpected() throws KeyTemplateException {
        Template generator = new SimpleGeneratorRegEx();
        Map<String, String> data = new HashMap<>();
        data.put("name", "Max");
        data.put("age", "17");
        assertThat(generator.generate("${name}", data), is("Max"));
    }

}