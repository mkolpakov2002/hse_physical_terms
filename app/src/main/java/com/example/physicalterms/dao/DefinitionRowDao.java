package com.example.physicalterms.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.physicalterms.api.DefinitionRow;

import java.util.List;

@Dao
public interface DefinitionRowDao {
    // Добавление Note в бд
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DefinitionRow... item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DefinitionRow> item);

    // Удаление Note из бд
    @Query("DELETE FROM definitions WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM definitions")
    void deleteAll();

    @Update
    void update(DefinitionRow item);

    // Получение всех Person из бд
    @Query("SELECT * FROM definitions")
    List<DefinitionRow> getAll();

    @Query("SELECT * FROM definitions WHERE id = :suchId")
    DefinitionRow getById(int suchId);
}
