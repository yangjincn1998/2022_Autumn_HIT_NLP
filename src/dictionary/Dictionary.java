package dictionary;

import java.io.IOException;

public interface Dictionary {
    boolean hasWord(String word);
    void printDic(String path)throws IOException;

}
