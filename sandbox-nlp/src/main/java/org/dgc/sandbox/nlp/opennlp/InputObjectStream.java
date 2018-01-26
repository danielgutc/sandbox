package org.dgc.sandbox.nlp.opennlp;

import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.ObjectStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InputObjectStream implements ObjectStream<DocumentSample>
{
    //private String[] lines = new String[] {"This is a Cat", "This is a Dog", "This is Horse" , "This is Bird", "This is a Panda", "This is a Penguin"};
    private Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
    private String[] lines;
    private int count;

    public InputObjectStream() throws IOException
    {
        lines = Arrays.stream(Files.readAllLines(Paths.get(this.getClass().getResource("/training.txt").getFile().replaceFirst("/", ""))).toArray(new String[0]))
            .filter(l -> l.length() > 0).collect(Collectors.toList()).toArray(new String[0]);
        count = lines.length - 1;
    }


    @Override
    public DocumentSample read() throws IOException
    {
        if (count >= 0)
        {
            DocumentSample ds = new DocumentSample(lines[count].split("\t")[0], tokenizer.tokenize(lines[count].split("\t")[1]));
            count--;

            return ds;
        }
        else
        {
            return null;
        }
    }
}
