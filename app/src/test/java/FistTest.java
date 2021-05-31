import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void A_testRegistro() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test Registro");
        MobileElement el1 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonRegistro");
        el1.click();
        MobileElement el2 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/NombreRegistro");
        el2.sendKeys("Jose Daniel");
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/CorreoRegistro");
        el3.sendKeys("joded@yahoo.com");
        MobileElement el4 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/ContraseñaRegistro");
        el4.sendKeys("uno dos tres");
        MobileElement el5 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/BttRegistrame");
        el5.click();
        String actual = driver.findElementById("EZCode.Pantallas:id/textView7").getText();
        MobileElement el6 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonCerrarSesion");
        el6.click();
        Assert.assertEquals("Horario de Jose Daniel",actual);
    }
    @Test
    public void B_testAutenticacion() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test Autenticacion");
        MobileElement el2 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoCorreo");
        el2.sendKeys("joded@yahoo.com");
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoPassword");
        el3.sendKeys("uno dos tres");
        MobileElement el4 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonInicioSesion");
        el4.click();
        String actual = driver.findElementById("EZCode.Pantallas:id/textView7").getText();
        Assert.assertEquals("Horario de Jose Daniel",actual);
    }
    @Test
    public void C_testAgregarMeta() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test Agregar Meta");
        MobileElement el1 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonMetas");
        el1.click();
        MobileElement el2 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonnuevameta");
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoNombreMeta");
        el3.sendKeys("Aprender Inglés");
        MobileElement el4 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoDescripcionMeta");
        el4.sendKeys("Leyendo el libro");
        MobileElement el5 = (MobileElement) driver.findElementById("android:id/text1");
        el5.click();
        MobileElement el6 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[3]");
        el6.click();
        MobileElement el7 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonAgregarMeta");
        el7.click();
        String actual = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout/android.widget.Button").getText();
        MobileElement el9 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonvolver");
        el9.click();
        MobileElement el10 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonCerrarSesion");
        el10.click();
        Assert.assertEquals("Aprender Inglés",actual);
    }
    @Test
    public void D_testCorreoFalso() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test mal inicio de sesión");
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoCorreo");
        el3.sendKeys("correo");
        MobileElement el4 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoPassword");
        el4.sendKeys("123");
        MobileElement el5 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonInicioSesion");
        el5.click();
        String actual = driver.findElementById("EZCode.Pantallas:id/textView").getText();
        Assert.assertEquals("Bienvenido a EZCode",actual);
    }
    @Test
    public void E_testMalregistroMalo() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test Mal Registro");
        MobileElement el1 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonRegistro");
        el1.click();
        MobileElement el2 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/NombreRegistro");
        el2.sendKeys("nombre");
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/CorreoRegistro");
        el3.sendKeys("correo");
        MobileElement el4 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/ContraseñaRegistro");
        el4.sendKeys("123");
        MobileElement el5 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/BttRegistrame");
        el5.click();
        String actual = driver.findElementById("EZCode.Pantallas:id/textView4").getText();
        driver.navigate().back();
        Assert.assertEquals("Crear Cuenta",actual);
    }
    @Test
    public void F_testMalEvento() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test Mal evento");
        MobileElement el1 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoCorreo");
        el1.sendKeys("joded@yahoo.com");
        MobileElement el2 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoPassword");
        el2.sendKeys("uno dos tres");
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonInicioSesion");
        el3.click();
        MobileElement el4 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonNuevoEvento");
        el4.click();
        MobileElement el5 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoNombreEvento");
        el5.sendKeys("Mal evento");
        MobileElement el6 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/textoFechaInicio");
        el6.click();
        el6.click();
        MobileElement el7 = (MobileElement) driver.findElementById("android:id/button1");
        el7.click();
        MobileElement el8 = (MobileElement) driver.findElementById("android:id/button1");
        el8.click();
        MobileElement el9 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/textoFechaFin");
        el9.click();
        el9.click();
        MobileElement el10 = (MobileElement) driver.findElementById("android:id/button1");
        el10.click();
        MobileElement el11 = (MobileElement) driver.findElementByAccessibilityId("12");
        el11.click();
        MobileElement el12 = (MobileElement) driver.findElementByAccessibilityId("0");
        el12.click();
        MobileElement el13 = (MobileElement) driver.findElementById("android:id/am_label");
        el13.click();
        MobileElement el14 = (MobileElement) driver.findElementById("android:id/button1");
        el14.click();
        MobileElement el15 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoDescripcionActividad");
        el15.sendKeys("Este es un mal evento");
        MobileElement el16 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonAgregarEvento");
        el16.click();
        String actual = driver.findElementById("EZCode.Pantallas:id/textView8").getText();
        driver.navigate().back();
        Assert.assertEquals("Crear evento nuevo",actual);
    }
    @Test
    public void G_testMalaMeta() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test Mala meta");
        MobileElement el1 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonMetas");
        el1.click();
        MobileElement el2 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonnuevameta");
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoNombreMeta");
        el3.sendKeys("No va a haber descripcion");
        MobileElement el4 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonAgregarMeta");
        el4.click();
        String actual = driver.findElementById("EZCode.Pantallas:id/textoErrorMeta").getText();
        driver.navigate().back();
        driver.navigate().back();
        Assert.assertEquals("Verifique que todos los campos estén llenos",actual);
    }
    @Test
    public void H_testAgregarEvento() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test agregar evento");
        MobileElement el1 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonNuevoEvento");
        el1.click();
        MobileElement el2 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoNombreEvento");
        el2.sendKeys("Hacer ejercicio");
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/textoFechaInicio");
        el3.click();
        el3.click();
        MobileElement el4 = (MobileElement) driver.findElementByAccessibilityId("31 May 2021");
        el4.click();
        MobileElement el5 = (MobileElement) driver.findElementById("android:id/button1");
        el5.click();
        MobileElement el6 = (MobileElement) driver.findElementByAccessibilityId("2");
        el6.click();
        MobileElement el7 = (MobileElement) driver.findElementByAccessibilityId("0");
        el7.click();
        MobileElement el8 = (MobileElement) driver.findElementById("android:id/pm_label");
        el8.click();
        MobileElement el9 = (MobileElement) driver.findElementById("android:id/button1");
        el9.click();
        MobileElement el10 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/textoFechaFin");
        el10.click();
        el10.click();
        MobileElement el11 = (MobileElement) driver.findElementByAccessibilityId("31 May 2021");
        el11.click();
        MobileElement el12 = (MobileElement) driver.findElementById("android:id/button1");
        el12.click();
        MobileElement el13 = (MobileElement) driver.findElementByAccessibilityId("4");
        el13.click();
        MobileElement el14 = (MobileElement) driver.findElementByAccessibilityId("0");
        el14.click();
        MobileElement el15 = (MobileElement) driver.findElementById("android:id/pm_label");
        el15.click();
        MobileElement el16 = (MobileElement) driver.findElementById("android:id/button1");
        el16.click();
        MobileElement el17 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoDescripcionActividad");
        el17.sendKeys("Trotar 20 minutos");
        MobileElement el18 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonAgregarEvento");
        el18.click();
        MobileElement el19 = (MobileElement) driver.findElementByAccessibilityId("31 May 2021");
        el19.click();
        String actual = driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.TextView").getText();
        Assert.assertEquals("Hacer ejercicio--14:00",actual);
    }
    @Test
    public void I_testModificarEvento() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test modificar evento");
        MobileElement el1 = (MobileElement) driver.findElementByAccessibilityId("31 May 2021");
        el1.click();
        MobileElement el2 = (MobileElement) driver.findElementById("android:id/text1");
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoModificarDescripcionEvento");
        el3.sendKeys("Saltar cuerda");
        MobileElement el4 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/campoModificarNombreEvento");
        el4.sendKeys("Hacer menos ejercicio");
        MobileElement el5 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonModificarEvento");
        el5.click();
        MobileElement el6 = (MobileElement) driver.findElementByAccessibilityId("31 May 2021");
        el6.click();
        MobileElement el7 = (MobileElement) driver.findElementById("android:id/text1");
        el7.click();
        Assert.assertEquals("Hacer menos ejercicio",driver.findElementById("EZCode.Pantallas:id/campoModificarNombreEvento").getText());
        Assert.assertEquals("Saltar cuerda",driver.findElementById("EZCode.Pantallas:id/campoModificarDescripcionEvento").getText());
    }
    @Test
    public void J_testBorrarEvento() throws MalformedURLException {
        setUp();
        System.out.println("Corriendo Test borrar evento");
        MobileElement el1 = (MobileElement) driver.findElementByAccessibilityId("31 May 2021");
        el1.click();
        MobileElement el2 = (MobileElement) driver.findElementById("android:id/text1");
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementById("EZCode.Pantallas:id/botonEliminarEvento");
        el3.click();
        (new TouchAction(driver)).tap(new PointOption().withCoordinates(464, 1363)).perform();
        Assert.assertEquals("Horario de Jose Daniel",driver.findElementById("EZCode.Pantallas:id/textView7").getText());
    }
    @After
    public void End(){driver.quit();}
}
