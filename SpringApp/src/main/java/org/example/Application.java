package org.example;

import org.example.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.createUser(10l, "Ляйсан");
        userService.createUser(13l, "Олег");

        System.out.println(userService.getUser(10l).getName());

        userService.updateUser(10l, "Ляйсанодинокий волк");
        System.out.println(userService.getUser(10l).getName());

        userService.getAllUsers()
                .forEach(user -> System.out.println(user.getName()));

        userService.deleteUser(13l);

        context.close();

    }
}