package com.spcloud.app.provider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;

@TableName("hse")
public class Hse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String proname;

    private String resperson;

    private String personnel;

    private String timeon;

    private String endtime;

    private String objectives;

    private String identification;

    private String report;

    private String records;

    private String measures;

    private String compliance;

    private String status;

    private String operator;

    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAuditingCount() {
        return DB.name("auditing").where("hseid", id).count();
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

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives == null ? "" : objectives.trim();
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification == null ? "" : identification.trim();
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? "" : report.trim();
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records == null ? "" : records.trim();
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures == null ? "" : measures.trim();
    }

    public String getCompliance() {
        return compliance;
    }

    public void setCompliance(String compliance) {
        this.compliance = compliance == null ? "" : compliance.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? "" : status.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? "" : operator.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }
}
