package com.example.crud_room_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorSistemasOperativos(
    val listaSistemasOperativos: MutableList<SistemaOperativo>,
    val listener: AdaptadorListener
): RecyclerView.Adapter<AdaptadorSistemasOperativos.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_usuario, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sistemaOperativo = listaSistemasOperativos[position]

        holder.tvNombre.text = sistemaOperativo.nombre
        holder.tvVersion.text = sistemaOperativo.version
        holder.tvFechaLanzamiento.text = sistemaOperativo.fechaLanzamiento

        holder.cvSistemaOperativo.setOnClickListener {
            listener.onEditItemClick(sistemaOperativo)
        }

        holder.btnBorrar.setOnClickListener {
            listener.onDeleteItemClick(sistemaOperativo)
        }
    }

    override fun getItemCount(): Int {
        return listaSistemasOperativos.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cvSistemaOperativo = itemView.findViewById<CardView>(R.id.cvUsuario)
        val tvNombre = itemView.findViewById<TextView>(R.id.tvUsuario)
        val tvVersion = itemView.findViewById<TextView>(R.id.tvPais)
        val btnBorrar = itemView.findViewById<Button>(R.id.btnBorrar)
    }
}
