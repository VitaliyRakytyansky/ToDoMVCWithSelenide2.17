package work;

import org.junit.After;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class BaseTest {
    @After
    public void clearData(){
        executeJavaScript("localStorage.clear()");
    }


}
