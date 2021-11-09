package worldofzuul;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Parser 
{

    //Attributes
    private CommandWords commands;
    private Scanner reader;

    //Constructor
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    //Methods
    public Command getCommand() 
    {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> "); 

        //Creates inputline from scanner.nextLine()
        inputLine = reader.nextLine();

        //Creates scanner called tokenizer, takes inputLine
        Scanner tokenizer = new Scanner(inputLine);

        //Takes first word of tokenizer and equals to word 1
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();

            //Takes second word of tokenizer, if there is second word, and equals to word 2
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next(); 
            }
        }
        tokenizer.close();

        //Returns command with commandword word1, and second word word2
        return new Command(commands.getCommandWord(word1), word2);
    }

    public void showCommands()
    {
        commands.showAll();
    }
}
