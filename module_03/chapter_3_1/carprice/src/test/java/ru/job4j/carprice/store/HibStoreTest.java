package ru.job4j.carprice.store;

/*
import org.junit.Test;
import ru.job4j.carprice.items.Body;
import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.Engine;
import ru.job4j.carprice.items.Transmission;
import ru.job4j.carprice.dao.*;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
*/

public class HibStoreTest {
/*
    @Test
    public void hibStoreTest() throws Exception {

        Body body = new Body();
        body.setName("TestBody");
        DAO<Body> bodyDAO = new HibStore(Body.class);
        bodyDAO.create(body);

        Engine engine = new Engine();
        engine.setName("TestEngine");
        DAO<Engine> engineDAO = new HibStore(Engine.class);
        engineDAO.create(engine);

        Transmission transmission = new Transmission();
        transmission.setName("TestTransmission");
        DAO<Transmission> transmissionDAO = new HibStore(Transmission.class);
        transmissionDAO.create(transmission);


        DAO<Car> carDAO = new HibStore(Car.class);

        List<Car> list = (List<Car>) carDAO.find();
        Integer size = list.size();

        Car car = new Car();
        car.setModel("TestCar");
        car.setBody(body);
        car.setEngine(engine);
        car.setTransmission(transmission);
        carDAO.create(car);

        list = (List<Car>) carDAO.find();
        assertThat(list.size() - size, is(1));

        assertThat(car.equals(carDAO.find(car.getId())), is(true));
        assertThat(car.equals(carDAO.find(car.getId() + 1)), is(false));

        Car car1 = carDAO.find(car.getId());
        assertThat(car1.getModel(), is("TestCar"));
        assertThat(car1.getBody().getName(), is("TestBody"));
        assertThat(car1.getTransmission().getName(), is("TestTransmission"));
        assertThat(car1.getEngine().getName(), is("TestEngine"));

        engine.setName("NewTestEngine");
        engineDAO.update(engine);
        Car car2 = carDAO.find(car.getId());
        assertThat(car2.getEngine().getName(), is("NewTestEngine"));

        carDAO.delete(car);
        bodyDAO.delete(body);
        engineDAO.delete(engine);
        transmissionDAO.delete(transmission);

        assertThat(carDAO.find(car.getId()) == null, is(true));
    }
*/
}