package dac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class DacDemo {

  private Map<Integer, String> rights;
  private Map<Integer, String> objects;
  private List<User> users;

  // Конструктор наполняет систему данными о пользователях, правах и объектах.
  public DacDemo() {
    this.rights = new HashMap<>();
    rights.put(0, "Чтение");
    rights.put(1, "Запись");
    rights.put(2, "Передача_прав");

    this.objects = new HashMap<>();
    objects.put(0, "Файл_1");
    objects.put(1, "Файл_2");
    objects.put(2, "Файл_3");
    objects.put(3, "Файл_4");
    objects.put(4, "CD-RW");
    objects.put(5, "Дисковод");

    this.users = new ArrayList();
    users.add(new User("admin"));
    users.add(new User("Иван"));
    users.add(new User("Борис"));
    users.add(new User("Геннадий"));
    users.add(new User("Азамат"));
    users.add(new User("Петр"));
    users.add(new User("Гуля"));

    // Заполним матрицу прав на объекты для пользователей.
    for (User tempUser : users) {
      tempUser.accessRights = new String[6][4];
      // Назначим полные права для Админа.
      if (tempUser.name.equals("admin")) {
        for (int i = 0; i < 6; i++) {
          tempUser.accessRights[i][0] = objects.get(i);
          for (int j = 1; j < 4; j++) {
            tempUser.accessRights[i][j] = rights.get(j - 1);
          }
        }
        // Для остальных пользователей назначим случайные права.
      } else {
        for (int i = 0; i < 6; i++) {
          tempUser.accessRights[i][0] = objects.get(i);
          Random random = ThreadLocalRandom.current();
          int randomIter = random.nextInt(4);
          for (int k = 1; k <= randomIter; k++) {
            int j = random.nextInt(4 - 1) + 1; // получаем случайное число в промежутке [1, 3]
            if (!hasRight(tempUser, rights.get(j - 1), objects.get(i))) {
              tempUser.accessRights[i][k] = rights.get(j - 1);
            }
          }
        }
      }
    }
  }

  public List<User> getUsers() {
    return users;
  }

  public Map<Integer, String> getRights() {
    return rights;
  }

  public Map<Integer, String> getObjects() {
    return objects;
  }

  private User findUser(String name, DacDemo demo) {
    User user = new User(name);
    for (User tempUser : demo.users) {
      if (user.equals(tempUser)) {
        return tempUser;
      }
    }
    return null;
  }

  public String getUserRights(User user, DacDemo demo) {
    String userRights = "Перечень прав для пользователя " + user.name;
    for (int i = 0; i < 6; i++) {
      userRights = userRights + "\n" + "Объект: " + user.accessRights[i][0] + " Права: ";
      for (int j = 1; j < 4; j++) {
        if (user.accessRights[i][j] != null) {
          userRights = userRights + user.accessRights[i][j] + ", ";
        }
      }
      userRights = userRights.substring(0, userRights.length() - 2);
    }
    return userRights;
  }

  public User identify(Scanner sc, DacDemo demo) {
    boolean identified = false;
    User user = null;

    do {
      System.out.print("Введите идентификатор пользователя для входа: ");
      String userName = sc.nextLine();
      if (!userName.isEmpty()) {
        user = findUser(userName, demo);
        if (user != null) {
          System.out.println("Пользователь: " + userName);
          System.out.println("Идентификация прошла успешно. Добро пожаловать в систему.");
          System.out.println("***");
          identified = true;
        } else {
          System.out.println("Указанный пользователь не найден - " + userName);
          identified = false;
        }
      }
    } while (!identified);
    return user;
  }

  public void runCommand(Scanner sc, DacDemo demo, User user) {
    boolean running = false;

    do {
      System.out.println(
          "Введите команду и объект в форме [команда] [объект]. Для выхода введите команду Выход");
      System.out.print(">>");
      String input = sc.nextLine();
      String[] inputs = input.split(" ");
      // выйти, если команда выход.
      if (inputs[0].equals("Выход")) {
        System.out.println("Работа пользователя " + user.name + " закончена. До свидания.");
        running = false;
        break;
      }
      if (!demo.getRights().containsValue(inputs[0])) {
        System.out.println("Введенная команда не поддерживается системой. Повторите попытку");
        running = true;
        continue;
      }
      if (!demo.getObjects().containsValue(inputs[1])) {
        System.out.println("Введенный объект не распознан в системе. Повторите попытку");
        running = true;
        continue;
      }

      performCommand(user, demo, inputs);
      running = true;

    } while (running);

  }

  public boolean performCommand(User user, DacDemo demo, String inputs[]) {

    boolean authorized = hasRight(user, inputs[0], inputs[1]);

    if (authorized) {
      System.out.println("--> Выполнение команды авторизовано. Выполняется...");
      if (inputs[0].equals("Передача_прав")) {
        User targetUser = findUser(inputs[3], demo);
        updateRights(targetUser, inputs[2], inputs[1]);
      }
    } else {
      System.out.println("-->> Выполнение команды запрещено.");
    }
    return authorized;
  }

  public boolean hasRight(User user, String right, String object) {

    boolean hasRights = false;
    loopi:
    for (int i = 0; i < 6; i++) {
      for (int j = 1; j < 4; j++) {
        if (user.accessRights[i][0] != null && user.accessRights[i][j] != null) {
          if (user.accessRights[i][0].equals(object)
              && user.accessRights[i][j].equals(right)) {
            hasRights = true;
            break loopi;
          } else {
            hasRights = false;
          }
        }
      }
    }
    return hasRights;
  }

  public void updateRights(User user, String right, String object) {
    if (!hasRight(user, right, object)) {
      loopi:
      for (int i = 0; i < 6; i++) {
        for (int j = 1; j < 4; j++) {
          if (user.accessRights[i][0] != null) {
            if (user.accessRights[i][0].equals(object)
                && user.accessRights[i][j] == null) {
              user.accessRights[i][j] = right;
              break loopi;
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {

    System.out.println("Система контроля доступа.");
    DacDemo demo = new DacDemo();
    Scanner sc = new Scanner(System.in);

    do {
      User user = demo.identify(sc, demo);
      System.out.println(demo.getUserRights(user, demo));
      System.out.println("***");
      demo.runCommand(sc, demo, user);
    } while (true);
  }
}