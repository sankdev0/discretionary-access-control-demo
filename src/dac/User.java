package dac;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class User {

  public String name; // Используется в качестве идентификатора

  public String[][] accessRights; // Список прав пользователя

  public User(String name) {
    this.name = name;
  }

  public User(String name, String[][] accessRights) {
    this.name = name;
    this.accessRights = accessRights;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return name.equals(user.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

}
