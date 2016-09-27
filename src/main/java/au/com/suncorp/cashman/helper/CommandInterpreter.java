package au.com.suncorp.cashman.helper;

import au.com.suncorp.cashman.Controller;

public class CommandInterpreter {

    private Controller controller;
    private boolean isReadyToParseCommand;
    private final String USAGE = "Usage: ";

    public CommandInterpreter(Controller controller) {
        this.isReadyToParseCommand = true;
        this.controller = controller;
    }

    public void parseCommand(String command) {
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

    public boolean isReadyToParseCommand() {
        return isReadyToParseCommand;
    }

    public void printUsage() {
        System.out.println(USAGE);
    }

}
