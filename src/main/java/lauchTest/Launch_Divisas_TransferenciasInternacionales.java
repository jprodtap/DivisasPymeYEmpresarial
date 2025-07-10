package lauchTest;

import dav.library.reporting.EvidencePdfFile;
import dav.transversal.DatosDavivienda;
import library.core.BaseTestNG;
import library.reporting.Reporter;
import library.settings.SettingsRun;

public class Launch_Divisas_TransferenciasInternacionales extends BaseTestNG {

	// ***********************************************************************************************************************
	// * Instancias Controllers - Implementacion Patron Singleton *
	// ***********************************************************************************************************************

	ControllerGeneralDivisas controllerGeneralDivisas = null;

	// ***********************************************************************************************************************

// ***********************************************************************************************************************

	@Override
	public void launchData() {
		// TODO Auto-generated method stub
		Reporter.initializeEvidenceType(new EvidencePdfFile());
		Reporter.writeTitle("\n*** PRUEBAS AUTENTICACIÓN DAVICOM ***");
		SettingsRun.DEFAULT_HEADER = 4;

		// PARÁMETROS REQUERIDOS EN LA HOJA DE DATOS GLOBAL PARA EL LAUNCH QUE SE ESTÉ
		// HACIENDO
		SettingsRun.ARRAY_DATA_PARAMS = new String[] { "testConfigId" };
//		String nombreTestCase = SettingsRun.getTestData().getParameter("testConfigId").trim();
//		this.loadEvidenceFileInfo(nombreTestCase,nombreTestCase + " - " + SettingsRun.getTestData().getParameter("testConfigId").trim());
	}

	@Override
	public void initializeControllerAndConfiguration() throws Exception {
		
		
		// Determina el tipo de portal desde los datos de prueba o por lógica
		// Puedes leerlo desde Excel, aquí está hardcodeado como ejemplo:
		// Obtén el tipo de portal desde Excel (o config)
//		String tipoPortal = SettingsRun.getTestData().getParameter("Typo Portal");
//		String tipoPortal = "Pyme";
		String tipoPortal = "Empresarial";
		ControllerGeneralDivisas.PortalType portalType = (tipoPortal != null&& tipoPortal.equalsIgnoreCase("Empresarial")) ? ControllerGeneralDivisas.PortalType.EMPRESARIAL: ControllerGeneralDivisas.PortalType.PYME;

		Reporter.reportEvent(Reporter.MIC_HEADER,"*** Ingreso al Portal: [" + tipoPortal + "]");
		
		controllerGeneralDivisas = ControllerGeneralDivisas.getInstance(portalType);
		
		String stratus = SettingsRun.getGlobalData("data.tipoValidacionStratus");
		
		if (stratus.equalsIgnoreCase("True")) {
		Reporter.reportEvent(Reporter.MIC_HEADER, "INICIO STRATUS");
		controllerGeneralDivisas.LoginStratus();
		}
		
	
	}

	@Override
	public void doingTest() throws Exception {
		
		String validarC360 = SettingsRun.getTestData().getParameter("ValidarC360");
		
		String codigoCIIU = SettingsRun.getTestData().getParameter("Validar CIIU").trim();
		
		if (validarC360.equalsIgnoreCase("SI")) {
			Reporter.reportEvent(Reporter.MIC_HEADER, "INICIO SESIÓN CLIENTE 360");
			controllerGeneralDivisas.logueoC360();
			}
		

		controllerGeneralDivisas.inicializarSesion();
		
		// Lógica principal del test: ejecuta el flujo de Divisas
		controllerGeneralDivisas.transar();
		//Lógica Consulat de Informes de Divisas
		String informe = SettingsRun.getTestData().getParameter("Informes").trim();
		if (informe.equalsIgnoreCase("SI")) {			
			controllerGeneralDivisas.ValidacionInformeInicial();
		}
	}

	@Override
	public void launchClose() {
		
		if (DatosDavivienda.RISKMOTOR != null && SettingsRun.esIteracionFinal())
			DatosDavivienda.RISKMOTOR.cerrarMotorRiesgo();

// -----------------------------------------------------------------------------------------------------------------------

		// Limpieza si aplica
		if (controllerGeneralDivisas != null)
			controllerGeneralDivisas.destroy();
	}

}
