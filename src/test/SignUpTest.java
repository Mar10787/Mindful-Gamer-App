import com.example.mindfulgamer.model.SqliteUserDAO;
import com.example.mindfulgamer.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SignUpTest {
    private SqliteUserDAO SignUp = new SqliteUserDAO();

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$"; //Contains 1 digit, one uppercase letter, and at least 8 characters long
        return password.matches(regex);
    }
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$"; // Contains characters before @ symbol, the @ symbol, letters after @ symbol, "." and letters after ".".
        return email.matches(regex);
    }
    private boolean isEmailExist(String input_email){
        for (User user: users){
            String email = user.getEmail();
            if (email.equals(input_email)){
                return true;
            }
        }
        return false;
    }








    private User[] users = {
            new User("Martino", "Nguyen", "", "martino@gmail.com","Mar10787"),
            new User("Marty", "Tree", "", "marty@gmail.com","Password101"),
            new User("Mar", "Plant", "", "plant@example.com","EnteringDung101")
    };
    // Email Validation
    // Email contains characters before @ symbol, the @ symbol, letters after @ symbol, "." and letters after ".".
    String validEmail = "marty@gmail.com";
    String EmailNoAt = "martygmail.com";
    String EmailNoAtNoDot = "marty";
    String EmailAtNoDot = "marty@gmail";
    String EmailDotNoAfterString = "marty@gmail.";

    @Test
    public void TestEmailNoSpecialChar(){
        assertEquals(false, isValidEmail(EmailNoAt));
    }
    @Test
    public void TestEmailWithSpecialCharNoDot() {
        assertEquals(false, isValidEmail(EmailNoAtNoDot));
    }
    @Test
    public void TestEmailWithoutDot(){
        assertEquals(false, isValidEmail(EmailAtNoDot));
    }
    @Test
    public void TestEmailWithDotNoStringAfter(){
        assertEquals(false, isValidEmail(EmailDotNoAfterString));
    }
    @Test
    public void TestValidEmail(){
        assertEquals(true, isValidEmail(validEmail));
    }

    // Password Validation
    // Contains 1 digit, one uppercase letter, and at least 8 characters long
    // When testing a condition, all other conditions are met for simplicity

    String validPass = "Mar10787";
    String invalidPass = "";
    String PassNoDigit = "Abcdefgh";
    String PassNoUpper = "abcdefg1";
    String PassNot8Char = "A4c";

    @Test
    public void TestPassNull(){
        assertEquals(false, isValidPassword(invalidPass));
    }
    @Test
    public void TestPassValid(){
        assertEquals(true, isValidPassword(validPass));
    }
    @Test
    public void TestPassNoDigit(){
        assertEquals(false, isValidPassword(PassNoDigit));
    }
    @Test
    public void TestPassNoUpper(){
        assertEquals(false, isValidPassword(PassNoUpper));
    }
    @Test
    public void TestPassNo8Length(){
        assertEquals(false, isValidPassword(PassNot8Char));
    }

    // Testing to method used to check if email already exists in database

    String input_email1 = "martino@gmail.com";
    String input_email2 = "apple@outlook.com";
    String input_email3 = "plant@example.com";

    @Test
    public void testisEmailExistForExistingEmail_input1(){
        assertEquals(true, isEmailExist(input_email1));
    }
    @Test
    public void testisEmailExistForExistingEmail_input2(){
        assertEquals(false, isEmailExist(input_email2));
    }
    @Test
    public void testisEmailExistForExistingEmail_input3(){
        assertEquals(true, isEmailExist(input_email3));
    }



}
