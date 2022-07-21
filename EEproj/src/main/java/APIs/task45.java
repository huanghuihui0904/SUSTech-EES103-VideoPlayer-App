package APIs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class task45 {
    private static URL propertyURL = task45.class
            .getResource("/loader.cnf");

    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static boolean verbose = false;

    //////////////////
    private static String selectStatement ="select ";
    private static String updateStatement;//后面加
    private static String deleteStatement="delete from ";


    static HashMap<String,Integer> centerLength=new HashMap<>();

    //    private static ArrayList<String> Columns;
////    private static ArrayList<Integer> updateColumnIndex;
//    private static ArrayList<String> Data;
//    private static ArrayList<Integer> ColumnIndex;
//
    private static int cnt;
//    private static ArrayList<Integer> INT;
//    private static ArrayList<Integer> DATE;

//    private static ArrayList<String> Columns;
//    private static ArrayList<Integer> ColumnIndex;
//    private static ArrayList<String> Data;
//    private static ArrayList<Integer> selectColumnIndex;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {

            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }

//        try {
////            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
//            stmt = con.prepareStatement("select type, count(*) form staff");
//        } catch (SQLException e) {
//            System.err.println("Insert statement failed");
//            System.err.println(e.getMessage());
//            closeDB();
//            System.exit(1);
//        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }


    public static Date strToDate(String strDate) {
        if (strDate.equals("")) {
            return null;
        } else {
            String str = strDate;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d = null;
            try {

                d = format.parse(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Date date = new Date(d.getTime());
            return date;
        }

    }

    public static void main(String[] args) throws SQLException {

        System.out.println(propertyURL);
        System.out.println("\n");
        System.out.println();

        centerLength.put("center",2);
        centerLength.put("enterprise",6);
        centerLength.put("model",5);
        centerLength.put("staff",8);
        centerLength.put("contract",6);
        centerLength.put("orders",6);
        centerLength.put("inventory",6);

        String fileName = null;
        boolean verbose = false;
///////////////////////////////////////////////////////////////////////////
        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
                switch (args[0]) {
                    case "-v":
                        verbose = true;
                        break;
                    default:
                        System.err.println("Usage: java [-v] GoodLoader filename");
                        System.exit(1);
                }
                fileName = args[1];
                break;
            default:
                System.err.println("Usage: java [-v] GoodLoader filename");
                System.exit(1);
        }
//////////////////////////////////////////////////////////

        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "postgres");
        defprop.put("password", "wsy@13650597327");
        defprop.put("database", "Project2");
        Properties prop = new Properties(defprop);
        String line;
        ArrayList<String[]> parts=new ArrayList<>();

////////////////////////////////////////////////////////////////
        try (BufferedReader infile = new BufferedReader(new FileReader(fileName));) {
            infile.readLine();
            while ((line = infile.readLine()) != null) {
                parts.add(line.split("\t"));

            }} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
////////////////////////////////////////////////////////////////

        long start;
        long end;
        start = System.currentTimeMillis();
        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));
//        UpdateOrder(parts);
        deleteOrder(parts);
//////////////////////////////////////////////////////////////////////////





//////////////////////////////////////////////////////////////////////////////////
        con.commit();
        stmt.close();
        closeDB();
        end = System.currentTimeMillis();
        System.out.println(cnt + " records successfully loaded");
        System.out.println("Loading speed : "
                + (cnt * 1000)/(end - start)
                + " records/s");
        System.out.println(end - start);

    }
    public static int getOrderCount() throws SQLException {
        int fin=0;
        try {
//            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
            stmt = con.prepareStatement("select count(*) from orders;");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {

            ResultSet rs1 = null;
            rs1=  stmt.executeQuery();
            while (rs1.next()){
                fin=rs1.getInt(1);
                System.out.println(fin);
            }
        }
        return fin;
    }



    public static void deleteOrder(ArrayList<String[]>parts) throws SQLException {
        ArrayList<String[]>p=new ArrayList<>();

        int length=getOrderCount() ;
        try {
//            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
            stmt = con.prepareStatement("select * ,rank()over (partition by contract_num,salesman_num order by estimated_delivery,product_model )  as seq from orders");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {

//            for (int j = 0; j <length ; j++) {


            ResultSet rs = null;
            rs = stmt.executeQuery();
            for (int j = 0; j <length ; j++) {
                if (rs.next()) {
                    String[]fin=new String[7];
                    for (int i = 1; i <= 7; i++) {


                        String s = rs.getString(i);
                        fin[i - 1] = s;
                        System.out.print(s + " ");
                    }
                    System.out.println();
                    p.add(fin);

                }
            }
        }

        ArrayList<String[]>p1=new ArrayList<>();
        for (int i = 0; i <parts.size() ; i++) {
            for (int j = 0; j < p.size(); j++) {
                if (parts.get(i)[0].equals(p.get(j)[0])){
                    if (parts.get(i)[1].equals(p.get(j)[3])){
                        if (parts.get(i)[2].equals(p.get(j)[6])){
                            p1.add(p.get(j));
                            break;
                        }
                    }
                }
            }
        }



        stmt= con.prepareStatement("delete from orders where contract_num=? and product_model=? and salesman_num=?");

        for (int i = 0; i <p1.size() ; i++) {

            stmt.setString(1,p1.get(i)[0]);
            stmt.setString(2,p1.get(i)[1]);
            stmt.setString(3,p1.get(i)[3]);
            stmt.executeUpdate();

        }








    }



    private static void load_delete(String[]strings,ArrayList<Integer>INT,ArrayList<Integer>DATE) throws SQLException {
        if (con != null) {
            int count = 0;

            for (int i = 0; i < strings.length; i++) {
                if (INT.size() > count) {
                    if (INT.get(count) == i) {
                        stmt.setInt(i + 1, Integer.parseInt(strings[i]));
                        count++;
                        continue;
                    }
                }
                stmt.setString(i + 1, strings[i]);
            }


            stmt.executeUpdate();
        }
    }







    static ArrayList<Integer> nulls=new ArrayList<>();
    public static void UpdateOrder(ArrayList<String[]>parts) throws SQLException {
        String selectStatement ="select ";
        String deleteStatement="delete from ";
        String updateStatement;//后面加
//        String table="order";
        ArrayList<String>Columns =new ArrayList<>();
        ArrayList<String> Data =new ArrayList<>();

        ArrayList<String[]>p1=new ArrayList<>();//从order中选（有相同salesman contract model的对应的行）更新前的
        for (int i = 0; i < parts.size(); i++) {
            String[]strings=new String[3];
            strings[0]=parts.get(i)[0];
            strings[1]=parts.get(i)[1];
            strings[2]=parts.get(i)[2];
            p1.add(strings);
        }
        Data.add("contract_num");
        Data.add("product_model");
        Data.add("salesman_num");

        ArrayList<String[]> beforeUpdate=SelectColums("orders",p1,Columns,Data,selectStatement);
        Columns.clear();
        Data.clear();

        for (int i = 0; i < nulls.size(); i++) {
            parts.remove(i);
        }

        updateStatement="update orders set ";
        Columns.add("contract_num");
        Columns.add("product_model");
        Columns.add("salesman_num");
        Data.add("quantity");
        Data.add("estimated_delivery");
        Data.add("lodgement_date");
        ArrayList<String[]> p=new ArrayList<>();
        for (String[] strings:
                parts) {
            String[] s=new String[strings.length];
            for (int j = 0; j < Data.size(); j++) {
                if (strings[j+Columns.size()].equals("")){
                    s[j]="null";
                }else {
                    s[j] = strings[j + Columns.size()];
                }
            }
            for (int j = Data.size(); j <strings.length ; j++) {
                if (strings[j-Data.size()].equals("")){
                    s[j]="null";
                }else {
                    s[j] = strings[j - Data.size()];
                }
            }
            p.add(s);
        }
        UpdateColumns("orders",p,Columns,Data,updateStatement);/////////////////////更新(确保了更新的是自己的 更新存在的
        Columns.clear();
        Data.clear();


        selectStatement ="select ";
        Data.add("contract_num");
        Data.add("product_model");
        Data.add("salesman_num");
        ArrayList<String[]> afterUpdate=SelectColums("orders",p1,Columns,Data,selectStatement);////////////更新后的
        Columns.clear();
        Data.clear();

        try {
            stmt = con.prepareStatement("delete from orders where quantity=0");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        if (con != null) {
           stmt.executeUpdate();

        }



        selectStatement ="select ";
        ArrayList<String[]>p2=new ArrayList<>();
        for (int i = 0; i < afterUpdate.size(); i++) {
            String[]strings1=new String[1];
            strings1[0]=afterUpdate.get(i)[0];
            p2.add(strings1);
        }
        Data.add("contract_num");
        ArrayList<String[]>centers=SelectColums("contract",p2,Columns,Data,selectStatement);////////找对应的center
        Columns.clear();
        Data.clear();




        selectStatement ="select ";
        ArrayList<String[]>p3=new ArrayList<>();
        for (int i = 0; i <afterUpdate.size() ; i++) {
            String[]strings2=new String[2];
            strings2[0]=centers.get(i)[5];
            strings2[1]=afterUpdate.get(i)[1];
            p3.add(strings2);
        }
        Data.add("center");
        Data.add("product_model");
        ArrayList<String[]>inventory=SelectColums("inventory",p3,Columns,Data,selectStatement);///////////从工厂中找相应的quantity
        Columns.clear();
        Data.clear();


        selectStatement ="select ";
        ArrayList<String[]>p4=new ArrayList<>();
        for (int i = 0; i <afterUpdate.size(); i++) {
            String[]strings1=new String[1];
            strings1[0]=afterUpdate.get(i)[1];
            p4.add(strings1);
        }
//        Columns.add("model");
//        Columns.add("unit_price");
        Data.add("model");
        ArrayList<String[]>unit_price=SelectColums("model",p4,Columns,Data,selectStatement);////////找对应的unit_price
        Columns.clear();
        Data.clear();




        updateStatement="update inventory set ";
        ArrayList<String[]>fin=new ArrayList<>();
        for (int i = 0; i <inventory.size() ; i++) {
            String[]strings3=new String[5];
            strings3[0]=String.valueOf(Integer.parseInt(inventory.get(i)[2])+Integer.parseInt(beforeUpdate.get(i)[2])-Integer.parseInt(afterUpdate.get(i)[2]));
            strings3[1]=String.valueOf(Integer.parseInt(inventory.get(i)[4])-Integer.parseInt(beforeUpdate.get(i)[2])+Integer.parseInt(afterUpdate.get(i)[2]));
            strings3[2]=String.valueOf(Integer.parseInt(inventory.get(i)[5])-Integer.parseInt(beforeUpdate.get(i)[2])*Integer.parseInt(unit_price.get(i)[4])
                    +Integer.parseInt(afterUpdate.get(i)[2])*Integer.parseInt(unit_price.get(i)[4]));//////////profits-之前的数量*单价+现在的数量*单价;
            strings3[3]=inventory.get(i)[0];
            strings3[4]=inventory.get(i)[1];
            fin.add(strings3);
        }

        Data.add("quantity");
        Data.add("sales_quantity");
        Data.add("profits");
        Columns.add("center");
        Columns.add("product_model");
        UpdateColumns("inventoty",fin,Columns,Data,updateStatement);/////////////////根据前面找的单价和center和更新前后的quantity 更新仓库


    }


    public  static void UpdateColumns(String table, ArrayList<String[]> parts, ArrayList<String>Columns, ArrayList<String>Data,String updateStatement){
        ArrayList<Integer> INT =new ArrayList<>();
        ArrayList<Integer> DATE =new ArrayList<>();
        ArrayList<Integer> ColumnIndex =new ArrayList<>();
        if (Data.size()!=0){
            for (int i = 0; i < Data.size() ; i++) {
                if (i== Data.size()-1){
                    updateStatement = updateStatement + Trans(i,Data.get(i),INT,DATE)+" ";
                }else {
                    updateStatement = updateStatement + Trans(i,Data.get(i),INT,DATE) + ",";
                }
            }


        }
        updateStatement=updateStatement+"where ";
        if (Columns.size()!=0){
            for (int i = Data.size(); i < Columns.size()+Data.size() ; i++) {
                if (i== Columns.size()+Data.size()-1){
                    updateStatement = updateStatement + Trans(i, Columns.get(i-Data.size()),INT,DATE);
                }else {
                    updateStatement = updateStatement + Trans(i, Columns.get(i-Data.size()),INT,DATE) + " and ";
                }
            }
        }

        /////////////////////////////open DB
        try {
            stmt = con.prepareStatement(updateStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        String[]strings=new String[parts.get(0).length ];
        cnt =0;
        for (int i = 1; i < parts.size(); i++) {
            try {
                cnt++;

                if (parts.get(i).length > 1) {
                    for (int j = 0; j < parts.get(0).length; j++) {
                        if (parts.get(i)[j].equals("")){
                            parts.get(i)[j]="null";
                        }
                        strings[j]= parts.get(i)[j];
                    }
                    load_update(strings,INT,DATE);
                    con.commit();
                }


            } catch (SQLException se) {
                System.err.println("SQL error: " + se.getMessage());
                try {
                    con.rollback();
                } catch (Exception e2) {
                }
            }
        }
//

//
//        ChooseTable(table,parts);
    }

    public static ArrayList<String[]> SelectColums(String table, ArrayList<String[]> parts, ArrayList<String>Columns, ArrayList<String>Data,String selectStatement){
//        table table = null;
        ArrayList<Integer> INTData =new ArrayList<>();
        ArrayList<Integer> INTColumn =new ArrayList<>();
        ArrayList<Integer> DATEData =new ArrayList<>();
        ArrayList<Integer> DATEColumn =new ArrayList<>();
        ArrayList<String[]>p=new ArrayList<>();
        if (Columns.size()!=0){
            for (int i = 0; i < Columns.size() ; i++) {
                if (i== Columns.size()-1){
                    selectStatement = selectStatement + Trans(i, Columns.get(i),INTColumn,DATEColumn)+" ";
                }else {
                    selectStatement = selectStatement + Trans(i, Columns.get(i),INTColumn,DATEColumn) + ",";
                }
            }


        }else {
            selectStatement = selectStatement +"* ";
            switch (table){
                case "center":INTColumn.add(0);break;
                case "enterprise":INTColumn.add(0);break;
                case "model":INTColumn.add(0);INTColumn.add(4);break;
                case "staff":INTColumn.add(0);INTColumn.add(2);break;
                case "contract":DATEColumn.add(3);break;
                case "orders":INTColumn.add(2);DATEColumn.add(4);DATEColumn.add(5);break;
                case"inventory":INTColumn.add(2);INTColumn.add(3);INTColumn.add(4);INTColumn.add(5);break;
                case "temp":INTColumn.add(2);INTColumn.add(6);break;
            }
        }


        selectStatement = selectStatement +"from "+table+" where ";
        for (int i = 0; i < Data.size(); i++) {
            if (i == 0) {
                selectStatement = selectStatement + Trans(i, Data.get(i),INTData,DATEData);
            } else {
                selectStatement = selectStatement + " and " + Trans(i, Data.get(i),INTData,DATEData);
            }
        }

        /////////////////////////////open DB
        try {
            stmt = con.prepareStatement(selectStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        String[]strings=new String[parts.get(0).length ];
        cnt =0;
        for (int i = 0; i < parts.size();i++ ) {
            try {
                cnt++;

                if (parts.get(i).length >= 1) {
                    for (int j = 0; j < parts.get(0).length; j++) {
                        if (parts.get(i)[j].equals("")){
                            parts.get(i)[j]="null";
                        }
                        strings[j]= parts.get(i)[j];
                    }
                    String[]strings1=load_select(strings,Columns,INTData,DATEData,table);
                    if (strings1!=null){
                        p.add( strings1);
                    }else {
                        nulls.add(i);
                    }
                    con.commit();
                }
            } catch (SQLException se) {
                System.err.println("SQL error: " + se.getMessage());
                try {
                    con.rollback();
                } catch (Exception e2) {
                }
            }
        }
        return p;
    }


    private static String[] load_select(String[] strings,ArrayList<String>Columns,ArrayList<Integer>INT,ArrayList<Integer>DATE,String table)
            throws SQLException {

//        int index=0;
        String[] fin = new String[centerLength.get(table)];
        if (con != null) {
            int countINT=0;
            int countDATE=0;
            ResultSet rs = null;
            for (int i = 0; i <strings.length ; i++) {
                if (INT.size()>countINT){
                    if (INT.get(countINT)==i){
                        stmt.setInt(i+1,Integer.parseInt(strings[i]));
                        countINT++;
                        continue;
                    }
                }
                if (DATE.size()>countDATE){
                    if (DATE.get(countDATE)==i){
                        stmt.setDate(i+1,strToDate(strings[i]));
                        countDATE++;
                        continue;
                    }
                }
                stmt.setString(i+1,strings[i]);
            }
            countINT=0;
            countDATE=0;
            rs=  stmt.executeQuery();
           
            if(rs.next()){
                if (Columns.size()==0) {
                    fin=new String[centerLength.get(table)];
                    for (int i = 1; i <= centerLength.get(table); i++) {
                        if (INT.size() > countINT) {
                            if (INT.get(countINT)+1 == i) {
                                String s=String.valueOf(rs.getInt(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countINT++;
                                continue;
                            }
                        }
                        if (DATE.size() > countDATE) {
                            if (DATE.get(countDATE)+1 == i) {
                                String s=String.valueOf(rs.getDate(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countDATE++;
                                continue;
                            }
                        }
                        String s=rs.getString(i);
                        fin[i-1]=s;
                        System.out.print(s + " ");
                    }
                    System.out.println();
                }else {
                    fin=new String[Columns.size()];
                    for (int i = 1; i <= Columns.size(); i++) {
                        if (INT.size() > countINT) {
                            if (INT.get(countINT)+1 == i) {
                                String s=String.valueOf(rs.getInt(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countINT++;
                                continue;
                            }
                        }
                        if (DATE.size() > countDATE) {
                            if (DATE.get(countDATE)+1 == i) {
                                String s=String.valueOf(rs.getDate(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countDATE++;
                                continue;
                            }
                        }
                        String s=rs.getString(i);
                        fin[i-1]=s;
                        System.out.print(s + " ");
                    }
                    System.out.println();
                }
            }
        }
        boolean isNull=true;
        for (int i = 0; i < fin.length; i++) {
            if (fin[i]!=null){
                isNull=false;
                break;
            }
        }
        return isNull?null:fin;
    }
    public static void load_update(String[] strings,ArrayList<Integer>INT,ArrayList<Integer>DATE) throws SQLException {
        if (con != null) {
            int countINT=0;
            int countDATE=0;
            for (int i = 0; i < strings.length; i++) {
                if (INT.size()>countINT){
                    if (INT.get(countINT)==i){
                        stmt.setInt(i+1,Integer.parseInt(strings[i]));
                        countINT++;
                        continue;
                    }
                }
                if (DATE.size()>countDATE){
                    if (DATE.get(countDATE)==i){
                        stmt.setDate(i+1,strToDate(strings[i]));
                        countDATE++;
                        continue;
                    }
                }
                stmt.setString(i + 1, strings[i]);
            }


            stmt.executeUpdate();
        }
    }

    public static String Trans(int i,String s,ArrayList<Integer>INT,ArrayList<Integer>DATE){
//        if (Columns.contains(s)){
//            ColumnIndex.add(i);
//        }
        if (s.toLowerCase(Locale.ROOT).equals("id")||s.toLowerCase().contains("seq")||s.toLowerCase(Locale.ROOT).equals("age")||s.toLowerCase(Locale.ROOT).equals("unit_price")||
                s.toLowerCase(Locale.ROOT).contains("quantity")||s.toLowerCase(Locale.ROOT).contains("money")||s.toLowerCase(Locale.ROOT).contains("profits")){
            INT.add(i);

        }
        if (s.toLowerCase(Locale.ROOT).contains("date")||s.toLowerCase().contains("estimated")||s.toLowerCase().contains("lodgement")){
            DATE.add(i);

        }
        return s+"=? ";

    }





}


