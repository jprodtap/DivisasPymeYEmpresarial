package lauchTest;

import library.core.BasePageWeb;
import library.core.Controller;
import library.reporting.Evidence;
import library.reporting.Reporter;
import library.settings.SettingsRun;
import library.reporting.Reporter;
import library.common.Util;
import pages.actions.Divisas.PageAprobacionInter;
import pages.actions.Divisas.PageConsultatxInternacional;
import pages.actions.Divisas.PageDivisas;
import pages.actions.Divisas.PageDocumentos_Y_Formularios;
import pages.actions.Divisas.PageEnviarTransInternacional;
import pages.actions.Divisas.PageRecibirTransferenciasInternacionales;
import pages.actions.FrontEmpresarial.CommonFrontEmpresarial;
import pages.actions.FrontEmpresarial.PageConsultaProductosFrontEmpresarial;
import pages.actions.FrontEmpresarial.PageFrontEmpresarial;
import pages.actions.FrontEmpresarial.PageLoginFrontEmpresarial;
import pages.actios.Pyme.PageActualizacionDeDatos1;
import pages.actios.Pyme.PageAdminParametros1;
import pages.actios.Pyme.PageConfirmacion1;
import pages.actios.Pyme.PageLoginPymes1;
import pages.actios.Pyme.PageOrigen1;

import java.io.File;

import dav.transversal.DatosDavivienda;
import dav.transversal.DatosEmpresarial;
import dav.transversal.MotorRiesgo;
import dav.transversal.NotificacionSMS;
import dxc.util.DXCUtil;

public class ControllerGeneralDivisas implements Controller {

	// ***********************************************************************************************************************
	// * Constructor - Implementacion Patron Singleton *
	// ***********************************************************************************************************************

	private static ControllerGeneralDivisas instanciaUnicaControllerGeneralDivisas; // Variable en donde se Almacena la
																					// Instancia Unica de la Clase.

	// ***********************************************************************************************************************

	/**
	 * 
	 * Metodo getInstanciaUnicaControllerFrontEmpresarial: Su Proposito es realizar
	 * la creacion del Objeto una unica vez, a la vez de retornar la Instancia Unica
	 * de dicho Objeto (Patron Singleton).
	 * 
	 * @return instanciaUnicaControllerFrontEmpresarial - ControllerFrontEmpresarial
	 *         - Instancia Unica de la Clase.
	 * 
	 * @date 13/12/2023
	 * 
	 * @author DAARUBIO
	 * 
	 */

	public static ControllerGeneralDivisas getInstanciaUnicaControllerGeneralDivisas() {

		if (instanciaUnicaControllerGeneralDivisas == null)
			instanciaUnicaControllerGeneralDivisas = new ControllerGeneralDivisas();

		return instanciaUnicaControllerGeneralDivisas;

	}

	// ***********************************************************************************************************************

	/**
	 * 
	 * Metodo inicializarInstanciaUnicaControllerFrontEmpresarial: Su Proposito es
	 * Inicializar la Instancia Unica de la Clase.
	 * 
	 * @date 22/03/2024
	 * 
	 * @author DAARUBIO
	 * 
	 */

	public static void inicializarInstanciaUnicaControllerFrontEmpresarial() {

		instanciaUnicaControllerGeneralDivisas = null;

	}
	// ***********************************************************************************************************************
	// * Instancias. *
	// ***********************************************************************************************************************

	// Pyme
	PageLoginFrontEmpresarial loginFrontEmpresarial = null;
	PageLoginPymes1 loginFrontPyme = null;
	PageOrigen1 pageOrigen = null;
	PageAdminParametros1 pageAdminParametros = null;
	PageActualizacionDeDatos1 pageActualizacionDeDatos = null;
	PageConfirmacion1 pageConf;

	// Empresarial
	PageFrontEmpresarial frontEmpresarial = null;
	PageConsultaProductosFrontEmpresarial consultaProductosFrontEmpresarial = null;

	// Divisas
	PageDivisas pageDivisas = null;
	PageEnviarTransInternacional pageEnviarTransInternacional = null;
	PageRecibirTransferenciasInternacionales pageRecibirTransferenciasInternacionales = null;
	PageDocumentos_Y_Formularios pageDocumentos_Y_Formularios = null;
	PageConsultatxInternacional pageConsultatxInternacional = null;
	PageAprobacionInter pageAprobInter = null;

	// ===========================================================================================================================================

	// ===========================================================================================================================================
	public static String numAprobaciones = null; // Número de Firmas a Probar la TX
	boolean unaFirma;

	String servicio; // Servicio o TX a realizar

	public static String tipoPrueba = null; // Tipo de Tx si es en [Login - Tx Linea - Pendiente de transacción -
											// Eliminar Tx]

	String pageTitle;

	String descripcionsin;
	String valorsin, valorcon;

	String referenciasin, referenciacon;

	String numerodereferenciaExterna;// Numero de Referencia Externa Recibir Divisas

	String riesgo, prioridaRiesgo, userAgent;// Datos Motor de riesgo
	public static String[] cuentasDesMotor = null, datoNumDestCuentas = null;// Datos Motor de riesgo

	public static String numeroTx = null; // Número O Documento de la Transacción [TX]

	String valorTx;
	String bancoDesTx;
	String estadoTx;
	String montoReal;

	// -----------------------------------------------------------------------------------------------------------------------

	// Ayuda a identificar la cantidad de destinos transados
	String cantidadTxPorTipoDestino = null;

	int incCuentasDavivienda = 0;
	int incCuentasOtrosBancos = 0;
	int incTarjetaPrepago = 0;
	int incDaviplata = 0;
	int incDepositosElectronicos = 0;
	int incPagoServicios = 0;
	int totalDestinos;

	public static int[] contadorTx = null;

	// -----------------------------------------------------------------------------------------------------------------------

	public static String navegador = null,
//				numAprova = null, 
			activityAmount = null, bancoDesMotor = null, riesgoBc = null, riesgoEfm = null, estado = null,
			diaDiaDelpago = null, nombreEmpre = null, nitEmpre = null, uuid = null, tipoIdEm = null,
			desdeElDetalle = null, adenda = null;

	// -----------------------------------------------[Transacional y
	// pagos]------------------------------------------------------------------------

	// Cuenta Origen y destino
	static String numCta = "", tipoCta = "", referencia1 = "", referencia2 = "";

	// DIVISAS
	static String tipoMoneda = null, modena = null, concepTx = null, concepto = null, valorNumeral1 = null,
			valorNumeral2 = null, tipoEnvio = null, FechaEnvioFrecuente = null, nombreBene = null, paisDestino = null,
			ciudadDestino = null, direccionBene = null, cuentaBene = null, infoPago = null, referenciaPago = null,
			tipoCodigo = null, numeroCodigo = null, intermediario = null, tipoCodigoInter = null,
			numeroCodigoInter = null;

	// Consuta Comprobantes

	static String tipoConstaTxRealizadas = null, fecha = null, hora = null, moneda = null;

	// -----------------------------------------------------------------------------------------------------------------------

	private String[] arrMsgBuscarEstado = { PageConfirmacion1.MSG_EXITO_APROB, PageConfirmacion1.MSG_EXITO_PAGO,
			PageConfirmacion1.MSG_EXITO_ENV_OTP, PageConfirmacion1.MSG_EXITO_GUARD,
			PageConfirmacion1.MSG_EXITO_APROB_INTER, };

	private String[] arrMsgTxDeclinada = { PageConfirmacion1.MSG_EXITO_DECL1, PageConfirmacion1.MSG_EXITO_DECL2,
			PageConfirmacion1.MSG_EXITO_DECL3, PageConfirmacion1.MSG_EXITO_DECL5, };

	private String[] arrEstadosTxAprob = { "Pagado", "Pago Realizado", "Transferencia Realizada",
			"Pendiente Respuesta de Otros Bancos", "Pagado Parcial" };

	String transaccion = MotorRiesgo.TX_EMP_LOGIN_SUCC; // VALOR POR DEFECTO

	// -----------------------------------------------------------------------------------------------------------------------

	final String TP_LOGIN = "Login";
	final String TP_EN_LINEA = "Tx En Línea";
	final String TP_PEND_APR = "Tx Pend Aprobación";

	// ***********************************************************************************************************************
	/**
	 * 
	 * Metodo validarReferenciaODescripcionEnMovimientoFrontEmpresarial
	 * 
	 * @date 24/11/2023
	 * 
	 * @author DAARUBIO
	 */

	public void validarReferenciaODescripcionEnMovimientoFrontEmpresarial() throws Exception {

		String msgError = null;

		// Datos Globales
		this.servicio = SettingsRun.getTestData().getParameter("Servicio").trim();
		this.tipoPrueba = SettingsRun.getTestData().getParameter("Tipo prueba").trim();
		adenda = SettingsRun.getTestData().getParameter("Adenda").trim();
		this.numAprobaciones = SettingsRun.getTestData().getParameter("Números de Aprobaciones").trim();
		this.unaFirma = this.numAprobaciones.equals("1");

		riesgoBc = SettingsRun.getTestData().getParameter("Nivel de Riesgo BC").trim();
		riesgoEfm = SettingsRun.getTestData().getParameter("Nivel de Riesgo SAS EFM").trim();

		navegador = SettingsRun.getTestData().getParameter("Navegador").trim();

		this.nombreEmpre = SettingsRun.getTestData().getParameter("Nombre Empresa").trim();

		this.nitEmpre = SettingsRun.getTestData().getParameter("Numero ID Empresa").trim();
		this.tipoIdEm = SettingsRun.getTestData().getParameter("Tipo ID Empresa");

		this.tipoCta = SettingsRun.getTestData().getParameter("Tipo producto origen / Franquicia").trim();
		this.numCta = SettingsRun.getTestData().getParameter("Número producto origen").trim();

		this.uuid = SettingsRun.getTestData().getParameter("Hash").trim();

		this.descripcionsin = SettingsRun.getTestData().getParameter("Descripcion").trim();

		this.valorsin = SettingsRun.getTestData().getParameter("Valor a Pagar / Transferir").trim();
		this.valorcon = SettingsRun.getTestData().getParameter("Valor a Pagar / Transferir");
		this.referenciasin = SettingsRun.getTestData().getParameter("Referencia").trim();
		this.desdeElDetalle = SettingsRun.getTestData().getParameter("Desde_el_Detalle").trim();

		// INGRESO DE LA REFERENCIA
		if (!this.servicio.contains("Nómi") && !this.servicio.contains("Proveedores")) {
			this.referencia1 = SettingsRun.getTestData().getParameter("Referencia1 / Tipo Producto Destino").trim();
			this.referencia2 = SettingsRun.getTestData().getParameter("Referencia2 / Número Producto Destino").trim();

		}

		// Divisas
		if (this.servicio.contains("Tx Internacionales Recibir desde el exterior")
				|| this.servicio.contains("Tx Internacionales Enviar al exterior")) {
			this.numerodereferenciaExterna = SettingsRun.getTestData().getParameter("Número de referencia Externa")
					.trim();

			this.tipoMoneda = SettingsRun.getTestData().getParameter("Tipo Moneda");
			this.modena = SettingsRun.getTestData().getParameter("Tipo Moneda").trim();
			this.concepTx = SettingsRun.getTestData().getParameter("Concepto de la transferencia").trim();
			this.concepto = SettingsRun.getTestData().getParameter("Concepto de la transferencia");
			this.valorNumeral1 = SettingsRun.getTestData().getParameter("Valor numeral cambiario 1");
			this.valorNumeral2 = SettingsRun.getTestData().getParameter("Valor numeral cambiario 2");
			this.tipoEnvio = SettingsRun.getTestData().getParameter("Tipo de Envio");
			this.FechaEnvioFrecuente = SettingsRun.getTestData().getParameter("Fecha envío frecuente");
			this.nombreBene = SettingsRun.getTestData()
					.getParameter("Ordenante / Nombre del beneficiario en el exterior");
			this.paisDestino = SettingsRun.getTestData().getParameter("País de destino de la transferencia");
			this.ciudadDestino = SettingsRun.getTestData()
					.getParameter("Ciudad y país donde está ubicado el beneficiario");
			this.direccionBene = SettingsRun.getTestData().getParameter("Dirección del beneficiario");
			this.cuentaBene = SettingsRun.getTestData().getParameter("Número de cuenta, IBAN o CLABE");
			this.infoPago = SettingsRun.getTestData().getParameter("Información para el beneficiario");
			this.referenciaPago = SettingsRun.getTestData().getParameter("Información para el beneficiario Numero");
			this.tipoCodigo = SettingsRun.getTestData().getParameter("Tipo de código");
			this.numeroCodigo = SettingsRun.getTestData().getParameter("Número de código");
			this.intermediario = SettingsRun.getTestData().getParameter("Requiere un banco intermediario");
			this.tipoCodigoInter = SettingsRun.getTestData().getParameter("Tipo de código Intermediario");
			this.numeroCodigoInter = SettingsRun.getTestData().getParameter("Número de código Intermediario");

		}

		// Consulta Comprobantes
		this.tipoConstaTxRealizadas = SettingsRun.getTestData().getParameter("Tiempo de Consulta");
		this.fecha = SettingsRun.getTestData().getParameter("Fecha tx");
		this.hora = SettingsRun.getTestData().getParameter("Hora tx");
		this.moneda = SettingsRun.getTestData().getParameter("Tipo Moneda").trim();

		this.creacionObjetos();

//		msgError = this.realizarLoginFrontEmpresarial();
//
//		if (msgError != null)
//			return;
//
//		msgError = this.seleccionarEmpresaFrontEmpresarial();
//
//		if (msgError != null)
//			return;
//
//		msgError = this.seleccionarTransferenciasInternacionales();
//		if (msgError != null)
//			return;

		EnviarTransferenciasInternacionales(false);
	}

	// ***********************************************************************************************************************
	/**
	 * 
	 * Metodo creacionObjetos: Su proposito es crear los objetos correspondientes a
	 * las Clases, para acceder a sus atributus y metodos.
	 * 
	 * @date 23/04/2024
	 * 
	 * @author DAARUBIO
	 * 
	 * @throws Exception
	 */

	private void creacionObjetos() throws Exception {

		PageFrontEmpresarial.instanciaGlobalLocatorsFrontEmpresarial = null;

		String evidenceDir = SettingsRun.RESULT_DIR + File.separator + "Temp";

		loginFrontEmpresarial = new PageLoginFrontEmpresarial(BasePageWeb.CHROME, evidenceDir);

		frontEmpresarial = new PageFrontEmpresarial(loginFrontEmpresarial);

		pageDivisas = new PageDivisas(frontEmpresarial);

		pageEnviarTransInternacional = new PageEnviarTransInternacional(frontEmpresarial);

		pageRecibirTransferenciasInternacionales = new PageRecibirTransferenciasInternacionales(frontEmpresarial);

		pageDocumentos_Y_Formularios = new PageDocumentos_Y_Formularios(frontEmpresarial);

		pageConf = new PageConfirmacion1(frontEmpresarial);

		pageConsultatxInternacional = new PageConsultatxInternacional(frontEmpresarial);
		pageAprobInter = new PageAprobacionInter(frontEmpresarial);

		realizarLoginFrontPyme();
		this.pageDivisas = new PageDivisas(loginFrontPyme);

		pageDivisas = new PageDivisas(loginFrontPyme);

		pageEnviarTransInternacional = new PageEnviarTransInternacional(loginFrontPyme);

		pageRecibirTransferenciasInternacionales = new PageRecibirTransferenciasInternacionales(loginFrontPyme);

		pageDocumentos_Y_Formularios = new PageDocumentos_Y_Formularios(loginFrontPyme);

		pageConf = new PageConfirmacion1(loginFrontPyme);

		pageConsultatxInternacional = new PageConsultatxInternacional(loginFrontPyme);
		pageAprobInter = new PageAprobacionInter(loginFrontPyme);

	}

	// ***********************************************************************************************************************

	/**
	 * 
	 * Metodo realizarLoginFrontEmpresarial: Su proposito es realizar Login en el
	 * Front Empresarial, teniendo en cuenta los diferentes tipos de Autenticacion
	 * como lo son Token (Estatica y Fisica) y Clave Virtual junto a la siguiente
	 * Logica: <br>
	 * 
	 * <br>
	 * * Si el Login es Exitoso = ESTA_LOGUEADO = true. <br>
	 * 
	 * <br>
	 * * Si el Login No es Exitoso = ESTA_LOGUEADO = false. <br>
	 * 
	 * @param aplicaParaDataNotaCredito - boolean - true, si la Data corresponde a
	 *                                  la Data Nota Credito.
	 * 
	 * @return msgError - String - null (Flujo Ok) o != null (Flujo Fallido).
	 * 
	 * @date 29/01/2024
	 * 
	 * @author DAARUBIO
	 */

	private String realizarLoginFrontEmpresarial() throws Exception {

		String msgError = null;

		String numeroClienteEmpresarial = null, tipoIdentificacionClienteEmpresarial = null,
				numeroIdentificacionClienteEmpresarial = null, tipoAutenticacion = null, clavePersonal = null,
				tokenEmpClaveVirtual = null;
		// ------------------------------------------------------------------------------------------------------------------------
		// Alista Data para Login
		numeroClienteEmpresarial = SettingsRun.getTestData().getParameter("Cliente Empresarial").trim();
		tipoIdentificacionClienteEmpresarial = SettingsRun.getTestData().getParameter("Tipo Identificación").trim();
		numeroIdentificacionClienteEmpresarial = SettingsRun.getTestData().getParameter("Id usuario").trim();
		String tipoToken = SettingsRun.getTestData().getParameter("Tipo Token").trim();

		if (tipoToken.equals("ESTATICO")) {
			tipoToken = "Token Estatica";
		} else if (tipoToken.equals("FISICO")) {
			tipoToken = "Token Fisica";
		} else if (tipoToken.equals("OTP")) {
			tipoToken = "Clave Virtual";
		}

		tipoAutenticacion = tipoToken;
		clavePersonal = SettingsRun.getTestData().getParameter("Clave personal o CVE").trim();
		tokenEmpClaveVirtual = SettingsRun.getTestData().getParameter("Semilla / Valor Estático / Celular").trim();
		// ------------------------------------------------------------------------------------------------------------------------
		// Realiza el Login en el Front Empresarial.
		msgError = loginFrontEmpresarial.realizarLogin(numeroClienteEmpresarial, tipoIdentificacionClienteEmpresarial,
				numeroIdentificacionClienteEmpresarial, tipoAutenticacion, clavePersonal, tokenEmpClaveVirtual);
		DatosEmpresarial.loadLoginData("Cliente Empresarial", "Tipo Identificación", "Id usuario",
				"Clave personal o CVE", "Tipo Token", "Semilla / Valor Estático / Celular");
		// ------------------------------------------------------------------------------------------------------------------------
		// Login No Exitoso, termina la iteracion actual, genera el Alertamiento
		// Correspondiente y ESTA_LOGUEADO = false.

		if (msgError != null) {
			CommonFrontEmpresarial.ESTA_LOGUEADO = false;
			Reporter.reportEvent(Reporter.MIC_NOT_COMPLETED, msgError);
			frontEmpresarial.cerrarNavegador();
			// -----------------------------------------------------------------------------------------------------------------------
			// Flujo de Logueo en el Front Empresarial Exitoso, ESTA_LOGUEADO = true.

		} else
			CommonFrontEmpresarial.ESTA_LOGUEADO = true;
		// -----------------------------------------------------------------------------------------------------------------------
		// Retorna msgError. Si es null (Flujo Ok) o != null (Flujo Fallido).
		return msgError;
	}
	// ***********************************************************************************************************************

	/**
	 * 
	 * Metodo seleccionarEmpresaFrontEmpresarial: Su proposito es realizar la
	 * seleccion de Empresa Deseada.
	 * 
	 * @param aplicaParaDataNotaCredito - boolean - true, si la Data corresponde a
	 *                                  la Data Nota Credito.
	 * 
	 * @return msgError - String - null (Flujo Ok) o != null (Flujo Fallido).
	 * 
	 * @date 15/11/2023
	 * 
	 * @author DAARUBIO
	 */

	private String seleccionarEmpresaFrontEmpresarial() throws Exception {

		String msgError = null;

		String empresa = null;
		// ------------------------------------------------------------------------------------------------------------------------
		// Alista Data dependiendo del Escenario (Data Empresa o Data Empresa Nota
		// Credito).

//		empresa = SettingsRun.getTestData().getParameter("Numero ID Empresa");
		empresa = SettingsRun.getTestData().getParameter("Nombre Empresa");
		// ------------------------------------------------------------------------------------------------------------------------
		// Realiza el Flujo de Seleccionde Empresa en Front Empresarial.

		msgError = frontEmpresarial.seleccionarEmpresa(empresa);
		// ------------------------------------------------------------------------------------------------------------------------
		// Si el flujo no fue exitoso, Genera Alertamiento y Cierra Sesion.

		if (msgError != null) {
			Reporter.reportEvent(Reporter.MIC_NOT_COMPLETED, "Empresa: [" + empresa + "] No Encontrada");
			frontEmpresarial.cerrarSesionFrontEmpresarial();
			CommonFrontEmpresarial.ESTA_LOGUEADO = false;
		}
		// -----------------------------------------------------------------------------------------------------------------------
		// Retorna msgError. Si es null (Flujo Ok) o != null (Flujo Fallido).
		return msgError;
	}

	public String seleccionarTransferenciasInternacionales() throws Exception {
		String msgError = null;
		msgError = pageDivisas.seleDivisasEmpresarial("Transferencias Internacionales");
		if (msgError != null) {
			Reporter.reportEvent(Reporter.MIC_NOT_COMPLETED,
					"Modulo de Servicio: [Transferencias Internacionales] No Encontrada");
			frontEmpresarial.cerrarSesionFrontEmpresarial();
			CommonFrontEmpresarial.ESTA_LOGUEADO = false;
		}
		// Retorna msgError. Si es null (Flujo Ok) o != null (Flujo Fallido).
		return msgError;
	}

	
	// Variables para guardar los últimos valores
		private String lastNumAprobaciones = "";
		private String lastTipoAbono = "";
		private String lastCtaInscrita = "";
		private String lastIdusuario = "";
		private String lastempresa = "";
	
	private String realizarLoginFrontPyme() throws Exception {

		String msgError = null;
		// Datos Login front Login, estos datos se encuentran el archivo del carge DATA
		DatosEmpresarial.loadLoginData("Cliente Empresarial", "Tipo Identificación", "Id usuario",
				"Clave personal o CVE", "Tipo Token", "Semilla / Valor Estático / Celular");

		String[] datosLogin = DatosEmpresarial.getLoginData();

		Reporter.reportEvent(Reporter.MIC_INFO, "*** Navegador: []");
		Reporter.reportEvent(Reporter.MIC_INFO,
				"*** Datos de Logueo Front: [" + Util.arrayToString(datosLogin, " - ") + "]");

		String nombreAmbiente = SettingsRun.getGlobalData("data.ambienteFrontPyme", "PROYECTOS_NUBE");

		switch (nombreAmbiente) {
		case "1":
		case "PROYECTOS":
			nombreAmbiente = "PROYECTOS";
			break;
		case "2":
		case "CONTENCION":
			nombreAmbiente = "CONTENCION";
			break;
		case "3":
		case "OBSOLESCENCIA":
			nombreAmbiente = "OBSOLESCENCIA";
			break;
		case "4":
		case "ONPREMISE":
			nombreAmbiente = "ONPREMISE";
			break;
		case "5":
		case "POST_NUBE":
			nombreAmbiente = "POST_NUBE";
			break;
		case "6":
		case "CONTENCION_NUBE":
			nombreAmbiente = "CONTENCION_NUBE";
			break;
		case "7":
		case "PROYECTOS_NUBE":
			nombreAmbiente = "PROYECTOS_NUBE";
			break;
		case "8":
		case "MEJORAS":
			nombreAmbiente = "MEJORAS";
			break;
		default:
			Reporter.reportEvent(Reporter.MIC_FAIL, "Opción no válida");
			break;
		}

		if (nombreAmbiente.isEmpty()) {
			Reporter.reportEvent(Reporter.MIC_FAIL, "Nombre del ambiente seleccionado: Portal - " + nombreAmbiente);
		} else {
			Reporter.reportEvent(Reporter.MIC_HEADER, "Nombre del ambiente seleccionado: Portal - " + nombreAmbiente);
		}

		DatosEmpresarial.AMBIENTE_TEST = nombreAmbiente;
		String evidenceDir = SettingsRun.RESULT_DIR + File.separator + "Temp";
		loginFrontPyme = new PageLoginPymes1(BasePageWeb.CHROME, evidenceDir);

		DatosEmpresarial.loadLoginData("Cliente Empresarial", "Tipo Identificación", "Id usuario",
				"Clave personal o CVE", "Tipo Token", "Semilla / Valor Estático / Celular");
		loginFrontPyme.loginFront();
		loginFrontPyme.selecionambienteClose("NO"); // Indicativo para el ambiente Front// Marca si esta en el Ambiente
													// Middle o FRONT

		boolean isWindowOpened = loginFrontPyme.WaitForNumberOfWindos();

		if (isWindowOpened) {
			Reporter.reportEvent(Reporter.MIC_PASS, "La ventana emergente se abrió correctamente");
		} else {
			Reporter.reportEvent(Reporter.MIC_FAIL, "No se abrió La ventana emergente");
			loginFrontPyme.terminarIteracion();
		}

		// Cierra la venta inicial
		loginFrontPyme.closeCurrentBrowser();

		// INTERATUA CON LA VENTANA EMERGENTE DE FRONT PYME LOGIN
		loginFrontPyme.changeWindow(loginFrontPyme.accedioAlPortal());
		loginFrontPyme.maximizeBrowser();
		this.pageOrigen = new PageOrigen1(loginFrontPyme);
		/*
		 * Intenta seleccionar la empresa. Retorna [null] si pudo hacer la selecci�n, en
		 * caso contrario retorna mensajede error.
		 */
		String empresa = SettingsRun.getTestData().getParameter("Nombre Empresa").trim();

		msgError = this.pageOrigen.seleccionarEmpresa(empresa);

		// SI ES NULL EL MENSAJE DE ALERTA, SIGUE CON LAS DEM�S VALIDACIONES
		if (msgError == null) {

//-----------------------------------------------------------------------------------------------------------------------

			// Datos requeridos para la configuracion de los datos generales

			String tipoAbono = SettingsRun.getTestData().getParameter("Tipo de abono").trim();
			String ctaInscrita = SettingsRun.getTestData().getParameter("Cuentas Inscriptas").trim();
			String numAprobaciones = SettingsRun.getTestData().getParameter("Números de Aprobaciones").trim();
			String Idusuario = SettingsRun.getTestData().getParameter("Id usuario").trim();
//-----------------------------------------------------------------------------------------------------------------------

			// Comparar con los valores anteriores
			if (!Idusuario.equals(lastIdusuario) || !empresa.equals(lastempresa)|| !numAprobaciones.equals(lastNumAprobaciones) || !tipoAbono.equals(lastTipoAbono)|| !ctaInscrita.equals(lastCtaInscrita)) {

			if (!this.servicio.equals("Divisas Documentos y Formularios")
					&& !this.servicio.equals("Consulta Tx Internacionales Enviar al exterior Validar Estado")) {
				// Realizar la configuraci�n si los valores son diferentes
				this.pageAdminParametros = new PageAdminParametros1(loginFrontPyme);
				// Este m�todo hace la configuraci�n en par�metros generales, y guarda la
				// evidencia.
				msgError = this.pageAdminParametros.hacerConfiguracion(numAprobaciones, tipoAbono, ctaInscrita);
			} else {
				msgError = "Divisas";
			}

			// Actualizar los valores
				lastNumAprobaciones = numAprobaciones;
				lastTipoAbono = tipoAbono;
				lastCtaInscrita = ctaInscrita;
				lastIdusuario = Idusuario;
				lastempresa = empresa;

			} else {
				// Omitir la configuraci�n si los valores son los mismos
				msgError = "Ya se configuro los Parámetros Generales";
			}

			String codigoCIIU = "";
			if (this.servicio.contains("Tx Internacionales Recibir desde el exterior")
					|| this.servicio.contains("Tx Internacionales Enviar al exterior")) {
				codigoCIIU = SettingsRun.getTestData().getParameter("Validar CIIU").trim();

				if (codigoCIIU.equals("SI")) {
					Util.wait(3);
					this.pageActualizacionDeDatos = new PageActualizacionDeDatos1(loginFrontPyme);

					msgError = this.pageActualizacionDeDatos.InicioActualizacionDatos(false);

					if (msgError != null && !msgError.equals("Se actualizaron exitosamente los datos de su empresa")) {
						msgError = this.pageActualizacionDeDatos.MsgAlertaActualizacionDatos();
						this.pageOrigen.terminarIteracion(Reporter.MIC_FAIL, msgError);
					} else {
						Reporter.reportEvent(Reporter.MIC_PASS, msgError);
					}
				}

			}

			// SI ES INDIFRENTE A NULL EL MENSAJE DE ALERTA, SIGUE CON LAS DEM�S
			// VALIDACIONES
			if (msgError != null) {

//-----------------------------------------------------------------------------------------------------------------------

				// Se encuentra logueado en el portal Pymes, empieza arealizar las TX.

			} else {
				Reporter.reportEvent(Reporter.MIC_FAIL, msgError);
				this.pageOrigen.terminarIteracion();

			}
		} else {
			Reporter.reportEvent(Reporter.MIC_FAIL, msgError);
			this.pageOrigen.terminarIteracion();
		}
		return null;
	}

	// ===========================================[EnviarTransferenciasInternacionales]===========================================================================
	/**
	 * Metodo se encarga de realizar el flujo completo de transacción de Enviar
	 * dinero al exterior.<b>
	 * 
	 * @throws Exception
	 */
	public void EnviarTransferenciasInternacionales(boolean soloGuardar) throws Exception {

		NotificacionSMS.TIPO_NOTIFICACION = NotificacionSMS.TIPO_NOTIF_PYMES_TRANS_ENVIADAS;

		// Pertenece al flujo de enviar trans internacionales*
		String msg = "";
		String numeral1 = Util.left(SettingsRun.getTestData().getParameter("Numeral cambiario 1"), 4);
		String numeral2 = Util.left(SettingsRun.getTestData().getParameter("Numeral cambiario 2"), 4);

		if (this.tipoCta.contains("AHORRO") || this.tipoCta.contains("Ahorro") || this.tipoCta.contains("ahorro")) {
			this.tipoCta = "CUENTA AHORROS";
		} else if (this.tipoCta.contains("CORRIENTE") || this.tipoCta.contains("Corriente")
				|| this.tipoCta.contains("corriente")) {
			this.tipoCta = "CUENTA CORRIENTE";
		}
		this.inicioCrearTx(); // DEJA LA PANTALLA [Pagos, Transferencias yotros] y seleciona el link del
								// servicio a realizar

		// Espera hasta que el iframe de divisas esté disponible
		if (this.pageEnviarTransInternacional.switchToFrameDivisas()) {

			msg = pageEnviarTransInternacional.seleccionarTransferencia("Enviar");// Se en carga de selecionar el modulo
																					// de Divisas

			if (msg != null && !msg.equals(""))
				this.terminarIteracionXError(msg);

			/**
			 * Metodo que retorna el mensaje de alerta si este existe en divisas primer
			 * mensaje Importante Para enviar o recibir transferencias cierra el mensaje
			 */

			msg = pageEnviarTransInternacional.closeActiveIntAlert();
			if (msg != null && !msg.equals("") && !msg.contains("Para enviar o recibir transferencias"))
				this.terminarIteracionXError(msg);

			// Seleciona la cuenta origen Inicial de la tx si es cuenta Ahorros o Corriente.
			msg = pageEnviarTransInternacional.seleccionarCuenta(this.servicio, this.tipoIdEm, this.nitEmpre,
					this.tipoCta, this.numCta);

			if (!msg.equals(""))
				this.terminarIteracionXError(msg);

			msg = pageEnviarTransInternacional.ingresarValores(this.tipoMoneda, this.valorcon, this.concepto, numeral1,
					numeral2, this.valorNumeral1, this.valorNumeral2, this.descripcionsin, this.tipoEnvio);

			if (msg != null && !msg.equals(""))
				this.terminarIteracionXError(msg);

			// Realiza el flujo Si es Nuevo Envio
			if (this.tipoEnvio.equals("Nuevo Envio")) {

				// Ingresa todos los datos del Beneficiario
				msg = pageEnviarTransInternacional.ingresarBeneficiario(this.nombreBene, this.paisDestino,
						this.ciudadDestino, this.direccionBene, this.cuentaBene, this.infoPago, this.referenciaPago,
						this.tipoEnvio);

				if (msg != null && !msg.equals(""))
					this.terminarIteracionXError(msg);

				msg = pageEnviarTransInternacional.datosDestino(this.tipoCodigo, this.numeroCodigo, this.intermediario,
						this.tipoCodigoInter, this.numeroCodigoInter);

				if (msg != null && !msg.equals(""))
					this.terminarIteracionXError(msg);

				// Realiza el flujo Si es Envio Frecuente
			} else if (this.tipoEnvio.equals("Envio Frecuente")) {

				// Seleciona el Beneficiario frecuente
				msg = pageEnviarTransInternacional.SeleccionarBeneficiario(this.nombreBene, this.paisDestino,
						this.cuentaBene, this.FechaEnvioFrecuente);

				if (msg != null && !msg.equals(""))
					this.terminarIteracionXError(msg);

				msg = pageEnviarTransInternacional.ValidarBeneficiario(this.nombreBene, this.paisDestino,
						this.ciudadDestino, this.direccionBene, this.cuentaBene, this.infoPago, this.referenciaPago);
				if (msg != null && !msg.equals(""))
					this.terminarIteracionXError(msg);

				msg = pageEnviarTransInternacional.ValidaciondatosSelecDestino(this.tipoCodigo, this.numeroCodigo,
						this.intermediario, this.tipoCodigoInter, this.numeroCodigoInter);

				if (msg != null && !msg.equals(""))
					this.terminarIteracionXError(msg);
			}

			msg = pageEnviarTransInternacional.cotizacion();
			if (msg != null && !msg.equals(""))
				this.terminarIteracionXError(msg);

			msg = pageEnviarTransInternacional.confirmacion(intermediario);
			if (msg != null && !msg.equals(""))
				this.terminarIteracionXError(msg);

			this.accionConfirmarInternacional(soloGuardar);

//			if (!DatosDavivienda.IS_RISKMOTOR) {
//				if (DatosDavivienda.STRATUS != null) {
//					String tipoProdUpper = this.tipoCta;
//					String tipoProd = " "; // VALOR POR DEFECTO
//
//					if (tipoProdUpper.contains("AHORROS") || tipoProdUpper.contains("ahorros"))
//						tipoProd = "AHORROS";
//					else if (tipoProdUpper.contains("CORRIENTE") || tipoProdUpper.contains("corriente")) // CRÉDIPLUS
//						tipoProd = "CORRIENTE";
//
//					this.pageEnviarTransInternacional.validacionSaldosStratus(this.servicio, this.tipoIdEm,this.nitEmpre, tipoProd, this.numCta, false);
//					String saldoIni = this.pageEnviarTransInternacional.getSaldoTotalInicialOrigen();
//					String saldodis = this.pageEnviarTransInternacional.getSaldoDispoInicialOrigen();
//					String saldoFin = this.pageEnviarTransInternacional.getSaldoTotalFinalOrigen();
//					String saldoDispoFin = this.pageEnviarTransInternacional.getSaldoTotalFinalOrigen();
//					this.pageConsultatxInternacional.setSaldoTotalInicial(saldoIni);
//					this.pageConsultatxInternacional.setSaldoDisInicial(saldodis);
//					this.pageConsultatxInternacional.setSaldoTotalFinal(saldoFin);
//					this.pageConsultatxInternacional.setSaldoDisponibleFinal(saldoDispoFin);
//				}
//			}

			pageDivisas.setTime(pageConf.getFechaHoraTx());
		}

	}

	// ====================================[inicioCrearTx]==============================================================================

	/**
	 * Metodo se ubica en la ruta de inicio de creacion de [Pagos, Transferencias y
	 * otros] y seleciona el link del servicio a realizar
	 * 
	 * @throws Exception
	 */
	public void inicioCrearTx() throws Exception {

		String msgError = null;
		String pagoyTx = this.pageOrigen.getTextMid();

		if (pagoyTx.contains("Pagos, Transferencias y otros")) {
			if (navegador.contains("CHROME")) {
				msgError = pageOrigen.irAOpcion("Crear Transacción", "Pagos, Transferencias y otros",
						"Crear Transacción");

			} else {
				msgError = pageOrigen.irAOpcionMoZilla("Crear Transacción", "Pagos, Transferencias y otros",
						"Crear Transacción", null, null);

			}

		} else if (pagoyTx.contains("Pagos, Transferencias y Otros")) {

			if (navegador.contains("CHROME")) {
				msgError = pageOrigen.irAOpcion("Crear Transacción", "Pagos, Transferencias y Otros",
						"Crear Transacción");

			} else {
				msgError = pageOrigen.irAOpcionMoZilla("Crear Transacción", "Pagos, Transferencias y Otros",
						"Crear Transacción", null, null);

			}

		} else if (pagoyTx.contains("Pagos, Transferencias y Otros")) {

			if (navegador.contains("CHROME")) {
				msgError = pageOrigen.irAOpcion("Crear Transacción", "Pagos, Transferencias y Otros",
						"Crear Transacción");

			} else {
				msgError = pageOrigen.irAOpcionMoZilla("Crear Transacción", "Pagos, Transferencias y Otros",
						"Crear Transacción", null, null);

			}

		}

		else if (pagoyTx.contains("Pagos, Transferencias e Inscripciones")) {

			if (navegador.contains("CHROME")) {
				msgError = pageOrigen.irAOpcion("Crear Transacción", "Pagos, Transferencias e Inscripciones",
						"Crear Transacción");

			} else {
				msgError = pageOrigen.irAOpcionMoZilla("Crear Transacción", "Pagos, Transferencias e Inscripciones",
						"Crear Transacción", null, null);

			}

		} else if (pagoyTx.contains("Pagos, Transferencias e inscripciones")) {

			if (navegador.contains("CHROME")) {
				msgError = pageOrigen.irAOpcion("Crear Transacción", "Pagos, Transferencias e inscripciones",
						"Crear Transacción");

			} else {
				msgError = pageOrigen.irAOpcionMoZilla("Crear Transacción", "Pagos, Transferencias e inscripciones",
						"Crear Transacción", null, null);

			}

		}

		else {
			if (navegador.contains("CHROME")) {
				msgError = pageOrigen.irAOpcion("Crear Transacción", "Pagos y Transferencias", "Crear Transacción");

			} else {
				msgError = pageOrigen.irAOpcionMoZilla("Crear Transacción", "Pagos y Transferencias",
						"Crear Transacción", null, null);

			}
		}

		if (msgError != null)
			this.terminarIteracionXError(msgError);

		msgError = pageOrigen.irAServicio("Transferencias Internacionales");

		if (msgError != null)
			this.terminarIteracionXError(msgError);

		// Nivel de riesgo Motor
		this.riesgo = this.prioridaRiesgo;

	}

	// ===========================================[accionGuardarSinAprobarInternacional]===========================================================================

	/**
	 * Metodo se encarga de las tx Divisas guardar sin probar
	 * 
	 * @throws Exception
	 */
	private void accionGuardarSinAprobarInternacional() throws Exception {
		// CONFIRMAR GUARDAR SIN APROBAR TX
		String msgRta, msgError;
		msgRta = pageConf.guardarSinAprobarInternacional();
		if (msgRta != null)
			this.terminarIteracionXError(msgRta);

	}
	// =======================================================================================================================

	// ===========================================[accionConfirmarInternacional]===========================================================================

	/**
	 * Metodo se encarga en aprobar las Tx de Divisas
	 * 
	 * @param soloGuardar
	 * @throws Exception
	 */
	private void accionConfirmarInternacional(boolean soloGuardar) throws Exception {
		this.estadoTx = null;
		// CONFIRMAR TX
		String msgRta, msgError;
		// SI LLEGA A ESTE PUNTO SE GUARDÓ SIN APROBAR DE FORMA EXITOSA : EXTRAE EL
		// NÚMERO DE DOCUMENTO
//		if (!this.servicio.contains("Recibir"))
//			if (DatosDavivienda.IS_RISKMOTOR) {
//				this.valorTx = pageConf.getValorFinalTx();
//				if (this.valorTx != null)
//					SetActivityAmount(this.valorTx.replace("$", "").replace(" ", ""));
//
//				this.bancoDesTx = pageConf.getBancoDesTx();
//				if (this.bancoDesTx != null)
//					SetBancoDesTx(this.bancoDesTx);
//			}
		if (soloGuardar) {
			msgRta = pageConf.guardarSinAprobarInternacional();
			pageRecibirTransferenciasInternacionales.setTime(pageConf.getFechaHoraTx());
			if (msgRta != null && !msgRta.contains("guardada"))
				this.terminarIteracionXError(msgRta);

		} else {
			int numRetosFalla = 0;
//			if (DatosDavivienda.IS_RISKMOTOR)
//				numRetosFalla = Integer.valueOf(SettingsRun.getTestData().getParameter("Ingresos Fallidos").trim());

			msgRta = pageConf.aprobarTx(numRetosFalla, true);

			if (this.servicio.contains("Tx Internacionales Enviar al exterior")
					|| this.servicio.contains("Tx Internacionales Enviar al exterior Pendiente Aprobación")
					|| this.servicio.contains("Tx Internacionales Recibir desde el exterior")) {
				if (msgRta != null && (!msgRta.contains("Transacción recibida")
						&& !msgRta.contains("No se habilito el campo del código de confirmación")
						&& !msgRta.contains("no podemos atender su solicitud")))
					msgRta = pageConf.closeActiveIntAlert();
				else if (msgRta == null) {
					this.numeroTx = pageConf.getNumeroDocumentoInterna();
					if (this.numeroTx == null) {

						msgRta = pageConf.closeActiveIntAlert();
						pageRecibirTransferenciasInternacionales.setTime(pageConf.getFechaHoraTx());
					}
				}
			}
//			if (DatosDavivienda.IS_RISKMOTOR) {
//				this.estadoTx = msgRta;
//				this.SetEstado(msgRta);
//				if (msgRta != null && DXCUtil.itemContainsAnyArrayItem(msgRta, arrMsgBuscarEstado)) {
//					this.estadoTx = "Transferencia Realizada";// @estado de la transaccion
//				} else if (msgRta != null && DXCUtil.itemContainsAnyArrayItem(msgRta, this.arrMsgTxDeclinada)) {
//					this.estadoTx = "DECLINADA";
//					this.riesgo = this.prioridaRiesgo;
//					if (this.riesgo.equals("ALTO")) {
//						this.numeroTx = pageConf.getNumeroDocumentoInterna();
////						SetNumApr(this.numeroTx);
//					}
//					if (this.servicio.contains("Nómina") || this.servicio.contains("Pago a Proveedores")
//							|| this.servicio.contains("AFC") || this.servicio.contains("Crédito.3ros")) {
//						this.validarIngresoMRDestinosMasivo();
//					} else {
//
//						this.validarIngresoMR();
//
//					}
////					this.pageOrigen.terminarIteracion();
//				}
//			}

//			else {
//				System.out.println("En construcción depende del tipo de la prueba");
//			}

//			if (msgRta != null && !msgRta.contains(PageConfirmacion1.MSG_EXITO_GUARD)&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_PAGO)&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_APROB_INTER)&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_APROB_INTER2)&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_DOC_Y_FOR_INTER)&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_DOC_Y_FOR_COMPLETE_INF_INTER))
//
//				if (!DatosDavivienda.IS_RISKMOTOR) {
//					this.terminarIteracionXError(msgRta);
//				} else {
//					this.terminarIteracionXError(msgRta);
//				}
		}

		if (msgRta != null && !msgRta.contains("recibida") && this.tipoPrueba.equals("Tx En Línea")) {
			String msg = null;
			if (!msgRta.contains(PageConfirmacion1.MSG_EXITO_DOC_Y_FOR_COMPLETE_INF_INTER)) {

				if (this.fecha == null || this.fecha.trim().isEmpty()
						|| this.hora == null && this.hora.trim().isEmpty()) {
					this.fecha = SettingsRun.getTestData().getParameter("Fecha tx");
					this.hora = SettingsRun.getTestData().getParameter("Hora tx");
				}

				msg = this.pageDocumentos_Y_Formularios.ModuloDocumetosYFormularios(this.tipoPrueba, this.servicio,
						this.fecha, this.hora, this.moneda);
				if (msg != null && !msg.contains("En este módulo puede visualizar las operaciones") && !msg
						.contains("Los campos que no se presentan en la declaración de cambio serán autocompletados"))
					this.terminarIteracionXError(msg);
			} else {
				if (this.fecha == null || this.fecha.trim().isEmpty()
						|| this.hora == null && this.hora.trim().isEmpty()) {
					this.fecha = SettingsRun.getTestData().getParameter("Fecha tx");
					this.hora = SettingsRun.getTestData().getParameter("Hora tx");
				}
				msg = this.pageDocumentos_Y_Formularios.IralModuloDocumetosYFormularios(this.tipoPrueba, this.servicio,
						this.fecha, this.hora, this.moneda);
				if (msg != null && !msg.isEmpty())
					this.terminarIteracionXError(msg);
			}

			String numCambiario1 = DXCUtil.left(SettingsRun.getTestData().getParameter("Numeral cambiario 1"), 4);
			String numCambiario2 = DXCUtil.left(SettingsRun.getTestData().getParameter("Numeral cambiario 2"), 4);
			String tipoOperacion = SettingsRun.getTestData().getParameter("Tipo de operación");
			String desInversion = SettingsRun.getTestData().getParameter("Destino de la inversión");
			String opciondeinversión = SettingsRun.getTestData().getParameter("Opción de inversión");
			String deducciones = SettingsRun.getTestData().getParameter("Deducciones");

			// Datos Documentos y formularios A cambiar
			String cambiarConcepto = SettingsRun.getTestData().getParameter("Cambiar Concepto de la transferencia");
			String conceptoAcambiar = SettingsRun.getTestData().getParameter("Concepto de la transferencia A Cambiar");
			String numeroDeposito = SettingsRun.getTestData().getParameter("Número de depósito 1");
			String numeroFacturaoReferDeclaracion = SettingsRun.getTestData().getParameter("Número de declaración 1");
			String cambiarlistnumeralOperacion_Numeral1 = SettingsRun.getTestData()
					.getParameter("Cambiar Numeral cambiario 1");
			String numeral1Acambiar = SettingsRun.getTestData().getParameter("Numeral cambiario A Cambiar 1");
			String cambiarDatosDescripciondelaoperacion = SettingsRun.getTestData()
					.getParameter("Cambiar Datos Descripción de la operación");
			String numerodelprestamooaval = SettingsRun.getTestData().getParameter("Número del préstamo o aval");
			String nombredelacreedoroeldeudoroavalista = SettingsRun.getTestData()
					.getParameter("Nombre del acreedor o el deudor o avalista");
			String nombredeldeudoroacreedorAvaladoobeneficiarioresidente = SettingsRun.getTestData()
					.getParameter("Nombre del deudor o acreedor / Avalado o beneficiario residente");
			String tipodeidentificacióndeldeudor = SettingsRun.getTestData()
					.getParameter("Tipo de identificación del deudor");
			String numerodeidentificaciondeldeudor = SettingsRun.getTestData()
					.getParameter("Número de identificación del deudor");
			String digitodeverificacion = SettingsRun.getTestData().getParameter("Dígito de verificación");
			String monedaestipulada = SettingsRun.getTestData().getParameter("Moneda estipulada");
			String valormonedaestipulada = SettingsRun.getTestData().getParameter("Valor moneda estipulada");
			String tasadecambiomoneda = SettingsRun.getTestData().getParameter("Tasa de cambio moneda");
			String cambiarValornumeralcambiario1 = SettingsRun.getTestData()
					.getParameter("Cambiar Valor numeral cambiario 1");
			String ValorNumeral1Camb = SettingsRun.getTestData().getParameter("Valor numeral cambiario A Cambiar 1");
			String validacionAdicionar = SettingsRun.getTestData().getParameter("Validar Numerales");

			// Datos Formularios del EmpresaReceptora a agregar o Actualizar

			String tipodeidentificacionReceptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Tipo de identificación");
			String numerodeidentificacionReceptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Número de identificación");
			String digitodeverificacionReceptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Dígito de verificación");
			String nombreorazonsocialReceptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Nombre o razón social");
			String codigopaisReceptora = SettingsRun.getTestData().getParameter("Empresa receptora - Código país");
			String codigodepartamentoReptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Código departamento");
			String codigociudadReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Código ciudad");
			String codigoCIIUReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Código CIIU");
			String telefonoReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Teléfono");
			String direccionReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Dirección");
			String correoReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Correo electrónico");
			String sectorReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Sector");
			String tipodeempresaReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Tipo de empresa");
			String superintendenciaReptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Superintendencia de vigilancia");
			String actividadReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Actividad");
			String tipoderegimenReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Tipo de régimen");
			String naturalezaReptora = SettingsRun.getTestData().getParameter("Empresa receptora  - Naturaleza");

			this.pageDocumentos_Y_Formularios.EmpresaReceptora(tipodeidentificacionReceptora,
					numerodeidentificacionReceptora, digitodeverificacionReceptora, nombreorazonsocialReceptora,
					codigopaisReceptora, codigodepartamentoReptora, codigociudadReptora, codigoCIIUReptora,
					telefonoReptora, direccionReptora, correoReptora, sectorReptora, tipodeempresaReptora,
					superintendenciaReptora, actividadReptora, tipoderegimenReptora, naturalezaReptora);

			String identificacionInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Tipo de identificación");
			String numerodeidentificacionInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Número de identificación");
			String digitodeverificacionInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Dígito de verificación");
			String nombreorazonsocialInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Nombre o razón social");

			String codigoPaisInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Código país");
			String codigoDepartamentoInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Código departamento");
			String codigociudadInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Código ciudad");
			String codigoCIIUInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Código CIIU");

			String correoElectronicoInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Correo electrónico");
			String sectorInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Sector");
			String tipodeempresaInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Tipo de empresa");
			String superintendenciaInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Superintendencia de vigilancia");
			String naturalezaInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Naturaleza");

			String telefonoInversionista = "";
			String direccionInversionista = "";

			if (SettingsRun.getTestData().parameterExist("Identificación del inversionista - Teléfono"))

				telefonoInversionista = SettingsRun.getTestData()
						.getParameter("Identificación del inversionista - Teléfono");

			if (SettingsRun.getTestData().parameterExist("Identificación del inversionista - Dirección"))
				direccionInversionista = SettingsRun.getTestData()
						.getParameter("Identificación del inversionista - Dirección");

			this.pageDocumentos_Y_Formularios.Inversionista(identificacionInversionista,
					numerodeidentificacionInversionista, digitodeverificacionInversionista,
					nombreorazonsocialInversionista, codigoPaisInversionista, codigoDepartamentoInversionista,
					codigociudadInversionista, codigoCIIUInversionista, correoElectronicoInversionista,
					sectorInversionista, tipodeempresaInversionista, superintendenciaInversionista,
					naturalezaInversionista, telefonoInversionista, direccionInversionista);

			// Validacion Dian
			String validacionDian = SettingsRun.getTestData().getParameter("Validacion - DIAN");

			String cargueDocu = SettingsRun.getTestData().getParameter("Cargue Archivo Documentos");

			// Divide la ruta en un array de strings separados por comas
			String[] rutaArch = cargueDocu.split(",");

			msg = this.pageDocumentos_Y_Formularios.DatosDocumetosYFormularios(this.concepTx, tipoOperacion,
					desInversion, opciondeinversión, this.valorsin, numCambiario1, this.valorNumeral1, numCambiario2,
					this.valorNumeral2, deducciones, cambiarConcepto, conceptoAcambiar, numeroDeposito,
					numeroFacturaoReferDeclaracion, cambiarlistnumeralOperacion_Numeral1, numeral1Acambiar,
					cambiarDatosDescripciondelaoperacion, numerodelprestamooaval, nombredelacreedoroeldeudoroavalista,
					nombredeldeudoroacreedorAvaladoobeneficiarioresidente, tipodeidentificacióndeldeudor,
					numerodeidentificaciondeldeudor, digitodeverificacion, monedaestipulada, valormonedaestipulada,
					tasadecambiomoneda, cambiarValornumeralcambiario1, ValorNumeral1Camb, validacionAdicionar,
					validacionDian, rutaArch);

			if (msg != null && !msg.isEmpty()) {
				if (msg.contains("Para continuar debe completar la información solicitada")) {
					msg = "Para continuar debe completar la información solicitada";
				}
				this.terminarIteracionXError(msg);
			}

		} else {
			if (!soloGuardar) {
				if (this.numeroTx == null)
					this.numeroTx = pageConf.getNumeroDocumentoInterna();
				if (this.numeroTx == null)
					this.terminarIteracionXError("No se encontró el Número de Documento de la transacción");

				SettingsRun.getTestData().setParameter("Número Aprobación", this.numeroTx);

				this.pageConsultatxInternacional.SetNumAprInterna(this.numeroTx);

//				SetNumApr(this.numeroTx);
			}
		}

	}

	// =======================================================================================================================

	// ===========================================[aprobarTxPendienteIntern]===========================================================================
	/**
	 * Intenta aprobar una transacción cuyo número de transacción se encuentra
	 * almacenado en el parámetro "Número Aprobación".<br>
	 * Este metodo solo es utilizado para transferencias Internacionales "Divisas".
	 * 
	 * @throws Exception
	 */
	public String aprobarTxPendienteIntern(boolean desdeDetalle) throws Exception {

		if (this.fecha == null || this.fecha.trim().isEmpty() || this.hora == null && this.hora.trim().isEmpty()) {
			this.fecha = SettingsRun.getTestData().getParameter("Fecha tx");
			this.hora = SettingsRun.getTestData().getParameter("Hora tx");
		}

		this.pageAprobInter.inicioAprobaciones(this.tipoPrueba, this.servicio, this.fecha, this.hora, this.moneda);

		int numRetosFalla = 0;
		String msgRta = null;
		if (DatosDavivienda.IS_RISKMOTOR)
			numRetosFalla = Integer.valueOf(SettingsRun.getTestData().getParameter("Ingresos Fallidos").trim());

//				String msgRta = pageConf.aprobarTx(numRetosFalla, numCodSeg);
		msgRta = pageConf.aprobarTx(numRetosFalla, desdeDetalle);

		if (msgRta != null && !msgRta.contains(PageConfirmacion1.MSG_EXITO_GUARD)
				&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_PAGO)
				&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_ENV_OTP)
				&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_APROB)
				&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_APROB_INTER)
				&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_APROB_INTER2)
				&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_DOCYFOR))

			this.terminarIteracionXError(msgRta);

//		if (DatosDavivienda.IS_RISKMOTOR) {
//			this.estadoTx = msgRta;
//			this.SetEstado(msgRta);
//			if (msgRta != null && Util.itemContainsAnyArrayItem(msgRta, arrMsgBuscarEstado)) {
//				this.estadoTx = "Transferencia Realizada";// Buscar estado de la transaccion
//			} else if (Util.itemContainsAnyArrayItem(msgRta, arrMsgTxDeclinada))
//				this.estadoTx = "DECLINADA";
//
//		}
//			else {
//				System.out.println("En construcción, depende del tipo de la prueba");
//			}
		String msg = null;

		if (msgRta != null && msgRta.contains(PageConfirmacion1.MSG_EXITO_DOCYFOR)) {

			msg = this.pageDocumentos_Y_Formularios.closeActiveIntAlert();

			if (msgRta.contains(PageConfirmacion1.MSG_EXITO_DOCYFOR)) {
				Reporter.reportEvent(Reporter.MIC_INFO,
						"Tiene al menos una operación que requiere adjuntar documentos.");
				;
			}

			msg = this.pageDocumentos_Y_Formularios.ModuloDocumetosYFormularios(this.tipoPrueba, this.servicio,
					this.fecha, this.hora, this.moneda);

			if (msg != null && !msg.contains("En este módulo puede visualizar las operaciones") && !msg
					.contains("Los campos que no se presentan en la declaración de cambio serán autocompletados"))
				this.terminarIteracionXError(msg);

			String numCambiario1 = Util.left(SettingsRun.getTestData().getParameter("Numeral cambiario 1"), 4);
			String numCambiario2 = Util.left(SettingsRun.getTestData().getParameter("Numeral cambiario 2"), 4);
			String tipoOperacion = SettingsRun.getTestData().getParameter("Tipo de operación");
			String desInversion = SettingsRun.getTestData().getParameter("Destino de la inversión");
			String opciondeinversión = SettingsRun.getTestData().getParameter("Opción de inversión");
			String deducciones = SettingsRun.getTestData().getParameter("Deducciones");

			// Datos Documentos y formularios A cambiar
			String cambiarConcepto = SettingsRun.getTestData().getParameter("Cambiar Concepto de la transferencia");
			String conceptoAcambiar = SettingsRun.getTestData().getParameter("Concepto de la transferencia A Cambiar");
			String numeroDeposito = SettingsRun.getTestData().getParameter("Número de depósito 1");
			String numeroFacturaoReferDeclaracion = SettingsRun.getTestData().getParameter("Número de declaración 1");
			String cambiarlistnumeralOperacion_Numeral1 = SettingsRun.getTestData()
					.getParameter("Cambiar Numeral cambiario 1");
			String numeral1Acambiar = SettingsRun.getTestData().getParameter("Numeral cambiario A Cambiar 1");

			String cambiarDatosDescripciondelaoperacion = SettingsRun.getTestData()
					.getParameter("Cambiar Datos Descripción de la operación");
			String numerodelprestamooaval = SettingsRun.getTestData().getParameter("Número del préstamo o aval");
			String nombredelacreedoroeldeudoroavalista = SettingsRun.getTestData()
					.getParameter("Nombre del acreedor o el deudor o avalista");
			String nombredeldeudoroacreedorAvaladoobeneficiarioresidente = SettingsRun.getTestData()
					.getParameter("Nombre del deudor o acreedor / Avalado o beneficiario residente");

			String tipodeidentificacióndeldeudor = SettingsRun.getTestData()
					.getParameter("Tipo de identificación del deudor");
			String numerodeidentificaciondeldeudor = SettingsRun.getTestData()
					.getParameter("Número de identificación del deudor");

			String digitodeverificacion = SettingsRun.getTestData().getParameter("Dígito de verificación");

			String monedaestipulada = SettingsRun.getTestData().getParameter("Moneda estipulada");
			String valormonedaestipulada = SettingsRun.getTestData().getParameter("Valor moneda estipulada");
			String tasadecambiomoneda = SettingsRun.getTestData().getParameter("Tasa de cambio moneda");
			String cambiarValornumeralcambiario1 = SettingsRun.getTestData()
					.getParameter("Cambiar Valor numeral cambiario 1");
			String ValorNumeral1Camb = SettingsRun.getTestData().getParameter("Valor numeral cambiario A Cambiar 1");
			String validacionAdicionar = SettingsRun.getTestData().getParameter("Validar Numerales");

			// Datos Formularios del EmpresaReceptora a agregar o Actualizar

			String tipodeidentificacionReceptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Tipo de identificación");
			String numerodeidentificacionReceptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Número de identificación");
			String digitodeverificacionReceptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Dígito de verificación");
			String nombreorazonsocialReceptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Nombre o razón social");
			String codigopaisReceptora = SettingsRun.getTestData().getParameter("Empresa receptora - Código país");
			String codigodepartamentoReptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Código departamento");
			String codigociudadReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Código ciudad");
			String codigoCIIUReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Código CIIU");
			String telefonoReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Teléfono");
			String direccionReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Dirección");
			String correoReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Correo electrónico");
			String sectorReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Sector");
			String tipodeempresaReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Tipo de empresa");
			String superintendenciaReptora = SettingsRun.getTestData()
					.getParameter("Empresa receptora - Superintendencia de vigilancia");
			String actividadReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Actividad");
			String tipoderegimenReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Tipo de régimen");
			String naturalezaReptora = SettingsRun.getTestData().getParameter("Empresa receptora  - Naturaleza");

			this.pageDocumentos_Y_Formularios.EmpresaReceptora(tipodeidentificacionReceptora,
					numerodeidentificacionReceptora, digitodeverificacionReceptora, nombreorazonsocialReceptora,
					codigopaisReceptora, codigodepartamentoReptora, codigociudadReptora, codigoCIIUReptora,
					telefonoReptora, direccionReptora, correoReptora, sectorReptora, tipodeempresaReptora,
					superintendenciaReptora, actividadReptora, tipoderegimenReptora, naturalezaReptora);

			String identificacionInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Tipo de identificación");
			String numerodeidentificacionInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Número de identificación");
			String digitodeverificacionInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Dígito de verificación");
			String nombreorazonsocialInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Nombre o razón social");

			String codigoPaisInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Código país");
			String codigoDepartamentoInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Código departamento");
			String codigociudadInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Código ciudad");
			String codigoCIIUInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Código CIIU");

			String correoElectronicoInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Correo electrónico");
			String sectorInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Sector");
			String tipodeempresaInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Tipo de empresa");
			String superintendenciaInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Superintendencia de vigilancia");
			String naturalezaInversionista = SettingsRun.getTestData()
					.getParameter("Identificación del inversionista - Naturaleza");

			String telefonoInversionista = "";
			String direccionInversionista = "";

			if (SettingsRun.getTestData().parameterExist("Identificación del inversionista - Teléfono"))

				telefonoInversionista = SettingsRun.getTestData()
						.getParameter("Identificación del inversionista - Teléfono");

			if (SettingsRun.getTestData().parameterExist("Identificación del inversionista - Dirección"))
				direccionInversionista = SettingsRun.getTestData()
						.getParameter("Identificación del inversionista - Dirección");

			this.pageDocumentos_Y_Formularios.Inversionista(identificacionInversionista,
					numerodeidentificacionInversionista, digitodeverificacionInversionista,
					nombreorazonsocialInversionista, codigoPaisInversionista, codigoDepartamentoInversionista,
					codigociudadInversionista, codigoCIIUInversionista, correoElectronicoInversionista,
					sectorInversionista, tipodeempresaInversionista, superintendenciaInversionista,
					naturalezaInversionista, telefonoInversionista, direccionInversionista);

			// Validacion Dian
			String validacionDian = SettingsRun.getTestData().getParameter("Validacion - DIAN");

			String cargueDocu = SettingsRun.getTestData().getParameter("Cargue Archivo Documentos");
			// Divide la ruta en un array de strings separados por comas
			String[] rutaArch = cargueDocu.split(",");

			msg = this.pageDocumentos_Y_Formularios.DatosDocumetosYFormularios(this.concepTx, tipoOperacion,
					desInversion, opciondeinversión, this.valorsin, numCambiario1, this.valorNumeral1, numCambiario2,
					this.valorNumeral2, deducciones, cambiarConcepto, conceptoAcambiar, numeroDeposito,
					numeroFacturaoReferDeclaracion, cambiarlistnumeralOperacion_Numeral1, numeral1Acambiar,
					cambiarDatosDescripciondelaoperacion, numerodelprestamooaval, nombredelacreedoroeldeudoroavalista,
					nombredeldeudoroacreedorAvaladoobeneficiarioresidente, tipodeidentificacióndeldeudor,
					numerodeidentificaciondeldeudor, digitodeverificacion, monedaestipulada, valormonedaestipulada,
					tasadecambiomoneda, cambiarValornumeralcambiario1, ValorNumeral1Camb, validacionAdicionar,
					validacionDian, rutaArch);

			if (msg != null) {

				if (msg.contains("Para continuar debe completar la información solicitada")) {
					msg = "Para continuar debe completar la información solicitada";
				}

				this.terminarIteracionXError(msg);
			}

		} else {

			// SI LLEGA A ESTE PUNTO SE GUARDÓ SIN APROBAR DE FORMA EXITOSA : EXTRAE EL
			// NÚMERO DE DOCUMENTO
			this.numeroTx = pageConf.getNumeroDocumentoInterna();

			if (this.numeroTx == null || this.numeroTx.isEmpty())
				this.terminarIteracionXError("No se encontró el Número de Documento de la transacción");

			SettingsRun.getTestData().setParameter("Número Aprobación", this.numeroTx);

			this.pageDivisas.SetNumAprInterna(this.numeroTx);
			this.pageConsultatxInternacional.SetNumAprInterna(this.numeroTx);

			msgRta = this.pageConsultatxInternacional.closeActiveIntAlert();

		}

		return msgRta;

	}

	// =======================================================================================================================

// ===========================================[terminarIteracionXError]===========================================================================

	/**
	 * Metodo se encarga de Termininar la interacion y guardar el mensaje de error
	 * 
	 * @param msgError
	 * @throws Exception
	 */
	private void terminarIteracionXError(String msgError) throws Exception {

		if (msgError == null) {
			msgError = "Error_1";
		}
		Reporter.write(msgError);

		if (frontEmpresarial != null) {

			Evidence.save(msgError.replace(" ", "").replaceAll("[\\\\/:*?\"<>|¡!\\.]", "").replaceAll("\\s+", "_"),
					frontEmpresarial);
		} else {

			Evidence.save(msgError.replace(" ", "").replaceAll("[\\\\/:*?\"<>|¡!\\.]", "").replaceAll("\\s+", "_"),
					loginFrontPyme);
		}

		if (DatosDavivienda.IS_RISKMOTOR) {
			Reporter.reportEvent(Reporter.MIC_FAIL, "No se adiciona a MR >>> " + msgError);
			Reporter.write("No se adiciona a MR >>> " + msgError);
		} else {
			Reporter.write("En construcción XQ no sé si es error >>> " + msgError);
			Reporter.reportEvent(Reporter.MIC_FAIL, msgError);
		}
//		this.pageOrigen.terminarIteracion(); //Cerrar tanto en empresarial como Pyme
	}

	// ***********************************************************************************************************************

	public void destroy() {
	}
	// ***********************************************************************************************************************

}
