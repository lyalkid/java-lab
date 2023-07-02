package ru.itis.repositories.impl;

import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 6/30/2023
 * Repository Example
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class UsersRepositoryFileImpl implements UsersRepository {

    private final String fileName;

    public UsersRepositoryFileImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(User model) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))){
            writer.write(model.getId() + "|" + model.getEmail() + "|" + model.getPassword());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public User findByEmail(String emailUser) {
        // TODO: реализовано
        Map<String, User> userMap = new HashMap<>();

        String[] data = new String[3];
        File file = new File(this.fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                data = line.split("\\|");
                User user = new User(data[0],data[1], data[2]);
                userMap.put(data[1], user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (userMap.containsKey(emailUser)) {
            return userMap.get(emailUser);
        } return null;
    }
}
