package com.nailonline.client.entity;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.annotations.Ignore;

/**
 * Created by Roman T. on 16.02.2017.
 */

public class Job implements Parcelable {
    private int jobId;
    private Integer jobLocationId;
    private Integer jobMasterId;
    private Integer jobSkillId;
    private Integer jobStateId;
    private Integer jobUserId;
    private Long lastChangeDate;
    private Integer price;
    private Long startDate;
    private Long endDate;
    private Integer jobAmount;
    private String jobComments;

    @Ignore
    private boolean bonusPay;

    protected Job(Parcel in) {
        jobId = in.readInt();
        jobLocationId = in.readByte() == 0x00 ? null : in.readInt();
        jobMasterId = in.readByte() == 0x00 ? null : in.readInt();
        jobSkillId = in.readByte() == 0x00 ? null : in.readInt();
        jobStateId = in.readByte() == 0x00 ? null : in.readInt();
        jobUserId = in.readByte() == 0x00 ? null : in.readInt();
        lastChangeDate = in.readByte() == 0x00 ? null : in.readLong();
        price = in.readByte() == 0x00 ? null : in.readInt();
        startDate = in.readByte() == 0x00 ? null : in.readLong();
        endDate = in.readByte() == 0x00 ? null : in.readLong();
        jobAmount = in.readByte() == 0x00 ? null : in.readInt();
        jobComments = in.readString();
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
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

    public Long getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Long lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
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

    public Integer getJobAmount() {
        return jobAmount;
    }

    public void setJobAmount(Integer jobAmount) {
        this.jobAmount = jobAmount;
    }

    public String getJobComments() {
        return jobComments;
    }

    public void setJobComments(String jobComments) {
        this.jobComments = jobComments;
    }

    public boolean isBonusPay() {
        return bonusPay;
    }

    public void setBonusPay(boolean bonusPay) {
        this.bonusPay = bonusPay;
    }

    public static Creator<Job> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", jobLocationId=" + jobLocationId +
                ", jobMasterId=" + jobMasterId +
                ", jobSkillId=" + jobSkillId +
                ", jobStateId=" + jobStateId +
                ", jobUserId=" + jobUserId +
                ", lastChangeDate=" + lastChangeDate +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", jobAmount=" + jobAmount +
                ", jobComments='" + jobComments + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(jobId);
        if (jobLocationId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(jobLocationId);
        }
        if (jobMasterId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(jobMasterId);
        }
        if (jobSkillId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(jobSkillId);
        }
        if (jobStateId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(jobStateId);
        }
        if (jobUserId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(jobUserId);
        }
        if (lastChangeDate == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(lastChangeDate);
        }
        if (price == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(price);
        }
        if (startDate == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(startDate);
        }
        if (endDate == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(endDate);
        }
        if (jobAmount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(jobAmount);
        }
        dest.writeString(jobComments);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}
