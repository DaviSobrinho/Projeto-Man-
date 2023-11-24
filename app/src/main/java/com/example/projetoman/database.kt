package com.example.projetoman

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import java.util.Date

@Entity
class Crianca (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "nome")
    val nome: String,
    @ColumnInfo(name = "dataNascimento")
    val dataNascimento: String,
    @ColumnInfo(name = "nomeResponsavel")
    val nomeResponsavel: String,
    @ColumnInfo(name = "telefoneResponsavel")
    val telefoneResponsavel: String,
    @ColumnInfo(name = "endereco")
    val endereco: String,
    @ColumnInfo(name = "observacoes")
    val observacoes: String
)
@Entity
class Encontro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "titulo")
    val titulo: String,
    @ColumnInfo(name = "dataEncontro")
    val dataEncontro: String,
    @ColumnInfo(name = "observacoes")
    val observacoes: String,
    @ColumnInfo(name = "pontuacaoEquipeAzul")
    val equipeAzul : Int,
    @ColumnInfo(name = "equipeVermelha")
    val equipeVermelha : Int,
    @ColumnInfo(name = "equipeVerde")
    val equipeVerde : Int,
    @ColumnInfo(name = "equipeLaranja")
    val equipeLaranja : Int,
    @ColumnInfo(name = "equipeAmarela")
    val equipeAmarela : Int
)
@Entity
@Dao
interface DaoFunctions{
    @Query("SELECT * FROM Crianca")
    fun getAllCrianca(): List<Crianca>

    @Query("SELECT * FROM Crianca WHERE id LIKE :id LIMIT 1")
    fun findCriancaByID(id: Int): Crianca

    @Query("SELECT * FROM Encontro")
    fun getAllEncontro(): List<Encontro>

    @Query("SELECT * FROM Encontro WHERE id LIKE :id LIMIT 1")
    fun findEncontroByID(id: Int): Encontro

    @Insert
    fun insertCrianca(crianca: Crianca)

    @Update
    fun updateCrianca(crianca: Crianca)

    @Delete
    fun deleteCrianca(crianca: Crianca)

    @Insert
    fun insertEncontro(encontro: Encontro)

    @Update
    fun updateEncontro(encontro: Encontro)

    @Delete
    fun deleteEncontro(encontro: Encontro)
}
@Database(entities = [Crianca::class, Encontro::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun daoFunctions() : DaoFunctions

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Ensinativa_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}