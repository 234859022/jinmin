package com.nanwang.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "E_USER_ANSWER")
public class EUserAnswer   implements Serializable{
    @Id
    @Column(name = "A_ID")
    private String aId;

    @Column(name = "A_USERNAME")
    private String aUsername;

    @Column(name = "A_USERID")
    private String aUserid;

    @Column(name = "A_YI_ADMIN")
    private String aYiAdmin;

    @Column(name = "A_YI_ADMINID")
    private String aYiAdminid;

    @Column(name = "A_YI_STATUS")
    private Integer aYiStatus;

    @Column(name = "A_ER_ADMIN")
    private String aErAdmin;

    @Column(name = "A_ER_ADMINID")
    private String aErAdminid;

    @Column(name = "A_ER_STATUS")
    private Integer aErStatus;

    @Column(name = "A_SAN_ADMIN")
    private String aSanAdmin;

    @Column(name = "A_SAN_ADMINID")
    private String aSanAdminid;

    @Column(name = "A_SAN_SATATUS")
    private Integer aSanSatatus;

    @Column(name = "A_STATUS")
    private Integer aStatus;

    @Column(name = "A_SCORE")
    private BigDecimal aScore;

    @Column(name = "A_CREATE_TIME")
    private Date aCreateTime;

    @Column(name = "A_CREATE_USER")
    private String aCreateUser;

    @Column(name = "A_CREATE_USERID")
    private String aCreateUserid;

    @Column(name = "A_YI_SCORE")
    private BigDecimal aYiScore;

    @Column(name = "A_ER_SCORE")
    private BigDecimal aErScore;

    @Column(name = "A_SAN_SCORE")
    private BigDecimal aSanScore;

    @Column(name = "A_TITLE")
    private String aTitle;

    @Column(name = "A_YI_SCORES")
    private String aYiScores;

    @Column(name = "A_YI_COUNT")
    private Integer aYiCount;

    @Column(name = "A_ER_SCORES")
    private String aErScores;

    @Column(name = "A_ER_COUNT")
    private Integer aErCount;

    @Column(name = "A_SAN_SCORES")
    private String aSanScores;

    @Column(name = "A_SAN_COUNT")
    private Integer aSanCount;

    @Column(name = "A_MY_ADMINID")
    private String aMyAdminid;

    @Column(name = "A_MY_SCORE")
    private BigDecimal aMyScore;

    @Column(name = "A_MY_STATUS")
    private Integer aMyStatus;

    /**
     * @return A_ID
     */
    public String getaId() {
        return aId;
    }

    /**
     * @param aId
     */
    public void setaId(String aId) {
        this.aId = aId;
    }

    /**
     * @return A_USERNAME
     */
    public String getaUsername() {
        return aUsername;
    }

    /**
     * @param aUsername
     */
    public void setaUsername(String aUsername) {
        this.aUsername = aUsername;
    }

    /**
     * @return A_USERID
     */
    public String getaUserid() {
        return aUserid;
    }

    /**
     * @param aUserid
     */
    public void setaUserid(String aUserid) {
        this.aUserid = aUserid;
    }

    /**
     * @return A_YI_ADMIN
     */
    public String getaYiAdmin() {
        return aYiAdmin;
    }

    /**
     * @param aYiAdmin
     */
    public void setaYiAdmin(String aYiAdmin) {
        this.aYiAdmin = aYiAdmin;
    }

    /**
     * @return A_YI_ADMINID
     */
    public String getaYiAdminid() {
        return aYiAdminid;
    }

    /**
     * @param aYiAdminid
     */
    public void setaYiAdminid(String aYiAdminid) {
        this.aYiAdminid = aYiAdminid;
    }

    /**
     * @return A_YI_STATUS
     */
    public Integer getaYiStatus() {
        return aYiStatus;
    }

    /**
     * @param aYiStatus
     */
    public void setaYiStatus(Integer aYiStatus) {
        this.aYiStatus = aYiStatus;
    }

    /**
     * @return A_ER_ADMIN
     */
    public String getaErAdmin() {
        return aErAdmin;
    }

    /**
     * @param aErAdmin
     */
    public void setaErAdmin(String aErAdmin) {
        this.aErAdmin = aErAdmin;
    }

    /**
     * @return A_ER_ADMINID
     */
    public String getaErAdminid() {
        return aErAdminid;
    }

    /**
     * @param aErAdminid
     */
    public void setaErAdminid(String aErAdminid) {
        this.aErAdminid = aErAdminid;
    }

    /**
     * @return A_ER_STATUS
     */
    public Integer getaErStatus() {
        return aErStatus;
    }

    /**
     * @param aErStatus
     */
    public void setaErStatus(Integer aErStatus) {
        this.aErStatus = aErStatus;
    }

    /**
     * @return A_SAN_ADMIN
     */
    public String getaSanAdmin() {
        return aSanAdmin;
    }

    /**
     * @param aSanAdmin
     */
    public void setaSanAdmin(String aSanAdmin) {
        this.aSanAdmin = aSanAdmin;
    }

    /**
     * @return A_SAN_ADMINID
     */
    public String getaSanAdminid() {
        return aSanAdminid;
    }

    /**
     * @param aSanAdminid
     */
    public void setaSanAdminid(String aSanAdminid) {
        this.aSanAdminid = aSanAdminid;
    }

    /**
     * @return A_SAN_SATATUS
     */
    public Integer getaSanSatatus() {
        return aSanSatatus;
    }

    /**
     * @param aSanSatatus
     */
    public void setaSanSatatus(Integer aSanSatatus) {
        this.aSanSatatus = aSanSatatus;
    }

    /**
     * @return A_STATUS
     */
    public Integer getaStatus() {
        return aStatus;
    }

    /**
     * @param aStatus
     */
    public void setaStatus(Integer aStatus) {
        this.aStatus = aStatus;
    }

    /**
     * @return A_SCORE
     */
    public BigDecimal getaScore() {
        return aScore;
    }

    /**
     * @param aScore
     */
    public void setaScore(BigDecimal aScore) {
        this.aScore = aScore;
    }

    /**
     * @return A_CREATE_TIME
     */
    public Date getaCreateTime() {
        return aCreateTime;
    }

    /**
     * @param aCreateTime
     */
    public void setaCreateTime(Date aCreateTime) {
        this.aCreateTime = aCreateTime;
    }

    /**
     * @return A_CREATE_USER
     */
    public String getaCreateUser() {
        return aCreateUser;
    }

    /**
     * @param aCreateUser
     */
    public void setaCreateUser(String aCreateUser) {
        this.aCreateUser = aCreateUser;
    }

    /**
     * @return A_CREATE_USERID
     */
    public String getaCreateUserid() {
        return aCreateUserid;
    }

    /**
     * @param aCreateUserid
     */
    public void setaCreateUserid(String aCreateUserid) {
        this.aCreateUserid = aCreateUserid;
    }

    /**
     * @return A_YI_SCORE
     */
    public BigDecimal getaYiScore() {
        return aYiScore;
    }

    /**
     * @param aYiScore
     */
    public void setaYiScore(BigDecimal aYiScore) {
        this.aYiScore = aYiScore;
    }

    /**
     * @return A_ER_SCORE
     */
    public BigDecimal getaErScore() {
        return aErScore;
    }

    /**
     * @param aErScore
     */
    public void setaErScore(BigDecimal aErScore) {
        this.aErScore = aErScore;
    }

    /**
     * @return A_SAN_SCORE
     */
    public BigDecimal getaSanScore() {
        return aSanScore;
    }

    /**
     * @param aSanScore
     */
    public void setaSanScore(BigDecimal aSanScore) {
        this.aSanScore = aSanScore;
    }

    /**
     * @return A_TITLE
     */
    public String getaTitle() {
        return aTitle;
    }

    /**
     * @param aTitle
     */
    public void setaTitle(String aTitle) {
        this.aTitle = aTitle;
    }

    /**
     * @return A_YI_SCORES
     */
    public String getaYiScores() {
        return aYiScores;
    }

    /**
     * @param aYiScores
     */
    public void setaYiScores(String aYiScores) {
        this.aYiScores = aYiScores;
    }

    /**
     * @return A_YI_COUNT
     */
    public Integer getaYiCount() {
        return aYiCount;
    }

    /**
     * @param aYiCount
     */
    public void setaYiCount(Integer aYiCount) {
        this.aYiCount = aYiCount;
    }

    /**
     * @return A_ER_SCORES
     */
    public String getaErScores() {
        return aErScores;
    }

    /**
     * @param aErScores
     */
    public void setaErScores(String aErScores) {
        this.aErScores = aErScores;
    }

    /**
     * @return A_ER_COUNT
     */
    public Integer getaErCount() {
        return aErCount;
    }

    /**
     * @param aErCount
     */
    public void setaErCount(Integer aErCount) {
        this.aErCount = aErCount;
    }

    /**
     * @return A_SAN_SCORES
     */
    public String getaSanScores() {
        return aSanScores;
    }

    /**
     * @param aSanScores
     */
    public void setaSanScores(String aSanScores) {
        this.aSanScores = aSanScores;
    }

    /**
     * @return A_SAN_COUNT
     */
    public Integer getaSanCount() {
        return aSanCount;
    }

    /**
     * @param aSanCount
     */
    public void setaSanCount(Integer aSanCount) {
        this.aSanCount = aSanCount;
    }

    /**
     * @return A_MY_ADMINID
     */
    public String getaMyAdminid() {
        return aMyAdminid;
    }

    /**
     * @param aMyAdminid
     */
    public void setaMyAdminid(String aMyAdminid) {
        this.aMyAdminid = aMyAdminid;
    }

    /**
     * @return A_MY_SCORE
     */
    public BigDecimal getaMyScore() {
        return aMyScore;
    }

    /**
     * @param aMyScore
     */
    public void setaMyScore(BigDecimal aMyScore) {
        this.aMyScore = aMyScore;
    }

    /**
     * @return A_MY_STATUS
     */
    public Integer getaMyStatus() {
        return aMyStatus;
    }

    /**
     * @param aMyStatus
     */
    public void setaMyStatus(Integer aMyStatus) {
        this.aMyStatus = aMyStatus;
    }
}