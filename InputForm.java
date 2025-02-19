
package inputform;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.*;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;

class Customer
{
    private int custid;
    private String name;
    private String address;
    
    public Customer(int c,String n,String a)
    {
        custid=c;
        name=n;
        address=a;
    }
    public int getID(){return custid;}
    public String getName(){return name;}
    public String getAdress(){return address;}
    public void setAddress(String add){address=add;}
}

public class InputForm extends Application
{
    int count=0;
    public void start(Stage stage)
    {
        HashMap<Integer,Customer> hm=new HashMap<>();
        
        
        
        Font f=new Font("Times New Roman",20);
        
        Label l1=new Label("Customer ID");l1.setFont(f);
        Label l2=new Label("Name");l2.setFont(f);
        Label l3=new Label("Address");l3.setFont(f);
        
        ComboBox<Integer> cust=new ComboBox<>();
        cust.setStyle("-fx-font-size:20");
        TextField name=new TextField();name.setFont(f);
        TextField add=new TextField();add.setFont(f);
        name.setPrefColumnCount(15);
        add.setPrefColumnCount(20);
        
        Button save=new Button("Save");
        Button create=new Button("Create");
        
        create.setOnAction(e->{
            ++count;
            cust.getItems().add(count);
            cust.setValue(count);
            name.setText("");
            add.setText("");
        });
        
        save.setOnAction(e->{
            
        Customer c=new Customer(cust.getValue(),name.getText(),add.getText());
        hm.put(count, c);
        
        try(PrintStream ps=new PrintStream(new FileOutputStream("Customer.txt")))
        {
            for(Customer ct:hm.values())
            {
                ps.println(ct.getID());
                ps.println(ct.getName());
                ps.println(ct.getAdress());
            }
        }
        catch(Exception ex)
        {
            
        }
        });
        
        HBox hb1=new HBox();hb1.setAlignment(Pos.CENTER);
        HBox hb2=new HBox();hb2.setAlignment(Pos.CENTER);
        HBox hb3=new HBox();hb3.setAlignment(Pos.CENTER);
        HBox hb4=new HBox();hb4.setAlignment(Pos.CENTER);
        
        hb1.getChildren().addAll(l1,cust);
        hb2.getChildren().addAll(l2,name);
        hb3.getChildren().addAll(l3,add);
        hb4.getChildren().addAll(create,save);
        VBox vb=new VBox();vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(hb1,hb2,hb3,hb4);
        
        try(Scanner scan=new Scanner(new FileInputStream("Customer.txt")))
        {
            int c;
            String n;
            String a;
            System.out.println("hi");
            while(scan.hasNext())
            {
                c=scan.nextInt();
                n=scan.next();
                a=scan.next();
                hm.put(c, new Customer(c,n,a));
                if(c>count)count=c;
                cust.getItems().add(c);
            }
        }
        catch(Exception ex)
        {
            
        }
        
        cust.valueProperty().addListener(e->{
            int c=cust.getValue();
            Customer ct=hm.get(c);
            name.setText(ct.getName());
            add.setText(ct.getAdress());
        });
        
        Scene sc=new Scene(vb,500,500);
        stage.setScene(sc);
        stage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
        
    }
    
}
