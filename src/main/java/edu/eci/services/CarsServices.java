/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.services;

import edu.eci.models.Car;
import edu.eci.models.User;
import edu.eci.persistences.repositories.ICarRepository;
import edu.eci.persistences.repositories.IUserRepository;
import edu.eci.services.contracts.ICarService;
import edu.eci.services.contracts.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
/**
 *
 * @author 2112107
 */
public class CarsServices implements ICarService {

    @Autowired
    @Qualifier("CarMemoryRepository")
    private ICarRepository CarRepository;

    @Override
    public List<Car> list() {
        return CarRepository.findAll();
    }

    @Override
    public Car create(Car car) {
        if (null == car.getLicencePlate()) {
            throw new RuntimeException("Id invalid");
        } else if (CarRepository.find(car.getLicencePlate()) != null) {
            throw new RuntimeException("The car already exists");
        } else {
            CarRepository.save(car);
        }
        return car;
    }

    @Override
    public Car get(String  id) {
        return CarRepository.getCarByLicence(id);
    }

    @Override
    public void update(Car car) {
        CarRepository.update(car);
    }

    @Override
    public void delete(String  id) {
        CarRepository.delete(CarRepository.find(id));
    }

}
