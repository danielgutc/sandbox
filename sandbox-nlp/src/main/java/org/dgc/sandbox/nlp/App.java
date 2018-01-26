package org.dgc.sandbox.nlp;

import org.dgc.sandbox.nlp.opennlp.OpenNLP;

import java.io.IOException;

public class App
{
    public static void main(String[] args) throws IOException
    {
        OpenNLP openNLP = new OpenNLP();
        openNLP.classify();
    }
}
