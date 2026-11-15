package com.ada.wallet_api.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContaInterfaceSeleniumTest {
    
    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Deve realizar deposito com sucesso através da interface web")
    void deveRealizarDepositoComSucessoPelaInterface() {
        String url = "http://localhost:" + port + "/deposito.html";
        driver.get(url);

        WebElement campoContaId = driver.findElement(By.id("contaId"));
        WebElement campoValor = driver.findElement(By.id("valor"));
        WebElement botaoDepositar = driver.findElement(By.id("btnDepositar"));

        campoContaId.sendKeys("1");
        campoValor.sendKeys("150.00");
        botaoDepositar.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement mensagemResultado = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensagem")));
    
        assertEquals("Depósito de R$ 150.00 realizado com sucesso!", mensagemResultado.getText());
    }

}
