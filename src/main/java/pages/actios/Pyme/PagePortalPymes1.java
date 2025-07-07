package pages.actios.Pyme;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import library.reporting.Reporter;
import library.settings.SettingsRun;
import library.common.Util;
import library.core.BasePageWeb;
import library.reporting.Evidence;

public class PagePortalPymes1 extends BasePageWeb {

//=======================================================================================================================
	// LOCATORS Y EXPRESIONES PARA ENCONTRAR LOS OBJETOS EN FRONT PYMES
	By locCerrSes = By.cssSelector("a[id='CerrarSesion']"); // EN FRONT Y MIDDLE SE PRESENTA
	By locCmEmpresa = By.xpath("//select[@id='dropMasterEmpresa']");
	By locTitle = By.id("lblMasterTitulo");// "id:=lblMasterTitulo"; // TagName = "span"

	// LOCATORS MENU
	By Menu = By.id("mnMenu");
	By dflMenu = By.xpath("//a[@class='Nivel1']"); // Diferencia el menu ya que cambia el locator por mnMenu_1 Nivel1
													// mnMenu_3

	// XPATH QUE REMPLAZAN EL TEXTO CONTENIDO ('NB_MENU_INICIAL','NB_SUBMENU') POR
	// EL QUE DESEAMOS BUSCAR
	String xPathLocMenu = "//a[@class='Nivel1'][text()='NB_MENU_INICIAL']//parent::td";
	String xPathLocSubMenu = "//a[@class='Nivel2'][text()='NB_SUBMENU']//parent::td";

	// XPATH YA QUE AL REALIZAR UN PAGO CAMBIA EL LOCATOR
	String xPathLocMenuDF = "//a[@class='mnMenu_1 Nivel1 mnMenu_3'][text()='NB_MENU_INICIAL']//parent::td";
	String xPathLocSubMenuDF = "//a[@class='mnMenu_1 Nivel2 mnMenu_5'][text()='NB_SUBMENU']//parent::td";

	By table = By.xpath(
			"//*[@id=\"cphCuerpo_panConfirmacion\"]/table/tbody/tr[1]/td/table/tbody/tr[7]/td/table/tbody/tr[2]/td");
	By table2 = By.xpath("//*[@id='Pnl_cuerpo']/table/tbody/tr[2]/td/table/tbody/tr[9]/td");
	By table3 = By.xpath("//*[@id='cphCuerpo_gvTransaccionesMasivas']");
	By table4 = By.xpath("//*[@id='cphCuerpo_gvDestinoFondos']");

	// Divisas
	By cmpPopup = By.id("mensaje");
	By btnPopup = By.xpath("//*[@id='botonModal' or @id='alertamodalbtn']");
	By btnPopupaceptar = By.id("btnmodalaceptar");
	By btnPopupAvertaceptar = By.xpath("//*[@id='AlertaModal']/div//button[contains(text(), 'Aceptar')]");
	By btnModalPopup = By.id("btnmodal2");
	By btnPopup2 = By.xpath("//*[@id='btnmodal']");
	By btnAcetarAler = By.id("alertamodalbtn");

	By AlertPopup = By.xpath("//*[@id='AlertaModal']/div");
	By AlertPopupAdver = By.xpath("//*[@id='AlertaModalComponente']/div");

	String Alermod = "//*[@id='AlertaModal']/div/div/div[2]/p";
	String Alermod2 = "//*[@id='AlertaModal']/div";

	protected PageLoginPymes1 pageLogin;

//=======================================================================================================================
	/**
	 * Este es el Ã¯Â¿Â½nico constructor que tiene esta [BasePageWeb] ya que por
	 * sÃ¯Â¿Â½ sola no puede existir, debe depender de otra [BasePageWeb] que ya
	 * haya sido abierta y que direccione a Ã¯Â¿Â½sta.
	 */
	public PagePortalPymes1(BasePageWeb parentPage) {
		super(parentPage);
		this.pageLogin = new PageLoginPymes1(parentPage);
	}
//***********************************************************************************************************************
	/**
	 * MÃ¯Â¿Â½todo para hacer el cierre de la sesiÃ¯Â¿Â½n.
	 */

	/*
	 * @ZEA public void closeSession() throws Exception {
	 * this.pageLogin.closeSession(); }
	 */

	// ***********************************************************************************************************************
	/**
	 * Este mÃƒÂ©todo permite terminar una iteraciÃƒÂ³n transaccional que se estÃƒÂ©
	 * realizando en Davicom.<br>
	 * Se reporta el evento [eventStatus] con el [mensaje] y se guarda evidencia,
	 * revisa si se debe cerrar sesiÃƒÂ³n antes de terminar la iteraciÃƒÂ³n actual.
	 */
	public void terminarIteracion(int eventStatus, String mensaje) throws Exception {
		Reporter.reportEvent(eventStatus, mensaje);
//		Evidence.save("AntesDeTerminar", this);
		Evidence.save("AntesDeTerminar", this);
		// CIERRA SESIÃƒÂ“N SI ES TIEMPO DE CERRARLA
		this.terminarIteracion();
	}

//***********************************************************************************************************************
	/**
	 * EstÃ¯Â¿Â½ mÃ¯Â¿Â½todo termina la iteraciÃ¯Â¿Â½n actual que se estÃ¯Â¿Â½
	 * realizando en PortalPyme.<br>
	 * Si es tiempo de cerrar sesiÃ¯Â¿Â½n, la cierra.<br>
	 * <b>OJO:</b> El cierre de sesiÃ¯Â¿Â½n garantiza el cierre del Browser.
	 */
	public void terminarIteracion() throws Exception {
		this.getDriver().switchTo().defaultContent();
		this.pageLogin.terminarIteracion();
	}

//***********************************************************************************************************************
	/**
	 * Intenta seleccionar la empresa.<br>
	 * Retorna [null] si pudo hacer la selecciÃ¯Â¿Â½n, en caso contrario retorna
	 * mensaje de error.
	 * 
	 * @throws Exception
	 */
	public String seleccionarEmpresa(String nbEmpresa) throws Exception {
		int contador = 1;
		do {
			contador++;
			Util.wait(1);
			if (contador > 650) {
				Reporter.reportEvent(Reporter.MIC_FAIL, "TimeOut en el Login");
				SettingsRun.exitTestIteration();
			}
		} while (this.element(locCmEmpresa) == null);
		this.closeActiveCtas(); // CIERRA POPUP DE ACTIVE CUENTAS SI SE PRESENTA
		Util.wait(1);

		String[] empresasContra = this.getListItems(locCmEmpresa);

		if (nbEmpresa.equals("MANTENIMINETO DE EQUIPOS DE SEGURI")
				|| nbEmpresa.equals("MANTENIMINETO DE EQUIPOS DE SE GURIDAD")
				|| nbEmpresa.equals("MANTENIMINETO DE EQUIPOS DE SEGUIR")) {

			for (String empresas : empresasContra) {

				if (empresas.contains("MANTENIMINETO DE EQUIPOS DE SEGURI")
						|| empresas.contains("MANTENIMINETO DE EQUIPOS DE SE GURIDAD")
						|| empresas.contains("MANTENIMINETO DE EQUIPOS DE SEGUIR")
						|| empresas.contains("MANTENIMINETO DE EQUIPOS DE SE GUR")) {
					nbEmpresa = empresas;

				}
			}
		}

		if (nbEmpresa.equals("EMP AUTO DOS") || nbEmpresa.equals("EMP PYME AUTO DOS")) {

			for (String empresas : empresasContra) {

				if (empresas.contains("EMP AUTO DOS") || empresas.contains("EMP PYME AUTO DOS")) {
					nbEmpresa = empresas;

				}
			}
		}

		if (nbEmpresa.equals("CAMILO QUEVEDO QUEVEDO") || nbEmpresa.equals("CAMILO TORRES")) {

			for (String empresas : empresasContra) {

				if (empresas.contains("CAMILO QUEVEDO QUEVEDO") || empresas.contains("CAMILO TORRES")) {
					nbEmpresa = empresas;

				}
			}
		}

		String msgError = this.selectListItem(this.element(locCmEmpresa), nbEmpresa);
		if (msgError != null) {

			if (!msgError.isEmpty()) {
				for (String empresas : empresasContra) {
					Reporter.reportEvent(Reporter.MIC_INFO, "*** Empresas: [" + empresas + "]");
				}
				Evidence.save(msgError, this);
//			Evidence.save(msgError);
				return "Seleccionando empresa : " + msgError;
			}
		}
		this.closeActiveCtas(); // CIERRA POPUP DE ACTIVE CUENTAS SI SE PRESENTA
		return null;
	}

//***********************************************************************************************************************
	/**
	 * MÃ¯Â¿Â½todo que permite moverse a travÃ¯Â¿Â½s de los menÃ¯Â¿Â½es presentados
	 * en Middle. Abriendo el menÃ¯Â¿Â½ dado por [opcMenu] y siguiendo la ruta por
	 * las opciones enviadas en [opcSubMenu], dando click en el Ã¯Â¿Â½ltimo elemento
	 * de [opcSubMenu], en caso que no hayan sido enviados, da click directamente en
	 * [opcMenu].<br>
	 * [title] puede ser [null], VACIO o un valor; cuando se envÃ¯Â¿Â½a valor este
	 * mÃ¯Â¿Â½todo espera a que el tÃ¯Â¿Â½tulo enviado se presente en pantalla
	 * <b>OJO</b> sÃ¯Â¿Â½lo si el tÃ¯Â¿Â½tulo corresponde a un elemento con ID
	 * 'lblMasterTitulo'.<br>
	 * El retorno es [null] si se pudo ir a la opciÃ¯Â¿Â½n deseada, en caso
	 * contrario envÃ¯Â¿Â½a un error, los errores que se pueden presentar, son
	 * porque no se encuentran las opciones enviadas.
	 */
	public String irAOpcion(String title, String opcMenu, String opcSubMenu, String... opcSubMenu2) throws Exception {
		String xPath = "";
		if (this.isDisplayed(Menu)) {
			if (this.isDisplayed(dflMenu)) {
				xPath = xPathLocMenu.replace("NB_MENU_INICIAL", opcMenu);
			} else {
				xPath = xPathLocMenuDF.replace("NB_MENU_INICIAL", opcMenu);
			}
			String pathMenu = opcMenu;
			WebElement elemtMenu = this.element(xPath);
			if (elemtMenu == null) {
				Evidence.save("ErrorMenu", this);
//				Evidence.save("ErrorMenu");
				return "No se encontro en el Menu [" + pathMenu + "] - Valide la informaciÃ³n";
			}
// -----------------------------------------------------------------------------------------------------------------------
			// SI LLEGA A ESTE PUNTO PUEDE IR SELECCIONANDO LAS OTRAS OPCIONES
			this.mouseOver(elemtMenu);
			List<WebElement> listaElements;
			if (opcSubMenu != null) {

				for (int numOpc = 0; numOpc < opcSubMenu.length(); numOpc++) {

					if (this.isDisplayed(dflMenu)) {
						xPath = xPathLocSubMenu.replace("NB_SUBMENU", opcSubMenu);
					} else {
						xPath = xPathLocSubMenuDF.replace("NB_SUBMENU", opcSubMenu);
					}
					listaElements = this.findElements(By.xpath(xPath));
					if (listaElements.size() == 0) {
						Evidence.save("ErrorMenu", this);
//						Evidence.save("ErrorMenu");
						return "En el menu [" + pathMenu + "] NO se encontro la opciï¿½n [" + opcSubMenu
								+ "] - Valide la informaciciï¿½n";
					} else if (listaElements.size() > 1)
						return "Existen muchos submenus que contienen [" + opcSubMenu + "] en el WebPage";
					if (!this.isDisplayed(listaElements.get(0))) {

						do { // ESPERA A QUE SE HAYA DESPLEGADO EL ELEMENTO
							Util.wait(1);
						} while (!this.isDisplayed(listaElements.get(0)));
					}
					pathMenu += "/" + opcSubMenu;
					// NO SE PUEDE CON [listaElements.get(0)] PORQUE SE PRESENTA UN
					// [MoveTargetOutOfBoundsException]
					this.mouseOver(this.element(xPath)); // POSICIONA EL MOUSE SOBRE EL ELEMENTO RESPECTIVO
				}
			}
			if (opcSubMenu2 != null) {
				for (int numOpc = 0; numOpc < opcSubMenu2.length; numOpc++) {
					if (this.isDisplayed(dflMenu)) {
						xPath = xPathLocSubMenu.replace("NB_SUBMENU", opcSubMenu2[numOpc]);
					} else {
						xPath = xPathLocSubMenuDF.replace("NB_SUBMENU", opcSubMenu2[numOpc]);
					}
					listaElements = this.findElements(By.xpath(xPath));
					if (listaElements.size() == 0) {
						Evidence.save("ErrorMenu", this);
//						Evidence.save("ErrorMenu");
						return "En el menu [" + pathMenu + "] NO se encontro la opciÃ³n [" + opcSubMenu2[numOpc]
								+ "] - Valide la informaciÃ³n";
					} else if (listaElements.size() > 1)
						return "Existen muchos submenus que contienen [" + opcSubMenu2[numOpc] + "] en el WebPage";

					if (!this.isDisplayed(listaElements.get(0))) {
						do { // ESPERA A QUE SE HAYA DESPLEGADO EL ELEMENTO
							Util.wait(1);
						} while (!this.isDisplayed(listaElements.get(0)));
					}
					pathMenu += "/" + opcSubMenu2[numOpc];
					// NO SE PUEDE CON [listaElements.get(0)] PORQUE SE PRESENTA UN
					// [MoveTargetOutOfBoundsException]
					this.mouseOver(this.element(xPath)); // POSICIONA EL MOUSE SOBRE EL ELEMENTO RESPECTIVO
				}
			}
			Evidence.save("Menu", this);
//			Evidence.save("Menu");
			this.mouseClick();
// -----------------------------------------------------------------------------------------------------------------------
			// ESPERA LA MUESTRA DEL TÃ¯Â¿Â½TULO [title]
			if (title != null && !title.isEmpty()) {
				this.ValidacionPreguntasPep();
				this.waitIngresoModulo(title);
				Evidence.save("IngresoModulo", this);
//				Evidence.save("IngresoModulo");
			}
		}
		return null;
	}

//***********************************************************************************************************************
	/**
	 * Mï¿½todo que permite moverse a travï¿½s de los menï¿½es presentados en
	 * Middle. Abriendo el menï¿½ dado por [opcMenu] y siguiendo la ruta por las
	 * opciones enviadas en [opcSubMenu], dando click en el ï¿½ltimo elemento de
	 * [opcSubMenu], en caso que no hayan sido enviados, da click directamente en
	 * [opcMenu].<br>
	 * [title] puede ser [null], VACIO o un valor; cuando se envï¿½a valor este
	 * mï¿½todo espera a que el tï¿½tulo enviado se presente en pantalla <b>OJO</b>
	 * sï¿½lo si el tï¿½tulo corresponde a un elemento con ID 'lblMasterTitulo'.<br>
	 * El retorno es [null] si se pudo ir a la opciï¿½n deseada, en caso contrario
	 * envï¿½a un error, los errores que se pueden presentar, son porque no se
	 * encuentran las opciones enviadas.
	 */
	public String irAOpcionMoZilla(String title, String opcMenu, String opcSubMenu, String opcSubMenu2,
			String opcSubMenu3) throws Exception {
		String xPath = "";

		int contador = 0;
		this.refresh();
		Util.wait(10);

		do {
			Util.wait(1);
			contador++;
			if (contador >= 30) {
				Reporter.reportEvent(Reporter.MIC_FAIL, "TimeOut");
				this.terminarIteracion();
			}
		} while (this.element(Menu) == null);

		if (this.isDisplayed(Menu)) {
			if (this.isDisplayed(dflMenu)) {
				xPath = xPathLocMenu.replace("NB_MENU_INICIAL", opcMenu);
			} else {
				xPath = xPathLocMenuDF.replace("NB_MENU_INICIAL", opcMenu);
			}
			String pathMenu = opcMenu;
			WebElement elemtMenu = this.element(xPath);
			if (elemtMenu == null) {
				Evidence.save("ErrorMenu", this);
//				Evidence.save("ErrorMenu");
				return "No se encontro en el Menu [" + pathMenu + "] - Valide la informaciÃ³n";
			}
// -----------------------------------------------------------------------------------------------------------------------
			// SI LLEGA A ESTE PUNTO PUEDE IR SELECCIONANDO LAS OTRAS OPCIONES
			do {
				Util.wait(1);

			} while (!elemtMenu.isDisplayed());
			this.getJse().executeScript(
					"var event = new MouseEvent('mouseover', {bubbles: true, cancelable: true, view: window}); arguments[0].dispatchEvent(event);",
					elemtMenu);
			List<WebElement> listaElements;
			if (opcSubMenu != null) {
//				for (int numOpc = 0; numOpc < opcSubMenu.length(); numOpc++) {

				if (this.isDisplayed(dflMenu)) {
					xPath = xPathLocSubMenu.replace("NB_SUBMENU", opcSubMenu);
				} else {
					xPath = xPathLocSubMenuDF.replace("NB_SUBMENU", opcSubMenu);
				}
				listaElements = this.findElements(By.xpath(xPath));
				if (listaElements.size() == 0) {
					Evidence.save("ErrorMenu", this);
//					Evidence.save("ErrorMenu");
					return "En el menu [" + pathMenu + "] NO se encontrÃ³ la opciÃ³n [" + opcSubMenu
							+ "] - Valide la informaciï¿½n";
				} else if (listaElements.size() > 1)
					return "Existen muchos submenus que contienen [" + opcSubMenu + "] en el WebPage";
				if (!this.isDisplayed(listaElements.get(0))) {

					do { // ESPERA A QUE SE HAYA DESPLEGADO EL ELEMENTO
						Util.wait(1);
					} while (!this.isDisplayed(listaElements.get(0)));
				}
				pathMenu += "/" + opcSubMenu;
				// NO SE PUEDE CON [listaElements.get(0)] PORQUE SE PRESENTA UN
				// [MoveTargetOutOfBoundsException]
//				Util.wait(3);
				elemtMenu = this.element(xPath);
//				this.mouseOver(this.element(xPath)); // POSICIONA EL MOUSE SOBRE EL ELEMENTO RESPECTIVO
				do {
					Util.wait(1);
					contador++;
					if (contador >= 30) {
						Reporter.reportEvent(Reporter.MIC_FAIL, "TimeOut");
						this.terminarIteracion();
					}
				} while (elemtMenu == null);
				this.getJse().executeScript(
						"var event = new MouseEvent('mouseover', {bubbles: true, cancelable: true, view: window}); arguments[0].dispatchEvent(event);",
						elemtMenu);
				Util.wait(4);
			}
//			}
			if (opcSubMenu2 != null) {
//				for (int numOpc = 0; numOpc < opcSubMenu2.length; numOpc++) {

				if (this.isDisplayed(dflMenu)) {
					xPath = xPathLocSubMenu.replace("NB_SUBMENU", opcSubMenu2);
				} else {
					xPath = xPathLocSubMenuDF.replace("NB_SUBMENU", opcSubMenu2);
				}
				listaElements = this.findElements(By.xpath(xPath));
				if (listaElements.size() == 0) {
					Evidence.save("ErrorMenu", this);
//					Evidence.save("ErrorMenu");
					return "En el menu [" + pathMenu + "] NO se encontro la opciÃ³n [" + opcSubMenu2
							+ "] - Valide la informaciÃ³n";
				} else if (listaElements.size() > 1)
					return "Existen muchos submenus que contienen [" + opcSubMenu2 + "] en el WebPage";

				if (!this.isDisplayed(listaElements.get(0))) {
					do { // ESPERA A QUE SE HAYA DESPLEGADO EL ELEMENTO
						Util.wait(1);
					} while (!this.isDisplayed(listaElements.get(0)));
				}
				pathMenu += "/" + opcSubMenu2;
				// NO SE PUEDE CON [listaElements.get(0)] PORQUE SE PRESENTA UN
				// [MoveTargetOutOfBoundsException]
				Util.wait(3);
//				this.mouseOver(this.element(xPath)); // POSICIONA EL MOUSE SOBRE EL ELEMENTO RESPECTIVO
				elemtMenu = this.element(xPath);
				Util.wait(5);
				this.getJse().executeScript(
						"var event = new MouseEvent('mouseover', {bubbles: true, cancelable: true, view: window}); arguments[0].dispatchEvent(event);",
						elemtMenu);
//				}
				Util.wait(3);
			}
			if (opcSubMenu3 != null) {
//				for (int numOpc = 0; numOpc < opcSubMenu3.length; numOpc++) {

				if (this.isDisplayed(dflMenu)) {
					xPath = xPathLocSubMenu.replace("NB_SUBMENU", opcSubMenu3);
				} else {
					xPath = xPathLocSubMenuDF.replace("NB_SUBMENU", opcSubMenu3);
				}
				listaElements = this.findElements(By.xpath(xPath));
				if (listaElements.size() == 0) {
					Evidence.save("ErrorMenu", this);
//					Evidence.save("ErrorMenu");
					return "En el menu [" + pathMenu + "] NO se encontro la opcion [" + opcSubMenu3
							+ "] - Valide la informaciÃ³n";
				} else if (listaElements.size() > 1)
					return "Existen muchos submenus que contienen [" + opcSubMenu3 + "] en el WebPage";

				if (!this.isDisplayed(listaElements.get(0))) {
					do { // ESPERA A QUE SE HAYA DESPLEGADO EL ELEMENTO
						Util.wait(1);
					} while (!this.isDisplayed(listaElements.get(0)));
				}
				pathMenu += "/" + opcSubMenu3;
				// NO SE PUEDE CON [listaElements.get(0)] PORQUE SE PRESENTA UN
				// [MoveTargetOutOfBoundsException]
//					Util.wait(3);
//				this.mouseOver(this.element(xPath)); // POSICIONA EL MOUSE SOBRE EL ELEMENTO RESPECTIVO
				elemtMenu = this.element(xPath);
				Util.wait(2);
				this.getJse().executeScript(
						"var event = new MouseEvent('mouseover', {bubbles: true, cancelable: true, view: window}); arguments[0].dispatchEvent(event);",
						elemtMenu);
//				}
				Util.wait(3);
			}
			elemtMenu.click();
//			this.getJse().executeScript("arguments[0].click();", elemtMenu);
//			this.mouseClick();
// -----------------------------------------------------------------------------------------------------------------------
			// ESPERA LA MUESTRA DEL TITULO [title]
			if (title != null && !title.isEmpty()) {
				this.waitIngresoModulo(title);
				Evidence.save("IngresoModulo", this);
//				Evidence.save("IngresoModulo");
			}
		}
		return null;
	}

//***********************************************************************************************************************

	/**
	 * Metodo que da click en la opcion de menÃ¯Â¿Â½ 'Inicio'.
	 */
	public void irAInicio() throws Exception {
		this.irAOpcion("Saldos Consolidados", "Inicio", null);
	}

//***********************************************************************************************************************
	/**
	 * MÃ¯Â¿Â½todo que espera a que se presente el TÃ¯Â¿Â½TULO [titleModulo].<br>
	 * Se presenta ExcepciÃ¯Â¿Â½n si supera los 60 segundos de espera.<br>
	 * Este mÃ¯Â¿Â½todo sirve sÃ¯Â¿Â½lo cuando el tÃ¯Â¿Â½tulo corresponde al
	 * estÃ¯Â¿Â½ndar Middle que no tiene que moverse a ningÃ¯Â¿Â½n frame y buscar el
	 * elemento cuyo ID sea "lblMasterTitulo"
	 */
	public void waitIngresoModulo(String titleModulo) throws Exception {
		int time = 1;
		boolean esTitleEsperado = false;
		WebElement elemTitle;
		titleModulo = titleModulo.trim();
		do {
			Util.wait(1);
			elemTitle = this.element(locTitle);
			if (elemTitle != null)
				esTitleEsperado = Util.equalsIgnoreCaseAndAccents(titleModulo, elemTitle.getText().trim());

			if (!esTitleEsperado && this.retornoALogueo())
				return; // TERMINA EL CICLO

			time++;
		} while (!esTitleEsperado && time <= 60);
		if (!esTitleEsperado) {
			throw new Exception(
					"PageInicioMiddle ERROR -- Se supera los 60 segundos esperando ingreso al modulo con titulo ["
							+ titleModulo + "]");
		}
	}

	// ***********************************************************************************************************************
	/**
	 * MÃ¯Â¿Â½todo que espera a que se presente el TÃ¯Â¿Â½TULO [titleModulo].<br>
	 * Se presenta ExcepciÃ¯Â¿Â½n si supera los 60 segundos de espera.<br>
	 * Este mÃ¯Â¿Â½todo sirve sÃ¯Â¿Â½lo cuando el tÃ¯Â¿Â½tulo corresponde al
	 * estÃ¯Â¿Â½ndar Middle que no tiene que moverse a ningÃ¯Â¿Â½n frame y buscar el
	 * elemento cuyo ID sea "lblMasterTitulo"
	 */
	public boolean waitEspetaTitulo(String titleModulo) throws Exception {
		int time = 1;
		boolean esTitleEsperado = false;
		WebElement elemTitle;
		titleModulo = titleModulo.trim();
		do {
			Util.wait(1);
			elemTitle = this.element(locTitle);
			if (elemTitle != null)
				esTitleEsperado = Util.equalsIgnoreCaseAndAccents(titleModulo, elemTitle.getText().trim());

			if (!esTitleEsperado && this.retornoALogueo())
				return esTitleEsperado; // TERMINA EL CICLO

			time++;
		} while (!esTitleEsperado && time <= 30);

		return esTitleEsperado;
	}

//***********************************************************************************************************************
	public void closeActiveCtas() throws Exception {
		WebElement popUpCtas = this.element("//div[@id='cphCuerpo_pnlMensaje']");
		if (this.isDisplayed(popUpCtas)) {
			Evidence.save("PopupActiveCtas", this);
//			Evidence.save("PopupActiveCtas");
			this.clickNoScrollIntoView(this.element("//button[@class='close']"));
		}
	}

//***********************************************************************************************************************
	/**
	 * Este mÃ¯Â¿Â½todo retorna [true] si la pantalla se encuentra en la de logueo,
	 * como que se saliÃ¯Â¿Â½ del portal Middle.<br>
	 * Adicional deja almacenado en [estaEnLogueo] el valor respectivo.
	 */
	private boolean retornoALogueo() {
		/*
		 * WebElement objTituloInicio = this.element("//td[@class='Titulo']");
		 * this.estaEnLogueo = false; if (this.isDisplayed(objTituloInicio))
		 * this.estaEnLogueo =
		 * objTituloInicio.getText().contains("Bienvenido al Portal PYMES"); return
		 * this.estaEnLogueo;
		 */
		return false;
	}

//***********************************************************************************************************************
	/**
	 * MÃ¯Â¿Â½todo que retorna el mensaje de alerta si este existe. Si el retorno es
	 * [null] es porque NO existe un mensaje de alerta.<br>
	 * Se recibe por parÃ¯Â¿Â½metro un listado de los posibles id de las alertas que
	 * se pueden presentar.
	 */
	public String getMsgAlertIfExist(String... idsAlerta) {
		String msgAlert = null;
		for (String id : idsAlerta) {
			By locMessage = By.id(id);
			if (this.element(locMessage) != null) {
				if (this.isDisplayed(locMessage)) {
					Util.wait(1);
					try {
						msgAlert = this.getText(locMessage).trim();
					} catch (Exception e) {
						msgAlert = null;
					}
					if (msgAlert == null || msgAlert.equals("")) // NO HAY MENSAJE
						msgAlert = null;
					else
						break; // PARA TERMINAR EL CICLO
				}
			}
		}
		return msgAlert;
	}

// ***********************************************************************************************************************
	/**
	 * Metodo que retorna el mensaje de alerta si este existe. Si el retorno es
	 * [null] es porque NO existe un mensaje de alerta.<br>
	 * Se recibe por parametro un listado de los posibles id de las alertas que se
	 * pueden presentar.
	 */
	public String getMsgAlertIfExistxPath(String... xPathExpressions) {
		String msgAlert = null;

		for (String xPathExpression : xPathExpressions) {
			WebElement element = this.element(By.xpath(xPathExpression));

			if (element != null) {
				Util.wait(1);
				try {
					msgAlert = element.getText().trim();
				} catch (Exception e) {
					msgAlert = null;
				}

				if (msgAlert == null || msgAlert.equals("")) {
					msgAlert = null;
				} else {
					break; // PARA TERMINAR EL CICLO
				}
			}
		}

		return msgAlert;
	}

	public String getTextMid() throws Exception {
		WebElement getexto = null;
		Util.wait(3);
		try {
			getexto = this.element(By.xpath("//*[contains(text(), 'Pagos')]"));

		} catch (Exception e) {
			Reporter.reportEvent(Reporter.MIC_FAIL, "No se pudo encontrar el modulo de Pagos, Transferenia  y otros");
			this.terminarIteracion();
		}
		String texto = this.getText(getexto);
		return texto;
	}

//***********************************************************************************************************************

	public String irAOpcionIframe(String title, String opcMenu, String opcSubMenu, String... opcSubMenu2)
			throws Exception {
		String xPath = "";
		if (this.isDisplayed(Menu)) {
			if (this.isDisplayed(dflMenu)) {
				xPath = xPathLocMenu.replace("NB_MENU_INICIAL", opcMenu);
			} else {
				xPath = xPathLocMenuDF.replace("NB_MENU_INICIAL", opcMenu);
			}
			String pathMenu = opcMenu;
			WebElement elemtMenu = this.element(xPath);
			if (elemtMenu == null) {
				Evidence.save("ErrorMenu", this);
//				Evidence.save("ErrorMenu");
				return "No se encontrÃ³ en el Menu [" + pathMenu + "] - Valide la informaciÃ³n";
			}
// -----------------------------------------------------------------------------------------------------------------------
			// SI LLEGA A ESTE PUNTO PUEDE IR SELECCIONANDO LAS OTRAS OPCIONES
			this.mouseOver(elemtMenu);
			List<WebElement> listaElements;
			if (opcSubMenu != null) {
				for (int numOpc = 0; numOpc < opcSubMenu.length(); numOpc++) {

					if (this.isDisplayed(dflMenu)) {
						xPath = xPathLocSubMenu.replace("NB_SUBMENU", opcSubMenu);
					} else {
						xPath = xPathLocSubMenuDF.replace("NB_SUBMENU", opcSubMenu);
					}
					listaElements = this.findElements(By.xpath(xPath));
					if (listaElements.size() == 0) {
						Evidence.save("ErrorMenu", this);
//						Evidence.save("ErrorMenu");
						return "En el menu [" + pathMenu + "] NO se encontrola opciÃ³n [" + opcSubMenu
								+ "] - Valide la informaciÃ³n";
					} else if (listaElements.size() > 1)
						return "Existen muchos submenus que contienen [" + opcSubMenu + "] en el WebPage";
					if (!this.isDisplayed(listaElements.get(0))) {

						do { // ESPERA A QUE SE HAYA DESPLEGADO EL ELEMENTO
							Util.wait(1);
						} while (!this.isDisplayed(listaElements.get(0)));
					}
					pathMenu += "/" + opcSubMenu;
					// NO SE PUEDE CON [listaElements.get(0)] PORQUE SE PRESENTA UN
					// [MoveTargetOutOfBoundsException]
					this.mouseOver(this.element(xPath)); // POSICIONA EL MOUSE SOBRE EL ELEMENTO RESPECTIVO
				}
			}
			if (opcSubMenu2 != null) {
				for (int numOpc = 0; numOpc < opcSubMenu2.length; numOpc++) {
					if (this.isDisplayed(dflMenu)) {
						xPath = xPathLocSubMenu.replace("NB_SUBMENU", opcSubMenu2[numOpc]);
					} else {
						xPath = xPathLocSubMenuDF.replace("NB_SUBMENU", opcSubMenu2[numOpc]);
					}
					listaElements = this.findElements(By.xpath(xPath));
					if (listaElements.size() == 0) {
						Evidence.save("ErrorMenu", this);
//						Evidence.save("ErrorMenu");
						return "En el menu [" + pathMenu + "] NO se encontro la opciÃ³n [" + opcSubMenu2[numOpc]
								+ "] - Valide la informaciÃ³n";
					} else if (listaElements.size() > 1)
						return "Existen muchos submenus que contienen [" + opcSubMenu2[numOpc] + "] en el WebPage";

					if (!this.isDisplayed(listaElements.get(0))) {
						do { // ESPERA A QUE SE HAYA DESPLEGADO EL ELEMENTO
							Util.wait(1);
						} while (!this.isDisplayed(listaElements.get(0)));
					}
					pathMenu += "/" + opcSubMenu2[numOpc];
					// NO SE PUEDE CON [listaElements.get(0)] PORQUE SE PRESENTA UN
					// [MoveTargetOutOfBoundsException]
					this.mouseOver(this.element(xPath)); // POSICIONA EL MOUSE SOBRE EL ELEMENTO RESPECTIVO
				}
			}
			this.mouseClick();
// -----------------------------------------------------------------------------------------------------------------------
			// ESPERA LA MUESTRA DEL TÃ¯Â¿Â½TULO [title]
			if (title != null && !title.isEmpty()) {
				this.waitIngresoModulo(title);
				/*
				 * if (this.estaEnLogueo) { // SE CERRÃ¯Â¿Â½ EL PORTAL POR TIEMPO, TOCA
				 * LOGUEARSE DE NUEVO this.pageLogin.loginMiddle(); return this.irAOpcion(title,
				 * opcMenu, opcSubMenu); }
				 */
				Evidence.save("IngresoModulo", this);
//				Evidence.save("IngresoModulo");
			}
		}
		return null;
	}

	public String lisProducto(By locator, String tipoProducto, String numeroProducto) throws Exception {
		int contador = 0;
		String msgError = null;
		do {
			Util.wait(7);
			contador++;
			if (contador > 3) {
				Reporter.reportEvent(Reporter.MIC_FAIL, "TimeOut no se encontro la lista: Producto");
				SettingsRun.exitTestIteration();
			}
		} while (this.element(locator) == null);
//		String ptoduct = tipoProducto + " ************" + Util.right(numeroProducto, 4);
		String[] lista = this.getListItems(this.element(locator));
		for (String lis : lista) {
			Reporter.write(lis);
		}

		if (tipoProducto.equals("Mastercard") || tipoProducto.equals("mastercard")) {
			for (String list : lista) {
				if (list.contains("mastercard")) {
					tipoProducto = "mastercard";
				}
			}
		}

		msgError = this.selectListContainsItems(this.element(locator), tipoProducto, " ************",
				Util.right(numeroProducto, 4));
		Evidence.save("Producto Selecionado", this);
//		Evidence.save("Producto Selecionado");
		String msg = Util.left(msgError, 58);
		if (!msgError.isEmpty() && !msg.contains(Util.right(numeroProducto, 4))) {
			return "Seleccionando producto : " + msgError;
		}

		msgError = this.getMsgAlertIfExist("lblMasterAlerta");
		if (msgError != null && !msgError.isEmpty()) {
			Evidence.save(msgError, this);
//			Evidence.save(msgError);
			return msgError;
		}
		return null;
	}

	/**
	 * Metodo extrae los datos ingresados
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] CuentaDesMotor() throws Exception {
		// Encuentra el elemento de la tabla por su ID (asumiendo que la tabla tiene un
		// ID)
		Util.wait(5);
		WebElement tablaTx = null;
		String[] movi = null;
		if (this.element(table) != null) {
			tablaTx = this.element(table);
		}
		if (this.element(table2) != null) {
			tablaTx = this.element(table2);
		}
		if (this.element(table3) != null) {
			tablaTx = this.element(table3);
		}
		if (this.element(table4) != null) {
			tablaTx = this.element(table4);
		}

		int contador = 0;
		do {
			contador++;
			Util.wait(1);
			if (contador == 29) {
				Reporter.reportEvent(Reporter.MIC_FAIL, "TimeOut no se encontro la Tabla de registros");
			}
		} while (tablaTx == null && contador <= 30);

		if (tablaTx != null) {

			// Encuentra los encabezados y mapea sus posiciones
			Map<String, Integer> mapHeaders = new HashMap<>();
			List<WebElement> encabezados = tablaTx.findElements(By.xpath("//tr[1]/th"));
			if (encabezados.isEmpty()) {
				encabezados = tablaTx.findElements(By.xpath("//tr[1]/td")); // fallback si vienen como <td>
			}
			for (int i = 0; i < encabezados.size(); i++) {
				String header = encabezados.get(i).getText();
				mapHeaders.put(header, i);
			}

			// Encuentra todas las filas (excluyendo la de encabezados)
			List<WebElement> filas = tablaTx.findElements(By.xpath("//tr[@class='CeldaGrilla']"));
			movi = new String[filas.size()];

			// Recorre cada fila
			for (int i = 0; i < filas.size(); i++) {
				WebElement fila = filas.get(i);
				List<WebElement> celdas = fila.findElements(By.tagName("td"));

				// Obtiene los posibles headers reales desde el DOM (por si tienen nombre
				// variable)
				String headerBancoDestino = getHeaderAlias("Banco destino", "Banco Destino", "Banco", "BANCO DESTINO",
						"BANCO DESTINO", "BANCO");
				String headerIdentificacion = getHeaderAlias("IdentificaciÃ³n", "identificaciÃ³n", "ID Destinatario",
						"IDENTIFICACIÃ“N", "IDENTIFICACIÃ“N", "ID DESTINATARIO");
				String headerTitular = getHeaderAlias("Titular", "titular", "TÃ­tular", "tÃ­tular",
						"Nombre Destinatario", "TITULAR", "TÃ?TULAR", "NOMBRE DESTINATARIO");
				String headerTipodes = getHeaderAlias("Tipo destino", "Tipo Destino", "TIPO DESTINO");
				String headerNumerodes = getHeaderAlias("NÃºmero destino", "NÃºmero Destino", "NÃšMERO DESTINO");
				String headerEstado = getHeaderAlias("Estado", "estado", "ESTADO");
				String headerMotivo = getHeaderAlias("Motivo", "motivo", "MOTIVO");

				// Extrae valores con base en los headers encontrados
				String bancoDestino = getCellValue(celdas, mapHeaders, headerBancoDestino);
				String tipoDestino = getCellValue(celdas, mapHeaders, headerTipodes);
				String numeroDestino = getCellValue(celdas, mapHeaders, headerNumerodes);
				String titular = getCellValue(celdas, mapHeaders, headerTitular);
				String identificacion = getCellValue(celdas, mapHeaders, headerIdentificacion);
				String estado = getCellValue(celdas, mapHeaders, headerEstado);
				String motivo = getCellValue(celdas, mapHeaders, headerMotivo);

				if (motivo == null || motivo.isEmpty()) {
					motivo = getCellValue(celdas, mapHeaders, "Rechazo");
				}

				String valor = getCellValue(celdas, mapHeaders, "Valor");

				if (valor != null && valor.contains("$")) {

					valor = valor.replace("$", "").replace(".", "").trim();

					if (valor.contains(",")) {
						valor = valor.replace(",", ".").trim();
					}
//					if (valor.contains(",")) {
//						valor = valor.replace(",", ".").trim();
//					}
//
//					int numPoints = 0;
//					for (char c : valor.toCharArray()) {
//						if (c == '.')
//							numPoints++;
//					}
//					if (numPoints >= 2) {
//						int pos = valor.indexOf(".");
//						valor = valor.substring(0, pos) + valor.substring(pos + 1);
//					}
				}

				// Guarda el resultado
				movi[i] = bancoDestino + "|" + tipoDestino + "|" + valor + "|" + identificacion + "|" + numeroDestino
						+ "|" + estado + "|" + motivo;
			}
		}

		return movi;
	}

	private String getCellValue(List<WebElement> celdas, Map<String, Integer> mapHeaders, String headerName) {
		if (headerName == null)
			return null;
		Integer index = mapHeaders.get(headerName.trim());
		if (index != null && index < celdas.size()) {
			return celdas.get(index).getText();
		}
		return null;
	}

	private String getHeaderAlias(String... posiblesTextos) {
		for (String texto : posiblesTextos) {
			WebElement header = this.element("//table[@class='TablaGrilla']//a[contains(text(), '" + texto + "')]");
			if (header != null) {
				return header.getText();
			}

			header = this.element("//table[@class='TablaGrilla']//tr/th[contains(text(), '" + texto + "')]");
			if (header != null) {
				return header.getText();
			}
		}
		return null;
	}

	public void ValidacionPreguntasPep() throws Exception {

		WebElement infoPep = this.element("//h2[contains(text(), 'InformaciÃ³n PEP')]");
		if (this.isDisplayed(infoPep)) {
//			Evidence.save("Informaciï¿½n PEP", this);
//			Evidence.save("InformaciÃ³n PEP");
			Evidence.save("InformaciÃ³n PEP", this);
			// Botones Sï¿½ y No
			WebElement botonSi = this.element(By.id("rbSiPreguntaUno"));
			WebElement botonSi2 = this.element(By.id("rbSiPreguntaDos"));
			WebElement botonSi3 = this.element(By.id("rbNoPreguntaTres"));
			WebElement botonSi4 = this.element(By.id("rbNoPreguntaCuatro"));

			// Checkbox Acepto el tratamiento
			WebElement checkboxAcepto = this.element(By.id("cbTratamientoDatos"));

			// Botï¿½n Continuar
			WebElement botonContinuar = this.element(By.id("cphCuerpo_btnContinuar"));
			if (!botonSi.isSelected()) {
				String scriptClicIngresar1 = "document.querySelector(\"#rbSiPreguntaUno\").click();";
				this.getJse().executeScript(scriptClicIngresar1);
//				botonSi.click();
			}
			if (!botonSi2.isSelected()) {
				Util.wait(1);
				String scriptClicIngresar1 = "document.querySelector(\"#rbSiPreguntaDos\").click();";
				this.getJse().executeScript(scriptClicIngresar1);
//				botonSi.click();
			}
			if (!botonSi3.isSelected()) {
				Util.wait(4);
				String scriptClicIngresar1 = "document.querySelector(\"#rbNoPreguntaTres\").click();";
				this.getJse().executeScript(scriptClicIngresar1);
//				botonSi.click();
			}
			if (!botonSi4.isSelected()) {
				Util.wait(4);
				String scriptClicIngresar1 = "document.querySelector(\"#rbNoPreguntaTres\").click();";
				this.getJse().executeScript(scriptClicIngresar1);
//				botonSi.click();
			}

			if (!checkboxAcepto.isSelected()) {
				Util.wait(1);
				String scriptClicIngresar1 = "document.querySelector(\"#rbNoPreguntaCuatro\").click();";
				this.getJse().executeScript(scriptClicIngresar1);
//				checkboxAcepto.click();
			}
			Evidence.save("Informaciï¿½n PEP", this);
//			Evidence.save("InformaciÃ³n PEP");
			Util.wait(1);
			botonContinuar.click();

		}
	}

	// =========================================================================================================================================

	/**
	 * Metodo que retorna el mensaje de alerta si este existe en divisas. Si el
	 * retorno es [null] es porque NO existe un mensaje de alerta.<br>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getActiveIntAlert() throws Exception {
		int contador = 1;
		String msgResp;

		if (this.element(btnModalPopup) != null && this.isDisplayed(btnModalPopup) && this.isEnabled(btnModalPopup)) {
			do {
				Util.wait(1);
				contador++;
			} while (this.element(btnModalPopup) != null && !this.isDisplayed(btnModalPopup)
					&& !this.isEnabled(btnModalPopup) && contador <= 10);

			msgResp = this.getMsgAlertIfExist("AlertaModal");// cambiar el extraer mensaje popup

		} else if (this.element(btnPopup) != null && this.isDisplayed(cmpPopup) && this.isEnabled(btnPopup)) {
			do {
				Util.wait(1);
				contador++;
			} while (!this.isDisplayed(btnPopup) && !this.isEnabled(btnPopup) && contador <= 10);

			msgResp = this.getMsgAlertIfExist("mensaje");// cambiar el extraer mensaje popup
		} else if (this.element(AlertPopupAdver) != null && this.isDisplayed(AlertPopupAdver)
				&& this.isEnabled(AlertPopupAdver)) {
			Util.wait(1);
			msgResp = this.element(AlertPopupAdver).getAttribute("outerText");

		} else {
			do {
				Util.wait(1);
				contador++;
			} while (this.element(btnPopup2) != null && !this.isDisplayed(btnPopup2) && !this.isEnabled(btnPopup2)
					&& contador <= 10);

			msgResp = this.getMsgAlertIfExistxPath(Alermod);// cambiar el extraer mensaje popup
		}

//			Util.wait(1);
		if (this.isDisplayed(btnModalPopup) && this.isEnabled(btnModalPopup)) {
			Evidence.saveAllScreens("Alert", this);
		} else if (this.isDisplayed(btnPopup2) && this.isEnabled(btnPopup2)) {
			Evidence.saveAllScreens("Alert", this);
		} else if (this.isDisplayed(btnPopup) && this.isDisplayed(cmpPopup) && this.isEnabled(btnPopup)) {
			Evidence.saveAllScreens("Alert", this);
		} else if (this.element(AlertPopupAdver) != null && this.isDisplayed(AlertPopupAdver)
				&& this.isEnabled(AlertPopupAdver)) {
			Evidence.saveAllScreens("Alert", this);

		}

		return msgResp;
	}

	// =========================================================================================================================================

	/**
	 * Metodo que retorna el mensaje de alerta si este existe en divisas. Si el
	 * retorno es [null] es porque NO existe un mensaje de alerta.<br>
	 * 
	 * @return Mensaje Alerta del portal
	 * @throws Exception
	 */
	public String closeActiveIntAlert() throws Exception {
		int contador = 1;
		String msgResp = null;

		if (this.element(btnModalPopup) != null && this.isDisplayed(btnModalPopup) && this.isEnabled(btnModalPopup)) {
			do {
				Util.wait(1);
				contador++;
			} while (!this.isDisplayed(btnModalPopup) && !this.isEnabled(btnModalPopup) && contador <= 10);
			msgResp = this.getMsgAlertIfExist("AlertaModal");

		} else if (this.element(cmpPopup) != null && this.isDisplayed(cmpPopup) && this.isEnabled(btnPopup)) {
			do {
				Util.wait(1);
				contador++;
			} while (!this.isDisplayed(btnPopup) && !this.isEnabled(btnPopup) && contador <= 10);

			msgResp = this.getMsgAlertIfExist("mensaje");
		} else if (this.element(AlertPopupAdver) != null && this.isDisplayed(AlertPopupAdver)
				&& this.isEnabled(AlertPopupAdver)) {
			do {
				Util.wait(1);
				contador++;
			} while (!this.isDisplayed(AlertPopupAdver) && !this.isEnabled(AlertPopupAdver) && contador <= 10);

			msgResp = this.getMsgAlertIfExist("mensaje");
		} else {
			do {
				Util.wait(1);
				contador++;
			} while (this.element(btnPopup2) != null && !this.isDisplayed(btnPopup2) && !this.isEnabled(btnPopup2)
					&& contador <= 10);

			msgResp = this.getMsgAlertIfExistxPath(Alermod);
		}

		if (this.element(Alermod2) != null && this.isDisplayed(this.element(Alermod2))
				&& this.isEnabled(this.element(Alermod2))) {
			do {
				Util.wait(6);
				contador++;
			} while (!this.isDisplayed(this.element(Alermod2)) && !this.isEnabled(this.element(Alermod2))
					&& contador <= 10);
			msgResp = this.getMsgAlertIfExistxPath(Alermod2);// cambiar el extraer

		}

//			Util.wait(1);
		if (this.isDisplayed(btnModalPopup) && this.isEnabled(btnModalPopup)) {
			Evidence.saveAllScreens("Alert", this);
			this.click(btnModalPopup);
		} else if (this.isDisplayed(btnPopup2) && this.isEnabled(btnPopup2)) {
			Evidence.saveAllScreens("Alert", this);
			this.click(btnPopup2);
		} else if (this.isDisplayed(cmpPopup) && this.isEnabled(btnPopup)) {
			Evidence.saveAllScreens("Alert", this);
			this.click(btnPopup);
		} else if (this.isDisplayed(AlertPopupAdver) && this.isEnabled(AlertPopupAdver)) {
			Evidence.saveAllScreens("Alert", this);
			this.click(btnAcetarAler);
		} else if (this.isDisplayed(this.element(Alermod2)) && this.isEnabled(this.element(Alermod2))) {
			Evidence.saveAllScreens("Alert", this);
			if (this.element(btnPopupaceptar) != null)
				this.click(btnPopupaceptar);
			if (this.element(btnPopupAvertaceptar) != null)
				this.click(btnPopupAvertaceptar);
		}

		return msgResp;
	}
	
	
	//***********************************************************************************************************************    
		/**
		 * M�todo SeleOption Seleciona la opcion de una lista por su mismo valor.
		 * 
		 * @param locator
		 * @param opcion
		 * @return
		 * @throws Exception
		 */
		public String seleOption(By locator, String opcion) throws Exception {
			String msg = "";
			WebElement list = this.element(locator);
			this.click(locator);
			// SELECIONA LA OPCION DE UNA LISTA
			List<WebElement> options = list.findElements(By.tagName("option"));
			for (WebElement seleoption : options) {
				if (seleoption.getText().contains(opcion)) {
//	                le da clic a la opci�n selecionada
					seleoption.click();
//	                Devuelve la opcion selecionada
					msg = String.valueOf(seleoption.getAttribute("text"));
				}
			}
			return msg;
		}
	
	/**
	 * Metodo BonotesTecla LLama los botones expecificos a llamar ejemplo (Bot�n
	 * TAB, bot�n ENTER)
	 * 
	 * @throws AWTException
	 * 
	 */
	public static String BonotesTecla(String tecla) throws AWTException {
		String numCliented = tecla;

		Robot robot = new Robot();

		switch (tecla) {
		case "ALTTAB":
			// Suelta la tecla ALT
			robot.keyPress(KeyEvent.VK_ALT);
			// Presiona la tecla TAB
			robot.keyPress(KeyEvent.VK_TAB);
			// Suelta la tecla TAB
			robot.keyRelease(KeyEvent.VK_TAB);
			// Suelta la tecla ALT
			robot.keyRelease(KeyEvent.VK_ALT);
			break;
		case "TAB":
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			break;
		case "ENTER":
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			break;
		case "ABAJO":
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			break;
		case "F9":
			robot.keyPress(KeyEvent.VK_F9);
			robot.keyRelease(KeyEvent.VK_F9);
			break;
		case "CTRLT":
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			break;
		case "SPACE":
			robot.keyPress(KeyEvent.VK_SPACE);
			robot.keyRelease(KeyEvent.VK_SPACE);
			break;
		}
		return tecla;
	}
}