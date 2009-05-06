package gestorDeConfiguracion;

import java.io.Serializable;

/**
 * Clase con la información que se quiere guardar de cada descarga.
 * ¡¡OJO!! ¡Tiene que implementar la interfaz Serializable!!
 * @author F. Javier Sanchez Pardo
 */
public class InfoDescarga implements Serializable{
    public String _sNombreDescarga;
    public String _sCampo1;
    public String _sCampo2;
    public String _sCampo3;
    
    public InfoDescarga (String sNombreDescarga, String sCampo1, String sCampo2, String sCampo3){
        this._sNombreDescarga = sNombreDescarga;
        this._sCampo1 = sCampo1;
        this._sCampo2 = sCampo2;
        this._sCampo3 = sCampo3;
    }
    
    @Override
    public String toString(){
        return _sNombreDescarga + "\t" + _sCampo1 + "\t" + _sCampo2 + "\t" + _sCampo3;
    }
}
