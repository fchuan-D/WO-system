package com.spcloud.app.provider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;

@TableName("situation")
public class Situation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer planid;

    private String num;

    private String well;

    private Integer type;

    private String pressure;

    private String speed;

    private String flowrate;

    private String indicators;

    private String details;

    private String addpeople;

    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanid() {
        return planid;
    }

    public void setPlanid(Integer planid) {
        this.planid = planid == null ? 0 : planid;
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

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure == null ? "" : pressure.trim();
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed == null ? "" : speed.trim();
    }

    public String getFlowrate() {
        return flowrate;
    }

    public void setFlowrate(String flowrate) {
        this.flowrate = flowrate == null ? "" : flowrate.trim();
    }

    public String getIndicators() {
        return indicators;
    }

    public void setIndicators(String indicators) {
        this.indicators = indicators == null ? "" : indicators.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? "" : details.trim();
    }

    public String getAddpeople() {
        return addpeople;
    }

    public void setAddpeople(String addpeople) {
        this.addpeople = addpeople == null ? "" : addpeople.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }
}
