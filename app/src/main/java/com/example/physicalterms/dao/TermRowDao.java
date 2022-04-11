package com.example.physicalterms.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.physicalterms.api.FormulaRow;
import com.example.physicalterms.api.TaskRow;
import com.example.physicalterms.api.TermRow;

import java.util.List;

@Dao
public interface TermRowDao {
    // Добавление Note в бд
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TermRow... item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TermRow> item);

    // Удаление Note из бд
    @Query("DELETE FROM terms WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM terms")
    void deleteAll();

    @Update
    void update(TermRow item);

    // Получение всех Person из бд
    @Query("SELECT * FROM terms")
    List<TermRow> getAll();

    @Query("SELECT * FROM terms WHERE id = :suchId")
    TermRow getById(int suchId);
}