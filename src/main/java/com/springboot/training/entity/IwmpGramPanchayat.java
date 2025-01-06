package com.springboot.training.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "iwmp_gram_panchayat", schema = "public")
public class IwmpGramPanchayat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iwmp_gram_panchayat_gcode_seq")
    @SequenceGenerator(name = "iwmp_gram_panchayat_gcode_seq", sequenceName = "iwmp_gram_panchayat_gcode_seq", allocationSize = 1)
    @Column(name = "gcode", nullable = false)
    private Integer gcode;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "block_code", nullable = false)
    private Integer blockCode;

    @Column(name = "dist_code", nullable = false)
    private Integer distCode;

    @Column(name = "gram_panchayat_lgd_code", nullable = false)
    private Integer gramPanchayatLgdCode;

    @Column(name = "gram_panchayat_name", length = 100)
    private String gramPanchayatName;

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

    @Column(name = "bcode")
    private Integer bcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bcode", referencedColumnName = "bcode", insertable = false, updatable = false)
    private IwmpBlock iwmpBlock;

    // Getters and Setters

    public Integer getGcode() {
        return gcode;
    }

    public void setGcode(Integer gcode) {
        this.gcode = gcode;
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

    public Integer getDistCode() {
        return distCode;
    }

    public void setDistCode(Integer distCode) {
        this.distCode = distCode;
    }

    public Integer getGramPanchayatLgdCode() {
        return gramPanchayatLgdCode;
    }

    public void setGramPanchayatLgdCode(Integer gramPanchayatLgdCode) {
        this.gramPanchayatLgdCode = gramPanchayatLgdCode;
    }

    public String getGramPanchayatName() {
        return gramPanchayatName;
    }

    public void setGramPanchayatName(String gramPanchayatName) {
        this.gramPanchayatName = gramPanchayatName;
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

    public Integer getBcode() {
        return bcode;
    }

    public void setBcode(Integer bcode) {
        this.bcode = bcode;
    }

    public IwmpBlock getIwmpBlock() {
        return iwmpBlock;
    }

    public void setIwmpBlock(IwmpBlock iwmpBlock) {
        this.iwmpBlock = iwmpBlock;
    }
}

