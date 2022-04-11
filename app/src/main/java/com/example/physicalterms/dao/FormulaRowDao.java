package com.example.physicalterms.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.physicalterms.api.DefinitionRow;
import com.example.physicalterms.api.FormulaRow;

import java.util.List;

@Dao
public interface FormulaRowDao {
    // Добавление Note в бд
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(FormulaRow... item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FormulaRow> item);

    // Удаление Note из бд
    @Query("DELETE FROM formulas WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM formulas")
    void deleteAll();

    @Update
    void update(FormulaRow item);

    // Получение всех Person из бд
    @Query("SELECT * FROM formulas")
    List<FormulaRow> getAll();

    @Query("SELECT * FROM formulas WHERE id = :suchId")
    FormulaRow getById(int suchId);
}
