package com.example.physicalterms.api;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class FormulaRow {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_rus")
    @Expose
    private String nameRus;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public FormulaRow() {
    }

    /**
     *
     * @param createdAt
     * @param nameRus
     * @param name
     * @param language
     * @param comment
     * @param section
     * @param id
     * @param value
     * @param updatedAt
     */
    public FormulaRow(Integer id, String name, String nameRus, String value, String language, String comment, String section, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.name = name;
        this.nameRus = nameRus;
        this.value = value;
        this.language = language;
        this.comment = comment;
        this.section = section;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(FormulaRow.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("nameRus");
        sb.append('=');
        sb.append(((this.nameRus == null)?"<null>":this.nameRus));
        sb.append(',');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null)?"<null>":this.value));
        sb.append(',');
        sb.append("language");
        sb.append('=');
        sb.append(((this.language == null)?"<null>":this.language));
        sb.append(',');
        sb.append("comment");
        sb.append('=');
        sb.append(((this.comment == null)?"<null>":this.comment));
        sb.append(',');
        sb.append("section");
        sb.append('=');
        sb.append(((this.section == null)?"<null>":this.section));
        sb.append(',');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(',');
        sb.append("updatedAt");
        sb.append('=');
        sb.append(((this.updatedAt == null)?"<null>":this.updatedAt));
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
        result = ((result* 31)+((this.createdAt == null)? 0 :this.createdAt.hashCode()));
        result = ((result* 31)+((this.nameRus == null)? 0 :this.nameRus.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.language == null)? 0 :this.language.hashCode()));
        result = ((result* 31)+((this.comment == null)? 0 :this.comment.hashCode()));
        result = ((result* 31)+((this.section == null)? 0 :this.section.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        result = ((result* 31)+((this.updatedAt == null)? 0 :this.updatedAt.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FormulaRow) == false) {
            return false;
        }
        FormulaRow rhs = ((FormulaRow) other);
        return ((((((((((this.createdAt == rhs.createdAt)||((this.createdAt!= null)&&this.createdAt.equals(rhs.createdAt)))&&((this.nameRus == rhs.nameRus)||((this.nameRus!= null)&&this.nameRus.equals(rhs.nameRus))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.language == rhs.language)||((this.language!= null)&&this.language.equals(rhs.language))))&&((this.comment == rhs.comment)||((this.comment!= null)&&this.comment.equals(rhs.comment))))&&((this.section == rhs.section)||((this.section!= null)&&this.section.equals(rhs.section))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))))&&((this.updatedAt == rhs.updatedAt)||((this.updatedAt!= null)&&this.updatedAt.equals(rhs.updatedAt))));
    }

}