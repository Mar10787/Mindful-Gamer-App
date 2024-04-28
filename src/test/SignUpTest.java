import com.example.mindfulgamer.controller.SignUpController;
import com.example.mindfulgamer.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SignUpTest {
    private SignUpController SignUp = new SignUpController();

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
        boolean valid = SignUp.isValidEmail(EmailNoAt);
        assertEquals(false, valid);
    }
    @Test
    public void TestEmailWithSpecialCharNoDot() {
        boolean valid = SignUp.isValidEmail(EmailNoAtNoDot);
        assertEquals(false, valid);
    }
    @Test
    public void TestEmailWithoutDot(){
        boolean valid = SignUp.isValidEmail(EmailAtNoDot);
        assertEquals(false, valid);
    }
    @Test
    public void TestEmailWithDotNoStringAfter(){
        boolean valid = SignUp.isValidEmail(EmailDotNoAfterString);
        assertEquals(false, valid);
    }
    @Test
    public void TestValidEmail(){
        boolean valid = SignUp.isValidEmail(validEmail);
        assertEquals(true, valid);
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
        boolean valid = SignUp.isValidPassword(invalidPass);
        assertEquals(false, valid);
    }
    @Test
    public void TestPassValid(){
        boolean valid = SignUp.isValidPassword(validPass);
        assertEquals(true, valid);
    }
    @Test
    public void TestPassNoDigit(){
        boolean valid = SignUp.isValidPassword(PassNoDigit);
        assertEquals(false, valid);
    }
    @Test
    public void TestPassNoUpper(){
        boolean valid = SignUp.isValidPassword(PassNoUpper);
        assertEquals(false, valid);
    }
    @Test
    public void TestPassNo8Length(){
        boolean valid = SignUp.isValidPassword(PassNot8Char);
        assertEquals(false, valid);
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
