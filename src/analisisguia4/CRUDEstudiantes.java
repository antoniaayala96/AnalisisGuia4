/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisisguia4;

import java.sql.*;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Christhoper
 */
public class CRUDEstudiantes {
    // Declarar 3 elementos bases necesarios para hacer interfaz con nuestra base de datos
    private Connection conexion;
    private ResultSet  rs;
    private Statement  s;

    public CRUDEstudiantes() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Se obtiene una conexión con la base de datos.
            this.conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost/escuela", "root", "");

        } catch (ClassNotFoundException e1) {
            //Error si no puedo leer el driver de MySQL
            System.out.println("ERROR:No encuentro el driver de la BD: " + e1.getMessage());
            System.exit(0);
        } catch (SQLException e2) {
            //Error SQL: login/passwd ó sentencia sql erronea
            System.out.println("ERROR:Fallo en SQL: " + e2.getMessage());
            System.exit(0);
        }        
    }
    
    public void CerrarConexion() throws SQLException {
        conexion.close();
    }
    
    public void MostrarReportes(int tipo, int codAlumno) {
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));  // Limpiar la pantalla
        
        String sql, titulo, columnas;
        int cntColumnas=0; 
        // Determinamos el script SQL, el titulo y los titulos para las columnas.
        switch(tipo) {
            case 0: sql = "SELECT * FROM Alumno ORDER BY Apellido"; 
                    titulo = "Reporte de Alumnos por Apellidos"; 
                    columnas = "Código Alumno | Nombres     | Apellidos     | Edad | Dirección  |";
                    cntColumnas = 5;
                    break;
            case 1: sql = "SELECT m.Cod_materia, m.Nombre, m.Descripcion, (SELECT COUNT(*) FROM Alumno_Materia am WHERE am.Cod_materia = m.Cod_materia) AS 'Alumnos Registrados' FROM Materia m"; 
                    titulo = "Lista de materias registradas"; 
                    columnas = "Cod.Materia | Nombre                | Descripción           |   Alumnos Registrados ";
                    cntColumnas = 4;
                    break;
            case 2: sql = "SELECT m.Cod_materia, m.Nombre, m.Descripcion FROM Materia m LEFT JOIN alumno_materia am ON m.Cod_materia = am.Cod_materia WHERE am.Cod_alumno = " + codAlumno + ";"; 
                    titulo=" Materias que está cursando el Alumno especificado"; 
                    columnas="Cod.Materia | Nombre                | Descripción           |"; 
                    cntColumnas = 3;
                    break;
            default: return;
        }
        try {
            //Con nuestros datos ya definidos Ejecutamos todo con Java.sql. 
            s = conexion.createStatement();
            rs = s.executeQuery(sql);
            System.out.println(titulo);
            System.out.println("--------------------------------------------------------------------");
            System.out.println(columnas);
            
            while(rs.next()) {
                String contenido="";
                for(int i = 0; i < cntColumnas; i++) {
                    contenido += rs.getString(i+1) + " "; //NOTA: Las columnas de SQL empiezan desde 1! no 0
                }
                System.out.println(contenido);
            }
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de consultar en la base de datos");
            e.printStackTrace();
        }
    }
    
    //Esta funcion utilitaria determina si un registro en nuestras tablas existe o no.
    public boolean Existe(int tipo, int cod) {
        String sql;
        if(tipo == 1) sql = "SELECT * FROM Materia WHERE Cod_materia=" + cod;
        else sql = "SELECT * FROM Alumno  WHERE Cod_alumno=" + cod;
        
        try {
            s = conexion.createStatement();
            rs = s.executeQuery(sql);
            return rs.next(); //
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de consultar en la base de datos");
            return false;
        }
    } 
    
    public boolean IngresarAlumno(int Cod, String nombre, String apellido, int Edad, String direccion) {
        try {
            s = conexion.createStatement(); // Inicializamos nuestro statement SQL.
            s.executeUpdate("INSERT INTO Alumno VALUES("+Cod+",\""+nombre+"\",\""+apellido+"\","+Edad+",\","+direccion+"\""+ ")");
            JOptionPane.showMessageDialog(null,"Alumno Ingresada Correctamente");
            return true;
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de guardar en la base de datos");
            return false;
        }
    }
    
    public boolean ActualizarAlumno(int Cod, String nombre, String apellido, int Edad, String direccion) {
        try {
            s = conexion.createStatement(); // Inicializamos nuestro statement SQL.
            s.executeUpdate("UPDATE Alumno SET Nombre="+"\""+nombre+"\", Apellido=\""+apellido+"\",Edad="+Edad+",\", Direccion="+direccion+"\" WHERE Cod_alumno="+Cod+ ")");
            JOptionPane.showMessageDialog(null,"Alumno Ingresada Correctamente");
            return true;
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de guardar en la base de datos");
            return false;
        }        
    }
    
    public void EliminarAlumno(int id) {
        // Realizamos la ejecución de SQL a la base de datos
        try {
            s = conexion.createStatement();
            s.execute("DELETE FROM Alumno WHERE Cod_alumno=" + id);
            JOptionPane.showMessageDialog(null,"Alumno eliminada Correctamente");
        }
        catch(SQLException e) { // Posible error donde el id no ha sido encontrados.
            JOptionPane.showMessageDialog(null,"Error de eliminar Alumno en la base de datos");
        }
    }
    
    public void IngresarMateria(int cod, String nombre, String descripcion) {
        try {
            s = conexion.createStatement(); // Inicializamos nuestro statement SQL.
            s.executeUpdate("INSERT INTO Materia VALUES("+cod+",\""+nombre+"\",\""+descripcion+"\""+")");
            JOptionPane.showMessageDialog(null,"materia Ingresada Correctamente");
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de guardar en la base de datos");
        }
    }
    
    public void ActualizarMateria(int cod, String nombre, String descripcion) {
        try {
            s = conexion.createStatement(); // Inicializamos nuestro statement SQL.
            s.executeUpdate("UPDATE Materia SET Nombre="+"\""+nombre+"\", Apellido=\""+descripcion+"\""+ "WHERE Cod_materia="+cod+ ")");
            JOptionPane.showMessageDialog(null,"Materia Ingresada Correctamente");
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de guardar en la base de datos");
        }        
    }
    
    public void EliminarMateria(int cod) {
        // Realizamos la ejecución de SQL a la base de datos
        try {
            s = conexion.createStatement();
            s.execute("DELETE FROM Materia WHERE Cod_materia=" + cod);
            JOptionPane.showMessageDialog(null,"Materia eliminada Correctamente");
        }
        catch(SQLException e) { // Posible error donde el id no ha sido encontrados.
            JOptionPane.showMessageDialog(null,"Error de eliminar Alumno en la base de datos");
        }        
    }
    
    // Funciones utilitarias que facilitan a la clase MenuEstudiantes a poder asociar materias con listas que adivinar.
    public HashMap<String, Integer> ObtenerMateriasMap() {
        HashMap<String, Integer> mapa = new HashMap<>();
        try {
            s = conexion.createStatement();
            rs = s.executeQuery("SELECT * FROM Materia");
            while(rs.next())
                mapa.put(rs.getString("Nombre"), rs.getInt("Cod_materia"));
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de consultar en la base de datos");
            return null;
        }

        return mapa;
    }
    
    public HashMap<String, Integer> ObtenerAlumnosMap() {
        HashMap<String, Integer> mapa = new HashMap<>();
        try {
            s = conexion.createStatement();
            rs = s.executeQuery("SELECT * FROM Alumno");
            while(rs.next())
                mapa.put(rs.getString("Nombre") + "," + rs.getString("Apellidos"), rs.getInt("Cod_alumno"));
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de consultar en la base de datos");
            return null;
        }

        return mapa;
    }
    
    public void AsociarMateriaCurso(int CodAlumno, int CodMateria) {
        try {
            s = conexion.createStatement(); // Inicializamos nuestro statement SQL.
            s.executeUpdate("INSERT INTO Alumno_Materia VALUES("+CodAlumno+","+ CodMateria +")"); 
            JOptionPane.showMessageDialog(null,"Asociación Ingresada Correctamente");
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de guardar en la base de datos");
        }
        
    }

}
