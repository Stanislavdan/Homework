import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MtsOnlinePaymentTest {

    private WebDriver driver;
    private PaymentBlockPage paymentBlock;
    private static final String BASE_URL = "https://www.mts.by/";

    @BeforeAll
    void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        closeCookieBannerIfPresent();
        paymentBlock = new PaymentBlockPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    private void closeCookieBannerIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("cookie-agree"))).click();
        } catch (TimeoutException ignored) {}
    }

    @Test
    @DisplayName("1. Название блока")
    void testPaymentBlockTitle() {
        assertEquals("Онлайн пополнение без комиссии", paymentBlock.getTitleText());
    }

    @Test
    @DisplayName("2. Логотипы платёжных систем")
    void testPaymentLogosPresence() {
        List<WebElement> logos = paymentBlock.getLogos();
        assertFalse(logos.isEmpty(), "Логотипы не найдены");
        logos.forEach(logo -> {
            assertTrue(logo.isDisplayed(), "Логотип не отображается");
            assertNotNull(logo.getAttribute("src"), "Нет атрибута src");
        });
    }

    @Test
    @DisplayName("3. Ссылка 'Подробнее о сервисе'")
    void testDetailsLink() {
        paymentBlock.clickDetailsLink();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("poryadok-oplaty"));
        assertTrue(driver.getCurrentUrl().contains("poryadok-oplaty"));
    }

    @Test
    @DisplayName("4. Плейсхолдеры во всех вкладках")
    void testPlaceholdersInAllTabs() {
        String[] tabs = {"Услуги связи", "Домашний интернет", "Рассрочка", "Задолженность"};
        for (String tab : tabs) {
            List<String> actual = paymentBlock.getActualPlaceholders(tab);
            List<String> expected = paymentBlock.getExpectedPlaceholders(tab);
            assertEquals(expected, actual, "Ошибка во вкладке: " + tab);
        }
    }

    @Test
    @DisplayName("5. Форма 'Услуги связи' и проверка iframe")
    void testCommunicationFormAndIframe() {
        paymentBlock.selectTab("Услуги связи");
        paymentBlock.fillCommunicationForm("297777777", "15", "test@test.com");
        paymentBlock.clickContinue();

        PaymentIframePage iframe = new PaymentIframePage(driver);
        iframe.switchTo();

        String amountDesc = iframe.getAmountInDescription();
        assertTrue(amountDesc.contains("15.00"), "Сумма в описании не совпадает: " + amountDesc);
        String amountBtn = iframe.getAmountOnButton();
        assertTrue(amountBtn.contains("15.00"), "Сумма на кнопке не совпадает: " + amountBtn);

        String phoneText = iframe.getDisplayedPhone();
        assertTrue(phoneText.contains("297777777"), "Номер телефона не отображается: " + phoneText);

        assertTrue(iframe.areCardFieldsDisplayed(), "Не все поля карты отображаются");
        assertTrue(iframe.areCardLabelsCorrect(), "Не все лейблы полей карты корректны");
        assertTrue(iframe.arePaymentIconsDisplayed(), "Иконки платёжных систем не отображаются");

        iframe.switchToDefault();
    }
}

class PaymentBlockPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final By PAY_SECTION = By.id("pay-section");
    private static final By TITLE = By.xpath(".//h2[contains(normalize-space(.),'Онлайн пополнение')]");
    private static final By LOGOS = By.cssSelector("#pay-section .pay__partners img");
    private static final By MORE_INFO_LINK = By.xpath(".//a[contains(normalize-space(.),'Подробнее о сервисе')]");
    
    private static final By SELECT_HEADER = By.cssSelector("#pay-section .select__header");
    private static final By SELECT_LIST = By.cssSelector("#pay-section .select__list");
    private static final By SELECT_NOW = By.cssSelector("#pay-section .select__now");

    private static final Map<String, List<By>> TAB_FIELDS = Map.of(
            "Услуги связи", List.of(By.id("connection-phone"), By.id("connection-sum"), By.id("connection-email")),
            "Домашний интернет", List.of(By.id("internet-phone"), By.id("internet-sum"), By.id("internet-email")),
            "Рассрочка", List.of(By.id("score-instalment"), By.id("instalment-sum"), By.id("instalment-email")),
            "Задолженность", List.of(By.id("score-arrears"), By.id("arrears-sum"), By.id("arrears-email"))
    );

    private static final Map<String, List<String>> EXPECTED_PLACEHOLDERS = Map.of(
            "Услуги связи", List.of("Номер телефона", "Сумма", "E-mail для отправки чека"),
            "Домашний интернет", List.of("Номер абонента", "Сумма", "E-mail для отправки чека"),
            "Рассрочка", List.of("Номер счета на 44", "Сумма", "E-mail для отправки чека"),
            "Задолженность", List.of("Номер счета на 2073", "Сумма", "E-mail для отправки чека")
    );

    PaymentBlockPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(PAY_SECTION));
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    private boolean isTabSelected(String tabName) {
        WebElement now = driver.findElement(SELECT_NOW);
        return now.getText().trim().equals(tabName);
    }

    public void selectTab(String tabName) {
        if (isTabSelected(tabName)) {
            return;
        }

        WebElement header = wait.until(ExpectedConditions.elementToBeClickable(SELECT_HEADER));
        scrollIntoView(header);
        header.click();

        WebElement list = wait.until(ExpectedConditions.visibilityOfElementLocated(SELECT_LIST));
        By optionLocator = By.xpath(String.format(".//li[contains(@class,'select__item')]//p[text()='%s']", tabName));
        WebElement option = list.findElement(optionLocator);
        scrollIntoView(option);
        option.click();

        wait.until(ExpectedConditions.invisibilityOf(list));
        List<By> fields = TAB_FIELDS.get(tabName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(fields.get(0)));
    }

    public List<String> getActualPlaceholders(String tabName) {
        selectTab(tabName);
        return TAB_FIELDS.get(tabName).stream()
                .map(loc -> driver.findElement(loc).getAttribute("placeholder"))
                .collect(Collectors.toList());
    }

    public List<String> getExpectedPlaceholders(String tabName) {
        return EXPECTED_PLACEHOLDERS.get(tabName);
    }

    public String getTitleText() {
        WebElement title = driver.findElement(PAY_SECTION).findElement(TITLE);
        scrollIntoView(title);
        return title.getText().replace("\n", " ").replaceAll("\\s+", " ").trim();
    }

    public List<WebElement> getLogos() {
        WebElement section = driver.findElement(PAY_SECTION);
        scrollIntoView(section);
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOS));
        return driver.findElements(LOGOS);
    }

    public void clickDetailsLink() {
        WebElement link = driver.findElement(PAY_SECTION).findElement(MORE_INFO_LINK);
        scrollIntoView(link);
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();
    }

    public void fillCommunicationForm(String phone, String sum, String email) {
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("connection-phone")));
        WebElement sumInput = driver.findElement(By.id("connection-sum"));
        WebElement emailInput = driver.findElement(By.id("connection-email"));

        fillField(phoneInput, phone);
        fillField(sumInput, sum);
        fillField(emailInput, email);
    }

    private void fillField(WebElement field, String value) {
        scrollIntoView(field);
        wait.until(ExpectedConditions.elementToBeClickable(field));
        field.click();
        field.clear();
        field.sendKeys(value);
        field.sendKeys(Keys.TAB);
    }

    public void clickContinue() {
        By continueButtonLocator = By.cssSelector("#pay-connection button[type='submit']");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(continueButtonLocator));
        scrollIntoView(button);
        button.click();
    }
}

class PaymentIframePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final By IFRAME = By.cssSelector("iframe[src*='bepaid']");

    private static final By AMOUNT_IN_DESCRIPTION = By.cssSelector(".pay-description__cost span");
    private static final By AMOUNT_ON_BUTTON = By.cssSelector("button.colored span");
    private static final By PHONE_DISPLAY = By.cssSelector(".pay-description__text span");

    private static final By CARD_NUMBER_INPUT = By.id("cc-number");
    private static final By EXPIRY_INPUT = By.cssSelector("input[formcontrolname='expirationDate']");
    private static final By CVC_INPUT = By.cssSelector("input[formcontrolname='cvc']");
    private static final By CARDHOLDER_INPUT = By.cssSelector("input[formcontrolname='holder']");

    private static final By CARD_NUMBER_LABEL = By.xpath("//label[text()='Номер карты']");
    private static final By EXPIRY_LABEL = By.xpath("//label[text()='Срок действия']");
    private static final By CVC_LABEL = By.xpath("//label[text()='CVC']");
    private static final By CARDHOLDER_LABEL = By.xpath("//label[text()='Имя и фамилия на карте']");

    private static final By PAYMENT_ICONS = By.cssSelector(".cards-brands img");

    PaymentIframePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void switchTo() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IFRAME));
    }

    public void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    public String getAmountInDescription() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(AMOUNT_IN_DESCRIPTION)).getText();
    }

    public String getAmountOnButton() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(AMOUNT_ON_BUTTON)).getText();
    }

    public String getDisplayedPhone() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PHONE_DISPLAY)).getText();
    }

    public boolean areCardFieldsDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CARD_NUMBER_INPUT)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(EXPIRY_INPUT)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(CVC_INPUT)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(CARDHOLDER_INPUT)).isDisplayed();
    }

    public boolean areCardLabelsCorrect() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CARD_NUMBER_LABEL)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(EXPIRY_LABEL)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(CVC_LABEL)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(CARDHOLDER_LABEL)).isDisplayed();
    }

    public boolean arePaymentIconsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PAYMENT_ICONS));
        } catch (TimeoutException e) {
            return false;
        }
        List<WebElement> icons = driver.findElements(PAYMENT_ICONS);
        return !icons.isEmpty();
    }
}