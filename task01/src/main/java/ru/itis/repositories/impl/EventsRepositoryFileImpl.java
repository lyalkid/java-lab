package ru.itis.repositories.impl;

import ru.itis.models.Event;
import ru.itis.models.User;
import ru.itis.repositories.EventsRepository;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EventsRepositoryFileImpl implements EventsRepository {

    private final String eventFileName;
    private final String eventsAndUsersFileName;

    public EventsRepositoryFileImpl(String eventFileName, String eventsAndUsersFileName) {
        this.eventFileName = eventFileName;
        this.eventsAndUsersFileName = eventsAndUsersFileName;
    }

    @Override
    public List<Event> findAllByMembersContains(User user) throws NullPointerException{
        List<Event> eventsByUsers = new ArrayList<>();

        String userId = user.getId();
        Set<String> membersSet = new HashSet<>(); // k - мероприятие

        File file = new File(this.eventsAndUsersFileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String[] eventNUsers = new String[2];
            while ((line = br.readLine()) != null) {
                eventNUsers = line.split("\\|");
                String uId = eventNUsers[0];
                String eId = eventNUsers[1];
                if(userId.equals(uId) && userId != null) {
                    membersSet.add(eId);
                }
            }
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException(e);
        }

        File file1 = new File(this.eventFileName);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file1))) {
            String line;
            String[] data = new String[3];
            while ((line = bufferedReader.readLine()) != null) {
                data = line.split("\\|");

                String id = data[0];
                String name = data[1];
                LocalDate localDate = LocalDate.parse(data[2]);
                if(membersSet.contains(id)){
                    Event event = Event.builder()
                            .id(id)
                            .name(name)
                            .date(localDate)
                            .build();
                    eventsByUsers.add(event);
                }
            }

        }
        catch (NullPointerException | IOException e) {
            throw new RuntimeException(e);
        }
        return eventsByUsers;
    }

    @Override
    public void save(Event model) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(eventFileName, true))) {
            writer.write(model.getId() + "|" + model.getName() + "|" + model.getDate());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Event findByName(String nameEvent) throws NullPointerException {
        List<Event> events = findAll(this.eventFileName);
        events = events
                .stream()
                .filter(event -> event.getName().equals(nameEvent))
                .collect(Collectors.toList());
        for (Event event : events) {
            if (event.getName().equals(nameEvent)){
                return event;
            }
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

    public List<Event> findAll(String filename){

        List<Event> result = new ArrayList<>();

        String[] data;

        File file = new File(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                data = line.split("\\|");
                String id = data[0];
                String name = data[1];
                LocalDate localDate = LocalDate.parse(data[2]);
                Event event = Event.builder()
                        .id(id)
                        .name(name)
                        .date(localDate)
                        .build();
                result.add(event);
            }
        }
        catch (NullPointerException | ArrayIndexOutOfBoundsException | IOException e){
            throw new RuntimeException(e);
        }

        return result;
    }
}
