package Library;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.PriorityQueue;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class LibraryManipulator
{
    private PriorityQueue<Book> collection;
    private String fileName;
    private Scanner scanner;

    public LibraryManipulator(PriorityQueue<Book> collection, String fileName)
    {
        this.collection = collection;
        this.fileName = fileName;
    }

    public void start()
    {
        scanner = new Scanner(System.in);
        String command;
        System.out.print("WELCOME TO LIBRARY MANAGER.\ncommands: show, show_json, add{}, remove{}, remove_first, remove_lower{}, quit\n=============================\n");

        do
        {
            System.out.print(">");
            command = scanner.next();
            String bookJSONString = "";
            JSONObject bookJSON;
            String nextWord;
            int numOfJSONObjects = 0;
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
                    remove_lower();
                    break;

                case ("remove_first"):
                    remove_first();
                    break;

                case ("add"):
                    add();
                    break;

                case ("remove"):
                    remove();
                    break;

                case ("quit"):
                    break;

                default:
                    System.out.print("Wrong command\n");
                    break;

            }
        }
        while (!command.equals("quit"));

    }

    private void show()
    {
        for (Book a : collection)
            System.out.print(a.toString());
    }

    private void info()
    {
        System.out.print("Collection type: PriorityQueue\nCurrently " + collection.size() + " elements stored\n" + "Storage: " + fileName + "\nFormat: csv\n");
    }

    private void show_json()
    {
        for (Book a : collection)
            System.out.print(a.toJSONString());
    }

    private void remove_lower()
    {
        Scanner scanner = new Scanner(System.in);
        JSONParser parser = new JSONParser();
        JSONObject bookJSON;
        String nextWord, bookJSONString ="";
        int numOfJSONObjects = 0;
        do
        {
            nextWord = scanner.next();
            bookJSONString += nextWord + " ";
            if (nextWord.contains("{"))
                numOfJSONObjects += StringUtils.countMatches(nextWord, "{");
            if (nextWord.contains("}"))
                numOfJSONObjects -= StringUtils.countMatches(nextWord, "}");
        }
        while (numOfJSONObjects != 0);
        try
        {
            bookJSON = (JSONObject) parser.parse(bookJSONString);
        } catch (ParseException e)
        {
            e.printStackTrace();
            return;
        }
        Book bookRemoveLower = new Book();
        try
        {
            bookRemoveLower.setName(bookJSON.get("name").toString());
        } catch (NullPointerException e)
        {
            System.out.print("Set \"name\" value \n");
            return;
        }
        int oldSize = collection.size();
        collection.removeIf((Book a) -> a.compareTo(bookRemoveLower) < 0);
        System.out.print(oldSize - collection.size() + " elements removed\n");
    }

    private void add()
    {
        JSONParser parser = new JSONParser();
        JSONObject bookJSON;
        String nextWord, bookJSONString ="";
        int numOfJSONObjects = 0;
        do
        {
            nextWord = scanner.next();
            bookJSONString += nextWord + " ";
            if (nextWord.contains("{"))
                numOfJSONObjects += StringUtils.countMatches(nextWord, "{");
            if (nextWord.contains("}"))
                numOfJSONObjects -= StringUtils.countMatches(nextWord, "}");
        }
        while (numOfJSONObjects != 0);
        try
        {
            bookJSON = (JSONObject) parser.parse(bookJSONString);
        } catch (ParseException e)
        {
            e.printStackTrace();
            return;
        }
        Book bookToAdd = new Book();
        try
        {
            bookToAdd.setName(bookJSON.get("name").toString());
            bookToAdd.setAuthor(bookJSON.get("author").toString());
            bookToAdd.setSize(Integer.valueOf(bookJSON.get("size").toString()));
            JSONObject locationJSON = (JSONObject) bookJSON.get("location");
            bookToAdd.setLocation(Integer.valueOf(locationJSON.get("cupboard").toString()), Integer.valueOf(locationJSON.get("shelf").toString()));
        } catch (NullPointerException e)
        {
            e.printStackTrace();
            System.out.print("Please fill every field\n");
            return;
        }
        collection.offer(bookToAdd);
        System.out.print("Element added!\n");
    }

    private void remove()
    {
        JSONParser parser = new JSONParser();
        JSONObject bookJSON;
        String nextWord, bookJSONString ="";
        int numOfJSONObjects = 0;
        do
        {
            nextWord = scanner.next();
            bookJSONString += nextWord + " ";
            if (nextWord.contains("{"))
                numOfJSONObjects += StringUtils.countMatches(nextWord, "{");
            if (nextWord.contains("}"))
                numOfJSONObjects -= StringUtils.countMatches(nextWord, "}");
        }
        while (numOfJSONObjects != 0);
        try
        {
            bookJSON = (JSONObject) parser.parse(bookJSONString);
        } catch (ParseException e)
        {
            e.printStackTrace();
            return;
        }
        Book bookToRemove = new Book();
        try
        {
            bookToRemove.setName(bookJSON.get("name").toString());
            bookToRemove.setAuthor(bookJSON.get("author").toString());
            bookToRemove.setSize(Integer.valueOf(bookJSON.get("size").toString()));
        } catch (NullPointerException e)
        {
            System.out.print("Please fill name, author, size fields\n");
            return;
        }
        collection.remove(bookToRemove);
        System.out.print("Element removed!\n");
    }

    private void remove_first()
    {
        collection.poll();
        System.out.print("First element removed\n");
    }
}
