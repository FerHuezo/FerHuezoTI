package fernando.ramirez.ticketit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_loginorregister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginorregister)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val login = findViewById<Button>(R.id.btnLoginInto)
        val register = findViewById<Button>(R.id.btnRegisterInto)

        login.setOnClickListener {
            val Login = Intent(this, MainActivity::class.java)
            startActivity(Login)
        }
        register.setOnClickListener {
            val Register = Intent(this, activityRegistro::class.java)
            startActivity(Register)
        }
    }
}