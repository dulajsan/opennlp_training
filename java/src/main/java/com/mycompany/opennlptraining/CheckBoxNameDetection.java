/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.opennlptraining;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
public class CheckBoxNameDetection {
    
      public void train(String entity,String traingSamplePath,String trainedModelPath) throws IOException{
        //reading training data
        InputStreamFactory in=null;
        in=new MarkableFileInputStreamFactory(new File(traingSamplePath));
        
        ObjectStream<NameSample> sampleStream=null;
        sampleStream= new NameSampleDataStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8));
        TokenNameFinderModel model;
        OutputStream modelOut=null;
        
        //setting the parameters for training
        TrainingParameters params=new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 70);
        params.put(TrainingParameters.CUTOFF_PARAM, 1);
        
        model= NameFinderME.train("en",entity, sampleStream, params,TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
                //saving the model to bin file
        File output= new File(trainedModelPath);
        FileOutputStream outputStream=new FileOutputStream(output);
        //model.serialize(outputStream);
        if(model!=null){
            model.serialize(outputStream);
        }
        
        //testing the model and print
        TokenNameFinder nameFinder=new NameFinderME(model);
        String testSentence[]={"The","Quick","Registered","Customer","check","box","on","the","Customer/Order/General","tab","and","the","Customers","window","for","this","customer","will","automatically","be","selected.","select","the","Free","of","Charge","check","box"};
      
        
        System.out.println("Finding checkbox names in test sentence");
        
        Span[] checkBoxes=nameFinder.find(testSentence);
        for(Span checkBox:checkBoxes){
            String checkBoxName="";
            for(int i=checkBox.getStart();i<checkBox.getEnd();i++){
                checkBoxName+=testSentence[i]+" ";
            }
            
            System.out.println(checkBox.getType()+" : "+checkBoxName+"\t [probability="+checkBox.getProb()+"]");
            
        }
     
        
    }

    
    
    
}
