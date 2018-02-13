package ru.job4j.threads;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ModelsStorageTest {
    @Test
    public void add() throws Exception {
        Model model = new Model();
        model.version = 1;
        model.id = "M1";
        model.name = "MODEL";

        ModelsStorage modelsStorage = new ModelsStorage();
        modelsStorage.add(model);

        assertThat(modelsStorage.models.get("M1"), is(model));

    }

    @Test
    public void update() throws Exception {
        Model model = new Model();
        model.version = 1;
        model.id = "M1";
        model.name = "MODEL";

        ModelsStorage modelsStorage = new ModelsStorage();
        modelsStorage.add(model);

        Model newModel = new Model();
        newModel.version = 2;
        newModel.id = "M1";
        newModel.name = "MODEL2";
        try {
            modelsStorage.update(newModel);
        } catch (OplimisticException e) {
            System.out.println("Получено исключение при НЕсовпадении версий");
            e.printStackTrace();
        }

        assertThat(modelsStorage.models.get("M1").name.equals("MODEL"), is(true));
        assertThat(modelsStorage.models.get("M1").name.equals("MODEL2"), is(false));

        newModel.version = 1;
        try {
            modelsStorage.update(newModel);
        } catch (OplimisticException e) {
            System.out.println("Получено исключение при совпадении версий");
            e.printStackTrace();
        }

        assertThat(modelsStorage.models.get("M1").name.equals("MODEL"), is(false));
        assertThat(modelsStorage.models.get("M1").name.equals("MODEL2"), is(true));

    }

    @Test
    public void delete() throws Exception {
        Model model = new Model();
        model.version = 1;
        model.id = "M1";
        model.name = "MODEL";

        ModelsStorage modelsStorage = new ModelsStorage();
        modelsStorage.delete(model);

        assertThat(modelsStorage.models.get("M1") == null, is(true));
    }

}