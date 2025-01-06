package com.springboot.training.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "iwmp_district", schema = "public")
public class IwmpDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iwmp_district_dcode_seq")
    @SequenceGenerator(name = "iwmp_district_dcode_seq", sequenceName = "iwmp_district_dcode_seq", allocationSize = 1)
    @Column(name = "dcode", nullable = false)
    private Integer dcode;

    @Column(name = "st_code", nullable = false)
    private Integer stCode;

    @Column(name = "dist_code", nullable = false)
    private Integer distCode;

    @Column(name = "dist_name", nullable = false, length = 50)
    private String distName;

    @Column(name = "no_of_blocks_ddp")
    private Integer noOfBlocksDdp;

    @Column(name = "no_of_blocks_dpap")
    private Integer noOfBlocksDpap;

    @Column(name = "no_of_blocks_iwdp")
    private Integer noOfBlocksIwdp;

    @Column(name = "iwdp_count")
    private Integer iwdpCount;

    @Column(name = "ddp_count")
    private Integer ddpCount;

    @Column(name = "dpap_count")
    private Integer dpapCount;

    @Column(name = "iwdp")
    private Integer iwdp;

    @Column(name = "ddp")
    private Integer ddp;

    @Column(name = "dpap")
    private Integer dpap;

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

    @Column(name = "mong", length = 1)
    private String mong;

    @Column(name = "state_code2001", length = 200)
    private String stateCode2001;

    @Column(name = "state_code2011", length = 200)
    private String stateCode2011;

    @Column(name = "district_code2001", length = 200)
    private String districtCode2001;

    @Column(name = "district_code2011", length = 200)
    private String districtCode2011;

    @Column(name = "census_code_ported_data", length = 1)
    private String censusCodePortedData;

    @Column(name = "state_codelgd", length = 200)
    private String stateCodeLgd;

    @Column(name = "district_codelgd")
    private Integer districtCodeLgd;

    @Column(name = "lgd_code_ported_data", length = 1)
    private String lgdCodePortedData;

    @Column(name = "jal_shakati")
    private BigDecimal jalShakati;

    @Column(name = "dist_proj")
    private Boolean distProj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "st_code", referencedColumnName = "st_code", insertable = false, updatable = false)
    private IwmpState iwmpState;

    // Getters and Setters
    public Integer getDcode() {
        return dcode;
    }

    public void setDcode(Integer dcode) {
        this.dcode = dcode;
    }

    public Integer getStCode() {
        return stCode;
    }

    public void setStCode(Integer stCode) {
        this.stCode = stCode;
    }

    public Integer getDistCode() {
        return distCode;
    }

    public void setDistCode(Integer distCode) {
        this.distCode = distCode;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public Integer getNoOfBlocksDdp() {
        return noOfBlocksDdp;
    }

    public void setNoOfBlocksDdp(Integer noOfBlocksDdp) {
        this.noOfBlocksDdp = noOfBlocksDdp;
    }

    public Integer getNoOfBlocksDpap() {
        return noOfBlocksDpap;
    }

    public void setNoOfBlocksDpap(Integer noOfBlocksDpap) {
        this.noOfBlocksDpap = noOfBlocksDpap;
    }

    public Integer getNoOfBlocksIwdp() {
        return noOfBlocksIwdp;
    }

    public void setNoOfBlocksIwdp(Integer noOfBlocksIwdp) {
        this.noOfBlocksIwdp = noOfBlocksIwdp;
    }

    public Integer getIwdpCount() {
        return iwdpCount;
    }

    public void setIwdpCount(Integer iwdpCount) {
        this.iwdpCount = iwdpCount;
    }

    public Integer getDdpCount() {
        return ddpCount;
    }

    public void setDdpCount(Integer ddpCount) {
        this.ddpCount = ddpCount;
    }

    public Integer getDpapCount() {
        return dpapCount;
    }

    public void setDpapCount(Integer dpapCount) {
        this.dpapCount = dpapCount;
    }

    public Integer getIwdp() {
        return iwdp;
    }

    public void setIwdp(Integer iwdp) {
        this.iwdp = iwdp;
    }

    public Integer getDdp() {
        return ddp;
    }

    public void setDdp(Integer ddp) {
        this.ddp = ddp;
    }

    public Integer getDpap() {
        return dpap;
    }

    public void setDpap(Integer dpap) {
        this.dpap = dpap;
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

    public String getMong() {
        return mong;
    }

    public void setMong(String mong) {
        this.mong = mong;
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

    public String getDistrictCode2001() {
        return districtCode2001;
    }

    public void setDistrictCode2001(String districtCode2001) {
        this.districtCode2001 = districtCode2001;
    }

    public String getDistrictCode2011() {
        return districtCode2011;
    }

    public void setDistrictCode2011(String districtCode2011) {
        this.districtCode2011 = districtCode2011;
    }

    public String getCensusCodePortedData() {
        return censusCodePortedData;
    }

    public void setCensusCodePortedData(String censusCodePortedData) {
        this.censusCodePortedData = censusCodePortedData;
    }

    public String getStateCodeLgd() {
        return stateCodeLgd;
    }

    public void setStateCodeLgd(String stateCodeLgd) {
        this.stateCodeLgd = stateCodeLgd;
    }

    public Integer getDistrictCodeLgd() {
        return districtCodeLgd;
    }

    public void setDistrictCodeLgd(Integer districtCodeLgd) {
        this.districtCodeLgd = districtCodeLgd;
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

    public Boolean getDistProj() {
        return distProj;
    }

    public void setDistProj(Boolean distProj) {
        this.distProj = distProj;
    }

    public IwmpState getIwmpState() {
        return iwmpState;
    }

    public void setIwmpState(IwmpState iwmpState) {
        this.iwmpState = iwmpState;
    }
}
