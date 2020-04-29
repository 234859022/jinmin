package com.sanposs.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_machine_task_ingrediient")
public class YhsjMachineTaskIngrediient {
    @Id
    @Column(name = "task_ingredient_id")
    private String taskIngredientId;

    /**
     * 机器使用id
     */
    @Column(name = "task_id")
    private String taskId;

    /**
     * 材料id
     */
    @Column(name = "ingredient_id")
    private String ingredientId;

    /**
     * 材料名
     */
    @Column(name = "ingredient_name")
    private String ingredientName;

    /**
     * 数量
     */
    @Column(name = "ingredient_num")
    private Integer ingredientNum;

    /**
     * 创建时间
     */
    @Column(name = "ingredient_create_time")
    private Date ingredientCreateTime;

    /**
     * 创建人
     */
    @Column(name = "ingredient_create_user")
    private String ingredientCreateUser;

    /**
     * 创建人ID
     */
    @Column(name = "ingredient_create_userid")
    private String ingredientCreateUserid;

    /**
     * @return task_ingredient_id
     */
    public String getTaskIngredientId() {
        return taskIngredientId;
    }

    /**
     * @param taskIngredientId
     */
    public void setTaskIngredientId(String taskIngredientId) {
        this.taskIngredientId = taskIngredientId;
    }

    /**
     * 获取机器使用id
     *
     * @return task_id - 机器使用id
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 设置机器使用id
     *
     * @param taskId 机器使用id
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取材料id
     *
     * @return ingredient_id - 材料id
     */
    public String getIngredientId() {
        return ingredientId;
    }

    /**
     * 设置材料id
     *
     * @param ingredientId 材料id
     */
    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    /**
     * 获取材料名
     *
     * @return ingredient_name - 材料名
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * 设置材料名
     *
     * @param ingredientName 材料名
     */
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    /**
     * 获取数量
     *
     * @return ingredient_num - 数量
     */
    public Integer getIngredientNum() {
        return ingredientNum;
    }

    /**
     * 设置数量
     *
     * @param ingredientNum 数量
     */
    public void setIngredientNum(Integer ingredientNum) {
        this.ingredientNum = ingredientNum;
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
     * 获取创建人
     *
     * @return ingredient_create_user - 创建人
     */
    public String getIngredientCreateUser() {
        return ingredientCreateUser;
    }

    /**
     * 设置创建人
     *
     * @param ingredientCreateUser 创建人
     */
    public void setIngredientCreateUser(String ingredientCreateUser) {
        this.ingredientCreateUser = ingredientCreateUser;
    }

    /**
     * 获取创建人ID
     *
     * @return ingredient_create_userid - 创建人ID
     */
    public String getIngredientCreateUserid() {
        return ingredientCreateUserid;
    }

    /**
     * 设置创建人ID
     *
     * @param ingredientCreateUserid 创建人ID
     */
    public void setIngredientCreateUserid(String ingredientCreateUserid) {
        this.ingredientCreateUserid = ingredientCreateUserid;
    }
}