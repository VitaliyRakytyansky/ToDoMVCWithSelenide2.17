package helpers;

import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Selenide.actions;

/**
 * Created by Winterfall on 17.04.2016.
 */
public class Helpers {

    public static void doubleClick(SelenideElement element){
        actions().doubleClick(element).perform();
    }
}
