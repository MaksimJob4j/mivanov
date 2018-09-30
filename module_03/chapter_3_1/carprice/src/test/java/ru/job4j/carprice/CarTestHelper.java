package ru.job4j.carprice;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.internal.SessionImpl;
import ru.job4j.carprice.helper.ExceptionWrapperLiquibase;
import ru.job4j.carprice.store.HibernateUtil;

import java.sql.Connection;

public class CarTestHelper {
    private static final CarTestHelper INSTANCE = new CarTestHelper();
    private static boolean updated;

    private CarTestHelper() {
    }

    public static CarTestHelper getInstance() {
        return INSTANCE;
    }

    public void updateTestBase() throws LiquibaseException {
        if (!updated) {
            ExceptionWrapperLiquibase ex = new ExceptionWrapperLiquibase();
            HibernateUtil.ses(session -> {
                        try {
                            Connection connection = ((SessionImpl) session.getSession()).connection();
                            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                            Liquibase liquibase = new liquibase.Liquibase("liquibase/db.changelog-0.1.3-test.xml", new ClassLoaderResourceAccessor(), database);
                            liquibase.update(new Contexts(), new LabelExpression());
                        } catch (LiquibaseException e) {
                            ex.set(e);
                        }

                    }
            );
            if (!ex.isEmpty()) {
                throw ex.get();
            }
            updated = true;
        }
    }
}

