package com.example.githubrepository.network

import androidx.lifecycle.LiveData
import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM SearchFields")
    fun getAllSearch() : Flow<List<SearchFields>>
    @Query("SELECT * FROM SearchFields")
    suspend fun getAllSearchOnce() : List<SearchFields>
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearch(search: List<SearchFields>)
    @Query("Delete FROM SearchFields WHERE id = :id")
    suspend fun deleteSearch(id: Int)
    @Query("Update SearchFields SET name = :name WHERE id = :id")
    suspend fun updateSearch(id: Int, name: String)

    @Query("DELETE FROM SearchFields")
    suspend fun clearAll()
}

@Dao
interface SelectedDao{
    @Query("SELECT * FROM SelectedFields")
    fun getAllSearch() : Flow<List<SearchFields>>
    @Query("SELECT * FROM SelectedFields")
    suspend fun getAllSearchOnce() : List<SearchFields>
    @Insert
    suspend fun addSearch(search: List<SelectedFields>)
    @Query("Delete FROM SelectedFields WHERE id = :id")
    suspend fun deleteSearch(id: Int)
    @Query("Update SelectedFields SET name = :name WHERE id = :id")
    suspend fun updateSearch(id: Int, name: String)

    @Query("DELETE FROM SelectedFields")
    suspend fun clearAll()

    @Query("DELETE FROM SelectedFields where id = :id")
    suspend fun clear(id:Int)

}
