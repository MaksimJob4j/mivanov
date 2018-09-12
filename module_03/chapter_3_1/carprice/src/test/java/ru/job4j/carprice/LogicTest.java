package ru.job4j.carprice;

import org.junit.Test;
import ru.job4j.carprice.dao.*;
import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.User;
import ru.job4j.carprice.items.description.*;
import ru.job4j.carprice.store.*;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LogicTest {
    private final Logic logic = Logic.getInstance();

    @Test
    public void testFindCars() {
        new TestHelper<Car>().testFindEntities(
                10,
                new Car(),
                logic -> {
                    try {
                        return (List<Car>) logic.findCars();
                    } catch (StoreException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    @Test
    public void testFindCarsFilter() {
        try {
            Car car1 = new Car();
            Car car2 = new Car();
            Car car3 = new Car();

            Brand brand1 = new TestHelper<Brand>().create(new Brand());
            Brand brand2 = new TestHelper<Brand>().create(new Brand());
            Model model1 = new Model();
            model1.setBrand(brand1);
            Model model2 = new Model();
            model2.setBrand(brand1);
            Model model3 = new Model();
            model3.setBrand(brand2);
            new TestHelper<Model>().create(model1);
            new TestHelper<Model>().create(model2);
            new TestHelper<Model>().create(model3);
            car1.setModel(model1);
            car2.setModel(model2);
            car3.setModel(model3);
            ZonedDateTime dateTime1 = ZonedDateTime.now();
            ZonedDateTime dateTime2 = dateTime1.minusDays(2);
            ZonedDateTime dateTime3 = dateTime1.minusSeconds(1);
            car1.setDateCreated(dateTime1);
            car2.setDateCreated(dateTime2);
            car3.setDateCreated(dateTime3);
            logic.createCar(car1);
            logic.createCar(car2);
            logic.createCar(car3);
            Photo photo = new Photo();
            photo.setCar(car2);
            logic.createPhoto(photo);
            List<Car> filter0 = logic.findCars(0, false, false);
            assertTrue(filter0.contains(car1));
            assertTrue(filter0.contains(car2));
            assertTrue(filter0.contains(car3));
            List<Car> filterBr1 = logic.findCars(brand1.getId(), false, false);
            assertTrue(filterBr1.contains(car1));
            assertTrue(filterBr1.contains(car2));
            assertFalse(filterBr1.contains(car3));
            List<Car> filterBr2 = logic.findCars(brand2.getId(), false, false);
            assertFalse(filterBr2.contains(car1));
            assertFalse(filterBr2.contains(car2));
            assertTrue(filterBr2.contains(car3));
            List<Car> filterDate = logic.findCars(0, true, false);
            assertTrue(filterDate.contains(car1));
            assertFalse(filterDate.contains(car2));
            assertTrue(filterDate.contains(car3));
            List<Car> filterPhoto = logic.findCars(0, false, true);
            assertFalse(filterPhoto.contains(car1));
            assertTrue(filterPhoto.contains(car2));
            assertFalse(filterPhoto.contains(car3));
            List<Car> filterBr1Photo = logic.findCars(brand1.getId(), false, true);
            assertFalse(filterBr1Photo.contains(car1));
            assertTrue(filterBr1Photo.contains(car2));
            assertFalse(filterBr1Photo.contains(car3));
            List<Car> filterBr2Photo = logic.findCars(brand2.getId(), false, true);
            assertFalse(filterBr2Photo.contains(car1));
            assertFalse(filterBr2Photo.contains(car2));
            assertFalse(filterBr2Photo.contains(car3));
            List<Car> filterBr1Date = logic.findCars(brand1.getId(), true, false);
            assertTrue(filterBr1Date.contains(car1));
            assertFalse(filterBr1Date.contains(car2));
            assertFalse(filterBr1Date.contains(car3));
            List<Car> filterBr2Date = logic.findCars(brand2.getId(), true, false);
            assertFalse(filterBr2Date.contains(car1));
            assertFalse(filterBr2Date.contains(car2));
            assertTrue(filterBr2Date.contains(car3));
        } catch (StoreException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findCar() {
        Car car = new TestHelper<Car>().create(new Car());
        Car newCar = null;
        try {
            newCar = logic.findCar(car.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newCar);
        assertEquals(newCar, car);
    }

    @Test
    public void findCarStringId() {
        Car car = new TestHelper<Car>().create(new Car());
        Car newCar = null;
        try {
            newCar = logic.findCar(car.getId().toString());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertEquals(newCar, car);
    }

    @Test
    public void findBrands() {
        new TestHelper<Brand>().testFindEntities(
                10,
                new Brand(),
                logic -> {
                    try {
                        return logic.findBrands();
                    } catch (StoreException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    @Test
    public void findModels() {
        new TestHelper<Model>().testFindEntities(
                10,
                new Model(),
                logic -> {
                    try {
                        return logic.findModels();
                    } catch (StoreException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    @Test
    public void findBodies() {
        new TestHelper<Body>().testFindEntities(
                10,
                new Body(),
                logic -> {
                    try {
                        return logic.findBodies();
                    } catch (StoreException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    @Test
    public void findColors() {
        new TestHelper<Color>().testFindEntities(
                10,
                new Color(),
                logic -> {
                    try {
                        return logic.findColors();
                    } catch (StoreException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    @Test
    public void findTransmissions() {
        new TestHelper<Transmission>().testFindEntities(
                10,
                new Transmission(),
                logic -> {
                    try {
                        return logic.findTransmissions();
                    } catch (StoreException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    @Test
    public void findEngines() {
        new TestHelper<Engine>().testFindEntities(
                10,
                new Engine(),
                logic -> {
                    try {
                        return logic.findEngines();
                    } catch (StoreException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    @Test
    public void findDrives() {
        new TestHelper<Drive>().testFindEntities(
                10,
                new Drive(),
                logic -> {
                    try {
                        return logic.findDrives();
                    } catch (StoreException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    @Test
    public void findModel() {
        Model model = new TestHelper<Model>().create(new Model());
        Model newModel = null;
        try {
            newModel = logic.findModel(model.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newModel);
        assertEquals(newModel, model);
    }

    @Test
    public void findModelStringId() {
        Model model = new TestHelper<Model>().create(new Model());
        Model newModel = null;
        try {
            newModel = logic.findModel(model.getId().toString());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newModel);
        assertEquals(newModel, model);
    }

    @Test
    public void findBody() {
        Body body = new TestHelper<Body>().create(new Body());
        Body newBody = null;
        try {
            newBody = logic.findBody(body.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newBody);
        assertEquals(newBody, body);
    }

    @Test
    public void findBodyStringId() {
        Body body = new TestHelper<Body>().create(new Body());
        Body newBody = null;
        try {
            newBody = logic.findBody(body.getId().toString());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertEquals(newBody, body);
    }

    @Test
    public void findColor() {
        Color color = new TestHelper<Color>().create(new Color());
        Color newColor = null;
        try {
            newColor = logic.findColor(color.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newColor);
        assertEquals(newColor, color);
    }

    @Test
    public void findColorStringId() {
        Color color = new TestHelper<Color>().create(new Color());
        Color newColor = null;
        try {
            newColor = logic.findColor(color.getId().toString());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newColor);
        assertEquals(newColor, color);
    }

    @Test
    public void findTransmission() {
        Transmission transmission = new TestHelper<Transmission>().create(new Transmission());
        Transmission newTransmission = null;
        try {
            newTransmission = logic.findTransmission(transmission.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newTransmission);
        assertEquals(newTransmission, transmission);
    }

    @Test
    public void findTransmission1() {
        Transmission transmission = new TestHelper<Transmission>().create(new Transmission());
        Transmission newTransmission = null;
        try {
            newTransmission = logic.findTransmission(transmission.getId().toString());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newTransmission);
        assertEquals(newTransmission, transmission);
    }

    @Test
    public void findEngine() {
        Engine engine = new TestHelper<Engine>().create(new Engine());
        Engine newEngine = null;
        try {
            newEngine = logic.findEngine(engine.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newEngine);
        assertEquals(newEngine, engine);
    }

    @Test
    public void findEngineStringId() {
        Engine engine = new TestHelper<Engine>().create(new Engine());
        Engine newEngine = null;
        try {
            newEngine = logic.findEngine(engine.getId().toString());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newEngine);
        assertEquals(newEngine, engine);
    }

    @Test
    public void findDrive() {
        Drive drive = new TestHelper<Drive>().create(new Drive());
        Drive newDrive = null;
        try {
            newDrive = logic.findDrive(drive.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newDrive);
        assertEquals(newDrive, drive);
    }

    @Test
    public void findDriveStringId() {
        Drive drive = new TestHelper<Drive>().create(new Drive());
        Drive newDrive = null;
        try {
            newDrive = logic.findDrive(drive.getId().toString());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newDrive);
        assertEquals(newDrive, drive);
    }

    @Test
    public void createCar() {
        Car car = new Car();
        int size = 0;
        int newSize = 0;
        Integer id = car.getId();
        try {
            size = new HibStore<>(Car.class).find().size();
            logic.createCar(car);
            newSize = new HibStore<>(Car.class).find().size();
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertThat(size + 1, is(newSize));
        assertNotEquals(id, car.getId());
        Car newCar = null;
        try {
            newCar = new HibStore<>(Car.class).find(car.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertEquals(newCar, car);
    }

    @Test
    public void findUsersCars() {
        User user = new TestHelper<User>().create(new User());
        Car car = new Car();
        car.setOwner(user);
        new TestHelper<Car>().create(car);
        List<Car> cars = new ArrayList<>();
        try {
            cars = logic.findUsersCars(user.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertTrue(cars.contains(car));
    }

    @Test
    public void isCredential() {
        boolean credential = true;
        String login = "TestUser" + new Date().getTime();
        String pas = "TestPas" + new Date().getTime();
        User user = new User();
        user.setLogin(login);
        user.setPassword(pas);
        try {
            credential = logic.isCredential(login, pas);
            assertFalse(credential);
            logic.createUser(user);
            credential = logic.isCredential(login, pas);
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertTrue(credential);
    }

    @Test
    public void findUserByLogin() {
        String login = "TestUser" + new Date().getTime();
        User user = new User();
        user.setLogin(login);
        User newUser = null;
        try {
            newUser = logic.findUserByLogin(login);
            assertNull(newUser);
            logic.createUser(user);
            newUser = logic.findUserByLogin(login);
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newUser);
        assertEquals(newUser, user);
    }

    @Test
    public void createUser() {
        User user = new User();
        assertNull(user.getId());
        try {
            logic.createUser(user);
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(user.getId());
    }

    @Test
    public void findModelsByBrand() {
        Brand brand = new TestHelper<Brand>().create(new Brand());
        Model model = new Model();
        model.setBrand(brand);
        new TestHelper<Model>().create(model);
        List<Model> models = new ArrayList<>();
        try {
            models = logic.findModelsByBrand(brand.getId().toString());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertTrue(models.contains(model));
    }

    @Test
    public void createPhoto() {
        Photo photo = new Photo();
        assertNull(photo.getId());
        try {
            logic.createPhoto(photo);
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(photo.getId());
    }

    @Test
    public void findPhoto() {
        Photo photo = new TestHelper<Photo>().create(new Photo());
        Photo newPhoto = null;
        try {
            newPhoto = logic.findPhoto(photo.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(newPhoto);
        assertEquals(newPhoto, photo);
    }

    @Test
    public void findPhotoStringId() {
        Photo photo = new TestHelper<Photo>().create(new Photo());
        Photo newPhoto = null;
        try {
            newPhoto = logic.findPhoto(photo.getId().toString());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertEquals(newPhoto, photo);
    }

    @Test
    public void findPhotoByCar() {
        Car car = new TestHelper<Car>().create(new Car());
        Photo photo = new Photo();
        photo.setCar(car);
        new TestHelper<Photo>().create(photo);
        List<Photo> photos = new ArrayList<>();
        try {
            photos = logic.findPhotoByCar(car.getId());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertTrue(photos.contains(photo));
    }

    @Test
    public void findPhotoByCarStringId() {
        Car car = new TestHelper<Car>().create(new Car());
        Photo photo = new Photo();
        photo.setCar(car);
        new TestHelper<Photo>().create(photo);
        List<Photo> photos = new ArrayList<>();
        try {
            photos = logic.findPhotoByCar(car.getId().toString());
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertTrue(photos.contains(photo));
    }

    @Test
    public void update() {
        Car car = new TestHelper<Car>().create(new Car());
        Car newCar = null;
        try {
            newCar = logic.findCar(car.getId());
            assertNull(newCar.getPrice());
            car.setPrice(100);
            assertNull(newCar.getPrice());
            logic.update(car);
            assertNull(newCar.getPrice());
            newCar = logic.findCar(car.getId());
            assertNotNull(newCar.getPrice());
        } catch (StoreException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createCarFromParameters() {
        Map<String, String> parameters = new HashMap<>();
        Car carNull = null;
        try {
            carNull = logic.createCarFromParameters(parameters, null);
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(carNull.getId());
        Model model = new TestHelper<Model>().create(new Model());
        parameters.put("model", model.getId().toString());
        Integer year = 2000;
        parameters.put("year", year.toString());
        Integer mileage = 5000;
        parameters.put("mileage", mileage.toString());
        Body body = new TestHelper<Body>().create(new Body());
        parameters.put("body", body.getId().toString());
        Color color = new TestHelper<Color>().create(new Color());
        parameters.put("color", color.getId().toString());
        Float volume = 1.2f;
        parameters.put("volume", volume.toString());
        Transmission transmission = new TestHelper<Transmission>().create(new Transmission());
        parameters.put("transmission", transmission.getId().toString());
        Engine engine = new TestHelper<Engine>().create(new Engine());
        parameters.put("engine", engine.getId().toString());
        Drive drive = new TestHelper<Drive>().create(new Drive());
        parameters.put("drive", drive.getId().toString());
        Boolean rightWheel = true;
        parameters.put("rightWheel", rightWheel.toString());
        Boolean broken = true;
        parameters.put("broken", broken.toString());
        Integer ownersNum = 3;
        parameters.put("ownersNum", ownersNum.toString());
        String vin = "some vin";
        parameters.put("vin", vin);
        Integer power = 50;
        parameters.put("power", power.toString());
        String address = "some address";
        parameters.put("address", address);
        Integer price = 100;
        parameters.put("price", price.toString());
        User user = new TestHelper<User>().create(new User());
        Car car = null;
        try {
            car = logic.createCarFromParameters(parameters, user);
        } catch (StoreException e) {
            e.printStackTrace();
        }
        assertNotNull(car.getId());
        assertThat(car.getModel(), is(model));
        assertThat(car.getYear(), is(year));
        assertThat(car.getMileage(), is(mileage));
        assertThat(car.getBody(), is(body));
        assertThat(car.getColor(), is(color));
        assertThat(car.getVolume(), is(volume));
        assertThat(car.getTransmission(), is(transmission));
        assertThat(car.getEngine(), is(engine));
        assertThat(car.getDrive(), is(drive));
        assertThat(car.getRightWheel(), is(rightWheel));
        assertThat(car.getBroken(), is(broken));
        assertThat(car.getOwnersNum(), is(ownersNum));
        assertThat(car.getVin(), is(vin));
        assertThat(car.getPower(), is(power));
        assertThat(car.getAddress(), is(address));
        assertThat(car.getPrice(), is(price));
        assertThat(car.getOwner(), is(user));
    }

    class TestHelper<T> {
        T create(T entity) {
            try {
                new HibStore(entity.getClass()).create(entity);
            } catch (StoreException e) {
                e.printStackTrace();
            }
            return entity;
        }

        void testFindEntities(int quantity, T entity, final Function<Logic, List<T>> command) {
            List<T> entities = new ArrayList<>();
            HibStore<T> hibStore = new HibStore(entity.getClass());
            try {
                for (int i = 0; i < quantity; i++) {
                    T newEntity = (T) entity.getClass().newInstance();
                    entities.add(newEntity);
                    hibStore.create(newEntity);
                }
            } catch (InstantiationException | IllegalAccessException | StoreException e) {
                e.printStackTrace();
            }
            List<T> list = command.apply(logic);
            for (T t : entities) {
                assertTrue(list.contains(t));
            }
        }
    }
}