package Demo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public  class PaperCheckingTest  {

    /*public PaperCheckingTest(File originaltext, File comparedtext, File outPutFile) {
        super(originaltext, comparedtext, outPutFile);
    }*/

    @BeforeClass
    public static void beforeTest(){
        System.out.println("测试即将开始");
    }

    @AfterClass
    public static void afterTest(){
        System.out.println("测试结束");
    }

    @Test
    void addTestProcess() throws IOException
    {
        PaperChecking.process("C:\\Users\\ASUS\\Desktop\\test\\orig.txt","C:\\Users\\ASUS\\Desktop\\test\\orig_0.8_add.txt","C:\\Users\\ASUS\\Desktop\\test\\result.txt");
    }

    @Test
    void delTestProcess() throws IOException
    {
        PaperChecking.process("C:\\Users\\ASUS\\Desktop\\test\\orig.txt","C:\\Users\\ASUS\\Desktop\\test\\orig_0.8_del.txt","C:\\Users\\ASUS\\Desktop\\test\\result.txt");
    }

    @Test
    void dis_1TestProcess() throws IOException
    {
        PaperChecking.process("C:\\Users\\ASUS\\Desktop\\test\\orig.txt","C:\\Users\\ASUS\\Desktop\\test\\orig_0.8_dis_1.txt","C:\\Users\\ASUS\\Desktop\\test\\result.txt");
    }

    @Test
    void dis_10TestProcess() throws IOException
    {
        PaperChecking.process("C:\\Users\\ASUS\\Desktop\\test\\orig.txt","C:\\Users\\ASUS\\Desktop\\test\\orig_0.8_dis_10.txt","C:\\Users\\ASUS\\Desktop\\test\\result.txt");
    }

    @Test
    void dis_15TestProcess() throws IOException
    {
        PaperChecking.process("C:\\Users\\ASUS\\Desktop\\test\\orig.txt","C:\\Users\\ASUS\\Desktop\\test\\orig_0.8_dis_15.txt","C:\\Users\\ASUS\\Desktop\\test\\result.txt");
    }

    @Test
    void oriTestProcess() throws IOException
    {
        PaperChecking.process("C:\\Users\\ASUS\\Desktop\\test\\orig.txt","C:\\Users\\ASUS\\Desktop\\test\\orig.txt","C:\\Users\\ASUS\\Desktop\\test\\result.txt");
    }

    //随便找的不同的文档
    @Test
    void difTestProcess() throws IOException
    {
        PaperChecking.process("C:\\Users\\ASUS\\Desktop\\test\\orig.txt","C:\\Users\\ASUS\\Desktop\\test\\dif.txt","C:\\Users\\ASUS\\Desktop\\test\\result.txt");
    }
}