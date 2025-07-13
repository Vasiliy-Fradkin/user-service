package org.example;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.entity.User;
import org.example.util.HibernateUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    Выберите действие:
                    1 - Создать пользователя
                    2 - Показать пользователя по ID
                    3 - Показать всех пользователей
                    4 - Обновить пользователя
                    5 - Удалить пользователя
                    0 - Выход
                    """);

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    User user = new User();
                    System.out.print("Имя: ");
                    user.setName(scanner.nextLine());
                    System.out.print("Email: ");
                    user.setEmail(scanner.nextLine());
                    System.out.print("Возраст: ");
                    user.setAge(Integer.parseInt(scanner.nextLine()));

                    userDao.createUser(user);
                    System.out.println("Пользователь создан!");
                }
                case "2" -> {
                    System.out.print("Введите ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    userDao.getUserById(id)
                            .ifPresentOrElse(
                                    System.out::println,
                                    () -> System.out.println("Пользователь не найден")
                            );
                }
                case "3" -> {
                    userDao.getAllUsers().forEach(System.out::println);
                }
                case "4" -> {
                    System.out.print("Введите ID пользователя для обновления: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    var optUser = userDao.getUserById(id);
                    if (optUser.isPresent()) {
                        User user = optUser.get();
                        System.out.print("Новое имя (" + user.getName() + "): ");
                        user.setName(scanner.nextLine());
                        System.out.print("Новый email (" + user.getEmail() + "): ");
                        user.setEmail(scanner.nextLine());
                        System.out.print("Новый возраст (" + user.getAge() + "): ");
                        user.setAge(Integer.parseInt(scanner.nextLine()));
                        userDao.updateUser(user);
                        System.out.println("Пользователь обновлён");
                    } else {
                        System.out.println("Пользователь не найден");
                    }
                }
                case "5" -> {
                    System.out.print("Введите ID для удаления: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    userDao.deleteUser(id);
                    System.out.println("Пользователь удалён");
                }
                case "0" -> {
                    HibernateUtil.shutdown();
                    scanner.close();
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный выбор");
            }
        }
    }
}
