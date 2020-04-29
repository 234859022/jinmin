package com.nanwang.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "E_ANSWER")
public class EAnswer   implements Serializable{
    @Id
    @Column(name = "answer_id")
    private String answerId;

    @Column(name = "answer_title")
    private String answerTitle;

    @Column(name = "answer_date")
    private String answerDate;

    @Column(name = "answer_userid")
    private String answerUserid;

    @Column(name = "answer_username")
    private String answerUsername;

    @Column(name = "answer_total_score")
    private BigDecimal answerTotalScore;

    @Column(name = "answer_create_time")
    private Date answerCreateTime;

    @Column(name = "answer_type")
    private Integer answerType;

    @Column(name = "answer_create_user")
    private String answerCreateUser;

    @Column(name = "answer_create_userid")
    private String answerCreateUserid;

    @Column(name = "answer_status")
    private Integer answerStatus;

    private String useranswerid;

    /**
     * @return answer_id
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * @param answerId
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    /**
     * @return answer_title
     */
    public String getAnswerTitle() {
        return answerTitle;
    }

    /**
     * @param answerTitle
     */
    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    /**
     * @return answer_date
     */
    public String getAnswerDate() {
        return answerDate;
    }

    /**
     * @param answerDate
     */
    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }

    /**
     * @return answer_userid
     */
    public String getAnswerUserid() {
        return answerUserid;
    }

    /**
     * @param answerUserid
     */
    public void setAnswerUserid(String answerUserid) {
        this.answerUserid = answerUserid;
    }

    /**
     * @return answer_username
     */
    public String getAnswerUsername() {
        return answerUsername;
    }

    /**
     * @param answerUsername
     */
    public void setAnswerUsername(String answerUsername) {
        this.answerUsername = answerUsername;
    }

    /**
     * @return answer_total_score
     */
    public BigDecimal getAnswerTotalScore() {
        return answerTotalScore;
    }

    /**
     * @param answerTotalScore
     */
    public void setAnswerTotalScore(BigDecimal answerTotalScore) {
        this.answerTotalScore = answerTotalScore;
    }

    /**
     * @return answer_create_time
     */
    public Date getAnswerCreateTime() {
        return answerCreateTime;
    }

    /**
     * @param answerCreateTime
     */
    public void setAnswerCreateTime(Date answerCreateTime) {
        this.answerCreateTime = answerCreateTime;
    }

    /**
     * @return answer_type
     */
    public Integer getAnswerType() {
        return answerType;
    }

    /**
     * @param answerType
     */
    public void setAnswerType(Integer answerType) {
        this.answerType = answerType;
    }

    /**
     * @return answer_create_user
     */
    public String getAnswerCreateUser() {
        return answerCreateUser;
    }

    /**
     * @param answerCreateUser
     */
    public void setAnswerCreateUser(String answerCreateUser) {
        this.answerCreateUser = answerCreateUser;
    }

    /**
     * @return answer_create_userid
     */
    public String getAnswerCreateUserid() {
        return answerCreateUserid;
    }

    /**
     * @param answerCreateUserid
     */
    public void setAnswerCreateUserid(String answerCreateUserid) {
        this.answerCreateUserid = answerCreateUserid;
    }

    /**
     * @return answer_status
     */
    public Integer getAnswerStatus() {
        return answerStatus;
    }

    /**
     * @param answerStatus
     */
    public void setAnswerStatus(Integer answerStatus) {
        this.answerStatus = answerStatus;
    }

    /**
     * @return useranswerid
     */
    public String getUseranswerid() {
        return useranswerid;
    }

    /**
     * @param useranswerid
     */
    public void setUseranswerid(String useranswerid) {
        this.useranswerid = useranswerid;
    }
}