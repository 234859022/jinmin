package com.nanwang.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "E_ANSWER_DETAIL")
public class EAnswerDetail   implements Serializable{
    @Id
    @Column(name = "detail_id")
    private String detailId;

    @Column(name = "detail_questionid")
    private String detailQuestionid;

    @Column(name = "detail_answerid")
    private String detailAnswerid;

    @Column(name = "detail_questiontitle")
    private String detailQuestiontitle;

    @Column(name = "detail_questionweightd")
    private Integer detailQuestionweightd;

    @Column(name = "detail_select")
    private Integer detailSelect;

    @Column(name = "detail_score")
    private BigDecimal detailScore;

    @Column(name = "detail_questiontype")
    private String detailQuestiontype;

    @Column(name = "detail_questiontypeid")
    private String detailQuestiontypeid;

    @Column(name = "detail_remark")
    private String detailRemark;

    @Column(name = "detail_create_time")
    private Date detailCreateTime;

    /**
     * @return detail_id
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * @param detailId
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    /**
     * @return detail_questionid
     */
    public String getDetailQuestionid() {
        return detailQuestionid;
    }

    /**
     * @param detailQuestionid
     */
    public void setDetailQuestionid(String detailQuestionid) {
        this.detailQuestionid = detailQuestionid;
    }

    /**
     * @return detail_answerid
     */
    public String getDetailAnswerid() {
        return detailAnswerid;
    }

    /**
     * @param detailAnswerid
     */
    public void setDetailAnswerid(String detailAnswerid) {
        this.detailAnswerid = detailAnswerid;
    }

    /**
     * @return detail_questiontitle
     */
    public String getDetailQuestiontitle() {
        return detailQuestiontitle;
    }

    /**
     * @param detailQuestiontitle
     */
    public void setDetailQuestiontitle(String detailQuestiontitle) {
        this.detailQuestiontitle = detailQuestiontitle;
    }

    /**
     * @return detail_questionweightd
     */
    public Integer getDetailQuestionweightd() {
        return detailQuestionweightd;
    }

    /**
     * @param detailQuestionweightd
     */
    public void setDetailQuestionweightd(Integer detailQuestionweightd) {
        this.detailQuestionweightd = detailQuestionweightd;
    }

    /**
     * @return detail_select
     */
    public Integer getDetailSelect() {
        return detailSelect;
    }

    /**
     * @param detailSelect
     */
    public void setDetailSelect(Integer detailSelect) {
        this.detailSelect = detailSelect;
    }

    /**
     * @return detail_score
     */
    public BigDecimal getDetailScore() {
        return detailScore;
    }

    /**
     * @param detailScore
     */
    public void setDetailScore(BigDecimal detailScore) {
        this.detailScore = detailScore;
    }

    /**
     * @return detail_questiontype
     */
    public String getDetailQuestiontype() {
        return detailQuestiontype;
    }

    /**
     * @param detailQuestiontype
     */
    public void setDetailQuestiontype(String detailQuestiontype) {
        this.detailQuestiontype = detailQuestiontype;
    }

    /**
     * @return detail_questiontypeid
     */
    public String getDetailQuestiontypeid() {
        return detailQuestiontypeid;
    }

    /**
     * @param detailQuestiontypeid
     */
    public void setDetailQuestiontypeid(String detailQuestiontypeid) {
        this.detailQuestiontypeid = detailQuestiontypeid;
    }

    /**
     * @return detail_remark
     */
    public String getDetailRemark() {
        return detailRemark;
    }

    /**
     * @param detailRemark
     */
    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    /**
     * @return detail_create_time
     */
    public Date getDetailCreateTime() {
        return detailCreateTime;
    }

    /**
     * @param detailCreateTime
     */
    public void setDetailCreateTime(Date detailCreateTime) {
        this.detailCreateTime = detailCreateTime;
    }
}