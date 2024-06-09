package fernando.ramirez.ticketit.RecyclerViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fernando.ramirez.ticketit.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val txtNombreCard = view.findViewById<TextView>(R.id.txtNombreCard)
    val txtDescripcionCard = view.findViewById<TextView>(R.id.txtDescCard)
    val txtAutorCard = view.findViewById<TextView>(R.id.txtAutorCard)
    val txtEmailAutorCard = view.findViewById<TextView>(R.id.txtEmailAutorCard)
    val txtFechaCreacionCard = view.findViewById<TextView>(R.id.txtFechaCreacionCard)
    val txtEstadoCard = view.findViewById<TextView>(R.id.txtEstadoCard)
    val txtFechaFinalizacionCard = view.findViewById<TextView>(R.id.txtFechaFinalizacionCard)
    val imgBorrar : ImageView = view.findViewById(R.id.imgBorrar)
    val imgEditar : ImageView = view.findViewById(R.id.imgEditar)
}