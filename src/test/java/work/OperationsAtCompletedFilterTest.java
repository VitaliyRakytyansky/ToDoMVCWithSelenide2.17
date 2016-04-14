package work;

import org.junit.Test;
import org.openqa.selenium.Keys;
import pages.TodoMVCPage;

import static com.codeborne.selenide.Selenide.$;
import static pages.TodoMVCPage.TaskType.ACTIVE;
import static pages.TodoMVCPage.TaskType.COMPLETED;

public class OperationsAtCompletedFilterTest extends BaseTest{

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testAddAtCompleted() {
        page.givenAtCompleted(ACTIVE, "a");

        page.add("b");
        page.assertNoVisibleTask();
        page.assertItemsLeft(2);
    }

    @Test
    public void testEditAtCompleted() {
        page.givenAtCompleted(page.aTask("a", COMPLETED), page.aTask("b", ACTIVE));

        page.startEdit("a", "a edit").sendKeys(Keys.ENTER);
        page.assertVisibleTasks("a edit");
        page.assertItemsLeft(1);
    }

    @Test
    public void testDeleteAtCompleted() {
        page.givenAtCompleted(page.aTask("a", ACTIVE), page.aTask("b", COMPLETED));

        page.delete("b");
        page.assertItemsLeft(1);
    }

    @Test
    public void testCompleteAllAtCompleted() {
        page.givenAtCompleted(ACTIVE, "a", "b");

        page.toggleAll();
        page.assertItemsLeft(0);
    }

    @Test
    public void testReopenAllAtCompletedAndMoveToActive() {
        page.givenAtCompleted(page.aTask("a", COMPLETED), page.aTask("b", COMPLETED));

        page.toggleAll();
        page.assertNoVisibleTask();
        page.assertItemsLeft(2);

        page.filterActive();
        page.assertVisibleTasks("a", "b");
    }

    @Test
    public void testCancelEditByEscAtCompleted() {
        page.given(page.aTask("a", COMPLETED), page.aTask("b", COMPLETED));

        page.startEdit("a", "a edit cancelled").sendKeys(Keys.ESCAPE);
        page.assertTasks("a", "b");
        page.assertItemsLeft(0);
    }

    @Test
    public void testEditByPressTabAtCompleted() {
        page.givenAtCompleted(COMPLETED, "a", "b");

        page.startEdit("a", "a edited").sendKeys(Keys.TAB);
        page.assertTasks("a edited", "b");
        page.assertItemsLeft(0);
    }

    @Test
    public void testEditByPressOutsideAtCompleted() {
        page.givenAtCompleted(page.aTask("a", ACTIVE), page.aTask("b", COMPLETED));


        page.startEdit("b", "b edited");
        $("#header").click();
        page.assertVisibleTasks("b edited");
        page.assertItemsLeft(1);
    }

    @Test
    public void testEditByRemovalTextAtCompleted() {
        page.givenAtCompleted(page.aTask("a", COMPLETED));

        page.startEdit("a", "").sendKeys(Keys.ENTER);
        page.assertNoVisibleTask();
    }
}