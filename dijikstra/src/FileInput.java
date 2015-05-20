/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for reading input from file
 * @author Sudarsan
 * 
 */

public class FileInput 
{
    FileReader freader = null;
    BufferedReader breader = null;
    int NoofLines;
    /**
     * Default Constructor
     */
    public FileInput()
    {
        
    }
    
    /**
     * Function for getting the number of lines from the given file
     * @param FileName - Path of the file name
     * @return - The Number of lines in the file
     * @throws IOException 
     */
    public int getLineNumber(String FileName) throws IOException
    {
        int NoofLines=0;
        try
        {
        
        freader = new FileReader(FileName);
        breader = new BufferedReader(freader);
        
        while((breader.readLine() != null))
        {
            NoofLines++;
        }
        
        breader.close();
        
        }
         catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(IOException e)
        {
           throw e;
        }
        return NoofLines;
    }
    
    /**
     * Function for reading the input from the file
     * @param FileName - Path of the file name
     * @return - The Contents of the file in a String array
     * @throws IOException 
     */

    public String[] readFileInput(String FileName) throws IOException
    {
   
        
        NoofLines = getLineNumber(FileName);
        String[] inputFile = new String[NoofLines];

        try
        {

            freader = new FileReader(FileName);
            breader = new BufferedReader(freader);
            
            
            for(int i=0;i<NoofLines;i++)
            {
                inputFile[i] = breader.readLine();
            }
            
            breader.close();
       
        }

        catch(FileNotFoundException e)
        {
            throw e;
        }
        catch(IOException e)
        {
           throw e;
        }
        return inputFile;
    }
    
}