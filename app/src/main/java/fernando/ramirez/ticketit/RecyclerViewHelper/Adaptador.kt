package fernando.ramirez.ticketit.RecyclerViewHelper

import Modelo.ClaseConexion
import Modelo.dataClassTicket
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import fernando.ramirez.ticketit.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Adaptador(private var Datos: List<dataClassTicket>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_info_card, parent, false)
        return ViewHolder(vista)
    }

    fun Actualizarlista(nuevalista: List<dataClassTicket>){
        Datos = nuevalista
        notifyDataSetChanged()
    }

    fun ActualizarlistaLuegoCargarDatos(numeroTicket: String, nuevotitulo: String, nuevodescripcion: String, nuevoautor: String, nuevoemailautor: String, nuevofechacreacion: String, nuevoestado: String, nuevofechafinalizado: String){
        val index = Datos.indexOfFirst { it.numeroTicket == numeroTicket }
        Datos[index].titulo = nuevotitulo
        Datos[index].descripcion = nuevodescripcion
        Datos[index].autor = nuevoautor
        Datos[index].emailAutor = nuevoemailautor
        Datos[index].fechaCreacion = nuevofechacreacion
        Datos[index].estado = nuevoestado
        Datos[index].fechaFinalizado = nuevofechafinalizado
        notifyItemChanged(index)
    }

    fun Eliminarlista(titulo: String, posicion: Int){

        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().CadenaConexion()
            val delTickets = objConexion?.prepareStatement("delete tbTickets where titulo = ?")!!
            delTickets.setString(1, titulo)
            delTickets.executeUpdate()
            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        //notificamos que se eliminaron los datos
        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }
    fun actualizarProductos(titulo: String, descripcion: String, autor: String, emailAutor: String, fechaCreacion: String, estado: String, fechaFinalizado: String, numeroTicket: String){
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().CadenaConexion()
            val updateProductos = objConexion?.prepareStatement("Update tbTickets set titulo = ?, descripcion = ?, autor = ?, emailautor = ?, fechacreacion = ?, estadoticket = ?, fechafinalizado = ? where numeroTicket = ?")!!
            updateProductos.setString(1, titulo)
            updateProductos.setString(2, descripcion)
            updateProductos.setString(3, autor)
            updateProductos.setString(4, emailAutor)
            updateProductos.setString(5, fechaCreacion)
            updateProductos.setString(6, estado)
            updateProductos.setString(7, fechaFinalizado)
            updateProductos.setString(8, numeroTicket)
            updateProductos.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                ActualizarlistaLuegoCargarDatos(numeroTicket, titulo, descripcion, autor, emailAutor, fechaCreacion, estado, fechaFinalizado)
            }
        }

    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = Datos[position]
        holder.txtNombreCard.text = ticket.titulo
        holder.txtDescripcionCard.text = ticket.descripcion
        holder.txtAutorCard.text = ticket.autor
        holder.txtEmailAutorCard.text = ticket.emailAutor
        holder.txtFechaCreacionCard.text = ticket.fechaCreacion
        holder.txtEstadoCard.text = ticket.estado
        holder.txtFechaFinalizacionCard.text = ticket.fechaFinalizado

        val item = Datos[position]
        holder.imgBorrar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("¿Estás seguro?")
            builder.setMessage("¿Deseas eliminar el registro?")
            builder.setPositiveButton("Sip") { dialog, which ->
                Eliminarlista(item.titulo, position)
            }
            builder.setNegativeButton("Nop") { dialog, which ->
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        holder.imgEditar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("¿Editar este Ticket?")

            val cuadroNewTitulo = EditText(context).apply {
                setText(item.titulo)
            }
            val cuadroNewDesc = EditText(context).apply {
                setText(item.descripcion)
            }
            val cuadroNewAutor = EditText(context).apply {
                setText(item.autor)
            }
            val cuadroNewEmail = EditText(context).apply {
                setText(item.emailAutor)
            }
            val cuadroNewFechaCreacion = EditText(context).apply {
                setText(item.fechaCreacion)
            }
            val cuadroNewEstado = EditText(context).apply {
                setText(item.estado)
            }
            val cuadroNewFechaFinalizacion = EditText(context).apply {
                setText(item.fechaFinalizado)
            }

            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(cuadroNewTitulo)
                addView(cuadroNewDesc)
                addView(cuadroNewAutor)
                addView(cuadroNewEmail)
                addView(cuadroNewFechaCreacion)
                addView(cuadroNewEstado)
                addView(cuadroNewFechaFinalizacion)
            }
            builder.setView(layout)
            builder.setPositiveButton("Sip") { dialog, which ->
                actualizarProductos(cuadroNewTitulo.text.toString(), cuadroNewDesc.text.toString(), cuadroNewAutor.text.toString(), cuadroNewEmail.text.toString(), cuadroNewFechaCreacion.text.toString(), cuadroNewEstado.text.toString(), cuadroNewFechaFinalizacion.text.toString(), item.numeroTicket)
            }
            builder.setNegativeButton("Nop") { dialog, which ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}