/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.opennlptraining;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 *
 * @author dusalk
 */
public class DocClassificationMaxentTrainer {
    
    public void docClassify() throws FileNotFoundException, IOException{
        //read the training data
        InputStreamFactory datain=new MarkableFileInputStreamFactory(new File("C:/Users/dusalk/Documents/NetBeansProjects/OpennlpTraining/src/main/resources/training_inputs/en-movie-category.train"));
        ObjectStream lineStream=new PlainTextByLineStream(datain, "UTF-8");
        ObjectStream sampleStream=new DocumentSampleStream(lineStream);
        
        //define the training parameters
        TrainingParameters params=new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 10+"");
        params.put(TrainingParameters.CUTOFF_PARAM,0+"");
        
        //create a model fom training data
        DoccatModel model=DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
        System.out.println("\nModel is succesfully trained");
        
        //save the model to local
        BufferedOutputStream modelOut=new BufferedOutputStream(new FileOutputStream("C:/Users/dusalk/Documents/NetBeansProjects/OpennlpTraining/src/main/resources/models/en-movie-classifier-maxent.bin"));
        model.serialize(modelOut);
        System.out.println("Trained model is saved");
        
        //test the model file
        DocumentCategorizer doccat=new DocumentCategorizerME(model);
        String[] docWords="AfterWards stuart and charlie notice Kate in the photos stuart took at leopolds ball and realise that her destini must be to go back and be with leopold That night while kate is accepting her promotion at a company banquet he and charlie race to meet her and show her the pictures kate initially rejects their overtures and goes on to give her acceptance speech but is is there that she sees Stuart picture and realises that she truly wants to be with Leopold".replaceAll("[^A-Za-z]"," ").split(" ");
        double[] aprobs=doccat.categorize(docWords);
        
        //print the probabilities of the categories
        System.out.println("\nprobability");
        
        for(int i=0;i<doccat.getNumberOfCategories();i++){
            System.out.println(doccat.getCategory(i)+" : "+aprobs[i]);
        }
        
        System.out.println("----------------");
        
        System.out.println("\n"+doccat.getBestCategory(aprobs)+" is the predicted category");
        
        
        
        
       
        
    }
    
}
