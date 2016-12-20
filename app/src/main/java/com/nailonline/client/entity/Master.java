package com.nailonline.client.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman on 11.12.2016.
 */

public class Master extends RealmObject {

    @PrimaryKey
    private int masterId;
    private Integer isBlocked;
    private Integer masterBalance;
    private String masterBirthDay;
    private String masterCode;
    private String masterComment;
    private String masterFb;
    private String masterFirstName;
    private Integer masterGender;
    private String masterIg;
    private String masterLastName;
    private String masterLogin;
    private String masterMail;
    private Integer masterMainLocationId;
    private String masterMiddleName;
    private String masterOk;
    private String masterPass;
    private String masterPassword;
    private String masterPhone;
    private String masterPhoto;
    private String masterPushToken;
    private Long masterRegisterDate;
    private Integer masterTbs1;
    private Integer masterTbs2;
    private Integer masterTbs3;
    private Integer masterTbs4;
    private String masterToken;
    private String masterVk;

    private MasterLocation masterLocation;

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public Integer getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Integer isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Integer getMasterBalance() {
        return masterBalance;
    }

    public void setMasterBalance(Integer masterBalance) {
        this.masterBalance = masterBalance;
    }

    public String getMasterBirthDay() {
        return masterBirthDay;
    }

    public void setMasterBirthDay(String masterBirthDay) {
        this.masterBirthDay = masterBirthDay;
    }

    public String getMasterCode() {
        return masterCode;
    }

    public void setMasterCode(String masterCode) {
        this.masterCode = masterCode;
    }

    public String getMasterComment() {
        return masterComment;
    }

    public void setMasterComment(String masterComment) {
        this.masterComment = masterComment;
    }

    public String getMasterFb() {
        return masterFb;
    }

    public void setMasterFb(String masterFb) {
        this.masterFb = masterFb;
    }

    public String getMasterFirstName() {
        return masterFirstName;
    }

    public void setMasterFirstName(String masterFirstName) {
        this.masterFirstName = masterFirstName;
    }

    public Integer getMasterGender() {
        return masterGender;
    }

    public void setMasterGender(Integer masterGender) {
        this.masterGender = masterGender;
    }

    public String getMasterIg() {
        return masterIg;
    }

    public void setMasterIg(String masterIg) {
        this.masterIg = masterIg;
    }

    public String getMasterLastName() {
        return masterLastName;
    }

    public void setMasterLastName(String masterLastName) {
        this.masterLastName = masterLastName;
    }

    public String getMasterLogin() {
        return masterLogin;
    }

    public void setMasterLogin(String masterLogin) {
        this.masterLogin = masterLogin;
    }

    public String getMasterMail() {
        return masterMail;
    }

    public void setMasterMail(String masterMail) {
        this.masterMail = masterMail;
    }

    public Integer getMasterMainLocationId() {
        return masterMainLocationId;
    }

    public void setMasterMainLocationId(Integer masterMainLocationId) {
        this.masterMainLocationId = masterMainLocationId;
    }

    public String getMasterMiddleName() {
        return masterMiddleName;
    }

    public void setMasterMiddleName(String masterMiddleName) {
        this.masterMiddleName = masterMiddleName;
    }

    public String getMasterOk() {
        return masterOk;
    }

    public void setMasterOk(String masterOk) {
        this.masterOk = masterOk;
    }

    public String getMasterPass() {
        return masterPass;
    }

    public void setMasterPass(String masterPass) {
        this.masterPass = masterPass;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public String getMasterPhone() {
        return masterPhone;
    }

    public void setMasterPhone(String masterPhone) {
        this.masterPhone = masterPhone;
    }

    public String getMasterPhoto() {
        return masterPhoto;
    }

    public void setMasterPhoto(String masterPhoto) {
        this.masterPhoto = masterPhoto;
    }

    public String getMasterPushToken() {
        return masterPushToken;
    }

    public void setMasterPushToken(String masterPushToken) {
        this.masterPushToken = masterPushToken;
    }

    public Long getMasterRegisterDate() {
        return masterRegisterDate;
    }

    public void setMasterRegisterDate(Long masterRegisterDate) {
        this.masterRegisterDate = masterRegisterDate;
    }

    public Integer getMasterTbs1() {
        return masterTbs1;
    }

    public void setMasterTbs1(Integer masterTbs1) {
        this.masterTbs1 = masterTbs1;
    }

    public Integer getMasterTbs2() {
        return masterTbs2;
    }

    public void setMasterTbs2(Integer masterTbs2) {
        this.masterTbs2 = masterTbs2;
    }

    public Integer getMasterTbs3() {
        return masterTbs3;
    }

    public void setMasterTbs3(Integer masterTbs3) {
        this.masterTbs3 = masterTbs3;
    }

    public Integer getMasterTbs4() {
        return masterTbs4;
    }

    public void setMasterTbs4(Integer masterTbs4) {
        this.masterTbs4 = masterTbs4;
    }

    public String getMasterToken() {
        return masterToken;
    }

    public void setMasterToken(String masterToken) {
        this.masterToken = masterToken;
    }

    public String getMasterVk() {
        return masterVk;
    }

    public void setMasterVk(String masterVk) {
        this.masterVk = masterVk;
    }

    public MasterLocation getMasterLocation() {
        return masterLocation;
    }

    public void setMasterLocation(MasterLocation masterLocation) {
        this.masterLocation = masterLocation;
    }
}
