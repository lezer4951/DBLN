package com.dubalin.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dubalin.app.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(usuario: UsuarioEntity): Long

    @Update
    suspend fun update(usuario: UsuarioEntity)

    @Delete
    suspend fun delete(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuario WHERE id = :id")
    suspend fun getById(id: Int): UsuarioEntity?

    @Query("SELECT * FROM usuario WHERE id = :id")
    fun observeById(id: Int): Flow<UsuarioEntity?>

    /** Usado por AuthRepository para login y para validar correo único. */
    @Query("SELECT * FROM usuario WHERE correo = :correo LIMIT 1")
    suspend fun getByCorreo(correo: String): UsuarioEntity?
}
