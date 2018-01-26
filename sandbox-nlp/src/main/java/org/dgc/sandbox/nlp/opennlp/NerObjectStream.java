package org.dgc.sandbox.nlp.opennlp;

import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.ObjectStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class NerObjectStream implements ObjectStream<String>
{
    private Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
    private String[] lines;
    private int count;
    private String type;

    public NerObjectStream(String type) throws IOException
    {
        this.type = type;
        lines = Arrays.stream(Files.readAllLines(Paths.get(this.getClass().getResource("/ner-" + this.type + ".train").getFile().replaceFirst("/", ""))).toArray(new String[0]))
                .filter(l -> l.length() > 0)
                .collect(Collectors.toList())
                .toArray(new String[0]);

        count = lines.length - 1;
    }

    @Override
    public String read() throws IOException
    {
        return count >=0 ? lines[count--] : null;
    }
}
