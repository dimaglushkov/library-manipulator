package toCollect;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.Date;

public class Book
{
    private String name;
    private String author;
    private int numOfPages;
    private BookLocation location;
    private ZonedDateTime creationDate;

    public Book()
    {
        location = new BookLocation();
        creationDate = new ZonedDateTime()
    }

    public void write(String fileName)
    {
        try
        {
            FileWriter writer = new FileWriter(fileName);
            writer.write(name + "," + author + "," + numOfPages + ", \"" + location.cupboard + "," + location.shelf + "\"," + printDate.toString());
        }
        catch (IOException ignored)
        {
        }
    }

    public void read(String filename) throws FileNotFoundException
    {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public int getNumOfPages()
    {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages)
    {
        this.numOfPages = numOfPages;
    }

    public BookLocation getLocation()
    {
        return location;
    }

    public void setLocation(int cupboard, int shelf)
    {
        this.location.cupboard = cupboard;
        this.location.shelf = shelf;
    }

    public ZonedDateTime getCreationDate()
    {
        return creationDate;
    }


}
