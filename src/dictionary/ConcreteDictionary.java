package dictionary;

import myADT.*;

import java.io.*;
import java.util.Iterator;

public class ConcreteDictionary implements Dictionary{
    public static final String srcPath="./data/199801_seg&pos.txt";
    public static final String dstPath="./data/dicResult.txt";
    public static final String dateReg="([0-9]+-)+[0-9]+";
    public static final String symbolReg="��|��|��|��|��|��|��|��|(����)|��|��|��|��|(����)|(������)|��|��|��|��";
    public static final String englishReg="(([��-��][��-��]*)|��)+[��-��]";
    public static final String numReg="��?[��-��]+((.|��)[��-��]+)?��?%?��?��?��?";
    public static final String chineseNumReg="(���֮)?(ǧ��֮)?(�ٷ�֮)?[��|��|��|һ|��|��|��|��|��|��|��|��|��|ʮ|��|ǧ|��|��]+��?��?" +
            "��?((��|��|.)[��|��|һ|��|��|��|��|��|��|��|��|ʮ|��|ǧ|��|��]+)?(��֮[��|��|��|һ|��|��|��|��|��|��|��|��|ʮ|��|ǧ|��|��]+)?" +
            "(��[��|��|��|һ|��|��|��|��|��|��|��|��|ʮ|��|ǧ|��|��]+��֮[��|��|��|һ|��|��|��|��|��|��|��|��|ʮ|��|ǧ|��|��]+)?";

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
        //������bug������墣������С������Ѿ�����ӵ�catalogue֮�У���ȴ���Ҳ���
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
//                if(word.equals("������"))
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
        if(word.equals("������")||word.equals("���"))
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
