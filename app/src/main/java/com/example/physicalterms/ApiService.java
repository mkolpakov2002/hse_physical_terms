package com.example.physicalterms;


import com.example.physicalterms.api.DefinitionRow;
import com.example.physicalterms.api.FormulaRow;
import com.example.physicalterms.api.TaskRow;
import com.example.physicalterms.api.TermRow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/definitions/get")
    Call<List<DefinitionRow>> getDefinitionList();

    @GET("/formulas/get")
    Call<List<FormulaRow>> getFormulaList();

    @GET("/tasks/get")
    Call<List<TaskRow>> getTaskList();

    @GET("/terms/get")
    Call<List<TermRow>> getTermList();

    @GET("/definitions/getByParams/{lang}/{section}")
    Call<List<DefinitionRow>> getDefinitionListByLang(@Path("lang") String langCodeName, @Path("section") String sectionName);

    @GET("/formulas/getByParams/{lang}/{section}")
    Call<List<FormulaRow>> getFormulaListByLang(@Path("lang") String langCodeName, @Path("section") String sectionName);

    @GET("/terms/getByParams/{lang}/{section}")
    Call<List<TermRow>> getTermsListByLang(@Path("lang") String langCodeName, @Path("section") String sectionName);
}
