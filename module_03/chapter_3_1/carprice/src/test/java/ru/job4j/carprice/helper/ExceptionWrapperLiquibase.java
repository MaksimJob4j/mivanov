package ru.job4j.carprice.helper;

import liquibase.exception.LiquibaseException;

public class ExceptionWrapperLiquibase implements Wrapper<LiquibaseException> {
    private LiquibaseException exception = null;

    @Override
    public LiquibaseException get() {
        LiquibaseException result = exception;
        exception = null;
        return result;
    }

    @Override
    public void set(LiquibaseException exception) {
        this.exception = exception;
    }

    @Override
    public boolean isEmpty() {
        return exception == null;
    }
}
