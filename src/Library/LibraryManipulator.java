package Library;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class LibraryManipulator implements Serializable
{
    private PriorityQueue<Book> collection;
    private String fileName;
    private Scanner scanner;
    private ZoneId zoneId;
    private Locale locale;
    private ResourceBundle bundle;


    public LibraryManipulator(PriorityQueue<Book> collection, String fileName)
    {
        this.collection = collection;
        this.fileName = fileName;
        this.zoneId = ZoneId.of("Europe/Moscow");
        this.locale = new Locale("ru", "RU");
        this.bundle = ResourceBundle.getBundle("resources.lang", locale);
        for (Book a: collection)
            a.changeZone(zoneId);
         
    }

    public void start()
    {
        scanner = new Scanner(System.in);
        String command;
        System.out.print("WELCOME TO LIBRARY MANAGER.\ncommands: show, show_json, add{}, remove{}, remove_first, remove_lower{}, zone, quit\n");
        info();
        do
        {
            System.out.print("> ");
            command = scanner.next();
            switch (command)
            {

                case ("info"):
                    info();
                    break;

                case ("show"):
                    show();
                    break;

                case ("show_json"):
                    show_json();
                    break;

                case ("remove_lower"):
                    if(!remove_lower(scan_JSON()))
                        System.err.print("Error while removing lower");
                    break;

                case ("remove_first"):
                    remove_first();
                    break;

                case ("add"):
//                    if (!add(scan_JSON()))
                    try{
                        add(scan_JSON());
                    }
                    catch (Exception e)
                    {
                        System.err.print(e.getMessage());
                    }
                    break;

                case ("remove"):
                    if (!remove(scan_JSON()))
                        System.err.print("Error while removing");
                    break;

                case ("quit"):
                    break;

                case ("zone"):
                    if (!setZone(scanner.next()))
                        System.err.print("Wrong zone");
                    break;


                default:
                    System.out.print("Wrong command\n");
                    break;

            }
        }
        while (!command.equals("quit"));

    }


    public boolean setZone(String zoneName)
    {
        switch (zoneName)
        {
            case("ru"):
                locale = new Locale("ru", "RU");
                zoneId = ZoneId.of("Europe/Moscow");
                break;

            case("nz"):
                locale = new Locale("en", "NZ");
                zoneId = ZoneId.of("NZ");
                break;

            case("sk"):
                locale = new Locale("sk");
                zoneId = ZoneId.of("Europe/Bratislava");
                break;

            case("hu"):
                locale = new Locale("hu");
                zoneId = ZoneId.of("Europe/Budapest");
                break;

            default:
                System.err.print("Wrong zone name\n");
                return false;
        }
        this.bundle = ResourceBundle.getBundle("resources.lang", locale);
        for (Book a: collection)
            a.changeZone(zoneId);
        System.out.print("Zone changed successfully!\n");
        return true;
    }


    private void show()
    {
        System.out.printf("| %23s | %15s | %5s | %6s | %6s | %15s |\n",
                bundle.getObject("name"),
                bundle.getObject("author"),
                bundle.getObject("size"),
                bundle.getObject("cupboard"),
                bundle.getObject("shelf"),
                bundle.getObject("created"));
        for (Book a : collection)
            System.out.print(a.toString(zoneId));
    }

    private void info()
    {
        System.out.print("----------------------------\n| Collection type: PriorityQueue\n| " + collection.size() + " elements stored\n" + "| Storage: " + fileName + "\n| Format: csv\n| Current localization: " + zoneId.toString() + "\n| Available zones: \n|\tru - Russia (Moscow)\n|\tnz - New Zealand\n|\tsk - Slovakia (Bratislava)\n|\thu - Hungary (Budapest)\n----------------------------\n");
    }

    private void show_json()
    {
        for (Book a : collection)
            System.out.print(a.toJSONString());
    }

    public boolean remove_lower(String bookJSONString)
    {
        JSONParser parser = new JSONParser();
        JSONObject bookJSON;

        try
        {
            bookJSON = (JSONObject) parser.parse(bookJSONString);
        } catch (ParseException e)
        {
            e.printStackTrace();
            return false;
        }
        Book bookRemoveLower = new Book();
        try
        {
            bookRemoveLower.setName(bookJSON.get("name").toString());
        } catch (NullPointerException e)
        {
            System.out.print("Set \"name\" value \n");
            return false;
        }
        int oldSize = collection.size();
        collection.removeIf((Book a) -> a.compareTo(bookRemoveLower) < 0);
        System.out.print(oldSize - collection.size() + " elements removed\n");
        return true;
    }

    private String scan_JSON()
    {
        String nextWord;
        StringBuilder bookJSONString = new StringBuilder();
        int numOfJSONObjects = 0;
        do
        {
            nextWord = scanner.next();
            bookJSONString.append(nextWord).append(" ");
            if (nextWord.contains("{"))
                numOfJSONObjects += StringUtils.countMatches(nextWord, "{");
            if (nextWord.contains("}"))
                numOfJSONObjects -= StringUtils.countMatches(nextWord, "}");
        }
        while (numOfJSONObjects != 0);
        return bookJSONString.toString();
    }

    public boolean add(String bookJSONString) throws Exception
    {
        JSONParser parser = new JSONParser();
        JSONObject bookJSON;

        try
        {
            bookJSON = (JSONObject) parser.parse(bookJSONString);
        } catch (ParseException e)
        {
            throw new Exception("Error while parsing JSON object\n");
        }
        Book bookToAdd = new Book();
        try
        {
            bookToAdd.setName(bookJSON.get("name").toString());
            bookToAdd.setAuthor(bookJSON.get("author").toString());
            bookToAdd.setSize(Integer.valueOf(bookJSON.get("size").toString()));
            JSONObject locationJSON = (JSONObject) bookJSON.get("location");
            bookToAdd.setLocation(Integer.valueOf(locationJSON.get("cupboard").toString()), Integer.valueOf(locationJSON.get("shelf").toString()));
            bookToAdd.setZonedDateTime(ZonedDateTime.now());
        } catch (NullPointerException e)
        {
            System.err.print("Wrong input format\n");
            return false;
        }
        collection.offer(bookToAdd);
        System.out.print("Element added!\n");
        return true;
    }

    public boolean remove(String bookJSONString)
    {
        JSONParser parser = new JSONParser();
        JSONObject bookJSON;
        try
        {
            bookJSON = (JSONObject) parser.parse(bookJSONString);
        } catch (ParseException e)
        {
            e.printStackTrace();
            return false;
        }
        Book bookToRemove = new Book();
        try
        {
            bookToRemove.setName(bookJSON.get("name").toString());
            bookToRemove.setAuthor(bookJSON.get("author").toString());
            bookToRemove.setSize(Integer.valueOf(bookJSON.get("size").toString()));
        } catch (NullPointerException e)
        {
            System.err.print("Please fill name, author, size fields\n");
            return false;
        }
        collection.remove(bookToRemove);
        System.out.print("Element removed!\n");
        return true;
    }

    private void remove_first()
    {
        collection.poll();
        System.out.print("First element removed\n");
    }
}
