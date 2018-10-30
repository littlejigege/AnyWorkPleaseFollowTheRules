package com.qgstudio.anywork.data.model;

/**
 * Created by FunriLy on 2017/7/10.
 * From small beginnings comes great things.
 */
public class Relation {

    private int relationId;     //ID
    private int organizationId;        //组织ID
    private int userId;         //用户ID
    private int role;           //角色，预留字段

    public Relation() {
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
