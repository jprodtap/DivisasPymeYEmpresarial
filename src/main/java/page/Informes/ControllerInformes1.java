package page.Informes;

import library.reporting.Reporter;
import library.settings.SettingsRun;
import page.middlePymes.PageInicioMiddle1;
import pages.actios.Pyme.PageLoginPymes1;
import pages.actios.Pyme.PageOrigen1;

public class ControllerInformes1 {

// ====================================[Page & Crontroller]===================================================================================

	PageLoginPymes1 pageLoginPymes = null;
	PageLoginPymes1 pageLogin = null;
	PageInicioMiddle1 inicioMiddle = null;
	PageOrigen1 pageOrigen = null;
	PageInformes1 pageInformes = null;

	// ===========================================================================================================================================

		/**
		 * Pages y constructores
		 * @return 
		 */
		public void ControllerInformes(PageLoginPymes1 pageParent) {
			pageLoginPymes = pageParent;
			this.inicioMiddle = new PageInicioMiddle1(pageParent);
			this.pageOrigen = new PageOrigen1(pageLoginPymes);

		}


	// ===========================================================================================================================================

		public void informesMiddle() throws Exception {
			String mensajeReporte = "";
			int eventStatus = Reporter.MIC_PASS;

			boolean resultadoPrueba = this.validacionInformes();
			if (resultadoPrueba) {
				mensajeReporte = this.pageInformes.getMensajes();
				Reporter.reportEvent(eventStatus, mensajeReporte);

			} else {
				eventStatus = Reporter.MIC_FAIL;
				mensajeReporte = this.pageInformes.getMensajes();
				this.pageOrigen.terminarIteracion(eventStatus, mensajeReporte);
			}

		}

		public boolean validacionInformes() throws Exception {

			boolean resultado = false;

			String tipoPrueba = SettingsRun.getTestData().getParameter("Tipo de Prueba").trim();
			
			Reporter.write("================INFORMES - Tipo de prueba ["+tipoPrueba+"]================");
			Reporter.write("");
			Reporter.write("Se ingresa al m�dulo y se ingresa p�rametros de b�squeda");
			
			String[] ruta = this.RutaInforme(tipoPrueba);
			if (ruta[2] == null) {
				String msgError = this.inicioMiddle.irAOpcion(ruta[0], "Informes", ruta[1]);
				if (msgError != null) {
				 this.pageOrigen.terminarIteracion(Reporter.MIC_FAIL, msgError);
				}
			} else {
				String msgError = this.inicioMiddle.irAOpcion(ruta[0], "Informes", ruta[1], ruta[2]);
				if (msgError != null) {
					this.pageOrigen.terminarIteracion(Reporter.MIC_FAIL, msgError);
				}
			}

			switch (tipoPrueba) {

			case "Acceso al Sistema":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.accesoAlSistema();
				break;
			case "Actividades Administradores":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.actividadesAdministradores();
				break;
			case "Cobros Efectivos":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.cobrosEfectivos();
				break;
			case "Informe de Empresas":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.informeEmpresas();
				break;
			case "Estad�sticas de Transacciones":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.est�disticasTransacciones();
				break;
			case "Informe Operativo":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.informeOperativo();
				break;
			case "Pagos Masivos":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.pagosMasivos();
				break;
			case "Pagos y Transferencias":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.pagosYTransferencias();
				break;
			case "Servicios Contratados":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.serviciosContratados();
				break;
			case "Transacciones ACH":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.transaccionesACH();
				break;
			case "Transacciones Realizadas":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.transaccionesRealizadas();
				break;
			case "Usuarios Creados":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.usuariosCreados();
				break;
			case "Aprobaciones App":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.aprobacionesApp();
				break;
			case "Informes Autogesti�n":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.informesAutogestion();
				break;
			case "Retiros sin tarjeta":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.informeRetirosSinTarjeta();
				break;
			case "Negocios Fiduciarios":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.negociosFiduciarios();
				break;
			case "Transacciones FIC":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.informeTransaccionesFic();
				break;
			case "Estados Empresa":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.informeEstadosEmpresa();
				break;
			case "Informes Transferencias Internacionales":
				this.pageInformes = new PageInformes1(this.pageLoginPymes);
				resultado = this.pageInformes.informeEstadosEmpresa();
				break;	
			
				
				

			default:
				break;
			}
			return resultado;
		}

		/**
		 * Retorna la ruta de cada informe con su respectivo titulo y si tienen su
		 * modulos [0] Titulo [1]subModulo [2]Submodulo2
		 * 
		 * @param tipoconsul
		 * @return
		 */
		public String[] RutaInforme(String tipoconsul) {
			String[] ruta = null;

			if (tipoconsul.contains("Acceso al Sistema")) {
				ruta = new String[] { "Consulta de Acceso al Sistema", "Acceso al Sistema", null };
			} else if (tipoconsul.contains("Actividades Administradores")) {
				ruta = new String[] { "Informe Consultas Novedades de Administraci�n", "Actividades Administradores",
						null };
			} else if (tipoconsul.contains("Cobros Efectivos")) {
				ruta = new String[] { "Informes de Cobros Efectivos", "Cobros Efectivos", null };
			} else if (tipoconsul.contains("Informe de empresas")) {
				ruta = new String[] { "Estad�sticas Empresas Portal Pyme", "Informe de empresas", null };
			} else if (tipoconsul.contains("Estad�sticas de Transacciones")) {
				ruta = new String[] { "Estad�sticas Portal Empresarial", "Estad�sticas de Transacciones", null };
			} else if (tipoconsul.contains("Estad�sticas de Transacciones")) {
				ruta = new String[] { "Informe Operativo", "Estad�sticas de Transacciones", null };
			} else if (tipoconsul.contains("Informe Operativo")) {
				ruta = new String[] { "Informe Operativo", "Informe Operativo", null };
			} else if (tipoconsul.contains("Pagos Masivos")) {
				ruta = new String[] { "Informes Pagos Masivos", "Pagos Masivos", null };
			} else if (tipoconsul.contains("Pagos y Transferencias")) {
				ruta = new String[] { "Informes Pagos y Transferencias", "Pagos y Transferencias", null };
			} else if (tipoconsul.contains("Servicios Contratados")) {
				ruta = new String[] { "Consulta Servicios Contratados", "Servicios Contratados", null };
			} else if (tipoconsul.contains("Transacciones ACH")) {
				ruta = new String[] { null, "Transacciones ACH", null };
			} else if (tipoconsul.contains("Transacciones Realizadas")) {
				ruta = new String[] { null, "Transacciones Realizadas", null };
			} else if (tipoconsul.contains("Usuarios Creados")) {
				ruta = new String[] { null, "Usuarios Creados", null };
			} else if (tipoconsul.contains("Multiabonos Consulta dispersi�n")) {
				ruta = new String[] { null, "Multiabonos", "Consulta dispersi�n" };
			} else if (tipoconsul.contains("Multiabonos Consulta Parametros")) {
				ruta = new String[] { null, "Multiabonos", "Consulta Parametros" };
			} else if (tipoconsul.contains("Aprobaciones App")) {
				ruta = new String[] { null, "Aprobaciones App", null };
			} else if (tipoconsul.contains("Informes Autogesti�n")) {
				ruta = new String[] { null, "Informes Autogesti�n", null };
			} else if (tipoconsul.contains("Retiros sin tarjeta")) {
				ruta = new String[] { null, "Retiros sin tarjeta", null };
			} else if (tipoconsul.contains("Negocios Fiduciarios")) {
				ruta = new String[] { null, "Negocios Fiduciarios", null };
			} else if (tipoconsul.contains("Transacciones FIC")) {
				ruta = new String[] { null, "Transacciones FIC", null };
			} else if (tipoconsul.contains("Estados Empresa")) {
				ruta = new String[] { null, "Estados Empresa", null };
			} else if (tipoconsul.contains("Informes Transferencias Internacionales")) {
				ruta = new String[] { null, "Informes Transferencias Internacionales", null };
			} else if (tipoconsul.contains("Administraci�n del efectivo")) {
				ruta = new String[] { null, "Administraci�n del efectivo", null };
			} else if (tipoconsul.contains("Administre sus Cr�ditos")) {
				ruta = new String[] { null, "Administre sus Cr�ditos", null };
			}

			return ruta;
		}

	}
