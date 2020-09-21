package Demo;

import org.ansj.domain.Result;

import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PaperChecking {

    private File originaltext;
    private File comparedtext;

    //构造方法
    public PaperChecking(File originaltext, File comparedtext) {
        this.originaltext = originaltext;
        this.comparedtext = comparedtext;

    }


    //将txt文件的文字转化为字符串
    //@return String
    public String fileToString(File file) throws IOException {
        FileReader fr=new FileReader(file);
        char[] strArray=new char[1024*1024];
        fr.read(strArray);
        String str=new String(strArray);
        return str;
    }


    //将字符串中的标点符号去掉
    //@return String
    public String CleanPunctuation(String str) {
        return str.replaceAll("\\s*", "").replaceAll("[\\pP\\p{Punct}]", "");
    }

    //将字符串进行分词
    //@return Result(类似list集合)
    public  Result WordSegmentation(String str){
        Result result = (Result) ToAnalysis.parse(str);//分词结果的一个封装，主要是一个List<Term>的terms
        return result;
    }


    //求两个集合的去重并集
    //@return Result
    public Result getUnion(Result r1,Result r2) {
        Result r11=r1;
        Result r22=r2;
        r11.getTerms().removeAll(r22.getTerms());
        r11.getTerms().addAll(r22.getTerms());
        return r11;
    }


    //将字符串分词后的词集合根据词频生成各自的词频向量
    public int[] getWordfrequencyVector(Result r,Result union) {
        int [] wordfrequencyVector=new int[1024*1024];




        return wordfrequencyVector;
    }

    //得到两篇文章的特征向量后利用余弦公式计算相似度
    //x1y1+x2y2.../sqrt(x1*x1+y1*y1)+sqrt(x2*x2+y2*y2)...
    //return double
    public double getSimilarity (int[] vector1,int[] vector2) {
        //double result=0.0;
        double up=0.0;
        double down=1.0;
        for (int i = 0; i <vector1.length ; i++) {
            up+=vector1[i]*vector2[i];
            down*=Math.sqrt(vector1[i]*vector1[i]+vector2[i]*vector2[i]);
        }
        double result=up/down;
        return result;
    }

    public static void main(String[] args) {
        System.out.println("请输入原文路径：");
        String str1=new Scanner(System.in).next();
        File f1=new File(str1);

        System.out.println("请输入要比较文章的路径：");
        String str2=new Scanner(System.in).next();
        File f2=new File(str2);

        PaperChecking pc=new PaperChecking(f1,f2);

    }


}
