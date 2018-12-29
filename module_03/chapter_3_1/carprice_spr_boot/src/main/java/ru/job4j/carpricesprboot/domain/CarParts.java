package ru.job4j.carpricesprboot.domain;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carpricesprboot.domain.description.*;

import java.util.List;

@Getter
public class CarParts {
    private final static Logger LOGGER = LogManager.getLogger(CarParts.class);

    private final List<Body> bodies;
    private final List<Brand> brands;
    private final List<Color> colors;
    private final List<Drive> drives;
    private final List<Engine> engines;
    private final List<Transmission> transmissions;


    public CarParts(List<Body> bodies,
                    List<Brand> brands,
                    List<Color> colors,
                    List<Drive> drives,
                    List<Engine> engines,
                    List<Transmission> transmissions) {
        this.bodies = bodies;
        this.brands = brands;
        this.colors = colors;
        this.drives = drives;
        this.engines = engines;
        this.transmissions = transmissions;
    }
}
