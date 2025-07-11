package pages.actions.Divisas;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;

import dav.c360.PageInicioC360;
import library.reporting.Reporter;
import library.settings.SettingsRun;
import library.common.Util;
import library.core.BasePageWeb;
import library.reporting.Evidence;

import pages.locators.Divisas.LocatorsDivisas;

public class PageDivisas extends BasePageWeb {

	LocatorsDivisas loc;
	public By iframeIdDivisas = By.id("cphCuerpo_IframeDivisas");
	public By iframeIdDivisasEmpresarial = By.name("contenido");

	public PageDivisas(BasePageWeb parentPage) {

		super(parentPage);
		initializeLocators();

	}

	private void initializeLocators() {

		loc = new LocatorsDivisas();
		AjaxElementLocatorFactory ajaxElementLocatorFactory = new AjaxElementLocatorFactory(this.getDriver(), 0);
		PageFactory.initElements(ajaxElementLocatorFactory, this.loc);
	}

// =======================================================================================================================

	static String numAprova = null;

	private static int contador = 1;

	/**
	 * Llama el dato de numero de aprobacion desde la transaccion
	 */
	public void SetNumAprInterna(String numAproTx) {
		numAprova = numAproTx;
	}

// =======================================================================================================================

	/**
	 * Espera hasta que el iframe de divisas estÃ© disponible, lo selecciona y hace
	 * zoom.
	 * 
	 * @return true si se cargÃ³ correctamente, false si hubo timeout.
	 * @throws Exception
	 */
	public boolean switchToFrameDivisas() throws Exception {
		int contador = 0;

		while (contador <= 30) {
			Util.wait(1);
			WebElement iframe = this.element(loc.iframeIdDivisas);
			if (iframe != null) {
				this.getDriver().switchTo().frame(iframe);
				this.getJse().executeScript("document.body.style.zoom ='90%';");
				return true;
			}

			if (iframe == null) {
				iframe = this.element(loc.iframeIdDivisasEmpresarial);
				if (iframe != null) {
					this.getDriver().switchTo().frame(iframe);
					return true;
				}
			}
			contador++;

			if (isElementInteractable(loc.sesionEx)) {
				String msg = this.element(loc.sesionEx).getText();
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}
			
			this.getDriver().switchTo().defaultContent();
		}
		this.getDriver().switchTo().defaultContent();
		Reporter.reportEvent(Reporter.MIC_FAIL, "TimeOut: No se presentá el módulo de divisas");
		return false;
	}

// =======================================================================================================================

	public String seleDivisasEmpresarial(String servicio) throws Exception {
		contador = 0;
		List<WebElement> elemntBtnMenuDivisas = null;
		do {
			Util.wait(1);
			elemntBtnMenuDivisas = this.findElements(loc.moduSelDivisasEmpresarial);
			if (contador >= 30) {
				// Divisas
				this.getDriver().switchTo().defaultContent();
				Evidence.save("No se encuentra el modulo: " + servicio, this);
				return "No se encuentra el modulo: " + servicio;
			}
			contador++;
		} while (elemntBtnMenuDivisas == null || elemntBtnMenuDivisas.isEmpty());

		if (elemntBtnMenuDivisas.size() > 1) {
			// Si hay más de uno, da clic solo al segundo (índice 1)
			elemntBtnMenuDivisas.get(0).click();
			Util.wait(1);
			elemntBtnMenuDivisas.get(1).click();
		} else if (elemntBtnMenuDivisas.size() == 1) {
			// Si solo hay uno, da clic al primero (índice 0)
			elemntBtnMenuDivisas.get(0).click();
		} else {
			// Si no hay elementos, retorna mensaje de error
			return "No se encontraron elementos para Divisas Empresarial";
		}

		return null;
	}

	/**
	 * Se en carga de selecionar el modulo de Divisas: [Recibir dinero del exterior
	 * - Enviar Transferencias Internacionales - Aprobaciones - Documentos y
	 * Formularios - Consultas]
	 * 
	 * @param servicio: [Recibir dinero del exterior - Enviar Transferencias
	 *                  Internacionales - Aprobaciones - Documentos y Formularios -
	 *                  Consultas]
	 * @return
	 * @throws Exception
	 */
	public String seleccionarTransferencia(String servicio) throws Exception {

		String msg = this.ErrorSesionExpirada();
		if (msg != null) {
			this.getDriver().switchTo().defaultContent();
			return msg;
		}
		Evidence.save("Transferencias internacionales", this);

		switchToFrameDivisas();

		String msgError = this.getMsgAlertIfExist("lblMasterAlerta");
		if (isValid(msgError)) {
			return msgError;
		}

		contador = 0;
		do {
			contador++;
			Util.wait(1);
			if (contador >= 30) {
				// Divisas
				this.getDriver().switchTo().defaultContent();
				Evidence.save("No se encuentra el modulo: " + servicio, this);
				return "No se encuentra el modulo: " + servicio;
			}

			if (isElementInteractable(loc.popMenAler)) {
				Util.wait(5);
				String msAlertam = this.getText(this.element(loc.popMenAler));
				Reporter.reportEvent(Reporter.MIC_FAIL, msAlertam);
				this.getDriver().switchTo().defaultContent();
				Evidence.save(msAlertam, this);
				return msAlertam;
			}

			msgError = this.getMsgAlertIfExist("lblMasterAlerta");
			if (isValid(msgError)) {
				return msgError;
			}

		} while (this.element(loc.moduloSelecionarDivisas.replace("MENU", servicio)) == null);

		this.click(By.xpath(loc.moduloSelecionarDivisas.replace("MENU", servicio)));

		msg = this.ErrorSesionExpirada();

		if (msg != null) {
			this.getDriver().switchTo().defaultContent();
			return msg;
		}
		
		msgError = this.getMsgAlertIfExist("lblMasterAlerta");

		if (isValid(msgError)) {
			return msgError;
		}
		return "";
	}

// =======================================================================================================================

	public String obtenerNumeroTxDocumentoGeneral(String tipoPrueba, String servicio, String page, String fecha,
			String hora, String moneda) throws Exception {
		String documentoTx = null;
		String numAprovaLocal = numAprova;

		// 1. Intentar por nÃºmero de aprobaciÃ³n
		if (!isValid(numAprovaLocal)) {
			numAprovaLocal = SettingsRun.getTestData().getParameter("Número Aprobación");
		}

		if (isValid(numAprovaLocal)) {

			documentoTx = loc.xpathNumDocumTxCon.replace("fechayhoraconvert", "").replace("MONEDA", "").replace("DOCID",
					numAprovaLocal);

			if (this.element(documentoTx) == null) {
				Reporter.reportEvent(Reporter.MIC_FAIL, "El numero de Aprobacion no se encontro: " + numAprova);
				documentoTx = null;

			} else {

				String objeto = this.element(documentoTx).getText();
				if (objeto.equals("Editar")) {
					this.element(documentoTx).click();
					return numAprovaLocal;

				} else {
					return documentoTx;
				}

			}

			if (isValid(documentoTx))
				return documentoTx;
		}

		// 2. Intentar por fecha y hora (con varios intentos de ajuste)

		if (isValid(fecha) && isValid(hora)) {

			// Buscar botÃ³n "Siguiente"
			documentoTx = SiguientePagina(tipoPrueba, servicio, fecha, hora, moneda, page);

			String horaIntento = hora;
			horaIntento = horaAdd("HH:mm", hora, 1);

			documentoTx = findDocumentWithTime(tipoPrueba, servicio, fecha, horaIntento, moneda, page);

			if (isValid(documentoTx))
				return documentoTx;

			for (int minutosARestar = 0; minutosARestar <= 5; minutosARestar++) {
				horaIntento = hora;
				for (int i = 0; i < minutosARestar; i++)
					horaIntento = subtractOneMinute(horaIntento);

				documentoTx = findDocumentWithTime(servicio, horaIntento, moneda);

				if (!isValid(documentoTx)) {

					documentoTx = findDocumentWithTime(tipoPrueba, servicio, fecha, horaIntento, moneda, page);
				}

				if (isValid(documentoTx))
					return documentoTx;
			}

			Reporter.reportEvent(Reporter.MIC_FAIL, "Error: Documento no encontrado con tiempos ajustados.");
		}

		return documentoTx;
	}

	public String SiguientePagina(String pendieteAprobar, String servicio, String fecha, String hora, String moneda,
			String page) throws Exception {

		// Buscar botÃ³n "Siguiente"
		WebElement btnSiguiente = null;

		String documentoTx = null;

		btnSiguiente = this.element(By.xpath("//button[contains(text(),'Siguiente')]"));

		if (isElementInteractable(btnSiguiente)) {

			WebElement paginaElement = this.element(By.xpath("//*[@id='pagina']"));

			// Obtiene la opciÃ³n seleccionada
			String paginaActualStr = null;
			int paginaActual = 0;
			int totalPaginas = 0;
			Select dropdown = null;
			String[] paginas = null;

			// Crea un objeto Select para manipularlo
			if (paginaElement != null) {
				dropdown = new Select(paginaElement);
				paginaActualStr = dropdown.getFirstSelectedOption().getText().trim();
			}

			if (isValid(paginaActualStr)) {
				paginas = paginaActualStr.split("de");
				paginaActual = Integer.parseInt(paginas[0].trim());
				totalPaginas = Integer.parseInt(paginas[1].trim());
			}

			do {

				btnSiguiente = this.element(By.xpath("//button[contains(text(),'Siguiente')]"));

				// Buscar la fecha y hora en la pÃ¡gina actual
				List<WebElement> listaFecha = null;
				List<WebElement> listahora = null;

				if (isValid(fecha))
					listaFecha = this
							.findElements(By.xpath(loc.xpathBuscarFechayHora.replace("fechayhoraconvert", fecha)));

				String horaconver = null;

				String[] horaes = hora.split(":");

				if (isValid(hora) && !horaes[0].equals("12")
						&& !servicio.equals("Consulta Tx Internacionales Validar Estado"))
					horaconver = convertirHoraSiPM(hora);

				else

					horaconver = hora;

				listahora = this
						.findElements(By.xpath(loc.xpathBuscarFechayHora.replace("fechayhoraconvert", horaconver)));

				// Si encuentra ambos, intenta obtener el documento
				if (listaFecha != null && listahora != null) {

					documentoTx = findDocumentWithTime(servicio, hora, moneda);

					if (!isValid(documentoTx)) {

						documentoTx = findDocumentWithTimeAfterDelay(pendieteAprobar, servicio, fecha, hora, moneda,
								page);
					}
					if (isValid(documentoTx)) {
						// Documento encontrado
						return documentoTx;
					}
				}

				paginaElement = this.element(By.xpath("//*[@id='pagina']"));
				// Crea un objeto Select para manipularlo
				dropdown = new Select(paginaElement);

				// Obtiene la opciÃ³n seleccionada
				paginaActualStr = dropdown.getFirstSelectedOption().getText().trim();
				paginas = paginaActualStr.split("de");

				paginaActual = Integer.parseInt(paginas[0].trim());

				// Verificamos si ya estamos en la Ãºltima pÃ¡gina
				if (paginaActual >= totalPaginas) {
					return null;
				}

				// Verificar si el botÃ³n siguiente estÃ¡ disponible
				btnSiguiente = this.element(By.xpath("//button[contains(text(),'Siguiente')]"));

				if (isElementInteractable(btnSiguiente)) {
					this.click(btnSiguiente);
					Util.wait(10); // Espera a que cargue la siguiente pÃ¡gina
				} else {
					return null;
				}

			} while (!isElementInteractable(btnSiguiente));
		}

		return documentoTx;

	}

	// ============================================[setTime]===========================================================================
	public void setTime(Date fechaTxTemp) throws Exception {
		String today = "";
		String hour = "";
		if (fechaTxTemp != null) {
			today = Util.dateToString(fechaTxTemp, "dd/mm/yyyy");
			hour = Util.hourToString(fechaTxTemp, "HH:mm");
			hour = this.convertTime(hour);
		}
		SettingsRun.getTestData().setParameter("Fecha tx", today);
		SettingsRun.getTestData().setParameter("Hora tx", hour);

	}

	private String convertTime(String fechaConverTime) throws Exception {
		SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm");

		Date date = inputFormat.parse(fechaConverTime);
		String convertedTime = outputFormat.format(date);
		return convertedTime;
	}

	// ============================================[findDocumentWithTimeAfterDelay]===========================================================================

	public String findDocumentWithTimeAfterDelay(String pendieteAprobar, String servicio, String fecha,
			String horaconvert, String moneda, String page) throws Exception {
		// Convierte minutos a milisegundos
		return findDocumentWithTime(pendieteAprobar, servicio, fecha, horaconvert, moneda, page);
	}

	// ============================================[findDocumentWithTime]===========================================================================

	/**
	 * Obtiene el nÃºmero de transacciÃ³n o documento de la transacciÃ³n segÃºn
	 * fecha y hora.
	 *
	 * @param fecha       Fecha de la transacciÃ³n en formato esperado (ej.
	 *                    "yyyy-MM-dd")
	 * @param horaConvert Hora de la transacciÃ³n en formato esperado (ej. "HH:mm")
	 * @return NÃºmero de transacciÃ³n o documento, o null si ocurre un error
	 * @throws Exception en caso de error en el procesamiento
	 */
	public String findDocumentWithTime(String tipoPrueba, String servicio, String fecha, String horaconvert,
			String moneda, String page) throws Exception {

		WebElement obTNumDocumTxCon = null;
		try {

			String fechayHoraconver = null;

			if (servicio.equals("Consulta Tx Internacionales Enviar al exterior Validar Estado")
					&& !tipoPrueba.contains("Pend")) {

				fechayHoraconver = fecha + " " + horaconvert;

				obTNumDocumTxCon = this.element(loc.xpathNumDocumTxCon2.replace("fechayhoraconvert", fechayHoraconver)
						.replace("MONEDA", moneda));
				Reporter.write(fechayHoraconver);

				if (obTNumDocumTxCon != null) {
					return obTNumDocumTxCon.getText();
				}

				if (isValid(horaconvert) && !horaconvert.equals("12")
						&& !servicio.equals("Consulta Tx Internacionales Validar Estado"))
					fechayHoraconver = fecha + " " + convertirHoraSiPM(horaconvert);

				obTNumDocumTxCon = this.element(loc.xpathNumDocumTxCon2.replace("fechayhoraconvert", fechayHoraconver)
						.replace("MONEDA", moneda));
				Reporter.write(fechayHoraconver);

				if (obTNumDocumTxCon != null) {
					return obTNumDocumTxCon.getText();
				}

			}

			if (!servicio.equals("Consulta Tx Internacionales Enviar al exterior Validar Estado")
					&& tipoPrueba.contains("Pend") && page.equals("Aprobaciones")) {

				obTNumDocumTxCon = this.element(
						loc.xpathNumDocumTxCon3.replace("fechayhoraconvert", horaconvert).replace("MONEDA", moneda));
				Reporter.write(horaconvert);

				if (obTNumDocumTxCon != null) {
					return obTNumDocumTxCon.getText();
				}

			} else {

				fechayHoraconver = horaconvert;
				if (isValid(horaconvert) && !horaconvert.equals("12")
						&& !servicio.equals("Consulta Tx Internacionales Validar Estado"))
					fechayHoraconver = convertirHoraSiPM(fechayHoraconver);
				Reporter.write(fechayHoraconver);

				obTNumDocumTxCon = this.element(loc.xpathNumDocumTxCon2.replace("fechayhoraconvert", fechayHoraconver)
						.replace("MONEDA", moneda));

				if (obTNumDocumTxCon != null) {
					return obTNumDocumTxCon.getText();
				}

				String horaConver = null;

				String[] fechayHoraSepara = fechayHoraconver.split(" ");

				String[] horaSepara = fechayHoraSepara[1].split(":");

				if (isValid(horaconvert) && !horaSepara[0].equals("12"))

					fechayHoraconver = fecha + " " + convertirHoraSiPM(fechayHoraSepara[1]);

				else

					fechayHoraconver = fecha + " " + horaConver;

				Reporter.write(fechayHoraconver);

				obTNumDocumTxCon = this.element(loc.xpathNumDocumTxCon2.replace("fechayhoraconvert", fechayHoraconver)
						.replace("MONEDA", moneda));

				if (obTNumDocumTxCon != null) {
					return obTNumDocumTxCon.getText();
				}
			}

		} catch (Exception e) {

			return null;
		}

		return null;
	}

	/**
	 * Este metodo Obtiene el [NÃºmero de tx o Documento de la Tx] con la fecha y
	 * Hora si son necesarios
	 * 
	 * @param fecha       Tx
	 * @param horaconvert Tx
	 * @return Retorna el [NÃºmero de tx o Documento de la Tx] o null
	 * @throws Exception
	 */
	public String findDocumentWithTime(String servicio, String horaconvert, String moneda) throws Exception {

		String documentoTx = "";
		String fechayHoraconver = "";

		if (isValid(numAprova))
			documentoTx = numAprova;

		String[] horaes = horaconvert.split(":");
		if (isValid(horaconvert) && !horaes[0].equals("12")
				&& !servicio.equals("Consulta Tx Internacionales Validar Estado"))
			fechayHoraconver = convertirHoraSiPM(horaconvert);

		else
			fechayHoraconver = horaconvert;

		try {
			Util.wait(1);

			String Obje = loc.xpathNumDocumTxCon.replace("fechayhoraconvert", fechayHoraconver)
					.replace("MONEDA", moneda).replace("DOCID", documentoTx);

			WebElement ObjetodocumentoTx = null;

			ObjetodocumentoTx = this.element(Obje);
			if (ObjetodocumentoTx == null)
				Obje = loc.xpathNumDocumTxObt.replace("fechayhoraconvert", fechayHoraconver).replace("MONEDA", moneda);

			ObjetodocumentoTx = this.element(Obje);
			if (!isElementPresentAndNotInteractable(ObjetodocumentoTx))
				documentoTx = ObjetodocumentoTx.getText();
			if (isValid(documentoTx) && !documentoTx.contains(".") && !documentoTx.contains(",")) {

				numAprova = documentoTx;
				return documentoTx;
			} else {
				return null;
			}

		} catch (Exception e) {

			return null;
		}

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

		switchToFrameDivisas();

		if (isElementInteractable(loc.btnModalPopup)) {

			do {
				Util.wait(1);
				contador++;
			} while (isElementPresentAndNotInteractable(loc.btnModalPopup) && contador <= 7);

			msgResp = this.getMsgAlertIfExist("AlertaModal");

		} else if (isElementInteractable(loc.btnPopup)) {

			do {
				Util.wait(1);
				contador++;
			} while (isElementPresentAndNotInteractable(loc.btnPopup) && contador <= 7);

			msgResp = this.getMsgAlertIfExist("mensaje");
		} else if (isElementInteractable(loc.AlertPopupAdver)) {
			do {
				Util.wait(1);
				contador++;
			} while (isElementPresentAndNotInteractable(loc.AlertPopupAdver) && contador <= 7);

			msgResp = this.getMsgAlertIfExist("mensaje");
		} else {
			do {
				Util.wait(1);
				contador++;
			} while (isElementPresentAndNotInteractable(loc.btnPopup2) && contador <= 7);

			msgResp = this.getMsgAlertIfExistxPath(loc.Alermod);
		}

		if (isElementInteractable(loc.Alermod2)) {

			do {
				Util.wait(6);
				contador++;
			} while (isElementPresentAndNotInteractable(loc.Alermod2) && contador <= 7);
			msgResp = this.getMsgAlertIfExistxPath(loc.Alermod2);// cambiar el extraer

		}

		if (isElementInteractable(loc.btnModalPopup)) {
			Evidence.saveAllScreens("Alert", this);
			this.click(loc.btnModalPopup);
		} else if (isElementInteractable(loc.btnPopup2)) {
			Evidence.saveAllScreens("Alert", this);
			this.click(loc.btnPopup2);
		} else if (isElementInteractable(loc.btnPopup)) {
			Evidence.saveAllScreens("Alert", this);
			this.click(this.element(loc.btnPopup));
		} else if (isElementInteractable(loc.AlertPopupAdver)) {
			Evidence.saveAllScreens("Alert", this);
			this.click(loc.btnAcetarAler);
		} else if (isElementInteractable(loc.Alermod2)) {
			Evidence.saveAllScreens("Alert", this);
			if (isElementInteractable(loc.btnPopupaceptar))
				this.click(loc.btnPopupaceptar);
			if (isElementInteractable(loc.btnPopupAvertaceptar))
				this.click(this.element(loc.btnPopupAvertaceptar));
		}

		return msgResp;
	}

	/**
	 * Retorna el mensaje de alerta si existe alguna alerta activa en divisas, sin
	 * cerrar el popup. Realiza la gestiÃ³n adecuada de los diferentes tipos de
	 * popups presentes en la pÃ¡gina y guarda evidencia si corresponde.
	 *
	 * @return Mensaje de alerta mostrado o null si no existe alerta activa.
	 * @throws Exception Si ocurre un error durante la interacciÃ³n.
	 */
	public String getActiveIntAlert() throws Exception {
		int contador = 1;
		String msgResp = null;

		if (isElementInteractable(loc.btnModalPopup)) {
			do {
				Util.wait(1);
				contador++;
			} while (isElementPresentAndNotInteractable(loc.btnModalPopup) && contador <= 10);

			msgResp = this.getMsgAlertIfExist("AlertaModal"); // cambiar el extraer mensaje popup

		} else if (isElementInteractable(loc.btnPopup)) {
			do {
				Util.wait(1);
				contador++;
			} while (isElementPresentAndNotInteractable(loc.btnPopup) && contador <= 10);

			msgResp = this.getMsgAlertIfExist("mensaje"); // cambiar el extraer mensaje popup

		} else if (isElementInteractable(loc.AlertPopupAdver)) {
			Util.wait(1);
			msgResp = this.element(loc.AlertPopupAdver).getAttribute("outerText");

		} else {
			do {
				Util.wait(1);
				contador++;
			} while (isElementPresentAndNotInteractable(loc.btnPopup2) && contador <= 10);

			msgResp = this.getMsgAlertIfExistxPath(loc.Alermod); // cambiar el extraer mensaje popup
		}

		// Guardar evidencia visual de la alerta detectada
		if (isElementInteractable(loc.btnModalPopup)) {
			Evidence.saveAllScreens("Alert", this);
		} else if (isElementInteractable(loc.btnPopup2)) {
			Evidence.saveAllScreens("Alert", this);
		} else if (isElementInteractable(loc.btnPopup) && isElementInteractable(loc.cmpPopup)) {
			Evidence.saveAllScreens("Alert", this);
		} else if (isElementInteractable(loc.AlertPopupAdver)) {
			Evidence.saveAllScreens("Alert", this);
		}

		return msgResp;
	}

	// =========================================================================================================================================

	/**
	 * Cierra el mensaje alerta (popup) que requiere confirmaciÃ³n en la secciÃ³n de
	 * divisas. Gestiona diferentes tipos de popups, guarda evidencia y retorna el
	 * mensaje de alerta mostrado.
	 *
	 * @return Mensaje de alerta mostrado, o "TimeOut" si no se puede cerrar la
	 *         alerta dentro del tiempo mÃ¡ximo.
	 * @throws Exception Si ocurre un error durante la interacciÃ³n con los popups.
	 */
	public String closeActiveIntAlertConfirma() throws Exception {
		int contador = 1;
		String msgResp = null;

		// Esperar a que el popup estÃ© disponible o expire el tiempo mÃ¡ximo de espera
		do {
			Util.wait(1);
			contador++;

			if (this.element(loc.sesionEx) != null) {
				String msg = this.element(loc.sesionEx).getText();
				Evidence.save("msg", this);
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}

			if (isElementInteractable(loc.cmpPopup)) {
				Evidence.save("AlertPopup", this);
			}

		} while (isElementPresentAndNotInteractable(loc.btnPopup) && contador <= 7);

		// Obtener el mensaje de alerta si existe en los diferentes posibles popups
		if (this.element("//*[@id='mensajeerror']") != null) {

			msgResp = this.getMsgAlertIfExist("mensajeerror");// cambiar el extraer mensaje popup

		} else if (this.element("//*[@id='mensaje']") != null) {

			msgResp = this.getMsgAlertIfExist("mensaje");// cambiar el extraer mensaje popup

		} else if (loc.Alermod != null) {

			msgResp = this.getMsgAlertIfExistxPath(loc.Alermod); // cambiar el extraer mensaje popup
		}

		// Cerrar el popup y guardar evidencia
		if (isElementInteractable(loc.btnPopup)) {

			if (isElementInteractable(loc.btnPopup)) {
				Evidence.save("MensajeAlert", this);
				this.click(this.element(loc.btnPopup));
			}

		} else if (isElementInteractable(loc.btnPopup)) {

			if (loc.Alermod != null) {

				msgResp = this.getMsgAlertIfExistxPath(loc.Alermod);// cambiar el extraer mensaje popup
			}

			if (isElementInteractable(loc.btnPopup)) {

				Evidence.save("MensajeAlert", this);
				this.click(this.element(loc.btnPopup));

			}
		}

		return msgResp;
	}

// =========================================================================================================================================

	/**
	 * Validad que si aparcese el mensaje de SesiÃ³n no existe o ha expirado cierra
	 * sesiÃ³n.
	 * 
	 * @throws Exception
	 */
	public String ErrorSesionExpirada() throws Exception {
		contador = 0;
		do {
			switchToFrameDivisas();
			Util.wait(1);
			contador++;
			if (this.element(loc.sesionEx) != null) {

				Evidence.save("Sesión no existe o ha expirado", this);
				this.getDriver().switchTo().defaultContent();
				return "Sesión no _Existe o ha expirado";
			}
		} while (contador <= 5);

		return null;
	}

// =========================================================================================================================================

	/**
	 * Si aparece el mensaje de sesion expirada cierra sesion, pasa al siguiente row
	 */
	public String waitForElementToAppear(String elementLocator) throws Exception {
		contador = 0;
		String msg = null;
		do {
			Util.wait(1);
			if (contador >= 30) {
				if (loc.sesionEx != null) {
					msg = this.element(loc.sesionEx).getText();
					if (isValid(msg))
						Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				}

				return "TimeOut No se presentá el campo";
			}
		} while (this.element(elementLocator) == null);
		return msg;
	}

// =========================================================================================================================================

	public String assertElementExists(By elementLocator, String successMessage) throws Exception {
		String msg = null;

		if (this.element(elementLocator) != null) {
			Reporter.reportEvent(Reporter.MIC_PASS, successMessage);
			msg = successMessage;
		} else {
			Reporter.reportEvent(Reporter.MIC_FAIL, "No se encuentra el elemento.");
			msg = "No se encuentra el elemento.";
		}
		return msg;
	}

// =========================================================================================================================================
	/**
	 * Valida que una cadena no sea null, no estÃ© vacÃ­a ni contenga solo espacios
	 * en blanco.
	 *
	 * @param datoValidar la cadena a validar
	 * @return true si la cadena contiene al menos un carÃ¡cter no vacÃ­o y no es
	 *         null; false en caso contrario
	 */
	protected boolean isValid(String datoValidar) {
		return datoValidar != null && !datoValidar.trim().isEmpty();
	}

// =========================================================================================================================================

	/**
	 * Verifica si un WebElement ubicado por un String es visible y habilitado en la
	 * pÃ¡gina.
	 *
	 * @param locator el localizador del elemento (xpath)
	 * @return true si el elemento existe, estÃ¡ visible y habilitado; false en caso
	 *         contrario
	 */
	protected boolean isElementInteractable(String xpath) {
		WebElement element = this.element(xpath);
		return isElementInteractable(element);
	}

	/**
	 * Verifica si un WebElement ubicado por un By es visible y habilitado en la
	 * pÃ¡gina.
	 *
	 * @param locator el localizador del elemento (By)
	 * @return true si el elemento existe, estÃ¡ visible y habilitado; false en caso
	 *         contrario
	 */
	protected boolean isElementInteractable(By locator) {
		WebElement element = this.element(locator);
		return isElementInteractable(element);
	}

	/**
	 * Verifica si un WebElement ya localizado es visible y habilitado.
	 *
	 * @param element el elemento WebElement
	 * @return true si el elemento es visible y habilitado; false en caso contrario
	 */
	protected boolean isElementInteractable(WebElement element) {
		return element != null && this.isDisplayed(element) && this.isEnabled(element);
	}

	/**
	 * Verifica si un elemento identificado por un localizador By existe en el DOM,
	 * pero NO estÃ¡ visible o habilitado.
	 *
	 * @param locator Localizador del elemento (By).
	 * @return true si el elemento existe pero no es visible o habilitado; false en
	 *         caso contrario.
	 */
	protected boolean isElementPresentAndNotInteractable(By locator) {
		WebElement el = this.element(locator);
		return el != null && (!this.isDisplayed(el) || !this.isEnabled(el));
	}

	/**
	 * Verifica si un elemento recibido directamente como WebElement existe, pero NO
	 * estÃ¡ visible o habilitado.
	 *
	 * @param element Elemento WebElement a validar.
	 * @return true si el elemento existe pero no es visible o habilitado; false en
	 *         caso contrario.
	 */
	protected boolean isElementPresentAndNotInteractable(WebElement element) {
		return element != null && (!this.isDisplayed(element) || !this.isEnabled(element));
	}

	/**
	 * Verifica si un elemento identificado por un localizador String (por ejemplo,
	 * un xpath) existe en el DOM, pero NO estÃ¡ visible o habilitado.
	 *
	 * @param locator Localizador del elemento (tipo String, por ejemplo un xpath).
	 * @return true si el elemento existe pero no es visible o habilitado; false en
	 *         caso contrario.
	 */
	protected boolean isElementPresentAndNotInteractable(String locator) {
		WebElement el = this.element(locator);
		return el != null && (!this.isDisplayed(el) || !this.isEnabled(el));
	}

// =========================================================================================================================================

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
// =========================================================================================================================================

	/**
	 * Metodo que retorna el mensaje de alerta si este existe. Si el retorno es
	 * [null] es porque NO existe un mensaje de alerta.<br>
	 * Se recibe por parametro un listado de los posibles id de las alertas que se
	 * pueden presentar.
	 */
	public String getMsgAlertIfExistxPath(WebElement... xPathExpressions) {
		String msgAlert = null;

		for (WebElement xPathExpression : xPathExpressions) {

			if (xPathExpression != null) {
				Util.wait(1);
				try {
					msgAlert = xPathExpression.getText().trim();
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

// =========================================================================================================================================

	/**
	 * Mï¿½todo que retorna el mensaje de alerta si este existe. Si el retorno es
	 * [null] es porque NO existe un mensaje de alerta.<br>
	 * Se recibe por parï¿½metro un listado de los posibles id de las alertas que se
	 * pueden presentar.
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

	// =========================================================================================================================================

	/**
	 * Método SeleOption Seleciona la opcion de una lista por su mismo valor.
	 * 
	 * @param locator
	 * @param opcion
	 * @return
	 * @throws Exception
	 */
	public String seleOptionGetAttribu(By locator, String opcion) throws Exception {
		String msg = "";
		WebElement list = this.element(locator);
		list.click();
		// SELECIONA LA OPCION DE UNA LISTA
		List<WebElement> options = list.findElements(By.tagName("option"));
		for (WebElement seleoption : options) {
			if (seleoption.getAttribute("innerText").contains(opcion)) {
//                le da clic a la opci�n selecionada
				seleoption.click();
//                Devuelve la opcion selecionada
				msg = "elemento selecionado";
			}
		}
		return msg;
	}

// =========================================================================================================================================

	/**
	 * Método SeleOption Seleciona la opcion de una lista por su mismo valor.
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
//                le da clic a la opci�n selecionada
				seleoption.click();
//                Devuelve la opcion selecionada
				msg = String.valueOf(seleoption.getAttribute("text"));
			}
		}
		return msg;
	}

	// ***********************************************************************************************************************
	/**
	 * Obtiene el dato seleccionado de un elemento <select>.
	 *
	 * @param element El elemento WebElement correspondiente al <select>.
	 * @return El texto de la opci�n seleccionada, o un mensaje indicando que no hay
	 *         ninguna opci�n seleccionada.
	 */
	public String selectListObtenerDatoseleccionado(By locator) {
		return this.selectListObtenerDatoseleccionado(this.element(locator));
	}

	/**
	 * Obtiene el dato seleccionado de un elemento <select>.
	 *
	 * @param element El elemento WebElement correspondiente al <select>.
	 * @return El texto de la opci�n seleccionada, o un mensaje indicando que no hay
	 *         ninguna opci�n seleccionada.
	 */
	public String selectListObtenerDatoseleccionado(WebElement element) {
		// Crear un objeto Select con el elemento dado
		Select selectList = new Select(element);

		// Verificar si hay una opcion seleccionada
		try {
			WebElement selectedOption = selectList.getFirstSelectedOption();

			if (selectedOption != null) {
				String selectedValue = selectedOption.getText();
				return selectedValue;
			} else {
				return "No hay ninguna opción seleccionada.";
			}
		} catch (Exception e) {
			return "No hay ninguna opción seleccionada.";
		}
	}

	// ***********************************************************************************************************************
	/**
	 * Selecciona de la lista que se encuentra en el campo identificado por
	 * [locator] el [item]. La b�squeda del elemento NO es case sensitive NI tiene
	 * en cuenta acentos, y selecciona el elemento si est� contenido.
	 * 
	 * @param locator - Debe ser un [select]
	 * @param item    - Elemento a seleccionar
	 * @return Mensaje de error en caso que no haya encontrado el elemento. El
	 *         retorno es VACIO si se pudo seleccionar.
	 */
	public String selectListItem2(By locator, String item) {
		return selectListItem2(this.element(locator), item);
	}

	/**
	 * Selecciona de la lista que se encuentra en el campo identificado por
	 * [element] el [item]. La b�squeda del elemento NO es case sensitive NI tiene
	 * en cuenta acentos, y selecciona el elemento si est� contenido.
	 * 
	 * @param element - Debe ser un [select]
	 * @param item    - Elemento a seleccionar
	 * @return Mensaje de error en caso que no haya encontrado el elemento. El
	 *         retorno es VACIO si se pudo seleccionar.
	 */
	public String selectListItem2(WebElement element, String item) {

		Select selectList = new Select(element);
		String valActual = this.getText(selectList.getFirstSelectedOption());

		if (Util.equalsIgnoreCaseAndAccents(valActual, item))
			return ""; // YA EST� SELECCIONADO
		else if (Util.containsIgnoreCaseAndAccents(valActual, item)
				|| Util.containsIgnoreCaseAndAccents(item, valActual)) {
			return "Valor ya seleccionado [" + valActual + "] - no es igual a [" + item
					+ "] pero se deja por estar contenido.";

		}

		List<WebElement> options = selectList.getOptions();
		return this.getItemInOptions(options, item);
	}

	// ***********************************************************************************************************************
	/**
	 * M�todo usado por los m�todos [selectListItem]
	 */
	private String getItemInOptions(List<WebElement> options, String item) {

		String msgRetorno = "Elemento [" + item + "] NO presentado, tampoco hay un valor parecido."; // DEFAULT
		String itemBuscar = item.trim(); // Por si tiene espacios, se le quita.
		String itemInList = "";
		boolean itemEncontrado;
		for (WebElement option : options) {
			itemInList = this.getText(option).trim(); // Se le quitan los espacios por si tiene.

			itemEncontrado = Util.containsIgnoreCaseAndAccents(itemInList, itemBuscar)
					|| Util.containsIgnoreCaseAndAccents(itemBuscar, itemInList);

			if (itemEncontrado) {
//					this.click(option);
				option.click();
				msgRetorno = "";
				break;
			}
		}
		return msgRetorno;
	}

	// ***********************************************************************************************************************
	/**
	 * Selecciona de la lista predictiva que se encuentra en el campo identificado
	 * por [element] el primer elemento que coincida con el ingreso de [item]
	 * elemento [li].<br>
	 * Si ning�n elemento coincide se retorna un mensaje que lo indica, en caso
	 * contrario el retorno es VAC�O.
	 */
	public String selectPredictiveListItemLi(By locator, String item) {
		return this.selectPredictiveListItemLi(this.element(locator), item);
	}

	/**
	 * Selecciona de la lista predictiva que se encuentra en el campo identificado
	 * por [element] el primer elemento que coincida con el ingreso de [item]
	 * elemento [li].<br>
	 * Si ning�n elemento coincide se retorna un mensaje que lo indica, en caso
	 * contrario el retorno es VAC�O.
	 */
	public String selectPredictiveListItemLi(WebElement element, String item) {
		String text = null;
		try {
			// Limpia y enfoca el campo de texto
			element.click();
			// Ingresa el Dato en la lista preditiva para que muestre el la etiqueta //li
			this.write(element, item);

			// Espera para que se generen las coincidencias
			Util.wait(3);

			// Obtenemos el id del elemento para la busqueda del elemento li correspondiente
			String id = element.getAttribute("id");
			// este elemento es unico al elemento inicial y si es una lista predetiva
			List<WebElement> elemento = this.findElements(By.xpath("//*[@id='" + id + "']//following-sibling::ul//li"));
			// Obtiene la lista y
			for (WebElement elementos : elemento) {
				text = elementos.getText();
				if (Util.containsIgnoreCaseAndAccents(text, item)) {
					String[] pais = text.split("-");
					item = pais[1];
				}
			}

			// Busca los elementos de la lista predictiva
			List<WebElement> opciones = this.findElements(By.xpath("//li[contains(text(), '" + item + "')]"));

			if (opciones.isEmpty()) {
				return "Elemento [" + item + "] no encontrado en la lista.";
			}
			// Verifica si alguna opci�n coincide con el texto deseado (ignorando may�sculas
			// y acentos)
			for (WebElement opcion : opciones) {
				String textoLimpio = Util.removeAccents(opcion.getText()).toLowerCase();
				if (Util.containsIgnoreCaseAndAccents(textoLimpio, item)) {
					this.click(this.element(By.xpath("//li[contains(text(), '" + opcion.getText() + "')]")));// Wait
																												// para
																												// esperar
																												// que
																												// se
																												// active
																												// casilla
					return "";
				}
			}
			// Si no se encontr� una coincidencia exacta
			return "No se encontró una coincidencia exacta para [" + item + "].";
		} catch (Exception e) {
			return "Error al seleccionar el elemento: " + e.getMessage();
		}
	}

	public void focusInIframe(By iframeSelector, String elementSelector) {
		focusInIframe(this.element(iframeSelector), elementSelector);
	}

	public void focusInIframe(WebElement iframe2, String elementSelector) {

		try {
			// Switch to the iframe
			this.changeToDefaultFrame();
			WebElement iframe = iframe2;
			this.getDriver().switchTo().frame(iframe);
			// Find the element inside the iframe
			WebElement element = this.element(elementSelector);
			// Scroll the element into view
			this.getJse().executeScript("arguments[0].scrollIntoView(true);", element);
			// Focus the element
			this.getJse().executeScript("arguments[0].focus();", element);
			// Switch back to the default content
			this.getDriver().switchTo().defaultContent();
			// Scroll the iframe into view in the main document
			this.getJse().executeScript("arguments[0].scrollIntoView(true);", iframe);
			this.getDriver().switchTo().frame(iframe);
		} catch (NoSuchElementException | ElementNotInteractableException e) {
			System.out.println("Element not interactable or not found: " + e.getMessage());
			// Retry focusing
			focusInIframe(iframe2, elementSelector);
		} catch (WebDriverException e) {
			System.out.println("WebDriver exception: " + e.getMessage());
		}

	}

// =========================================================================================================================================

	/**
	 * Añade los Minutos ingresados a la hora con valor String
	 * 
	 * @param String hora
	 * @param int    minuto
	 * @return
	 */
	public static String horaAdd(String formatTime, String hora, int minuto) {
		// Formateador para parsear la cadena en LocalTime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatTime);
		// Parsear la cadena en LocalTime
		LocalTime tiempo = LocalTime.parse(hora, formatter);
		// Restar los minutos ingresados
		LocalTime tiempoRestado = tiempo.plusMinutes(minuto);
		// Formatear el resultado de nuevo en formato "HHmm"
		return tiempoRestado.format(formatter);
	}

// =========================================================================================================================================

	/**
	 * resta Un Minuto a la hora con valor String
	 * 
	 * @param hora
	 * @return retorna la hora
	 * @throws ParseException
	 */
	public static String subtractOneMinute(String hora) throws ParseException {
		SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm");
		Date date = inputFormat.parse(hora);
		long timeInMilliseconds = date.getTime();
		timeInMilliseconds -= 60000; // Subtracting one minute (in milliseconds)
		Date modifiedDate = new Date(timeInMilliseconds);
		return inputFormat.format(modifiedDate);
	}

	/**
	 * Convierte la Hora en formato militar si es Pm
	 * 
	 * @param hora en formato "hh:mm a"
	 * 
	 * @return retorna la hora en formato "HH:mm"
	 */
	public static String convertirHoraSiPM(String hora) {
		// Formato de entrada en formato de 12 horas con AM/PM
		SimpleDateFormat formatoEntrada = new SimpleDateFormat("hh:mm");
		// Formato de salida en formato de 24 horas
		SimpleDateFormat formatoSalida = new SimpleDateFormat("HH:mm");
		try {
			// Parsear la fecha de entrada
			Date fechaDate = formatoSalida.parse(hora);
			// Obtener la hora actual y verificar si es AM o PM
			Calendar calendarioActual = Calendar.getInstance();
			int horaActual = calendarioActual.get(Calendar.HOUR_OF_DAY);
			boolean esPM = horaActual >= 12;
			// Si es PM, convertir la hora
			if (esPM) {
				int horas = fechaDate.getHours();
				if (horas < 12) {
					horas += 12;
				} else {
					horas -= 12;
				}
				fechaDate.setHours(horas);
			}
			// Convertir la fecha a String en el nuevo formato
			return formatoSalida.format(fechaDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
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

	/**
	 * Metodo NumerosTeclado: Recibe numeros en Texto y los aregla en un arreglo
	 * donde los llama por teclado a digitar.
	 * 
	 * @throws AWTException
	 * 
	 */
	public static String StringNumerosTeclado(String numero) throws AWTException {
		String numCliented = numero;
		int can = 0;
		String cadena = "";
		cadena = String.valueOf(numCliented);
		can = cadena.length();
		int[] digitos = new int[can];
		Robot r = new Robot();
		for (int d = 0; d < digitos.length; d++) {
			digitos[d] = Integer.parseInt(cadena.substring(d, d + 1));
			switch (digitos[d]) {
			case 0:
				r.keyPress(KeyEvent.VK_0);
				r.keyRelease(KeyEvent.VK_0);
				break;
			case 1:
				r.keyPress(KeyEvent.VK_1);
				r.keyRelease(KeyEvent.VK_1);
				break;
			case 2:
				r.keyPress(KeyEvent.VK_2);
				r.keyRelease(KeyEvent.VK_2);
				break;
			case 3:
				r.keyPress(KeyEvent.VK_3);
				r.keyRelease(KeyEvent.VK_3);
				break;
			case 4:
				r.keyPress(KeyEvent.VK_4);
				r.keyRelease(KeyEvent.VK_4);
				break;
			case 5:
				r.keyPress(KeyEvent.VK_5);
				r.keyRelease(KeyEvent.VK_5);
				break;
			case 6:
				r.keyPress(KeyEvent.VK_6);
				r.keyRelease(KeyEvent.VK_6);
				break;
			case 7:
				r.keyPress(KeyEvent.VK_7);
				r.keyRelease(KeyEvent.VK_7);
				break;
			case 8:
				r.keyPress(KeyEvent.VK_8);
				r.keyRelease(KeyEvent.VK_8);
				break;
			case 9:
				r.keyPress(KeyEvent.VK_9);
				r.keyRelease(KeyEvent.VK_9);
				break;

			}
		}
		return numero;
	}

	/**
	 * Podemos cargar un archivo por medio de las funciones de la clase [Robot]
	 * Carga un archivo dividiendo la ruta en carpeta y archivo.
	 * 
	 * @param rutaCompleta Ruta completa del archivo (ej.
	 *                     "C:\\Users\\jprodtap\\Documents\\Requerimiento software y
	 *                     hardware.pdf").
	 * @throws Exception
	 */
	public static void cargueArchivo(String rutaCompleta) throws Exception {
		Robot accion = new Robot();

		// Dividir la ruta completa en carpeta y archivo
		int lastSeparatorIndex = rutaCompleta.lastIndexOf("\\");
		String carpeta = rutaCompleta.substring(0, lastSeparatorIndex);
		String archivo = rutaCompleta.substring(lastSeparatorIndex + 1);

		// Copiar la carpeta al portapapeles
		StringSelection stringSelectionCarpeta = new StringSelection(carpeta);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelectionCarpeta, null);
		Util.wait(5);
		try {
			// Navegar a la carpeta
			accion.keyPress(KeyEvent.VK_CONTROL);
			accion.keyPress(KeyEvent.VK_V);
			accion.keyRelease(KeyEvent.VK_CONTROL);
			accion.keyRelease(KeyEvent.VK_V);
			Util.wait(1);

			accion.keyPress(KeyEvent.VK_ENTER);
			accion.keyRelease(KeyEvent.VK_ENTER);
			Util.wait(5);

			// Copiar el archivo al portapapeles
			StringSelection stringSelectionArchivo = new StringSelection(rutaCompleta);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelectionArchivo, null);
			Util.wait(1);

			// Pegar el archivo
			accion.keyPress(KeyEvent.VK_CONTROL);
			accion.keyPress(KeyEvent.VK_V);
			accion.keyRelease(KeyEvent.VK_CONTROL);
			accion.keyRelease(KeyEvent.VK_V);
			Util.wait(1);

			accion.keyPress(KeyEvent.VK_ENTER);
			accion.keyRelease(KeyEvent.VK_ENTER);
			Util.wait(3);
			// Pegar el archivo
			accion.keyPress(KeyEvent.VK_CONTROL);
			accion.keyPress(KeyEvent.VK_V);
			accion.keyRelease(KeyEvent.VK_CONTROL);
			accion.keyRelease(KeyEvent.VK_V);
			Util.wait(1);
			accion.keyPress(KeyEvent.VK_ENTER);
			accion.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
