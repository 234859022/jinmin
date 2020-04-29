package com.sanposs.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_ingredient")
public class YhsjIngredient {
    /**
     * 配料id
     */
    @Id
    @Column(name = "ingredient_id")
    private String ingredientId;

    /**
     * 配料名称
     */
    @Column(name = "ingredient_name")
    private String ingredientName;

    /**
     * 配料备注
     */
    @Column(name = "ingredient_remark")
    private String ingredientRemark;

    /**
     * 创建时间
     */
    @Column(name = "ingredient_create_time")
    private Date ingredientCreateTime;

    /**
     * 是否删除，1是，否
     */
    @Column(name = "ingredient_is_delete")
    private Integer ingredientIsDelete;

    /**
     * 获取配料id
     *
     * @return ingredient_id - 配料id
     */
    public String getIngredientId() {
        return ingredientId;
    }

    /**
     * 设置配料id
     *
     * @param ingredientId 配料id
     */
    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    /**
     * 获取配料名称
     *
     * @return ingredient_name - 配料名称
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * 设置配料名称
     *
     * @param ingredientName 配料名称
     */
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    /**
     * 获取配料备注
     *
     * @return ingredient_remark - 配料备注
     */
    public String getIngredientRemark() {
        return ingredientRemark;
    }

    /**
     * 设置配料备注
     *
     * @param ingredientRemark 配料备注
     */
    public void setIngredientRemark(String ingredientRemark) {
        this.ingredientRemark = ingredientRemark;
    }

    /**
     * 获取创建时间
     *
     * @return ingredient_create_time - 创建时间
     */
    public Date getIngredientCreateTime() {
        return ingredientCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param ingredientCreateTime 创建时间
     */
    public void setIngredientCreateTime(Date ingredientCreateTime) {
        this.ingredientCreateTime = ingredientCreateTime;
    }

    /**
     * 获取是否删除，1是，否
     *
     * @return ingredient_is_delete - 是否删除，1是，否
     */
    public Integer getIngredientIsDelete() {
        return ingredientIsDelete;
    }

    /**
     * 设置是否删除，1是，否
     *
     * @param ingredientIsDelete 是否删除，1是，否
     */
    public void setIngredientIsDelete(Integer ingredientIsDelete) {
        this.ingredientIsDelete = ingredientIsDelete;
    }
}