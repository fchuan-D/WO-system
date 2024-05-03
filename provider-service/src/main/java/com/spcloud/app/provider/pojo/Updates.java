package com.spcloud.app.provider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;

@TableName("updates")
public class Updates implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer planid;

    private String num;

    private String well;

    private Integer type;

    private String pic;

    private String opera;

    private String cont;

    private String pic1;

    private String updatesper;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? "" : pic.trim();
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

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1 == null ? "" : pic1.trim();
    }

    public String getUpdatesper() {
        return updatesper;
    }

    public void setUpdatesper(String updatesper) {
        this.updatesper = updatesper == null ? "" : updatesper.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }
}
