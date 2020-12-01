import com.codeborne.selenide.Condition;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.withText;

public class AnnotationStepsTest {

    Credentials credentials = new Credentials();
    String user = credentials.getUser();
    String password = credentials.getPassword();
    String IssueTitle = "Разобраться с домашним заданием";
    String[] labels = {"help wanted", "question"};
    String repository = "/dianamishanich/qa_guru_3_5";

    @Test
    @DisplayName("Тест с аннотациями")
    @Feature("Issues")
    @Story("User should see issues in existing repository")
    @Link(url = "https://github.com")
    @Owner("dianamishanich")
    @Severity(SeverityLevel.CRITICAL)
    public void creatingNewIssue() {
        final IssuesBaseSteps steps = new IssuesBaseSteps();

        steps.openMainPage();
        steps.LogIn(user, password);
        steps.OpenListRepositories(repository);
        steps.GoToRepository(repository);
        steps.CreateNewIssues(repository);
        steps.IssueNameInput(IssueTitle);
        steps.AssignAssignees();
        steps.SelectLabels(labels);
        steps.SubmitNewIssue();
        steps.SearchRepository(repository);
        steps.GoToRepository(repository);
        steps.GoToIssues();
        steps.shouldSeeIssueWithTitle(IssueTitle);
    }

    public static class IssuesBaseSteps {

        @Step("Открываем github.com")
        public void openMainPage() {
            open("https://github.com/");
        }

        @Step("Осуществляем авторизацию")
        public void LogIn(String user, String password) {
            $("[href='/login']").click();
            $("#login_field").val(user);
            $("#password").val(password);
            $(".btn.btn-primary.btn-block").click();
        }

        @Step("Открываем список репозиториев пользователя")
        public void OpenListRepositories(String repository) {
            $("[data-ga-click='Header, show menu, icon:avatar']").click();
            $(byText("Your repositories")).click();
        }

        @Step("Открываем нужный репозиторий ")
        public void GoToRepository(String repository) {
            $("[href='"+ repository +"']").click();
        }

        @Step("Открываем раздел Issues и кликаем на Create New Issues")
        public void CreateNewIssues(String repository) {
            $("[href='" + repository + "/issues']").click();
            $$(byText("New issue")).find(Condition.visible).parent().click();
        }

        @Step("Вводим название Issue ")
        public void IssueNameInput(String IssueTitle) {
            $("#issue_title").val(IssueTitle);
        }

        @Step("Открываем меню Assignees и назначаем Issue на пользоваталя из списка")
        public void AssignAssignees() {
            $("#assignees-select-menu summary").click();
            $(".js-username").click();
            $(".select-menu-item").pressEscape();
        }

        @Step("Открываем меню Labels и выбираем теги")
        public void SelectLabels(String [] labels) {
            $("#labels-select-menu").$("summary").click();
            for (String label : labels) {
                $(byText(label)).click();
            }
            $("#labels-select-menu").$("summary").pressEscape();
        }

        @Step("Нажимаем на Submit New Issue")
        public void SubmitNewIssue() {
            $(byText("Submit new issue")).scrollTo().click();
        }

        @Step("Для проверки находим репозиторий ")
        public void SearchRepository(String repository) {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(repository);
            $(".header-search-input").submit();
        }

        @Step("Переходим в раздел Issues")
        public void GoToIssues() {
            $(withText("Issues")).click();
        }

        @Step("Проверяем наличие Issue с названием ")
        public void shouldSeeIssueWithTitle(String IssueTitle) {
            $(withText(IssueTitle)).should(Condition.exist);
        }

    }
}
