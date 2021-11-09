package worldofzuul;
import java.util.HashMap;


public class CommandWords
{
    //Attributes
    private HashMap<String, CommandWord> validCommands;

    //Constructor
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandWord>();
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return CommandWord.UNKNOWN;
        }
    }

    //Checks if string is command word
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    //Displays all commands
    public void showAll() 
    {
        for(String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
