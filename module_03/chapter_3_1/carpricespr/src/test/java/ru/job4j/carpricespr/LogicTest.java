package ru.job4j.carpricespr;

import liquibase.exception.LiquibaseException;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.carpricespr.items.*;
import ru.job4j.carpricespr.items.description.*;
import ru.job4j.carpricespr.dao.StoreException;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LogicTest {
    private final Logic logic = Logic.getInstance();

    @BeforeClass
    public static void setUpClass() throws LiquibaseException {
        CarTestHelper.getInstance().updateTestBase();
    }

    @Test
    public void findCars() throws StoreException {
        List<Car> cars = (List<Car>) logic.findCars();
        assertTrue(cars.size() > 2);
    }

    @Test
    public void findCarsFilter() throws StoreException {
        assertFilter(false, false);
        assertFilter(false, true);
        assertFilter(true, false);
        assertFilter(true, true);
    }

    private void assertFilter(Boolean dateFilter, Boolean photoFilter) throws StoreException {
        ZonedDateTime lastDateCreated = ZonedDateTime.now().with(LocalTime.MIDNIGHT);
        List<Car> cars = (List<Car>) logic.findCars();
        Brand nullIdBrand = new Brand();
        nullIdBrand.setId(0);
        List<Brand> brands = new ArrayList<>();
        brands.add(nullIdBrand);
        brands.addAll(logic.findBrands());
        for (Brand brand: brands) {
            List<Car> filterBr = logic.findCars(brand.getId(), dateFilter, photoFilter);
            for (Car car: cars) {
                if (brand.equals(nullIdBrand) || (car.getModel() != null && car.getModel().getBrand().equals(brand))) {
                    if (photoFilter) {
                        List<Photo> photos = logic.findPhotoByCar(car.getId());
                        if (photos != null && photos.size() > 0) {
                            assertDateFilter(dateFilter, lastDateCreated, filterBr, car);
                        } else {
                            assertFalse(filterBr.contains(car));
                        }
                    } else {
                        assertDateFilter(dateFilter, lastDateCreated, filterBr, car);
                    }
                } else {
                    assertFalse(filterBr.contains(car));
                }
            }
        }
    }

    private void assertDateFilter(Boolean dateFilter, ZonedDateTime lastDateCreated, List<Car> filterBr, Car car) {
        if ((car.getDateCreated() != null && car.getDateCreated().isAfter(lastDateCreated)) || !dateFilter) {
            assertTrue(filterBr.contains(car));
        } else {
            assertFalse(filterBr.contains(car));
        }
    }

    @Test
    public void findCar() throws StoreException {
        Integer id = ((ArrayList<Car>) logic.findCars()).get(0).getId();
        Car car = logic.findCar(id);
        assertThat(car.getId(), is(id));
    }

    @Test
    public void findCarStringId() throws StoreException {
        Integer id = ((ArrayList<Car>) logic.findCars()).get(0).getId();
        Car car = logic.findCar(id.toString());
        assertThat(car.getId(), is(id));
    }


    @Test
    public void findBrands() throws StoreException {
        List<Brand> brands = logic.findBrands();
        assertTrue(brands.size() > 2);
    }

    @Test
    public void findModels() throws StoreException {
        List<Model> models = logic.findModels();
        assertTrue(models.size() > 2);
    }

    @Test
    public void findBodies() throws StoreException {
        List<Body> bodies = logic.findBodies();
        assertTrue(bodies.size() > 2);
    }

    @Test
    public void findColors() throws StoreException {
        List<Color> colors = logic.findColors();
        assertTrue(colors.size() > 2);
    }

    @Test
    public void findTransmissions() throws StoreException {
        List<Transmission> transmissions = logic.findTransmissions();
        assertTrue(transmissions.size() > 2);
    }

    @Test
    public void findEngines() throws StoreException {
        List<Engine> engines = logic.findEngines();
        assertTrue(engines.size() > 2);
    }

    @Test
    public void findDrives() throws StoreException {
        List<Drive> drives = logic.findDrives();
        assertTrue(drives.size() > 2);
    }

    @Test
    public void findModel() throws StoreException {
        Integer id = logic.findModels().get(0).getId();
        Model model = logic.findModel(id);
        assertThat(model.getId(), is(id));
    }

    @Test
    public void findModelStringId() throws StoreException {
        Integer id = logic.findModels().get(0).getId();
        Model model = logic.findModel(id.toString());
        assertThat(model.getId(), is(id));
    }

    @Test
    public void findBody() throws StoreException {
        Integer id = logic.findBodies().get(0).getId();
        Body body = logic.findBody(id);
        assertThat(body.getId(), is(id));
    }

    @Test
    public void findBodyStringId() throws StoreException {
        Integer id = logic.findBodies().get(0).getId();
        Body body = logic.findBody(id.toString());
        assertThat(body.getId(), is(id));
    }

    @Test
    public void findColor() throws StoreException {
        Integer id = logic.findColors().get(0).getId();
        Color color = logic.findColor(id);
        assertThat(color.getId(), is(id));
    }

    @Test
    public void findColorStringId() throws StoreException {
        Integer id = logic.findColors().get(0).getId();
        Color color = logic.findColor(id.toString());
        assertThat(color.getId(), is(id));
    }

    @Test
    public void findTransmission() throws StoreException {
        Integer id = logic.findTransmissions().get(0).getId();
        Transmission transmission = logic.findTransmission(id);
        assertThat(transmission.getId(), is(id));
    }

    @Test
    public void findTransmission1() throws StoreException {
        Integer id = logic.findTransmissions().get(0).getId();
        Transmission transmission = logic.findTransmission(id.toString());
        assertThat(transmission.getId(), is(id));
    }

    @Test
    public void findEngine() throws StoreException {
        Integer id = logic.findEngines().get(0).getId();
        Engine engine = logic.findEngine(id);
        assertThat(engine.getId(), is(id));
    }

    @Test
    public void findEngineStringId() throws StoreException {
        Integer id = logic.findEngines().get(0).getId();
        Engine engine = logic.findEngine(id.toString());
        assertThat(engine.getId(), is(id));
    }

    @Test
    public void findDrive() throws StoreException {
        Integer id = logic.findDrives().get(0).getId();
        Drive drive = logic.findDrive(id);
        assertThat(drive.getId(), is(id));
    }

    @Test
    public void findDriveStringId() throws StoreException {
        Integer id = logic.findDrives().get(0).getId();
        Drive drive = logic.findDrive(id.toString());
        assertThat(drive.getId(), is(id));
    }

    @Test
    public void createCar() throws StoreException {
        Car car = new Car();
        Integer id = car.getId();
        int size = logic.findCars().size();
        logic.createCar(car);
        int newSize = logic.findCars().size();
        assertThat(size + 1, is(newSize));
        assertNotEquals(id, car.getId());
        Car newCar = logic.findCar(car.getId());
        assertEquals(newCar, car);
    }

    @Test
    public void findUsersCars() throws StoreException {
        User user = logic.findUserByLogin("test_user");
        List<Car> cars = logic.findUsersCars(user.getId());
        for (Car car : cars) {
            assertThat(car.getOwner(), is(user));
        }
    }

    @Test
    public void isCredential() throws StoreException {
        boolean credential;
        credential = logic.isCredential(null, null);
        assertFalse(credential);
        User user = logic.findUserByLogin("test_user");
        credential = logic.isCredential(user.getLogin(), user.getPassword());
        assertTrue(credential);
        credential = logic.isCredential(user.getLogin(), "wrong_psw");
        assertFalse(credential);
    }

    @Test
    public void findUserByLogin() throws StoreException {
        User user = logic.findUserByLogin("test_user");
        assertThat(user.getPassword(), is("test_psw"));
        User wrongUser = logic.findUserByLogin("wrong_user");
        assertNull(wrongUser);
    }

    @Test
    public void createUser() throws StoreException {
        User user = new User();
        assertNull(user.getId());
        logic.createUser(user);
        assertNotNull(user.getId());
        assertNotNull(logic.findUser(user.getId()));
    }

    @Test
    public void findModelsByBrand() throws StoreException {
        Model model = logic.findModel(logic.findModels().get(0).getId());
        List<Model> models = logic.findModelsByBrand(model.getBrand().getId().toString());
        for (Model m : models) {
            assertThat(m.getBrand(), is(model.getBrand()));
        }
    }

    @Test
    public void createPhoto() throws StoreException {
        Photo photo = new Photo();
        assertNull(photo.getId());
        logic.createPhoto(photo);
        assertNotNull(photo.getId());
        assertNotNull(logic.findPhoto(photo.getId()));
    }

    @Test
    public void findPhoto() throws StoreException {
        Integer id = logic.findPhotos().get(0).getId();
        Photo photo = logic.findPhoto(id);
        assertThat(photo.getId(), is(id));
    }

    @Test
    public void findPhotoStringId() throws StoreException {
        Integer id = logic.findPhotos().get(0).getId();
        Photo photo = logic.findPhoto(id.toString());
        assertThat(photo.getId(), is(id));
    }

    @Test
    public void findPhotoByCar() throws StoreException {
        List<Car> cars = (List<Car>) logic.findCars();
        for (Car car: cars) {
            List<Photo> photos = logic.findPhotoByCar(car.getId());
            if (photos != null && photos.size() > 0) {
                for (Photo p : photos) {
                    assertThat(p.getCar(), is(car));
                }
            }
        }
    }

    @Test
    public void findPhotoByCarStringId() throws StoreException {
        List<Car> cars = (List<Car>) logic.findCars();
        for (Car car: cars) {
            List<Photo> photos = logic.findPhotoByCar(car.getId().toString());
            if (photos != null && photos.size() > 0) {
                for (Photo p : photos) {
                    assertThat(p.getCar(), is(car));
                }
            }
        }
    }

    @Test
    public void updateCar() throws StoreException {
        Car car = new Car();
        logic.createCar(car);
        int price = (int) Math.round(Math.random() * 1000);
        car.setPrice(price);
        logic.update(car);
        Car newCar = logic.findCar(car.getId());
        assertThat(newCar.getPrice(), is(price));
    }

    @Test
    public void updateUser() throws StoreException {
        User user = new User();
        logic.createUser(user);
        String login = String.valueOf(Math.round(Math.random() * 1000000));
        user.setLogin(login);
        logic.update(user);
        User newUser = logic.findUser(user.getId());
        assertThat(newUser.getLogin(), is(login));
    }

    @Test
    public void findUser() throws StoreException {
        User user = new User();
        logic.createUser(user);
        User newUser = logic.findUser(user.getId());
        assertThat(newUser, is(user));
    }

    @Test
    public void createCarFromParameters() throws StoreException {
        for (int i = 0; i < 10; i++) {
            Map<String, String> parameters = new HashMap<>();
            Car carNull = logic.createCarFromParameters(parameters, null);
            assertNotNull(carNull.getId());
            Model model = logic.findModels().get(0);
            parameters.put("model", model.getId().toString());
            Integer year = 2000;
            parameters.put("year", year.toString());
            Integer mileage = (int) Math.round(Math.random() * 1000000);
            parameters.put("mileage", mileage.toString());
            Body body = logic.findBodies().get(0);
            parameters.put("body", body.getId().toString());
            Color color = logic.findColors().get(0);
            parameters.put("color", color.getId().toString());
            Float volume = (float) Math.random();
            parameters.put("volume", volume.toString());
            Transmission transmission = logic.findTransmission(1);
            parameters.put("transmission", transmission.getId().toString());
            Engine engine = logic.findEngines().get(0);
            parameters.put("engine", engine.getId().toString());
            Drive drive = logic.findDrives().get(0);
            parameters.put("drive", drive.getId().toString());
            Boolean rightWheel = Math.random() > 0.5;
            parameters.put("rightWheel", rightWheel.toString());
            Boolean broken = Math.random() > 0.5;
            parameters.put("broken", broken.toString());
            Integer ownersNum = (int) Math.round(Math.random() * 100);
            parameters.put("ownersNum", ownersNum.toString());
            String vin = "some vin";
            parameters.put("vin", vin);
            Integer power = (int) Math.round(Math.random() * 100);
            parameters.put("power", power.toString());
            String address = "some address";
            parameters.put("address", address);
            Integer price = (int) Math.round(Math.random() * 100);
            parameters.put("price", price.toString());
            User user = logic.findUsers().get(0);
            Car car = logic.createCarFromParameters(parameters, user);
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
    }

    @Test
    public void getInstance() {
        Logic testLogic = Logic.getInstance();
        assertNotNull(testLogic);
    }

    @Test
    public void findPhotos() throws StoreException {
        List<Photo> photos = logic.findPhotos();
        assertTrue(photos.size() > 2);
    }

    @Test
    public void findUsers() throws StoreException {
        List<User> users = logic.findUsers();
        assertTrue(users.size() > 2);
    }
}