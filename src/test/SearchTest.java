import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.Test;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {
    private ObservableList<String> games = FXCollections.observableArrayList("Warframe", "League of Legends", "Warframe", "Warframe", "Overwatch", "League of Legends");

    private ObservableList<String> fetchAllGameNames(){
        HashSet<String> uniqueGameNames = new HashSet<>(games);
        ObservableList<String> uniqueGameNamesList = FXCollections.observableArrayList(uniqueGameNames);
        return uniqueGameNamesList;
    }

    private boolean search(String input){
        String game = input.toLowerCase();
        for (int i = 0; i < games.size();i++){
            String currentElement = games.get(i).toLowerCase();
            if (currentElement.equals(game)){
                return true;
            }
        }
        return false;
    }

    @Test
    public void TestNameDuplicates(){
        assertEquals(3, fetchAllGameNames().size());
    }

    @Test
    public void TestGameNames(){
        assertEquals("League of Legends", fetchAllGameNames().get(0));
        assertEquals("Warframe", fetchAllGameNames().get(1));
        assertEquals("Overwatch", fetchAllGameNames().get(2));
    }

    // Testing the search
    @Test
    public void TestGameFound(){
        assertTrue(search("League of Legends"));
        assertTrue(search("Warframe"));
        assertTrue(search("Overwatch"));
    }
    @Test
    public void TestGameNotFound(){
        assertTrue(!search("Call of Duty"));
    }
    @Test
    public void TestGameLowerAndUpper(){
        assertTrue(search("league of legends"));
        assertTrue(search("Warframe"));
        assertTrue(search("overwatch"));
    }
}

