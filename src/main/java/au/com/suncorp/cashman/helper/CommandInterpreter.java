package au.com.suncorp.cashman.helper;

import au.com.suncorp.cashman.controller.FundsController;

import java.math.BigDecimal;

public class CommandInterpreter {

    private FundsController fundsController;
    private boolean isReadyToParseCommand;
    private final String USAGE = "Usage: withdraw [amount]\nreport\nquit\n";

    public CommandInterpreter(FundsController fundsController) {
        this.isReadyToParseCommand = true;
        this.fundsController = fundsController;
    }

    public void parseCommand(String command) {
        if (command.startsWith("withdraw ")) {
            command = command.replace("withdraw ", "");
            BigDecimal amount = new BigDecimal(Integer.parseInt(command));
            fundsController.withdraw(amount);
        } else {
            switch (command) {
                case "report":
                    fundsController.reportAll();
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

    public boolean isReadyToParseCommand() {
        return isReadyToParseCommand;
    }

    public void printUsage() {
        System.out.println(USAGE);
    }

}
