package Library;

import com.sun.xml.internal.ws.util.StringUtils;

import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;

//add {"name": , "author": , "size": , "location":{"cupboard": , "shelf": }}

public class Book implements Comparable<Book>
{
    private String name;
    private String author;
    private int size;
    private BookLocation location;
    private ZonedDateTime zonedDateTime;

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
                writer.write(a.name + "," + a.author + "," + a.size + "," + a.location.cupboard + "," + a.location.shelf + "," + a.zonedDateTime.toString() + "\n");
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
                book.setZonedDateTime(ZonedDateTime.parse(values[5]));
                collection.add(book);
            }

            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public String toString(ZoneId zoneId)
    {
       // return "NAME: " + name + ", AUTHOR: " + author + ", " + size + " PAGES, LOCATION: CUPBOARD " + location.cupboard + ", SHELF " + location.shelf +  " "
                //+ zonedDateTime.withZoneSameInstant(zoneId).format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm")) +"\n";
        //          + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(zonedDateTime.withZoneSameInstant(zoneId)) +"\n";
        return String.format("| %23s | %15s | %4d | %5d | %5d | %15s |\n", name, author, size, location.cupboard, location.shelf, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(zonedDateTime.withZoneSameInstant(zoneId)));
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

    public ZonedDateTime getZonedDateTime()
    {
        return zonedDateTime;
    }

    public void changeZone(ZoneId zoneId)
    {
        this.zonedDateTime = zonedDateTime.withZoneSameInstant(zoneId);
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime)
    {
        this.zonedDateTime = zonedDateTime;
    }

    @Override
    public int compareTo(Book o)
    {
        return this.name.compareTo(o.getName());
    }

}