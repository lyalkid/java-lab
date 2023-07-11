package ru.itis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.models.*;
import ru.itis.repositories.*;


public class  Main {

    public static void main(String[] args) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/online_shop");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("1234");
        hikariConfig.setDriverClassName("org.postgresql.Driver");

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        StudentsRepository studentsRepository = new StudentsRepositorySpringJdbcImpl(dataSource);

        System.out.println(studentsRepository.findAll());

        CourseRepository courseRepository = new CourseRepositorySpringJdbcImpl(dataSource);

        Course course =  Course.builder()
                .title("Практика АиСД")
                .startDate("2023-05-01")
                .finishDate("2023-05-31")
                .build();
        System.out.println(course);

        courseRepository.save(course);

        System.out.println(course);

        System.out.println(courseRepository.findAll());

    }
}
