package fernando.ramirez.ticketit

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtUser = findViewById<EditText>(R.id.txtUser)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            val back = Intent(this, activity_loginorregister::class.java)
            startActivity(back)
        }
        btnLogin.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val nombreUsuario = txtUser.text.toString()
                val contrasena = txtContrasena.text.toString()

                try {
                    val objConexion = ClaseConexion().CadenaConexion()
                    if (objConexion != null) {
                        val verificar = objConexion.prepareStatement("select count(*) from tbUsuarios where nombreUsuario = ? and contrasena = ?")
                        verificar.setString(1, nombreUsuario)
                        verificar.setString(2, contrasena)
                        val resultado = verificar.executeQuery()

                        if (resultado.next() && resultado.getInt(1) > 0) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@MainActivity, "Inicio sesión correctamente", Toast.LENGTH_LONG).show()
                                val homeIntent = Intent(this@MainActivity, home_navigation::class.java)
                                startActivity(homeIntent)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Error en la conexión a la base de datos", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
