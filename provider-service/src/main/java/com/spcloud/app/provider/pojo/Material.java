package com.spcloud.app.provider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jntoo.db.DB;
import java.io.Serializable;

@TableName("material")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String materialnu;

    private String materialna;

    private Integer type;

    private String specification;

    private String pic;

    private String facturer;

    private String supplier;

    private String unit;

    private String level;

    private String type1;

    private Integer quantity;

    private String status;

    private String con;

    private String cont;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMaintenance1Count() {
        return DB.name("maintenance1").where("materialid", id).count();
    }

    public Long getOutboundCount() {
        return DB.name("outbound").where("materialid", id).count();
    }

    public Long getWarehousingCount() {
        return DB.name("warehousing").where("materialid", id).count();
    }

    public String getMaterialnu() {
        return materialnu;
    }

    public void setMaterialnu(String materialnu) {
        this.materialnu = materialnu == null ? "" : materialnu.trim();
    }

    public String getMaterialna() {
        return materialna;
    }

    public void setMaterialna(String materialna) {
        this.materialna = materialna == null ? "" : materialna.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type == null ? 0 : type;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? "" : specification.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? "" : pic.trim();
    }

    public String getFacturer() {
        return facturer;
    }

    public void setFacturer(String facturer) {
        this.facturer = facturer == null ? "" : facturer.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? "" : supplier.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? "" : unit.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? "" : level.trim();
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1 == null ? "" : type1.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity == null ? 0 : quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? "" : status.trim();
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con == null ? "" : con.trim();
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont == null ? "" : cont.trim();
    }
}
