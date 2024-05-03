package com.example.crud_room_kotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sistemas_operativos")
data class SistemaOperativo(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "version") var version: String,
    @ColumnInfo(name = "fecha_lanzamiento") var fechaLanzamiento: String
)
