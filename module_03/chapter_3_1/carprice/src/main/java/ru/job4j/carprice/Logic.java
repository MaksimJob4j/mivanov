package ru.job4j.carprice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.dao.*;
import ru.job4j.carprice.items.Car;
import ru.job4j.carprice.items.User;
import ru.job4j.carprice.items.description.*;
import ru.job4j.carprice.store.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Logic {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.carprice.Logic.class);
    private static final Logic INSTANCE = new Logic();

    private final UserDAO userDAO = new UserStore();
    private final PhotoDAO photoDAO = new PhotoStore();
    private final ModelDAO modelDAO = new ModelStore();
    private final CarDAO carDAO = new CarStore();

    private Logic() {
    }

    public static Logic getInstance() {
        LOGGER.traceEntry();
        return INSTANCE;
    }

    public Collection<Car> findCars() throws StoreException {
        LOGGER.traceEntry();
        return carDAO.find();
    }

    public List<Car> findCars(Integer brandFilter, Boolean dateFilter, Boolean photoFilter) throws StoreException {
        LOGGER.traceEntry();
        return carDAO.findCarsCriteria(brandFilter, dateFilter, photoFilter);
    }

    public Car findCar(Integer carId) throws StoreException {
        LOGGER.traceEntry();
        return carDAO.find(carId);
    }

    public Car findCar(String carId) throws StoreException {
        LOGGER.traceEntry();
        return findCar(Integer.parseInt(carId));
    }

    public List<Brand> findBrands() throws StoreException {
        LOGGER.traceEntry();
        return (List<Brand>) new HibStore(Brand.class).find();
    }

    public List<Model> findModels() throws StoreException {
        LOGGER.traceEntry();
        return (List<Model>) modelDAO.find();
    }

    public List<Body> findBodies() throws StoreException {
        LOGGER.traceEntry();
        return (List<Body>) new HibStore(Body.class).find();
    }

    public List<Color> findColors() throws StoreException {
        LOGGER.traceEntry();
        return (List<Color>) new HibStore(Color.class).find();
    }

    public List<Transmission> findTransmissions() throws StoreException {
        LOGGER.traceEntry();
        return (List<Transmission>) new HibStore(Transmission.class).find();
    }

    public List<Engine> findEngines() throws StoreException {
        LOGGER.traceEntry();
        return (List<Engine>) new HibStore(Engine.class).find();
    }

    public List<Drive> findDrives() throws StoreException {
        LOGGER.traceEntry();
        return (List<Drive>) new HibStore(Drive.class).find();
    }

    public Model findModel(Integer modelId) throws StoreException {
        LOGGER.traceEntry();
        return modelDAO.find(modelId);
    }

    public Model findModel(String modelId) throws StoreException {
        LOGGER.traceEntry();
        return findModel(Integer.parseInt(modelId));
    }
    public Body findBody(Integer bodyId) throws StoreException {
        LOGGER.traceEntry();
        return (Body) new HibStore(Body.class).find(bodyId);
    }

    public Body findBody(String bodyId) throws StoreException {
        LOGGER.traceEntry();
        return findBody(Integer.parseInt(bodyId));
    }

    public Color findColor(Integer colorId) throws StoreException {
        LOGGER.traceEntry();
        return (Color) new HibStore(Color.class).find(colorId);
    }

    public Color findColor(String colorId) throws StoreException {
        LOGGER.traceEntry();
        return findColor(Integer.parseInt(colorId));
    }

    public Transmission findTransmission(Integer transmissionId) throws StoreException {
        LOGGER.traceEntry();
        return (Transmission) new HibStore(Transmission.class).find(transmissionId);
    }

    public Transmission findTransmission(String transmissionId) throws StoreException {
        LOGGER.traceEntry();
        return findTransmission(Integer.parseInt(transmissionId));
    }

    public Engine findEngine(Integer engineId) throws StoreException {
        LOGGER.traceEntry();
        return (Engine) new HibStore(Engine.class).find(engineId);
    }

    public Engine findEngine(String engineId) throws StoreException {
        LOGGER.traceEntry();
        return findEngine(Integer.parseInt(engineId));
    }

    public Drive findDrive(Integer driveId) throws StoreException {
        LOGGER.traceEntry();
        return (Drive) new HibStore(Drive.class).find(driveId);
    }

    public Drive findDrive(String driveId) throws StoreException {
        LOGGER.traceEntry();
        return findDrive(Integer.parseInt(driveId));
    }

    public void createCar(Car car) throws StoreException {
        LOGGER.traceEntry();
        carDAO.create(car);
    }

    public List<Car> findUsersCars(int userId) throws StoreException {
        LOGGER.traceEntry();
        return userDAO.findUsersCars(userId);
    }

    public boolean isCredential(String login, String password) throws StoreException {
        LOGGER.traceEntry();
        boolean exist = false;
        User user = userDAO.findUserByLogin(login);
        if (user != null && password.equals(user.getPassword())) {
            exist = true;
        }
        return exist;
    }

    public User findUserByLogin(String login) throws StoreException {
        LOGGER.traceEntry();
        return userDAO.findUserByLogin(login);
    }

    public void createUser(User user) throws StoreException {
        LOGGER.traceEntry();
        userDAO.create(user);
    }

    public List<Model> findModelsByBrand(String brandId) throws StoreException {
        LOGGER.traceEntry();
        return modelDAO.findModelsByBrand(Integer.parseInt(brandId));
    }

    public void createPhoto(Photo photo) throws StoreException {
        LOGGER.traceEntry();
        new HibStore(Photo.class).create(photo);
    }

    public Photo findPhoto(Integer photoId) throws StoreException {
        LOGGER.traceEntry();
        return (Photo) new HibStore(Photo.class).find(photoId);
    }

    public Photo findPhoto(String photoId) throws StoreException {
        LOGGER.traceEntry();
        return findPhoto(Integer.parseInt(photoId));
    }

    public List<Photo> findPhotoByCar(String carId) throws StoreException {
        LOGGER.traceEntry();
        return this.findPhotoByCar(Integer.parseInt(carId));
    }

    public List<Photo> findPhotoByCar(Integer carId) throws StoreException {
        LOGGER.traceEntry();
        return photoDAO.findPhotoByCar(carId);
    }


    public void update(Car car) throws StoreException {
        LOGGER.traceEntry();
        carDAO.update(car);
    }

    public Car createCarFromParameters(Map<String, String> parameters, User loginUser) throws StoreException {
        LOGGER.traceEntry();
        return carDAO.createCarFromParameters(parameters, loginUser);
    }

    public void update(User user) throws StoreException {
        LOGGER.traceEntry();
        userDAO.update(user);
    }

    public User findUser(Integer userId) throws StoreException {
        LOGGER.traceEntry();
        return userDAO.find(userId);
    }

    public List<Photo> findPhotos() throws StoreException {
        LOGGER.traceEntry();
        return (List<Photo>) photoDAO.find();
    }

    public List<User> findUsers() throws StoreException {
        LOGGER.traceEntry();
        return (List<User>) new HibStore(User.class).find();
    }
}