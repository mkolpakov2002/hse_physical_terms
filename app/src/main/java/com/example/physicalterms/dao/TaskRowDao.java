package com.example.physicalterms.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.physicalterms.api.FormulaRow;
import com.example.physicalterms.api.TaskRow;

import java.util.List;

@Dao
public interface TaskRowDao {
    // Добавление Note в бд
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TaskRow... item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaskRow> item);

    // Удаление Note из бд
    @Query("DELETE FROM tasks WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM tasks")
    void deleteAll();

    @Update
    void update(TaskRow item);

    // Получение всех Person из бд
    @Query("SELECT * FROM tasks")
    List<TaskRow> getAll();

    @Query("SELECT * FROM tasks WHERE id = :suchId")
    TaskRow getById(int suchId);
}