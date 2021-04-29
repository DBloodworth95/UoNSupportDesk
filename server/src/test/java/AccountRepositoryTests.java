import account.Account;
import account.ProfilePicture;
import org.junit.Test;
import repository.AccountRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountRepositoryTests {
    @Test
    public void accountFindTest() {
        //Find an account with the email of "dan@admin.com".
        Account account = AccountRepository.find("dan@admin.com", "admin");
        //Test passes if the accounts email is "dan@admin.com"
        assertEquals("dan@admin.com", account.getEmail());
    }

    @Test
    public void getAccountHolderNameTest() {
        //Get the name of the Account with an ID of one (Dan).
        String name = AccountRepository.getNameOfAccountHolder(1);
        //Test passes if the name equals Dan.
        assertEquals("Dan", name);
    }

    @Test
    public void submitProfilePictureTest() {
        byte[] dummyPicture = new byte[0]; //Create dummy byte array
        //Submit the byte array to a dummy acount with an ID of 10.
        ProfilePicture profilePicture = AccountRepository.submitProfilePicture(10, dummyPicture);
        //Test passes if the profile picture returned matches the dummy byte array.
        assertEquals(profilePicture.getPicture(), dummyPicture);
    }

    @Test
    public void accountFindFailedTest() {
        //Attempt to find an account which doesn't exist (should return null).
        Account account = AccountRepository.find("notaname", "notapassword");
        //Test passes if the account is null.
        assertNull(account);
    }

    @Test
    public void getAccountHolderNameFailedTest() {
        //Get the name of an account holder with the ID of 999 (doesn't exist).
        String name = AccountRepository.getNameOfAccountHolder(999);
        //Test passes if the name returned is "empty".
        assertEquals("empty", name);
    }
}

