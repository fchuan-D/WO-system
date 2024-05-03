package com.spcloud.app.provider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;

@TableName("plan")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String num;

    private String well;

    private Integer type;

    private String pic;

    private String timeon;

    private String endtime;

    private Integer duration;

    private String location;

    private String res;

    private String operators;

    private String materials;

    private String information;

    private String status;

    private String measures;

    private String cost;

    private String programme;

    private String con;

    private String opera;

    private String cont;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUpdatesCount() {
        return DB.name("updates").where("planid", id).count();
    }

    public Long getTrackingCount() {
        return DB.name("tracking").where("planid", id).count();
    }

    public Long getSituationCount() {
        return DB.name("situation").where("planid", id).count();
    }

    public Long getCompletedCount() {
        return DB.name("completed").where("planid", id).count();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? "" : num.trim();
    }

    public String getWell() {
        return well;
    }

    public void setWell(String well) {
        this.well = well == null ? "" : well.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type == null ? 0 : type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? "" : pic.trim();
    }

    public String getTimeon() {
        return timeon;
    }

    public void setTimeon(String timeon) {
        this.timeon = timeon == null ? "" : timeon.trim();
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? "" : endtime.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration == null ? 0 : duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? "" : location.trim();
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res == null ? "" : res.trim();
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators == null ? "" : operators.trim();
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials == null ? "" : materials.trim();
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information == null ? "" : information.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? "" : status.trim();
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures == null ? "" : measures.trim();
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost == null ? "" : cost.trim();
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme == null ? "" : programme.trim();
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con == null ? "" : con.trim();
    }

    public String getOpera() {
        return opera;
    }

    public void setOpera(String opera) {
        this.opera = opera == null ? "" : opera.trim();
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont == null ? "" : cont.trim();
    }
}
