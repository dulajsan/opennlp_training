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
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;

/**
 *
 * @author dusalk
 */
public class NERTrainingExample {
    public void TrainNERModel() throws FileNotFoundException, IOException{
        //reading training data
        InputStreamFactory in=null;
        in=new MarkableFileInputStreamFactory(new File("C:/Users/dusalk/Documents/NetBeansProjects/OpennlpTraining/src/main/resources/training_inputs/AnnotatedSentences.txt"));
        
        ObjectStream<NameSample> sampleStream=null;
        sampleStream= new NameSampleDataStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8));
        
        //setting the parameters for training
        TrainingParameters params=new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 70);
        params.put(TrainingParameters.CUTOFF_PARAM, 1);
        
        //traing the model using token name finder model class
        TokenNameFinderModel nameFinderModel=null;
        nameFinderModel=NameFinderME.train("en", null , sampleStream, params,TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
        
        //saving the model to bin file
        File output= new File("C:/Users/dusalk/Documents/NetBeansProjects/OpennlpTraining/src/main/resources/models/ner-custom-model.bin");
        FileOutputStream outputStream=new FileOutputStream(output);
        nameFinderModel.serialize(outputStream);
        
        //testing the model and print
        TokenNameFinder nameFinder=new NameFinderME(nameFinderModel);
        String testSentence[]={"Alisa","fernandes","is","a","tourist","from","spain"};
        
        System.out.println("Finding types in test sentence");
        
        Span[] names=nameFinder.find(testSentence);
        for(Span name:names){
            String personName="";
            for(int i=name.getStart();i<name.getEnd();i++){
                personName+=testSentence[i]+" ";
            }
            
            System.out.println(name.getType()+" : "+personName+"\t [probability="+name.getProb()+"]");
            
        }

        
    }
    
    
}
