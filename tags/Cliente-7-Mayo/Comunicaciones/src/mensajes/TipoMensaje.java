/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes;

/**
 *
 * @author Luis Ayuso
 */
public enum TipoMensaje {
    
    // mensajes P2P
    HolaQuiero,
    Conozco,
    Tengo,
    Toma,
    Altoo, 
    Dame,
    
    // mensajes Servidor-Cliente
    PeticionConsulta,
    PeticionDescarga,
    RespuestaPeticionConsulta,
    RespuestaPeticionDescarga,
    DatosCliente,
    Bienvenido,
    ListaDeArchivos,
    
    // offTopic
    keepAlive
}
