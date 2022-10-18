import Tokenizer.*;
import dictionary.ConcreteDictionary;
import dictionary.Dictionary;
import myADT.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import static dictionary.ConcreteDictionary.*;


public class Main {
    public static void main(String[] args) throws IOException {
        long timaa=System.currentTimeMillis();
        Dictionary dic = new ConcreteDictionary(ConcreteDictionary.srcPath);
        long timeb=System.currentTimeMillis();
        System.out.println("Dictionary constructure in "+(timeb-timaa)*0.001+"s");
        Tokenizer bmm= new BMMTokenizer(dic);
        Tokenizer fmm =new FMMTokenizer(dic);
        bmm.readPassage(Tokenizer.readPath);
        fmm.readPassage(Tokenizer.readPath);
        System.out.println("Structure Success!");
        timaa=System.currentTimeMillis();
        fmm.participle();
        timeb=System.currentTimeMillis();
        System.out.println("fmm runs for "+(timeb-timaa)*0.001+"s");
        timaa=System.currentTimeMillis();
        bmm.participle();
        timeb=System.currentTimeMillis();
        System.out.println("bmm runs for "+(timeb-timaa)*0.001+"s");
        System.out.println("Tokenize Success!");
        fmm.writeResult();
        bmm.writeResult();
        System.out.println("Write Success");
        System.out.println("precise of fmm:\t"+fmm.precise(ConcreteDictionary.srcPath));
        System.out.println("recall of fmm:\t"+fmm.recall(ConcreteDictionary.srcPath));
        System.out.println("precise of bmm:\t"+bmm.precise(ConcreteDictionary.srcPath));
        System.out.println("recall of bmm:\t"+bmm.recall(ConcreteDictionary.srcPath));
    }

}