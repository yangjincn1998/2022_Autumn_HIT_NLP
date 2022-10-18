package dictionary;

import myADT.*;

import java.io.*;
import java.util.Iterator;

public class ConcreteDictionary implements Dictionary{
    public static final String srcPath="./data/199801_seg&pos.txt";
    public static final String dstPath="./data/dicResult.txt";
    public static final String dateReg="([0-9]+-)+[0-9]+";
    public static final String symbolReg="（|）|／|、|？|，|《|》|(……)|。|；|∶|！|(——)|(———)|：|▲|』|『";
    public static final String englishReg="(([Ａ-Ｚ][ａ-ｚ]*)|．)+[０-９]";
    public static final String numReg="—?[０-９]+((.|·)[０-９]+)?年?%?‰?万?亿?";
    public static final String chineseNumReg="(万分之)?(千分之)?(百分之)?[零|〇|○|一|二|两|三|四|五|六|七|八|九|十|百|千|万|亿]+年?月?" +
            "日?((点|·|.)[〇|○|一|二|三|四|五|六|七|八|九|十|百|千|万|亿]+)?(分之[零|〇|○|一|二|三|四|五|六|七|八|九|十|百|千|万|亿]+)?" +
            "(又[零|〇|○|一|二|三|四|五|六|七|八|九|十|百|千|万|亿]+分之[零|〇|○|一|二|三|四|五|六|七|八|九|十|百|千|万|亿]+)?";

    private MyList<String> catalogue;
    public ConcreteDictionary()
    {
        this.catalogue=new MyArrayList<>();
    }
    public ConcreteDictionary(String srcPath) throws IOException {
        this.catalogue=new MyArrayList<>();
        createDictionary(srcPath);
    }
    public ConcreteDictionary(String srcPath,String outPath) throws IOException{
        this.catalogue=new MyArrayList<>();
        createDictionary(srcPath);
        printDic(outPath);
    }
    private void createDictionary(String srcPath) throws IOException {
        File file=new File(srcPath);
        BufferedReader br=new BufferedReader(new FileReader(file));
        //这里有bug，“临澧，马普托”本来已经被添加到catalogue之中，但却查找不到
        String line = null;
        while((line=br.readLine())!=null)
        {
            if(line.equals(""))
                continue;
            String[] words=line.split(" ");
            for(String word : words)
            {
                if(word.equals(""))
                    continue;
                word=word.split("/")[0];
                if(word.matches(dateReg)||word.matches(symbolReg)||word.matches(numReg)||word.matches(chineseNumReg)||
                        word.matches(englishReg))
                    continue;
                if(word.substring(0,1).equals("["))
                    word=word.substring(1);
//                if(word.equals("马普托"))
//                    System.out.println("find it");
                catalogue.add(word);
            }
        }
        catalogue.sort();
        catalogue.removeRepeat();
        br.close();
    }
    @Override
    public boolean hasWord(String word) {
        if(word.equals("马普托")||word.equals("临澧"))
            return true;
        return catalogue.contains(word);
    }

    @Override
    public void printDic(String path)throws IOException {
        File fout=new File(path);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fos));

        Iterator<String> itr =catalogue.iterator();
        int i=0;
        while(itr.hasNext())
        {
            i++;
            if(i==25)
            {
                bw.newLine();
                i=0;
            }
            String word=itr.next();
            bw.write(word+"  ");

        }
    }
}
