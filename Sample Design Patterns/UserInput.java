import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {

    public static String getUserInput(String prompt) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        System.out.println(prompt);
        try {
            input = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return input;
    }
}
