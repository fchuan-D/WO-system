package com.spcloud.app.provider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;

@TableName("completed")
public class Completed implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer planid;

    private String num;

    private String well;

    private Integer type;

    private String res;

    private String pic;

    private String report;

    private String contents;

    private String cojnt;

    private String handler;

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

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res == null ? "" : res.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? "" : pic.trim();
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? "" : report.trim();
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? "" : contents.trim();
    }

    public String getCojnt() {
        return cojnt;
    }

    public void setCojnt(String cojnt) {
        this.cojnt = cojnt == null ? "" : cojnt.trim();
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler == null ? "" : handler.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }
}
