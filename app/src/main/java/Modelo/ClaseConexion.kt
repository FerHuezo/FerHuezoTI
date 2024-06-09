package Modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun CadenaConexion(): Connection? {
        try {
            val url = "jdbc:oracle:thin:@192.168.1.12:1521:xe"
            val user = "HUEZO_DEV"
            val contrasena = "k1nMDq6X"
            val connection = DriverManager.getConnection(url, user, contrasena)
            return connection
        }
        catch (e:Exception) {
            println("Este es tu error: $e")
            return null
        }
    }
}