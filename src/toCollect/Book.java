package toCollect;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class Book implements Comparable<Book>
{
    private String name;
    private String author;
    private int numOfPages;
    private BookLocation location;
    private Date creationDate;

    public Book()
    {
        location = new BookLocation();
        creationDate = new Date();
    }

    public static void writeCollectionToFile(Collection<Book> collection, String fileName)
    {
        try
        {
            FileWriter writer = new FileWriter(fileName);
            writer.close();
            writer = new FileWriter(fileName);

            for(Book a : collection)
            {
                writer.write(a.name + "," + a.author + "," + a.numOfPages + "," + a.location.cupboard + "," + a.location.shelf + "," + a.creationDate.toString() + "\n");
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void readCollectionFromFile(Collection<Book> collection, String fileName)
    {
        String separator = ",";
        String line;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

            while ((line = reader.readLine()) != null && line.length() > 2)
            {
                Book book = new Book();
                String[] values = line.split(separator);
                book.setName(values[0]);
                book.setAuthor(values[1]);
                book.setNumOfPages(Integer.valueOf(values[2]));
                book.setLocation(Integer.valueOf(values[3]), Integer.valueOf(values[4]));
                book.setCreationDate(new Date());
                collection.add(book);
            }

            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    @Override
    public int compareTo(Book o)
    {
        return this.name.compareTo(o.getName());
    }

}
