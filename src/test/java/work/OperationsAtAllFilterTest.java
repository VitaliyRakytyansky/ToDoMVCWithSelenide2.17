package work;


import org.junit.Test;
import org.openqa.selenium.Keys;
import pages.TodoMVCPage;

import static com.codeborne.selenide.Selenide.$;
import static pages.TodoMVCPage.TaskType.ACTIVE;
import static pages.TodoMVCPage.TaskType.COMPLETED;


public class OperationsAtAllFilterTest  {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testCompleteAllAtAll() {
        page.givenAtAll(ACTIVE, "a", "b");

        page.toggleAll();
        page.assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtAll() {
        page.given(page.aTask("a", COMPLETED),
                page.aTask("b", ACTIVE),
                page.aTask("c", COMPLETED));

        page.clearCompleted();
        page.assertTasks("b");
        page.assertItemsLeft(1);
    }

    @Test
    public void testReopenAllAtAllAndMoveToCompleted() {
        page.givenAtAll(COMPLETED, "a", "b");

        page.toggleAll();
        page.assertTasks("a", "b");
        page.assertItemsLeft(2);

        page.filterCompleted();
        page.assertNoVisibleTask();
    }

    @Test
    public void testCancelEditByEscAtAll() {
        page.given(page.aTask("a", ACTIVE), page.aTask("b", COMPLETED));

        page.startEdit("a", "a edit cancelled").sendKeys(Keys.ESCAPE);
        page.assertTasks("a", "b");
        page.assertItemsLeft(1);
    }

    @Test
    public void testEditByPressTabAtAll() {
        page.given(page.aTask("a", COMPLETED), page.aTask("b", ACTIVE));

        page.startEdit("a", "a edited").sendKeys(Keys.TAB);
        page.assertTasks("a edited", "b");
        page.assertItemsLeft(1);
    }

    @Test
    public void testEditByPressOutsideAtAll() {
        page.givenAtAll(ACTIVE, "a");

        page.startEdit("a", "a edited");
        $("#header").click();
        page.assertVisibleTasks("a edited");
        page.assertItemsLeft(1);
    }

    @Test
    public void testEditByRemovalTextAtAll() {
        page.given(page.aTask("a", COMPLETED), page.aTask("b", ACTIVE));

        page.startEdit("a", "").sendKeys(Keys.ENTER);
        page.assertTasks("b");
        page.assertItemsLeft(1);
    }

}