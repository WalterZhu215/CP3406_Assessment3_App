package com.example.natureexplorer.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.flow.Flow


// Stores trails saved by the user.
@Entity(tableName = "saved_trails")
data class SavedTrailEntity(
    @PrimaryKey val name: String,
    val imageUrl: String,
    val addedDate: String
)


// Stores completed quiz results.
@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trailName: String,
    val score: Int,
    val totalQuestions: Int,
    val completedAt: Long
)


@Dao
interface TrailDao {

    @Query("SELECT * FROM saved_trails ORDER BY addedDate DESC")
    fun getAllSavedTrails(): Flow<List<SavedTrailEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrail(trail: SavedTrailEntity)

    @Query("DELETE FROM saved_trails WHERE name = :trailName")
    suspend fun deleteTrail(trailName: String)
}


@Dao
interface QuizResultDao {

    @Query("SELECT * FROM quiz_results ORDER BY completedAt DESC")
    fun getAllQuizResults(): Flow<List<QuizResultEntity>>

    @Insert
    suspend fun insertQuizResult(result: QuizResultEntity)

    @Query("DELETE FROM quiz_results")
    suspend fun deleteAllQuizResults()
}


@Database(
    entities = [
        SavedTrailEntity::class,
        QuizResultEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class TrailDatabase : RoomDatabase() {

    abstract fun trailDao(): TrailDao

    abstract fun quizResultDao(): QuizResultDao


    companion object {

        @Volatile
        private var INSTANCE: TrailDatabase? = null


        private val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS quiz_results (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        trailName TEXT NOT NULL,
                        score INTEGER NOT NULL,
                        totalQuestions INTEGER NOT NULL,
                        completedAt INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }


        fun getDatabase(context: Context): TrailDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrailDatabase::class.java,
                    "trail_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}

