package com.example.physicalterms.api;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "terms")
@Generated("jsonschema2pojo")
public class TermRow {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name_rus")
    @Expose
    private String nameRus;
    @SerializedName("name_lang")
    @Expose
    private String nameLang;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("section")
    @Expose
    private String section;

    @Ignore
    private boolean isExpanded = false;

    /**
     * No args constructor for use in serialization
     *
     */
    public TermRow() {
    }

    /**
     *
     * @param nameRus
     * @param nameLang
     * @param language
     * @param section
     * @param id
     */
    public TermRow(Integer id, String nameRus, String nameLang, String language, String section) {
        super();
        this.id = id;
        this.nameRus = nameRus;
        this.nameLang = nameLang;
        this.language = language;
        this.section = section;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TermRow withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
    }

    public TermRow withNameRus(String nameRus) {
        this.nameRus = nameRus;
        return this;
    }

    public String getNameLang() {
        return nameLang;
    }

    public void setNameLang(String nameLang) {
        this.nameLang = nameLang;
    }

    public TermRow withNameLang(String nameLang) {
        this.nameLang = nameLang;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public TermRow withLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public TermRow withSection(String section) {
        this.section = section;
        return this;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TermRow.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("nameRus");
        sb.append('=');
        sb.append(((this.nameRus == null)?"<null>":this.nameRus));
        sb.append(',');
        sb.append("nameLang");
        sb.append('=');
        sb.append(((this.nameLang == null)?"<null>":this.nameLang));
        sb.append(',');
        sb.append("language");
        sb.append('=');
        sb.append(((this.language == null)?"<null>":this.language));
        sb.append(',');
        sb.append("section");
        sb.append('=');
        sb.append(((this.section == null)?"<null>":this.section));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.nameLang == null)? 0 :this.nameLang.hashCode()));
        result = ((result* 31)+((this.language == null)? 0 :this.language.hashCode()));
        result = ((result* 31)+((this.section == null)? 0 :this.section.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.nameRus == null)? 0 :this.nameRus.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TermRow) == false) {
            return false;
        }
        TermRow rhs = ((TermRow) other);
        return ((((((this.nameLang == rhs.nameLang)||((this.nameLang!= null)&&this.nameLang.equals(rhs.nameLang)))&&((this.language == rhs.language)||((this.language!= null)&&this.language.equals(rhs.language))))&&((this.section == rhs.section)||((this.section!= null)&&this.section.equals(rhs.section))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.nameRus == rhs.nameRus)||((this.nameRus!= null)&&this.nameRus.equals(rhs.nameRus))));
    }

}