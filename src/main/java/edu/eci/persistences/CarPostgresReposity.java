/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.persistences;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.eci.models.Car;
import edu.eci.models.User;
import edu.eci.persistences.repositories.ICarRepository;
import edu.eci.persistences.repositories.IUserRepository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
@Qualifier("CarPostgresReposity")
public class CarPostgresReposity implements ICarRepository {

    private String dbUrl = "jdbc:postgres://iukiexucwyckzk:2df9a58420c2b7db39c165f33e762d5f8c574c283f2803edd777cdbe569298d0@ec2-174-129-10-235.compute-1.amazonaws.com:5432/d25qeplrnjlljm";

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Car> findAll() {
        String query = "SELECT * FROM cars";
        List<Car> cars = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Car car = new Car();
                car.setLicencePlate(rs.getString("licencePlate"));
                car.setBrand(rs.getString("brand"));
                cars.add(car);
            }
            return cars;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car getCarByLicence(String licence) {
        String query = "SELECT * FROM cars WHERE licence='" + licence + "';";
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Car car = new Car();
            car.setLicencePlate(rs.getString("licencePlate"));
            car.setBrand(rs.getString("brand"));
            return car;
        } catch (Exception e) {

            System.out.println(e.getMessage());

            throw new RuntimeException(e);
        }
    }

    @Override
    public Car find(String id) {
        String query = "SELECT * FROM cars WHERE id=" + id + ";";

        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Car car = new Car();
            car.setLicencePlate(rs.getString("licencePlate"));
            car.setBrand(rs.getString("brand"));
            return car;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String  save(Car entity) {
        String query = "INSERT INTO (brand,licencePlate) values ('" + entity.getBrand() + "'," + entity.getLicencePlate() + ");";

        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            return entity.getLicencePlate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Car entity) {
        String query = "UPDATE  cars SET brand='" + entity.getBrand() + "' Where licencePlate='" + entity.getLicencePlate() + "';";
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Car o) {
        String query = "delete  from users where licencePlate='" + o.getLicencePlate() + "';";
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Bean

    public DataSource dataSource1() throws SQLException {

        if (dbUrl == null || dbUrl.isEmpty()) {

            return new HikariDataSource();

        } else {

            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(dbUrl);

            return new HikariDataSource(config);

        }

    }

}
