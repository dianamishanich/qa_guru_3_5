import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class SelenideIssuesTest {

    Credentials сredentials = new Credentials();
    String user = сredentials.getUser();
    String password = сredentials.getPassword();
    String IssueTitle = "Разобраться с домашним заданием";
    String[] labels = { "help wanted", "question" };
    String repository = "dianamishanich/qa_guru_3_5";

    @Test
        public void createIssueTest() {

        Configuration.startMaximized = true;

        open("https://github.com");
            $(byText("Sign in")).click();
            $(("#login_field")).val(user);
            $("#password").val(password);
            $(".btn.btn-primary.btn-block").click();
            $("[data-ga-click='Header, show menu, icon:avatar']").click();
            $(byText("Your repositories")).click();
            $("[href='/dianamishanich/qa_guru_3_5']").click();
            $("[href='/dianamishanich/qa_guru_3_5/issues']").click();
            $$(byText("New issue")).find(Condition.visible).parent().click();
            $("#issue_title").val(IssueTitle);
            $("#assignees-select-menu summary").click();
            $(".js-username").click();
            $(".select-menu-item").pressEscape();

        $("#labels-select-menu").$("summary").click();

        for (String label : labels) {
            $(byText(label)).click();
        }

        $("#labels-select-menu").$("summary").pressEscape();
        $(byText("Submit new issue")).click();



            $(".header-search-input").click();
            $(".header-search-input").sendKeys(repository);
            $(".header-search-input").submit();

            $(By.linkText(repository)).click();
            $(withText("Issues")).click();
            $$("[data-hovercard-type='issue']").find(text(IssueTitle)).shouldHave(Condition.exist);
    }


}