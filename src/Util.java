import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Util {
  public static String readFromInput(String prompt) {
    System.out.print(prompt);
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    String input = "";
    try {
      input = reader.readLine();
    } catch (IOException e) {
      System.out.println("Error reading input: " + e.getMessage());
    }

    return input;
  }

  private Util() {}
}
