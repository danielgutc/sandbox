package org.dgc.sandbox.nlp.opennlp;

import opennlp.tools.doccat.*;
import opennlp.tools.formats.NameSampleDataStreamFactory;
import opennlp.tools.namefind.*;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

/**
 * NLP using Open NLP
 */
public class OpenNLP
{
    /**
     * Train a model and use it for text classification.
     *
     * @throws IOException
     */
    public void classify() throws IOException
    {
        TrainingParameters tp = new TrainingParameters();
        tp.put(TrainingParameters.ITERATIONS_PARAM, 100);
        tp.put(TrainingParameters.CUTOFF_PARAM, 0);

        DoccatFactory doccatFactory = new DoccatFactory();
        DoccatModel model = DocumentCategorizerME.train("en", new IntentsObjectStream(), tp, doccatFactory);

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

    public void nameEntityRecognition() throws IOException
    {
        NameSampleDataStream nameSampleDataStream = new NameSampleDataStream(new NerObjectStream());
        TrainingParameters tp = new TrainingParameters();
        tp.put(TrainingParameters.ITERATIONS_PARAM, 100);
        tp.put(TrainingParameters.CUTOFF_PARAM, 0);
        TokenNameFinderFactory tokenNameFinderFactory = new TokenNameFinderFactory();

        TokenNameFinderModel model = NameFinderME.train("en", "add_activity", nameSampleDataStream, tp, tokenNameFinderFactory);
        NameFinderME finderME = new NameFinderME(model);
        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;

        try (Scanner scanner = new Scanner(System.in))
        {
            while (true)
            {
                String input = scanner.nextLine();
                if (input.equals("exit"))
                {
                    break;
                }

                Span[] spans = finderME.find(tokenizer.tokenize(input));
                for (Span span: spans)
                {
                    StringBuilder sb = new StringBuilder();
                    for (int i = span.getStart(); i < span.getEnd(); i++ )
                    {
                        if (i < input.split(" ").length)
                        {
                            sb.append(input.split(" ")[i]).append(" ");
                        }
                    }
                    System.out.println(String.format("'%s' - %s", sb.toString(), span.getType()));
                }
            }
        }
    }
}
