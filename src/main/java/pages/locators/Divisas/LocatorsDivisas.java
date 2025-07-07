package pages.locators.Divisas;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LocatorsDivisas {
	
	
	//Frame Principal de Transferencias Internacionales Divisas
	public By iframeIdDivisas= By.id("cphCuerpo_IframeDivisas");
	public By iframeIdDivisasEmpresarial= By.name("contenido");
	
	//Controlar mensaje de sesión expirada
	public String  sesionEx = "//b[contains(text(), 'Sesión no existe o ha expirado por inactividad.')]";
		
	// Locator MENU
	public String moduloSelecionarDivisas = "//li[contains(text(), 'MENU')]";
	
	public By moduSelDivisasEmpresarial = By.xpath("//a[contains(text(), 'Transferencias Internacionales')]");
	

	// Locator [ALERTAS-POPUP-MENSAJES]
	public String popMenAler = "//p[contains(text(), 'disponible')]";
	
	
	public By btnMenAlerdisponible= By.id("btnmodal");
	
	public String popMens = "//div[@id='AlertaModal']//strong[contains(text(), 'Importante')]";
	
	public By cmpPopup = By.id("mensaje");

	public String btnPopup = "//*[@id='botonModal' or @id='alertamodalbtn']";
	
	public By btnPopupaceptar= By.id("btnmodalaceptar");

	public String btnPopupAvertaceptar  = "//*[@id='AlertaModal']/div//button[contains(text(), 'Aceptar')]";
	
	
	public By btnModalPopup= By.id("btnmodal2");
	
	public By btnPopup2 = By.xpath("//*[@id='btnmodal']");


	public By btnAcetarAler= By.id("alertamodalbtn");

	public String AlertPopup = "//*[@id='AlertaModal']/div";

	public String AlertPopupAdver = "//*[@id='AlertaModalComponente']/div";
	
	public String Alermod = "//*[@id='AlertaModal']/div/div/div[2]/p";

	public String Alermod2 = "//*[@id='AlertaModal']/div";


// =======================================================================================================================

	// Busca el registro mediante la hora y el tipo de moneda
	public String xpathNumDocumTxCon = "//td[contains(text(), 'fechayhoraconvert')]/following-sibling::td[contains(text(), 'MONEDA')]/following-sibling::td[contains(text(), 'DOCID')]/preceding-sibling::td/a";
	public String xpathNumDocumTxCon2 = "//td[contains(text(), 'fechayhoraconvert')]/following-sibling::td[contains(text(), 'MONEDA')]/preceding-sibling::td[4]";

	public String xpathNumDocumTxCon3 = "//td[contains(text(), 'fechayhoraconvert')]/following-sibling::td[contains(text(), 'MONEDA')]/preceding-sibling::td[3]";

	public String xpathNumDocumTxObt = "//td[contains(text(), 'fechayhoraconvert')]/following-sibling::td[contains(text(), 'MONEDA')]/following-sibling::td[contains(text(), '')][3]";

	public String xpathBuscarFechayHora = "//td[contains(text(), 'fechayhoraconvert')]";

}
