package com.example.physicalterms.api;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class TaskRow {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("answers")
    @Expose
    private List<String> answers = null;
    @SerializedName("rightAnswer")
    @Expose
    private String rightAnswer;
    @SerializedName("task_type")
    @Expose
    private Integer taskType;
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
    public TaskRow() {
    }

    /**
     *
     * @param rightAnswer
     * @param createdAt
     * @param taskType
     * @param answers
     * @param section
     * @param id
     * @param text
     * @param updatedAt
     */
    public TaskRow(Integer id, String text, List<String> answers, String rightAnswer, Integer taskType, String section, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.text = text;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
        this.taskType = taskType;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
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
        sb.append(TaskRow.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("answers");
        sb.append('=');
        sb.append(((this.answers == null)?"<null>":this.answers));
        sb.append(',');
        sb.append("rightAnswer");
        sb.append('=');
        sb.append(((this.rightAnswer == null)?"<null>":this.rightAnswer));
        sb.append(',');
        sb.append("taskType");
        sb.append('=');
        sb.append(((this.taskType == null)?"<null>":this.taskType));
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
        result = ((result* 31)+((this.rightAnswer == null)? 0 :this.rightAnswer.hashCode()));
        result = ((result* 31)+((this.createdAt == null)? 0 :this.createdAt.hashCode()));
        result = ((result* 31)+((this.taskType == null)? 0 :this.taskType.hashCode()));
        result = ((result* 31)+((this.answers == null)? 0 :this.answers.hashCode()));
        result = ((result* 31)+((this.section == null)? 0 :this.section.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.text == null)? 0 :this.text.hashCode()));
        result = ((result* 31)+((this.updatedAt == null)? 0 :this.updatedAt.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TaskRow) == false) {
            return false;
        }
        TaskRow rhs = ((TaskRow) other);
        return (((((((((this.rightAnswer == rhs.rightAnswer)||((this.rightAnswer!= null)&&this.rightAnswer.equals(rhs.rightAnswer)))&&((this.createdAt == rhs.createdAt)||((this.createdAt!= null)&&this.createdAt.equals(rhs.createdAt))))&&((this.taskType == rhs.taskType)||((this.taskType!= null)&&this.taskType.equals(rhs.taskType))))&&((this.answers == rhs.answers)||((this.answers!= null)&&this.answers.equals(rhs.answers))))&&((this.section == rhs.section)||((this.section!= null)&&this.section.equals(rhs.section))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.text == rhs.text)||((this.text!= null)&&this.text.equals(rhs.text))))&&((this.updatedAt == rhs.updatedAt)||((this.updatedAt!= null)&&this.updatedAt.equals(rhs.updatedAt))));
    }

}