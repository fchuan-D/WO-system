package com.spcloud.app.provider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;

@TableName("auditing")
public class Auditing implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer hseid;

    private String proname;

    private String resperson;

    private String personnel;

    private String operator;

    private String auditing1;

    private String cont;

    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHseid() {
        return hseid;
    }

    public void setHseid(Integer hseid) {
        this.hseid = hseid == null ? 0 : hseid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname == null ? "" : proname.trim();
    }

    public String getResperson() {
        return resperson;
    }

    public void setResperson(String resperson) {
        this.resperson = resperson == null ? "" : resperson.trim();
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel == null ? "" : personnel.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? "" : operator.trim();
    }

    public String getAuditing1() {
        return auditing1;
    }

    public void setAuditing1(String auditing1) {
        this.auditing1 = auditing1 == null ? "" : auditing1.trim();
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont == null ? "" : cont.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }
}
