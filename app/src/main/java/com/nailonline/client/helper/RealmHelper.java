package com.nailonline.client.helper;

import com.nailonline.client.entity.DutyChart;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.MasterLocation;
import com.nailonline.client.entity.Present;
import com.nailonline.client.entity.Promo;
import com.nailonline.client.entity.Region;
import com.nailonline.client.entity.Skill;
import com.nailonline.client.entity.SkillsTemplate;
import com.nailonline.client.entity.UserTheme;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by Roman T. on 16.12.2016.
 */

public class RealmHelper {

    public static void clearAllForClass(Realm realm, final Class<? extends RealmModel> cls){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(cls);
            }
        });
    }

    public static void clearAllForClass(final Class<? extends RealmModel> cls) {
        Realm realm = Realm.getDefaultInstance();
        clearAllForClass(realm, cls);
        realm.close();
    }

    public static UserTheme getDefaultTheme() {
        Realm realm = Realm.getDefaultInstance();
        UserTheme result = realm.copyFromRealm(realm.where(UserTheme.class).equalTo("themeDefault", 1).findFirst());
        realm.close();
        return result;
    }

    public static UserTheme getUserTheme(int id) {
        Realm realm = Realm.getDefaultInstance();
        UserTheme result = realm.copyFromRealm(realm.where(UserTheme.class).equalTo("themeId", id).findFirst());
        realm.close();
        return result;
    }

    public static List<UserTheme> getAllThemes() {
        Realm realm = Realm.getDefaultInstance();
        List<UserTheme> result = realm.copyFromRealm(realm.where(UserTheme.class).findAll());
        realm.close();
        return result;
    }

    public static Promo getPromoById(int id){
        Realm realm = Realm.getDefaultInstance();
        Promo result = realm.copyFromRealm(realm.where(Promo.class).equalTo("promoId", id).findFirst());
        realm.close();
        return result;
    }

    public static List<Promo> getAllPromo() {
        Realm realm = Realm.getDefaultInstance();
        List<Promo> result = realm.copyFromRealm(realm.where(Promo.class).findAll());
        realm.close();
        return result;
    }

    public static Master getMasterById(int id){
        Realm realm = Realm.getDefaultInstance();
        Master result = realm.copyFromRealm(realm.where(Master.class).equalTo("masterId", id).findFirst());
        MasterLocation location = realm.where(MasterLocation.class).equalTo("masterId", result.getMasterId()).findFirst();
        if (location != null) {
            result.setMasterLocation(realm.copyFromRealm(location));
        }
        realm.close();
        return result;
    }

    public static List<Master> getAllMasters(){
        Realm realm = Realm.getDefaultInstance();
        List<Master> tempMasterList = realm.copyFromRealm(realm.where(Master.class).findAll());
        List<Master> resultList = new ArrayList<>();
        for (Master master : tempMasterList){
            MasterLocation location = realm.where(MasterLocation.class).equalTo("masterId", master.getMasterId()).findFirst();
            //if location == null, we cannot place master on the map, master skipped
            if (location != null) {
                master.setMasterLocation(realm.copyFromRealm(location));
                resultList.add(master);
            }
        }
        realm.close();
        return resultList;
    }

    public static List<Master> getMastersForRegion(List<Integer> regionIdList){
        Realm realm = Realm.getDefaultInstance();
        List<Master> tempMasterList = realm.copyFromRealm(realm.where(Master.class).findAll());
        List<Master> resultList = new ArrayList<>();
        for (Master master : tempMasterList){
            MasterLocation location = realm.where(MasterLocation.class).equalTo("masterId", master.getMasterId()).findFirst();
            //if location == null, we cannot place master on the map, master skipped
            if (location != null && regionIdList.contains(location.geteRegionId())) {
                master.setMasterLocation(realm.copyFromRealm(location));
                resultList.add(master);
            }
        }
        realm.close();
        return resultList;
    }

    public static List<Master> getMastersWithSkillsTemplate(int templateId){
        List<Skill> skillList = getSkillsByTemplate(templateId);
        HashSet<Integer> masterIdSet = new HashSet<>();
        for (Skill skill : skillList){
            masterIdSet.add(skill.getMasterId());
        }
        List<Master> resultList = new ArrayList<>();
        if (masterIdSet.isEmpty()) return resultList;
        Integer[] masterIdArray = masterIdSet.toArray(new Integer[masterIdSet.size()]);
        Realm realm = Realm.getDefaultInstance();
        List<Master> tempMasterList = realm.copyFromRealm(realm.where(Master.class).in("masterId", masterIdArray).findAll());
        for (Master master : tempMasterList){
            MasterLocation location = realm.where(MasterLocation.class).equalTo("masterId", master.getMasterId()).findFirst();
            //if location == null, we cannot place master on the map, master skipped
            if (location != null) {
                master.setMasterLocation(realm.copyFromRealm(location));
                resultList.add(master);
            }
        }
        realm.close();
        return resultList;
    }

    public static Skill getSkillById(int id) {
        Realm realm = Realm.getDefaultInstance();
        Skill result = realm.copyFromRealm(realm.where(Skill.class).equalTo("skillId", id).findFirst());
        realm.close();
        return result;
    }

    public static List<Skill> getSkillsByTemplate(int templateId) {
        Realm realm = Realm.getDefaultInstance();
        List<Skill> result = realm.copyFromRealm(realm.where(Skill.class).equalTo("templateId", templateId).findAll());
        realm.close();
        return result;
    }

    public static List<Skill> getSkillsByMaster(int masterId) {
        Realm realm = Realm.getDefaultInstance();
        List<Skill> result = realm.copyFromRealm(realm.where(Skill.class).equalTo("masterId", masterId).findAll());
        realm.close();
        return result;
    }

    public static List<Skill> getSkillsByPresent(int presentId) {
        Realm realm = Realm.getDefaultInstance();
        List<Skill> result = realm.copyFromRealm(realm.where(Skill.class).equalTo("presentId", presentId).findAll());
        realm.close();
        return result;
    }

    public static List<SkillsTemplate> getAllSkillsTemplates() {
        Realm realm = Realm.getDefaultInstance();
        List<SkillsTemplate> result = realm.copyFromRealm(realm.where(SkillsTemplate.class).findAll());
        realm.close();
        return result;
    }

    public static SkillsTemplate getSkillsTemplateById(int id) {
        Realm realm = Realm.getDefaultInstance();
        SkillsTemplate result = realm.copyFromRealm(realm.where(SkillsTemplate.class).equalTo("templateId", id).findFirst());
        realm.close();
        return result;
    }

    public static List<Present> getEnabledPresents() {
        Realm realm = Realm.getDefaultInstance();
        List<Present> result = realm.copyFromRealm(realm.where(Present.class).equalTo("presentEnable", 1).findAll());
        realm.close();
        return result;
    }

    public static Present getPresentById(int id) {
        Realm realm = Realm.getDefaultInstance();
        Present result = realm.copyFromRealm(realm.where(Present.class).equalTo("presentId", id).equalTo("presentEnable", 1).findFirst());
        realm.close();
        return result;
    }

    public static List<DutyChart> getDutyChartForMaster(int masterId) {
        Realm realm = Realm.getDefaultInstance();
        List<DutyChart> result = realm.copyFromRealm(realm.where(DutyChart.class).equalTo("masterId", masterId).findAllSorted("order"));
        realm.close();
        return result;
    }

    public static List<Region> getRegionList(){
        Realm realm = Realm.getDefaultInstance();
        List<Region> result = realm.copyFromRealm(realm.where(Region.class).findAll());
        realm.close();
        return result;
    }

    public static List<Region> getRegionListForIds(List<Integer> regionIdList){
        Realm realm = Realm.getDefaultInstance();
        List<Region> tempRegions = realm.copyFromRealm(realm.where(Region.class).findAll());
        List<Region> result = new ArrayList<>();
        for (Region region : tempRegions){
            if (regionIdList.contains(region.geteRegionId())) result.add(region);
        }
        realm.close();
        return result;
    }

}
