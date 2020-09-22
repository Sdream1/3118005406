package Demo;

import com.sun.security.jgss.GSSUtil;
import org.ansj.domain.Result;

import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PaperChecking {

    private File originaltext;
    private File comparedtext;
    private File outPutFile;

    //构造方法
    public PaperChecking(File originaltext, File comparedtext,File outPutFile) {
        this.originaltext = originaltext;
        this.comparedtext = comparedtext;
        this.outPutFile   = outPutFile;
    }


    //将txt文件的文字转化为字符串
    //@return String
    public String fileToString(File file) throws IOException {
        FileReader fr=new FileReader(file);
        char[] strArray=new char[1024*100];
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
        Result result = ToAnalysis.parse(str);//分词结果的一个封装，主要是一个List<Term>的terms
        return result;
    }


    //求两个集合的去重并集
    //@return Result
    public Result getUnion(Result r1,Result r2) {
        Result r11=new Result(new ArrayList<>());
        Result r22=new Result(new ArrayList<>());
        r11.getTerms().addAll(r1.getTerms());
        r22.getTerms().addAll(r2.getTerms());
        r11.getTerms().removeAll(r22.getTerms());
        r11.getTerms().addAll(r22.getTerms());
        return r11;
    }


    //将字符串分词后的词集合根据词频生成各自的词频向量
    public int[] getWordfrequencyVector(Result r,Result union) {
        int [] wordfrequencyVector=new int[union.getTerms().size()];
        for (int i = 0; i <union.getTerms().size() ; i++) {
            for (int j = 0; j <r.getTerms().size() ; j++) {
                if (union.get(i).equals(r.get(i))) {
                    wordfrequencyVector[i]+=1;
                }
            }
        }
        return wordfrequencyVector;
    }

    //得到两篇文章的词频向量后利用余弦公式计算相似度
    //x1y1+x2y2.../sqrt(x1*x1+y1*y1)+sqrt(x2*x2+y2*y2)...
    //return double
    public double cosCalculator (int[] vector1,int[] vector2) {
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

    //将计算结果（文章相似度）打印在控制台并输出到指定文件上
    public void outPutResult(double result,File outPutFile) throws IOException {
        System.out.println("两篇文章的相似度为："+result);
        FileOutputStream fo=new FileOutputStream(outPutFile);
        fo.write("两篇文章的相似度为：".getBytes());
        fo.write(String.valueOf(result).getBytes());
        fo.close();
    }


    //得到相似度
    public void getSimilarity() throws IOException {
        String str1= CleanPunctuation(fileToString(originaltext));//转化为字符串并清除标点符号
        String str2= CleanPunctuation(fileToString(comparedtext));
        //String str3= fileToString(outPutFile);

        Result res1=WordSegmentation(str1);//分词
        Result res2=WordSegmentation(str2);

        int[] wordfrequencyVector1=getWordfrequencyVector(res1,getUnion(res1,res2));//计算词频向量
        int[] wordfrequencyVector2=getWordfrequencyVector(res1,getUnion(res1,res2));

        double result=cosCalculator(wordfrequencyVector1,wordfrequencyVector2);//计算余弦相似度
        outPutResult(result,outPutFile);//控制台输出结果并输出到指定文件中去
    }


    public static void main(String[] args) throws IOException {
        /*System.out.println("请输入原文路径：");
        String str1=new Scanner(System.in).next();
        File f1=new File(str1);

        System.out.println("请输入要比较文章的路径：");
        String str2=new Scanner(System.in).next();
        File f2=new File(str2);

        System.out.println("请输入答案文件（结果）路径：");
        String str3=new Scanner(System.in).next();
        File f3=new File(str3);

        PaperChecking pc=new PaperChecking(f1,f2,f3);
        pc.getSimilarity();*/

        //
        //
        //
        //
        //
        //
        //


        File fa=new File("C:\\Users\\ASUS\\Desktop\\test\\orig.txt");
        File fb=new File("C:\\Users\\ASUS\\Desktop\\test\\orig_0.8_add.txt");
        File fc=new File("C:\\Users\\ASUS\\Desktop\\test\\result.txt");
        File fd=new File("C:\\Users\\ASUS\\Desktop\\test\\路径.txt");

        PaperChecking pc=new PaperChecking(fa,fb,fc);

        //System.out.println(pc.fileToString(fa)); //测试String fileToString(File file);1

        String str=pc.CleanPunctuation(pc.fileToString(fa));
        String str2=pc.CleanPunctuation(pc.fileToString(fb));
        System.out.println(str);//测试String CleanPunctuation(String str)；1


        System.out.println(pc.WordSegmentation(str));//测试Result WordSegmentation(String str)；1
        System.out.println(pc.WordSegmentation(str2));

        System.out.println(pc.getUnion(pc.WordSegmentation(str),pc.WordSegmentation(str2)));
        //测试Result getUnion(Result r1,Result r2)；xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx




    }
}
