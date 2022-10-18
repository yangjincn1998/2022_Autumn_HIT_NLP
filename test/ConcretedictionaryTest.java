import org.junit.jupiter.api.Test;
import dictionary.*;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class ConcretedictionaryTest {

    @Test
    public void ConstructorTest() throws IOException {
        Dictionary dic = new ConcreteDictionary(ConcreteDictionary.srcPath);
        dic.printDic(ConcreteDictionary.dstPath);
    }
}
