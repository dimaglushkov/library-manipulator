import Library.LibraryManipulator;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class Tests
{
    private String toAdd[] = new String[4];
    private LibraryManipulator lm;

    @Before
    public void initialize()
    {
        toAdd[0]  = "{\"name\":\"A\", \"author\":\"B\", \"size\":1, \"location\":{\"cupboard\":1, \"shelf\":1}}\n";
        toAdd[1] = "{\"name\":\"B\", \"author\":\"A\", \"size\":123, \"location\":{\"cupboard\":2, \"shelf\":2}}\n";
        toAdd[2] = "{\"name\":\"C\", \"author\":\"A\", \"size\":1, \"location\":{\"cupboard\":1, \"shelf\":2}}\n";
        toAdd[3] = "{\"name\":\"D\", \"author\":\"AD\", \"size\":100, \"location\":{\"cupboard\":1, \"shelf\":231}}\n";
        String path = "temp.txt";
        lm = new LibraryManipulator(new PriorityQueue<>(), path);
    }


    @Test
    public void Test1()
    {
        String toAddWrong = "{kappapride : {";
        assertTrue((lm.add(toAdd[0]) && lm.add(toAdd[1])));
        assertFalse(lm.add(toAddWrong));
        assertTrue((lm.add(toAdd[2]) && lm.add(toAdd[3])));

    }

    @Test
    public void Test2()
    {
        for(String it : toAdd)
            lm.add(it);
        assertTrue(lm.remove_lower("{\"name\" : \"B\"}"));
        assertTrue(lm.remove_lower("{\"name\" : \"0\"}"));
        assertTrue(lm.remove_lower("{\"name\" : \"Z\"}"));

    }

    @Test
    public void Test3()
    {
        for(String it : toAdd)
            lm.add(it);
        assertTrue(lm.setZone("hu"));
        assertFalse(lm.setZone("kappa"));
        assertTrue(lm.setZone("ru"));
    }

    @Test
    public void Test4()
    {
        for(String it : toAdd)
            lm.add(it);

        assertTrue(lm.remove("{\"name\":\"D\", \"author\":\"AD\", \"size\":100}"));
        assertFalse(lm.remove("{\"name\":\"D\", \"MISTAKE\":\"AD\", \"size\":100}"));
        assertTrue(lm.remove("{\"name\":\"asfasf\", \"author\":\"A123123\", \"size\":0100}"));
    }

}
