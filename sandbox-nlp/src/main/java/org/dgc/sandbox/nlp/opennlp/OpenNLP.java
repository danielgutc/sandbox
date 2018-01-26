package org.dgc.sandbox.nlp.opennlp;

import opennlp.tools.doccat.*;
import opennlp.tools.util.TrainingParameters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class OpenNLP
{
    public void classify() throws IOException
    {
/*        NaiveBayesClassifierFactory nbcf = new NaiveBayesClassifierFactory();
        Dataset<String, String> ds = new Dataset<>();
        List<String> iGreeting = Arrays.asList("Hello", "Hi", "Hola");
        //List<String> iGoodbye = Arrays.asList("Good bye", "Have a nice day", "Take care");
        ds.add(iGreeting, "greeting");
        //ds.add(iGoodbye, "goodbye");
        ds.toSummaryStatistics();

        
        NaiveBayesClassifier naivesClassifier = nbcf.trainClassifier(ds);
        naivesClassifier.print();
        BasicDatum<String, String> bd = new BasicDatum<String, String>(Arrays.asList("Hi"));
        ClassicCounter c = naivesClassifier.scoresOf(bd);
        c.toString();*/

        TrainingParameters tp = new TrainingParameters();
        tp.put(TrainingParameters.ITERATIONS_PARAM, 100);
        tp.put(TrainingParameters.CUTOFF_PARAM, 0);

        DoccatFactory doccatFactory = new DoccatFactory();
        DoccatModel model = DocumentCategorizerME.train("en", new InputObjectStream(), tp, doccatFactory);

        DocumentCategorizerME categorizerME = new DocumentCategorizerME(model);

        try (Scanner scanner = new Scanner(System.in))
        {
            while (true)
            {
                String input = scanner.nextLine();
                if (input.equals("exit"))
                {
                    break;
                }

                double[] classDistribution = categorizerME.categorize(new String[]{input});
                String predictedCategory =
                        Arrays.stream(classDistribution).filter(cd -> cd > 0.5D).count() > 0? categorizerME.getBestCategory(classDistribution): "I don't understand";
                System.out.println(String.format("Model prediction for '%s' is: '%s'", input, predictedCategory));
            }
        }

    }
}
