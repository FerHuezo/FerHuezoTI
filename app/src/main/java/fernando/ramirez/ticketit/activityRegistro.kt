package fernando.ramirez.ticketit

import Modelo.ClaseConexion
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

class activityRegistro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtUsuario = findViewById<EditText>(R.id.txtUsuario)
        val txtContra = findViewById<EditText>(R.id.txtContra)
        val txtContraR = findViewById<EditText>(R.id.txtContraR)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        fun limpiar()
        {
            txtUsuario.setText("")
            txtContra.setText("")
            txtContraR.setText("")
        }

        fun VefContra()
        {
            if (txtContra.text.toString() == txtContraR.text.toString())
            {
                GlobalScope.launch(Dispatchers.Main){

                    val claseConexion = ClaseConexion().CadenaConexion()

                    val addUser = claseConexion?.prepareStatement("insert into tbUsuarios(nombreUsuario,contrasena) values(?,?)")!!

                    addUser.setString(1, txtUsuario.text.toString())
                    addUser.setString(2, txtContra.text.toString())
                    addUser.executeUpdate()
                    limpiar()
                }
            }
            else
            {
                Toast.makeText(this@activityRegistro, "Error, las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
        btnRegister.setOnClickListener {
            VefContra()
        }

    }
}