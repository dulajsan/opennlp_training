/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.opennlptraining;

import java.io.IOException;

/**
 *
 * @author dusalk
 */
public class App {
    public static void main(String args[]) throws IOException{
//       SentenceDetectorTrainingExample SDT=new SentenceDetectorTrainingExample();
//       SDT.TrainSentDetectorModel();

//        NERTrainingExample NTE=new NERTrainingExample();
//        NTE.TrainNERModel();

//        DocClassificationMaxentTrainer doc=new DocClassificationMaxentTrainer();
//        doc.docClassify();


//         DiseaseFinder customEntity=new DiseaseFinder();
//         customEntity.train("disease");

            CheckBoxNameDetection CBND=new CheckBoxNameDetection();
            CBND.train("checkbox","C:/Users/dusalk/Documents/NetBeansProjects/OpennlpTraining/src/main/resources/training_inputs/checkbox.train","C:/Users/dusalk/Documents/NetBeansProjects/OpennlpTraining/src/main/resources/models/checkbox-model.bin");

    }
    
}
