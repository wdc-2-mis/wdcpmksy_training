package com.springboot.training.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "iwmp_block", schema = "public")
public class IwmpBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iwmp_block_bcode_seq")
    @SequenceGenerator(name = "iwmp_block_bcode_seq", sequenceName = "iwmp_block_bcode_seq", allocationSize = 1)
    @Column(name = "bcode", nullable = false)
    private Integer bcode;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "block_code", nullable = false)
    private Integer blockCode;

    @Column(name = "block_codelgd")
    private Integer blockCodeLgd;

    @Column(name = "block_name", nullable = false, length = 50)
    private String blockName;

    @Column(name = "census_code_ported_data", length = 1)
    private String censusCodePortedData;

    @Column(name = "ddp", length = 11)
    private String ddp;

    @Column(name = "dist_code", nullable = false)
    private Integer distCode;

    @Column(name = "district_code2001", length = 200)
    private String districtCode2001;

    @Column(name = "district_code2011", length = 200)
    private String districtCode2011;

    @Column(name = "district_codelgd")
    private Integer districtCodeLgd;

    @Column(name = "dpap", length = 11)
    private String dpap;

    @Column(name = "import_type", length = 4)
    private String importType;

    @Column(name = "iwdp", length = 11)
    private String iwdp;

    @Column(name = "iwmp")
    private Integer iwmp;

    @Column(name = "last_updated_by", length = 20)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    @Column(name = "request_ip", length = 20)
    private String requestIp;

    @Column(name = "st_code", nullable = false)
    private Integer stCode;

    @Column(name = "state_code2001", length = 200)
    private String stateCode2001;

    @Column(name = "state_code2011", length = 200)
    private String stateCode2011;

    @Column(name = "state_codelgd")
    private Integer stateCodeLgd;

    @Column(name = "subdistrict_code2001", length = 200)
    private String subdistrictCode2001;

    @Column(name = "subdistrict_code2011", length = 200)
    private String subdistrictCode2011;

    @Column(name = "dcode")
    private Integer dcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dcode", referencedColumnName = "dcode", insertable = false, updatable = false)
    private IwmpDistrict iwmpDistrict;

    // Getters and Setters
    public Integer getBcode() {
        return bcode;
    }

    public void setBcode(Integer bcode) {
        this.bcode = bcode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(Integer blockCode) {
        this.blockCode = blockCode;
    }

    public Integer getBlockCodeLgd() {
        return blockCodeLgd;
    }

    public void setBlockCodeLgd(Integer blockCodeLgd) {
        this.blockCodeLgd = blockCodeLgd;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getCensusCodePortedData() {
        return censusCodePortedData;
    }

    public void setCensusCodePortedData(String censusCodePortedData) {
        this.censusCodePortedData = censusCodePortedData;
    }

    public String getDdp() {
        return ddp;
    }

    public void setDdp(String ddp) {
        this.ddp = ddp;
    }

    public Integer getDistCode() {
        return distCode;
    }

    public void setDistCode(Integer distCode) {
        this.distCode = distCode;
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

    public Integer getDistrictCodeLgd() {
        return districtCodeLgd;
    }

    public void setDistrictCodeLgd(Integer districtCodeLgd) {
        this.districtCodeLgd = districtCodeLgd;
    }

    public String getDpap() {
        return dpap;
    }

    public void setDpap(String dpap) {
        this.dpap = dpap;
    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public String getIwdp() {
        return iwdp;
    }

    public void setIwdp(String iwdp) {
        this.iwdp = iwdp;
    }

    public Integer getIwmp() {
        return iwmp;
    }

    public void setIwmp(Integer iwmp) {
        this.iwmp = iwmp;
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

    public Integer getStCode() {
        return stCode;
    }

    public void setStCode(Integer stCode) {
        this.stCode = stCode;
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

    public Integer getStateCodeLgd() {
        return stateCodeLgd;
    }

    public void setStateCodeLgd(Integer stateCodeLgd) {
        this.stateCodeLgd = stateCodeLgd;
    }

    public String getSubdistrictCode2001() {
        return subdistrictCode2001;
    }

    public void setSubdistrictCode2001(String subdistrictCode2001) {
        this.subdistrictCode2001 = subdistrictCode2001;
    }

    public String getSubdistrictCode2011() {
        return subdistrictCode2011;
    }

    public void setSubdistrictCode2011(String subdistrictCode2011) {
        this.subdistrictCode2011 = subdistrictCode2011;
    }

    public Integer getDcode() {
        return dcode;
    }

    public void setDcode(Integer dcode) {
        this.dcode = dcode;
    }

    public IwmpDistrict getIwmpDistrict() {
        return iwmpDistrict;
    }

    public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
        this.iwmpDistrict = iwmpDistrict;
    }
}

