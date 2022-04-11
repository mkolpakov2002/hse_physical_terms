package com.example.physicalterms;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.physicalterms.api.DefinitionRow;
import com.example.physicalterms.api.FormulaRow;
import com.example.physicalterms.api.TaskRow;
import com.example.physicalterms.api.TermRow;
import com.example.physicalterms.dao.Converters;
import com.example.physicalterms.dao.DefinitionRowDao;
import com.example.physicalterms.dao.FormulaRowDao;
import com.example.physicalterms.dao.TaskRowDao;
import com.example.physicalterms.dao.TermRowDao;

@Database(entities = {
        DefinitionRow.class,
        FormulaRow.class,
        TaskRow.class,
        TermRow.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    public abstract DefinitionRowDao getDefinitionRowDao();
    public abstract FormulaRowDao getFormulaRowDao();
    public abstract TaskRowDao getTaskRowDao();
    public abstract TermRowDao getTermRowDao();

}
