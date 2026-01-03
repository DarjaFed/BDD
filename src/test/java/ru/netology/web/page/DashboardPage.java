package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardPage {
    private final String balanceStart = "баланс:";
    private final String balanceFinish = " р.";
    private final SelenideElement heading = $("[data-test-id='dashboard']");
    private final ElementsCollection cards = $$(".list__item");
    private final SelenideElement reloadButton = $("[data-test-id='action-reload']");


    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = getCard(cardInfo).getText();
        return extractBalance(text);
    }
   // public int getCardBalance(int index) {
     //   var text = cards.get(index).getText();
       // return extractBalance(text);
    // }
    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        getCard(cardInfo).$("button").click();
        return new TransferPage();
    }
    private SelenideElement getCard(DataHelper.CardInfo cardInfo) {
        return cards.findBy(Condition.text(cardInfo.getLastDigits()));

    }
    public void reloadDashboardPage() {
        reloadButton.click();
        heading.shouldBe(visible);
    }
    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish).trim();
        return Integer.parseInt(value);
    }
    public void checkCardBalance(DataHelper.CardInfo cardInfo, int expectedBalance) {
        int actualBalance = getCardBalance(cardInfo);
        assertEquals(expectedBalance, actualBalance);

    }
}