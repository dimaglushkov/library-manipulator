package Library;

import java.io.*;
import java.util.Collection;

public class Book implements Comparable<Book>
{
    private String name;
    private String author;
    private int size;
    private BookLocation location;

    public Book()
    {
        location = new BookLocation();
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
                writer.write(a.name + "," + a.author + "," + a.size + "," + a.location.cupboard + "," + a.location.shelf + "\n");
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
                book.setSize(Integer.valueOf(values[2]));
                book.setLocation(Integer.valueOf(values[3]), Integer.valueOf(values[4]));
                collection.add(book);
            }

            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public String toString()
    {
        return "NAME: " + name + ", AUTHOR: " + author + ", " + size + " PAGES, LOCATION: CUPBOARD " + location.cupboard + ", SHELF " + location.shelf + "\n";
    }

    public String toJSONString()
    {
        return "{\"name\":\"" + name + "\", \"author\":\"" + author + "\", \"size\":" + size + ", \"location\":{\"cupboard\":" + location.cupboard + ", \"shelf\":" + location.shelf + "}}\n";
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

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
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


    @Override
    public int compareTo(Book o)
    {
        return this.name.compareTo(o.getName());
    }

}