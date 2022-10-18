package Tokenizer;

import dictionary.Dictionary;
import myADT.MyArrayList;
import myADT.MyList;
import myADT.MyStack;

import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import static dictionary.ConcreteDictionary.*;

public class BMMTokenizer extends Tokenizer{
    public static final String outPath="./data/BMM_seg.txt";
    public BMMTokenizer(Dictionary dic)
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
        while(itr.hasNext()) {
            String line = itr.next();

            String[] sentences;

            sentences=line.split("(?<="+symbolReg+")");
            //对切分好的每一句进行划分
            for (String sentence : sentences) {
                //
                Stack<String> stack =new Stack<>();
                int senLen = sentence.length();
                int wordLen = senLen;
                String copy;
                while (!sentence.equals("")) {
                    copy = sentence.substring(senLen-wordLen, senLen);
                    //找到匹配项
                    if (copy.matches(symbolReg) || copy.matches(chineseNumReg) || copy.matches(numReg) || copy.matches(dateReg)
                            || copy.matches(englishReg) || dic.hasWord(copy)) {
                        stack.push(copy);
                        sentence = sentence.substring(0,senLen-wordLen);
                        senLen = senLen - wordLen;
                        wordLen = senLen;
                    } else if (wordLen == 1) {
                        stack.push(copy);
                        sentence = sentence.substring(0,senLen-1);
                        senLen = senLen - 1;
                        wordLen = senLen;
                    } else {
                        wordLen--;
                    }
                }
                while(!stack.empty())
                    result[idx].add(stack.pop());
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

