package au.com.suncorp.cashman.helper;

import au.com.suncorp.cashman.Controller;

import java.math.BigDecimal;

public class CommandInterpreter {

    private Controller controller;
    private boolean isReadyToParseCommand;
    private final String USAGE = "Usage: "; // TODO finish this

    public CommandInterpreter(Controller controller) {
        this.isReadyToParseCommand = true;
        this.controller = controller;
    }

    public void parseCommand(String command) {
        if (controller.isInitialised()) {
            if (command.startsWith("withdraw ")) {
                command = command.replace("withdraw ", "");
                BigDecimal amount = new BigDecimal(Integer.parseInt(command));
                controller.withdraw(amount);
            } else {
                switch (command) {
                    case "report":
                        controller.reportAll();
                        break;
                    case "quit":
                        isReadyToParseCommand = false;
                        break;
                    case "help":
                        printUsage();
                        break;
                }
            }
        }
    }

    public boolean isReadyToParseCommand() {
        return isReadyToParseCommand;
    }

    public void printUsage() {
        System.out.println(USAGE);
    }

}
