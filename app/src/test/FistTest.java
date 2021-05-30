import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class FistTest {

    AndroidDriver<AndroidElement> driver;

    public void setUp() throws MalformedURLException{
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("deviceName","Android Emulator");
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("appPackage","EZCode.Pantallas");
        capabilities.setCapability("appActivity","EZCode.Pantallas.PantallaAutenticacion");
        capabilities.setCapability("noReset",true);
        driver = new AndroidDriver<AndroidElement>(new URL("http://localhost:4723/wd/hub"),capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    @Test
    public void testAutenticacion() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test Autenticacion");
        MobileElement el2 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoCorreo");
        el2.sendKeys("dani.monsalveg@gmail.com");
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoPassword");
        el3.sendKeys("ricardo milos");
        MobileElement el4 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonInicioSesion");
        el4.click();
        Assert.assertEquals("Horario de Daniel Monsalve",driver.findElementById("EZCode.Pantallas:id/textView7").getText());
        MobileElement e15 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonCerrarSesion");
        e15.click();
    }

    @After
    public void End(){driver.quit();}
}
