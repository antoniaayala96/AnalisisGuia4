/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisguia4;

import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Christhoper
 */
public class MenuEstudiantes {
    private final CRUDEstudiantes crud = new CRUDEstudiantes(); // La clase que gestiona toda nuestra base de datos
    
    // Los arrays que proveen opciones de las 4 categorias.
    private final String[] opcionesGeneral = new String[] { "Gestión Estudiantes", "Gestión Materias", "Cursar Materia a Alumno", "Reportes", "Salir Aplicación"};
    private final String[] opcionesAlumnos = new String[] {"Ingresar Estudiante", "Actualizar Estudiante", "Eliminar Estudiante", "Salir Submenu"};
    private final String[] opcionesMateria = new String[] {"Ingresar Materia", "Actualizar Materia", "Eliminar Materia", "Salir Submenu"};
    private final String[] opcionesReporte = new String[] {"Reporte de Alumnos", "Reporte de Materias con Alumnos Inscritos", "Materias Cursadas por Alumno", "Salir Submenu"};

    // Funciones utilitarias que validan la entrada de un JOptionPane y regresará si nuestro valor solicitado es nulo o esta vacio.
    private int SolicitarNumero(String mensaje, String titulo) {
        int valor = 0;
        while(true){
            String strValor  = JOptionPane.showInputDialog(null, mensaje, titulo);
            if(strValor == null || strValor.trim().isEmpty()) continue;
            try{
                valor = Integer.parseInt(strValor);
            }
            catch(NumberFormatException e) {
                continue;
            }
            
            break;
        }
        
        return valor;
    }
    
    private String SolicitarTexto(String mensaje, String titulo) {
        String valor;
        while(true){
            valor = JOptionPane.showInputDialog(null, mensaje, titulo);
            if(valor == null || valor.trim().isEmpty()) continue;
            break;
        }
        
        return valor;       
    }
    
    // Las fuciones Solicitar* son atajos para solicitar los datos y reducir el codigo repetido.
    private void SolicitarAlumnoDatos(int cod, boolean actualizar) {
        String nombre = SolicitarTexto("Ingrese el nombre del alumno", "Gestión de Alumnado");
        String apellido = SolicitarTexto("Ingrese el apellido del alumno", "Gestión de Alumnado");
        int edad = SolicitarNumero("Ingrese la edad del alumno", "Gestión de Alumnado");        
        String direccion = SolicitarTexto("Ingrese la dirección del alumno", "Gestión de Alumnado");
        
        if(!actualizar)
            crud.IngresarAlumno(cod, nombre, apellido, edad, direccion);
        else
            crud.ActualizarAlumno(cod, nombre, apellido, edad, direccion);
    }
    
    private void SolicitarMateriaDatos(int cod, boolean actualizar) {
        String nombre = SolicitarTexto("Ingrese el nombre de la materia", "Gestión de Materias/Cursos");
        String descripcion = SolicitarTexto("Ingrese la descripción de la materia", "Gestión de Materias/Cursos");
        
        if(!actualizar)
            crud.IngresarMateria(cod, nombre, descripcion);
        else
            crud.ActualizarMateria(cod, nombre, descripcion);
    }
        
    // Menú de Estudiantes
    private void MenuGestionEstudiantes() {
        boolean salir = false;
        while(!salir) {
            String opt = (String)JOptionPane.showInputDialog(null, "Bienvenido al Gestión de Alumnado", "Gestión de Estudiantes y Materias", JOptionPane.INFORMATION_MESSAGE, null, opcionesAlumnos, opcionesAlumnos[0]);
            if(opt == null || opt.equals(opcionesAlumnos[3])) break;
            
            if(opt.equals(opcionesAlumnos[0])) {
                int codigo = SolicitarNumero("Ingrese el codigo del alumno a crear", "Gestión de Alumnado");
                SolicitarAlumnoDatos(codigo, false);
            }
            
            if(opt.equals(opcionesAlumnos[1])) {
                int codigo = SolicitarNumero("Ingrese el codigo del alumno a actualizar", "Gestión de Alumnado");
                if(crud.Existe(2, codigo))
                    SolicitarAlumnoDatos(codigo, true);
                else
                    JOptionPane.showMessageDialog(null, "El alumno especificado no existe!", "Gestión de Alumnado", JOptionPane.ERROR_MESSAGE);
            }
            
            if(opt.equals(opcionesAlumnos[2])) {
                int codigo = SolicitarNumero("Ingrese el codigo del alumno a actualizar", "Gestión de Alumnado");
                if(crud.Existe(2, codigo))
                    crud.EliminarAlumno(codigo);
                else
                    JOptionPane.showMessageDialog(null, "El alumno especificado no existe!", "Gestión de Alumnado", JOptionPane.ERROR_MESSAGE);
            }
        }  
    }
    
    // Menu de Materias
    private void MenuGestionMaterias() {
        boolean salir = false;
        while(!salir) {
            String opt = (String)JOptionPane.showInputDialog(null, "Bienvenido al Gestión de Materias", "Gestión de Estudiantes y Materias", JOptionPane.INFORMATION_MESSAGE, null, opcionesMateria, opcionesMateria[0]);
            if(opt == null || opt.equals(opcionesMateria[3])) break;
            
            if(opt.equals(opcionesMateria[0])) {
                int codigo = SolicitarNumero("Ingrese el codigo de la materia a crear", "Gestión de Materias");
                SolicitarMateriaDatos(codigo, false);
            }
            
            if(opt.equals(opcionesMateria[1])) {
                int codigo = SolicitarNumero("Ingrese el codigo de la materia a actualizar", "Gestión de Materias");
                if(crud.Existe(1, codigo))
                    SolicitarAlumnoDatos(codigo, true);
                else
                    JOptionPane.showMessageDialog(null, "La materia especificado no existe!", "Gestión de Alumnado", JOptionPane.ERROR_MESSAGE);
            }
            
            if(opt.equals(opcionesMateria[2])) {
                int codigo = SolicitarNumero("Ingrese el codigo de la materia a eliminar", "Gestión de Materias");
                if(crud.Existe(1, codigo))
                    crud.EliminarMateria(codigo);
                else
                    JOptionPane.showMessageDialog(null, "La materia especificado no existe!", "Gestión de Alumnado", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Menu de reportes
    private void MenuReportes() {
        boolean salir = false;
        while(!salir) {
            String opt = (String)JOptionPane.showInputDialog(null, "Que reporte desea visualizar", "Menú de Reportes", JOptionPane.INFORMATION_MESSAGE, null, opcionesReporte, opcionesReporte[0]);
            if(opt == null || opt.equals(opcionesReporte[3])) break;
            
            if(opt.equals(opcionesReporte[0]))
                crud.MostrarReportes(0, -1);
            
            if(opt.equals(opcionesReporte[1]))
                crud.MostrarReportes(1, -1);
            
            if(opt.equals(opcionesReporte[2])) {
                int codigo = SolicitarNumero("Ingrese el codigo del alumno", "Menú de Reportes");
                crud.MostrarReportes(2, codigo);
            }
        }
        
    }
    
    
    private void AsociacionCursoMaterias() {
        HashMap<String, Integer> Alumnos    = crud.ObtenerAlumnosMap(); 
        HashMap<String, Integer> Materias   = crud.ObtenerMateriasMap();
        
        // Debemos copiar las llaves como arrays para mostrarlos en nuestro JOptionPane.
        String[] alumnosArr = Alumnos.keySet().toArray(new String[Alumnos.size()]);
        String[] materiaArr = Materias.keySet().toArray(new String[Alumnos.size()]);
        String strAlumno  = (String)JOptionPane.showInputDialog(null, "Seleccione el alumno", "Cursar Materias a Alumno", JOptionPane.INFORMATION_MESSAGE, null, alumnosArr, alumnosArr[0]);
        String strMateria = (String)JOptionPane.showInputDialog(null, "Seleccione la materia que desea el alumno curse", "Cursar Materias a Alumno", JOptionPane.INFORMATION_MESSAGE, null, materiaArr, materiaArr[0]);
        
        if(strAlumno == null || strAlumno.trim().isEmpty()) { // Validamos que no existe o esta vacio
            JOptionPane.showMessageDialog(null, "El alumno especificado no existe!", "Cursar Materias a Alumno", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(strMateria == null || strMateria.trim().isEmpty()) { // Validamos que no existe o esta vacio
            JOptionPane.showMessageDialog(null, "La materia especificado no existe!", "Cursar Materias a Alumno", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        crud.AsociarMateriaCurso(Alumnos.get(strAlumno), Materias.get(strMateria)); // Aplicamos los cambios
    }
    
    // El menu principal donde es llamaado.
    public boolean MenuPrincipal() {
        String opt = (String)JOptionPane.showInputDialog(null, "Bienvenido al Menú Principal", "Gestión de Estudiantes y Materias", JOptionPane.INFORMATION_MESSAGE, null, opcionesGeneral, opcionesGeneral[0]);
        if(opt == null || opt.equals(opcionesGeneral[0])) {
            MenuGestionEstudiantes(); 
            return false; 
        }
        if(opt.equals(opcionesGeneral[1])) {
            MenuGestionMaterias();
            return false;
        } 
        
        if(opt.equals(opcionesGeneral[2])) {
            AsociacionCursoMaterias(); MenuReportes();
            return false;
        }
        
        if(opt.equals(opcionesGeneral[3])) {
            MenuReportes();
            return false;
        }
        
        return opt.equals(opcionesGeneral[4]); //Salir!
    }
    
    // Funcion llamada cuando finalizamos nuestra aplicación
    public void finalizar() throws SQLException {
     crud.CerrarConexion();
    }
    
    
    
    
}
