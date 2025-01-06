package com.springboot.training.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "iwmp_village", schema = "public")
public class IwmpVillage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iwmp_village_vcode_seq")
    @SequenceGenerator(name = "iwmp_village_vcode_seq", sequenceName = "iwmp_village_vcode_seq", allocationSize = 1)
    @Column(name = "vcode", nullable = false)
    private Integer vcode;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "block_code", nullable = false)
    private Integer blockCode;

    @Column(name = "census_code_ported_data", length = 1)
    private String censusCodePortedData;

    @Column(name = "dist_code", nullable = false)
    private Integer distCode;

    @Column(name = "district_code2001", length = 200)
    private String districtCode2001;

    @Column(name = "district_code2011", length = 200)
    private String districtCode2011;

    @Column(name = "gram_panchayat_lgdcode", nullable = false)
    private Integer gramPanchayatLgdCode;

    @Column(name = "import_type", length = 4)
    private String importType;

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

    @Column(name = "subdistrict_code2001", length = 200)
    private String subdistrictCode2001;

    @Column(name = "subdistrict_code2011", length = 200)
    private String subdistrictCode2011;

    @Column(name = "village_code2001", length = 200)
    private String villageCode2001;

    @Column(name = "village_code2011", length = 200)
    private String villageCode2011;

    @Column(name = "village_lgdcode", nullable = false)
    private Integer villageLgdCode;

    @Column(name = "village_name", length = 200, nullable = false)
    private String villageName;

    @Column(name = "gcode")
    private Integer gcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gcode", referencedColumnName = "gcode", insertable = false, updatable = false)
    private IwmpGramPanchayat iwmpGramPanchayat;

    // Getters and Setters

    public Integer getVcode() {
        return vcode;
    }

    public void setVcode(Integer vcode) {
        this.vcode = vcode;
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

    public String getCensusCodePortedData() {
        return censusCodePortedData;
    }

    public void setCensusCodePortedData(String censusCodePortedData) {
        this.censusCodePortedData = censusCodePortedData;
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

    public Integer getGramPanchayatLgdCode() {
        return gramPanchayatLgdCode;
    }

    public void setGramPanchayatLgdCode(Integer gramPanchayatLgdCode) {
        this.gramPanchayatLgdCode = gramPanchayatLgdCode;
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

    public String getVillageCode2001() {
        return villageCode2001;
    }

    public void setVillageCode2001(String villageCode2001) {
        this.villageCode2001 = villageCode2001;
    }

    public String getVillageCode2011() {
        return villageCode2011;
    }

    public void setVillageCode2011(String villageCode2011) {
        this.villageCode2011 = villageCode2011;
    }

    public Integer getVillageLgdCode() {
        return villageLgdCode;
    }

    public void setVillageLgdCode(Integer villageLgdCode) {
        this.villageLgdCode = villageLgdCode;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Integer getGcode() {
        return gcode;
    }

    public void setGcode(Integer gcode) {
        this.gcode = gcode;
    }

    public IwmpGramPanchayat getIwmpGramPanchayat() {
        return iwmpGramPanchayat;
    }

    public void setIwmpGramPanchayat(IwmpGramPanchayat iwmpGramPanchayat) {
        this.iwmpGramPanchayat = iwmpGramPanchayat;
    }
}
