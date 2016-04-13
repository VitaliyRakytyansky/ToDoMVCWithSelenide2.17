package work;


import org.junit.Test;
import pages.TodoMVCPage;

import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.$;
import static pages.TodoMVCPage.TaskType.ACTIVE;
import static pages.TodoMVCPage.TaskType.COMPLETED;



public class OperationsAtActiveFilterTest extends BaseTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testEditAtActive() {
        page.givenAtActive(page.aTask("a", ACTIVE), page.aTask("b", COMPLETED));

        page.startEdit("a", "a edit").sendKeys(Keys.ENTER);
        page.assertVisibleTasks("a edit");
        page.assertItemsLeft(1);
    }

    @Test
    public void testDeleteAtActive() {
        page.givenAtActive(page.aTask("a", COMPLETED), page.aTask("b", ACTIVE));

        page.delete("b");
        page.assertItemsLeft(0);
    }

    @Test
    public void testClearCompleteAtActive() {
        page.givenAtActive(page.aTask("a", ACTIVE), page.aTask(("b"), COMPLETED));

        page.clearCompleted();
        page.assertTasks("a");
        page.assertItemsLeft(1);
    }

    @Test
    public void testReopenAllAtActiveAndMoveToAll() {
        page.givenAtActive(COMPLETED, "a", "b");

        page.toggleAll();
        page.assertItemsLeft(2);

        page.filterAll();
        page.assertTasks("a", "b");
    }

    @Test
    public void testCancelEditByEscAtActive() {
        page.givenAtActive(ACTIVE, "a");

        page.startEdit("a", "a edit cancelled").sendKeys(Keys.ESCAPE);
        page.assertTasks("a");
        page.assertItemsLeft(1);
    }

    @Test
    public void testEditByPressTabAtActive() {
        page.givenAtActive(ACTIVE, "a");

        page.startEdit("a", "a edited").sendKeys(Keys.TAB);
        page.assertTasks("a edited");
        page.assertItemsLeft(1);
    }

    @Test
    public void testEditByPressOutsideAtActive() {
        page.givenAtActive(ACTIVE, "a", "b");

        page.startEdit("a", "a edited");
        $("#header").click();
        page.assertVisibleTasks("a edited", "b");
        page.assertItemsLeft(2);
    }

    @Test
    public void testEditByRemovalTextAtActive() {
        page.givenAtActive(ACTIVE, "a", "b");

        page.startEdit("a", "").sendKeys(Keys.ENTER);
        page.assertTasks("b");
        page.assertItemsLeft(1);
    }

}