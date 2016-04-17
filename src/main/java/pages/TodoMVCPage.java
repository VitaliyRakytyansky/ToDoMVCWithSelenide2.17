package pages;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.WebDriverRunner.url;
import static helpers.Helpers.doubleClick;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;


public class TodoMVCPage {

    public ElementsCollection tasks = $$("#todo-list>li");

    public void add(String... tasksTexts) {
        for (String task : tasksTexts) {
            $("#new-todo").shouldBe(enabled).setValue(task).pressEnter();
        }
    }

    public void delete(String taskText) {
        tasks.find(exactText(taskText)).hover();
        tasks.find(exactText(taskText)).$(".destroy").click();
    }

    public SelenideElement startEdit(String oldTaskText, String newTaskText) {
        doubleClick((tasks.find(exactText(oldTaskText)).find("label")));
        return tasks.findBy(cssClass("editing")).$(".edit").setValue(newTaskText);
    }

    public void clearCompleted() {
        $("#clear-completed").click();
    }

    public void toggle(String taskText) {
        tasks.findBy(exactText(taskText)).$(".toggle").click();
    }

    public void toggleAll() {
        $("#toggle-all").click();
    }

    public void assertVisibleTasks(String... tasksTexts) {
        tasks.filter(visible).shouldHave(exactTexts(tasksTexts));
    }

    public void assertTasks(String... tasksTexts) {
        tasks.shouldHave(exactTexts(tasksTexts));
    }

    public void assertNoVisibleTask() {
        tasks.filter(visible).shouldHave(empty);
    }

    public void assertNoTask() {
        tasks.shouldBe(empty);
    }

    public void filterAll() {
        $(By.linkText("All")).click();
    }

    public void filterActive() {
        $(By.linkText("Active")).click();
    }

    public void filterCompleted() {
        $(By.linkText("Completed")).click();
    }

    public void assertItemsLeft(Integer count) {
        $("#todo-count>strong").shouldHave(exactText(String.valueOf(count)));
    }

    //==============
    //Helpers Block
    //==============

    public enum TaskType {
        COMPLETED("true"), ACTIVE("false");

        public String flag;

        TaskType(String flag) {
            this.flag = flag;
        }

        public String getFlag() {
            return flag;
        }
    }

    public class Task {
        String taskText;
        TaskType taskType;

        public Task(String taskText, TaskType taskType) {
            this.taskText = taskText;
            this.taskType = taskType;
        }
    }

    public Task aTask(String taskText, TaskType taskType) {
        return new Task(taskText, taskType);
    }

    public void given(Task... tasks) {
        if (!(url().equals("https://todomvc4tasj.herokuapp.com/")))
            open("https://todomvc4tasj.herokuapp.com/");

        String elements = "";
        if (tasks.length != 0) {
            for (Task task : tasks) {
                elements += "{\\\"completed\\\":" + task.taskType.getFlag() + ", \\\"title\\\":\\\"" + task.taskText + "\\\"},";
            }
            elements = elements.substring(0, elements.length() - 1);
        }
        executeJavaScript("localStorage.setItem(\"todos-troopjs\", \"[" + elements + "]\")");
        refresh();
    }

    public void givenAtAll(Task... tasks) {
        given(tasks);
    }

    public void givenAtActive(Task... tasks) {
        given(tasks);
        filterActive();
    }

    public void givenAtCompleted(Task... tasks) {
        given(tasks);
        filterCompleted();
    }

    public void givenAtAll(TaskType taskType, String... taskTexts) {
        Task[] tasks = new Task[taskTexts.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new Task(taskTexts[i], taskType);
        }
        given(tasks);
    }

    public void givenAtActive(TaskType taskType, String... taskTexts) {
        givenAtAll(taskType, taskTexts);
        filterActive();
    }

    public void givenAtCompleted(TaskType taskType, String... taskTexts) {
        givenAtAll(taskType, taskTexts);
        filterCompleted();
    }

}