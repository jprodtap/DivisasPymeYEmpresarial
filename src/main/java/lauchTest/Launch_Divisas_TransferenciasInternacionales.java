package lauchTest;

import dav.library.reporting.EvidencePdfFile;
import library.core.BaseTestNG;
import library.reporting.Reporter;
import library.settings.SettingsRun;

public class Launch_Divisas_TransferenciasInternacionales extends BaseTestNG {



	//***********************************************************************************************************************
	//*                                Instancias Controllers - Implementacion Patron Singleton                             *
	//***********************************************************************************************************************
		

	ControllerGeneralDivisas controllerGeneralDivisas = new ControllerGeneralDivisas();

	//***********************************************************************************************************************

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
		this.setController();

	}

	@Override
	public void doingTest() throws Exception {

		controllerGeneralDivisas.getInstanciaUnicaControllerGeneralDivisas();
		controllerGeneralDivisas.validarReferenciaODescripcionEnMovimientoFrontEmpresarial();

	}



	

	public void launchClose() { // CIERRE DEL LANZAMIENTO
		

	}

}
