package fernando.ramirez.ticketit

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

        btnLogin.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val nombreUsuario = txtUser.text.toString()
                val contrasena = txtContrasena.text.toString()

                try {
                    val objConexion = ClaseConexion().CadenaConexion()
                    val verificar = objConexion?.prepareStatement("select count(*) from tbUsuarios where nombreUsuario = ? and contrasena = ?")
                    verificar?.setString(1, nombreUsuario)
                    verificar?.setString(2, contrasena)
                    val resultado = verificar?.executeQuery()

                    if (resultado != null && resultado.next() && resultado.getInt(1) > 0){
                        withContext(Dispatchers.IO){
                            Toast.makeText(this@MainActivity, "Inicio sesión correctamente", Toast.LENGTH_LONG).show()
                            val Intent 
                        }
                    }
                }
                catch (){

                }

            }

        }

    }
}