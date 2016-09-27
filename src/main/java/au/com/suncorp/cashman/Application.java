package au.com.suncorp.cashman;

import au.com.suncorp.cashman.enumeration.Denomination;
import au.com.suncorp.cashman.helper.CommandInterpreter;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        final String PROMPT = "> ";
        Scanner input = new Scanner(System.in);

        Controller controller = new Controller();
        CommandInterpreter commandInterpreter = new CommandInterpreter(controller);

        int count;
        for (Denomination denomination : Denomination.values()) {
            System.out.printf("> Enter the number of %s: ", denomination.name());
            count = input.nextInt();
            controller.add(denomination, count);
        }
        while (commandInterpreter.isReadyToParseCommand()) {
            System.out.print(PROMPT);
            commandInterpreter.parseCommand(input.next());
        }
    }
}
