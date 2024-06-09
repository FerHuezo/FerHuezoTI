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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

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
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtContra = findViewById<EditText>(R.id.txtContra)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnBackR = findViewById<ImageButton>(R.id.btnBackR)

        btnBackR.setOnClickListener {
            val backR = Intent(this, activity_loginorregister::class.java)
            startActivity(backR)
        }

        fun limpiar(txtUsuario: EditText, txtEmail: EditText, txtContra: EditText)
        {
            txtUsuario.setText("")
            txtEmail.setText("")
            txtContra.setText("")
        }

        btnRegister.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val objConexion = ClaseConexion().CadenaConexion()
                    val addUser = objConexion?.prepareStatement("insert into tbUsuarios (UUID, nombreUsuario, email, contrasena) values (?, ?, ?, ?)")!!
                    addUser.setString(1, UUID.randomUUID().toString())
                    addUser.setString(2, txtUsuario.text.toString())
                    addUser.setString(3, txtEmail.text.toString())
                    addUser.setString(4, txtContra.text.toString())

                    val verificarFilas = addUser.executeUpdate()
                    if (verificarFilas > 0) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@activityRegistro, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
                            limpiar(txtUsuario, txtEmail, txtContra)
                            val intent = Intent(this@activityRegistro, MainActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@activityRegistro, "Error al crear el usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@activityRegistro, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                    e.printStackTrace()
                }
            }
        }
    }
}