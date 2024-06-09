package fernando.ramirez.ticketit.ui.notifications

import Modelo.ClaseConexion
import Modelo.dataClassTicket
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fernando.ramirez.ticketit.RecyclerViewHelper.Adaptador
import fernando.ramirez.ticketit.databinding.FragmentDashboardBinding
import fernando.ramirez.ticketit.databinding.FragmentNotificationsBinding
import fernando.ramirez.ticketit.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val rcvTickets = binding.rcvTickets

        rcvTickets.layoutManager = LinearLayoutManager(this.context)

        CoroutineScope(Dispatchers.IO).launch {
            val DBHuezoTI = obtenerTickets()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(DBHuezoTI)
                rcvTickets.adapter = adapter
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun obtenerTickets(): List<dataClassTicket>{
        val objConexion = ClaseConexion().CadenaConexion()
        val statement = objConexion?.createStatement()
        val resultSet = statement?.executeQuery("select * from tbTickets")!!
        val ListaTickets = mutableListOf<dataClassTicket>()
        while (resultSet?.next() == true){
            val numeroTicket = resultSet.getString("numeroTicket")
            val titulo = resultSet.getString("titulo")
            val descripcion = resultSet.getString("descripcion")
            val autor = resultSet.getString("autor")
            val emailAutor = resultSet.getString("emailAutor")
            val fechaCreacion = resultSet.getString("fechaCreacion")
            val estadoTicket = resultSet.getString("estadoTicket")
            val fechaFinalizado = resultSet.getString("fechaFinalizado")
            val ticket = dataClassTicket(numeroTicket, titulo, descripcion, autor, emailAutor, fechaCreacion, estadoTicket, fechaFinalizado)
            ListaTickets.add(ticket)
        }
        return ListaTickets
    }
}