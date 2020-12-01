import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class LambdaStepsTest {

            Credentials сredentials = new Credentials();
            String user = сredentials.getUser();
            String password = сredentials.getPassword();
            String IssueTitle = "Разобраться с домашним заданием";
            String[] labels = {"help wanted", "question"};
            String repository = "dianamishanich/qa_guru_3_5";

            @Test
            public void createIssueTest() {


            step("Открываем github.com", () -> {
                open("https://github.com/");
            });

            step("Осуществляем авторизацию", () -> {
                $("[href='/login']").click();
                $("#login_field").val(user);
                $("#password").val(password);
                $(".btn.btn-primary.btn-block").click();
            });


            step("Открываем список репозиториев пользователя",() -> {
                $("[data-ga-click='Header, show menu, icon:avatar']").click();
                $(byText("Your repositories")).click();
            });

            step("Открываем нужный репозиторий " + repository,() -> {
                $("[href='/dianamishanich/qa_guru_3_5']").click();
            });

            step("Открываем раздел Issues и кликаем на Create New Issues",() -> {
                $("[href='/dianamishanich/qa_guru_3_5/issues']").click();
                $$(byText("New issue")).find(Condition.visible).parent().click();
            });

            step("Вводим название Issue " + IssueTitle,() -> {
                $("#issue_title").val(IssueTitle);
            });

            step("Открываем меню Assignees и назначаем Issue на пользоваталя из списка",() -> {
                $("#assignees-select-menu summary").click();
                $(".js-username").click();
                $(".select-menu-item").pressEscape();
            });

            step("Открываем меню Labels и выбираем теги",() -> {
                $("#labels-select-menu").$("summary").click();
                for (String label : labels) {
                    $(byText(label)).click();
                }
                $("#labels-select-menu").$("summary").pressEscape();
            });

            step("Нажимаем на Submit New Issue",() -> {
                    $(byText("Submit new issue")).scrollTo().click();
            });

            step("Для проверки находим репозиторий " + repository,() -> {
                $(".header-search-input").click();
                $(".header-search-input").sendKeys(repository);
                $(".header-search-input").submit();
            });

            step("Переходим в репозиторий " + repository,() -> {
                $(By.linkText(repository)).click();
            });

            step("Переходим в раздел Issues", () -> {
                    $(withText("Issues")).click();
            });
            step("Проверяем наличие Issue с названием " + IssueTitle, () -> {

                $(withText(IssueTitle)).should(Condition.exist);
            });

        }

}