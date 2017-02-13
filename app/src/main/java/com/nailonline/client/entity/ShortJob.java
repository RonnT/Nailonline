package com.nailonline.client.entity;

/**
 * Created by Roman T. on 13.02.2017.
 */

public class ShortJob {
    private Integer jobId;
    private Integer jobLocationId;
    private Integer jobMasterId;
    private Integer jobSkillId;
    private Integer jobStateId;
    private Integer jobUserId;
    private Integer jobAmount;
    private Integer price;
    private Long startDate;
    private Long endDate;
    private Long lastChangeDate;
    private String jobComments;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobLocationId() {
        return jobLocationId;
    }

    public void setJobLocationId(Integer jobLocationId) {
        this.jobLocationId = jobLocationId;
    }

    public Integer getJobMasterId() {
        return jobMasterId;
    }

    public void setJobMasterId(Integer jobMasterId) {
        this.jobMasterId = jobMasterId;
    }

    public Integer getJobSkillId() {
        return jobSkillId;
    }

    public void setJobSkillId(Integer jobSkillId) {
        this.jobSkillId = jobSkillId;
    }

    public Integer getJobStateId() {
        return jobStateId;
    }

    public void setJobStateId(Integer jobStateId) {
        this.jobStateId = jobStateId;
    }

    public Integer getJobUserId() {
        return jobUserId;
    }

    public void setJobUserId(Integer jobUserId) {
        this.jobUserId = jobUserId;
    }

    public Integer getJobAmount() {
        return jobAmount;
    }

    public void setJobAmount(Integer jobAmount) {
        this.jobAmount = jobAmount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Long lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public String getJobComments() {
        return jobComments;
    }

    public void setJobComments(String jobComments) {
        this.jobComments = jobComments;
    }

    @Override
    public String toString() {
        return "ShortJob{" +
                "jobId=" + jobId +
                ", jobLocationId=" + jobLocationId +
                ", jobMasterId=" + jobMasterId +
                ", jobSkillId=" + jobSkillId +
                ", jobStateId=" + jobStateId +
                ", jobUserId=" + jobUserId +
                ", jobAmount=" + jobAmount +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", lastChangeDate=" + lastChangeDate +
                ", jobComments='" + jobComments + '\'' +
                '}';
    }
}
