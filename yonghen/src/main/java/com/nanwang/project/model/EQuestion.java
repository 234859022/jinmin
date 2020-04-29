package com.nanwang.project.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "E_QUESTION")
public class EQuestion implements Serializable{
    @Id
    @Column(name = "QUESTION_ID")
    private String questionId;

    @Column(name = "QUESTION_TITLE")
    private String questionTitle;

    @Column(name = "QUESTION_WEIGHTED")
    private Integer questionWeighted;

    @Column(name = "QUESTION_TYPEID")
    private String questionTypeid;

    @Column(name = "QUESTION_TYPENAME")
    private String questionTypename;

    @Column(name = "QUESTION_CREATE_TIME")
    private Date questionCreateTime;

    @Column(name = "QUESTION_CREATE_USER")
    private String questionCreateUser;

    @Column(name = "QUESTION_STATUS")
    private Integer questionStatus;

    @Column(name = "QUESTION_TYPES")
    private Integer questionTypes;

    @Column(name = "QUESTION_SORT")
    private Integer questionSort;

    /**
     * @return QUESTION_ID
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    /**
     * @return QUESTION_TITLE
     */
    public String getQuestionTitle() {
        return questionTitle;
    }

    /**
     * @param questionTitle
     */
    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    /**
     * @return QUESTION_WEIGHTED
     */
    public Integer getQuestionWeighted() {
        return questionWeighted;
    }

    /**
     * @param questionWeighted
     */
    public void setQuestionWeighted(Integer questionWeighted) {
        this.questionWeighted = questionWeighted;
    }

    /**
     * @return QUESTION_TYPEID
     */
    public String getQuestionTypeid() {
        return questionTypeid;
    }

    /**
     * @param questionTypeid
     */
    public void setQuestionTypeid(String questionTypeid) {
        this.questionTypeid = questionTypeid;
    }

    /**
     * @return QUESTION_TYPENAME
     */
    public String getQuestionTypename() {
        return questionTypename;
    }

    /**
     * @param questionTypename
     */
    public void setQuestionTypename(String questionTypename) {
        this.questionTypename = questionTypename;
    }

    /**
     * @return QUESTION_CREATE_TIME
     */
    public Date getQuestionCreateTime() {
        return questionCreateTime;
    }

    /**
     * @param questionCreateTime
     */
    public void setQuestionCreateTime(Date questionCreateTime) {
        this.questionCreateTime = questionCreateTime;
    }

    /**
     * @return QUESTION_CREATE_USER
     */
    public String getQuestionCreateUser() {
        return questionCreateUser;
    }

    /**
     * @param questionCreateUser
     */
    public void setQuestionCreateUser(String questionCreateUser) {
        this.questionCreateUser = questionCreateUser;
    }

    /**
     * @return QUESTION_STATUS
     */
    public Integer getQuestionStatus() {
        return questionStatus;
    }

    /**
     * @param questionStatus
     */
    public void setQuestionStatus(Integer questionStatus) {
        this.questionStatus = questionStatus;
    }

    /**
     * @return QUESTION_TYPES
     */
    public Integer getQuestionTypes() {
        return questionTypes;
    }

    /**
     * @param questionTypes
     */
    public void setQuestionTypes(Integer questionTypes) {
        this.questionTypes = questionTypes;
    }

    /**
     * @return QUESTION_SORT
     */
    public Integer getQuestionSort() {
        return questionSort;
    }

    /**
     * @param questionSort
     */
    public void setQuestionSort(Integer questionSort) {
        this.questionSort = questionSort;
    }
}