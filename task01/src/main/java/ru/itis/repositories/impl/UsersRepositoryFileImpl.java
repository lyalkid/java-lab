package ru.itis.repositories.impl;

import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class UsersRepositoryFileImpl implements UsersRepository {

    private final String fileName;

    public UsersRepositoryFileImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(User model) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(model.getId() + "|" + model.getEmail() + "|" + model.getPassword());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public User findByEmail(String emailUser) {
        List<User> users = findAll();
        users = users
                .stream()
                .filter(user -> user.getEmail().equals(emailUser))
                .collect(Collectors.toList());
        for (User user: users) {
            if (user.getEmail().equals(emailUser)) {
                return user;
            }
        }

        return null;
    }

    private List<User> findAll(){

        List<User> result = new ArrayList<>();

        String[] data;

        File file = new File(this.fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                data = line.split("\\|");
                String id = data[0];
                String email = data[1];
                String password = data[2];
                User user = User.builder()
                        .id(id)
                        .email(email)
                        .password(password)
                        .build();
                result.add(user);
            }
        }catch (NullPointerException | ArrayIndexOutOfBoundsException | IOException e){
            throw new RuntimeException(e);
        }
        return result;
    }
}
