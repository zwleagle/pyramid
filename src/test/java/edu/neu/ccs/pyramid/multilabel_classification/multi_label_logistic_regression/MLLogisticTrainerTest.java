package edu.neu.ccs.pyramid.multilabel_classification.multi_label_logistic_regression;

import edu.neu.ccs.pyramid.configuration.Config;
import edu.neu.ccs.pyramid.dataset.*;
import edu.neu.ccs.pyramid.eval.Accuracy;
import edu.neu.ccs.pyramid.eval.Overlap;
import edu.neu.ccs.pyramid.optimization.LBFGS;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MLLogisticTrainerTest {
    private static final Config config = new Config("configs/local.config");
    private static final String DATASETS = config.getString("input.datasets");
    private static final String TMP = config.getString("output.tmp");

    public static void main(String[] args) throws Exception{
        test3();
    }


    private static void test1() throws Exception{
        ClfDataSet singleLabeldataSet = TRECFormat.loadClfDataSet(new File(DATASETS,"/spam/trec_data/train.trec"),
                DataSetType.CLF_DENSE, true);
        int numDataPoints = singleLabeldataSet.getNumDataPoints();
        int numFeatures = singleLabeldataSet.getNumFeatures();
        MultiLabelClfDataSet dataSet = MLClfDataSetBuilder.getBuilder()
                .numDataPoints(numDataPoints).numFeatures(numFeatures)
                .numClasses(2).build();
        int[] labels = singleLabeldataSet.getLabels();
        for (int i=0;i<numDataPoints;i++){
            dataSet.addLabel(i,labels[i]);
            for (int j=0;j<numFeatures;j++){
                double value = singleLabeldataSet.getRow(i).get(j);
                dataSet.setFeatureValue(i,j,value);
            }
        }



        List<MultiLabel> assignments = new ArrayList<>();
        assignments.add(new MultiLabel().addLabel(0));
        assignments.add(new MultiLabel().addLabel(1));

        MLLogisticRegression mlLogisticRegression = new MLLogisticRegression(dataSet.getNumClasses(),dataSet.getNumFeatures(),
                assignments);
        MLLogisticLoss function = new MLLogisticLoss(mlLogisticRegression,dataSet,10000);
        LBFGS lbfgs = new LBFGS(function);
        lbfgs.setEpsilon(0.01);
        lbfgs.setHistory(5);
        for (int i=0;i<100;i++){
            System.out.println(function.getValue());
//            System.out.println(Accuracy.accuracy(mlLogisticRegression,dataSet));
            lbfgs.iterate();

        }

        System.out.println(Accuracy.accuracy(mlLogisticRegression,dataSet));
        System.out.println(Overlap.overlap(mlLogisticRegression,dataSet));
    }

    private static void test2() throws Exception{
        ClfDataSet singleLabeldataSet = TRECFormat.loadClfDataSet(new File(DATASETS,"/spam/trec_data/train.trec"),
                DataSetType.CLF_DENSE, true);
        int numDataPoints = singleLabeldataSet.getNumDataPoints();
        int numFeatures = singleLabeldataSet.getNumFeatures();
        MultiLabelClfDataSet dataSet = MLClfDataSetBuilder.getBuilder()
                .numDataPoints(numDataPoints).numFeatures(numFeatures)
                .numClasses(2).build();
        int[] labels = singleLabeldataSet.getLabels();
        for (int i=0;i<numDataPoints;i++){
            dataSet.addLabel(i,labels[i]);
            for (int j=0;j<numFeatures;j++){
                double value = singleLabeldataSet.getRow(i).get(j);
                dataSet.setFeatureValue(i,j,value);
            }
        }

        List<MultiLabel> assignments = new ArrayList<>();
        assignments.add(new MultiLabel().addLabel(0));
        assignments.add(new MultiLabel().addLabel(1));


        MLLogisticTrainer trainer = MLLogisticTrainer.getBuilder().setEpsilon(0.01).setGaussianPriorVariance(10000)
                .setHistory(5).build();
        MLLogisticRegression mlLogisticRegression =trainer.train(dataSet,assignments);


        System.out.println(Accuracy.accuracy(mlLogisticRegression,dataSet));
        System.out.println(Overlap.overlap(mlLogisticRegression,dataSet));
    }

    /**
     *      * add a fake label in spam data set, if x=spam and x_0<0.1, also label it as 2
     * @throws Exception
     */
    private static void test3() throws Exception{
        ClfDataSet singleLabeldataSet = TRECFormat.loadClfDataSet(new File(DATASETS, "/spam/trec_data/train.trec"),
                DataSetType.CLF_DENSE, true);
        int numDataPoints = singleLabeldataSet.getNumDataPoints();
        int numFeatures = singleLabeldataSet.getNumFeatures();
        MultiLabelClfDataSet dataSet = MLClfDataSetBuilder.getBuilder()
                .numDataPoints(numDataPoints).numFeatures(numFeatures)
                .numClasses(3).build();
        int[] labels = singleLabeldataSet.getLabels();
        for (int i=0;i<numDataPoints;i++){
            dataSet.addLabel(i,labels[i]);
            if (labels[i]==1 && singleLabeldataSet.getRow(i).get(0)<0.1){
                dataSet.addLabel(i,2);
            }
            for (int j=0;j<numFeatures;j++){
                double value = singleLabeldataSet.getRow(i).get(j);
                dataSet.setFeatureValue(i,j,value);
            }
        }



        List<MultiLabel> assignments = new ArrayList<>();
        assignments.add(new MultiLabel().addLabel(0));
        assignments.add(new MultiLabel().addLabel(1));
        assignments.add(new MultiLabel().addLabel(1).addLabel(2));



        MLLogisticRegression mlLogisticRegression = new MLLogisticRegression(dataSet.getNumClasses(),dataSet.getNumFeatures(),
                assignments);
        MLLogisticLoss function = new MLLogisticLoss(mlLogisticRegression,dataSet,10000);
        LBFGS lbfgs = new LBFGS(function);
        lbfgs.setEpsilon(0.01);
        lbfgs.setHistory(5);
        for (int i=0;i<1000;i++){
//            System.out.println(function.getValue());
            System.out.println(Accuracy.accuracy(mlLogisticRegression,dataSet));
            lbfgs.iterate();

        }

        System.out.println(Accuracy.accuracy(mlLogisticRegression,dataSet));
        System.out.println(Overlap.overlap(mlLogisticRegression,dataSet));
    }

    /**
     * 0 is the same as 2
     * @throws Exception
     */
    private static void test4() throws Exception{
        ClfDataSet singleLabeldataSet = TRECFormat.loadClfDataSet(new File(DATASETS, "/spam/trec_data/train.trec"),
                DataSetType.CLF_DENSE, true);
        int numDataPoints = singleLabeldataSet.getNumDataPoints();
        int numFeatures = singleLabeldataSet.getNumFeatures();
        MultiLabelClfDataSet dataSet = MLClfDataSetBuilder.getBuilder()
                .numDataPoints(numDataPoints).numFeatures(numFeatures)
                .numClasses(3).build();
        int[] labels = singleLabeldataSet.getLabels();
        for (int i=0;i<numDataPoints;i++){
            dataSet.addLabel(i,labels[i]);
            if (labels[i]==0 ){
                dataSet.addLabel(i,2);
            }
            for (int j=0;j<numFeatures;j++){
                double value = singleLabeldataSet.getRow(i).get(j);
                dataSet.setFeatureValue(i,j,value);
            }
        }

//        System.out.println(Arrays.toString(dataSet.getMultiLabels()));

        List<MultiLabel> assignments = new ArrayList<>();
//        assignments.add(new MultiLabel().addLabel(0));
        assignments.add(new MultiLabel().addLabel(1));
        assignments.add(new MultiLabel().addLabel(0).addLabel(2));


        MLLogisticTrainer trainer = MLLogisticTrainer.getBuilder().setEpsilon(0.01).setGaussianPriorVariance(10000)
                .setHistory(5).build();
        MLLogisticRegression mlLogisticRegression =trainer.train(dataSet,assignments);

        for (int i=0;i<dataSet.getNumDataPoints();i++){
            MultiLabel label = dataSet.getMultiLabels()[i];
            MultiLabel pred = mlLogisticRegression.predict(dataSet.getRow(i));
            if (!label.equals(pred)){
                System.out.println("---");
                System.out.println("label = "+label);
                System.out.println("prediction = "+pred);
                System.out.println(Arrays.toString(mlLogisticRegression.calClassScores(dataSet.getRow(i))));
            }
        }

        System.out.println(Accuracy.accuracy(mlLogisticRegression,dataSet));
        System.out.println(Overlap.overlap(mlLogisticRegression,dataSet));
    }

}