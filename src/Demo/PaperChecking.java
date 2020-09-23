package Demo;

import org.ansj.domain.Result;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    //@return List<String>
    public  List<String> WordSegmentation(String str){
        Result result = ToAnalysis.parse(str);//分词结果的一个封装，主要是一个List<Term>的terms
        List<Term> terms=result.getTerms();
        List<String> strArray=terms.stream().map(param -> param.getName()).collect(Collectors.toList());
        //System.out.println(strArray);
        return strArray;
    }


    //求两个集合的去重并集
    //@return Result
    public List<String> getUnion(List<String> l1,List<String> l2) {
        List<String> l11=new ArrayList<>();
        l11.addAll(l1);
        l11.removeAll(l2);
        l11.addAll(l2);
        return l11;
    }


    //将字符串分词后的词集合根据词频生成各自的词频向量
    public int[] getWordfrequencyVector(List<String> list,List<String> unionList) {
        int[] vector=new int[unionList.size()];
        for (int i = 0; i <unionList.size() ; i++) {
            for (int j = 0; j < list.size(); j++) {
                if(unionList.get(i)==list.get(j))
                    vector[i]+=1;
            }
        }
            return vector;
    }

    //得到两篇文章的词频向量后利用余弦公式计算相似度
    //
    //return double
    public double cosCalculator (int[] vector1,int[] vector2) {

        double up=0.00;
        double down1=0.00;
        double down2=0.00;
        for (int i = 0; i <vector1.length ; i++) {
                up += vector1[i] * vector2[i];
                down1+=vector1[i]*vector1[i];
                down2+=vector2[i]*vector2[i];
        }
        double down=Math.sqrt(down1)*Math.sqrt(down2);
        double result=up/down;
//        System.out.println(up);
//        System.out.println(down);
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

        List<String> strArray1=WordSegmentation(str1);//分词
        List<String> strArray2=WordSegmentation(str2);

        int[] wordfrequencyVector1=getWordfrequencyVector(strArray1,getUnion(strArray1,strArray2));//计算词频向量
        //System.out.println(Arrays.toString(wordfrequencyVector1));
        int[] wordfrequencyVector2=getWordfrequencyVector(strArray2,getUnion(strArray1,strArray2));
        //System.out.println(Arrays.toString(wordfrequencyVector2));

        double result=cosCalculator(wordfrequencyVector1,wordfrequencyVector2);//计算余弦相似度
        outPutResult(result,outPutFile);//控制台输出结果并输出到指定文件中去
    }


    public static void main(String[] args) throws IOException {
        System.out.println("请输入原文路径：");
        String str1=new Scanner(System.in).next();
        //String str1="C:\\\\Users\\\\ASUS\\\\Desktop\\\\test\\\\orig.txt";
        File f1=new File(str1);

        System.out.println("请输入要比较文章的路径：");
        String str2=new Scanner(System.in).next();
        //String str2="C:\\\\Users\\\\ASUS\\\\Desktop\\\\test\\\\orig_0.8_dis_15.txt";
        File f2=new File(str2);

        System.out.println("请输入答案文件（结果）路径：");
        String str3=new Scanner(System.in).next();
        //String str3="C:\\\\Users\\\\ASUS\\\\Desktop\\\\test\\\\result.txt";
        File f3=new File(str3);

        PaperChecking pc=new PaperChecking(f1,f2,f3);
        pc.getSimilarity();

    }
}
