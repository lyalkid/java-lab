package ru.itis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.models.*;
import ru.itis.repositories.*;

import java.time.LocalDate;

public class  Main {

    public static void main(String[] args) {
        HikariConfig hikariConfig = new HikariConfig();
        StudentsRepositoryJdbcImpl studentRepositoryJdbc = new StudentsRepositoryJdbcImpl(hikariConfig.getDataSource());
        CourseRepositoryJdbcImpl courseRepositoryJdbc = new CourseRepositoryJdbcImpl(hikariConfig.getDataSource());

        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/online_shop");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("1234");
        hikariConfig.setDriverClassName("org.postgresql.Driver");

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        StudentsRepository studentsRepository = new StudentsRepositoryJdbcImpl(dataSource);
        Student student = Student.builder()
                .firstName("Имя3")
                .lastName("Фамилия3")
                .age(35)
                .build();

        System.out.println(student);
        studentsRepository.save(student);
        System.out.println(student);

        System.out.println(studentsRepository.findAll());

        CourseRepository courseRepository = new CourseRepositoryJdbcImpl(dataSource);
        Course course = Course.builder()
               .title("Практика по матану")
               .startDate("2022-09-01")
               .finishDate("2022-12-31")
               .build();
        System.out.println(course);
        courseRepository.save(course);
        System.out.println(course);
        System.out.println(courseRepository.findAll());

    }
}
