/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.opennlptraining;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 *
 * @author dusalk
 */
public class SentenceDetectorTrainingExample {
    public void TrainSentDetectorModel() throws FileNotFoundException, IOException{
        //dir to save model file
        File destDir=new File("C:/Users/dusalk/Documents/NetBeansProjects/OpennlpTraining/src/main/resources/models");
        //training data
        InputStreamFactory in=new MarkableFileInputStreamFactory(new File("C:/Users/dusalk/Documents/NetBeansProjects/OpennlpTraining/src/main/resources/training_inputs/trainingDataSentences.txt"));
        //parameter used by machine learning algorithm
        TrainingParameters mlParams=new TrainingParameters();
        mlParams.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(15));
        mlParams.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(1));
        
        //train the model
        SentenceModel sentdetectModel=SentenceDetectorME.train("en", new SentenceSampleStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8)), 
                true,null, mlParams);
        
        //save the model to a file
        File outFile=new File(destDir,"en-sent-custom.bin");
        FileOutputStream outFileStream=new FileOutputStream(outFile);
        sentdetectModel.serialize(outFileStream);
        
        //loading the model
        SentenceDetectorME sentDetector=new SentenceDetectorME(sentdetectModel);
        
        //detecting sentences in the test string
        String testString=("This is a testing sentence. we will see the result sentence next.");
        System.out.println("\nTest String: "+testString);
        String sents[]=sentDetector.sentDetect(testString);
        System.out.println("sentence detected by the sentenceDetectorMe class");
        for(int i=0;i<sents.length;i++){
            System.out.println("sentence "+(i+1)+" : "+sents[i]);
            
        }
        
        
        
        
        
        
        
        
    }
    
}
