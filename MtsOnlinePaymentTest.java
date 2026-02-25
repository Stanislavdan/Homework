import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MtsOnlinePaymentTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://www.mts.by/";

    @BeforeAll
    void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get(BASE_URL);

        closeCookieBannerIfPresent();
        waitForPaymentBlock();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void closeCookieBannerIfPresent() {
        try {
            By cookieButton = By.id("cookie-agree");
            wait.until(ExpectedConditions.elementToBeClickable(cookieButton)).click();
        } catch (TimeoutException ignored) {
        }
    }

    private void waitForPaymentBlock() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pay-section")));
    }

    private WebElement getPaymentSection() {
        return driver.findElement(By.id("pay-section"));
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    @Test
    @DisplayName("Проверка названия блока 'Онлайн пополнение без комиссии'")
    void testPaymentBlockTitle() {

        WebElement section = getPaymentSection();

        WebElement title = section.findElement(
                By.xpath(".//h2[contains(normalize-space(.),'Онлайн пополнение')]")
        );

        scrollIntoView(title);

        String actualText = title.getText()
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();

        assertEquals("Онлайн пополнение без комиссии", actualText);
    }

    @Test
    @DisplayName("Проверка наличия логотипов платёжных систем")
    void testPaymentLogosPresence() {

        WebElement section = getPaymentSection();
        scrollIntoView(section);

        By logosLocator = By.cssSelector("#pay-section .pay__partners img");

        wait.until(ExpectedConditions.presenceOfElementLocated(logosLocator));

        List<WebElement> logos = driver.findElements(logosLocator);

        assertFalse(logos.isEmpty(), "Логотипы не найдены");

        for (WebElement logo : logos) {
            assertTrue(logo.isDisplayed(), "Логотип не отображается");
            assertNotNull(logo.getAttribute("src"), "У логотипа отсутствует src");
        }
    }

    @Test
    @DisplayName("Проверка ссылки 'Подробнее о сервисе'")
    void testDetailsLink() {

        WebElement section = getPaymentSection();

        WebElement link = section.findElement(
                By.xpath(".//a[contains(normalize-space(.),'Подробнее о сервисе')]")
        );

        scrollIntoView(link);
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();

        wait.until(ExpectedConditions.urlContains("poryadok-oplaty"));

        assertTrue(driver.getCurrentUrl().contains("poryadok-oplaty"),
                "Редирект по ссылке не произошёл");
    }

    @Test
    @DisplayName("Проверка формы 'Услуги связи' и кнопки 'Продолжить'")
    void testCommunicationServicesForm() {

        WebElement form = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("pay-connection"))
        );

        scrollIntoView(form);

        WebElement phoneInput = form.findElement(By.id("connection-phone"));
        WebElement sumInput = form.findElement(By.id("connection-sum"));
        WebElement emailInput = form.findElement(By.id("connection-email"));

        phoneInput.clear();
        phoneInput.sendKeys("297777777");

        sumInput.clear();
        sumInput.sendKeys("15");

        emailInput.clear();
        emailInput.sendKeys("test@test.com");

        WebElement continueButton = form.findElement(
                By.xpath(".//button[contains(normalize-space(.),'Продолжить')]")
        );

        wait.until(ExpectedConditions.elementToBeClickable(continueButton));

        continueButton.click();

        By iframeLocator = By.cssSelector("iframe[src*='bepaid']");
        wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));

        List<WebElement> iframes = driver.findElements(iframeLocator);

        assertFalse(iframes.isEmpty(),
                "Платёжный iframe не появился после нажатия 'Продолжить'");
    }
}