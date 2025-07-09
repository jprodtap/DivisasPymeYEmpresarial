package pages.actios.Pyme;

import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;



import dxc.util.DXCUtil;
import library.core.BasePageWeb;
import library.common.Util;
import library.reporting.Evidence;
import library.reporting.Reporter;
import library.settings.SettingsRun;

public class PageActualizacionDeDatos1 extends BasePageWeb {

	public PageActualizacionDeDatos1(BasePageWeb parentPage) {
		super(parentPage);
	}

	PageOrigen1 pageOrigen = null;
	PageConfirmacion1 pageConfirmacion = null;
	PageActualizacionCalendario1 pageCalendario;
	PageActualizacionFileNet1 pageFileNet;

	By locUbicacionYRepresentante = By.id("Item1");
	By locInformacionFinanciera = By.id("Item2");
	By locInformacionSociosAccionistas = By.id("Item3");

	By locModInfoEmpresa = By.id("cphCuerpo_BtnModificarEmpresa");
	By locModInfoRepresentante = By.id("cphCuerpo_BtnModificarRepresentante");

	// Seccion de Datos de Ubicaci�n y de Representante Legal
	By locDireccion = By.id("cphCuerpo_dropEditarDir1");
	By locNumDireccion = By.id("cphCuerpo_txtEditarDir2");
	By locNumeral = By.id("cphCuerpo_txtEditarDir3");
	By locGuion = By.id("cphCuerpo_txtEditarDir4");

	By locMunicipio = By.id("cphCuerpo_dropMunicipios");
	String xpathPredecirMunicipio = "//*[@id=\"cphCuerpo_dropMunicipios\"]/option[contains(text(), 'MUNICIPIO')]";
	By locPrefijoTelefono = By.id("cphCuerpo_txtEditarTel1");
	By locTelFijo = By.id("cphCuerpo_txtEditarTel2");
	By locExt = By.id("cphCuerpo_txtEditarTel3");

	By locCorreo = By.id("cphCuerpo_txtEditarEmail");
	By locInfpoAdicional = By.id("cphCuerpo_TxtEditarInfoAdicional");
	By locNumeroCelular = By.id("cphCuerpo_txtEditarCelular");
	By btnAceptarDatosEmpresa = By.id("cphCuerpo_BtnGuardarDatosEmpresa");

	By locMsjDeAyuda = By.xpath("//*[@id='cphCuerpo_Contenido1']/table/tbody/tr[14]/td[4]/div/img");

	// Campo y CheckBox's de Aceptar Terminos
	By locDeclaraciones = By.id("cphCuerpo_txtDeclaraciones");
	By checkRecursos = By.id("cphCuerpo_RbtRecursos");
	By checkAutorizacion = By.id("cphCuerpo_RbtAutorizacion");
	By checkDeclaracion = By.id("cphCuerpo_RbtDeclaracion");

	// Seleccion de Representante Legal
	By btnAceptarRepresentante = By.xpath("//*[@id='cphCuerpo_BtnGuardarRepresentante']");
	By btnCancelarRepresentante = By.xpath("//*[@id='cphCuerpo_BtnCancelarRepresentante']");
	String xPathCheckRepresentante = "//td[contains(text(),'NUM_ID')]//preceding-sibling::td/input";
	String xPathNumContactoRepresentante = "//td[contains(text(),'NUM_ID')]//following-sibling::td[2]/select";
	String xPathCorreoRepresentante = "//td[contains(text(),'NUM_ID')]//following-sibling::td[3]/select";

	// Seecion de Actividad Economica
	By locTipoEmpresa = By.id("cphCuerpo_dropTipoEmpresa");
	By locCodigoCIIU = By.xpath("//*[@id='cphCuerpo_Contenido1']/table/tbody/tr[14]/td[4]/span/a/span[1]");
	String xpathCodigoCIIU = "//*[@class='ui-menu-item-wrapper'][contains(text(),'NUM_CIIU')]";

	// Seccion de Informacion Financiera
	By locCheckSocios5Porciento = By.id("cphCuerpo_ChkEmpresaSocios");
	By locTotalIngresosOpera = By.id("cphCuerpo_txtTotalIngOperac");
	By locOtrosIngresos = By.id("cphCuerpo_txtOtrosIngresos");
	By locTotalEgresos = By.id("cphCuerpo_txtTotalEgresos");
	By locActivoCorriente = By.id("cphCuerpo_txtActivoCorriente");
	By locOtrosActivos = By.id("cphCuerpo_txtOtrosActivos");
	By locPasivoLargoPlazo = By.id("cphCuerpo_txtPasivoLargoPlazo");

	By locTotalIngNoOperac = By.id("cphCuerpo_txtTotalIngNoOperac");
	By locActivoFijo = By.id("cphCuerpo_txtActivoFijo");
	By locPasivoCorriente = By.id("cphCuerpo_txtPasivoCorriente");

	By locTotalIngresos = By.id("cphCuerpo_txtTotalIngresos");
	By locTotalActivos = By.id("cphCuerpo_txtTotalActivos");
	By locTotalPasivos = By.id("cphCuerpo_txtTotalPasivo");

	By locMsjDeAyuda1 = By.xpath("//*[@id='cphCuerpo_Contenido2']/table/tbody/tr[1]/td/div/img");
	By locMsjDeAyuda2 = By.xpath("//*[@id='cphCuerpo_Contenido2']/table/tbody/tr[2]/td[4]/div/img");
	By locMsjDeAyuda3 = By.xpath("//*[@id='cphCuerpo_Contenido2']/table/tbody/tr[2]/td[2]/div/img");
	By locMsjDeAyuda4 = By.xpath("//*[@id='cphCuerpo_Contenido2']/table/tbody/tr[9]/td/div/img");

	// Seccion de Informacion Financiera (Operaciones Internacionales)
	By locRadioOperacionInternacionalSI = By.id("cphCuerpo_RbtOperacionInternacionalSI");
	By locRadioOperacionInternacionalNO = By.id("cphCuerpo_RbtOperacionInternacionalNO");

	String xpathRadioTipoOperacion = "//*[@id='cphCuerpo_RbtTIPO_OPERACION']";
	By locMontoMensual = By.id("cphCuerpo_txtMontoMensual");
	By locOtroCual = By.id("cphCuerpo_txtOtraOperacion");
	By locPaisDestinoOrigen = By.id("cphCuerpo_dropPais");
	By locNombreRemitente = By.id("cphCuerpo_txtNombreRemitente");
	By locNumeroCuenta = By.id("cphCuerpo_txtCuenta");
	By locCiudad = By.id("cphCuerpo_dropCiudad");
	By locBanco = By.id("cphCuerpo_txtBanco");
	By locMoneda = By.id("cphCuerpo_dropMoneda");

	// Informacion de Socios Accionistas
	By locNITCasaMatriz = By.id("cphCuerpo_TxtNitCasaMatriz");
	By locAgregarSocio = By.id("cphCuerpo_BtnAgregarSocio");
	By locAgregarRepresentante = By.id("cphCuerpo_BtnAgregarRepresentante");
	By btnConfirmarSocio = By.id("cphCuerpo_BtnConfirmar");
	By btnConfirmarRepresentante = By.id("cphCuerpo_BtnAgregarControlante");

	/* Informacion de Agregar Socio */
	By locTipoIdSocio = By.id("cphCuerpo_DdlTipoID");
	By locNumeroIdSocio = By.id("cphCuerpo_TxtIdentificacionSocio");
	By locNombreSocio = By.xpath("//*[@id='cphCuerpo_TxtNombreSocio']");
	By locPorcentajeParticipacion = By.id("cphCuerpo_TxtParticipacion");
	By locApellidoSocio = By.id("cphCuerpo_TxtApellidosSocio");
	By locPaisSocio = By.id("cphCuerpo_DLPaisCreacionSocio");

	By locTributacionSi = By.id("cphCuerpo_RbtTributacionSi");
	By locTributacionNo = By.id("cphCuerpo_RbtTributacionNo");
	By locPaisTribucion = By.id("cphCuerpo_DdlPaisSocio");
	By locTin = By.id("cphCuerpo_TxtTin");

	By locPoliticoSI = By.id("cphCuerpo_RbtPoliticoSI");
	By locPoliticoNo = By.id("cphCuerpo_RbtPoliticoNO");

	By locReconocidoSi = By.id("cphCuerpo_RbtReconocidoSI");
	By locReconocidoNo = By.id("cphCuerpo_RbtReconocidoNO");

	By locInterncionalSi = By.id("cphCuerpo_RbtOinternacionalSI");
	By locInternacionalNo = By.id("cphCuerpo_RbtOinternacionalNO");

	By locExpuestoSi = By.id("cphCuerpo_RbtPExpuestoSI");
	By locExpuestoNo = By.id("cphCuerpo_RbtPExpuestoNO");

	By locCargoSocio = By.id("cphCuerpo_ddlCargoPublicoSocio");
	By locFechaVinculacionSocio = By.id("cphCuerpo_txtSocioFechaVinculacion");
	By locFechaDesvinculacionSocio = By.id("cphCuerpo_txtSocioFechaDesVinculacion");
	By locActualVinculacionSocio = By.id("cphCuerpo_chkVinculadoSocio");

	By locFamiiarSi = By.id("cphCuerpo_RbtEsfamiliarSI");
	By locFamiliarNo = By.id("cphCuerpo_RbtEsfamiliarNO");

	By locParentezco = By.id("cphCuerpo_txtParentezco");
	By locNombreFamiliar = By.id("cphCuerpo_TxtNombreFamiliar");
	By locPrimerApellidofamiliar = By.id("cphCuerpo_TxtPApellidoFamiliar");
	By locSegundoApellidoFamiliar = By.id("cphCuerpo_TxtSApellidoFamiliar");

	/* Informacion de Agregar Socio NIT */
	By locCuerpoNIT = By.id("cphCuerpo_block1");
	By locDetallarNIT = By.id("cphCuerpo_RbRegistros__Page_LbDetallarPrincipla_0");

	By locTipoIdSocioNIT = By.id("cphCuerpo_DdlTipoID2");
	By locNumeroIdSocioNIT = By.id("cphCuerpo_TxtNumeroID2");
	By locNombreSocioNIT = By.id("cphCuerpo_TxtNombreRazon2");
	By locApellidoSocioNIT = By.id("cphCuerpo_TxtApellidos2");
	By locPorcentajeParticipacionNIT = By.id("cphCuerpo_TxtParticipacion2");
	By locPaisSocioNIT = By.id("cphCuerpo_DdlPais2");

	By locTributacionSiNIT = By.id("cphCuerpo_RbtTributacionSi2");
	By locTributacionNoNIT = By.id("cphCuerpo_RbtTributacionNo2");
	By locPaisTribucionNIT = By.id("cphCuerpo_DdlPaisFiscalSociosDet2");
	By locTinNIT = By.id("cphCuerpo_TxtTINSociosDet2");

	By locBtnConfirmarSocioNIT = By.id("cphCuerpo_BtnConfirmar2");
	By locBtnContinuarSocioNIT = By.id("cphCuerpo_BtnContinuar_2");
	By locBtnConfirmarPageSocioNIT = By.id("cphCuerpo_BtnConfirmar__Page");

	/* Informacion de Agregar Representante */
	By locTipoIdRepresentante = By.id("cphCuerpo_DdlTipoIDControlante");
	By locNumeroIdRepresentante = By.id("cphCuerpo_TxtIdentificacionControlante");
	By locNombreRepresentante = By.id("cphCuerpo_TxtNombreControlante");
	By locApellidosRepresentante = By.id("cphCuerpo_TxtApellidosControlante");
	By locCargoRepresentante = By.id("cphCuerpo_ddlCargoControlante");

	By locTributacionSiContol = By.id("cphCuerpo_RbtControlanteTributacionSi");
	By locTributacionNoContol = By.id("cphCuerpo_RbtControlanteTributacionNo");
	By locPaisTribucionContol = By.id("cphCuerpo_ddlPaisControlante");
	By locTinContol = By.id("cphCuerpo_TxtTinControlante");

	By locPoliticoSIContol = By.id("cphCuerpo_RbtControlantePoliticoSI");
	By locPoliticoNoContol = By.id("cphCuerpo_RbtControlantePoliticoNO");

	By locReconocidoSiContol = By.id("cphCuerpo_RbtControlanteReconocidoSI");
	By locReconocidoNoContol = By.id("cphCuerpo_RbtControlanteReconocidoNO");

	By locInterncionalSiContol = By.id("cphCuerpo_RbtControlanteOinternacionalSI");
	By locInternacionalNoContol = By.id("cphCuerpo_RbtControlanteOinternacionalNO");

	By locExpuestoSiContol = By.id("cphCuerpo_RbtControlantePExpuestoSI");
	By locExpuestoNoContol = By.id("cphCuerpo_RbtControlantePExpuestoNO");

	By locCargoSocioContol = By.id("cphCuerpo_ddlCargoPublicoControlante");
	By locFechaVinculacionSocioContol = By.id("cphCuerpo_txtControFechaVinculacion");
	By locFechaDesvinculacionSocioContol = By.id("cphCuerpo_txtControFechaDesVinculacion");
	By locActualVinculacionSocioContol = By.id("cphCuerpo_chkVinculadoControlante");

	By locFamiiarSiContol = By.id("cphCuerpo_RbtControlanteEsfamiliarSI");
	By locFamiliarNoContol = By.id("cphCuerpo_RbtControlanteEsfamiliarNO");

	By locParentezcoContol = By.id("cphCuerpo_txtParentezcoControlante");
	By locNombreFamiliarContol = By.id("cphCuerpo_TxtNombreFamiliarControlante");
	By locPrimerApellidofamiliarContol = By.id("cphCuerpo_TxtPApellidoFamiliarControlante");
	By locSegundoApellidoFamiliarContol = By.id("cphCuerpo_TxtSApellidoFamiliarControlante");

	// Xpath Eliminar Socio
	String xpathNumIDEliminarSocio = "//*[@id='cphCuerpo_gvSocios']/tbody/tr/td[2][contains(text(),'NUM_ID')]";
	String xpathBtnEliminarSocio = "//following-sibling::td[3]/a[2]";

	// Xpath Eliminar Representante
	String xpathNumIDEliminarRepre = "//*[@id='cphCuerpo_gvJunta']/tbody/tr/td[2][contains(text(),'NUM_ID')]";
	String xpathBtnEliminarRepre = "//following-sibling::td[3]/a[2]";

	By locMsgAlerta = By.xpath("//*[@id='lblMasterAlerta']");

	String infoEmpresa = null;
	String infoRepresentante = null;
	String infoActividadEconomica = null;
	String informacionFinanciera = null;
	String infoSociosAccionistas = null;
	String infoAgregarSocio = null;
	String infoAgregarRepresentante = null;
	String infoEliminarSocio = null;
	String infoEliminarRepresentante = null;

	String infoDireccion = null;
	String infoNumeroDireccion = null;
	String infoNumeral = null;
	String infGuion = null;
	String infoMunicipio = null;
	String infoTelefonoPrefijo = null;
	String infoTelFijo = null;
	String infoExt = null;
	String infoCorreo = null;
	String infoAdicional = null;
	String infoNumeroCelular = null;

	// Informacion Socio
	String infoNITCasaMatriz = null;
	String infoTipoIdSocio = null;
	String infoNombreSocio = null;
	String infoApellidoSocio = null;
	String infoParticipacionSocio = null;
	String infoNumeroIdSocio = null;
	String infoNacionalidadSocio = null;
	String infoNumeroIdSocioEliminar = null;

	// Informacion Representante
	String infoTipoIdRepresentante = null;
	String infoNombreRepresentante = null;
	String infoApellidosRepresentante = null;
	String infoCargoRepresentante = null;
	String infoNumeroIdRepresentante = null;
	String infoNumeroIdRepresentanteEliminar = null;
	String infoDeclaraciones = null;

	String infoTributacionFiscal = null;
	String infoPaisTributacion = null;
	String infoNumeroTIN = null;
	String infoReconocimientoPolitico = null;
	String infoReconocimientoPublico = null;
	String infoRepresentanteLegal = null;
	String infoExpuesto = null;
	String infoCargoPublico = null;
	String infoFechaVinculacion = null;
	String infoFechaDesvinculacion = null;
	String infoActualmenteVinculado = null;
	String infoVinculo = null;
	String infoParentezcoVinculo = null;
	String infoNombreVinculo = null;
	String infoPrimerApellidoVinculo = null;
	String infoSegundoApellidoVinculo = null;

	boolean actualizarDatos = false;
// ***********************************************************************************************************************

	public String InicioActualizacionDatos(boolean data) throws Exception {

		if (data) {

			infoEmpresa = SettingsRun.getTestData().getParameter("Informacion de la Empresa");
			infoRepresentante = SettingsRun.getTestData().getParameter("Informacion del Respresentante Legal");
			infoActividadEconomica = SettingsRun.getTestData().getParameter("Actividad Economica");
			informacionFinanciera = SettingsRun.getTestData().getParameter("Informacion Financiera");

			infoAgregarSocio = SettingsRun.getTestData().getParameter("Agregar Socio");
			infoAgregarRepresentante = SettingsRun.getTestData().getParameter("Agregar Representante");
			infoEliminarSocio = SettingsRun.getTestData().getParameter("Eliminar Socio");
			infoEliminarRepresentante = SettingsRun.getTestData().getParameter("Eliminar Representante");

			infoDireccion = SettingsRun.getTestData().getParameter("Direccion Via");
			infoNumeroDireccion = SettingsRun.getTestData().getParameter("Numero Via");
			infoNumeral = SettingsRun.getTestData().getParameter("#");
			infGuion = SettingsRun.getTestData().getParameter("-");
			infoMunicipio = SettingsRun.getTestData().getParameter("Municipio");
			infoTelefonoPrefijo = SettingsRun.getTestData().getParameter("Tel�fono Fijo - prefijo");
			infoTelFijo = SettingsRun.getTestData().getParameter("Tel�fono Fijo");
			infoExt = SettingsRun.getTestData().getParameter("ext");
			infoCorreo = SettingsRun.getTestData().getParameter("Correo Electr�nico");
			infoAdicional = SettingsRun.getTestData().getParameter("Informacion adicional");
			infoNumeroCelular = SettingsRun.getTestData().getParameter("N�mero Celular");

			// Informacion Socio
			infoNITCasaMatriz = SettingsRun.getTestData().getParameter("NIT Casa Matriz");
			infoTipoIdSocio = SettingsRun.getTestData().getParameter("Tipo de Identificacion del Socio");
			infoNombreSocio = SettingsRun.getTestData().getParameter("Nombres o Raz�n Social del Socio");
			infoApellidoSocio = SettingsRun.getTestData().getParameter("Apellidos del Socio");
			infoParticipacionSocio = SettingsRun.getTestData().getParameter("Participaci�n Accionaria del Socio");
			infoNumeroIdSocio = SettingsRun.getTestData().getParameter("Identificaci�n del Socio");
			infoNacionalidadSocio = SettingsRun.getTestData()
					.getParameter("Nacionalidad o pa�s de constituci�n del Socio");
			infoNumeroIdSocioEliminar = SettingsRun.getTestData().getParameter("Identificaci�n del Socio a Eliminar");

			// Informacion Representante
			infoTipoIdRepresentante = SettingsRun.getTestData().getParameter("Tipo de Identificacion del Represetante");
			infoNombreRepresentante = SettingsRun.getTestData().getParameter("Nombres o Raz�n Social del Represetante");
			infoApellidosRepresentante = SettingsRun.getTestData().getParameter("Apellidos del Represetante");
			infoCargoRepresentante = SettingsRun.getTestData().getParameter("Cargo del Represetante");
			infoNumeroIdRepresentante = SettingsRun.getTestData().getParameter("Identificaci�n del Represetante");
			infoNumeroIdRepresentanteEliminar = SettingsRun.getTestData().getParameter("Identificaci�n del Represetante a Eliminar");
			infoDeclaraciones = SettingsRun.getTestData().getParameter("Declaraciones");
			infoTributacionFiscal = SettingsRun.getTestData().getParameter("�Sujeto a tributaci�n fiscal en USA o tiene pa�s de residencia fiscal diferente a Colombia representante?");
			infoPaisTributacion = SettingsRun.getTestData().getParameter("Pa�s de residencia fiscal representante");
			infoNumeroTIN = SettingsRun.getTestData().getParameter("Numero de TIN representante");
			infoReconocimientoPolitico = SettingsRun.getTestData().getParameter("�La persona goza de: reconocimiento p�blico, pol�ticamente expuesto, representante legal de una organizaci�n internacional o es familiar de una persona con las anteriores caracter�sticas representante?");
			infoReconocimientoPublico = SettingsRun.getTestData().getParameter("�Reconocido p�blicamente representante?");
			infoRepresentanteLegal = SettingsRun.getTestData().getParameter("�Representante legal de una organizaci�n internacional representante?");
			infoExpuesto = SettingsRun.getTestData().getParameter("�Expuesta pol�ticamente representante?");
			infoCargoPublico = SettingsRun.getTestData().getParameter("Cargo Publico representante");
			infoFechaVinculacion = SettingsRun.getTestData().getParameter("Fecha de Vinculacion representante");
			infoFechaDesvinculacion = SettingsRun.getTestData().getParameter("Fecha de Desvinculacion representante");
			infoActualmenteVinculado = SettingsRun.getTestData().getParameter("Actualmente Vinculado representante");
			infoVinculo = SettingsRun.getTestData().getParameter("�Es usted c�nyuge, compa�ero permanente o tiene v�nculo hasta segundo grado de consanguinidad, segundo grado de afinidad y/o primero civil con una persona p�blicamente expuesta representante?");
			infoParentezcoVinculo = SettingsRun.getTestData().getParameter("Parentezco del Representante");
			infoNombreVinculo = SettingsRun.getTestData().getParameter("Nombre del Representante");
			infoPrimerApellidoVinculo = SettingsRun.getTestData().getParameter("Apellido Paterno del Representante");
			infoSegundoApellidoVinculo = SettingsRun.getTestData().getParameter("Apellido Materno del Representante");

		} else {

			infoEmpresa = "INFO";
			infoRepresentante = "INFO";
			infoActividadEconomica = "INFO";
			informacionFinanciera = "NO";

			infoAgregarSocio = "NO";
			infoAgregarRepresentante = "INFO";
			infoEliminarSocio = "NO";
			infoEliminarRepresentante = "NO";

			infoTipoIdRepresentante = SettingsRun.getTestData().getParameter("Tipo Identificaci�n");
			infoNombreRepresentante = "PRUEBAS";
			infoApellidosRepresentante = "AUTOMATIZACION";
			infoCargoRepresentante = "GERENTE";
			infoNumeroIdRepresentante = SettingsRun.getTestData().getParameter("Id usuario");
			infoTributacionFiscal = "NO";
			infoReconocimientoPolitico = "NO";
		}

		actualizarDatos = data;

		return this.InicioActualizacionDatos();
	}

	/**
	 * Metodo inicial de actualizacion de datos, donde se definen el conjunto de
	 * datos que se va actualizar
	 */
	public String InicioActualizacionDatos() throws Exception {
		String msgError = null;

		this.pageOrigen = new PageOrigen1(this);
		msgError = this.pageOrigen.irAOpcion(null, "Administraci�n", "Administraci�n Portal", "Actualizaci�n de datos");

		if (msgError == null) {

			if (infoEmpresa.equals("SI") || infoRepresentante.equals("SI") || infoActividadEconomica.equals("SI")|| infoEmpresa.equals("INFO") || infoRepresentante.equals("INFO")|| infoActividadEconomica.equals("INFO")) {
				this.UbicacionRepresentanteLegal();
			}

			if (informacionFinanciera.equals("SI")) {

				do {
					DXCUtil.wait(1);
				} while (!this.isDisplayed(locInformacionFinanciera));

				this.click(locInformacionFinanciera);
				this.InformacionFinaciera();

			}

			if (infoAgregarSocio.equals("SI") || infoAgregarRepresentante.equals("SI") || infoEliminarSocio.equals("SI")
					|| infoEliminarRepresentante.equals("SI") || infoAgregarSocio.equals("INFO")
					|| infoAgregarRepresentante.equals("INFO")) {

				do {
					DXCUtil.wait(1);
				} while (!this.isDisplayed(locInformacionSociosAccionistas));

				this.click(locInformacionSociosAccionistas);
				this.InformacionSociosAccionistas();

			}
			if (actualizarDatos) {

				msgError = this.AprobarActualizacionDeDatos();
			}
		}

		return msgError;
	}

// ***********************************************************************************************************************
	/**
	 * Metodo para actualizar los datos de la seccion de Ubicacion y Representante
	 * Legal
	 */
	public void UbicacionRepresentanteLegal() throws Exception {

		String infoTipoEmpresa = SettingsRun.getTestData().getParameter("Tipo de empresa");
		String infoCodigoCIIU = SettingsRun.getTestData().getParameter("C�digo CIIU");
		String msg = null;
		if (infoEmpresa.equals("SI")) {

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locModInfoEmpresa));

			this.click(locModInfoEmpresa);

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(btnAceptarDatosEmpresa));

			msg = this.selectListItem(locDireccion, infoDireccion);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}
			
			this.write(locNumDireccion, infoNumeroDireccion);
			this.write(locNumeral, infoNumeral);
			this.write(locGuion, infGuion);

			if (this.isDisplayed(locMsjDeAyuda)) {
				Reporter.reportEvent(Reporter.MIC_PASS,
						"Se encuentra mensaje de ayuda en Ubicacion y Representante Legal");
			} else {
				Reporter.reportEvent(Reporter.MIC_FAIL,
						"No se encuentra mensaje de ayuda en Ubicacion y Representante Legal");
			}

			msg = this.selectListItem(locMunicipio, infoMunicipio);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}
			this.write(locPrefijoTelefono, infoTelefonoPrefijo);
			this.write(locTelFijo, infoTelFijo);
			this.write(locExt, infoExt);
			this.write(locCorreo, infoCorreo);
			this.write(locInfpoAdicional, infoAdicional);
			this.write(locNumeroCelular, infoNumeroCelular);
			Evidence.saveAllScreens("Datos Ubicacion Respresentante Legal", this);
			this.click(btnAceptarDatosEmpresa);

		} else if (infoEmpresa.equals("INFO")) {

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locModInfoEmpresa));

			String nombreEmpr = this.getText(By.id("cphCuerpo_txtNombreEmpresa"));
			String tipoIdEmp = this.getText(By.id("cphCuerpo_TxtTipoIdentificacion"));
			String numidEmp = this.getText(By.id("cphCuerpo_txtNumIdentificacion"));

			System.out.println(nombreEmpr);
			System.out.println(tipoIdEmp);
			System.out.println(numidEmp);

			Evidence.saveAllScreens("Datos Ubicacion y de Respresentante Legal", this);
		}

		if (infoRepresentante.equals("SI")) {

			this.click(locModInfoRepresentante);

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(btnAceptarRepresentante));

			String infoNumID = SettingsRun.getTestData().getParameter("Numero ID");
			String infoNumContacto = SettingsRun.getTestData().getParameter("Numero de Contacto");
			String infoCorreoRepresentante = SettingsRun.getTestData().getParameter("Correo Electr�nico.");

			xPathCheckRepresentante = xPathCheckRepresentante.replace("NUM_ID", infoNumID);
			xPathNumContactoRepresentante = xPathNumContactoRepresentante.replace("NUM_ID", infoNumID);
			xPathCorreoRepresentante = xPathCorreoRepresentante.replace("NUM_ID", infoNumID);

			if (this.isDisplayed(By.xpath(xPathCheckRepresentante))) {

				this.checkCheckBox(By.xpath(xPathCheckRepresentante));

				do {
					DXCUtil.wait(1);
				} while (!this.isDisplayed(btnAceptarRepresentante));

				msg = this.selectListItem(By.xpath(xPathNumContactoRepresentante), infoNumContacto);
				if (msg != null)
				if (!msg.isEmpty()) {
					Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				}
				msg = this.selectListItem(By.xpath(xPathCorreoRepresentante), infoCorreoRepresentante);
				if (msg != null)
				if (!msg.isEmpty()) {
					Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				}
				Evidence.saveAllScreens("Datos Respresentante Legal", this);
				this.click(btnAceptarRepresentante);

			} else {

				Reporter.reportEvent(Reporter.MIC_FAIL, "No se encuentra el Representante Legal");
				Evidence.saveAllScreens("No se encuentra el Representante Legal", this);
				this.click(btnCancelarRepresentante);

			}
		} else if (infoEmpresa.equals("INFO")) {

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locModInfoRepresentante));

			String representante = this.getText(By.id("cphCuerpo_TxtTipoIDRepresentante"));
			String idRepresentante = this.getText(By.id("cphCuerpo_TxtIDRepresentante"));
			String representanteLegal = this.getText(By.id("cphCuerpo_txtRepresentanteLegal"));

			System.out.println(representante);
			System.out.println(idRepresentante);
			System.out.println(representanteLegal);

			Evidence.saveAllScreens("Datos Respresentante Legal", this);
		}

		if (infoActividadEconomica.equals("SI")) {

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locTipoEmpresa));

			msg = this.selectListItem(locTipoEmpresa, infoTipoEmpresa);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}
			this.click(locCodigoCIIU);

			xpathCodigoCIIU = xpathCodigoCIIU.replace("NUM_CIIU", infoCodigoCIIU);
			this.click(this.element(xpathCodigoCIIU));

			Evidence.saveAllScreens("Datos Actividad Economica", this);
		} else if (infoEmpresa.equals("INFO")) {

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locTipoEmpresa));

			String tipoEmpresaSelecio = this.getItemSelected(locTipoEmpresa);
			String codigoCIIUSelecinado = this
					.getText(By.xpath("//select[@id='cphCuerpo_dropCodigoCIIU']/following-sibling::span//input"));

			System.out.println(tipoEmpresaSelecio);
			System.out.println(codigoCIIUSelecinado);

			Evidence.saveAllScreens("Datos Respresentante Legal 1", this);

			if ((tipoEmpresaSelecio != null && !tipoEmpresaSelecio.isEmpty())
					|| (codigoCIIUSelecinado != null && !codigoCIIUSelecinado.isEmpty())) {
				msg = this.selectListItem(locTipoEmpresa, infoTipoEmpresa);
				if (msg != null)
				if (!msg.isEmpty()) {
					Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				}
				this.click(locCodigoCIIU);

				xpathCodigoCIIU = xpathCodigoCIIU.replace("NUM_CIIU", infoCodigoCIIU);
				this.click(this.element(xpathCodigoCIIU));
				Evidence.saveAllScreens("Datos Respresentante Legal 2", this);
				actualizarDatos = true;
			}
		}

	}

// ***********************************************************************************************************************
	/**
	 * Metodo para actualizar los datos de la Informacion Financiera
	 */
	public void InformacionFinaciera() throws Exception {
		String msg = null;
		String infoSocio5Porciento = SettingsRun.getTestData()
				.getParameter("Tiene socios con m�s del 5% del capital social");
		String infoTotalIngresosOpera = SettingsRun.getTestData().getParameter("Ingresos operacionales");
		String infoOtrosIngresos = SettingsRun.getTestData().getParameter("Otros ingresos mensuales");
		String infoEgresos = SettingsRun.getTestData().getParameter("Egresos mensuales");
		String infoActivoCorriente = SettingsRun.getTestData().getParameter("Activo corriente");
		String infoOtrosActivos = SettingsRun.getTestData().getParameter("Otros activos");
		String infoPasivoLargoPlazo = SettingsRun.getTestData().getParameter("Pasivo largo plazo");
		String infoIngresosNoOperac = SettingsRun.getTestData().getParameter("Ingresos no operacionales");
		String infoActivoFijo = SettingsRun.getTestData().getParameter("Activo fijo");
		String infoPasivoCorriente = SettingsRun.getTestData().getParameter("Pasivo corriente");

		// Operaciones Internacionales
		String infoTieneOperaciones = SettingsRun.getTestData().getParameter("Tiene Operaciones Internacionales?");
		String infoTipoOperacion = SettingsRun.getTestData().getParameter("Tipo de Operaci�n");
		String infoMontoMensualUSD = SettingsRun.getTestData().getParameter("Monto Estimado Mensual (USD)");
		String infoOtrosCual = SettingsRun.getTestData().getParameter("Otros/Cual");
		String infoPais = SettingsRun.getTestData().getParameter("Pa�s Destino/Origen");
		String infoNombreRemitente = SettingsRun.getTestData().getParameter("Nombre del remitente");
		String infoNumeroCuenta = SettingsRun.getTestData().getParameter("N�mero de Cuenta");
		String infoCiudad = SettingsRun.getTestData().getParameter("Ciudad");
		String infoBanco = SettingsRun.getTestData().getParameter("Banco");
		String infoMoneda = SettingsRun.getTestData().getParameter("Moneda");

		// Agregar Socio Si/No
		String infoAgregarSocio = SettingsRun.getTestData().getParameter("Agregar Socio");

		// Totales
		String infoTotalIngresos;
		String infoTotalActivos;
		String infoTotalPasivos;

		// Totales Enteros
		int intTotalIngresos;
		int intTotalActivos;
		int intTotalPasivos;

		do {
			DXCUtil.wait(1);
		} while (!this.isDisplayed(locCheckSocios5Porciento));

		if (infoSocio5Porciento.equals("SI")) {
			DXCUtil.wait(1);

			if (infoAgregarSocio.equals("NO"))
				Reporter.reportEvent(Reporter.MIC_FAIL, "Es necesario Agregar Socio, posible fallo en la transaccion");

			this.checkCheckBox(locCheckSocios5Porciento);
		} else {
			DXCUtil.wait(1);
			this.uncheckCheckBox(locCheckSocios5Porciento);
		}

		this.write(locTotalIngresosOpera, infoTotalIngresosOpera);
		this.write(locOtrosIngresos, infoOtrosIngresos);
		this.write(locTotalEgresos, infoEgresos);
		this.write(locActivoCorriente, infoActivoCorriente);
		this.write(locOtrosActivos, infoOtrosActivos);
		this.write(locPasivoLargoPlazo, infoPasivoLargoPlazo);
		this.write(locTotalIngNoOperac, infoIngresosNoOperac);
		this.write(locActivoFijo, infoActivoFijo);
		this.write(locPasivoCorriente, infoPasivoCorriente);

		intTotalIngresos = Integer.parseInt(infoTotalIngresosOpera) + Integer.parseInt(infoOtrosIngresos)
				+ Integer.parseInt(infoIngresosNoOperac);
		intTotalActivos = Integer.parseInt(infoActivoCorriente) + Integer.parseInt(infoOtrosActivos)
				+ Integer.parseInt(infoActivoFijo);
		intTotalPasivos = Integer.parseInt(infoPasivoLargoPlazo) + Integer.parseInt(infoPasivoCorriente);

		DXCUtil.wait(1);

		infoTotalIngresos = this.element(locTotalIngresos).getAttribute("value");
		infoTotalActivos = this.element(locTotalActivos).getAttribute("value");
		infoTotalPasivos = this.element(locTotalPasivos).getAttribute("value");

		infoTotalIngresos = infoTotalIngresos.replace(".", "");
		infoTotalActivos = infoTotalActivos.replace(".", "");
		infoTotalPasivos = infoTotalPasivos.replace(".", "");

		if (infoTotalIngresos.equals(String.valueOf(intTotalIngresos))) {
			Reporter.reportEvent(Reporter.MIC_PASS, "Valor correcto en Total Ingresos");
		} else {
			Reporter.reportEvent(Reporter.MIC_FAIL, "Valor incorrecto en Total Ingresos");
		}

		if (infoTotalActivos.equals(String.valueOf(intTotalActivos))) {
			Reporter.reportEvent(Reporter.MIC_PASS, "Valor correcto en Total Activos");
		} else {
			Reporter.reportEvent(Reporter.MIC_FAIL, "Valor incorrecto en Total Activos");
		}

		if (infoTotalPasivos.equals(String.valueOf(intTotalPasivos))) {
			Reporter.reportEvent(Reporter.MIC_PASS, "Valor correcto en Total Pasivos");
		} else {
			Reporter.reportEvent(Reporter.MIC_FAIL, "Valor incorrecto en Total Pasivos");
		}

		if (this.isDisplayed(locMsjDeAyuda1) && this.isDisplayed(locMsjDeAyuda2) && this.isDisplayed(locMsjDeAyuda3)
				&& this.isDisplayed(locMsjDeAyuda4)) {
			Reporter.reportEvent(Reporter.MIC_PASS, "Se encuentran mensajes de ayuda en Informacion Financiera");
		} else {
			Reporter.reportEvent(Reporter.MIC_FAIL, "No se encuentran mensaje de ayuda en Informacion Financiera");
		}

		if (infoTieneOperaciones.equals("NO"))
			this.checkCheckBox(locRadioOperacionInternacionalNO);

		if (infoTieneOperaciones.equals("SI"))
			this.checkCheckBox(locRadioOperacionInternacionalSI);

		do {
			DXCUtil.wait(1);
		} while (!this.isDisplayed(locRadioOperacionInternacionalSI));

		if (infoTieneOperaciones.equals("SI")) {

			if (infoTipoOperacion.equals("Pago de Servicios"))
				infoTipoOperacion = "PagoServicios";

			if (infoTipoOperacion.equals("Otros/Cual"))
				infoTipoOperacion = "Otro";

			xpathRadioTipoOperacion = xpathRadioTipoOperacion.replace("TIPO_OPERACION", infoTipoOperacion);

			this.checkCheckBox(By.xpath(xpathRadioTipoOperacion));

			if (this.isDisplayed(locMontoMensual))
				this.write(locMontoMensual, infoMontoMensualUSD);

			if (infoTipoOperacion.equals("Otro"))
				this.write(locOtroCual, infoOtrosCual);

			msg = this.selectListItem(locPaisDestinoOrigen, infoPais);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}
			this.write(locNombreRemitente, infoNombreRemitente);
			this.write(locNumeroCuenta, infoNumeroCuenta);
			msg = this.selectListItem(locCiudad, infoCiudad);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}
			this.write(locBanco, infoBanco);
			msg = this.selectListItem(locMoneda, infoMoneda);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}
			Evidence.saveAllScreens("Datos de informacion financiera", this);

		}
	}

// ***********************************************************************************************************************
	/**
	 * Metodo para actualizar los datos de la seccion de Socios y Accionistas
	 */
	public void InformacionSociosAccionistas() throws Exception {

		String msg = null;

		if (infoAgregarSocio.equals("SI")) {

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locAgregarSocio));

			if (this.isDisplayed(locNITCasaMatriz))
				this.write(locNITCasaMatriz, infoNITCasaMatriz);

			DXCUtil.wait(1);
			this.click(locAgregarSocio);

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locTipoIdSocio));

			msg = this.selectListItem(locTipoIdSocio, infoTipoIdSocio);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locTipoIdSocio));

			this.write(locNumeroIdSocio, infoNumeroIdSocio);
			this.click(locNombreSocio);

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locNombreSocio));

			if (this.element(locNombreSocio).getAttribute("disabled") == null)
				this.write(locNombreSocio, infoNombreSocio);

			if (this.element(locPorcentajeParticipacion).getAttribute("disabled") == null)
				this.write(locPorcentajeParticipacion, infoParticipacionSocio);

			if (this.element(locApellidoSocio).getAttribute("disabled") == null)
				this.write(locApellidoSocio, infoApellidoSocio);

			if (this.element(locPaisSocio).getAttribute("disabled") == null)
				msg = this.selectListItem(locPaisSocio, infoNacionalidadSocio);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}

			this.AgregarSocioDatosAdicionales(infoTipoIdSocio);

			if (!infoTipoIdSocio.equals("NIT"))
				this.click(btnConfirmarSocio);
		}

		if (infoAgregarRepresentante.equals("SI")) {

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locAgregarRepresentante));

			if (this.isDisplayed(locNITCasaMatriz))
				this.write(locNITCasaMatriz, infoNITCasaMatriz);

			DXCUtil.wait(1);
			this.click(locAgregarRepresentante);

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locTipoIdRepresentante));

			msg = this.selectListItem(locTipoIdRepresentante, infoTipoIdRepresentante);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}
			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locNumeroIdRepresentante));

			this.write(locNumeroIdRepresentante, infoNumeroIdRepresentante);
			this.click(locCargoRepresentante);
			DXCUtil.wait(5);
			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locCargoRepresentante) && !this.isEnabled(locCargoRepresentante));

			if (this.element(locCargoRepresentante) != null && this.isEnabled(locCargoRepresentante))
				msg = this.selectListItem(locCargoRepresentante, infoCargoRepresentante);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locNombreRepresentante) && !this.isEnabled(locNombreRepresentante));

			if (this.element(locNombreRepresentante) != null && this.isEnabled(locNombreRepresentante))
				this.write(locNombreRepresentante, infoNombreRepresentante);
			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locApellidosRepresentante) && !this.isEnabled(locApellidosRepresentante));

			if (this.element(locApellidosRepresentante) != null && this.isEnabled(locApellidosRepresentante))
				this.write(locApellidosRepresentante, infoApellidosRepresentante);

			this.AgregarRepresentanteDatosAdicionales();
			this.click(btnConfirmarRepresentante);
		} else if (infoAgregarRepresentante.equals("INFO")) {
			
			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locAgregarRepresentante));

			if (this.isDisplayed(locNITCasaMatriz))
				this.write(locNITCasaMatriz, infoNITCasaMatriz);

			DXCUtil.wait(1);
			this.click(locAgregarRepresentante);

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locTipoIdRepresentante));

			msg = this.selectListItem(locTipoIdRepresentante, infoTipoIdRepresentante);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}
			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locNumeroIdRepresentante));

			this.write(locNumeroIdRepresentante, infoNumeroIdRepresentante);
			this.click(locCargoRepresentante);
			DXCUtil.wait(5);
			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locCargoRepresentante) && !this.isEnabled(locCargoRepresentante));

			if (this.element(locCargoRepresentante) != null && this.isEnabled(locCargoRepresentante))
				msg = this.selectListItem(locCargoRepresentante, infoCargoRepresentante);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locNombreRepresentante) && !this.isEnabled(locNombreRepresentante));

			if (this.element(locNombreRepresentante) != null && this.isEnabled(locNombreRepresentante))
				this.write(locNombreRepresentante, infoNombreRepresentante);
			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locApellidosRepresentante) && !this.isEnabled(locApellidosRepresentante));

			if (this.element(locApellidosRepresentante) != null && this.isEnabled(locApellidosRepresentante))
				this.write(locApellidosRepresentante, infoApellidosRepresentante);

			this.AgregarRepresentanteDatosAdicionales();
			this.click(btnConfirmarRepresentante);
		}

		if (infoEliminarSocio.equals("SI")) {

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locAgregarSocio));

			xpathNumIDEliminarSocio = xpathNumIDEliminarSocio.replace("NUM_ID", infoNumeroIdSocioEliminar);

			if (this.isDisplayed(By.xpath(xpathNumIDEliminarSocio))) {

				xpathNumIDEliminarSocio = xpathNumIDEliminarSocio + xpathBtnEliminarSocio;
				this.click(By.xpath(xpathNumIDEliminarSocio));
				if (this.existDialog()) {
					this.acceptDialog();
				}

			} else {
				Reporter.reportEvent(Reporter.MIC_FAIL, "No se enceuntra el socio para eliminar");
			}
		}

		if (infoEliminarRepresentante.equals("SI")) {

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locAgregarRepresentante));

			xpathNumIDEliminarRepre = xpathNumIDEliminarRepre.replace("NUM_ID", infoNumeroIdRepresentanteEliminar);

			if (this.isDisplayed(By.xpath(xpathNumIDEliminarRepre))) {

				xpathNumIDEliminarRepre = xpathNumIDEliminarRepre + xpathBtnEliminarRepre;
				this.click(By.xpath(xpathNumIDEliminarRepre));
				if (this.existDialog()) {
					this.acceptDialog();
				}
			} else {
				Reporter.reportEvent(Reporter.MIC_FAIL, "No se enceuntra el representante para eliminar");
			}
		}
	}

// ***********************************************************************************************************************
	/**
	 * Metodo para agregar socio
	 */
	public void AgregarSocioDatosAdicionales(String infoTipoIdSocio) throws Exception {
		String msg = null;
		String infoTributacionFiscal = SettingsRun.getTestData().getParameter(
				"�Sujeto a tributaci�n fiscal en USA o tiene pa�s de residencia fiscal diferente a Colombia?");
		String infoPaisTributacion = SettingsRun.getTestData().getParameter("Pa�s de residencia fiscal");
		String infoNumeroTIN = SettingsRun.getTestData().getParameter("Numero de TIN");
		String infoReconocimientoPolitico = SettingsRun.getTestData().getParameter(
				"�La persona goza de: reconocimiento p�blico, pol�ticamente expuesto, representante legal de una organizaci�n internacional o es familiar de una persona con las anteriores caracter�sticas?");
		String infoReconocimientoPublico = SettingsRun.getTestData().getParameter("�Reconocido p�blicamente?");
		String infoRepresentanteLegal = SettingsRun.getTestData()
				.getParameter("�Representante legal de una organizaci�n internacional?");
		String infoExpuesto = SettingsRun.getTestData().getParameter("�Expuesta pol�ticamente?");
		String infoCargoPublico = SettingsRun.getTestData().getParameter("Cargo Publico");
		String infoFechaVinculacion = SettingsRun.getTestData().getParameter("Fecha de Vinculacion");
		String infoFechaDesvinculacion = SettingsRun.getTestData().getParameter("Fecha de Desvinculacion");
		String infoActualmenteVinculado = SettingsRun.getTestData().getParameter("Actualmente Vinculado");
		String infoVinculo = SettingsRun.getTestData().getParameter(
				"�Es usted c�nyuge, compa�ero permanente o tiene v�nculo hasta segundo grado de consanguinidad, segundo grado de afinidad y/o primero civil con una persona p�blicamente expuesta?");
		String infoParentezcoVinculo = SettingsRun.getTestData().getParameter("Parentezco del Socio");
		String infoNombreVinculo = SettingsRun.getTestData().getParameter("Nombre del Socio");
		String infoPrimerApellidoVinculo = SettingsRun.getTestData().getParameter("Apellido Paterno del Socio");
		String infoSegundoApellidoVinculo = SettingsRun.getTestData().getParameter("Apellido Materno del Socio");

		if (infoTributacionFiscal.equals("SI")) {
			DXCUtil.wait(1);
			this.checkCheckBox(locTributacionSi);

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locPaisTribucion));

			msg = this.selectListItem(locPaisTribucion, infoPaisTributacion);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locTin));

			this.write(locTin, infoNumeroTIN);

		} else {
			DXCUtil.wait(1);
			this.checkCheckBox(locTributacionNo);
		}

		if (!infoTipoIdSocio.equals("NIT")) {

			if (infoReconocimientoPolitico.equals("SI")) {
				DXCUtil.wait(1);
				this.checkCheckBox(locPoliticoSI);

				do {
					DXCUtil.wait(1);
				} while (!this.isDisplayed(locReconocidoSi));

				if (infoReconocimientoPublico.equals("SI")) {
					DXCUtil.wait(1);
					this.checkCheckBox(locReconocidoSi);
				} else {
					DXCUtil.wait(1);
					this.checkCheckBox(locReconocidoNo);
				}

				if (infoRepresentanteLegal.equals("SI")) {
					DXCUtil.wait(1);
					this.checkCheckBox(locInterncionalSi);
				} else {
					DXCUtil.wait(1);
					this.checkCheckBox(locInternacionalNo);
				}

				if (infoExpuesto.equals("SI")) {
					DXCUtil.wait(1);
					this.checkCheckBox(locExpuestoSi);

					do {
						DXCUtil.wait(1);
					} while (!this.isDisplayed(locCargoSocio));

					msg = this.selectListItem(locCargoSocio, infoCargoPublico);
					if (msg != null)
					if (!msg.isEmpty()) {
						Reporter.reportEvent(Reporter.MIC_FAIL, msg);
					}

					DXCUtil.wait(1);
					this.pageCalendario = new PageActualizacionCalendario1(this);
					this.pageCalendario.SeleccionarCalendarioVinculacionSocio(infoFechaVinculacion);

					if (infoActualmenteVinculado.equals("SI")) {
						DXCUtil.wait(1);
						this.checkCheckBox(locActualVinculacionSocio);
					} else {
						pageCalendario.SeleccionarCalendarioDesVinculacionSocio(infoFechaDesvinculacion);
						DXCUtil.wait(1);
						this.uncheckCheckBox(locActualVinculacionSocio);
					}

				} else {
					DXCUtil.wait(1);
					this.checkCheckBox(locExpuestoNo);
				}

				if (infoVinculo.equals("SI")) {
					DXCUtil.wait(1);
					this.checkCheckBox(locFamiiarSi);

					do {
						DXCUtil.wait(1);
					} while (!this.isDisplayed(locParentezco));

					this.write(locParentezco, infoParentezcoVinculo);
					this.write(locNombreFamiliar, infoNombreVinculo);
					this.write(locPrimerApellidofamiliar, infoPrimerApellidoVinculo);
					this.write(locSegundoApellidoFamiliar, infoSegundoApellidoVinculo);

				} else {
					DXCUtil.wait(1);
					this.checkCheckBox(locFamiliarNo);
				}

			} else {
				DXCUtil.wait(1);
				this.checkCheckBox(locPoliticoNo);
			}

			Evidence.saveAllScreens("Datos de Agregar Socio", this);

		} else {

			this.AgregarSocioNIT();

		}
	}

// ***********************************************************************************************************************
	/**
	 * Metodo para agregar representante
	 */
	public void AgregarRepresentanteDatosAdicionales() throws Exception {
		String msg = null;

		if (infoTributacionFiscal.equals("SI")) {
			DXCUtil.wait(1);
			this.checkCheckBox(locTributacionSiContol);

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locPaisTribucionContol));

			msg = this.selectListItem(locPaisTribucionContol, infoPaisTributacion);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locTinContol));

			this.write(locTinContol, infoNumeroTIN);

		} else {
			DXCUtil.wait(1);
			this.checkCheckBox(locTributacionNoContol);
		}

		if (infoReconocimientoPolitico.equals("SI")) {
			DXCUtil.wait(1);
			this.checkCheckBox(locPoliticoSIContol);

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locReconocidoSiContol));

			if (infoReconocimientoPublico.equals("SI")) {
				DXCUtil.wait(1);
				this.checkCheckBox(locReconocidoSiContol);
			} else {
				DXCUtil.wait(1);
				this.checkCheckBox(locReconocidoNoContol);
			}

			if (infoRepresentanteLegal.equals("SI")) {
				DXCUtil.wait(1);
				this.checkCheckBox(locInterncionalSiContol);
			} else {
				DXCUtil.wait(1);
				this.checkCheckBox(locInternacionalNoContol);
			}

			if (infoExpuesto.equals("SI")) {
				DXCUtil.wait(1);
				this.checkCheckBox(locExpuestoSiContol);

				do {
					DXCUtil.wait(1);
				} while (!this.isDisplayed(locCargoSocioContol));

				msg = this.selectListItem(locCargoSocioContol, infoCargoPublico);
				if (msg != null)
				if (!msg.isEmpty()) {
					Reporter.reportEvent(Reporter.MIC_FAIL, msg);
				}

				this.pageCalendario = new PageActualizacionCalendario1(this);
				pageCalendario.SeleccionarCalendarioVinculacionRepresentante(infoFechaVinculacion);

				if (infoActualmenteVinculado.equals("SI")) {
					DXCUtil.wait(1);
					this.checkCheckBox(locActualVinculacionSocioContol);
				} else {
					pageCalendario.SeleccionarCalendarioDesVinculacionRepresentante(infoFechaDesvinculacion);
					DXCUtil.wait(1);
					this.uncheckCheckBox(locActualVinculacionSocioContol);
				}

				Evidence.save("Representante Legal medio",this);

			} else {
				DXCUtil.wait(1);
				this.checkCheckBox(locExpuestoNoContol);
			}

			if (infoVinculo.equals("SI")) {
				DXCUtil.wait(1);
				this.checkCheckBox(locFamiiarSiContol);

				do {
					DXCUtil.wait(1);
				} while (!this.isDisplayed(locParentezcoContol));

				this.write(locParentezcoContol, infoParentezcoVinculo);
				this.write(locNombreFamiliarContol, infoNombreVinculo);
				this.write(locPrimerApellidofamiliarContol, infoPrimerApellidoVinculo);
				this.write(locSegundoApellidoFamiliarContol, infoSegundoApellidoVinculo);

				Evidence.save("Representante Legal total",this);
			} else {
				DXCUtil.wait(1);
				this.checkCheckBox(locFamiliarNoContol);
			}

		} else {
			DXCUtil.wait(1);
			this.checkCheckBox(locPoliticoNoContol);
		}

		Evidence.saveAllScreens("Datos de Agregar Representante", this);
	}

// ***********************************************************************************************************************
	/**
	 * Metodo para agregar socio con tipo de identificacion NIT
	 */
	public void AgregarSocioNIT() throws Exception {
		String msg = null;
		// Informacion Socio NIT
		String infoTipoIdSocio = SettingsRun.getTestData().getParameter("Tipo de Identificacion del Socio");
		String infoNombreSocio = SettingsRun.getTestData().getParameter("Nombres o Raz�n Social del Socio");
		String infoApellidoSocio = SettingsRun.getTestData().getParameter("Apellidos del Socio");
		String infoParticipacionSocio = SettingsRun.getTestData().getParameter("Participaci�n Accionaria del Socio");
		String infoNumeroIdSocio = SettingsRun.getTestData().getParameter("Identificaci�n del Socio");
		String infoNacionalidadSocio = SettingsRun.getTestData()
				.getParameter("Nacionalidad o pa�s de constituci�n del Socio");

		// Informacion Adicional Socio NIT
		String infoTributacionFiscal = SettingsRun.getTestData().getParameter(
				"�Sujeto a tributaci�n fiscal en USA o tiene pa�s de residencia fiscal diferente a Colombia?");
		String infoPaisTributacion = SettingsRun.getTestData().getParameter("Pa�s de residencia fiscal");
		String infoNumeroTIN = SettingsRun.getTestData().getParameter("Numero de TIN");

		DXCUtil.wait(4);

		if (!this.isDisplayed(locPoliticoSI)) {
			Reporter.reportEvent(Reporter.MIC_PASS, "Identificacion NIT correcto, No se presenten campos PEP");
		} else {
			Reporter.reportEvent(Reporter.MIC_FAIL, "Identificacion NIT incorrecto, Se presenten campos PEP");
		}

		this.click(btnConfirmarSocio);

		do {
			DXCUtil.wait(1);
		} while (!this.isDisplayed(locDetallarNIT));

		this.click(locDetallarNIT);

		do {
			DXCUtil.wait(1);
		} while (!this.isDisplayed(locTipoIdSocioNIT));

		msg = this.selectListItem(locTipoIdSocioNIT, infoTipoIdSocio);
		if (msg != null)
		if (!msg.isEmpty()) {
			Reporter.reportEvent(Reporter.MIC_FAIL, msg);
		}

		do {
			DXCUtil.wait(1);
		} while (!this.isDisplayed(locNumeroIdSocioNIT));

		this.write(locNumeroIdSocioNIT, infoNumeroIdSocio);
		this.click(locNombreSocioNIT);

		do {
			DXCUtil.wait(1);
		} while (!this.isDisplayed(locNombreSocioNIT));

		if (this.element(locNombreSocioNIT).getAttribute("disabled") == null)
			this.write(locNombreSocioNIT, infoNombreSocio);

		if (this.element(locPorcentajeParticipacionNIT).getAttribute("disabled") == null)
			this.write(locPorcentajeParticipacionNIT, infoParticipacionSocio);

		if (this.element(locApellidoSocioNIT).getAttribute("disabled") == null)
			this.write(locApellidoSocioNIT, infoApellidoSocio);

		if (this.element(locPaisSocioNIT).getAttribute("disabled") == null)
			msg = this.selectListItem(locPaisSocioNIT, infoNacionalidadSocio);
		if (msg != null)
		if (!msg.isEmpty()) {
			Reporter.reportEvent(Reporter.MIC_FAIL, msg);
		}

		if (infoTributacionFiscal.equals("SI")) {
			DXCUtil.wait(1);
			this.checkCheckBox(locTributacionSiNIT);

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locPaisTribucionNIT));

			msg = this.selectListItem(locPaisTribucionNIT, infoPaisTributacion);
			if (msg != null)
			if (!msg.isEmpty()) {
				Reporter.reportEvent(Reporter.MIC_FAIL, msg);
			}

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locTinNIT));

			this.write(locTinNIT, infoNumeroTIN);

		} else {
			DXCUtil.wait(1);
			this.checkCheckBox(locTributacionNoNIT);
		}

		do {
			DXCUtil.wait(1);
		} while (!this.isDisplayed(locBtnConfirmarSocioNIT));

		this.click(locBtnConfirmarSocioNIT);

		do {
			DXCUtil.wait(1);
		} while (!this.isDisplayed(locBtnContinuarSocioNIT));

		this.click(locBtnContinuarSocioNIT);

		do {
			DXCUtil.wait(1);
		} while (!this.isDisplayed(locBtnConfirmarPageSocioNIT));

		Evidence.saveAllScreens("Datos de Agregar Socio con Documento Tipo NIT", this);
		this.click(locBtnConfirmarPageSocioNIT);

	}

// ***********************************************************************************************************************
	/**
	 * Metodo para aprobar actualizacion de datos cve y token
	 */
	public String AprobarActualizacionDeDatos() throws Exception {

		String msjRespuesta;
		this.pageConfirmacion = new PageConfirmacion1(this);
		if (infoDeclaraciones != null && !infoDeclaraciones.isEmpty()) {

			if (!this.isDisplayed(locDeclaraciones)) {
				this.click(locInformacionFinanciera);
			}

			do {
				DXCUtil.wait(1);
			} while (!this.isDisplayed(locDeclaraciones));

			this.write(locDeclaraciones, infoDeclaraciones);

			this.checkCheckBox(checkRecursos);
			this.checkCheckBox(checkAutorizacion);
			this.checkCheckBox(checkDeclaracion);
		}

		msjRespuesta = this.pageConfirmacion.aprobarTx();

		if (msjRespuesta.equals("Se actualizaron exitosamente los datos de su empresa")) {

			Date fecha = new Date();
			Date fechaToday = DXCUtil.dateAdd(fecha, Calendar.DAY_OF_MONTH, 0);
			Date hora1 = DXCUtil.dateAdd(fecha, Calendar.MINUTE, 0);

			String today = DXCUtil.dateToString(fechaToday, "D-M-YY");
			String hora = DXCUtil.hourToString(hora1, "HH:mm");
			String todayHora = today + " " + hora;
			todayHora = todayHora.replace("-", "/").replace("24 ", "2024 ");

			this.pageFileNet = new PageActualizacionFileNet1(this);
			pageFileNet.FECHAHORA = todayHora;

		}

		return msjRespuesta;
	}

	public String MsgAlertaActualizacionDatos() throws Exception {
		String msgAlerta = this.pageOrigen.element(locMsgAlerta).getText();
		Reporter.reportEvent(Reporter.MIC_FAIL, msgAlerta);
		return msgAlerta;
	}
}