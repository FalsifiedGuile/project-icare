package group13.bob.templates;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
public class ReportTemplates {
  private static String[] columns = FormArray.getColumnNames();
  private static boolean[] includedInTemplate;
  private static JFrame templateFrame;
  private static boolean fieldsSelected;
  // A counter that keeps track of the amount of fields selected.
  private static int templateColumnNum;
  
  public static void CreateTemplatePopUp() {
    includedInTemplate = new boolean[columns.length];
    templateColumnNum = 0;
    fieldsSelected = false;
    templateFrame = new JFrame("Create new Template");
    templateFrame.setPreferredSize(new Dimension(500, 500));
    templateFrame.pack();
    templateFrame.setLocationRelativeTo(null);
    templateFrame.setVisible(true);
    
    JPanel combinePanel = new JPanel(new GridLayout(columns.length, 1));
    // Create an array of buttons that switch the color upon click.
    JButton[] buttons = new JButton[columns.length];
    for(int i = 0; i < columns.length; i++ ) {
      final int index = i;
      buttons[i] = new JButton(columns[i]);
      buttons[i].setBackground(Color.RED);
      buttons[i].setPreferredSize(new Dimension(40, 40));
      buttons[i].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switchButton(buttons[index], index);
      }
    });
      combinePanel.add(buttons[i]);
    }
    
    JPanel submitPanel = new JPanel(new GridLayout(0, 1));
    JScrollPane scrollPane = new JScrollPane(combinePanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    templateFrame.add(scrollPane, BorderLayout.CENTER);
    
    // A check box that selects all the fields.
    JCheckBox selectAll = new JCheckBox("Select all fields");
    selectAll.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < columns.length; i++) {
          if (!((fieldsSelected)^(includedInTemplate[i]))) {
            switchButton(buttons[i], i);
          }
        }
        fieldsSelected = !(fieldsSelected);
      }
      });
    submitPanel.add(selectAll);
    
    // Add the button for submit.
    JButton submitButton = new JButton("Submit the template");
    submitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createNamePopUp();
      }
    });
    submitPanel.add(submitButton);
    templateFrame.add(submitPanel, BorderLayout.PAGE_END);
    
  }
  
  private static void switchButton(JButton button, int index) {
    includedInTemplate[index] = !(includedInTemplate[index]);
    if (includedInTemplate[index]) {
      button.setBackground(Color.GREEN);
      templateColumnNum++;
    } else {
      button.setBackground(Color.RED);
      templateColumnNum--;
    }
  }
  private static void saveTemplate(String name) throws IOException {
    BufferedWriter temp = new BufferedWriter(new FileWriter(name));
    String[] templateNames = new String[templateColumnNum];
    int index = 0;
    for (int i = 0; i < columns.length; i++) {
      if (includedInTemplate[i]) {
        templateNames[index] = columns[i];
        index++;
      }
    }
    temp.write(templateColumnNum + "\n");
    for(int i = 0; i < templateNames.length; i++) {
      temp.write(templateNames[i]+"\n");
    }
    temp.flush();
    temp.close();
    templateFrame.dispose();
  }
  
  private static String[] retrieveTemplate(String name) throws IOException {
    try {
      FileReader fr = new FileReader(name);
      BufferedReader br = new BufferedReader(fr);
      int numFields = Integer.parseInt(br.readLine());
      String[] fields = new String[numFields];
      for(int i = 0; i < numFields; i++) {
        fields[i] = br.readLine();
      }
      fr.close();
      br.close();
      return fields;
    } catch (IOException e) {
      return new String[1];
    }
  }
  private static void createNamePopUp() {
    JFrame nameFrame = new JFrame("Type in the name of the file");
    nameFrame.setPreferredSize(new Dimension(400, 200));
    nameFrame.pack();
    nameFrame.setLocationRelativeTo(null);
    nameFrame.setVisible(true);
    
    JTextField fileName = new JTextField();
    fileName.setColumns(5);
    fileName.setBorder(BorderFactory.createLineBorder(Color.black));
    nameFrame.add(fileName);
    //fileName.setColumns(10);
    JPanel savePanel = new JPanel(new GridLayout(0, 1));
    JButton saveButton = new JButton("Save name");
    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          saveTemplate(fileName.getText());
        } catch (IOException e1) {
          System.out.println("Something is wrong");
        }
        nameFrame.dispose();
      }
    });
    savePanel.add(saveButton);
    nameFrame.add(savePanel, BorderLayout.PAGE_END);
  }
}
