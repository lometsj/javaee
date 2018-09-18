package com.lomebook.enti;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Role", schema = "dbo", catalog = "book_shop3")
public class RoleEntity {
    private int id;
    private String roleName;
    private short delFlag;
    private short roleType;
    private Timestamp subTime;
    private String remark;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "native")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "RoleName")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "DelFlag")
    public short getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(short delFlag) {
        this.delFlag = delFlag;
    }

    @Basic
    @Column(name = "RoleType")
    public short getRoleType() {
        return roleType;
    }

    public void setRoleType(short roleType) {
        this.roleType = roleType;
    }

    @Basic
    @Column(name = "SubTime")
    public Timestamp getSubTime() {
        return subTime;
    }

    public void setSubTime(Timestamp subTime) {
        this.subTime = subTime;
    }

    @Basic
    @Column(name = "Remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleEntity that = (RoleEntity) o;

        if (id != that.id) return false;
        if (delFlag != that.delFlag) return false;
        if (roleType != that.roleType) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;
        if (subTime != null ? !subTime.equals(that.subTime) : that.subTime != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (int) delFlag;
        result = 31 * result + (int) roleType;
        result = 31 * result + (subTime != null ? subTime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
