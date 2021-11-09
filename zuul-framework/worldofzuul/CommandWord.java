package worldofzuul;

public enum CommandWord
{

    //Creates keywords
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?");
    
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
