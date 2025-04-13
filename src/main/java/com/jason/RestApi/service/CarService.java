package com.jason.RestApi.service;

import com.jason.RestApi.dto.CarDTO;
import com.jason.RestApi.entity.Car;
import com.jason.RestApi.exception.ResourceNotFoundException;
import com.jason.RestApi.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Car entities.
 * This class handles business logic for CRUD operations on cars.
 */
@Service
public class CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    private CarRepository carRepository;

    /**
     * Retrieves all cars from the database.
     *
     * @return A list of all Car entities.
     */
    public List<Car> getAllCars() {
        logger.info("Fetching all cars");
        return carRepository.findAll();
    }

    /**
     * Retrieves a car by its ID.
     *
     * @param id The ID of the car to retrieve.
     * @return An Optional containing the Car if found, or empty if not found.
     * @throws ResourceNotFoundException if the car is not found.
     */
    public Optional<Car> getCarById(Long id) {
        logger.info("Fetching car with id: {}", id);
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
    }

    /**
     * Creates a new car in the database.
     *
     * @param carDTO The DTO containing the car details.
     * @return A CarDTO representing the saved car.
     */
    public CarDTO createCar(CarDTO carDTO) {
        logger.info("Creating new car: {}", carDTO);
        Car car = new Car(carDTO.getBrand(), carDTO.getModel(), carDTO.getYear(), carDTO.getDescription());
        Car savedCar = carRepository.save(car);
        return new CarDTO(savedCar.getId(), savedCar.getBrand(), savedCar.getModel(), savedCar.getYear(), savedCar.getDescription());
    }

    /**
     * Updates an existing car in the database.
     *
     * @param id The ID of the car to update.
     * @param carDetails The updated car details.
     * @return An Optional containing the updated Car if found, or empty if not found.
     * @throws ResourceNotFoundException if the car is not found.
     */
    public Optional<Car> updateCar(Long id, Car carDetails) {
        logger.info("Updating car with id: {}", id);
        return carRepository.findById(id)
                .map(existingCar -> {
                    existingCar.setBrand(carDetails.getBrand());
                    existingCar.setModel(carDetails.getModel());
                    existingCar.setYear(carDetails.getYear());
                    existingCar.setDescription(carDetails.getDescription());
                    return Optional.of(carRepository.save(existingCar));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
    }

    /**
     * Deletes a car from the database.
     *
     * @param id The ID of the car to delete.
     * @return true if the car was successfully deleted, false if the car was not found.
     */
    public boolean deleteCar(Long id) {
        logger.info("Deleting car with id: {}", id);
        return carRepository.findById(id)
                .map(car -> {
                    carRepository.delete(car);
                    return true;
                })
                .orElse(false);
    }

}
