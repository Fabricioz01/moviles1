package com.example.crud_room_kotlin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoSistemaOperativo {

    @Query("SELECT * FROM sistemas_operativos")
    suspend fun obtenerSistemasOperativos(): MutableList<SistemaOperativo>

    @Insert
    suspend fun agregarSistemaOperativo(sistemaOperativo: SistemaOperativo)

    @Update
    suspend fun actualizarSistemaOperativo(sistemaOperativo: SistemaOperativo)

    @Query("DELETE FROM sistemas_operativos WHERE id=:id")
    suspend fun borrarSistemaOperativo(id: Long)
}
