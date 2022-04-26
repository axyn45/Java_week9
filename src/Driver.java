// package week9;

//import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.sun.source.tree.TryTree;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import netscape.javascript.JSObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.JSONObject;
import org.json.JSONArray;

public class Driver {
    static String user_name;
    static String user_password;
    static LinkedList<Stu_Cour> list= new LinkedList<Stu_Cour>();
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        GetUser();//获得用户名和用户密码

        //test:测试功能是否正确
        // Update_password("123456");

        //  Read_From_Excel();
        //  Read_From_Txt();
        //  Read_From_Xml();
        //  Read_From_Json();
        //  Select_grade();
        //  Write_to_EXCEL();
        //  Write_to_Txt();
        //  Write_to_Xml();
        //  Write_To_Json();

          // Print_List();


        if(LogIn())
        {
            Mainmenu();
            int choice= sc.nextInt();
            while(choice!=12)
            {
                switch(choice)
                {
                    case 1:Read_From_Excel();break;
                    case 2:Read_From_Txt();break;
                    case 3:Read_From_Xml();break;
                    case 4:Read_From_Json();break;
                    case 5:Add();break;
                    case 6:Select_grade();break;
                    case 7:Write_to_EXCEL();break;
                    case 8:Write_to_Txt();break;
                    case 9:Write_to_Xml();break;
                    case 10:Write_To_Json();break;
                    case 11:Update_password();break;
                    case 13:Print_List();break;
                    default:break;
                }
                Mainmenu();
                choice= sc.nextInt();
            }

            //TODO:进一步操作
        }
        else
        {
            System.out.println("请重新进入！");
        }
    }

    public static void Mainmenu()
    {

        System.out.println("****************************************************************************************************");
        System.out.print(String.format("%-25s","1.从excel中加载数据"));
        System.out.print(String.format("%-25s","2.从文本文件中加载数据"));
        System.out.print(String.format("%-25s","3.从xml文件加载数据"));
        System.out.println(String.format("%-25s","4.从json文件中加载数据"));
        System.out.print(String.format("%-25s","5.键盘输入数据"));
        System.out.print(String.format("%-25s","6.成绩查询"));
        System.out.print(String.format("%-25s","7.输出到 excel 文件"));
        System.out.println(String.format("%-25s","8.输出到纯文本文件"));
        System.out.print(String.format("%-25s","9.输出到 xml 文件"));
        System.out.print(String.format("%-25s","10.输出到 json 文件"));
        System.out.print(String.format("%-25s","11.修改密码"));
        System.out.println(String.format("%-25s","12.退出"));
        System.out.println("****************************************************************************************************");
        System.out.println("请输入你的选择");

    }

    public static void Read_From_Json()
        {
        try {
            char cbuf[] = new char[10000];
            InputStreamReader input = new InputStreamReader(new FileInputStream(new File("data.json")), "UTF-8");
            int len = input.read(cbuf);
            String text = new String(cbuf, 0, len);
            //1.构造一个json对象
            JSONObject obj = new JSONObject(text.substring(text.indexOf("{")));   //过滤读出的utf-8前三个标签字节,从{开始读取w
          /*
            //2.通过getXXX(String key)方法获取对应的值
            System.out.println("FLAG:" + obj.getString("FLAG"));
            System.out.println("Id:" + obj.getString("Id"));
            System.out.println("Name:" + obj.getString("Name"));
            System.out.println("Gender:" + obj.getString("Gender"));
            System.out.println("Course_name:" + obj.getString("Course_name"));
            System.out.println("Grade:" + obj.getString("Grade"));
           */
            //获取数组
            JSONArray arr = obj.getJSONArray("ARRAYS");
            System.out.println("数组长度:" + arr.length());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject subObj = arr.getJSONObject(i);
              //  System.out.println("数组Name:" + subObj.getString("Name") + " String:" + subObj.getString("String"));
                Stu_Cour temp=new Stu_Cour();
                temp.setId(subObj.getString("Id"));
                temp.setName(subObj.getString("Name"));
                temp.setGender(subObj.getString("Gender"));
                temp.setCourse_name(subObj.getString("Course_name"));
                temp.setGrade(Double.parseDouble(subObj.getString("Grade")));
                list.add(temp);

            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void Write_To_Json()
    {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("data.json"), "utf-8");
            JSONObject obj = new JSONObject();
            obj.put("FLAG", "Stu");
            for (int i = 0; i < list.size(); i++) {
                JSONObject sub_obj = new JSONObject();
                sub_obj.put("Id", list.get(i).getId());
                sub_obj.put("Name", list.get(i).getName());
                sub_obj.put("Gender", list.get(i).getGender());
                sub_obj.put("Course_name", list.get(i).getCourse_name());
                sub_obj.put("Grade", String.valueOf(list.get(i).getGrade()));
                obj.accumulate("ARRAYS", sub_obj);

            }
            osw.write(obj.toString());
            osw.flush();
            osw.close();
        }
        catch(Exception e)
        {
            //TODO:..
        }
    }

    public static void Read_From_Xml()
    {
        SAXReader reader= new SAXReader();
        try {
            Document document = reader.read(new File("emps.xml"));
            Element root=document.getRootElement();//获取根节点
            List<Element> list_element=root.elements();//获取root下的所有子节点
            for(Element e:list_element)
            {
                Stu_Cour temp = new Stu_Cour();
                //List<Element> list_element1=e.elements();
                temp.setId(e.attributeValue("Stu_Id"));
                temp.setName(e.element("Stu_Name").getText());
                temp.setGender(e.element("Stu_Gender").getText());
                temp.setCourse_name(e.element("Stu_CourName").getText());
                temp.setGrade(Double.parseDouble(e.element("Stu_Grade").getText()));
                list.add(temp);
            }
        }
        catch(Exception e)
        {
            //TODO:..
        }
    }


    public static void Write_to_Xml()
    {
        //1、通过文档帮助器，创建一个文档对象
        Document doc = DocumentHelper.createDocument();
        //2、添加根元素
        Element root = doc.addElement("Stu");
        for(Stu_Cour temp :list)
        {   //3、添加子元素，属性，文本
            Element InfoEle=root.addElement("Stu_Info");
            InfoEle.addAttribute("Stu_Id", temp.getId()+"");
            Element NameEle = InfoEle.addElement("Stu_Name");
            NameEle.setText(temp.getName());
            Element GenderEle = InfoEle.addElement("Stu_Gender");
            GenderEle.addText(temp.getGender());
            Element CourNameEle = InfoEle.addElement("Stu_CourName");
            CourNameEle.addText(temp.getCourse_name());
            Element GradeEle = InfoEle.addElement("Stu_Grade");
            GradeEle.addText(String.valueOf(temp.getGrade()));
        }
        try {        //4、创建一个文件输出流
            FileOutputStream fos = new FileOutputStream("emps.xml");
            //装饰者模式  写XML文档的输出流
            XMLWriter writer = new XMLWriter(fos);
            writer.write(doc);
            //writer.flush();
            writer.close();
        }
        catch(Exception e)
        {
            //TODO:...
            e.printStackTrace();

        }

    }


    public static void Update_password()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入新密码");
        String new_password=sc.next();
        user_password=new_password;
        FileWriter f = null;//创建文件写入对象
        BufferedWriter f1 = null;//创建字符流写入对象
        try
        {/*
            File file = new File("src\\users.txt");
            f = new FileWriter(file);//
            f1 = new BufferedWriter(f);
            f1.write(user_name);
            f1.write(user_password);
        */
            PrintStream stream=new PrintStream("src\\\\users.txt");
            stream.print(user_name);//写入的字符串
            stream.print("\n");
            stream.print(user_password);//写入的字符串

        }
        catch(Exception e)
        {
            e.printStackTrace();
            //TODO:...
        }
        finally
        {
            try {
                f1.close();
                f.close();
            }
            catch (Exception e)
            {
                //TODO:...
            }
        }

    }

    public static void Write_to_Txt()
    {
        FileWriter f = null;//创建文件写入对象
        BufferedWriter f1 = null;//创建字符流写入对象
        String str=null;
        if(list==null)
        {
            System.out.println("无效");
            return;
        }
        try
        {
            File file = new File("data.txt");
            f = new FileWriter(file);//
            f1 = new BufferedWriter(f);
            for(int i =0;i<list.size();i++)
            {
                str=list.get(i).getId()+" "+list.get(i).getName()+" "+list.get(i).getGender()+" "+list.get(i).getCourse_name()+" "+String.valueOf(list.get(i).getGrade());
                f1.write(str);
                f1.newLine();//换行操作
            }
        }
        catch(Exception e)
        {
            //TODO:...
        }
        finally
        {
            try {
                f1.close();
                f.close();
            }
            catch (Exception e)
            {
                //TODO:...
            }
        }

    }

    public static void Write_to_EXCEL()
    {
        if(list==null)
        {
            System.out.println("无效");
            return;
        }
        try
        {
            File file = new File("jxl2.xls");
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet=workbook.createSheet("sheet0",0);
            for (int col = 0; col < list.size(); col++)
            {
                // 向工作表中添加数据
                sheet.addCell(new Label(0, col, list.get(col).getId()));
                sheet.addCell(new Label(1, col, list.get(col).getName()));
                sheet.addCell(new Label(2, col, list.get(col).getGender()));
                sheet.addCell(new Label(3, col, list.get(col).getCourse_name()));
                sheet.addCell(new Label(4, col, String.valueOf(list.get(col).getGrade())));

            }
            workbook.write();
            workbook.close();
        }
        catch(Exception e)
        {
            //TODO: ...
        }
    }

    public static void Print_List()
    {
        for(int i = 0;i< list.size();i++)
        {
            System.out.println(list.get(i));
        }
    }

    public static void Select_grade()
    {
        boolean IfSelect=false;
        Scanner sc= new Scanner(System.in);
        System.out.println("请输入要查询的学生的学号");
        String no= sc.next();
        for(int i = 0;i< list.size();i++)
        {
            if(list.get(i).getId().equals(no))
            {
                IfSelect=true;
                System.out.println(list.get(i));
            }
        }
    }


    public static void Add()//一次只能添加一个
    {
        Stu_Cour temp =new Stu_Cour();
        boolean LOOP=false;
        do {
            Scanner sc= new Scanner(System.in);
            System.out.println("请输入ID");
            temp.setId(sc.next());
            System.out.println("请输入姓名");
            temp.setName(sc.next());
            System.out.println("请输入性别");
            temp.setGender(sc.next());
            System.out.println("请输入课程名称");
            temp.setCourse_name(sc.next());
            System.out.println("请输入成绩");
            temp.setGrade(sc.nextDouble());
            for(int i=0;i< list.size();i++)
            {
                if(list.get(i).getId()== temp.getId()&&list.get(i).getCourse_name()==temp.getCourse_name())
                {
                    System.out.println("该学生不能重复录入成绩信息，请重新输入");
                    LOOP=true;
                }
            }
        }while(LOOP);

        list.add(temp);
        System.out.println("增加完成，现在有"+list.size()+"个学生");
    }



    public static void Read_From_Txt()
    {
        File file=new File("data.txt");
        FileReader f =null;
        BufferedReader f1=null;
        String str = null;
        String[] tempArray=null;
        Stu_Cour temp ;

        try
        {
            f=new FileReader(file);
            f1=new BufferedReader(f);
            while((str= f1.readLine())!=null)
            {
                temp=new Stu_Cour();
                tempArray=str.split(" ");
                temp.setId(tempArray[0]);//转换成Object类型并且去除空格
                temp.setName(tempArray[1]);//转换成Object类型并且去除空格
                temp.setGender(tempArray[2]);//转换成Object类型并且去除空格
                temp.setCourse_name(tempArray[3]);//转换成Object类型并且去除空格
                temp.setGrade(Double.parseDouble(tempArray[4]));//转换成Object类型并且去除空格
                list.add(temp);
            }
        }
        catch(Exception e)
        {
            System.out.println("打开文件错误");
        }
        finally
        {
            try
            {
                f1.close();
                f.close();
            }
            catch(Exception e)
            {
                System.out.println("关闭文件错误");
            }
        }
    }


    public static void Read_From_Excel()
    {
        File file=new File("jxl1.xls");
        try {
            Workbook workbook = Workbook.getWorkbook(file);
            Sheet sheet=workbook.getSheet(0);//获取第一页
            int rows= sheet.getRows();//获得行数
            Stu_Cour temp ;
            for(int j =0;j<rows;j++)//有时候j是1 表示越过第一行 因为第一行常常是属性名
            {
                temp=new Stu_Cour();
                Cell []cells=sheet.getRow(j);
               // for(int i=0;i< cells.length;i++)
               // {
                    temp.setId(cells[0].getContents().trim());//转换成Object类型并且去除空格
                    temp.setName(cells[1].getContents().trim());//转换成Object类型并且去除空格
                    temp.setGender(cells[2].getContents().trim());//转换成Object类型并且去除空格
                    temp.setCourse_name(cells[3].getContents().trim());//转换成Object类型并且去除空格
                    temp.setGrade(Double.parseDouble(cells[4].getContents().trim()));//转换成Object类型并且去除空格
               // }
                list.add(temp);

            }
            System.out.println("成功导入"+rows+"个学生");
        }
        catch(Exception e)
        {
            System.out.println("无文件！");
        }

    }

    public static boolean LogIn()
    {
        String input;
        int count=3;
        System.out.println("请输入你的用户名：");
        Scanner sc =new Scanner(System.in);
        input=sc.next();
        while(!user_name.equals(input))
        {
            System.out.println("错误的用户名，请重新输入！");
            input= sc.next();
        }
        System.out.println("请输入密码：");
        input= sc.next();
        while(!user_password.equals(input))
        {
            System.out.println("错误的密码！！");
            count--;
            System.out.println("你还有"+count+"次机会");
            return false;
        }
        System.out.println("登录成功");
        return true;
    }

    public static void GetUser()
    {
        File file = new File("src\\users.txt");//一个文件流
        FileReader f=null;//读具体某个文件对象里面的内容
        BufferedReader f1=null;//字符流对象
        try{
            f=new FileReader(file);//读取该指定路径下的文件
            f1=new BufferedReader(f);//读进缓存
            user_name= f1.readLine();
            user_password= f1.readLine();
        }
        catch (Exception e)
        {
            System.out.println("打开文件出错！");
        }
        finally {
            try{
                f1.close();
                f.close();
            }
            catch (Exception e)
            {
                System.out.println("关闭文件出错！");
            }
        }

    }
}
