package com.example.crud_room_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.crud_room_kotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdaptadorListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adaptador: AdaptadorSistemasOperativos
    private lateinit var room: DBPrueba
    private var listaSistemasOperativos: MutableList<SistemaOperativo> = mutableListOf()
    private var sistemaOperativo: SistemaOperativo = SistemaOperativo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvSistemasOperativos.layoutManager = LinearLayoutManager(this)

        room = Room.databaseBuilder(this, DBPrueba::class.java, "dbPruebas").build()

        obtenerSistemasOperativos()

        binding.btnAddUpdate.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val version = binding.etVersion.text.toString().trim()
            val fechaLanzamiento = binding.etFechaLanzamiento.text.toString().trim()

            if (nombre.isEmpty() || version.isEmpty() || fechaLanzamiento.isEmpty()) {
                Toast.makeText(this, "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoSistemaOperativo = SistemaOperativo(nombre = nombre, version = version, fechaLanzamiento = fechaLanzamiento)

            if (binding.btnAddUpdate.text == "agregar") {
                agregarSistemaOperativo(nuevoSistemaOperativo)
            } else if(binding.btnAddUpdate.text == "actualizar") {
                sistemaOperativo.nombre = nombre
                sistemaOperativo.version = version
                sistemaOperativo.fechaLanzamiento = fechaLanzamiento

                actualizarSistemaOperativo(sistemaOperativo)
            }
        }
    }

    private fun obtenerSistemasOperativos() {
        lifecycleScope.launch {
            listaSistemasOperativos = room.daoSistemaOperativo().obtenerSistemasOperativos()
            adaptador = AdaptadorSistemasOperativos(listaSistemasOperativos, this@MainActivity)
            binding.rvSistemasOperativos.adapter = adaptador
        }
    }

    private fun agregarSistemaOperativo(sistemaOperativo: SistemaOperativo) {
        lifecycleScope.launch {
            room.daoSistemaOperativo().agregarSistemaOperativo(sistemaOperativo)
            obtenerSistemasOperativos()
            limpiarCampos()
        }
    }

    private fun actualizarSistemaOperativo(sistemaOperativo: SistemaOperativo) {
        lifecycleScope.launch {
            room.daoSistemaOperativo().actualizarSistemaOperativo(sistemaOperativo)
            obtenerSistemasOperativos()
            limpiarCampos()
        }
    }

    private fun limpiarCampos() {
        binding.etNombre.text.clear()
        binding.etVersion.text.clear()
        binding.etFechaLanzamiento.text.clear()

        if (binding.btnAddUpdate.text == "actualizar") {
            binding.btnAddUpdate.text = "agregar"
            binding.etNombre.isEnabled = true
        }

        sistemaOperativo = SistemaOperativo()
    }

    override fun onEditItemClick(sistemaOperativo: SistemaOperativo) {
        binding.btnAddUpdate.text = "actualizar"
        binding.etNombre.isEnabled = false
        this.sistemaOperativo = sistemaOperativo
        binding.etNombre.setText(sistemaOperativo.nombre)
        binding.etVersion.setText(sistemaOperativo.version)
        binding.etFechaLanzamiento.setText(sistemaOperativo.fechaLanzamiento)
    }

    override fun onDeleteItemClick(sistemaOperativo: SistemaOperativo) {
        lifecycleScope.launch {
            room.daoSistemaOperativo().borrarSistemaOperativo(sistemaOperativo.id)
            obtenerSistemasOperativos()
        }
    }
}
