package edu.neu.ccs.pyramid.dataset;

import java.io.FileInputStream;
import java.util.Properties;

public class DataSetUtilTest {

    final static String CONFIG_PATH="property/local.properties";
    final static String INPUT_PATH="inputpath";
    static Properties prop;

    public static void main(String[] args) throws Exception{

        prop = new Properties();
        prop.load(new FileInputStream(CONFIG_PATH));
        test3();
    }

    static void test1(){
        ClfDataSet clfDataSet = new SparseClfDataSet(5,3);
        clfDataSet.setFeatureValue(0,0,3.5);
        clfDataSet.setFeatureValue(1,2,5.5);
        clfDataSet.setFeatureValue(4,1,2.5);
        clfDataSet.setFeatureValue(4,2,5.5);
        clfDataSet.setLabel(0, 1);
        clfDataSet.setLabel(1,2);
        clfDataSet.setLabel(2,3);
        clfDataSet.setLabel(3,5);
        clfDataSet.setLabel(4,2);
        clfDataSet.getFeatureRow(0).putSetting(new DataSetting().setExtId("zero").setExtLabel("spam"));
        clfDataSet.getFeatureRow(1).putSetting(new DataSetting().setExtId("first").setExtLabel("non-spam"));
        clfDataSet.getFeatureRow(2).putSetting(new DataSetting().setExtId("second").setExtLabel("good"));
        clfDataSet.getFeatureRow(3).putSetting(new DataSetting().setExtId("third").setExtLabel("bad"));
        clfDataSet.getFeatureRow(4).putSetting(new DataSetting().setExtId("fourth").setExtLabel("iii"));
        clfDataSet.getFeatureColumn(0).putSetting(new FeatureSetting().setFeatureName("color").setFeatureType(FeatureType.BINARY));
        clfDataSet.getFeatureColumn(1).putSetting(new FeatureSetting().setFeatureName("age").setFeatureType(FeatureType.NUMERICAL));
        clfDataSet.getFeatureColumn(2).putSetting(new FeatureSetting().setFeatureName("income").setFeatureType(FeatureType.NUMERICAL));
        System.out.println(clfDataSet);
        ClfDataSet trimmed = DataSetUtil.trim(clfDataSet,2);
        System.out.println(trimmed);
    }

    static void test2(){
        ClfDataSet clfDataSet = new SparseClfDataSet(5,3);
        clfDataSet.setFeatureValue(0,0,3.5);
        clfDataSet.setFeatureValue(1,2,5.5);
        clfDataSet.setFeatureValue(4,1,2.5);
        clfDataSet.setFeatureValue(4,2,5.5);
        clfDataSet.setLabel(0,1);
        clfDataSet.setLabel(1,0);
        clfDataSet.setLabel(2,1);
        clfDataSet.setLabel(3,2);
        clfDataSet.setLabel(4,2);
        clfDataSet.getFeatureRow(0).putSetting(new DataSetting().setExtId("zero").setExtLabel("spam"));
        clfDataSet.getFeatureRow(1).putSetting(new DataSetting().setExtId("first").setExtLabel("non-spam"));
        clfDataSet.getFeatureRow(2).putSetting(new DataSetting().setExtId("second").setExtLabel("good"));
        clfDataSet.getFeatureRow(3).putSetting(new DataSetting().setExtId("third").setExtLabel("bad"));
        clfDataSet.getFeatureRow(4).putSetting(new DataSetting().setExtId("fourth").setExtLabel("iii"));
        clfDataSet.getFeatureColumn(0).putSetting(new FeatureSetting().setFeatureName("color").setFeatureType(FeatureType.BINARY));
        clfDataSet.getFeatureColumn(1).putSetting(new FeatureSetting().setFeatureName("age").setFeatureType(FeatureType.NUMERICAL));
        clfDataSet.getFeatureColumn(2).putSetting(new FeatureSetting().setFeatureName("income").setFeatureType(FeatureType.NUMERICAL));
        System.out.println(clfDataSet);
        System.out.println("bootstrapped sample");
        System.out.println(DataSetUtil.bootstrap(clfDataSet));

    }

    static void test3() throws Exception{
        ClfDataSet dataSet = TRECFormat.loadClfDataSet(prop.getProperty(INPUT_PATH) + "/spam/trec_data/train.trec",
                DataSetType.CLF_DENSE,true);
        DataSetUtil.dumpDataSettings(dataSet,"~/tmp/data_settings.txt");
        DataSetUtil.dumpFeatureSettings(dataSet,"~/tmp/feature_settings.txt");
    }


    static  void test4() throws Exception{
        ClfDataSet clfDataSet = new SparseClfDataSet(5,3);
        clfDataSet.setFeatureValue(0,0,3.5);
        clfDataSet.setFeatureValue(1,2,5.5);
        clfDataSet.setFeatureValue(4,1,2.5);
        clfDataSet.setFeatureValue(4,2,5.5);
        clfDataSet.setLabel(0, 1);
        clfDataSet.setLabel(1,2);
        clfDataSet.setLabel(2,3);
        clfDataSet.setLabel(3,5);
        clfDataSet.setLabel(4,2);
        clfDataSet.getFeatureRow(0).putSetting(new DataSetting().setExtId("zero").setExtLabel("spam"));
        clfDataSet.getFeatureRow(1).putSetting(new DataSetting().setExtId("first").setExtLabel("non-spam"));
        clfDataSet.getFeatureRow(2).putSetting(new DataSetting().setExtId("second").setExtLabel("good"));
        clfDataSet.getFeatureRow(3).putSetting(new DataSetting().setExtId("third").setExtLabel("bad"));
        clfDataSet.getFeatureRow(4).putSetting(new DataSetting().setExtId("fourth").setExtLabel("iii"));
        clfDataSet.getFeatureColumn(0).putSetting(new FeatureSetting().setFeatureName("color").setFeatureType(FeatureType.BINARY));
        clfDataSet.getFeatureColumn(1).putSetting(new FeatureSetting().setFeatureName("age").setFeatureType(FeatureType.NUMERICAL));
        clfDataSet.getFeatureColumn(2).putSetting(new FeatureSetting().setFeatureName("income").setFeatureType(FeatureType.NUMERICAL));
        DataSetUtil.dumpDataSettings(clfDataSet,prop.getProperty(INPUT_PATH));
        DataSetUtil.dumpFeatureSettings(clfDataSet,prop.getProperty((INPUT_PATH)));
    }


}