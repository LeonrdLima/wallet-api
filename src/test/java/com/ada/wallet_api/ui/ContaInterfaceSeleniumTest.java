package com.ada.wallet_api.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContaInterfaceSeleniumTest {
    
    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
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
        abrirPaginaDeposito();
        preencherFormularioDeposito("1", "150.00");
        clicarBotaoDepositar();
    
        String mensagem = obterMensagemResultado();
        pausarParaVisualizacao();

        assertEquals("Depósito de R$ 150.00 realizado com sucesso!", mensagem);
    }

    private void abrirPaginaDeposito() {
        driver.get("http://localhost:" + port + "/deposito.html");
    }

    private void preencherFormularioDeposito(String contaId, String valor) {
        driver.findElement(By.id("contaId")).sendKeys(contaId);
        driver.findElement(By.id("valor")).sendKeys(valor);
    }

    private void clicarBotaoDepositar() {
        driver.findElement(By.id("btnDepositar")).click();
    }

    private String obterMensagemResultado() {
        WebElement mensagem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensagem")));
        return mensagem.getText();
    }

    private void pausarParaVisualizacao() {
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("A pausa para visualização foi interrompida", e);
        }
    }

}
