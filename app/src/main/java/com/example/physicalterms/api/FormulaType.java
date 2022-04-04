package com.example.physicalterms.api;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class FormulaType {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("rows")
    @Expose
    private List<FormulaRow> rows = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public FormulaType() {
    }

    /**
     *
     * @param count
     * @param rows
     */
    public FormulaType(Integer count, List<FormulaRow> rows) {
        super();
        this.count = count;
        this.rows = rows;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<FormulaRow> getRows() {
        return rows;
    }

    public void setRows(List<FormulaRow> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(FormulaType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("count");
        sb.append('=');
        sb.append(((this.count == null)?"<null>":this.count));
        sb.append(',');
        sb.append("rows");
        sb.append('=');
        sb.append(((this.rows == null)?"<null>":this.rows));
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
        result = ((result* 31)+((this.count == null)? 0 :this.count.hashCode()));
        result = ((result* 31)+((this.rows == null)? 0 :this.rows.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FormulaType) == false) {
            return false;
        }
        FormulaType rhs = ((FormulaType) other);
        return (((this.count == rhs.count)||((this.count!= null)&&this.count.equals(rhs.count)))&&((this.rows == rhs.rows)||((this.rows!= null)&&this.rows.equals(rhs.rows))));
    }

}