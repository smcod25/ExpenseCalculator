import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.awt.Font.CENTER_BASELINE;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Connection conn;
        JFrame frame= new JFrame("Expense Calculator");

        JLabel todayExpenselable= new JLabel("Expenses :");
        todayExpenselable.setForeground(Color.RED);
        todayExpenselable.setFont(new Font("ARIAL",Font.PLAIN,20));
        todayExpenselable.setBounds(120,10 ,140, 60);

        JLabel expensetype_label =new JLabel( "Expense Type:");
        expensetype_label.setBounds(20,50,120,50);
        frame.add(expensetype_label);

        JTextField expense_type_field = new JTextField();
        expense_type_field.setBounds(120,60,120,30);
        frame.add(expense_type_field);

        JLabel   expense_amt_label=new JLabel( "Expense amt:");
        expense_amt_label.setBounds(20,100,120,20);

        JTextField expense_amt_textfield = new JTextField();
        expense_amt_textfield.setBounds(120,90,120,30);
        frame.add(expense_amt_textfield);

        JLabel income_title_label= new JLabel("Income");
        income_title_label.setForeground(Color.GREEN);
        income_title_label.setFont(new Font("ARIAL",Font.PLAIN,20));
        income_title_label.setBounds(120,140,120,50);

        frame.add(income_title_label);
        frame.add(expense_amt_label);
        frame.add(todayExpenselable);
        JLabel   income_label=new JLabel( "Income:");
        income_label.setBounds(30,200,120,20);
        frame.add(income_label);
        JTextField income_textfield = new JTextField();
        income_textfield.setBounds(120,200,120,30);
        frame.add(income_textfield);
        JButton add_button= new JButton("Add");
        add_button.setBounds(130,250,120,40);
        frame.add(add_button);
        JButton view_button= new JButton("VIEW");
        view_button.setBounds(250,250,120,40);
        frame.add(view_button);
        String url="jdbc:mysql://localhost:3306/expensecalculator";
        String username ="root";
        String password ="";
        try {
            conn = DriverManager.getConnection(url,username,password);
            System.out.println("DB Connected");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        add_button.addActionListener(e -> {
            // Add The Validation of empty Form
            String expense_type =expense_type_field.getText();
            int expense_amount=expense_amt_textfield.getText().isEmpty()?0: Integer.valueOf(expense_amt_textfield.getText());
            if (expense_amount !=0 && !expense_type.isEmpty()){
                //insert the Record to Mysql

                String insert_data="INSERT INTO expensestb (expense_type,expense_amount,income_amount) VALUES (?,?,?)";
                PreparedStatement statement= null;
                try {
                    statement = conn.prepareStatement(insert_data);
                    statement.setString(1,expense_type);
                    statement.setInt(2,expense_amount);
                    statement.setInt(3,Integer.parseInt(income_textfield.getText()));
                    statement.execute();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"The values can't be 0 or Empty");
            }
        });
        frame.setSize(500,400);
        frame.setLayout(null);
        frame.setVisible(true);

    }
}