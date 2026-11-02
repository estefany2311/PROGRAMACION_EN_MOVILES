package com.tecsup.huellitasvet.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tecsup.huellitasvet.data.local.dao.CitaDao
import com.tecsup.huellitasvet.data.local.dao.MascotaDao
import com.tecsup.huellitasvet.data.local.dao.ServicioDao
import com.tecsup.huellitasvet.data.local.dao.UsuarioDao
import com.tecsup.huellitasvet.data.local.entity.Cita
import com.tecsup.huellitasvet.data.local.entity.Mascota
import com.tecsup.huellitasvet.data.local.entity.Servicio
import com.tecsup.huellitasvet.data.local.entity.Usuario

@Database(
    entities = [Servicio::class, Usuario::class, Mascota::class, Cita::class],
    version = 2,
    exportSchema = false
)
abstract class VetDatabase : RoomDatabase() {

    abstract fun servicioDao(): ServicioDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun mascotaDao(): MascotaDao
    abstract fun citaDao(): CitaDao

    companion object {
        @Volatile private var INSTANCE: VetDatabase? = null

        // Version 1 -> 2: agrega apellido y codigo a usuarios sin borrar los datos
        val MIGRACION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE usuarios ADD COLUMN apellido TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE usuarios ADD COLUMN codigo TEXT")
            }
        }

        // Singleton: una sola instancia de la base para toda la app
        fun obtener(context: Context): VetDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                VetDatabase::class.java,
                "huellitasvet.db"
            ).addMigrations(MIGRACION_1_2)
                .build().also { INSTANCE = it }
        }
    }
}
