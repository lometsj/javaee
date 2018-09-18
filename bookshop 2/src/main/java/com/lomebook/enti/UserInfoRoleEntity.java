package com.lomebook.enti;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserInfoRole", schema = "dbo", catalog = "book_shop3")
@IdClass(com.lomebook.enti.Info.class)
public class UserInfoRoleEntity implements Serializable{
    private int userInfoId;
    private int roleId;

    @Id
    @Column(name = "UserInfo_ID")
    public int getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Id
    @Column(name = "Role_ID")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfoRoleEntity that = (UserInfoRoleEntity) o;

        if (userInfoId != that.userInfoId) return false;
        if (roleId != that.roleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userInfoId;
        result = 31 * result + roleId;
        return result;
    }
}
