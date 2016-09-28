package au.com.suncorp.cashman;

import au.com.suncorp.cashman.controller.FundsController;
import au.com.suncorp.cashman.enumeration.Note;
import au.com.suncorp.cashman.helper.CommandInterpreter;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        final String PROMPT = "> ";
        Scanner input = new Scanner(System.in);

        FundsController fundsController = new FundsController();
        CommandInterpreter commandInterpreter = new CommandInterpreter(fundsController);

        int count;
        for (Note denomination : Note.values()) {
            System.out.printf("> Enter the number of %s: ", denomination.name());
            count = input.nextInt();
            fundsController.add(denomination, count);
        }
        while (commandInterpreter.isReadyToParseCommand()) {
            System.out.print(PROMPT);
            try {
                commandInterpreter.parseCommand(input.next());
            } catch (Exception e) {
                commandInterpreter.printUsage();
            }
        }
    }
}
