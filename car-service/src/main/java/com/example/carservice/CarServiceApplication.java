package com.example.carservice;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
public class CarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner init(CarRepository repository) {
        return args -> {
            Stream.of(
                    new Car("Ferrari", 20),
                    new Car("Jaguar", 30),
                    new Car("Porsche", 25),
                    new Car("Lamborghini", 30),
                    new Car("Bugatti", 23),
                    new Car("AMC Gremlin", 40),
                    new Car("Triumph Stag", 15),
                    new Car("Ford Pinto", 17),
                    new Car("Yugo GV", 29))
                    .forEach(repository::save);
            repository.findAll().forEach(System.out::println);
        };
    }
}

@Data
@NoArgsConstructor
@Entity
class Car {

    public Car(String name, int power) {
        this.name = name;
        this.power = power;
    }

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int power;
}

@RepositoryRestResource
interface CarRepository extends JpaRepository<Car, Long> {
}
