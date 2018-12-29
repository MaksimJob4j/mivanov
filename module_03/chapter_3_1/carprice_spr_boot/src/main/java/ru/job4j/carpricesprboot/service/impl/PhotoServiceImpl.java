package ru.job4j.carpricesprboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carpricesprboot.domain.description.Photo;
import ru.job4j.carpricesprboot.repository.PhotoRepository;
import ru.job4j.carpricesprboot.service.PhotoService;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoServiceImpl implements PhotoService {
    private final static Logger LOGGER = LogManager.getLogger(PhotoServiceImpl.class);
    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public void create(Photo photo) {
        LOGGER.traceEntry();
        photoRepository.save(photo);
    }

    @Override
    public Iterable<Photo> find() {
        LOGGER.traceEntry();
        return photoRepository.findAll();
    }

    @Override
    public List<Photo> findOrdered() {
        return photoRepository.findAllByOrderByFileName();
    }

    @Override
    public Optional<Photo> find(int id) {
        LOGGER.traceEntry();
        return photoRepository.findById(id);
    }

    @Override
    public void update(Photo photo) {
        LOGGER.traceEntry();
        photoRepository.save(photo);
    }

    @Override
    public void delete(int id) {
        LOGGER.traceEntry();
        photoRepository.deleteById(id);
    }

    @Override
    public void delete(Photo photo) {
        LOGGER.traceEntry();
        photoRepository.delete(photo);
    }

    @Override
    public List<Photo> findByCar(Integer carId) {
        LOGGER.traceEntry();
        return photoRepository.findAllByCarId(carId);
    }
}
