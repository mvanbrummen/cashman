package au.com.suncorp.cashman.helper;

import au.com.suncorp.cashman.controller.FundsController;
import au.com.suncorp.cashman.exceptions.CurrencyCombinationException;
import au.com.suncorp.cashman.exceptions.InsufficientFundsException;

import java.math.BigDecimal;

public class CommandInterpreter {

    private FundsController fundsController;
    private boolean isReadyToParseCommand;
    private final String USAGE = "Usage: \n\twithdraw [amount]\n\treport\n\tquit\n";

    public CommandInterpreter(FundsController fundsController) {
        this.isReadyToParseCommand = true;
        this.fundsController = fundsController;
    }

    public void parseCommand(String command) throws InsufficientFundsException, CurrencyCombinationException,
            NumberFormatException {
        if (command.startsWith("withdraw ")) {
            command = command.replace("withdraw ", "");
            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(command));
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
