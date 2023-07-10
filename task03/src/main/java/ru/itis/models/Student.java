package ru.itis.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Student {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;

}
