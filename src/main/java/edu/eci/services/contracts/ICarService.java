/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.services.contracts;

import edu.eci.models.Car;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2112107
 */
@Service
public interface ICarService {
    List<Car> list();
    Car create(Car user);
    Car get(String  id);
    void update(Car user) ;
    void delete(String  id);
}
