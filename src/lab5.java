import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Scanner;

//JSON TO TEST add / remove add {"name" : "My book", "author" : "Alex Xela" , "location": {"cupboard": 2, "shelf" : 5 }, "size" : 123}


public class lab5
{
    public static void main(String[] args)
    {
        String fileName;
        try
        {
            fileName = args[0];
        } catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.print("Enter file name: ");
            Scanner in = new Scanner(System.in);
            fileName = in.nextLine();
        }

        File file = new File(fileName);
        if (!file.exists())
        {
            System.out.print("This file does not exist");
            return;
        }


        PriorityQueue<Book> queue = new PriorityQueue<>(20, Book::compareTo);
        Book.readCollectionFromFile(queue, fileName);
        Scanner scanner = new Scanner(System.in);
        String command;
        JSONParser parser = new JSONParser();

        System.out.print("WELCOME TO LIBRARY MANAGER.\n(type quit to exit)\n=============================\n");

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
                    System.out.print("Collection type: PriorityQueue\nCurrently " + queue.size() + " elements stored\n" + "Storage: " + fileName + "\nFormat: csv\n");
                    break;


                case ("show"):
                    for (Book a : queue)
                        System.out.print(a.toString());
                    break;


                case ("remove_lower"):
                    do
                    {
                        nextWord = scanner.next();
                        bookJSONString += nextWord + " ";
                        if (nextWord.contains("{"))
                            numOfJSONObjects++;
                        if (nextWord.contains("}"))
                            numOfJSONObjects--;

                    }
                    while (numOfJSONObjects != 0);
                    try
                    {
                        bookJSON = (JSONObject) parser.parse(bookJSONString);
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                        break;
                    }
                    Book bookRemoveLower = new Book();
                    try
                    {
                        bookRemoveLower.setName(bookJSON.get("name").toString());
                    } catch (NullPointerException e)
                    {
                        System.out.print("Set \"name\" value \n");
                    }
                    int oldSize = queue.size();
                    queue.removeIf((Book a) -> a.compareTo(bookRemoveLower) < 0);
                    System.out.print(oldSize - queue.size() + " elements removed\n");
                    break;


                case ("remove_first"):
                    queue.poll();
                    System.out.print("First element removed\n");
                    break;


                case ("remove_all"):
                    queue.clear();
                    System.out.print("Collection cleared!\n");
                    break;

                case ("add"):
                    do
                    {
                        nextWord = scanner.next();
                        bookJSONString += nextWord + " ";
                        if (nextWord.contains("{"))
                            numOfJSONObjects++;
                        if (nextWord.contains("}"))
                            numOfJSONObjects--;

                    }
                    while (numOfJSONObjects != 0);
                    try
                    {
                        bookJSON = (JSONObject) parser.parse(bookJSONString);
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                        break;
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
                        break;
                    }
                    queue.offer(bookToAdd);
                    System.out.print("Element added!\n");
                    break;

                case ("remove"):
                    do
                    {
                        nextWord = scanner.next();
                        bookJSONString += nextWord + " ";
                        if (nextWord.contains("{"))
                            numOfJSONObjects++;
                        if (nextWord.contains("}"))
                            numOfJSONObjects--;

                    }
                    while (numOfJSONObjects != 0);
                    try
                    {
                        bookJSON = (JSONObject) parser.parse(bookJSONString);
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                        break;
                    }
                    Book bookToRemove = new Book();
                    try
                    {
                        bookToRemove.setName(bookJSON.get("name").toString());
                        bookToRemove.setAuthor(bookJSON.get("author").toString());
                        bookToRemove.setSize(Integer.valueOf(bookJSON.get("size").toString()));
                    } catch (NullPointerException e)
                    {
                        e.printStackTrace();
                        System.out.print("Please fill name, author, size fields\n");
                        break;
                    }
                    queue.remove(bookToRemove);
                    System.out.print("Element removed!\n");
                    break;

                case ("quit"):
                    break;


                default:
                    System.out.print("Wrong command\n");
                    break;

            }
        }
        while (!command.equals("quit"));

        Book.writeCollectionToFile(queue, fileName);
    }
}
