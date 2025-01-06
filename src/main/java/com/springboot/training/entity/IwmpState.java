package com.springboot.training.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "iwmp_state", schema = "public")
public class IwmpState {

    @Id
    @Column(name = "st_code", nullable = false)
    private Integer stCode;

    @Column(name = "st_name", nullable = false, length = 100)
    private String stName;

    @Column(name = "ddp", precision = 11, scale = 0)
    private BigDecimal ddp;

    @Column(name = "dpap", precision = 11, scale = 0)
    private BigDecimal dpap;

    @Column(name = "iwdp", precision = 11, scale = 0)
    private BigDecimal iwdp;

    @Column(name = "north_east", length = 100)
    private String northEast;

    @Column(name = "payee", length = 500)
    private String payee;

    @Column(name = "payeeofficer", length = 100)
    private String payeeOfficer;

    @Column(name = "payeecode", length = 100)
    private String payeeCode;

    @Column(name = "st_capital", length = 100)
    private String stCapital;

    @Column(name = "iwmp")
    private BigDecimal iwmp;

    @Column(name = "import_type", length = 1)
    private String importType;

    @Column(name = "last_updated_by", length = 20)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "isvillagewise", length = 1)
    private String isVillageWise;

    @Column(name = "state_code2001", length = 200)
    private String stateCode2001;

    @Column(name = "state_code2011", length = 200)
    private String stateCode2011;

    @Column(name = "census_code_ported_data", length = 1)
    private String censusCodePortedData;

    @Column(name = "approval_req", length = 1)
    private String approvalReq;

    @Column(name = "approval_req_date")
    private LocalDate approvalReqDate;

    @Column(name = "state_codelgd", length = 200)
    private String stateCodeLgd;

    @Column(name = "lgd_code_ported_data", length = 1)
    private String lgdCodePortedData;

    @Column(name = "jal_shakati", precision = 2, scale = 1)
    private BigDecimal jalShakati;

    @Column(name = "wdcpmksy")
    private Integer wdcpmksy;

    // Getters and Setters
    public Integer getStCode() {
        return stCode;
    }

    public void setStCode(Integer stCode) {
        this.stCode = stCode;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public BigDecimal getDdp() {
        return ddp;
    }

    public void setDdp(BigDecimal ddp) {
        this.ddp = ddp;
    }

    public BigDecimal getDpap() {
        return dpap;
    }

    public void setDpap(BigDecimal dpap) {
        this.dpap = dpap;
    }

    public BigDecimal getIwdp() {
        return iwdp;
    }

    public void setIwdp(BigDecimal iwdp) {
        this.iwdp = iwdp;
    }

    public String getNorthEast() {
        return northEast;
    }

    public void setNorthEast(String northEast) {
        this.northEast = northEast;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPayeeOfficer() {
        return payeeOfficer;
    }

    public void setPayeeOfficer(String payeeOfficer) {
        this.payeeOfficer = payeeOfficer;
    }

    public String getPayeeCode() {
        return payeeCode;
    }

    public void setPayeeCode(String payeeCode) {
        this.payeeCode = payeeCode;
    }

    public String getStCapital() {
        return stCapital;
    }

    public void setStCapital(String stCapital) {
        this.stCapital = stCapital;
    }

    public BigDecimal getIwmp() {
        return iwmp;
    }

    public void setIwmp(BigDecimal iwmp) {
        this.iwmp = iwmp;
    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getIsVillageWise() {
        return isVillageWise;
    }

    public void setIsVillageWise(String isVillageWise) {
        this.isVillageWise = isVillageWise;
    }

    public String getStateCode2001() {
        return stateCode2001;
    }

    public void setStateCode2001(String stateCode2001) {
        this.stateCode2001 = stateCode2001;
    }

    public String getStateCode2011() {
        return stateCode2011;
    }

    public void setStateCode2011(String stateCode2011) {
        this.stateCode2011 = stateCode2011;
    }

    public String getCensusCodePortedData() {
        return censusCodePortedData;
    }

    public void setCensusCodePortedData(String censusCodePortedData) {
        this.censusCodePortedData = censusCodePortedData;
    }

    public String getApprovalReq() {
        return approvalReq;
    }

    public void setApprovalReq(String approvalReq) {
        this.approvalReq = approvalReq;
    }

    public LocalDate getApprovalReqDate() {
        return approvalReqDate;
    }

    public void setApprovalReqDate(LocalDate approvalReqDate) {
        this.approvalReqDate = approvalReqDate;
    }

    public String getStateCodeLgd() {
        return stateCodeLgd;
    }

    public void setStateCodeLgd(String stateCodeLgd) {
        this.stateCodeLgd = stateCodeLgd;
    }

    public String getLgdCodePortedData() {
        return lgdCodePortedData;
    }

    public void setLgdCodePortedData(String lgdCodePortedData) {
        this.lgdCodePortedData = lgdCodePortedData;
    }

    public BigDecimal getJalShakati() {
        return jalShakati;
    }

    public void setJalShakati(BigDecimal jalShakati) {
        this.jalShakati = jalShakati;
    }

    public Integer getWdcpmksy() {
        return wdcpmksy;
    }

    public void setWdcpmksy(Integer wdcpmksy) {
        this.wdcpmksy = wdcpmksy;
    }
}
