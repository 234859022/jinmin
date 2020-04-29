package com.nanwang.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "E_ANSWER")
public class Common implements Serializable{
    @Id
    @Column(name = "answer_id")
    private String answerId;

}