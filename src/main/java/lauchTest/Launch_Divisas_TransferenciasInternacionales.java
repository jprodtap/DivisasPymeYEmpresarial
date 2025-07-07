package lauchTest;

import dav.library.reporting.EvidencePdfFile;
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
		String tipoPortal = "Pyme";
		ControllerGeneralDivisas.PortalType portalType = (tipoPortal != null&& tipoPortal.equalsIgnoreCase("Empresarial")) ? ControllerGeneralDivisas.PortalType.EMPRESARIAL: ControllerGeneralDivisas.PortalType.PYME;
		
		Reporter.reportEvent(Reporter.MIC_HEADER, "PRUEBAS PORTA - " + tipoPortal);
		controllerGeneralDivisas = ControllerGeneralDivisas.getInstance(portalType);
		controllerGeneralDivisas.inicializarSesion();
	}

	@Override
	public void doingTest() throws Exception {
		// Lógica principal del test: ejecuta el flujo de Divisas
		controllerGeneralDivisas.transar();
	}

	@Override
	public void launchClose() {
		// Limpieza si aplica
		if (controllerGeneralDivisas != null)
			controllerGeneralDivisas.destroy();
	}

}
