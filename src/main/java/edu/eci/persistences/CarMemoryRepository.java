/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.persistences;

import edu.eci.models.Car;
import edu.eci.persistences.repositories.ICarRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author 2112107
 */
@Component
@Qualifier("CarMemoryRepository")
public class CarMemoryRepository implements ICarRepository{

    public static List<Car> carContainer;
    private static List<Car> carsContainer;
     public static List<Car> getContainer(){
         if (CarMemoryRepository.carContainer == null){
             CarMemoryRepository.carContainer= new ArrayList<>();
         }
         return CarMemoryRepository.carContainer;
     }
    @Override
    public List<Car> findAll() {
       return CarMemoryRepository.carContainer; 
       
    }

    @Override
    public Car find(String id) {
        Optional<Car> answer = CarMemoryRepository.getContainer()
                .stream()
                .filter(u -> id.equals(u.getLicencePlate()))
                .findFirst();
        return answer.isPresent() ? answer.get() : null;
    }

    @Override
    public String save(Car entity) {
       CarMemoryRepository.getContainer().add(entity);
		return entity.getLicencePlate();
    }

    @Override
    public void update(Car entity) {
        CarMemoryRepository.carContainer = CarMemoryRepository.getContainer()
                .stream()
                .map(u -> u.getLicencePlate().equals(entity.getLicencePlate()) ? entity : u)
                .collect(toList());
    }

    @Override
    public void delete(Car o) {
        CarMemoryRepository.carContainer = CarMemoryRepository.getContainer()
                .stream()
                .filter(u -> !u.getLicencePlate().equals(o.getLicencePlate()))
                .collect(toList());
    }

    @Override
    public void remove(Long id) {
        CarMemoryRepository.carsContainer = CarMemoryRepository.getContainer()
                .stream()
                .filter(u -> !u.getLicencePlate().equals(id))
                .collect(toList());
    }

    @Override
    public Car getCarByUserName(String licence) {
        return CarMemoryRepository.getContainer()
                .stream()
                .filter(u -> licence.equals(u.getLicencePlate()))
                .findFirst()
                .get();
    }
    
}
