
package ru.itis;

import ru.itis.models.User;
import ru.itis.repositories.EventsRepository;
import ru.itis.repositories.UsersRepository;
import ru.itis.repositories.impl.EventsRepositoryFileImpl;
import ru.itis.repositories.impl.UsersRepositoryFileImpl;
import ru.itis.services.AppService;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        UsersRepository usersRepository = new UsersRepositoryFileImpl("C:\\Users\\Азамат\\IdeaProjects\\Java Lab\\javalab\\task01\\users.txt");
        EventsRepository eventsRepository = new EventsRepositoryFileImpl("C:\\Users\\Азамат\\IdeaProjects\\Java Lab\\javalab\\task01\\events.txt", "C:\\Users\\Азамат\\IdeaProjects\\Java Lab\\javalab\\task01\\events_users.txt");
        AppService appService = new AppService(usersRepository, eventsRepository);

        /*appService.signUp("admin@gmail.com", "qwerty007");
        appService.signUp("marsel@gmail.com", "qwerty008");

        appService.addEvent("Практика по Java", LocalDate.now());
        appService.addEvent("Практика по Golang", LocalDate.now().plusDays(1));

        appService.addUserToEvent("marsel@gmail.com", "Практика по Golang");
        appService.addUserToEvent("marsel","Практика по Java");

        System.out.println(appService.getAllEventsByUser("marsel@gmail.com"));
        System.out.print(usersRepository.findByEmail("marsel@gmail.com"));*/
        System.out.print(eventsRepository.findByName("Практика по Java"));

        /*User user = usersRepository.findByEmail("marsel@gmail.com");
        try {
            System.out.println(eventsRepository.findAllByMembersContains(user));
        }
        catch (NullPointerException e){
            throw new IllegalArgumentException(e);
        }*/
    }
}
