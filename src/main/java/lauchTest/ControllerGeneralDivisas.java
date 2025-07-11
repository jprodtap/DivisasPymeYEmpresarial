package lauchTest;

import library.core.BasePageWeb;
import library.core.Controller;
import library.reporting.Evidence;
import library.reporting.Reporter;
import library.settings.SettingsRun;
import page.middlePymes.ControllerValiPymeMiddle1;
import page.middlePymes.PageUsuariosEmpresa1;
import library.common.Util;
import pages.actions.Divisas.PageAprobacionInter;
import pages.actions.Divisas.PageConsultatxInternacional;
import pages.actions.Divisas.PageDivisas;
import pages.actions.Divisas.PageDocumentos_Y_Formularios;
import pages.actions.Divisas.PageEnviarTransInternacional;
import pages.actions.Divisas.PageInformesTransInternacionales;
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

import dav.c360.PageInicioC360;
import dav.c360.PageLogin;
import dav.c360.moduloPersonas.PageEmpresas;
import dav.transversal.DatosDavivienda;
import dav.transversal.DatosEmpresarial;
import dav.transversal.MotorRiesgo;
import dav.transversal.NotificacionSMS;
import dav.transversal.Stratus;
import dxc.util.DXCUtil;
import pages.actions.MiddleEmpresarial.CommonMiddleEmpresarial;
import pages.actions.MiddleEmpresarial.PageLoginMiddleEmpresarial;
import pages.actions.MiddleEmpresarial.PageMiddleEmpresarial;
import dav.Controllers.CommonMiddlesEmpresas;
import dav.Controllers.ControllerGeneralMiddlesEmpresas;
import dav.Controllers.ControllerMiddleEmpresarial;
import dav.Controllers.ControllerMiddlePyme;

/**
 * Controlador General de Divisas para los portales PYME y Portal Empresarial.
 * Permite ejecutar operaciones de divisas reutilizando la lógica y
 * diferenciando la inicialización según el tipo de portal.
 *
 * @author
 */
public class ControllerGeneralDivisas implements Controller {

	// ***********************************************************************************************************************
	// * Constructor - Implementacion Patron Singleton *
	// ***********************************************************************************************************************

	private static ControllerGeneralDivisas instanciaUnicaControllerGeneralDivisas; // Variable en donde se Almacena la
																					// Instancia Unica de la Clase.

	ControllerGeneralMiddlesEmpresas controlMiddlesEmpresas = ControllerGeneralMiddlesEmpresas
			.getInstanciaUnicaControllerMiddlesEmpresas();
	ControllerMiddleEmpresarial controlMiddleEmpresarial = ControllerMiddleEmpresarial
			.getInstanciaUnicaControllerMiddleEmpresarial();
	ControllerMiddlePyme controlMiddlePyme = ControllerMiddlePyme.getInstanciaUnicaControllerMiddlePyme();

	// ***********************************************************************************************************************
	// * Instancias. *
	// ***********************************************************************************************************************
	// Enum para distinguir el tipo de portal
	public enum PortalType {
		PYME, EMPRESARIAL
	}

	private static ControllerGeneralDivisas instanciaUnica;
	private PortalType portalType;

// =============================[Page & Crontroller]==========================================================================================

	// Instancias de páginas Cliente360
	PageLogin pageLoginC360 = null;
	PageInicioC360 pageInicioC360 = null;
	PageEmpresas pageEmpresasC360 = null;

	// Instancias de páginas para PYME
	private PageLoginPymes1 loginFrontPyme;
	private ControllerValiPymeMiddle1 controllerValiPymeMiddle;
	private PageOrigen1 pageOrigenPyme;
	private PageAdminParametros1 pageAdminParametros;
	private PageActualizacionDeDatos1 pageActualizacionDeDatos;

	// Instancias de páginas para Empresarial
	private PageLoginFrontEmpresarial loginFrontEmpresarial;
	private PageFrontEmpresarial frontEmpresarial;

	private PageLoginMiddleEmpresarial loginMiddleEmpresarial;
	private PageMiddleEmpresarial middleEmpresarial;

	// Instancias de páginas comunes a ambos portales (se inicializan según portal)
	private PageDivisas pageDivisas;
	private PageEnviarTransInternacional pageEnviarTransInternacional;
	private PageRecibirTransferenciasInternacionales pageRecibirTransferenciasInternacionales;
	private PageDocumentos_Y_Formularios pageDocumentos_Y_Formularios;
	private PageConsultatxInternacional pageConsultatxInternacional;
	private PageAprobacionInter pageAprobInter;
	private PageConfirmacion1 pageConf;
	private PageInformesTransInternacionales pageInfoTransInter;

// =======================================================================================================================

	// Variables de contexto y flujo
	private String usuario, servicio, tipoPrueba, navegador, nombreEmpre, nitEmpre, tipoIdEm, numCta, tipoCta,
			referencia1, referencia2, uuid;
	private String descripcionsin, valorsin, valorcon, referenciasin, desdeElDetalle;
	private String numerodereferenciaExterna, riesgo, prioridaRiesgo, userAgent;
	private String valorTx, bancoDesTx, estadoTx, montoReal;
	private String cantidadTxPorTipoDestino;
	private String modena, concepTx, concepto, valorNumeral1, valorNumeral2, tipoEnvio, FechaEnvioFrecuente;
	private String nombreBene, paisDestino, ciudadDestino, direccionBene, cuentaBene, infoPago, referenciaPago;
	private String tipoCodigo, numeroCodigo, intermediario, tipoCodigoInter, numeroCodigoInter;

// =======================================================================================================================
	// Variables Consulta comprobantes
	private String fecha, hora, moneda;

	String tipoConstaTxRealizadas = null, ordenanteBeneficiario = null, tipoTranferencia = null, estado = null,
			tipoMoneda = null, fechaTx = null, horaTx = null, fechaDesde = null, fechaHasta = null;

// =======================================================================================================================

	// Variables para guardar últimos valores (optimización de configuración)
	private String lastNumAprobaciones = "", lastTipoAbono = "", lastCtaInscrita = "", lastIdusuario = "",
			lastempresa = "";

	// Variables estáticas para uso compartido
	public static String numAprobaciones = null;
	public static String tipoPruebaStatic = null;
	public static String numeroTx = null;

// =======================================================================================================================

	private String[] arrMsgBuscarEstado = { PageConfirmacion1.MSG_EXITO_APROB, PageConfirmacion1.MSG_EXITO_PAGO,
			PageConfirmacion1.MSG_EXITO_ENV_OTP, PageConfirmacion1.MSG_EXITO_GUARD,
			PageConfirmacion1.MSG_EXITO_APROB_INTER, };

	private String[] arrMsgTxDeclinada = { PageConfirmacion1.MSG_EXITO_DECL1, PageConfirmacion1.MSG_EXITO_DECL2,
			PageConfirmacion1.MSG_EXITO_DECL3, PageConfirmacion1.MSG_EXITO_DECL5, };

// =======================================================================================================================

	// VARIABLES GLOBALES DE TX
	final String TP_LOGIN = "Login";
	final String TP_EN_LINEA = "Tx En Línea";
	final String TP_PEND_APR = "Tx Pend Aprobación";
	final String CN_APRO_PEND = "1";

// =======================================================================================================================
	// Datos Motor de riesgo
	public static String[] cuentasDesMotor = null, datoNumDestCuentas = null;

	public static String activityAmount = null, bancoDesMotor = null, riesgoBc = null, riesgoEfm = null;

// =======================================[ControllerGeneralDivisas]================================================================================
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
	private ControllerGeneralDivisas(PortalType portalType) {
		this.portalType = portalType;
	}

// =======================================[ControllerGeneralDivisas getInstance]================================================================================
	public static ControllerGeneralDivisas getInstance(PortalType portalType) {
		if (instanciaUnica == null) {
			instanciaUnica = new ControllerGeneralDivisas(portalType);
		}
		return instanciaUnica;
	}

// =======================================[resetInstance]================================================================================

	public static void resetInstance() {
		instanciaUnica = null;
	}

// =======================================[inicializarInstanciaUnicaControllerFrontEmpresarial]================================================================================

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

// ==========================================[cargarDatosGlobales]=================================================================================================

	/**
	 * Carga los datos de contexto globales desde SettingsRun. Centraliza la
	 * obtención de datos necesarios para la operación.
	 */
	private void cargarDatosGlobales() {
		// Aquí puedes encontrar la obtención de parámetros de prueba y globales
		// para todas las variables de instancia necesarias.
		this.usuario = SettingsRun.getTestData().getParameter("Nombre de Usuario").trim();
		this.servicio = SettingsRun.getTestData().getParameter("Servicio").trim();
		this.tipoPrueba = SettingsRun.getTestData().getParameter("Tipo prueba").trim();
		this.navegador = SettingsRun.getTestData().getParameter("Navegador").trim();
		this.nombreEmpre = SettingsRun.getTestData().getParameter("Nombre Empresa").trim();
		this.nitEmpre = SettingsRun.getTestData().getParameter("Numero ID Empresa").trim();
		this.tipoIdEm = SettingsRun.getTestData().getParameter("Tipo ID Empresa");
		this.tipoCta = SettingsRun.getTestData().getParameter("Tipo producto origen / Franquicia").trim();
		this.numCta = SettingsRun.getTestData().getParameter("Número producto origen").trim();
		this.referencia1 = SettingsRun.getTestData().getParameter("Referencia1 / Tipo Producto Destino").trim();
		this.referencia2 = SettingsRun.getTestData().getParameter("Referencia2 / Número Producto Destino").trim();
		this.uuid = SettingsRun.getTestData().getParameter("Hash").trim();
		this.descripcionsin = SettingsRun.getTestData().getParameter("Descripcion").trim();
		this.valorsin = SettingsRun.getTestData().getParameter("Valor a Pagar / Transferir").trim();
		this.valorcon = SettingsRun.getTestData().getParameter("Valor a Pagar / Transferir");
		this.referenciasin = SettingsRun.getTestData().getParameter("Referencia").trim();
		this.desdeElDetalle = SettingsRun.getTestData().getParameter("Desde_el_Detalle").trim();

		this.tipoMoneda = SettingsRun.getTestData().getParameter("Tipo Moneda");
		this.modena = SettingsRun.getTestData().getParameter("Tipo Moneda").trim();
		this.concepTx = SettingsRun.getTestData().getParameter("Concepto de la transferencia").trim();
		this.concepto = SettingsRun.getTestData().getParameter("Concepto de la transferencia");
		this.valorNumeral1 = SettingsRun.getTestData().getParameter("Valor numeral cambiario 1");
		this.valorNumeral2 = SettingsRun.getTestData().getParameter("Valor numeral cambiario 2");
		this.tipoEnvio = SettingsRun.getTestData().getParameter("Tipo de Envio");
		this.FechaEnvioFrecuente = SettingsRun.getTestData().getParameter("Fecha envío frecuente");
		this.nombreBene = SettingsRun.getTestData().getParameter("Ordenante / Nombre del beneficiario en el exterior");
		this.paisDestino = SettingsRun.getTestData().getParameter("País de destino de la transferencia");
		this.ciudadDestino = SettingsRun.getTestData().getParameter("Ciudad y país donde está ubicado el beneficiario");
		this.direccionBene = SettingsRun.getTestData().getParameter("Dirección del beneficiario");
		this.cuentaBene = SettingsRun.getTestData().getParameter("Número de cuenta, IBAN o CLABE");
		this.infoPago = SettingsRun.getTestData().getParameter("Información para el beneficiario");
		this.referenciaPago = SettingsRun.getTestData().getParameter("Información para el beneficiario Numero");
		this.tipoCodigo = SettingsRun.getTestData().getParameter("Tipo de código");
		this.numeroCodigo = SettingsRun.getTestData().getParameter("Número de código");
		this.intermediario = SettingsRun.getTestData().getParameter("Requiere un banco intermediario");
		this.tipoCodigoInter = SettingsRun.getTestData().getParameter("Tipo de código Intermediario");
		this.numeroCodigoInter = SettingsRun.getTestData().getParameter("Número de código Intermediario");

		this.tipoConstaTxRealizadas = SettingsRun.getTestData().getParameter("Tiempo de Consulta");
		this.fecha = SettingsRun.getTestData().getParameter("Fecha tx");
		this.hora = SettingsRun.getTestData().getParameter("Hora tx");
		this.moneda = SettingsRun.getTestData().getParameter("Tipo Moneda").trim();

		// Consulta Comprobantes
		this.tipoConstaTxRealizadas = SettingsRun.getTestData().getParameter("Tiempo de Consulta");
		this.ordenanteBeneficiario = SettingsRun.getTestData()
				.getParameter("Ordenante / Nombre del beneficiario en el exterior");
		this.tipoTranferencia = SettingsRun.getTestData().getParameter("Tipo de Transferencia");
		this.fechaDesde = SettingsRun.getTestData().getParameter("Fecha Día Inicial  Desde (dd/mm/YYYY)");
		this.fechaHasta = SettingsRun.getTestData().getParameter("Fecha DÍa Final Hasta (dd/mm/YYYY)");
		this.estado = SettingsRun.getTestData().getParameter("Estado").trim();
		this.tipoMoneda = SettingsRun.getTestData().getParameter("Tipo Moneda").trim();
		this.fechaTx = SettingsRun.getTestData().getParameter("Fecha tx");
		this.horaTx = SettingsRun.getTestData().getParameter("Hora tx");
	}

	public void setearNombreColumnasMiddlesEmpresa() {

		// -----------------------------------------------------------------------------------------------------------------------
		// Nombre Columnas Cliente Empresarial.

		CommonMiddlesEmpresas.COL_TIPO_CLIENTE = "Tipo Cliente";
		CommonMiddlesEmpresas.COL_CLIENTE_EMPRESARIAL = "Cliente Empresarial";
		CommonMiddlesEmpresas.COL_TIPO_IDENTIFICACION_CLIENTE_EMPRESARIAL = "Tipo Identificación";
		CommonMiddlesEmpresas.COL_NUMERO_IDENTIFICACION_CLIENTE_EMPRESARIAL = "Id usuario";
		// -----------------------------------------------------------------------------------------------------------------------
		// Nombre Columnas Empresa.

		CommonMiddlesEmpresas.COL_NOMBRE_EMPRESA = "Nombre Empresa";
		CommonMiddlesEmpresas.COL_TIPO_IDENTIFICACION_EMPRESA = "Tipo ID Empresa";
		CommonMiddlesEmpresas.COL_NUMERO_IDENTIFICACION_EMPRESA = "Numero ID Empresa";
		// -----------------------------------------------------------------------------------------------------------------------
		// Nombre Columnas Escenario Prueba.

		CommonMiddlesEmpresas.COL_ESCENERIO_PRUEBA = "Escenario de prueba";

//	        // -----------------------------------------------------------------------------------------------------------------------
//	        // Otros.
		CommonMiddlesEmpresas.COL_NUMERO_PRODUCTO_ORIGEN = "Número producto origen";

	}
// ==========================================[inicializarSesion]=================================================================================================

	/**
	 * Inicializa la sesión y páginas requeridas según el portal (PYME o
	 * Empresarial).
	 */
	public void inicializarSesion() throws Exception {

		cargarDatosGlobales();

		if (portalType == PortalType.PYME) {

			String contratacion = SettingsRun.getGlobalData("CONTRATACION");

			String msg = null;

			if (contratacion.equalsIgnoreCase("SI")) {
				msg = inicializarSesionPymeMiddle();

				if (msg == null) {
					setearNombreColumnasMiddlesEmpresa();
					ContratacionMiddlePyme();
				} else {
					Reporter.reportEvent(Reporter.MIC_FAIL, "No se pudo Inicializar Middle");
				}
			}

			inicializarSesionPyme();

		} else {
			String contratacion = SettingsRun.getGlobalData("CONTRATACION");
			String msg = null;
			if (contratacion.equalsIgnoreCase("SI")) {
				setearNombreColumnasMiddlesEmpresa();
				ContratacionMiddleEmpresarial();

			}
			inicializarSesionEmpresarial();
		}
	}

// ==========================================[ValidacionInformeInicial]=================================================================================================	

	/**
	 * Inicializa la sesión y páginas requeridas según el portal (PYME o
	 * Empresarial).
	 */
	public void ValidacionInformeInicial() throws Exception {
		if (portalType == PortalType.PYME) {
			String msg = null;
			msg = inicializarSesionPymeMiddle();
			if (msg == null) {
				ValidacionInformeMiddlePyme();
			} else {
				Reporter.reportEvent(Reporter.MIC_FAIL, "No se pudo Inicializar Middle");
			}

		} else {

			String msg = null;
			msg = inicializarSesionMiddleEmpresarial();
			if (msg == null) {
				// Ejecuta la validación de informe usando Middle Empresarial
				ValidacionInformeMiddleEmpresa();
			} else {
				Reporter.reportEvent(Reporter.MIC_FAIL, "No se pudo Inicializar Middle Empresarial");
			}

		}
	}

// ==========================================[ValidacionInformeInicial]=================================================================================================
	/**
	 * Ambiente a selecionar de Pyme
	 * 
	 * @param nombreAmbiente
	 */
	private void normalizarAmbiente(String nombreAmbiente) {
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
	}

// ==========================================[inicializarSesionPymeMiddle]=================================================================================================
	/**
	 * Método que llama logueo en el portal Middle Pyme. Realiza el primer ingreso
	 * 
	 * @throws Exception
	 */
	private String inicializarSesionPymeMiddle() throws Exception {
		String msgError = null;

		String nombreAmbiente = SettingsRun.getGlobalData("data.ambienteMiddlePyme", "PROYECTOS_NUBE");

		this.normalizarAmbiente(nombreAmbiente);

		/*
		 * Datos Fijos Middle Login, estos datos se encuentran el archivo
		 * data.properties
		 */
		// numCli tipoDoc numDoc clave tipoTok datoTok
		DatosEmpresarial.loadLoginDataFija("0", SettingsRun.getGlobalData("data.tipoIdPyme"),
				SettingsRun.getGlobalData("data.numIdPyme"), SettingsRun.getGlobalData("data.clavepersonalPyme"),
				SettingsRun.getGlobalData("data.tipoTkPyme"), SettingsRun.getGlobalData("data.tokenEstaticoPyme"));

		// -----------------------------------------------------------------------------------------------------------------------

		// Organiza los datos del cliente Middle con un array
		String[] datosLogin = DatosEmpresarial.getLoginData();
		// Reporta los datos del logeo
		Reporter.reportEvent(Reporter.MIC_INFO,
				"*** Datos de Logueo Middle: [" + DXCUtil.arrayToString(datosLogin, " - ") + "]");
		// numCli tipoDoc numDoc clave tipoTok datoTok
		String numCliEmp = datosLogin[0];
		String tipoDoc = datosLogin[1];
		String numDoc = datosLogin[2];
		String clave = datosLogin[3];
		String tipoTok = datosLogin[4];
		String datoTok = datosLogin[5];

		// -----------------------------------------------------------------------------------------------------------------------

		// PARÁMETROS REQUERIDOS EN EL ARCHIVO EXCEL
		this.navegador = SettingsRun.getTestData().getParameter("Navegador").trim();
		this.servicio = SettingsRun.getTestData().getParameter("Servicio").trim();
		this.tipoPrueba = SettingsRun.getTestData().getParameter("Tipo prueba").trim();
		String empresa = SettingsRun.getTestData().getParameter("Nombre Empresa").trim();

		String clienteEmpresarial = SettingsRun.getTestData().getParameter("Cliente Empresarial").trim();
		String Idusuario = SettingsRun.getTestData().getParameter("Id usuario").trim();
		String tipoIdentificacion = SettingsRun.getTestData().getParameter("Tipo Identificación").trim();
		String tipoIDEmpresa = SettingsRun.getTestData().getParameter("Tipo ID Empresa").trim();
		String numeroIDEmpresa = SettingsRun.getTestData().getParameter("Numero ID Empresa").trim();

		// -----------------------------------------------------------------------------------------------------------------------

		String msgInicio = "";

		msgInicio = "*** Cliente Empresarial: [" + clienteEmpresarial + "]";
		Reporter.reportEvent(Reporter.MIC_INFO, msgInicio);
		msgInicio = "*** Tipo Identificación Usuario: [" + tipoIdentificacion + "] - Tipo Identificación Usuario: ["
				+ Idusuario + "]";
		Reporter.reportEvent(Reporter.MIC_INFO, msgInicio);
		msgInicio = "*** Tipo Servicio a contratar: [" + this.servicio + "]";
		Reporter.reportEvent(Reporter.MIC_INFO, msgInicio);
		msgInicio = "*** Empresa a contratar: [" + empresa + "]";
		Reporter.reportEvent(Reporter.MIC_INFO, msgInicio);
		msgInicio = "*** Tipo Identificación Empresa: [" + tipoIDEmpresa + "] - Numero Identificación Empresa: ["
				+ numeroIDEmpresa + "]";
		Reporter.reportEvent(Reporter.MIC_INFO, msgInicio);

		if (this.tipoPrueba.isEmpty() || this.tipoPrueba.equals(" ")) {
			Reporter.write("Falta Ingresar Datos de Tipo Prueba, Campo es Obligatorio ");
			SettingsRun.exitTestIteration();
		}

		// -----------------------------------------------------------------------------------------------------------------------
		// Guarda los Datos del cliente Middle
		PageLoginPymes1.datosMidell(numCliEmp, tipoDoc, numDoc, clave, tipoTok, datoTok);
		// Guarda el Dato del Token Middle
		PageUsuariosEmpresa1.datosMidellToke(datoTok);
		/*
		 * OPCIÓN SI DESEAS REALIZAR LA CONTRATACIÓN, 1RE REALIZA EL FLUJO DE
		 * CONTRATACIÓN DESDE MIDDLE Y PROCEDE A REALIZAR EL FLIJO EN FRONT
		 */

		// INTENTA HACER EL LOGUEO
		String evidenceDir = SettingsRun.RESULT_DIR + File.separator + "Temp";
		this.loginFrontPyme = new PageLoginPymes1(this.navegador, evidenceDir);// Carga en que navegador se va realizar
																				// la prueba
		msgError = this.loginFrontPyme.loginMiddle(); // Método para hacer el logueo en el portal Middle Pyme.
		this.loginFrontPyme.selecionambienteClose("SI");// Indicativo para el ambiente middle// Marca si esta en el
		// Ambiente Middle o FRONT

		return msgError;
	}

// ==========================================[ContratacionMiddlePyme]=================================================================================================

	/**
	 * REALIZA EL FLUJO DE PYME MIDDLE, PARA REALIZAR LA CONTRARACION DE LOS
	 * SERVICIOS A LOS DEMAS USUARIOS SI ES POR FIRMAS, SUMA 1 A LA VARIABLE
	 * CONTADOR
	 * 
	 * @throws Exception
	 */
	public void ContratacionMiddlePyme() throws Exception {

		this.controllerValiPymeMiddle = new ControllerValiPymeMiddle1(this.loginFrontPyme);
		// -----------------------------------------------------------------------------------------------------------------------

		this.numAprobaciones = SettingsRun.getTestData().getParameter("Números de Aprobaciones").trim();

		/*
		 * VALIDA SI EL NUMERO DE FIRMAS ES IGUAL A 1 SI NO ES IGUAL REALIZA LA
		 * VALIDACIÓN DE MIDDLE AL 2 CLIENTE
		 */
		boolean unaFirma = this.numAprobaciones.equals(CN_APRO_PEND);
		// CONVIERTE EL NUMERO DE FIRMAS EN LA VARIABLE numfirm
		int numfirm = Integer.parseInt(this.numAprobaciones);
		int contador = 1;

		/*
		 * VALIDA SI EL NUMERO DE FIRMAS ES IDIFERENTE A 1 REALIZA LA VALIDACIÓN DE
		 * MIDDLE AL 2 CLIENTE
		 */
		if (!unaFirma) {
			// REALIZA EL BUCLE CON LA CANTIDA DE FIRMAS A PARAMETRIZAR
			controllerValiPymeMiddle.ValidacionMiddlefirmas(1);
			do {

				if (contador != numfirm) { // AUMENTA EL VALOR DE LA VARIABLE
					contador++;
				}
				/*
				 * REALIZA EL FLUJO DE PYME MIDDLE, PARA REALIZAR LA CONTRARACION DE LOS
				 * SERVICIOS A LOS DEMAS USUARIOS SI ES POR FIRMAS, SUMA 1 A LA VARIABLE
				 * CONTADOR
				 */
				controllerValiPymeMiddle.ValidacionMiddlefirmas(contador);

				// EL BUCLE CONTINÚA MIENTRAS EL CONTADOR VARIABLE SEA MENOR QUE NUMFIRM.
			} while (contador < numfirm);

		} else {
			// -----------------------------------------------------------------------------------------------------------------------
			// REALIZA LA CONTRATACI�N, EL FLUJO DE PYME MIDDLE
			controllerValiPymeMiddle.ValidacionMiddlefirmas(1);
		}

		// -----------------------------------------------------------------------------------------------------------------------
		// Cierra la sesion en la actual que se cuentra y procede al cierre del Browser.
		this.loginFrontPyme.CerrarSesionMiddle();

	}

// ==========================================[inicializarSesionPyme]=================================================================================================

	/**
	 * Método que llama logueo en el portal Front Pyme. Realiza el primer ingreso
	 * 
	 * @throws Exception
	 */
	public void inicializarSesionPyme() throws Exception {
		String msgError = null;
		// Datos Login front Login, estos datos se encuentran el archivo del carge DATA
		DatosEmpresarial.loadLoginData("Cliente Empresarial", "Tipo Identificación", "Id usuario",
				"Clave personal o CVE", "Tipo Token", "Semilla / Valor Estático / Celular");

		String[] datosLogin = DatosEmpresarial.getLoginData();

		Reporter.reportEvent(Reporter.MIC_INFO, "*** Navegador: [" + this.navegador + "]");
		Reporter.reportEvent(Reporter.MIC_INFO,
				"*** Datos de Logueo Front: [" + Util.arrayToString(datosLogin, " - ") + "]");

		String nombreAmbiente = SettingsRun.getGlobalData("data.ambienteFrontPyme", "PROYECTOS_NUBE");

		this.normalizarAmbiente(nombreAmbiente);

		String evidenceDir = SettingsRun.RESULT_DIR + File.separator + "Temp";
		loginFrontPyme = new PageLoginPymes1(this.navegador, evidenceDir);

		DatosEmpresarial.loadLoginData("Cliente Empresarial", "Tipo Identificación", "Id usuario",
				"Clave personal o CVE", "Tipo Token", "Semilla / Valor Estático / Celular");

		loginFrontPyme.loginFront();
		loginFrontPyme.selecionambienteClose("NO");
		loginFrontPyme.changeWindow(loginFrontPyme.accedioAlPortal());
		loginFrontPyme.maximizeBrowser();
		pageOrigenPyme = new PageOrigen1(loginFrontPyme);

		String empresa = SettingsRun.getTestData().getParameter("Nombre Empresa").trim();
		msgError = pageOrigenPyme.seleccionarEmpresa(empresa);
		if (msgError != null) {
			Reporter.reportEvent(Reporter.MIC_FAIL, msgError);
			pageOrigenPyme.terminarIteracion();
		}

		String codigoCIIU = SettingsRun.getTestData().getParameter("Validar CIIU").trim();

		if (codigoCIIU.equals("SI")) {
			DXCUtil.wait(3);
			this.pageActualizacionDeDatos = new PageActualizacionDeDatos1(this.pageOrigenPyme);

			msgError = this.pageActualizacionDeDatos.InicioActualizacionDatos(false);

			if (msgError != null && !msgError.equals("Se actualizaron exitosamente los datos de su empresa")) {
				msgError = this.pageActualizacionDeDatos.MsgAlertaActualizacionDatos();
				this.pageOrigenPyme.terminarIteracion(Reporter.MIC_FAIL, msgError);
			} else {
				Reporter.reportEvent(Reporter.MIC_PASS, msgError);
			}
		}

		String validarCliente = SettingsRun.getTestData().getParameter("ValidarC360");

		if (validarCliente.equals("SI")) {

			this.ValidarCCIU(this.nitEmpre);
			Reporter.write("*** Termina la Validación CLIENTE 360 ***");
		}

		configurarParametrosGeneralesPyme();
		actualizarDatosEmpresaSiRequiere();
		inicializarPaginasDivisas(loginFrontPyme);
	}

// ==========================================[ValidacionInformeMiddlePyme]=================================================================================================

	private void ValidacionInformeMiddlePyme() throws Exception {
		this.controllerValiPymeMiddle = new ControllerValiPymeMiddle1(this.loginFrontPyme);
		// -----------------------------------------------------------------------------------------------------------------------
		controllerValiPymeMiddle.ValidacionInformeTransInternacional();
	}

// ====================================[inicializarSesionMiddleEmpresarial]==============================================================================		

	private String inicializarSesionMiddleEmpresarial() throws Exception {
		// Crea los objetos necesarios
		loginMiddleEmpresarial = new PageLoginMiddleEmpresarial(BasePageWeb.CHROME);
		middleEmpresarial = new PageMiddleEmpresarial(loginMiddleEmpresarial);

		// Obtén los datos del login
		String tipoDocumento = SettingsRun.getGlobalData("data.tipoIdEmp").trim();
		String numeroDocumento = SettingsRun.getGlobalData("data.numIdEmp").trim();
		String numeroClienteEmpresarial = SettingsRun.getGlobalData("data.numClieEmp").trim();
		String clavePersonal = SettingsRun.getGlobalData("data.clavepersonalEmp").trim();
		String token = SettingsRun.getGlobalData("data.tokenEstaticoEmp").trim();

		// Realiza el login
		String msgError = loginMiddleEmpresarial.realizarLoginMiddleEmpresarial(tipoDocumento, numeroDocumento,
				numeroClienteEmpresarial, clavePersonal, token);

//		String msgError = controlMiddleEmpresarial.realizarLoginMiddleEmpresarial();
		return msgError;
	}

	public void cierreSesionMiddle() {

		if (portalType == PortalType.PYME) {
			if (loginFrontPyme != null) {
				loginFrontPyme.closeSession();
			}

		} else {
			middleEmpresarial.cierreSesion();
			CommonMiddleEmpresarial.ESTA_LOGUEADO = false;
		}

	}
// ====================================[ContratacionMiddleEmpresarial]==============================================================================		

	/**
	 * REALIZA EL FLUJO DE EMPRESARIAL MIDDLE, PARA REALIZAR LA CONTRARACION DE LOS
	 * SERVICIOS A LOS DEMAS USUARIOS SI ES POR FIRMAS, SUMA 1 A LA VARIABLE
	 * CONTADOR
	 * 
	 * @throws Exception
	 */
	public void ContratacionMiddleEmpresarial() throws Exception {

		String[] datoDivisas = { "Internacionales" };
		controlMiddleEmpresarial.realizarParametrizacionMiddleEmpresarial(datoDivisas,
				SettingsRun.getCurrentIteration(), "Parametrizacion Contratar Servicio");
		controlMiddleEmpresarial.cierreSesionMiddleEmpresarial();
	}

// ====================================[inicializarSesionEmpresarial]==============================================================================		

	private void inicializarSesionEmpresarial() throws Exception {
		String evidenceDir = SettingsRun.RESULT_DIR + File.separator + "Temp";
		loginFrontEmpresarial = new PageLoginFrontEmpresarial(BasePageWeb.CHROME, evidenceDir);
		DatosEmpresarial.loadLoginData("Cliente Empresarial", "Tipo Identificación", "Id usuario",
				"Clave personal o CVE", "Tipo Token", "Semilla / Valor Estático / Celular");

		String[] datosLogin = DatosEmpresarial.getLoginData();

		Reporter.reportEvent(Reporter.MIC_INFO, "*** Navegador: [" + this.navegador + "]");
		Reporter.reportEvent(Reporter.MIC_INFO,
				"*** Datos de Logueo Front: [" + Util.arrayToString(datosLogin, " - ") + "]");

		String msgError = loginFrontEmpresarial.realizarLogin(
				SettingsRun.getTestData().getParameter("Cliente Empresarial").trim(),
				SettingsRun.getTestData().getParameter("Tipo Identificación").trim(),
				SettingsRun.getTestData().getParameter("Id usuario").trim(), obtenerTipoAutenticacion(),
				SettingsRun.getTestData().getParameter("Clave personal o CVE").trim(),
				SettingsRun.getTestData().getParameter("Semilla / Valor Estático / Celular").trim());
		CommonFrontEmpresarial.ESTA_LOGUEADO = true;
		if (msgError != null) {
			Reporter.reportEvent(Reporter.MIC_FAIL, msgError);
		}

		frontEmpresarial = new PageFrontEmpresarial(loginFrontEmpresarial);

		String empresa = SettingsRun.getTestData().getParameter("Nombre Empresa").trim();

		msgError = frontEmpresarial.seleccionarEmpresa(empresa);
		if (msgError != null) {
			Reporter.reportEvent(Reporter.MIC_FAIL, msgError);
			frontEmpresarial.cerrarSesionFrontEmpresarial();
			SettingsRun.exitTestIteration();
		}

		inicializarPaginasDivisas(frontEmpresarial);

		msgError = this.seleccionarTransferenciasInternacionales();
		if (msgError != null) {
			Reporter.reportEvent(Reporter.MIC_FAIL, msgError);
			SettingsRun.exitTestIteration();
		}

	}

// ====================================[ValidacionInformeMiddleEmpresa]==============================================================================

	private void ValidacionInformeMiddleEmpresa() throws Exception {

		pageInfoTransInter = new PageInformesTransInternacionales(loginMiddleEmpresarial);
		this.servicio = SettingsRun.getTestData().getParameter("Servicio").trim();
		String idEmpresa = SettingsRun.getTestData().getParameter("Numero ID Empresa").trim();
		String tipoIDUser = SettingsRun.getTestData().getParameter("Tipo Identificación").trim();
		String numIdUser = SettingsRun.getTestData().getParameter("Id usuario").trim();
		pageInfoTransInter.irAOpcionTransFerenciasInDivisas(true);
		pageInfoTransInter.InformeTransInter(this.servicio, tipoIDUser, numIdUser, idEmpresa);
	}

// ====================================[obtenerTipoAutenticacion]==============================================================================		

	/**
	 * Obtiene el tipo de autenticación a usar para login en Empresarial.
	 */
	private String obtenerTipoAutenticacion() {
		String tipoToken = SettingsRun.getTestData().getParameter("Tipo Token").trim();
		switch (tipoToken) {
		case "ESTATICO":
			return "Token Estatica";
		case "FISICO":
			return "Token Fisica";
		case "OTP":
			return "Clave Virtual";
		default:
			return tipoToken;
		}
	}

// ====================================[inicializarPaginasDivisas]==============================================================================		
	/**
	 * Inicializa las páginas de Divisas comunes para ambos portales.
	 */
	private void inicializarPaginasDivisas(BasePageWeb parentPage) {
		pageDivisas = new PageDivisas(parentPage);
		pageEnviarTransInternacional = new PageEnviarTransInternacional(parentPage);
		pageRecibirTransferenciasInternacionales = new PageRecibirTransferenciasInternacionales(parentPage);
		pageDocumentos_Y_Formularios = new PageDocumentos_Y_Formularios(parentPage);
		pageConsultatxInternacional = new PageConsultatxInternacional(parentPage);
		pageAprobInter = new PageAprobacionInter(parentPage);
		pageConf = new PageConfirmacion1(parentPage);
	}

// ====================================[configurarParametrosGeneralesPyme]==============================================================================	
	/**
	 * Configura los parámetros generales para PYME (solo si han cambiado).
	 */
	private void configurarParametrosGeneralesPyme() throws Exception {
		String tipoAbono = SettingsRun.getTestData().getParameter("Tipo de abono").trim();
		String ctaInscrita = SettingsRun.getTestData().getParameter("Cuentas Inscriptas").trim();
		String numAprobaciones = SettingsRun.getTestData().getParameter("Números de Aprobaciones").trim();
		String Idusuario = SettingsRun.getTestData().getParameter("Id usuario").trim();
		String empresa = SettingsRun.getTestData().getParameter("Nombre Empresa").trim();

		if (!Idusuario.equals(lastIdusuario) || !empresa.equals(lastempresa)
				|| !numAprobaciones.equals(lastNumAprobaciones) || !tipoAbono.equals(lastTipoAbono)
				|| !ctaInscrita.equals(lastCtaInscrita)) {

			pageAdminParametros = new PageAdminParametros1(loginFrontPyme);
			String msgError = pageAdminParametros.hacerConfiguracion(numAprobaciones, tipoAbono, ctaInscrita);
			if (msgError != null && !msgError.contains("exitosa")) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msgError);
				throw new Exception("Configuración de parámetros generales fallida: " + msgError);
			}

			lastNumAprobaciones = numAprobaciones;
			lastTipoAbono = tipoAbono;
			lastCtaInscrita = ctaInscrita;
			lastIdusuario = Idusuario;
			lastempresa = empresa;
		}
	}

// ====================================[actualizarDatosEmpresaSiRequiere]==============================================================================

	/**
	 * Actualiza datos de empresa si el flujo lo requiere (PYME).
	 */
	private void actualizarDatosEmpresaSiRequiere() throws Exception {
		String servicio = SettingsRun.getTestData().getParameter("Servicio");
		if (servicio.contains("Tx Internacionales Recibir desde el exterior")
				|| servicio.contains("Tx Internacionales Enviar al exterior")) {
			String codigoCIIU = SettingsRun.getTestData().getParameter("Validar CIIU").trim();
			if ("SI".equalsIgnoreCase(codigoCIIU)) {
				Util.wait(3);
				pageActualizacionDeDatos = new PageActualizacionDeDatos1(loginFrontPyme);
				String msgError = pageActualizacionDeDatos.InicioActualizacionDatos(false);
				if (msgError != null && !"Se actualizaron exitosamente los datos de su empresa".equals(msgError)) {
					msgError = pageActualizacionDeDatos.MsgAlertaActualizacionDatos();
					pageOrigenPyme.terminarIteracion(Reporter.MIC_FAIL, msgError);

				} else {
					Reporter.reportEvent(Reporter.MIC_PASS, msgError);
				}
			}
		}
	}

// ====================================[seleccionarTransferenciasInternacionales]==============================================================================

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

// ===========================================[TransferenciasInternacionales]===========================================================================

	/**
	 * Realiza la transacción de recibir dinero del exterior.<b>
	 * TransferenciasInternacionales
	 * 
	 * @throws Exception
	 */
	public void Recibirdinerodelexterior(boolean soloGuardar) throws Exception {
		NotificacionSMS.TIPO_NOTIFICACION = NotificacionSMS.TIPO_NOTIF_PYMES_TRANS_RECIBIR;

		String msg = "";

		String numCambiario1 = Util.left(SettingsRun.getTestData().getParameter("Numeral cambiario 1"), 4);
		String numCambiario2 = Util.left(SettingsRun.getTestData().getParameter("Numeral cambiario 2"), 4);

		String mensaje = "Los siguientes campos están vacíos: ";
		if (this.numerodereferenciaExterna.isEmpty()) {
			mensaje += "Numero de referencia Externa";
		}
		if (this.modena.isEmpty()) {
			mensaje += "Tipo Moneda, ";
		}
		if (this.concepTx.isEmpty()) {
			mensaje += "Concepto de la transferencia, ";
		}
		if (numCambiario1.isEmpty()) {
			mensaje += "Numeral cambiario 1, ";
		}
		if (numCambiario2.isEmpty()) {
			mensaje += "Numeral cambiario 2, ";
		}
		if (this.valorNumeral1.isEmpty()) {
			mensaje += "Valor numeral cambiario 1, ";
		}
		if (this.valorNumeral2.isEmpty()) {
			mensaje += "Valor numeral cambiario 2, ";
		}
		if (!mensaje.equals("Los siguientes campos están vacíos: ")) {
			// Quita la última coma y espacio
			mensaje = mensaje.substring(0, mensaje.length() - 2);
			Reporter.reportEvent(Reporter.MIC_FAIL, mensaje);
		}

		if (portalType == PortalType.PYME)
			this.inicioCrearTx(); // DEJA LA PANTALLA EN LA SELECCIÓN DEL ORIGEN

		// Espera hasta que el iframe de divisas esté disponible
		if (this.pageRecibirTransferenciasInternacionales.switchToFrameDivisas()) {

			msg = pageRecibirTransferenciasInternacionales.seleccionarTransferencia("Recibir");// Se en carga de
																								// selecionar el modulo
																								// de Divisas [Recibir]

			if (msg != null && !msg.equals(""))
				this.terminarIteracionXError(msg);
			// SELECIONAR CUENTA ORIGEN
			String msgError = pageRecibirTransferenciasInternacionales.TxInternacionalesOrigen(
					this.numerodereferenciaExterna, this.modena, this.concepTx, numCambiario1, numCambiario2,
					this.valorNumeral1, this.valorNumeral2);

			if (msgError != null) {

				this.terminarIteracionXError(msgError);
			}

			// SELECIONAR CUENTA DESTINO CUENTA
			msgError = pageRecibirTransferenciasInternacionales.seleccionarCuenta(this.tipoIdEm, this.nitEmpre,
					this.referencia1, this.referencia2);
			if (msgError != null) {
				this.terminarIteracionXError(msgError);
			}

			msgError = pageRecibirTransferenciasInternacionales.Cotizacion();
			if (msgError != null) {
				this.terminarIteracionXError(msgError);
			}

			msgError = pageRecibirTransferenciasInternacionales.Confirmacion();
			if (msgError != null) {
				this.terminarIteracionXError(msgError);
			}

			this.accionConfirmarInternacional(soloGuardar);

			if (!DatosDavivienda.IS_RISKMOTOR) {
				if (DatosDavivienda.STRATUS != null) {
					this.pageRecibirTransferenciasInternacionales.validacionSaldosStratus(this.tipoIdEm, this.nitEmpre,
							this.referencia1, this.referencia2, false);
					String saldoIni = this.pageRecibirTransferenciasInternacionales.getSaldoTotalInicialOrigen();
					String saldodis = this.pageRecibirTransferenciasInternacionales.getSaldoDispoInicialOrigen();
					String saldoFin = this.pageRecibirTransferenciasInternacionales.getSaldoTotalFinalOrigen();
					String saldoDispoFin = this.pageRecibirTransferenciasInternacionales.getSaldoTotalFinalOrigen();
					this.pageConsultatxInternacional.setSaldoTotalInicial(saldoIni);
					this.pageConsultatxInternacional.setSaldoDisInicial(saldodis);
					this.pageConsultatxInternacional.setSaldoTotalFinal(saldoFin);
					this.pageConsultatxInternacional.setSaldoDisponibleFinal(saldoDispoFin);
				}
			}

			pageRecibirTransferenciasInternacionales.setTime(pageConf.getFechaHoraTx());
		}

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

		if (portalType == PortalType.PYME)
			this.inicioCrearTx(); // DEJA LA PANTALLA [Pagos, Transferencias yotros] y seleciona el link
									// delservicio a realizar

		// Espera hasta que el iframe de divisas esté disponible
		if (this.pageEnviarTransInternacional.switchToFrameDivisas()) {

			msg = pageEnviarTransInternacional.seleccionarTransferencia("Enviar");// Se en carga de selecionar el
																					// modulode Divisas

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

			if (!DatosDavivienda.IS_RISKMOTOR) {
				if (DatosDavivienda.STRATUS != null) {
					String tipoProdUpper = this.tipoCta;
					String tipoProd = " "; // VALOR POR DEFECTO

					if (tipoProdUpper.contains("AHORROS") || tipoProdUpper.contains("ahorros"))
						tipoProd = "AHORROS";
					else if (tipoProdUpper.contains("CORRIENTE") || tipoProdUpper.contains("corriente")) // CRÉDIPLUS
						tipoProd = "CORRIENTE";

					this.pageEnviarTransInternacional.validacionSaldosStratus(this.servicio, this.tipoIdEm,
							this.nitEmpre, tipoProd, this.numCta, false);
					String saldoIni = this.pageEnviarTransInternacional.getSaldoTotalInicialOrigen();
					String saldodis = this.pageEnviarTransInternacional.getSaldoDispoInicialOrigen();
					String saldoFin = this.pageEnviarTransInternacional.getSaldoTotalFinalOrigen();
					String saldoDispoFin = this.pageEnviarTransInternacional.getSaldoTotalFinalOrigen();
					this.pageConsultatxInternacional.setSaldoTotalInicial(saldoIni);
					this.pageConsultatxInternacional.setSaldoDisInicial(saldodis);
					this.pageConsultatxInternacional.setSaldoTotalFinal(saldoFin);
					this.pageConsultatxInternacional.setSaldoDisponibleFinal(saldoDispoFin);
				}
			}

			pageDivisas.setTime(pageConf.getFechaHoraTx());
		}

	}

// ===========================================[EnviarTransferenciasInternacionalesPendAprobacion]===========================================================================
	/**
	 * Metodo se en carga de dejar la Tx de Divisas en estado pendiente de
	 * aprobación
	 * 
	 * @throws Exception
	 */
	public void EnviarTransferenciasInternacionalesPendAprobacion() throws Exception {
		NotificacionSMS.TIPO_NOTIFICACION = NotificacionSMS.TIPO_NOTIF_PYMES_TRANS_ENVIADAS;
		// Pertenece al flujo de enviar trans internacionales*
		String msg = "";

		if (this.tipoCta.equals("Cuenta de Ahorros")) {
			this.tipoCta = "CUENTA AHORROS";
		} else {
			this.tipoCta = "CUENTA CORRIENTE";
		}

		if (portalType == PortalType.PYME)
			this.inicioCrearTx(); // DEJA LA PANTALLA EN LA SELECCIÓN DEL ORIGEN

		Util.wait(5);

		msg = pageEnviarTransInternacional.seleccionarTransferencia("Enviar"); // Se en carga de selecionar el modulo de
																				// Divisas

		if (!msg.equals(""))
			this.terminarIteracionXError(msg);

		pageRecibirTransferenciasInternacionales.closeActiveIntAlert();
		msg = pageEnviarTransInternacional.seleccionarCuenta(this.servicio, this.tipoIdEm, this.nitEmpre, this.tipoCta,
				this.numCta);

		if (!msg.equals(""))
			this.terminarIteracionXError(msg);

		String numeral1 = Util.left(SettingsRun.getTestData().getParameter("Numeral cambiario 1"), 4);
		String numeral2 = Util.left(SettingsRun.getTestData().getParameter("Numeral cambiario 2"), 4);

		msg = pageEnviarTransInternacional.ingresarValores(this.tipoMoneda, this.valorcon, this.concepto, numeral1,
				numeral2, this.valorNumeral1, this.valorNumeral2, this.descripcionsin, this.tipoEnvio);

		if (!msg.equals(""))
			Reporter.reportEvent(Reporter.MIC_FAIL, msg);

		pageEnviarTransInternacional.ingresarBeneficiario(this.nombreBene, this.paisDestino, this.ciudadDestino,
				this.direccionBene, this.cuentaBene, this.infoPago, this.referenciaPago, this.tipoEnvio);

		pageEnviarTransInternacional.datosDestino(this.tipoCodigo, this.numeroCodigo, this.intermediario,
				this.tipoCodigoInter, this.numeroCodigoInter);

		pageEnviarTransInternacional.cotizacion();

		pageEnviarTransInternacional.confirmacion(intermediario);

		this.accionGuardarSinAprobarInternacional();

		pageRecibirTransferenciasInternacionales.setTime(pageConf.getFechaHoraTx());

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
		String pagoyTx = this.pageOrigenPyme.getTextMid();

		if (pagoyTx.contains("Pagos, Transferencias y otros")) {
			if (navegador.contains("CHROME")) {
				msgError = this.pageOrigenPyme.irAOpcion("Crear Transacción", "Pagos, Transferencias y otros",
						"Crear Transacción");

			} else {
				msgError = this.pageOrigenPyme.irAOpcionMoZilla("Crear Transacción", "Pagos, Transferencias y otros",
						"Crear Transacción", null, null);

			}

		} else if (pagoyTx.contains("Pagos, Transferencias y Otros")) {

			if (navegador.contains("CHROME")) {
				msgError = this.pageOrigenPyme.irAOpcion("Crear Transacción", "Pagos, Transferencias y Otros",
						"Crear Transacción");

			} else {
				msgError = this.pageOrigenPyme.irAOpcionMoZilla("Crear Transacción", "Pagos, Transferencias y Otros",
						"Crear Transacción", null, null);

			}

		} else if (pagoyTx.contains("Pagos, Transferencias y Otros")) {

			if (navegador.contains("CHROME")) {
				msgError = this.pageOrigenPyme.irAOpcion("Crear Transacción", "Pagos, Transferencias y Otros",
						"Crear Transacción");

			} else {
				msgError = this.pageOrigenPyme.irAOpcionMoZilla("Crear Transacción", "Pagos, Transferencias y Otros",
						"Crear Transacción", null, null);

			}

		}

		else if (pagoyTx.contains("Pagos, Transferencias e Inscripciones")) {

			if (navegador.contains("CHROME")) {
				msgError = this.pageOrigenPyme.irAOpcion("Crear Transacción", "Pagos, Transferencias e Inscripciones",
						"Crear Transacción");

			} else {
				msgError = this.pageOrigenPyme.irAOpcionMoZilla("Crear Transacción",
						"Pagos, Transferencias e Inscripciones", "Crear Transacción", null, null);

			}

		} else if (pagoyTx.contains("Pagos, Transferencias e inscripciones")) {

			if (navegador.contains("CHROME")) {
				msgError = this.pageOrigenPyme.irAOpcion("Crear Transacción", "Pagos, Transferencias e inscripciones",
						"Crear Transacción");

			} else {
				msgError = this.pageOrigenPyme.irAOpcionMoZilla("Crear Transacción",
						"Pagos, Transferencias e inscripciones", "Crear Transacción", null, null);

			}

		}

		else {
			if (navegador.contains("CHROME")) {
				msgError = this.pageOrigenPyme.irAOpcion("Crear Transacción", "Pagos y Transferencias",
						"Crear Transacción");

			} else {
				msgError = this.pageOrigenPyme.irAOpcionMoZilla("Crear Transacción", "Pagos y Transferencias",
						"Crear Transacción", null, null);

			}
		}

		if (msgError != null)
			this.terminarIteracionXError(msgError);

		msgError = this.pageOrigenPyme.irAServicio("Transferencias Internacionales");

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
		if (!this.servicio.contains("Recibir"))
			if (DatosDavivienda.IS_RISKMOTOR) {
				this.valorTx = pageConf.getValorFinalTx();
				if (this.valorTx != null)
					SetActivityAmount(this.valorTx.replace("$", "").replace(" ", ""));

				this.bancoDesTx = pageConf.getBancoDesTx();
				if (this.bancoDesTx != null)
					SetBancoDesTx(this.bancoDesTx);
			}
		if (soloGuardar) {
			msgRta = pageConf.guardarSinAprobarInternacional();
			pageRecibirTransferenciasInternacionales.setTime(pageConf.getFechaHoraTx());
			if (msgRta != null && !msgRta.contains("guardada"))
				this.terminarIteracionXError(msgRta);

		} else {
			int numRetosFalla = 0;
			if (DatosDavivienda.IS_RISKMOTOR)
				numRetosFalla = Integer.valueOf(SettingsRun.getTestData().getParameter("Ingresos Fallidos").trim());

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
			if (DatosDavivienda.IS_RISKMOTOR) {
				this.estadoTx = msgRta;
				this.SetEstado(msgRta);
				if (msgRta != null && Util.itemContainsAnyArrayItem(msgRta, arrMsgBuscarEstado)) {
					this.estadoTx = "Transferencia Realizada";// @estado de la transaccion
				} else if (msgRta != null && Util.itemContainsAnyArrayItem(msgRta, this.arrMsgTxDeclinada)) {
					this.estadoTx = "DECLINADA";
					this.riesgo = this.prioridaRiesgo;
					if (this.riesgo.equals("ALTO")) {
						this.numeroTx = pageConf.getNumeroDocumentoInterna();
//						SetNumApr(this.numeroTx);
					}

//					this.validarIngresoMR();

					this.terminarIteracionXError(msgRta);
				}
			}

			else {
				System.out.println("En construcción depende del tipo de la prueba");
			}

			if (msgRta != null && !msgRta.contains(PageConfirmacion1.MSG_EXITO_GUARD)
					&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_PAGO)
					&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_APROB_INTER)
					&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_APROB_INTER2)
					&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_DOC_Y_FOR_INTER)
					&& !msgRta.contains(PageConfirmacion1.MSG_EXITO_DOC_Y_FOR_COMPLETE_INF_INTER))

				if (!DatosDavivienda.IS_RISKMOTOR) {
					this.terminarIteracionXError(msgRta);
				} else {
					this.terminarIteracionXError(msgRta);
				}
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

		if (DatosDavivienda.IS_RISKMOTOR) {
			this.estadoTx = msgRta;
			this.SetEstado(msgRta);
			if (msgRta != null && Util.itemContainsAnyArrayItem(msgRta, arrMsgBuscarEstado)) {
				this.estadoTx = "Transferencia Realizada";// Buscar estado de la transaccion
			} else if (Util.itemContainsAnyArrayItem(msgRta, arrMsgTxDeclinada))
				this.estadoTx = "DECLINADA";

		} else {
			System.out.println("En construcción, depende del tipo de la prueba");
		}
		String msg = null;

		if (msgRta != null && msgRta.contains(PageConfirmacion1.MSG_EXITO_DOCYFOR)) {

			msg = this.pageDocumentos_Y_Formularios.closeActiveIntAlert();

			if (msgRta.contains(PageConfirmacion1.MSG_EXITO_DOCYFOR)) {
				Reporter.reportEvent(Reporter.MIC_INFO,
						"Tiene al menos una operación que requiere adjuntar documentos.");

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

	/**
	 * Realiza la transacción seleccionada, cubriendo todos los casos de Divisas
	 * (internacionales, formularios y consultas) para ambos portales. Determina si
	 * es envío en línea o pendiente de aprobación, y ejecuta la lógica de
	 * aprobación si corresponde.
	 */
	public void transar() throws Exception {
		boolean primeroGuardar = this.tipoPrueba.equals("Tx Pend Aprobación");
		String msg = null;
		switch (this.servicio) {
		case "Tx Internacionales Recibir desde el exterior":
			this.Recibirdinerodelexterior(primeroGuardar);
			break;
		case "Tx Internacionales Enviar al exterior":
			this.EnviarTransferenciasInternacionales(primeroGuardar);
			break;
		case "Tx Internacionales Enviar al exterior Pendiente Aprobación":
			if (portalType == PortalType.PYME)
				this.inicioCrearTx();
			this.EnviarTransferenciasInternacionalesPendAprobacion();
			break;
		case "Divisas Documentos y Formularios":

			if (portalType == PortalType.PYME)
				this.inicioCrearTx();

			this.pageDocumentos_Y_Formularios = new PageDocumentos_Y_Formularios(this.pageDivisas);

			if (this.pageDivisas.switchToFrameDivisas()) {

				msg = this.pageDocumentos_Y_Formularios.IralModuloDocumetosYFormularios(this.tipoPrueba, this.servicio,
						this.fechaTx, this.horaTx, this.tipoMoneda);

				if (msg != null) {
					if (!msg.isEmpty())
						Reporter.reportEvent(Reporter.MIC_FAIL, msg);
					this.terminarIteracionXError(msg);
				}

				// Datos iniciales
				String concepTx = SettingsRun.getTestData().getParameter("Concepto de la transferencia").trim();
				String numCambiario1 = Util.left(SettingsRun.getTestData().getParameter("Numeral cambiario 1"), 4);
				String valorNumeral1 = SettingsRun.getTestData().getParameter("Valor numeral cambiario 1");
				String numCambiario2 = Util.left(SettingsRun.getTestData().getParameter("Numeral cambiario 2"), 4);
				String valorNumeral2 = SettingsRun.getTestData().getParameter("Valor numeral cambiario 2");

				// Datos Documentos y formularios
				String tipoOperacion = SettingsRun.getTestData().getParameter("Tipo de operación");
				String desInversion = SettingsRun.getTestData().getParameter("Destino de la inversión");
				String opciondeinversion = SettingsRun.getTestData().getParameter("Opción de inversión");
				String deducciones = SettingsRun.getTestData().getParameter("Deducciones");

				// Datos Documentos y formularios A cambiar
				String cambiarConcepto = SettingsRun.getTestData().getParameter("Cambiar Concepto de la transferencia");
				String conceptoAcambiar = SettingsRun.getTestData()
						.getParameter("Concepto de la transferencia A Cambiar");
				String numeroDeposito = SettingsRun.getTestData().getParameter("Número de depósito 1");
				String numeroFacturaoReferDeclaracion = SettingsRun.getTestData()
						.getParameter("Número de declaración 1");
				String cambiarlistnumeralOperacion_Numeral1 = SettingsRun.getTestData()
						.getParameter("Cambiar Numeral cambiario 1");
				String numeral1Acambiar = SettingsRun.getTestData().getParameter("Numeral cambiario A Cambiar 1");
				String cambiarDatosDescripciondelaoperacion = SettingsRun.getTestData()
						.getParameter("Cambiar Datos Descripción de la operación");

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
				String codigociudadReptora = SettingsRun.getTestData()
						.getParameter("Empresa receptora - Código ciudad");
				String codigoCIIUReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Código CIIU");
				String telefonoReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Teléfono");
				String direccionReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Dirección");
				String correoReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Correo electrónico");
				String sectorReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Sector");
				String tipodeempresaReptora = SettingsRun.getTestData()
						.getParameter("Empresa receptora - Tipo de empresa");
				String superintendenciaReptora = SettingsRun.getTestData()
						.getParameter("Empresa receptora - Superintendencia de vigilancia");
				String actividadReptora = SettingsRun.getTestData().getParameter("Empresa receptora - Actividad");
				String tipoderegimenReptora = SettingsRun.getTestData()
						.getParameter("Empresa receptora - Tipo de régimen");
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

				// Datos Documentos y formularios A cambiar y agregar
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
				String ValorNumeral1Camb = SettingsRun.getTestData()
						.getParameter("Valor numeral cambiario A Cambiar 1");
				String validacionAdicionar = SettingsRun.getTestData().getParameter("Validar Numerales");

				// Validacion Dian
				String validacionDian = SettingsRun.getTestData().getParameter("Validacion - DIAN");

				String cargueDocu = SettingsRun.getTestData().getParameter("Cargue Archivo Documentos");

				// Divide la ruta en un array de strings separados por comas
				String[] rutaArch = cargueDocu.split(",");
				msg = this.pageDocumentos_Y_Formularios.DatosDocumetosYFormularios(concepTx, tipoOperacion,
						desInversion, opciondeinversion, this.valorTx, numCambiario1, valorNumeral1, numCambiario2,
						valorNumeral2, deducciones, cambiarConcepto, conceptoAcambiar, numeroDeposito,
						numeroFacturaoReferDeclaracion, cambiarlistnumeralOperacion_Numeral1, numeral1Acambiar,
						cambiarDatosDescripciondelaoperacion, numerodelprestamooaval,
						nombredelacreedoroeldeudoroavalista, nombredeldeudoroacreedorAvaladoobeneficiarioresidente,

						tipodeidentificacióndeldeudor, numerodeidentificaciondeldeudor, digitodeverificacion,
						monedaestipulada, valormonedaestipulada, tasadecambiomoneda, cambiarValornumeralcambiario1,
						ValorNumeral1Camb, validacionAdicionar, validacionDian, rutaArch);

				if (msg != null && !msg.contains(
						"Documentos enviados exitosamente. Davivienda validará la información recibida y en caso de presentar inconsistencias informará vía correo electrónico. Por favor haga seguimiento de su operación en la opción de consultas y verifique el estado de su trámite.")) {
					this.terminarIteracionXError(msg);
				}
			}
			break;
		case "Consulta Tx Internacionales Validar Estado":
			if (portalType == PortalType.PYME)
				this.inicioCrearTx();

			msg = this.pageConsultatxInternacional.ConsultaNumtx(this.tipoPrueba, this.nombreEmpre, this.servicio,
					usuario, tipoConstaTxRealizadas, this.ordenanteBeneficiario, this.tipoTranferencia, this.estado,
					this.tipoMoneda, this.fechaTx, this.horaTx, this.fechaDesde, this.fechaHasta, this.valorTx);

			if (msg != null) {
				this.terminarIteracionXError(msg);
			}

			break;
		default:
			this.terminarIteracionXError("Servicio no soportado en flujo Divisas: " + this.servicio);
			throw new UnsupportedOperationException("Servicio no soportado en flujo Divisas: " + this.servicio);
		}

		// Flujo de aprobación pendiente para internacionales
		if (primeroGuardar && this.servicio.contains("Internacionales") && !this.servicio.contains("Aprobación")
				|| primeroGuardar && this.servicio.contains("Internacionales") && DatosDavivienda.IS_RISKMOTOR) {
			boolean desdeelDetalle = this.desdeElDetalle != null && this.desdeElDetalle.equalsIgnoreCase("SI");
			this.aprobarTxPendienteIntern(desdeelDetalle);
		}

		if ((this.servicio.contains("Internacionales") || this.servicio.contains("Divisas"))
				&& !this.servicio.contains("Aprobación") && !this.servicio.contains("Validar")) {

			this.pageConsultatxInternacional = new PageConsultatxInternacional(this.pageDivisas);
			if (this.fechaTx == null || this.fechaTx.trim().isEmpty()
					|| this.horaTx == null && this.horaTx.trim().isEmpty()) {
				this.fechaTx = SettingsRun.getTestData().getParameter("Fecha tx");
				this.horaTx = SettingsRun.getTestData().getParameter("Hora tx");
			}

			this.estado = SettingsRun.getTestData().getParameter("Estado");

			msg = this.pageConsultatxInternacional.ConsultaNumtx(this.tipoPrueba, this.nombreEmpre, this.servicio,
					this.usuario, tipoConstaTxRealizadas, this.ordenanteBeneficiario, this.tipoTranferencia,
					this.estado, this.tipoMoneda, this.fechaTx, this.horaTx, this.fechaDesde, this.fechaHasta,
					this.valorTx);
			if (msg != null) {
				this.terminarIteracionXError(msg);
			}
			if (DatosDavivienda.STRATUS != null)
				this.pageConsultatxInternacional.ValidacionesStratusConsulta();

			this.pageConsultatxInternacional.getDriver().switchTo().defaultContent();

		} else if ((this.servicio.contains("Internacionales") || this.servicio.contains("Divisas"))
				&& this.servicio.contains("Aprobación") && !this.servicio.contains("Validar Estado")) {
			this.pageConsultatxInternacional = new PageConsultatxInternacional(this.pageDivisas);
			if (this.fechaTx == null || this.fechaTx.trim().isEmpty()
					|| this.horaTx == null && this.horaTx.trim().isEmpty()) {
				this.fechaTx = SettingsRun.getTestData().getParameter("Fecha tx");
				this.horaTx = SettingsRun.getTestData().getParameter("Hora tx");
			}
			this.estado = SettingsRun.getTestData().getParameter("Estado");

			msg = this.pageConsultatxInternacional.ConsultaNumtx(this.tipoPrueba, this.nombreEmpre, this.servicio,
					usuario, tipoConstaTxRealizadas, this.ordenanteBeneficiario, this.tipoTranferencia, this.estado,
					this.tipoMoneda, this.fechaTx, this.horaTx, this.fechaDesde, this.fechaHasta, this.valorTx);

			if (msg != null) {
				this.terminarIteracionXError(msg);
			}
		}
	}

	// ==================== Manejo de errores y utilidades ====================

	/**
	 * Maneja el término de iteración por error, guarda evidencia y reporta el
	 * fallo.
	 * 
	 * @param msgError mensaje de error a reportar.
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
			Reporter.reportEvent(Reporter.MIC_FAIL, msgError);
		}
		if (frontEmpresarial != null) {
			frontEmpresarial.cerrarSesionFrontEmpresarial();
			SettingsRun.exitTestIteration();

		} else if (loginFrontPyme != null) {
			loginFrontPyme.terminarIteracion(); // Cerrar tanto en empresarial como Pyme
		}
	}

	public void cerrarSesionDependiemdoPortal() throws Exception {
		if (portalType == PortalType.PYME) {
			if (loginFrontPyme != null) {
				loginFrontPyme.closeSession();
			}

		} else {
			if (frontEmpresarial != null) {
				frontEmpresarial.cerrarSesionFrontEmpresarial();
			}

		}

	}

// ===========================================[getTransaccion]===========================================================================

	/**
	 * Retorna el valor de "Transacción", dependiendo del tipo de prueba y del
	 * servicio, este dato debería ser escrito en el Set de Motor de Riesgo.
	 */
	public String getTransaccion(String tipoPrueba, String servicio) throws Exception {

		String transaccion = tipoPrueba;

		if (tipoPrueba.equals(TP_PEND_APR)) {

			if (this.tipoPrueba.equals(TP_PEND_APR) && this.desdeElDetalle.contains("NO"))
				transaccion = MotorRiesgo.TX_EMP_APROB_TX;

			if (this.tipoPrueba.equals(TP_PEND_APR) && this.desdeElDetalle.contains("SI"))
				tipoPrueba = TP_EN_LINEA;

			if (servicio.equals("Retiro sin Tarjeta"))
				transaccion = MotorRiesgo.TX_PYM_APROB_RET;
		}

		if (tipoPrueba.equals(TP_EN_LINEA)) {
			switch (this.servicio) {

			case "Nómina":
			case "Pago de Nóminas":
				transaccion = MotorRiesgo.TX_EMP_PAGO_NOMI;
				break;
			case "Pago a Proveedores":
			case "Proveedores":
				transaccion = MotorRiesgo.TX_EMP_PAGO_PROV;
				break;
			case "Pago de Servicios":
			case "Servicios":
				transaccion = MotorRiesgo.TX_EMP_PAGO_SERVIC;
				break;
			case "Transferencia NIT Propio":
			case "Mismo NIT":
				transaccion = MotorRiesgo.TX_EMP_TF_MISMONIT;
				break;
			case "Transferencias Cuenta Inscrita":
			case "Cuenta Inscrita":
				transaccion = MotorRiesgo.TX_EMP_TF_TERCEROS;
				break;
			case "Transferencias Cuenta No Inscrita":
			case "Cuenta No Inscrita":
				transaccion = MotorRiesgo.TX_EMP_TF_CTANO;
				break;
			case "Tx Internacionales Enviar al exterior":
				transaccion = MotorRiesgo.TX_PYM_ENVIAR_AL_EXTERIOR;
				break;
			case "Tx Internacionales Recibir desde el exterior":
				transaccion = MotorRiesgo.TX_PYM_RECIBIR_DESDE_EL_EXTERIOR;
				break;
			}
		}
		return transaccion;
	}

// ===========================================[TipoProd]===========================================================================

	/**
	 * Metodo retorna el tipo de producto
	 * 
	 * @param tipoProdUpper
	 * @return
	 */
	public String TipoProd(String tipoProdUpper) {
		String tipoProd = "No Aplica";

		if (tipoProdUpper.contains("AHORROS") || tipoProdUpper.contains("ahorros") || tipoProdUpper.contains("Ahorros")
				|| tipoProdUpper.contains("ahorro") || tipoProdUpper.contains("Ahorro"))
			tipoProd = "Cuenta Ahorros";
		else if (tipoProdUpper.contains("CORRIENTE") || tipoProdUpper.contains("corriente")
				|| tipoProdUpper.contains("Corriente"))
			tipoProd = "Cuenta Corriente";
		else if (tipoProdUpper.contains("CRÉDITO") || tipoProdUpper.contains("Crédito")
				|| tipoProdUpper.contains("crédito") || tipoProdUpper.contains("CREDITO")
				|| tipoProdUpper.contains("Credito") || tipoProdUpper.contains("credito")) // CRÉDIPLUS
			tipoProd = "Tarjeta Crédito";

		return tipoProd;
	}

// ===========================================[Stratus]===========================================================================

	/**
	 * Realiza el Login de Stratus de Forma general
	 * 
	 * @throws Exception
	 */
	public void LoginStratus() throws Exception {
		// Obtiene los datos desde el archivo Dataproperties.
		String strusu = SettingsRun.getGlobalData("data.usuarioStratus");
		String strpass = SettingsRun.getGlobalData("data.claveStratus");

		// Se comentan las siguientes 3 lineas para que no ejecute en stratus
		if (DatosDavivienda.STRATUS == null) {
			// 1.1: Abrir WinAppDriver si no se encuentra abierto
			DXCUtil.startWinAppDriver();
			DatosDavivienda.STRATUS = new Stratus(strusu, strpass, "EMPRESAS");
			Reporter.reportEvent(Reporter.MIC_INFO, "Se cargan datos para logueo" + " de Stratus");
		}

	}

// ============================================[logueoC360]===========================================================================

	/**
	 * Metodo Realiza logueoC360
	 * 
	 * @throws Exception
	 */
	public PageInicioC360 logueoC360() throws Exception {
		// INTENTA HACER EL LOGUEO - C360
		Reporter.write("*** Validar CLIENTE 360 ***");
		this.pageLoginC360 = new PageLogin(BasePageWeb.CHROME);
		this.pageInicioC360 = new PageInicioC360(pageLoginC360);
		this.pageEmpresasC360 = new PageEmpresas(pageLoginC360);
		this.pageLoginC360.login360(SettingsRun.getGlobalData("data.c360User"),
				SettingsRun.getGlobalData("data.c360Pwd"));
		return this.pageInicioC360;
	}

// ============================================[ValidarCCIU]===========================================================================

	/**
	 * Valida en cliente 360 el CCIU de la empresa
	 * 
	 * @param numeroIDEmpresa
	 * @throws Exception
	 */
	public void ValidarCCIU(String numeroIDEmpresa) throws Exception {

		String reportMsg = null;

		this.pageDivisas.BonotesTecla("ALTTAB");

		this.pageInicioC360.irAModulo(PageInicioC360.MOD_PAGINA_INICIAL);

		reportMsg = this.pageEmpresasC360.buscarEmpresaC360(numeroIDEmpresa);

		if (!reportMsg.isEmpty()
				&& (!reportMsg.contains("El cliente no ha actualizado") && !reportMsg.contains("ACTUALIZADO")))
			this.pageInicioC360.terminarIteracion(Reporter.MIC_NOEXEC, "[ERROR DATA] " + reportMsg);

		Reporter.reportEvent(Reporter.MIC_PASS,
				"Se ha encontrado la empresa con el número de identificación [" + numeroIDEmpresa + "]");

//		String masIfEmpres[] = this.pageEmpresasC360.getMasInfoClienteEmpresa();
//
//		for (String masIfEmprepos : masIfEmpres) {
//			System.out.println(masIfEmprepos);
//		}

		this.pageDivisas.BonotesTecla("ALTTAB");

		this.pageInicioC360.cerrarSesion();
	}

// ============================================[Datos a utilizar a Motor]===========================================================================

	public void SetPrioridaMr(String riesgo) throws Exception {
		this.prioridaRiesgo = riesgo;

	}

	public void SetuserAgent(String user) throws Exception {
		this.userAgent = user;
	}

	public void SetActivityAmount(String activityAmountx) {

		this.activityAmount = activityAmountx;
	}

	public void SetBancoDesTx(String bancoDes) {
		this.bancoDesMotor = bancoDes;
	}

	public void SetEstado(String estado) {
		this.estado = estado;
	}

	public String[] getCuentasMotor() {
		return cuentasDesMotor;
	}

// ============================================[Destructor]===========================================================================

	/**
	 * Destructor. Útil para limpiar o cerrar recursos si es necesario.
	 */
	public void destroy() {
		// Implementar si hay recursos a liberar
	}

}
