package edu.eci.persistences.repositories;

import edu.eci.models.Car;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICarRepository extends DAO<Car, String> {
    Car getCarByUserName(String licence);
}
