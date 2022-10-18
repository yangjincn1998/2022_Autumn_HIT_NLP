package Tokenizer;

import dictionary.Dictionary;
import myADT.MyArrayList;
import myADT.MyList;

import java.io.*;
import java.util.*;

public abstract class Tokenizer {
    /**
     * 读入的需要分词的文章的各行
     */
    protected MyList<String> lines ;
    protected MyList<String>[] result;
    protected int rowNum;
    protected Dictionary dic;
    public static final String readPath="./data/199801_sent.txt";

    public int getRowNum()
    {return rowNum;}
    protected void initialize()
    {
        lines=new MyArrayList<>();
        rowNum=0;
    }

    /**
     * 读入要分词的文章
     * @param srcPath 读入文章的路径
     * @throws IOException 该路径没有文件
     */
    public void readPassage(String srcPath) throws IOException {
        File file=new File(srcPath);
        BufferedReader br=new BufferedReader(new FileReader(file));

        String line = null;
        while((line=br.readLine())!=null)
        {
            lines.add(line);
            rowNum++;
        }
        br.close();
    }
    public void writeResult(String outPath) throws IOException {
        File fout=new File(outPath);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fos));

        for(MyList<String> line : result)
        {
            Iterator<String> iterator = line.iterator();
            while(iterator.hasNext())
            {
                String word = iterator.next();
                bw.write(word+"/"+"  ");
            }
            bw.newLine();
        }
        bw.close();
    }
    public  abstract void participle();
    public abstract  void writeResult() throws IOException;

    private MyList<String>[] readAnswer(String answerPath) throws IOException {
        File file=new File(answerPath);
        BufferedReader br=new BufferedReader(new FileReader(file));
        MyList<String>[] ansLines=new MyList[rowNum];
        for(int i=0;i<rowNum;i++)
            ansLines[i]=new MyArrayList<>();

        String line = null;
        int idx=0;
        while((line=br.readLine())!=null)
        {
            String[] words=line.split(" ");
            for(String word : words)
            {
                if(word.equals(""))
                    continue;
                word=word.split("/")[0];
                if(word.substring(0,1).equals("["))
                    word=word.substring(1);
                ansLines[idx].add(word);
            }
            idx++;
        }
        br.close();
        return ansLines;
    }
    private int cover(MyList<String>[] ansLines) throws IOException {
        if(ansLines.length!=result.length)
            throw new NoSuchElementException();
        int cover=0;
        //分别计算每一行的覆盖数
        int offsetAns=0;
        int offsetRes=0;
        Set<Integer> ansOffsets=new HashSet<>();
        //构建每一行的偏移量集合
        for(int i=0;i<rowNum;i++)
        {
            //跳过空行
            if(ansLines[i].size()==1)
                continue;
            Iterator<String> itrAns=ansLines[i].iterator();
            Iterator<String> itrRes=result[i].iterator();
            while(itrAns.hasNext())
            {
                ansOffsets.add(offsetAns);
                offsetAns+=itrAns.next().length();
            }
            ansOffsets.add(offsetAns);
            boolean cur;
            boolean prev=false;
            while(itrRes.hasNext())
            {
                cur=ansOffsets.contains(offsetRes);
                offsetRes+=itrRes.next().length();
                if(cur&&prev)
                    cover++;
                prev=cur;
            }
            cur=ansOffsets.contains(offsetRes);
            if(cur&&prev)
                cover++;
        }
        return cover;
    }

    public float precise(String ansPath) throws IOException {
        int cover = cover(readAnswer(ansPath));
        int sum=0;
        for(MyList<String> line : result)
        {
            //跳过空行
            if(line.size()==1)
                continue;
            sum+= line.size();
        }
        return (float) cover/(float) sum;
    }

    public float recall(String ansPath) throws IOException {
        MyList<String>[] ansLines=readAnswer(ansPath);
        int cover = cover(ansLines);
        int sum=0;
        for(MyList<String> line : ansLines)
        {
            //跳过空行
            if(line.size()==1)
                continue;
            sum+= line.size();
        }
        return (float) cover/(float) sum;
    }
}
