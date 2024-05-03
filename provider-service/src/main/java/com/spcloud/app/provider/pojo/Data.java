package com.spcloud.app.provider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;

@TableName("data")
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String number1;

    private String wellnumber;

    private String pic;

    private String depth;

    private String diameter;

    private String mudproperties;

    private String bittype;

    private String speed;

    private String report;

    private String details;

    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1 == null ? "" : number1.trim();
    }

    public String getWellnumber() {
        return wellnumber;
    }

    public void setWellnumber(String wellnumber) {
        this.wellnumber = wellnumber == null ? "" : wellnumber.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? "" : pic.trim();
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth == null ? "" : depth.trim();
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter == null ? "" : diameter.trim();
    }

    public String getMudproperties() {
        return mudproperties;
    }

    public void setMudproperties(String mudproperties) {
        this.mudproperties = mudproperties == null ? "" : mudproperties.trim();
    }

    public String getBittype() {
        return bittype;
    }

    public void setBittype(String bittype) {
        this.bittype = bittype == null ? "" : bittype.trim();
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed == null ? "" : speed.trim();
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? "" : report.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? "" : details.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }
}
