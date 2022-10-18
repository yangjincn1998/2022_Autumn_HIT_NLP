package Tokenizer;

import dictionary.ConcreteDictionary;
import dictionary.Dictionary;
import myADT.MyArrayList;
import myADT.MyList;
import static dictionary.ConcreteDictionary.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FMMTokenizer extends Tokenizer{
    public static final String outPath="./data/FMM_seg.txt";
    public FMMTokenizer(Dictionary dic)
    {
        super.dic=dic;
        initialize();
    }
    @Override
    public void participle() {
        Iterator<String> itr=super.lines.iterator();
        result=new MyList[rowNum];
        for(int i=0;i<rowNum;i++)
            result[i]=new MyArrayList<>();
        int idx=0;
        while(itr.hasNext())
        {
            String line=itr.next();

            String[] sentences;

            sentences=line.split("(?<="+symbolReg+")");

            for(String sentence : sentences)
            {
                int senLen=sentence.length();
                int wordLen=senLen;
                String copy;
                while(!sentence.equals(""))
                {
                    copy=sentence.substring(0,wordLen);
                    if(copy.matches(symbolReg)||copy.matches(chineseNumReg)||copy.matches(numReg)||copy.matches(dateReg)
                            ||copy.matches(englishReg)||dic.hasWord(copy))
                    {
                        result[idx].add(copy);
                        sentence=sentence.substring(wordLen);
                        senLen=senLen-wordLen;
                        wordLen=senLen;
                    }
                    else if (wordLen==1) {
                        result[idx].add(copy);
                        sentence=sentence.substring(1);
                        senLen=senLen-1;
                        wordLen=senLen;
                    } else
                    {
                        wordLen--;
                    }
                }
            }
            idx++;
        }
    }
    @Override
    public void writeResult() throws IOException
    {
        super.writeResult(outPath);
    }
}
