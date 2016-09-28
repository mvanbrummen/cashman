package au.com.suncorp.cashman.helper;


import au.com.suncorp.cashman.controller.FundsController;
import org.junit.Assert;
import org.junit.Test;

public class CommandInterpreterTest {

    @Test
    public void whenInitialisedThenReadyToParseCommandTrue() {
        CommandInterpreter commandInterpreter = new CommandInterpreter(new FundsController());
        Assert.assertTrue(commandInterpreter.isReadyToParseCommand());
    }

    @Test
    public void whenQuitThenReadyToParseCommandFalse() {
        CommandInterpreter commandInterpreter = new CommandInterpreter(new FundsController());
        commandInterpreter.parseCommand("quit");
        Assert.assertFalse(commandInterpreter.isReadyToParseCommand());
    }

}
