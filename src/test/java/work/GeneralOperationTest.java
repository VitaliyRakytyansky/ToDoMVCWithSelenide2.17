package work;

import org.junit.Test;
import org.openqa.selenium.Keys;
import pages.TodoMVCPage;


public class GeneralOperationTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testTasksLifeCycle() {

        page.givenAtAll();
        page.add("1");
        page.startEdit("1", "a").sendKeys(Keys.ENTER);

        //complete
        page.toggle("a");
        page.assertTasks("a");

        page.filterActive();
        page.assertNoVisibleTask();

        page.add("b");
        page.assertVisibleTasks("b");
        page.assertItemsLeft(1);

        //complete all
        page.toggleAll();
        page.assertNoVisibleTask();

        page.filterCompleted();
        page.assertTasks("a", "b");

        //reopen task
        page.toggle("b");
        page.assertVisibleTasks("a");

        page.clearCompleted();
        page.assertNoVisibleTask();

        page.filterAll();
        page.assertTasks("b");

        page.delete("b");
        page.assertNoTask();
    }
}