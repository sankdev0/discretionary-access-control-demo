package dac;

import java.util.Scanner;

public class DacDemoOne {

  public static void main(String[] args)
  {
    String[] users = new String[7];
    String[] items = new String[5];
    int[] policy = new int[25];
    String[] idpolicy = new String[8];
    String id = "";
    int i;
    String obj;
    String operation = "";
    String[] operations = new String[4];

    users[0] = "Admin";
    users[1] = "Guest1";
    users[2] = "Guest2";
    users[3] = "User1";
    users[4] = "User2";

    idpolicy[0] = "Полный запрет";
    idpolicy[1] = "Передача прав";
    idpolicy[2] = "Запись";
    idpolicy[3] = "Запись, Передача прав";
    idpolicy[4] = "Чтение";
    idpolicy[5] = "Чтение, Передача прав";
    idpolicy[6] = "Чтение, Запись";
    idpolicy[7] = "Полный доступ";

    items[0] = "File1";
    items[1] = "File2";
    items[2] = "Floopy";
    items[3] = "CD-ROM";
    items[4] = "File3";


    policy[0] = 7;
    policy[1] = 7;
    policy[2] = 7;
    policy[3] = 7;
    policy[4] = 7;

    policy[5] = 7;
    policy[6] = 4;
    policy[7] = 0;
    policy[8] = 6;
    policy[9] = 0;

    policy[10] = 7;
    policy[11] = 4;
    policy[12] = 0;
    policy[13] = 0;
    policy[14] = 4;

    policy[15] = 5;
    policy[16] = 6;
    policy[17] = 0;
    policy[18] = 6;
    policy[19] = 3;

    policy[20] = 7;
    policy[21] = 4;
    policy[22] = 0;
    policy[23] = 6;
    policy[24] = 5;


    System.out.println("[Доступ к системе] - АВТОРИЗУЙТЕСЬ");

    System.out.println("ВЫ НАХОДИТЕСЬ В СИСТЕМЕ АВТОРИЗАЦИИ \n");
    System.out.println("Введите ваше имя! \n");
    System.out.print("User: ");

    Scanner sc = new Scanner(System.in);

    id = sc.nextLine();

    for (i = 0; i != 5; i++)
    {
      if (users[i] == id)
      {
        System.out.println("Добро пожаловать в систему " + id + "!");
        System.out.println("\nПеречень ваших прав: " + id);


        System.out.print(items[0] + "   ");
        System.out.println(idpolicy[policy[i]]);

        System.out.print(items[1] + "   ");
        System.out.println(idpolicy[policy[i + 5]]);

        System.out.print(items[2] + "   ");
        System.out.println(idpolicy[policy[i + 10]]);

        System.out.print(items[3] + "   ");
        System.out.println(idpolicy[policy[i + 15]]);

        System.out.print(items[4] + "   ");
        System.out.println(idpolicy[policy[i + 20]]);
        break;

      }
      else if (i == 5)
      {
        System.out.println("Внимание! " + id + " вы ввели неправильное имя!!!");
        System.out.println("Нажмите любую кнопку (Enter)!!");
        sc.nextLine();
        return;
      }
    }

    do
    {

      System.out.print("\nЖду ваших указаний " + id + ":");

      operation = sc.nextLine();


      if (operation == "exit")
      {
        System.out.println("Выход из системы.... Нажмите любую кнопку (Enter)");
        sc.nextLine();
        break;
      }

      if (operation == "read")
      {
        int o;
        System.out.println("Перечень объектов");
        System.out.println("[0] - [" + items[0] + "]\n" + "[1] - [" + items[1] + "]\n" +
            "[2] - [" + items[2] + "]\n" + "[3] - [" + items[3] + "]\n" + "[4] - [" + items[4] + "]\n");
        System.out.println("Над каким обьектом выполняется операция? ");
        obj = sc.nextLine();
        o = Integer.parseInt(obj);
        switch (o)
        {
          case 0: System.out.println("Выбран объект " + items[0]); break;
          case 1: System.out.println("Выбран объект " + items[1]); break;
          case 2: System.out.println("Выбран объект " + items[2]); break;
          case 3: System.out.println("Выбран объект " + items[3]); break;
          case 4: System.out.println("Выбран объект " + items[4]); break;
          default: System.out.println("Объект " + o + " не найден!!!"); break;
        }
      }

    } while (true);
  }
}