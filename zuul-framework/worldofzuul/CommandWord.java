package worldofzuul;

public enum CommandWord
{

    //Creates keywords
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"),
    NEXT("next");
    
    private String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public String toString()
    {
        return commandString;
    }
}
