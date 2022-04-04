package com.example.physicalterms;


import static com.example.physicalterms.Constants.TOKEN;

import com.example.physicalterms.api.DefinitionRow;
import com.example.physicalterms.api.DefinitionType;
import com.example.physicalterms.api.FormulaType;
import com.example.physicalterms.api.TaskType;
import com.example.physicalterms.api.TermType;
import com.example.physicalterms.api.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/definitions/get")
    Call<DefinitionType> getDefinitionList();

    @GET("/api/formulas/get")
    Call<FormulaType> getFormulaList();

    @GET("/api/tasks/get")
    Call<TaskType> getTaskList();

    @GET("/api/terms/get")
    Call<TermType> getTermList();
}
