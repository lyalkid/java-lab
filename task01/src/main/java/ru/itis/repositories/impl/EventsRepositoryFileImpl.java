package ru.itis.repositories.impl;

import ru.itis.models.Event;
import ru.itis.models.User;
import ru.itis.repositories.EventsRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 6/30/2023
 * Repository Example
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class EventsRepositoryFileImpl implements EventsRepository {
    private final String eventFileName;
    private final String eventsAndUsersFileName;

    public EventsRepositoryFileImpl(String eventFileName, String eventsAndUsersFileName) {
        this.eventFileName = eventFileName;
        this.eventsAndUsersFileName = eventsAndUsersFileName;
    }

    @Override
    public void save(Event model) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(eventFileName, true))){
            writer.write(model.getId() + "|" + model.getName() + "|" + model.getDate());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Event findByName(String nameEvent) {

        Map<String, Event> eventMap = new HashMap<>();
        String[] data = new String[3];

        File file = new File(this.eventFileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                data = line.split("\\|");

                String id = data[0];
                String name = data[1];
                LocalDate localDate = LocalDate.parse(data[2]);
                Event event = new Event(id, localDate, name);
                eventMap.put(name, event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (eventMap.containsKey(nameEvent)) {
            return eventMap.get(nameEvent);
        }
        return null;
    }

    @Override
    public void saveUserToEvent(User user, Event event) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(eventsAndUsersFileName, true))){
            writer.write(user.getId() + "|" + event.getId());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
