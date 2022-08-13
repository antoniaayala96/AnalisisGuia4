/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisguia4;

import java.sql.SQLException;

/**
 *
 * @author Christhoper
 */
public class AnalisisGuia4Main {

    public static void main(String[] args) throws SQLException {
        MenuEstudiantes menu = new MenuEstudiantes();
        boolean salir = false;
        while(true) {
            salir = menu.MenuPrincipal();
            if(salir) break;
        }
 
        menu.finalizar();
        System.exit(0);
    }
    
}
