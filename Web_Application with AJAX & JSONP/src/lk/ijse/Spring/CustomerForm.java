package lk.ijse.Spring;/*@author:Dilshan Rajika Withanachchi*/

import lk.ijse.Spring.db.CrudUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(urlPatterns = "/Customer")
public class CustomerForm extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
      System.out.println("Calling do get");



        try {
                BasicDataSource basicDataSource= (BasicDataSource) getServletContext().getAttribute("ds");
            final Connection connection = basicDataSource.getConnection();
            final PreparedStatement pst = connection.prepareStatement("Select * from Customer");
            final ResultSet rst = pst.executeQuery();
            // final ResultSet rst = CrudUtil.execute("Select * from Customer");
             final PrintWriter writer = resp.getWriter();
             resp.setContentType("application/json");
             final JsonArrayBuilder allcustomer = Json.createArrayBuilder();


             while (rst.next()) {
                        String id=rst.getString(1);
                        String fname=rst.getString(2);
                        String lname= rst.getString(3);
                        String address= rst.getString(4);
                        String contactno=rst.getString(5);
                        String country=  rst.getString(6);
                        String city= rst.getString(7);
                        String zipcode= rst.getString(8);
                 final JsonObjectBuilder customer = Json.createObjectBuilder();
                 customer.add("id",id);
                 customer.add("fname",fname);
                 customer.add("lname",lname);
                 customer.add("address",address);
                 customer.add("contactno",contactno);
                 customer.add("country",country);
                 customer.add("city",city);
                 customer.add("zipcode",zipcode);
                 allcustomer.add(customer.build());

             }
                    writer.print(allcustomer.build());
                     writer.close();

         }catch (Exception e){
         e.printStackTrace();
           }

    }
    /*
                    writer.write("[");
                    while (rst.next()) {
                        String id=rst.getString(1);
                        String fname=rst.getString(2);
                        String lname= rst.getString(3);
                        String address= rst.getString(4);
                        String contactno=rst.getString(5);
                        String country=  rst.getString(6);
                        String city= rst.getString(7);
                        String zipcode= rst.getString(8);
                        writer.write("{\"id\":\""+ id +"\",\"fname\":\""+ fname +"\",\"lname\":\""+ lname +"\"," +
                                "\"address\":\""+address +"\",\"contactno\":\""+contactno +"\",\"country\":\""+ country +
                                "\",\"city\":\""+ city +"\",\"zipcode\":\""+ zipcode +"\"},");
                    }
             writer.write("{}");
             writer.write("]");
             writer.close();*/

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String CustID=req.getParameter("id");
        String CustFirstName=req.getParameter("fname");
        String CustLastName= req.getParameter("lname");
        String CustAddress=req.getParameter("address");
        String CustContactNumber=req.getParameter("contactno");
        String CustCountry= req.getParameter("country");
        String CustCity=req.getParameter("city");
       String  CustZipCode=req.getParameter("zipcode");

        try {
            BasicDataSource basicDataSource= (BasicDataSource) getServletContext().getAttribute("ds");
            final Connection connection = basicDataSource.getConnection();
            final PreparedStatement pst = connection.prepareStatement("Insert into Customer values(?,?,?,?,?,?,?,?)");
                pst.setObject(1,CustID);
                pst.setObject(2,CustFirstName);
                pst.setObject(3,CustLastName);
                pst.setObject(4,CustAddress);
                pst.setObject(5,CustContactNumber);
                pst.setObject(6,CustCountry);
                pst.setObject(7,CustCity);
                pst.setObject(8,CustZipCode);
                pst.executeUpdate();

        }catch (Exception E){
    E.printStackTrace();
}



    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonReader reader = Json.createReader(req.getReader());
        final JsonObject customer = reader.readObject();
        String id=customer.getString("id");
        String fname=customer.getString("fname");
        String lname=customer.getString("lname");
        String address=customer.getString("address");
        String contactno=customer.getString("contactno");
        String country=customer.getString("country");
        String city=customer.getString("city");
        String zipcode=customer.getString("zipcode");

       try {
           BasicDataSource basicDataSource= (BasicDataSource) getServletContext().getAttribute("ds");
           final Connection connection = basicDataSource.getConnection();
           final PreparedStatement pst = connection.prepareStatement("Update Customer set CustFirstName=?,CustLastName=?,Address=?, ContactNo=?,Country=?,City=?,ZipCode=? where CustID=?");
           pst.setObject(1,fname);
           pst.setObject(2,lname);
           pst.setObject(3,address);
           pst.setObject(4,contactno);
           pst.setObject(5,country);
           pst.setObject(6,city);
           pst.setObject(7,zipcode);
           pst.setObject(8,id);
           pst.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }




    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doDelete");
        final JsonReader reader = Json.createReader(req.getReader());
        final JsonObject customer = reader.readObject();
        String id=customer.getString("id");
        try {
            BasicDataSource basicDataSource= (BasicDataSource) getServletContext().getAttribute("ds");
            final Connection connection = basicDataSource.getConnection();
            final PreparedStatement pst = connection.prepareStatement("Delete from Customer where CustID=?");
            pst.setObject(1,id);
            pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
