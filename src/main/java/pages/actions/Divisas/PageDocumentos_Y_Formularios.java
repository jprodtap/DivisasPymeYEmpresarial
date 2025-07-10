package pages.actions.Divisas;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import library.reporting.Reporter;
import library.settings.SettingsRun;
import library.common.Util;
import library.core.BasePageWeb;
import library.reporting.Evidence;


public class PageDocumentos_Y_Formularios extends PageDivisas {

	public PageDocumentos_Y_Formularios(BasePageWeb parentPage) {
		super(parentPage);
	}

// =======================================================================================================================

	// Busca el registro mediante la hora y el tipo de moneda
	String xpathNumDocumTxCon = "//td[contains(text(), 'fechayhoraconvert')]/following-sibling::td[contains(text(), 'MONEDA')]/following-sibling::td[contains(text(), 'DOCID')]/preceding-sibling::td/a";
	String xpathNumDocumTxObt = "//td[contains(text(), 'fechayhoraconvert')]/following-sibling::td[contains(text(), 'MONEDA')]/following-sibling::td[contains(text(), '')][3]";

	By TituloModulo = By.xpath("//button[contains(text(), 'Buscar')]");

	By campTitulo = By.xpath("//form[@id='declaracionForm']/div/h2");

	// Formulario
	By listConceptosCambiario = By.id("ConceptosCambiario");

	By cmpInfoNumdelformulario = By.id("NumeroFormulario");

	String tabInfoDatos = "//*[@id='table' and @style='display: none;' ]/table";
	String tabInfoDatos2 = "//*[@id='TABLExpath' and @style='display: none;' ]/table";
	String tabInfoDatosTx = "//*[@id='TABLENUM']/table";

	// -------------------------------[Tipo de
	// operaci�n]--------------------------------------------------

	// -------------------------------[Endeudamiento externo y avales y garant�as
	// Formulario 3]--------------------------------------------------
	By campInputNumerodelprestamooAval = By.id("descripcionOperacion_NumeroPrestamo");

	// Nombre del deudor o acreedor Avalado o beneficiario residente nombre del
	// deudor o la empresa ejemplo [EMP PYME 25]
	By campInputdescripcionOperacion_NombreDeudorAcredor = By.id("descripcionOperacion_NombreDeudorAcredor");
	By campSeletListaTipoIdentificacion = By.id("descripcionOperacion_TipoIdentificacion");
	By campInputNumeroIdentificacion = By.id("descripcionOperacion_NumeroIdentificacion");// ejemplo el numero de
																							// nit[EMP PYME 25]
	By campInputDigitoVerificacion = By.id("descripcionOperacion_DigitoVerificacion");
	By campSeletListaMonedaEstipulada = By.id("descripcionOperacion_MonedaEstipulada");
	By campInputValorMoneda = By.id("descripcionOperacion_ValorMoneda");
	By campInputTasadeCambioMonedaEstipulada = By.id("descripcionOperacion_TRM");

	// Nombre del acreedor (Cr�ditos pasivos) o el deudor (Cr�ditos activos) o
	// avalista
	By campInputNombreAcredorDeudorAvalista = By.id("descripcionOperacion_NombreAcredorDeudorAvalista");

//-------------------------------[Inversiones Internacionales]--------------------------------------------------

	By listDestinodelainversion = By.id("tipoInversionId");

	By listSeleOpcionInversionId = By.id("opcionInversionId");

	// ----------------[EMPRESA Receptora o Administrador
	// (Portafolio)]--------------------------------
	By listSeleOpcionempresaReceptoraTipoIdentificacion = By.id("empresaReceptora_TipoIdentificacion");
	By imputempresaReceptoraNumeroIdentificacion = By.id("empresaReceptora_NumeroIdentificacion");
	By imputempresaReceptoraDigitoVerificacion = By.id("empresaReceptora_DigitoVerificacion");
	By imputempresaReceptoraNombreRazonSocial = By.id("empresaReceptora_NombreRazonSocial");
	By empresaReceptoraCodigoDepartamento_NHidden = By.id("empresaReceptora_CodigoDepartamento_NHidden");
	By empresaReceptoraCodigoPais_NHidden = By.id("empresaReceptora_CodigoPais_NHidden");
	By empresaReceptoraCodigoCiudad = By.id("empresaReceptora_CodigoCiudad");
	By empresaReceptoraCodigoCIUU = By.id("empresaReceptora_CodigoCIUU");
	By empresaReceptoraTelefono = By.id("empresaReceptora_Telefono");
	By empresaReceptoraDireccion = By.id("empresaReceptora_Direccion");
	By empresaReceptoraCorreo = By.id("empresaReceptora_Correo");
	By empresaReceptoraSector = By.id("empresaReceptora_Sector");
	By empresaReceptoraTipoEmpresa = By.id("empresaReceptora_TipoEmpresa");
	By empresaReceptoraSuperintendencia = By.id("empresaReceptora_Superintendencia");
	By selectempresaReceptora_Naturaleza = By.id("empresaReceptora_Naturaleza");

	// ----------------[Identificaci�n del Inversionista (Nacional o
	// extranjero)]--------------------------------

	By listSeleOpcioninversionistaTipoIdentificacion = By.id("inversionista_TipoIdentificacion");

	By imputinversionistaNumeroIdentificacion = By.id("inversionista_NumeroIdentificacion");

	By imputinversionista_DigitoVerificacion = By.id("inversionista_DigitoVerificacion");

	By imputinversionista_NombreRazonSocial = By.id("inversionista_NombreRazonSocial");
	By imputinversionista_CodigoDepartamento_NHidden = By.xpath("//*[@id='inversionista_CodigoDepartamento_NHidden']");
	By selectinversionista_CodigoCiudad = By.id("inversionista_CodigoCiudad");
	By selectinversionista_CodigoPais = By.id("inversionista_CodigoPais");
	By imputinversionista_CodigoCIUU_NHidden = By.id("inversionista_CodigoCIUU_NHidden");
	By selectinversionista_Naturaleza = By.id("inversionista_Naturaleza");
	By imputinversionista_Telefono = By.id("inversionista_Telefono");
	By imputinversionista_Correo = By.id("inversionista_Correo");
	By imputinversionista_Direccion = By.xpath("//*[@id='inversionista_Direccion']");
	By selectinversionista_Sector = By.id("inversionista_Sector");
	By selectinversionista_TipoEmpresa = By.id("inversionista_TipoEmpresa");
	By selectinversionista_Superintendencia = By.id("inversionista_Superintendencia");

//---------------------------------------------------------------------------------

	By listNumeralOperacion_Numeral1 = By.id("numeralOperacion_Numeral");
	By ListNumeralOperacion_Numeral0id = By.id("numerales_0__Numeral");

	By ListNumeralOperacion_Numeral1id = By.id("numerales_1__Numeral");
	String ListNumeralOperacion_Numeral2id = "numerales_NUM__Numeral";

	By inputValorMonedaGiro = By.id("numerales_0__ValorMonedaGiro");
	By inputValorMonedaGiro1 = By.id("numerales_1__ValorMonedaGiro");
	String inputValorMonedaGiro2 = "numerales_NUM__ValorMonedaGiro";

	By inputValorMonedaNegociacion = By.id("numerales_0__ValorMonedaNegociacion");
	By inputValorMonedaNegociacion1 = By.id("numerales_1__ValorMonedaNegociacion");
	String inputValorMonedaNegociacion2 = "numerales_NUM__ValorMonedaNegociacion";

	By inputValorMonedaEstipulada = By.id("numerales_0__ValorMonedaEstipulada");
	By inputValorMonedaEstipulada1 = By.id("numerales_1__ValorMonedaEstipulada");
	String inputValorMonedaEstipulada2 = "numerales_NUM__ValorMonedaEstipulada";

	By inputValorenUSD = By.id("numerales_0__ValorUSD");
	By inputValorenUSD1 = By.id("numerales_1__ValorUSD");
	String inputValorenUSD2 = "numerales_NUM__ValorUSD";

	By inputOperacion_NumeroDeposito = By
			.xpath("//*[@id='numerales_0__NumeroDeposito' or @id='numeralOperacion_NumeroDeposito']");
	By inputOperacion_NumeroDeposito1 = By.xpath("//*[@id='numerales_1__NumeroDeposito']");
	String inputOperacion_NumeroDeposito2 = "numerales_NUM__NumeroDeposito";

	// ----------------------[Inversion Internacionales]-----------------------

	By inputNumeroDeclaracion = By.id("numerales_0__NumeroDeclaracion");
	By inputNumeroDeclaracion1 = By.id("numerales_1__NumeroDeclaracion");
	String inputNumeroDeclaracion2 = "numerales_NUM__NumeroDeclaracion";

	// ----------------------[Inversion Internacionales]-----------------------

	By inputnumeralOperacion_Participaciones = By.xpath("//input[@id='numeralOperacion_Participaciones']");
	By seleListnumeralOperacion_MotivosSinParticipacion = By
			.xpath("//*[@id='numeralOperacion_MotivosSinParticipacion']");

	// ----------------------[Exportacion de bienes]-----------------------

	By labelDeduciones = By.xpath("//*[@id='numeralExtendidoContainer']/div/div[3]/div[1]/div/label");
	By inputnumeralExtendidoDeducciones = By.xpath("//*[@id='numeralExtendido_Deducciones']");

	By btnSeleccioneunarchivo = By.id("uploadBtn");

	By btnEnviar = By.id("enviarForm");

	String tipoOperacionXpath = "//label[text()='OPERACION']//parent::div//span";

// ----------------[Informaci�n documentos de importaci�n - DIAN]-------------------------------------------

	String inputlisDeclaraciondeimportacionNo = "documentosImportacion_NUM__DeclaracionImportacionNro";
	String inputValorUSD = "documentosImportacion_NUM__ValorUSD";

	String predicDATO = "//li[contains(text(), 'DATOBUSCAR')]";

// ----------------[Cargue de archivo]---------------------------------------------------------------------

	String archivoCargado = "//*[@id='document-form-card-container']/documents-form-card//*[contains(text(), 'NOMARCH')]";

	// PSEUDOELEMENTO
	String strPseudoElemento = "return window.getComputedStyle(arguments[0],'::after').getPropertyValue('background-color')";
// =======================================================================================================================

	By CerrSesion = By.xpath("//*[@id='CerrarSesion']");
	By sesionEx = By.xpath("//b[contains(text(), 'Sesi�n no existe o ha expirado por inactividad.')]");

// =======================================================================================================================

	private static final String Importaciones = "Importaciones de bienes", Exportaciones = "Exportaciones de bienes",
			Endeudamiento = "Endeudamiento externo y avales y garant�as", Inversiones = "Inversiones internacionales",
			ServTryOtros = "Servicios, transferencias y otros conceptos";

	private static final String Inicial = "Inicial", Devolucion = "Devoluci�n";

	int contador = 0;

// ========================================[EmpresaReceptora]===============================================================================

	// Datos de la Empresa Recetora

	public String tipodeidentificacionReceptora;
	public String numerodeidentificacionReceptora;
	public String digitodeverificacionReceptora;
	public String nombreorazonsocialReceptora;
	public String codigopaisReceptora;
	public String codigodepartamentoReptora;
	public String codigociudadReptora;
	public String codigoCIIUReptora;
	public String telefonoReptora;
	public String direccionReptora;
	public String correoReptora;
	public String sectorReptora;
	public String tipodeempresaReptora;
	public String superintendenciaReptora;
	public String actividadReptora;
	public String tipoderegimenReptora;
	public String naturalezaReptora;

	/**
	 * Datos Formularios del EmpresaReceptora a agregar o Actualizar
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param digitoVerificacion
	 * @param nombreRazonSocial
	 * @param codigoPais
	 * @param codigoDepartamento
	 * @param codigoCiudad
	 * @param codigoCIIU
	 * @param correoElectronico
	 * @param sector
	 * @param tipoEmpresa
	 * @param superintendencia
	 * @param naturaleza
	 * @param telefono
	 * @param direccion
	 */
	public void EmpresaReceptora(String tipoIdentificacion, String numeroIdentificacion, String digitoVerificacion,
			String nombreRazonSocial, String codigoPais, String codigoDepartamento, String codigoCiudad,
			String codigoCIIU, String telefono, String direccion, String correo, String sector, String tipoEmpresa,
			String superintendencia, String actividad, String tipoRegimen, String naturaleza) {

		this.tipodeidentificacionReceptora = tipoIdentificacion;
		this.numerodeidentificacionReceptora = numeroIdentificacion;
		this.digitodeverificacionReceptora = digitoVerificacion;
		this.nombreorazonsocialReceptora = nombreRazonSocial;
		this.codigopaisReceptora = codigoPais;
		this.codigodepartamentoReptora = codigoDepartamento;
		this.codigociudadReptora = codigoCiudad;
		this.codigoCIIUReptora = codigoCIIU;
		this.telefonoReptora = telefono;
		this.direccionReptora = direccion;
		this.correoReptora = correo;
		this.sectorReptora = sector;
		this.tipodeempresaReptora = tipoEmpresa;
		this.superintendenciaReptora = superintendencia;
		this.actividadReptora = actividad;
		this.tipoderegimenReptora = tipoRegimen;
		this.naturalezaReptora = naturaleza;
	}
// ========================================[Inversionista]===============================================================================

	public String identificacionInversionista;
	public String numerodeidentificacionInversionista;
	public String digitodeverificacionInversionista;
	public String nombreorazonsocialInversionista;

	public String codigoPaisInversionista;
	public String codigoDepartamentoInversionista;
	public String codigociudadInversionista;
	public String codigoCIIUInversionista;

	public String correoElectronicoInversionista;
	public String sectorInversionista;
	public String tipodeempresaInversionista;
	public String superintendenciaInversionista;
	public String naturalezaInversionista;

	public String telefonoInversionista;
	public String direccionInversionista;

	/**
	 * Datos Formularios del Inversionista a agregar o Actualizar
	 * 
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @param digitoVerificacion
	 * @param nombreRazonSocial
	 * @param codigoPais
	 * @param codigoDepartamento
	 * @param codigoCiudad
	 * @param codigoCIIU
	 * @param correoElectronico
	 * @param sector
	 * @param tipoEmpresa
	 * @param superintendencia
	 * @param naturaleza
	 * @param telefono
	 * @param direccion
	 */
	public void Inversionista(String tipoIdentificacion, String numeroIdentificacion, String digitoVerificacion,
			String nombreRazonSocial, String codigoPais, String codigoDepartamento, String codigoCiudad,
			String codigoCIIU, String correoElectronico, String sector, String tipoEmpresa, String superintendencia,
			String naturaleza, String telefono, String direccion) {

		this.identificacionInversionista = tipoIdentificacion;
		this.numerodeidentificacionInversionista = numeroIdentificacion;
		this.digitodeverificacionInversionista = digitoVerificacion;
		this.nombreorazonsocialInversionista = nombreRazonSocial;
		this.codigoPaisInversionista = codigoPais;
		this.codigoDepartamentoInversionista = codigoDepartamento;

		this.codigociudadInversionista = codigoCiudad;
		this.codigoCIIUInversionista = codigoCIIU;

		this.correoElectronicoInversionista = correoElectronico;
		this.sectorInversionista = sector;
		this.tipodeempresaInversionista = tipoEmpresa;
		this.superintendenciaInversionista = superintendencia;
		this.naturalezaInversionista = naturaleza;
		this.telefonoInversionista = telefono;
		this.direccionInversionista = direccion;
	}

// ============================================[ModuloDocumetosYFormularios]===========================================================================

	/**
	 * Metodo Verifica si esta en el modulo Documentos y Formularios si no ingresa
	 * al modulo
	 * 
	 * @return
	 * @throws Exception
	 */

	public String ModuloDocumetosYFormularios(String tipoPrueba, String servicio, String fechaTx, String horaTx,
			String moneda) throws Exception {
		String msg = null;

		msg = validarAlertDocuc();

		if (isValid(msg) && !msg.contains("En este m�dulo puede visualizar las operaciones")
				&& !msg.contains("Los campos que no se presentan en la declaraci�n de cambio ser�n autocompletados"))
			return msg;

//		String tipoPrueba = SettingsRun.getTestData().getParameter("Tipo prueba").trim();

		if (tipoPrueba.equals("Tx Pend Aprobaci�n")) {
			IralModuloDocumetosYFormularios(tipoPrueba, servicio, fechaTx, horaTx, moneda);
		}

		contador = 0;
		WebElement Titulo = null;
		do {
			Titulo = this.element(campTitulo);
			Util.wait(1);
			if (contador >= 30) {
				this.getDriver().switchTo().defaultContent();
				Evidence.save("No se encuentra el Titulo Documentos y formularios",this);
				this.click(CerrSesion);
				Reporter.reportEvent(Reporter.MIC_FAIL, "No se encuentra el Titulo: Documentos y formularios");
				return "No se encuentra el Titulo: Documentos y formularios";
			}
			contador++;

		} while (Titulo == null || !Titulo.isDisplayed());

		return msg;

	}

// =============================================[IralModuloDocumetosYFormularios]==========================================================================

	/**
	 * Metodo ingresa al modulo de Documentos y Formularios y realiza la consulta de
	 * la Tx realizada en el portal
	 * 
	 * @return
	 * @throws Exception
	 */
	public String IralModuloDocumetosYFormularios(String tipoPrueba, String servicio, String fechaTx, String horaTx,
			String moneda) throws Exception {

		WebElement moduloDoc = null;
		String msg = null;

		this.switchToFrameDivisas();

		msg = this.seleccionarTransferencia("Documentos y Formularios");// Se en carga de selecionar el modulo de
																		// Divisas

		if (isValid(msg))
			Reporter.reportEvent(Reporter.MIC_FAIL, "No se encuentra el modulo: Documentos y Formularios");

		contador = 0;
		do {
			contador++;
			Util.wait(3);
			if (this.switchToFrameDivisas())
				moduloDoc = this.element(TituloModulo);
			if (contador >= 30) {
				this.getDriver().switchTo().defaultContent();
				Evidence.save("No se encuentra el modulo: Documentos y Formularios",this);
				this.click(CerrSesion);
				Reporter.reportEvent(Reporter.MIC_FAIL, "No se encuentra el modulo: Documentos y Formularios");
				SettingsRun.exitTestIteration();
			}

		} while (moduloDoc == null || !moduloDoc.isDisplayed());

		msg = obtenerNumeroTxDocumentoGeneral(tipoPrueba, servicio, "Documentos y Formularios", fechaTx, horaTx,
				moneda);

		if (isValid(msg) && msg.equals("Error: Documento no encontrado con tiempos ajustados."))
			return msg;

		Evidence.save("Modulo - Documentos Y Formularios TX",this);

		Util.wait(3);

		msg = validarAlertDocuc();

		if (isValid(msg))
			return msg;

		return null;
	}

// ============================================[DatosDocumetosYFormularios]===========================================================================

	/**
	 * Metodo Realiza el flujo completo y llenado de los datos de Documetos Y
	 * Formularios
	 * 
	 * @param conceptosCambiario
	 * @param tipoOperacion
	 * @param tipoDestinodelainversion
	 * @param opcionInversionId
	 * @param valorTx
	 * @param numeral1
	 * @param valorNumeral1
	 * @param numCambiario2
	 * @param valorNumeral2
	 * @param numeroDeposito
	 * @param numeroFacturaodeclaraci�n
	 * @param valordeduciones
	 * @param cargarArchivo
	 * @return
	 * @throws Exception
	 */
	public String DatosDocumetosYFormularios(String conceptosCambiario, String tipoOperacion,
			String tipoDestinodelainversion, String opcionInversionId, String valorTx, String numeral1,
			String valorNumeral1, String numCambiario2, String valorNumeral2, String valordeduciones,
			String cambiarConcepto, String conceptoAcambiar, String numeroDeposito,
			String numeroFacturaoReferDeclaracion, String cambiarlistnumeralOperacion_Numeral1, String numeral1Acambiar,
			String cambiarDatosDescripciondelaoperacion, String numerodelprestamooaval,
			String nombredelacreedoroeldeudoroavalista, String nombredeldeudoroacreedorAvaladoobeneficiarioresidente,
			String tipodeidentificaci�ndeldeudor, String numerodeidentificaciondeldeudor, String digitodeverificacion,
			String monedaestipulada, String valormonedaestipulada, String tasadecambiomoneda,
			String cambiarValornumeralcambiario1, String ValorNumeral1Camb, String validacionAdicionar,
			String validacionDian, String... cargarArchivo) throws Exception {

		WebElement listConceptodelatransferencia = null;

		String msg = null;

		contador = 0;

		msg = validarAlertDocuc();

		if (isValid(msg))
			return msg;

		// --------------------Concepto de la transferencia: ------------------- //
		do {
			Util.wait(2);
			listConceptodelatransferencia = this.element(listConceptosCambiario);
			contador++;
			if (contador >= 30) {
				msg = "No se presento el campo Concepto de la transferencia";
				if (this.element(sesionEx) != null) {
					msg = this.element(sesionEx).getText();
					if (isValid(msg)) {
						Reporter.reportEvent(Reporter.MIC_FAIL, msg);
					}
					Evidence.save("No se presento el campo Concepto de la transferencia",this);
					if (this.element(btnEnviar) != null) {
						this.click(btnEnviar);
					}
					if (isValid(msg))
						return msg;
				}

				Evidence.save("No se presento el campo Concepto de la transferencia",this);
				this.click(btnEnviar);
				return "No se presento el campo Concepto de la transferencia";
			}

		} while (listConceptodelatransferencia == null && !this.isDisplayed(listConceptodelatransferencia)&& !this.isSelected(listConceptodelatransferencia) && !this.isEnabled(listConceptodelatransferencia));

		Evidence.save("Campos-Documentos",this);

		if (isValid(conceptosCambiario)) {
			Util.wait(5);
			msg = this.selectListItem(listConceptosCambiario, conceptosCambiario);
			Evidence.save("Campo-Seleccione Concepto de la transferencia",this);
			if (isValid(msg) && !msg.contains("Valor no encontrado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}

//			String cambiarConcepto = SettingsRun.getTestData().getParameter("Cambiar Concepto de la transferencia");

			if (cambiarConcepto.equals("SI")) {
//				String conceptoAcambiar = SettingsRun.getTestData().getParameter("Concepto de la transferencia A Cambiar");
				conceptosCambiario = conceptoAcambiar;

				msg = this.selectListItem(listConceptosCambiario, conceptoAcambiar);
				Evidence.save("Campo-Seleccione Concepto de la transferencia",this);

				if (isValid(msg) && !msg.contains("Valor no encontrado")) {
					Reporter.reportEvent(Reporter.MIC_FAIL, msg);
					this.click(btnEnviar);
					msg = this.closeActiveIntAlertConfirma();
					if (isValid(msg)) {
						return msg;
					}
				}

			}

		} else {
			// el campo esta vacio da clic al boton siguiente
			Evidence.save("Campo-Seleccione concepto de la transferencia",this);
			this.click(btnEnviar);
			msg = this.closeActiveIntAlertConfirma();
			if (isValid(msg))
				return msg;
		}

		// --------------------N�mero del formulario: -------------------

		String numFormulario = this.element(cmpInfoNumdelformulario).getText();
		Reporter.reportEvent(Reporter.MIC_INFO, numFormulario);

		// -------------------- Tabla informaci�n transferencia -------------------

		this.ValidacionTablasInformativa();

		// --------------------[Tipo de operaci�n] -------------------
		if (isValid(conceptosCambiario)) {

			ValidacionTipodeoperacion(conceptosCambiario, tipoOperacion);
		}

		// --------------------[Detalles de destino de la inversi�n] -------------------

		if (conceptosCambiario.contains(Inversiones) && isValid(tipoDestinodelainversion)) {

			// --------------------Destino de la inversi�n: -------------------

			WebElement listTipodelainversion = null;
			contador = 0;
			do {
				Util.wait(5);
				listTipodelainversion = this.element(listDestinodelainversion);
				contador++;
				if (contador > 30) {
					msg = "No se presento el campo Destino de la inversi�n";
					if (this.element(sesionEx) != null) {
						msg = this.element(sesionEx).getText();
						if (isValid(msg)) {
							Reporter.reportEvent(Reporter.MIC_FAIL, msg);
						}

						Evidence.save("No se presento el campo Destino de la inversi�n",this);

						if (this.element(btnEnviar) != null) {
							this.click(btnEnviar);
						}

						if (isValid(msg))
							return msg;
					}

					Evidence.save("No se presento el campo Destino de la inversi�n",this);
					this.click(btnEnviar);
					return "No se presento el campo Destino de la inversi�n";
				}

			} while (listTipodelainversion == null && this.isEnabled(listTipodelainversion) == false
					&& this.isDisplayed(listTipodelainversion) == false);

			if (isValid(conceptosCambiario)) {
				msg = this.seleOption(listDestinodelainversion, tipoDestinodelainversion);
				Evidence.save("Campo-Seleccione Destino de la inversi�n",this);

				if (msg.isEmpty()) {
					Reporter.reportEvent(Reporter.MIC_FAIL,"Elemento [" + tipoDestinodelainversion + "] NO presentado");
					this.click(btnEnviar);
//					Util.wait(5);
					msg = this.closeActiveIntAlertConfirma();
					if (isValid(msg)) {
						return msg;
					}
				}

			} else {
				// el campo esta vacio da clic al boton siguiente
				Evidence.save("Campo-Seleccione Destino de la inversi�n",this);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg))
					return msg;
			}

			// --------------------Opci�n de inversi�n: -------------------

			WebElement listOpcionInversionId = null;

			contador = 0;

			do {

				Util.wait(3);

				listOpcionInversionId = this.element(listSeleOpcionInversionId);

				contador++;

				if (contador >= 30) {

					msg = "No se presento el campo Opci�n de inversi�n";

					if (this.element(sesionEx) != null) {
						msg = this.element(sesionEx).getText();

						if (isValid(msg)) {
							Reporter.reportEvent(Reporter.MIC_FAIL, msg);
						}

						Evidence.save("No se presento el campo Opci�n de inversi�n",this);

						if (this.element(btnEnviar) != null) {
							this.click(btnEnviar);
						}

						if (isValid(msg))
							return msg;
					}

					Evidence.save("No se presento el campo Opci�n de inversi�n",this);
					this.click(btnEnviar);
					return "No se presento el campo Opci�n de inversi�n";
				}

			} while (listOpcionInversionId == null || this.isEnabled(listOpcionInversionId) == false
					&& this.isDisplayed(listOpcionInversionId) == false);

			if (isValid(conceptosCambiario)) {

				msg = this.seleOptionGetAttribu(listSeleOpcionInversionId, opcionInversionId);
				Evidence.save("Campo-Seleccione Opci�n de inversi�n",this);

				if (!isValid(msg)) {
					Reporter.reportEvent(Reporter.MIC_FAIL, msg);
					this.click(btnEnviar);
					msg = this.closeActiveIntAlertConfirma();
					if (isValid(msg)) {
						return msg;
					}
				}

			} else {
				// el campo esta vacio da clic al boton siguiente
				Evidence.save("Campo-Seleccione Opci�n de inversi�n",this);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg))
					return msg;
			}

		}

		// --------------------Descripci�n de la operaci�n: -------------------

		String elementoSelecionado = ValidacionListaNumeral1(conceptosCambiario);

		if (elementoSelecionado.equals("No se presento el campo Numeral")
				|| elementoSelecionado.contains("Error mensaje:")) {
			return elementoSelecionado;
		}


		if (isValid(conceptosCambiario)) {

			Evidence.save("Descripci�n de la operaci�n",this);


			if (cambiarConcepto.equals("SI")) {
				conceptosCambiario = conceptoAcambiar;
			}

			if (conceptosCambiario.contains(Inversiones)) {

//--------------------------------[Identificaci�n del inversionista (Nacional o extranjero)]-------------------------------------------------------------------

				Util.wait(5);
				boolean Portafolio = false;
				boolean Inversionista = false;

				switch (opcionInversionId) {
				case "Empresas":
				case "Empresas (EMPA)":
				case "Capital asignado sucursal r�gimen especial  (CSRE)":
				case "Capital asignado sucursal r�gimen general (CSRG)":
				case "Capital asignado sucursales Bancos y Compa�ias de seguros (CSBC)":
				case "Inversi�n suplementaria al capital asignado sucursales de sociedades extranjeras (ISDC)":
				case "Bonos obligatoriamente convertibles en acciones (BOCE)":
				case "Patrimonios Aut�nomos (PATO)":
				case "Encargos fiduciarios (EFDC)":
				case "Actos o contratos sin participaci�n en el capital (ACON)":
				case "Fondos de capital privado (FCPR)":
					Portafolio = true;
					Inversionista = true;
					break;
				case "Establecimientos de comercio (EDCO)":
				case "Inmuebles (INMU)":
				case "Activos Intangibles (AINT)":
				case "Inversi�n de portafolio":
				case "Inversi�n financiera y en activos en el exterior":
					Inversionista = true;
					break;

				default:
					break;
				}

				// ----------------[EMPRESA Receptora o
				// Administrador(Portafolio)]--------------------------------

				if (Portafolio) {


					if (!isValid(tipodeidentificacionReceptora)) {
						tipodeidentificacionReceptora = "NIT";
					}

					if (!isValid(numerodeidentificacionReceptora)) {
						numerodeidentificacionReceptora = "1021374642";
					}

					if (!isValid(digitodeverificacionReceptora)) {
						digitodeverificacionReceptora = "1";
					}

					if (!isValid(nombreorazonsocialReceptora)) {
						nombreorazonsocialReceptora = "Davivienda";
					}

					if (!isValid(codigopaisReceptora)) {
						codigopaisReceptora = "CO";
					}

					if (!isValid(codigodepartamentoReptora)) {
						codigodepartamentoReptora = "BOGOTA";
					}
					if (!isValid(codigociudadReptora)) {
						codigociudadReptora = "BOGOTA";
					}

					if (!isValid(codigoCIIUReptora)) {
						codigoCIIUReptora = "115";
					}

					if (!isValid(telefonoReptora)) {
						telefonoReptora = "5121457";
					}

					if (!isValid(direccionReptora)) {
						direccionReptora = "CALLE 190 # 75";
					}

					if (!isValid(correoReptora)) {
						correoReptora = "davivienda@gmail.com";
					}

					if (!isValid(sectorReptora)) {
						sectorReptora = "P�blico";
					}

					if (!isValid(tipodeempresaReptora)) {
						tipodeempresaReptora = "1. Sociedades / Empresas unipersonales";
					}

					if (!isValid(superintendenciaReptora)) {
						superintendenciaReptora = "2. Superintendencia Financiera de Colombia";
					}

					if (!isValid(actividadReptora)) {
						actividadReptora = "Exploraci�n y explotaci�n de hidrocarburos o miner�a";
					}

					if (!isValid(tipoderegimenReptora)) {
						tipoderegimenReptora = "Especial";
					}

					if (!isValid(naturalezaReptora)) {
						naturalezaReptora = "Persona natural";
					}

					this.EmpresaReceptoraoAdministrador(this.getTipoCuenta(tipodeidentificacionReceptora),
							numerodeidentificacionReceptora, digitodeverificacionReceptora, nombreorazonsocialReceptora,
							codigopaisReceptora, codigodepartamentoReptora, codigociudadReptora, codigoCIIUReptora,
							telefonoReptora, direccionReptora, correoReptora, this.getSector(sectorReptora),
							this.getTipoEmpresa(tipodeempresaReptora), naturalezaReptora,
							this.getSuperintendencia(superintendenciaReptora));
				}

//-------------------------------------------------------------------------------------------------------------------------

				// ----------------[Identificaci�n del Inversionista (Nacional o
				// extranjero)]--------------------------------

				if (Inversionista) {

					if (!isValid(identificacionInversionista)) {
						identificacionInversionista = "NIT";
					}
					if (!isValid(numerodeidentificacionInversionista)) {
						numerodeidentificacionInversionista = "1084525645";
					}

					if (!isValid(digitodeverificacionInversionista)) {
						digitodeverificacionInversionista = "1";
					}

					if (!isValid(nombreorazonsocialInversionista)) {
						nombreorazonsocialInversionista = "AUTOMATIZACION";
					}

					if (!isValid(codigoPaisInversionista)) {
						codigoPaisInversionista = "CO";
					}

					if (!isValid(codigoDepartamentoInversionista)) {
						codigoDepartamentoInversionista = "BOGOTA";
					}

					if (!isValid(codigociudadInversionista)) {
						codigociudadInversionista = "BOGOTA";
					}

					if (!isValid(codigoCIIUInversionista)) {
						codigoCIIUInversionista = "115";
					}

					if (!isValid(correoElectronicoInversionista)) {
						correoElectronicoInversionista = "davivienda@gmail.com";
					}

					if (!isValid(sectorInversionista)) {
						sectorInversionista = "P�blico";
					}

					if (!isValid(tipodeempresaInversionista)) {
						tipodeempresaInversionista = "1. Sociedades / Empresas unipersonales";
					}

					if (!isValid(superintendenciaInversionista)) {
						superintendenciaInversionista = "3. Superintendencia de Puertos y Transporte";
					}

					if (!isValid(naturalezaInversionista)) {
						naturalezaInversionista = "Persona natural";
					}

					if (!isValid(telefonoInversionista)) {
						telefonoInversionista = "5121457"; // o el valor que necesites
					}

					if (!isValid(direccionInversionista)) {
						direccionInversionista = "CALLE 190 # 75"; // o el valor que necesites
					}

					this.Identificaci�ndelInversionista(this.getTipoCuenta(identificacionInversionista),
							numerodeidentificacionInversionista, digitodeverificacionInversionista,
							nombreorazonsocialInversionista, codigoPaisInversionista, codigoDepartamentoInversionista,
							codigociudadInversionista, codigoCIIUInversionista, telefonoInversionista,
							direccionInversionista, correoElectronicoInversionista, this.getSector(sectorInversionista),
							this.getTipoEmpresa(tipodeempresaInversionista), naturalezaInversionista,
							this.getSuperintendencia(superintendenciaInversionista));

				}

//-------------------------------------------------------------------------------------------------------------------------

				// ----------------[Descripci�n de la
				// operaci�nInversiones]--------------------------------

				msg = null;

				if (!isValid(elementoSelecionado) || elementoSelecionado.equals("No hay ninguna opci�n seleccionada.")
						|| elementoSelecionado.equals("Seleccione un numeral")) {

					if (this.element(listNumeralOperacion_Numeral1) != null) {
						msg = this.selectListItemExacto(listNumeralOperacion_Numeral1, numeral1);

						if (cambiarlistnumeralOperacion_Numeral1.equals("SI")) {

//							numeral1Acambiar = SettingsRun.getTestData().getParameter("Numeral cambiario A Cambiar 1");

							if (this.element(listNumeralOperacion_Numeral1) != null) {

								msg = this.selectListItemExacto(listNumeralOperacion_Numeral1, numeral1Acambiar);

								if (isValid(msg)) {

									String[] Listanumeral = this.getListItems(listNumeralOperacion_Numeral1);
									Reporter.write("Lista Disponible");

									for (String numerales : Listanumeral) {
										Reporter.write(numerales);
									}

									return msg;
								}
							}

						} else {

							if (isValid(msg)) {

								String[] Listanumeral = this.getListItems(listNumeralOperacion_Numeral1);
								Reporter.write("Lista Disponible");

								for (String numerales : Listanumeral) {
									Reporter.write(numerales);
								}

								return msg;
							}
						}

					}

				} else {
					Reporter.reportEvent(Reporter.MIC_INFO, "Valor ya seleccionado: " + elementoSelecionado + "");
				}

				if (cambiarlistnumeralOperacion_Numeral1.equals("SI")) {

//					numeral1Acambiar = SettingsRun.getTestData().getParameter("Numeral cambiario A Cambiar 1");

					if (this.element(listNumeralOperacion_Numeral1) != null) {
						msg = this.selectListItemExacto(listNumeralOperacion_Numeral1, numeral1Acambiar);
						if (isValid(msg)) {
							return msg;
						}
					}
				}

				// ------------------------------------------------------------------------------------------

			} else {

				msg = null;

				// ----------------[Descripci�n de la
				// operaci�nInversiones]--------------------------------
//				cambiarConcepto = SettingsRun.getTestData().getParameter("Cambiar Concepto de la transferencia");

				if (cambiarConcepto.equals("NO")) {

					if (!isValid(elementoSelecionado)
							|| elementoSelecionado.equals("No hay ninguna opci�n seleccionada.")
							|| elementoSelecionado.equals("Seleccione un numeral")) {

						if (this.element(listNumeralOperacion_Numeral1) != null) {
							msg = this.selectListItemExacto(listNumeralOperacion_Numeral1, numeral1);
						}

						if (this.element(ListNumeralOperacion_Numeral0id) != null) {
							msg = this.selectListItemExacto(ListNumeralOperacion_Numeral0id, numeral1);
						}

						if (isValid(msg)) {
							return msg;
						}

					} else {
						Reporter.reportEvent(Reporter.MIC_INFO, "Valor ya seleccionado: " + elementoSelecionado + "");
					}
				}

				if (conceptosCambiario.contains(Endeudamiento)) {

//					String cambiarDatosDescripciondelaoperacion = SettingsRun.getTestData().getParameter("Cambiar Datos Descripci�n de la operaci�n");

//					String numerodelprestamooaval = SettingsRun.getTestData().getParameter("N�mero del pr�stamo o aval");

					this.write(campInputNumerodelprestamooAval, numerodelprestamooaval);

//					String nombredelacreedoroeldeudoroavalista = SettingsRun.getTestData().getParameter("Nombre del acreedor o el deudor o avalista");

					this.write(campInputNombreAcredorDeudorAvalista, nombredelacreedoroeldeudoroavalista);

					if (cambiarDatosDescripciondelaoperacion.equals("SI")) {

//						String nombredeldeudoroacreedorAvaladoobeneficiarioresidente = SettingsRun.getTestData().getParameter("Nombre del deudor o acreedor / Avalado o beneficiario residente");

						this.write(campInputdescripcionOperacion_NombreDeudorAcredor,
								nombredeldeudoroacreedorAvaladoobeneficiarioresidente);

//						String tipodeidentificaci�ndeldeudor = SettingsRun.getTestData().getParameter("Tipo de identificaci�n del deudor");

						msg = this.seleOption(campSeletListaTipoIdentificacion, tipodeidentificaci�ndeldeudor);

						if (msg.isEmpty()) {
							Reporter.reportEvent(Reporter.MIC_FAIL,
									" Elemento [" + tipoDestinodelainversion + "] NO presentado");
							this.click(btnEnviar);
//							Util.wait(5);
							msg = this.closeActiveIntAlertConfirma();
							if (isValid(msg)) {
								return msg;
							}
						}

//						String numerodeidentificaciondeldeudor = SettingsRun.getTestData().getParameter("N�mero de identificaci�n del deudor");
						this.write(campInputNumeroIdentificacion, numerodeidentificaciondeldeudor);

//						String digitodeverificacion = SettingsRun.getTestData().getParameter("D�gito de verificaci�n");
						this.write(campInputDigitoVerificacion, digitodeverificacion);

//						String monedaestipulada = SettingsRun.getTestData().getParameter("Moneda estipulada");

						msg = this.seleOption(campSeletListaMonedaEstipulada, monedaestipulada);

						if (msg.isEmpty()) {
							Reporter.reportEvent(Reporter.MIC_FAIL,
									" Elemento [" + tipoDestinodelainversion + "] NO presentado");
							this.click(btnEnviar);
							Util.wait(5);
							msg = this.closeActiveIntAlertConfirma();
							if (isValid(msg)) {
								return msg;
							}
						}

//						String valormonedaestipulada = SettingsRun.getTestData().getParameter("Valor moneda estipulada");

						this.write(campInputValorMoneda, valormonedaestipulada);

//						String tasadecambiomoneda = SettingsRun.getTestData().getParameter("Tasa de cambio moneda");
						this.write(campInputTasadeCambioMonedaEstipulada, tasadecambiomoneda);

					} else {

						String nombreDeudorAcredor = this.element(campInputdescripcionOperacion_NombreDeudorAcredor)
								.getAttribute("value");

						String deudorAcredorTipoIdentificacion = this
								.selectListObtenerDatoseleccionado(campSeletListaTipoIdentificacion);

						String deudorAcredorNumeroIdentificacion = this.element(campInputNumeroIdentificacion)
								.getAttribute("value");

						String digitoVerificacion = this.element(campInputDigitoVerificacion).getAttribute("value");

//					String monedaEstipulada = this.selectListObtenerDatoseleccionado(ampSeletListaMonedaEstipulada);

						String monedaEstipulada = this.element(campSeletListaMonedaEstipulada).getText();

						String valorMoneda = this.element(campInputValorMoneda).getAttribute("value");

						String tasadeCambioMonedaEstipulada = this.element(campInputTasadeCambioMonedaEstipulada)
								.getAttribute("value");

					}
				}

				if (cambiarlistnumeralOperacion_Numeral1.equals("SI")) {

//					numeral1Acambiar = SettingsRun.getTestData().getParameter("Numeral cambiario A Cambiar 1");

					if (isElementInteractable(listNumeralOperacion_Numeral1)) {
						msg = this.selectListItemExacto(listNumeralOperacion_Numeral1, numeral1Acambiar);
						if (isValid(msg)) {
							return msg;
						}
					}

					if (isElementInteractable(ListNumeralOperacion_Numeral0id)) {
						msg = this.selectListItemExacto(ListNumeralOperacion_Numeral0id, numeral1Acambiar);
						if (isValid(msg)) {
							return msg;
						}
					}
				}
			}

			Util.wait(3);

			msg = this.closeActiveIntAlertConfirma();

			if (msg != null && msg.contains("Motivo no aplica para este numeral")) {
				Reporter.reportEvent(Reporter.MIC_WARNING, msg + " Se continua con el flujo");
			}

			if (isElementInteractable(inputnumeralOperacion_Participaciones)) {
				this.write(inputnumeralOperacion_Participaciones, "121");
				this.mouseOver(this.element("//label[contains(text(), 'Participaciones')]/following-sibling::div"));
				this.mouseClick();
			}

			Util.wait(3);
			if (isElementInteractable(seleListnumeralOperacion_MotivosSinParticipacion)) {
				msg = this.selectListItem2(empresaReceptoraCodigoCiudad, "Inversi�n a Plazos");

				if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
					Reporter.reportEvent(Reporter.MIC_FAIL, msg);
					this.click(btnEnviar);
					msg = this.closeActiveIntAlertConfirma();
					if (isValid(msg)) {
						return msg;
					}
				}
			}

			if (isElementInteractable(inputOperacion_NumeroDeposito)) {
				if (!isValid(numeroDeposito)) {
					Evidence.save("Se presento el Numero Deposito",this);
					Reporter.reportEvent(Reporter.MIC_INFO,
							"Se presento el campo Numero Deposito y el campo en la data es nulo o vacio ");
				}

				contador = 0;

				do {
					contador++;
					Util.wait(1);
					if (contador >= 30) {
						Evidence.save("DYF-Envio",this);
						this.click(btnEnviar);
						Util.wait(1);

						msg = this.closeActiveIntAlertConfirma();

						if (isValid(msg))
							Reporter.reportEvent(Reporter.MIC_FAIL, msg);

//						Util.wait(1);
						msg = this.closeActiveIntAlertConfirma();
						if (isValid(msg))
							this.getDriver().switchTo().defaultContent();

						return msg;

					}
				} while (this.element(inputOperacion_NumeroDeposito) == null
						&& this.isEnabled(inputOperacion_NumeroDeposito));

				this.write(inputOperacion_NumeroDeposito, numeroDeposito);

				Evidence.save("NumeroDeposito",this);
			}

			if (conceptosCambiario.contains(Exportaciones)) {
				ValidacionDeduciones(numeroDeposito, numeroFacturaoReferDeclaracion, valordeduciones, valorTx);

			} else if (!conceptosCambiario.contains(Exportaciones) || !conceptosCambiario.contains(Inversiones)) {

				double valortx = Double.parseDouble(valorTx.replace(".", "").replace(",", "."));
				double valorenUSD_Precargado = 0.0;

				if (!isValid(valorNumeral1)) {
					if (isElementInteractable(inputValorenUSD))
						valorenUSD_Precargado = ValidarValorNumeralvsPortal(inputValorenUSD, valorTx);

				} else if (isValid(valorNumeral1)) {
					if (isElementInteractable(inputValorenUSD))
						valorenUSD_Precargado = ValidarValorNumeralvsPortal(inputValorenUSD, valorNumeral1);
				}

				// Validacion Numeral
				// 1-----------------------------------------------------------------------------------

//				String cambiarValornumeralcambiario1 = SettingsRun.getTestData().getParameter("Cambiar Valor numeral cambiario 1");

				if (cambiarValornumeralcambiario1.equals("SI")) {

//					String ValorNumeral1Camb = SettingsRun.getTestData().getParameter("Valor numeral cambiario A Cambiar 1");

					if (isElementInteractable(inputValorMonedaGiro))
						this.write(inputValorMonedaGiro, String.valueOf(ValorNumeral1Camb).replace(".", ","));

					if (isElementInteractable(inputValorMonedaNegociacion))
						this.write(inputValorMonedaNegociacion, String.valueOf(ValorNumeral1Camb).replace(".", ","));

					Reporter.reportEvent(Reporter.MIC_PASS,
							"Se agrega el valor de las deduciones en el valor de la moneda: " + valorenUSD_Precargado);
				}

				// Validacion Numeral
				// 2-----------------------------------------------------------------------------------
				String CambiarNumeral2 = "";

				if (SettingsRun.getTestData().parameterExist("Cambiar Numeral cambiario 2"))
					CambiarNumeral2 = SettingsRun.getTestData().getParameter("Cambiar Numeral cambiario 2");

				String Numeralcambiario2aCambiar = SettingsRun.getTestData()
						.getParameter("Numeral cambiario A Cambiar 2");
				String cambiarValornumeralcambiario2 = SettingsRun.getTestData()
						.getParameter("Cambiar Valor numeral cambiario 2");

				if (isValid(numCambiario2)) {

					if (isElementInteractable(ListNumeralOperacion_Numeral1id))
						elementoSelecionado = ValidacionListaNumerales(ListNumeralOperacion_Numeral1id);

					if (elementoSelecionado.equals("No se presento el campo Numeral")
							|| elementoSelecionado.contains("Error mensaje:")) {
						return elementoSelecionado;
					}

					if (!isValid(elementoSelecionado)
							|| elementoSelecionado.equals("No hay ninguna opci�n seleccionada.")
							|| elementoSelecionado.equals("Seleccione un numeral")) {

						if (isElementInteractable(ListNumeralOperacion_Numeral1id)) {
							msg = this.selectListItemExacto(ListNumeralOperacion_Numeral1id, numCambiario2);
							if (isValid(msg)) {
								return msg;
							}

							Reporter.reportEvent(Reporter.MIC_FAIL, msg);
						}

					} else {

						Reporter.reportEvent(Reporter.MIC_INFO, "Valor ya seleccionado: " + elementoSelecionado + "");

					}

					double valorenUSD_Precargado2 = ValidarValorNumeralvsPortal(inputValorenUSD1, valorNumeral2);

					if (CambiarNumeral2.equals("SI")) {

						if (isElementInteractable(ListNumeralOperacion_Numeral1id)) {

							msg = this.selectListItemExacto(ListNumeralOperacion_Numeral1id, Numeralcambiario2aCambiar);

							if (isValid(msg)) {
								return msg;
							}

						}

						String ValorNumeral2Camb = SettingsRun.getTestData()
								.getParameter("Valor numeral cambiario A Cambiar 2");

						if (isElementInteractable(inputValorMonedaGiro1))
							this.write(inputValorMonedaGiro1, String.valueOf(ValorNumeral2Camb).replace(".", ","));

						if (isElementInteractable(inputValorMonedaNegociacion1))
							this.write(inputValorMonedaNegociacion1,
									String.valueOf(ValorNumeral2Camb).replace(".", ","));
					}

				}

			} else {

				// el campo esta vacio da clic al boton siguiente
				Evidence.save("Campo-Seleccione Numeral",this);
				this.click(btnEnviar);

				msg = this.closeActiveIntAlertConfirma();

				if (isValid(msg))
					return msg;
			}

//			String concepto = SettingsRun.getTestData().getParameter("Concepto de la transferencia A Cambiar");

			if (isValid(conceptoAcambiar) && !conceptoAcambiar.equals("4 - Inversiones internacionales")) {

				if (conceptoAcambiar.contains(Exportaciones))
					ValidacionDeduciones(numeroDeposito, numeroFacturaoReferDeclaracion, valordeduciones, valorTx);

				if (isValid(validacionAdicionar) && !validacionAdicionar.equals("No Aplica")
						&& !validacionAdicionar.equals("1")) {

					// Numero de deposito
					if (isElementInteractable(inputOperacion_NumeroDeposito))
						this.write(inputOperacion_NumeroDeposito, numeroDeposito);

					ValidarNumeral(Integer.parseInt(validacionAdicionar), validacionAdicionar);

				}
			}

			// ============================================================================================

			Evidence.save("Informacion-Numeral",this);

			Reporter.reportEvent(Reporter.MIC_PASS, "Se adiciona los numerales y valores en la moneda degiro");
		}

		// -------------------- [Cargue aqu� los documentos que soportan este
		// formulario]-------------------

		Util.wait(5);

//		String validacionDian = SettingsRun.getTestData().getParameter("Validacion - DIAN");

		ValidacionDocumentosDIAN(Integer.parseInt(validacionDian), validacionDian);

		Util.wait(2);

		msg = this.CargaArchivos(cargarArchivo);

		if (isValid(msg))
			return msg;

		Reporter.reportEvent(Reporter.MIC_PASS, "Se adjunta soporter y / o modificacion de los datos");

		Evidence.save("Cargue de Archivos",this);

		this.click(btnEnviar);

		contador = 0;

		do {

			msg = this.getMsgAlertIfExistxPath("//*[@id='myModal']");

		} while (!isValid(msg) && contador <= 5);

		if (isValid(msg)) {
			Reporter.reportEvent(Reporter.MIC_PASS, msg);
			Util.wait(3);
			if (msg.contains(
					"Documentos enviados exitosamente. Davivienda validar� la informaci�n recibida y en caso de presentar inconsistencias informar� v�a correo electr�nico. Por favor haga seguimiento de su operaci�n en la opci�n de consultas y verifique el estado de su tr�mite.")) {
				msg = this.closeActiveIntAlert();
			}
		}

		msg = this.closeActiveIntAlert();

		if (isValid(msg)) {
			Reporter.reportEvent(Reporter.MIC_FAIL, msg);
		}

		return msg;
	}

// ============================================[validarAlertDocuc]===========================================================================	

	/**
	 * Validad las alertas del cargue del documento
	 * 
	 * @return alert o null
	 * @throws Exception
	 */
	public String validarAlertDocuc() throws Exception {

		String msg = null;
		msg = this.closeActiveIntAlert();
		if (isValid(msg) && !msg.contains(
				"Los campos que no se presentan en la declaraci�n de cambio ser�n autocompletados por el aplicativo")) {
			Reporter.reportEvent(Reporter.MIC_FAIL, msg);
		}
		msg = this.getMsgAlertIfExistxPath("//p[1]");
		if (msg == null)
			msg = this.getMsgAlertIfExist("myModal");

		if (isValid(msg)) {
			if (!msg.contains("En este m�dulo puede visualizar las operaciones que requieren adjuntar soportes")
					&& !msg.contains("Los campos que no se presentan en la declaraci�n")
					&& !msg.contains("Los campos que no se presentan en la declaraci�n")) {
				Evidence.saveAllScreens(msg, this);
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}

			if (msg.contains("Los campos que no se presentan en la declaraci�n")) {
				this.element("//*[@id='btnmodalaceptar']").click();
			}
		}

		return msg;
	}

// ============================================[ValidacionTablasInformativa]===========================================================================	

	/**
	 * Valida la Tabla informativa
	 * 
	 * @throws Exception
	 */
	public void ValidacionTablasInformativa() throws Exception {
		WebElement tablaDatosTx = null;
		boolean tablaDesple = false;
		// Verificar tablas principales
		String[] tablasPrimarias = { "table1", "table3", "table4" };
		for (String tabla : tablasPrimarias) {
			tablaDatosTx = obtenerTabla(tabInfoDatosTx, tabla);
			if (tablaDatosTx != null) {
				tablaDesple = true;
				break;
			}
		}

		// Verificar tablas secundarias si no se encontr� ninguna principal
		if (!tablaDesple) {
			for (String tabla : tablasPrimarias) {
				tablaDatosTx = obtenerTabla(tabInfoDatos2, tabla);
				if (tablaDatosTx != null) {
					tablaDesple = true;
					break;
				}
			}
		}

		// Procesar la tabla en contrada
		if (tablaDatosTx != null) {
			List<WebElement> encabezados = tablaDatosTx.findElements(By.tagName("th"));
			List<WebElement> datos = tablaDatosTx.findElements(By.tagName("td"));

			String[] textosEncabezados = encabezados.stream().map(WebElement::getText).toArray(String[]::new);

			String[] textosDatos = datos.stream().map(WebElement::getText).toArray(String[]::new);

			// Redirigir la salida para capturar el contenido
			ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
			PrintStream originalOut = System.out;
			System.setOut(new PrintStream(salidaCapturada));

			System.out.println("Tabla de Datos:\n");

			// Imprimir encabezados
			System.out.println(String.join("\t", textosEncabezados));

			// Imprimir datos organizados por filas
			int columnas = textosEncabezados.length;
			for (int i = 0; i < textosDatos.length; i++) {
				System.out.print(textosDatos[i] + "\t");
				if ((i + 1) % columnas == 0) {
					System.out.println();
				}
			}
			// Restaurar salida est�ndar y capturar el texto generado
			System.setOut(originalOut);
			String textoCapturado = salidaCapturada.toString();

			Reporter.reportEvent(Reporter.MIC_INFO, textoCapturado);
		}
	}

// ============================================[ValidacionTablasInformativa]===========================================================================	

	/**
	 * Metodo se en carga de validar o selecionar el Tipo de operacion si es
	 * [Inicial o Devoluci�n]
	 * 
	 * @param conceptosCambiario
	 * @param tipoOperacion
	 * @throws Exception
	 */
	public void ValidacionTipodeoperacion(String conceptosCambiario, String tipoOperacion) throws Exception {

		WebElement webTipoO = null;
		String pseudoElemento = null;

		if (conceptosCambiario.contains(ServTryOtros) || conceptosCambiario.contains(Endeudamiento)
				|| conceptosCambiario.contains(Inversiones)) {

			if (isValid(tipoOperacion)) {

				if (tipoOperacion.equals(Inicial)) {

					webTipoO = this.element(tipoOperacionXpath.replace("OPERACION", Inicial));

				} else if (tipoOperacion.equals(Devolucion)) {

					webTipoO = this.element(tipoOperacionXpath.replace("OPERACION", Devolucion));
				}

				this.mouseOver(webTipoO);

				this.mouseClick();

				pseudoElemento = (String) this.getJse().executeScript(strPseudoElemento, webTipoO);

				if (!pseudoElemento.equals("rgb(255, 0, 0)"))
					Reporter.reportEvent(Reporter.MIC_WARNING, "El camo de Tipo de Operaci�n No selecionado");

			} else {
				Reporter.reportEvent(Reporter.MIC_INFO, "El camo de Tipo de Operaci�n esta en blanco");
			}

		} else if (conceptosCambiario.contains(Importaciones)) {

			webTipoO = this.element(tipoOperacionXpath.replace("OPERACION", Inicial));
			pseudoElemento = (String) this.getJse().executeScript(strPseudoElemento, webTipoO);

			if (!pseudoElemento.equals("rgb(255, 0, 0)"))
				Reporter.reportEvent(Reporter.MIC_WARNING,
						"El camo de Tipo de Operaci�n No esta selecionado" + Inicial + "para" + Importaciones);

		} else if (conceptosCambiario.contains(Exportaciones)) {

			webTipoO = this.element(tipoOperacionXpath.replace("OPERACION", Devolucion));

			pseudoElemento = (String) this.getJse().executeScript(strPseudoElemento, webTipoO);

			if (!pseudoElemento.equals("rgb(255, 0, 0)"))
				Reporter.reportEvent(Reporter.MIC_WARNING,
						"El camo de Tipo de Operaci�n No esta selecionado" + Devolucion + "para" + Exportaciones);
		}
	}

// ============================================[EmpresaReceptoraoAdministrador]===========================================================================

	/**
	 * Este metodo agrega los datos del receptor (Portafolio)
	 * 
	 * @param tipoId tipo de identificacion del recetor
	 * @param numID  numero de identificaci�n del recetor
	 * @param digNo  digito
	 * @param nomRaz
	 * @return Retorna el mensaje de error o null si es pass
	 * @throws Exception
	 */
	public String EmpresaReceptoraoAdministrador(String tipoId, String numID, String digNo, String nomRaz,
			String codigoPais, String codigoDepartamento, String codigoCiudad, String codigoCIUUEmpresas,
			String telefono, String direccion, String correo, String sector, String tipoEmpresa, String naturaleza,
			String superintendencia) throws Exception {

		String msg = null;

		contador = 0;
		do {
			contador++;
			Util.wait(3);
			if (contador >= 30) {
				this.getDriver().switchTo().defaultContent();
				return "No se encuentra el campo empresa Receptora";
			}

		} while (this.element(listSeleOpcionempresaReceptoraTipoIdentificacion) == null
				&& !this.element(listSeleOpcionempresaReceptoraTipoIdentificacion).isDisplayed());

		msg = this.selectListItemExacto(listSeleOpcionempresaReceptoraTipoIdentificacion, tipoId);
//		msg = this.selectListItemExacto(listSeleOpcionempresaReceptoraTipoIdentificacion, "NR");

		Evidence.save("Campo-Seleccione Tipo Identificaci�n",this);
		if (isValid(msg)) {
			Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			this.click(btnEnviar);
			msg = this.closeActiveIntAlertConfirma();
			if (isValid(msg)) {
				return msg;
			}
		}

		Util.wait(5);

		msg = this.getActiveIntAlert();

		if (msg != null && msg.contains("Advertencia Para continuar debe completar la informaci�n solicitada")) {

			msg = this.closeActiveIntAlertConfirma();
		}

		this.write(imputempresaReceptoraNumeroIdentificacion, numID);

		if (isElementInteractable(imputempresaReceptoraDigitoVerificacion)) {
			this.write(imputempresaReceptoraDigitoVerificacion, digNo);
		}

		if (isElementInteractable(imputempresaReceptoraNombreRazonSocial)) {
			this.write(imputempresaReceptoraNombreRazonSocial, nomRaz);
		}

		if (isElementInteractable(empresaReceptoraCodigoPais_NHidden)) {
			// Dato predetivo
			msg = this.selectPredictiveListItemLi(empresaReceptoraCodigoPais_NHidden, codigoPais);
			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}

		if (isElementInteractable(empresaReceptoraCodigoDepartamento_NHidden)) {

			// Dato predetivo
			msg = this.selectPredictiveListItemLi(empresaReceptoraCodigoDepartamento_NHidden, codigoDepartamento);

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}

		Util.wait(2);

		if (isElementInteractable(empresaReceptoraCodigoCiudad)) {

			msg = this.selectListItem2(empresaReceptoraCodigoCiudad, codigoCiudad);
			String[] listapais = this.getListItems(empresaReceptoraCodigoCiudad);

//			for (String listapis : listapais) {
//				System.out.println(listapis);
//			}

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}

		if (isElementInteractable(empresaReceptoraCodigoCIUU)) {
			msg = this.selectListItem2(empresaReceptoraCodigoCIUU, codigoCIUUEmpresas);

			Evidence.save("Campo-Seleccione Tipo Identificaci�n",this);

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}

		Util.wait(2);
		
		if (isElementInteractable(empresaReceptoraTelefono))
			this.write(empresaReceptoraTelefono, telefono);

		if (isElementInteractable(empresaReceptoraDireccion))
			this.write(empresaReceptoraDireccion, direccion);

		if (isElementInteractable(empresaReceptoraCorreo))
			this.write(empresaReceptoraCorreo, correo);

		if (isElementInteractable(empresaReceptoraSector)) {
			msg = this.selectListItem2(empresaReceptoraSector, sector);

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}
		
		if (isElementInteractable(empresaReceptoraTipoEmpresa)) {

			msg = this.selectListItem2(empresaReceptoraTipoEmpresa, tipoEmpresa);
			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}

		Util.wait(2);

		if (isElementInteractable(selectempresaReceptora_Naturaleza)) {

			msg = this.selectListItem2(selectempresaReceptora_Naturaleza, naturaleza);

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}
		
		Util.wait(2);
		if (isElementInteractable(empresaReceptoraSuperintendencia)) {
			msg = this.selectListItem2(empresaReceptoraSuperintendencia, superintendencia);
			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}
		return msg;
	}

// ============================================[Identificaci�ndelInversionista]===========================================================================

	/**
	 * Identificaci�n del Inversionista (Nacional o extranjero) Metodo se encarga de
	 * ingresar los Datos del tipo de inversion
	 * 
	 * @return
	 * @throws Exception
	 */
	public String Identificaci�ndelInversionista(String tipoId, String numID, String digNo, String nomRaz,
			String codigoPais, String codigoDepartamento, String codigoCiudad, String codigoCIUUInversionista,
			String telefono, String direccion, String correo, String sector, String tipoEmpresa, String naturaleza,
			String superintendencia) throws Exception {

		String msg = null;
		contador = 0;
		do {
			contador++;
			Util.wait(1);
			if (contador >= 30) {
				this.getDriver().switchTo().defaultContent();
				return "No se encuentra el campo Opcion inversionista";
			}

		} while (this.element(listSeleOpcioninversionistaTipoIdentificacion) == null && !this.element(listSeleOpcioninversionistaTipoIdentificacion).isDisplayed());

		msg = this.selectListItemExacto(listSeleOpcioninversionistaTipoIdentificacion, tipoId);

		Evidence.save("Campo-Seleccione Tipo Identificaci�n",this);

		if (isValid(msg)) {
			Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			this.click(btnEnviar);
			msg = this.closeActiveIntAlertConfirma();
			if (isValid(msg)) {
				return msg;
			}
		}

		Util.wait(2);
		this.write(imputinversionistaNumeroIdentificacion, numID);

		if (isElementInteractable(imputinversionista_DigitoVerificacion)) {

			this.write(imputinversionista_DigitoVerificacion, digNo);
		}

		this.write(imputinversionista_NombreRazonSocial, nomRaz);

		if (isElementInteractable(selectinversionista_CodigoPais)) {

			msg = this.selectListItem2(selectinversionista_CodigoPais, codigoPais);

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}

		Util.wait(2);
		
		if (isElementInteractable(imputinversionista_CodigoDepartamento_NHidden)) {
			// Dato predetivo
			msg = this.selectPredictiveListItemLi(imputinversionista_CodigoDepartamento_NHidden, codigoDepartamento);

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}
		Util.wait(5);

		msg = this.getActiveIntAlert();

		if (msg != null && msg.contains("Advertencia Para continuar debe completar la informaci�n solicitada")) {
			Reporter.reportEvent(Reporter.MIC_WARNING, msg);
			this.closeActiveIntAlertConfirma();

		}

		if (isElementInteractable(selectinversionista_CodigoCiudad)) {

			msg = this.selectListItem2(selectinversionista_CodigoCiudad, codigoCiudad);

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}

		Util.wait(2);
		// Dato predetivo
		if (isElementInteractable(imputinversionista_CodigoCIUU_NHidden)) {

			msg = this.selectPredictiveListItemLi(imputinversionista_CodigoCIUU_NHidden, codigoCIUUInversionista);
//			this.selectPredictiveListItemLi(imputinversionista_CodigoCIUU_NHidden, "4775");
			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}
		Evidence.save("Datos_Ingresados",this);

		Util.wait(2);

		if (isElementInteractable(selectinversionista_Naturaleza)) {

			msg = this.selectListItem2(selectinversionista_Naturaleza, naturaleza);

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}

		Util.wait(2);

		if (isElementInteractable(imputinversionista_Telefono))
			this.write(imputinversionista_Telefono, telefono);

		Util.wait(1);
		if (isElementInteractable(imputinversionista_Correo))

			this.write(imputinversionista_Correo, correo);

		Util.wait(1);
		if (isElementInteractable(imputinversionista_Direccion))
			this.write(imputinversionista_Direccion, direccion);

		Util.wait(1);
		if (isElementInteractable(selectinversionista_Sector)) {

			msg = this.selectListItem2(selectinversionista_Sector, sector);
			if (isValid(msg) && !msg.contains(
					"Valor ya seleccionado [PU = P�blico] - no es igual a [PU] pero se deja por estar contenido.")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				Util.wait(3);
				msg = this.closeActiveIntAlertConfirma();
				if (msg == null) {
					msg = this.closeActiveIntAlert();
				}

				if (isValid(msg)) {
					return msg;
				}
			}
		}

		Util.wait(1);
		if (isElementInteractable(selectinversionista_TipoEmpresa)) {

			msg = this.selectListItem2(selectinversionista_TipoEmpresa, tipoEmpresa);
//			msg = this.selectListItem2(selectinversionista_TipoEmpresa, "1");

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}

		Util.wait(1);

		if (isElementInteractable(selectinversionista_Superintendencia)) {

			msg = this.selectListItem2(selectinversionista_Superintendencia, superintendencia);

			if (isValid(msg) && !msg.contains("Valor ya seleccionado")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				this.click(btnEnviar);
				msg = this.closeActiveIntAlertConfirma();
				if (isValid(msg)) {
					return msg;
				}
			}
		}
		Util.wait(2);
		return msg;
	}

// ============================================[ValidacionListaNumeral1]===========================================================================

	/**
	 * 
	 * @param conceptosCambiario
	 * @return
	 * @throws Exception
	 */
	public String ValidacionListaNumeral1(String conceptosCambiario) throws Exception {

		WebElement listnumeralOperacion_Numeral1 = null;
		contador = 0;
		String msg = null;
		String elementoSelecionado = "";
		do {

			Util.wait(1);

			listnumeralOperacion_Numeral1 = this.element(listNumeralOperacion_Numeral1);

			if (!conceptosCambiario.contains(Inversiones)) {
				Util.wait(1);
				if (isElementInteractable(ListNumeralOperacion_Numeral0id))
					listnumeralOperacion_Numeral1 = this.element(ListNumeralOperacion_Numeral0id);
			}

			contador++;
			if (contador >= 30) {

				msg = "No se presento el campo Numeral";

				if (this.element(sesionEx) != null) {

					msg = this.element(sesionEx).getText();

					if (isValid(msg)) {
						Reporter.reportEvent(Reporter.MIC_FAIL, msg);
					}

					Evidence.save("No se presento el campo Numeral",this);

					if (this.element(btnEnviar) != null) {
						this.click(btnEnviar);
					}

					if (isValid(msg))
						return "Error mensaje: " + msg;
				}

				Evidence.save("No se presento el campo Numeral",this);

				this.click(btnEnviar);

				return "No se presento el campo Numeral";
			}

			if (listnumeralOperacion_Numeral1 != null) {
				if (isElementInteractable(listnumeralOperacion_Numeral1)) {
					elementoSelecionado = this.selectListObtenerDatoseleccionado(listnumeralOperacion_Numeral1);
				}
			}

		} while (listnumeralOperacion_Numeral1 == null
				|| this.isEnabled(listnumeralOperacion_Numeral1) == false
						&& this.isDisplayed(listnumeralOperacion_Numeral1) == false);

		return elementoSelecionado;
	}

// ============================================[ValidacionListaNumerales]===========================================================================	

	public String ValidacionListaNumerales(By locator) throws Exception {

		WebElement listnumeralOperacion_Numerales = null;
		contador = 0;
		String msg = null;
		String elementoSelecionado = "";
		do {
			Util.wait(1);
			listnumeralOperacion_Numerales = this.element(locator);
			contador++;
			if (contador >= 30) {
				msg = "No se presento el campo Numeral";
				if (this.element(sesionEx) != null) {

					msg = this.element(sesionEx).getText();

					if (isValid(msg)) {
						Reporter.reportEvent(Reporter.MIC_FAIL, msg);
					}

					Evidence.save("No se presento el campo Numeral",this);

					if (this.element(btnEnviar) != null) {
						this.click(btnEnviar);
					}

					if (isValid(msg))
						return "Error mensaje: " + msg;
				}

				Evidence.save("No se presento el campo Numeral",this);
				this.click(btnEnviar);
				return "No se presento el campo Numeral";
			}

			if (isElementInteractable(listnumeralOperacion_Numerales)) {
				elementoSelecionado = this.selectListObtenerDatoseleccionado(listnumeralOperacion_Numerales);
			}

		} while (listnumeralOperacion_Numerales == null
				|| this.isEnabled(listnumeralOperacion_Numerales) == false
						&& this.isDisplayed(listnumeralOperacion_Numerales) == false);

		return elementoSelecionado;
	}

// ============================================[ValidarValorNumeralvsPortal]===========================================================================	

	public double ValidarValorNumeralvsPortal(By locator, String valornumeral) throws Exception {

		double valorenUSD_Precargado = Double.parseDouble(this.getText(locator).replace(".", "").replace(",", "."));
		double valorNumeraldata = Double.parseDouble(valornumeral);

		if (valorNumeraldata == valorenUSD_Precargado) {
			Reporter.reportEvent(Reporter.MIC_PASS,
					"El valor de la Moneda corresponde con el Mostrado en el portal: " + valorenUSD_Precargado);
		} else {
			Reporter.reportEvent(Reporter.MIC_FAIL,
					"El valor de la Moneda NO corresponde con el Mostrado en el portal: " + valorenUSD_Precargado);
		}
		return valorenUSD_Precargado;
	}

// ============================================[ValidarNumeral]===========================================================================

	/**
	 * Valida los numerales a Adicionar
	 * 
	 * @param num
	 * @throws Exception
	 */
	public void ValidarNumeral(int num, String validacionAdicionar) throws Exception {
		int contadorVa = 1;
		for (int i = 2; i <= num; i++) {
			contadorVa++;
			String cambiarNumeralN = "";
			if (SettingsRun.getTestData().parameterExist("Cambiar Numeral cambiario " + i))
				cambiarNumeralN = SettingsRun.getTestData().getParameter("Cambiar Numeral cambiario " + i);

			if (cambiarNumeralN.equals("Adicionar numeral")) {

				// locator Lista Numeral y valor Numral
				String listNumeralId = ListNumeralOperacion_Numeral2id.replace("NUM", Integer.toString((i - 1)));

				String inputValorMonedaId = inputValorMonedaGiro2.replace("NUM", Integer.toString((i - 1)));
				String inputValorMonedaIdNegociacion = inputValorMonedaNegociacion2.replace("NUM",
						Integer.toString((i - 1)));
				String inputValorMonedaValorenUSD = inputValorenUSD2.replace("NUM", Integer.toString((i - 1)));

				String inputNumeroDeclaracion = inputNumeroDeclaracion2.replace("NUM", Integer.toString((i - 1)));
				String inputNumeroDeposito = inputOperacion_NumeroDeposito2.replace("NUM", Integer.toString((i - 1)));

				String inputValorMonedaEstipulada = inputValorMonedaEstipulada2.replace("NUM",
						Integer.toString((i - 1)));

				// Datos a agregar o valor a cambiar
				String numeralACambiar = SettingsRun.getTestData().getParameter("Numeral cambiario A Cambiar " + i);
				String valorNumeralCambiar = SettingsRun.getTestData()
						.getParameter("Valor numeral cambiario A Cambiar " + i);

				String valorDeclaracio = SettingsRun.getTestData().getParameter("N�mero de declaraci�n " + i);
				String ValorMonedaEstipulada = SettingsRun.getTestData().getParameter("Valor moneda estipulada " + i);
				String valorDeposito = SettingsRun.getTestData().getParameter("N�mero de dep�sito " + i);

				String result = AdicionarNumeral(contadorVa, listNumeralId, validacionAdicionar, inputValorMonedaId,
						inputValorMonedaIdNegociacion, inputValorMonedaValorenUSD, inputNumeroDeclaracion,
						inputNumeroDeposito, numeralACambiar, valorNumeralCambiar, valorDeclaracio, valorDeposito,
						inputValorMonedaEstipulada, ValorMonedaEstipulada);

				if (isValid(result)) {
					Reporter.reportEvent(Reporter.MIC_INFO, "Error en la iteraci�n " + i + ": " + result);
					break; // Detener el ciclo si hay un error
				}
			}
		}
	}

//==============================================================================================================================================================================================

// ===========================================[AdicionarNumeral]===========================================================================
	/**
	 * Adiciona la informacion del numeral dependiendo si tiene valor numero de
	 * posito o informacion
	 * 
	 * @param listNumeralId
	 * @param inputValorMonedaId
	 * @param inputValorMonedaIdNegociacion
	 * @param inputValorMonedaValorenUSD
	 * @param numeralACambiar
	 * @param valorNumeralCambiar
	 * @return
	 * @throws Exception
	 */
	public String AdicionarNumeral(int contadorVa, String listNumeralId, String validacionAdicionar,
			String inputValorMonedaId, String inputValorMonedaIdNegociacion, String inputValorMonedaValorenUSD,
			String inputNumeroDeclaracio, String inputNumeroDeposito, String numeralACambiar,
			String valorNumeralaCambiar, String valorDeclaracio, String valorDeposito,
			String inputValorMonedaEstipulada, String ValorMonedaEstipulada) throws Exception {

		String elementoSelecionado = "";
		String msg = "";

//		String validacionAdicionar = SettingsRun.getTestData().getParameter("Validar Numerales");

		if (contadorVa <= Integer.parseInt(validacionAdicionar)) {

			// Adicionar nuevos campos numeral
			this.click(By.xpath("//*[@id='addNewCardBtn']"));
		}

		Util.wait(2);

		// Dato numeral
		elementoSelecionado = ValidacionListaNumerales(By.id(listNumeralId));
		if (elementoSelecionado.equals("No se presento el campo Numeral")
				|| elementoSelecionado.contains("Error mensaje:")) {
			return elementoSelecionado;
		}

		if (!isValid(elementoSelecionado) || elementoSelecionado.equals("No hay ninguna opci�n seleccionada.")
				|| elementoSelecionado.equals("Seleccione un numeral")) {

			if (this.element(By.id(listNumeralId)) != null) {
				msg = this.selectListItemExacto(this.element(By.id(listNumeralId)), numeralACambiar);

				if (isValid(msg)) {
					return msg;
				}
			}

		} else {

			Reporter.reportEvent(Reporter.MIC_INFO, "Valor ya seleccionado: " + elementoSelecionado + "");
			msg = "Valor ya seleccionado: " + elementoSelecionado + "";

		}

		Util.wait(2);

		// Dato valor del numeral
		if (isElementInteractable(By.id(inputValorMonedaId))) {
			this.write(this.element(By.id(inputValorMonedaId)), String.valueOf(valorNumeralaCambiar).replace(".", ","));

		} else if (isElementInteractable(By.id(inputValorMonedaIdNegociacion))) {

			this.write(this.element(By.id(inputValorMonedaIdNegociacion)),
					String.valueOf(valorNumeralaCambiar).replace(".", ","));

		} else if (isElementInteractable(By.id(inputValorMonedaValorenUSD)))
			this.write(this.element(By.id(inputValorMonedaValorenUSD)),
					String.valueOf(valorNumeralaCambiar).replace(".", ","));

		// Dato Numero de Declaracion
		if (isElementInteractable(By.id(inputNumeroDeclaracio)))
			this.write(this.element(By.id(inputNumeroDeclaracio)), valorDeclaracio);

		// Dato Numero de Deposito
		if (isElementInteractable(By.id(inputNumeroDeposito)))
			this.write(this.element(By.id(inputNumeroDeposito)), valorDeposito);

		// Dato Valor moneda estipulada
		if (isElementInteractable(By.id(inputValorMonedaEstipulada)))
			this.write(this.element(By.id(inputValorMonedaEstipulada)), ValorMonedaEstipulada);

		return msg;

	}

// ===========================================[ValidacionDeduciones]===========================================================================

	public void ValidacionDeduciones(String numeroDeposito, String numeroFacturaoReferDeclaracion,
			String valordeduciones, String valorTx) throws Exception {

		// Numero de deposito
		Util.wait(3);
		if (isElementInteractable(inputOperacion_NumeroDeposito))
			this.write(inputOperacion_NumeroDeposito, numeroDeposito);

		if (isElementInteractable(inputNumeroDeclaracion)) {

			if (!isValid(numeroFacturaoReferDeclaracion)) {
				Evidence.save("NumeroDeclaracion",this);
				Reporter.reportEvent(Reporter.MIC_INFO, "Celda de Numero Declaracion vacio");

			} else {

				this.write(inputNumeroDeclaracion, numeroFacturaoReferDeclaracion);
			}

			Evidence.save("NumeroDeclaracion",this);

		} else {

			Reporter.reportEvent(Reporter.MIC_INFO, "No aparecio el campo Declaracion");
		}

		if (isElementInteractable(inputnumeralExtendidoDeducciones)) {

			if (isValid(valordeduciones)) {

				Evidence.save("Numeral Declaracion",this);
				Reporter.reportEvent(Reporter.MIC_INFO, "Celda de Deducciones vacio");

			} else {

				// Interatua priemro en el campo label
				this.mouseOver(labelDeduciones);
				this.mouseClick();
				this.mouseClick();

				// Realiza la acion tap para selecionar el campo de deduciones
				BonotesTecla("TAB");
				Util.wait(4);

				// Escribe el valor de deducion
				StringNumerosTeclado(valordeduciones);
				Util.wait(4);
				// espera que se ingresen los datos

				// Interatua nuevamente en el campo label
				this.mouseOver(labelDeduciones);
				this.mouseClick();

				// Espera la alerta si esiste
				String msg = this.closeActiveIntAlert();

				if (isValid(msg)) {
					Reporter.reportEvent(Reporter.MIC_INFO, msg);
				}

				double valorenUSD_Precargado = Double
						.parseDouble(this.getText(inputValorenUSD).replace(".", "").replace(",", "."));
				double valorDeducionesdata = Double.parseDouble(valordeduciones.replace(".", "").replace(",", "."));
				double valortx = Double.parseDouble(valorTx.replace(".", "").replace(",", "."));

				if (valortx == valorenUSD_Precargado) {
					Reporter.reportEvent(Reporter.MIC_PASS,
							"El valor de la Moneda corresponde con el Cargado en el portal: " + valorenUSD_Precargado);
				}

				double deducionesS = (valorDeducionesdata + valorenUSD_Precargado);

				this.write(inputValorenUSD, String.valueOf(deducionesS).replace(".", ","));

				Reporter.reportEvent(Reporter.MIC_PASS,
						"Se agrega el valor de las deduciones en el valor de la moneda: " + valorenUSD_Precargado);
			}

			Evidence.save("Numeral ExtendidoDeducciones",this);

		} else {

			Reporter.reportEvent(Reporter.MIC_INFO, "No aparecio el campo Deducciones");
		}
	}

// ===========================================[ValidacionDocumentosDIAN]===========================================================================

	/**
	 * Adiciona informacion de la Dian
	 * 
	 * @param num
	 * @throws Exception
	 */
	public void ValidacionDocumentosDIAN(int num, String validacionDian) throws Exception {

		int contadorVa = 1;
		for (int i = 0; i <= num; i++) {
			String listDeclaraci�n = "";
			String ValorDeclaraci�nUSD = "";
			contadorVa++;

			if (SettingsRun.getTestData().parameterExist("Declaraci�n de importaci�n No " + (1 + i)))
				listDeclaraci�n = SettingsRun.getTestData().getParameter("Declaraci�n de importaci�n No " + (1 + i));

			if (SettingsRun.getTestData().parameterExist("Valor en USD - DIAN " + (1 + i)))
				ValorDeclaraci�nUSD = SettingsRun.getTestData().getParameter("Valor en USD - DIAN " + (i + 1));

			// locator Lista Numeral y valor Numral
			String listDeclaraci�ndeimportaci�nNo = inputlisDeclaraciondeimportacionNo.replace("NUM",
					Integer.toString(i));
			String inputValorenUSB = inputValorUSD.replace("NUM", Integer.toString(i));

			String result = AdicionarDocDIAN(listDeclaraci�ndeimportaci�nNo, inputValorenUSB, listDeclaraci�n,
					ValorDeclaraci�nUSD);

			if (isValid(result)) {
				Reporter.reportEvent(Reporter.MIC_INFO, "Error en la iteraci�n " + i + ": " + result);
				break; // Detener el ciclo si hay un error
			}

//			String validacionDian = SettingsRun.getTestData().getParameter("Validacion - DIAN");
			// Adicionar nuevos campos numeral
			if (contadorVa <= Integer.parseInt(validacionDian)) {
				this.click(By.xpath("//*[@id='addBtn']"));
			}

		}
	}

// ===========================================[AdicionarDocDIAN]===========================================================================

	public String AdicionarDocDIAN(String listDeclaraci�ndeimportaci�nNo, String inputValorenUSB,
			String importaCionNumero, String valorUSD) throws Exception {

		String msg = "";

		if (isElementInteractable(By.id(listDeclaraci�ndeimportaci�nNo))) {
			Reporter.reportEvent(Reporter.MIC_INFO, "Informaci�n documentos de importaci�n - DIAN");
//			this.write(this.element(listDeclaraci�ndeimportaci�nNo), String.valueOf(Util.enteroRandom(1, 100)));
			this.write(By.id(listDeclaraci�ndeimportaci�nNo), importaCionNumero);

			if (this.element(By.id(inputValorenUSB)) != null && this.isDisplayed(By.id(inputValorenUSB))) {
				this.write(By.id(inputValorenUSB), valorUSD);
			}
		} else {
			msg = "No se agrego informacion";
		}
		return msg;

	}

// ===========================================[CargaArchivos]===========================================================================

	/**
	 * Realizar el cargue de Archivos en el Modulo Documen_Y_Formularios
	 * 
	 * @param cargarArchivo
	 * @return
	 * @throws Exception
	 */
	public String CargaArchivos(String... cargarArchivo) throws Exception {
		contador = 0;
		do {
			contador++;
			Util.wait(3);
			if (contador >= 30) {

				this.getDriver().switchTo().defaultContent();
				return "No se encuentra el boton de cargar archivo";

			}
		} while (this.element(btnSeleccioneunarchivo) == null && this.isDisplayed(btnSeleccioneunarchivo) == false);

		contador = 0;
		String msg = null;

		for (String archivo : cargarArchivo) {
			// el campo esta vacio da clic al boton siguiente
			Evidence.save("Cargue archivo",this);
			contador++;
			if (contador != 1) {
				Util.wait(1);
			}
			this.focus(btnSeleccioneunarchivo);
			this.mouseOver(btnSeleccioneunarchivo);
			this.mouseClick();
			String scriptClicIngresar1 = "document.querySelector(\"#uploadBtn\").click()";
			this.getJse().executeScript(scriptClicIngresar1);
			Util.wait(3);
			cargueArchivo(archivo);

			WebElement webArchtrue = null;
			contador = 0;
			String nombreArch = "";
			do {
				Util.wait(1);
				msg = this.closeActiveIntAlert();
				if (isValid(msg)) {
					return msg;
				}
				String[] rutaArch = archivo.split("\\\\");
				nombreArch = rutaArch[rutaArch.length - 1];
				webArchtrue = this.element(archivoCargado.replace("NOMARCH", nombreArch));
				contador++;
			} while (webArchtrue == null && !this.isDisplayed(webArchtrue) && contador <= 29);
			Reporter.reportEvent(Reporter.MIC_PASS, "Se carga documento: " + nombreArch);
		}
		return msg;
	}

// ===========================================[obtenerTabla]===========================================================================

	// M�todo auxiliar para verificar y obtener la tabla
	private WebElement obtenerTabla(String identificador, String reemplazo) {
		WebElement tabla = this.element(identificador.replace("TABLENUM", reemplazo));
		if (tabla != null && tabla.isDisplayed()) {
			return tabla;
		}
		return null;
	}

// ===========================================[getTipoIdentificai�n]===========================================================================

	private String getTipoCuenta(String tipoIdentifiacionComp) {

		String tipoIdentificacion = tipoIdentifiacionComp;

		if (tipoIdentifiacionComp.equals("C�DULA DE CIUDADAN�A")) {
			tipoIdentificacion = "CC";
		} else if (tipoIdentifiacionComp.equals("C�DULA DE EXTRANJER�A")) {
			tipoIdentificacion = "CE";
		} else if (tipoIdentifiacionComp.equals("NIT")) {
			tipoIdentificacion = "NI";
		} else if (tipoIdentifiacionComp.equals("RECEPTOR EN CONSTITUCI�N")) {
			tipoIdentificacion = "REC";
		} else if (tipoIdentifiacionComp.equals("PATRIMONIO AUT�NOMO")) {
			tipoIdentificacion = "PAC";
		} else if (tipoIdentifiacionComp.equals("PASAPORTE")) {
			tipoIdentificacion = "PB";
		} else if (tipoIdentifiacionComp.equals("NO RESIDENTE")) {
			tipoIdentificacion = "NR";
		}

		return tipoIdentificacion;
	}
	// ===========================================[Sector]===========================================================================

	private String getSector(String sector) {
		String tipoIdentificacion = sector;
		if (sector.equals("P�blico")) {
			tipoIdentificacion = "PU";
		} else if (sector.equals("Privado")) {
			tipoIdentificacion = "PR";
		} else if (sector.equals("Mixto")) {
			tipoIdentificacion = "MX";
		}
		return tipoIdentificacion;
	}

	// ===========================================[Superintendencia]===========================================================================

	private String getSuperintendencia(String sector) {
		String sectorDeVigilancia = sector;
		if (sector.equals("1. Superintendencia de Sociedades")) {
			sectorDeVigilancia = "1";
		} else if (sector.equals("2. Superintendencia Financiera de Colombia")) {
			sectorDeVigilancia = "2";
		} else if (sector.equals("3. Superintendencia de Puertos y Transporte")) {
			sectorDeVigilancia = "3";
		} else if (sector.equals("4. Superintendencia de la Econom�a Solidaria")) {
			sectorDeVigilancia = "4";
		} else if (sector.equals("5. Superintendencia de Vigilancia y Seguridad Privada")) {
			sectorDeVigilancia = "5";
		} else if (sector.equals("6. Superintendencia de Industria y Comercio")) {
			sectorDeVigilancia = "6";
		} else if (sector.equals("7. Superintendencia del Subsidio Familiar")) {
			sectorDeVigilancia = "7";
		} else if (sector.equals("8. Superintendencia Nacional de Salud")) {
			sectorDeVigilancia = "8";
		} else if (sector.equals("9. Superintendencia de Servicios P�blicos Domiciliarios")) {
			sectorDeVigilancia = "9";
		}
		return sectorDeVigilancia;
	}

	// ===========================================[TipoEmpresa]===========================================================================

	private String getTipoEmpresa(String sector) {
		String sectorDeVigilancia = sector;
		if (sector.equals("1. Sociedades / Empresas unipersonales")) {
			sectorDeVigilancia = "1";
		} else if (sector.equals("2. Entidades de naturaleza cooperativa")) {
			sectorDeVigilancia = "2";
		} else if (sector.equals("3. Entidad sin �nimo de lucro")) {
			sectorDeVigilancia = "3";
		} else if (sector.equals("4. Entidades gubernamentales del orden nacional o territorial")) {
			sectorDeVigilancia = "4";
		}
		return sectorDeVigilancia;
	}
}
