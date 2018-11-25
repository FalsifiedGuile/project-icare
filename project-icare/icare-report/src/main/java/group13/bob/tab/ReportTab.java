package group13.bob.tab;

import group13.bob.table.Table;
import group13.bob.table.TableConstructor;
import group13.bob.templates.FormArray;
import group13.bob.templates.ReportTemplates;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;


public class ReportTab extends JFrame {

  private JTabbedPane tab = new JTabbedPane();
  private JTable reportTable;
  private TableColumn column;
  private int lastTab;
  private final int WIDTH = 1518;
  private final int defaultWidth = 200;
  private String[] emptyValue = {"", "", "", ""};
  private String[][][] fields;
  private String topLanguages = "Top 10 Language of Service:";
  private String perferredOfficialLanguage = "Perferred Official Language:";
  private String referredBy = "Referred By";
  private String totalCounts =
      "                          Total Counts(all clients)";
  private JButton plus;

  // this is how the data should look like when extract data from database
  private String[] dateRange =
      new String[] {"", "2013", "2014", "2015", "2016", "2017", "2018"};
  private String[][] topLanguagesTestValues =
      {{"English", "10", "20", "30", "40", "50", "60"},
          {"English", "10", "20", "30", "40", "50", "60"},
          {"English", "10", "20", "30", "40", "50", "60"},
          {"English", "10", "20", "30", "40", "50", "60"},
          {"English", "10", "20", "30", "40", "50", "60"},
          {"English", "10", "20", "30", "40", "50", "60"},
          {"English", "10", "20", "30", "40", "50", "60"},
          {"English", "10", "20", "30", "40", "50", "60"},
          {"English", "10", "20", "30", "40", "50", "60"},
          {"English", "10", "20", "30", "40", "50", "60"},
          {"English", "10", "20", "30", "40", "50", "60"}};
  private String[][] perferredLanguageTestValues =
      {{"English", "10", "20", "30", "40", "50", "60"},
          {"French", "10", "20", "30", "40", "50", "60"},
          {"Unknown", "10", "20", "30", "40", "50", "60"}};
  private String[][] referredByTestValues =
      {{"English", "10", "20", "30", "40", "50", "60"},
          {"French", "10", "20", "30", "40", "50", "60"},
          {"Unknown", "10", "20", "30", "40", "50", "60"}};
  private String[][] totalCountsTestForTopLanguagesValues =
      {{"500", "600", "700", "800", "40", "50", "60"}};
  private String[][] totalCountsTestForPerferredLanguagesValues =
      {{"5000", "6000", "7000", "8000", "400", "500", "600"}};

  public void runReportTab() {
    lastTab = 2;
    plus = new JButton("+");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setTitle("Report Tabs");
    setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
    setBounds(200, 100, 1518, 878);
    setLayout(null);

  /*  int n = dateRange.length - 1;
    String[] sampleRange = new String[n];
    System.arraycopy(dateRange, 1, sampleRange, 0, n);

    topLanguagesTestValues = TableConstructor.getTopTableFromRanges(sampleRange,
        "InfoForum", "language_of_service", 10);
    perferredLanguageTestValues = TableConstructor.getTopTableFromRanges(
        sampleRange, "InfoForum", "official_language_of_preference", 4);
    referredByTestValues = TableConstructor.getTopTableFromRanges(sampleRange,
        "InfoForum", "referred_by", 3);

    JButton showTable = new JButton("Show Table");
    showTable.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        lauchTableGui();
      }
    });*/


    JLabel label = new JLabel("This is Report1");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    JPanel report = new JPanel();
    report.setBorder(new EmptyBorder(5, 5, 5, 5));
    report.setLayout(new BorderLayout());
    tab.addTab("report1", null, report, "First");
    populateTable(new String[] {"Language of Service", "Official Language of Preference", "Referred By"}
    , "report1", report);
    JButton tableButton = new JButton("show table");
    tableButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        launchTableGui();
      }
    });
    report.add(tableButton, BorderLayout.PAGE_END);
    JPanel report2 = new JPanel();
    tab.addTab("report2", null, report2, "second");

    JPanel report3 = new JPanel();
    tab.addTab("report3", null, report3, "third");

    final ReportTab temp = this;

    // Add the plus button to the tabs.
    plus.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ReportTemplates.CreateTemplatePopUp(temp);
      }
    });
    setPlus();

    tab.setSelectedIndex(0);
    setLayout(new GridLayout(1, 1));
    add(tab);
  }

  public void setNewTab(String name, String[] template) {
    JPanel newReport = new JPanel();
    newReport.setLayout(new BorderLayout());
    newReport.setBorder(new EmptyBorder(5, 5, 5, 5));
    setPlus();
    tab.setTitleAt(lastTab - 1, name);
    tab.setToolTipTextAt(lastTab - 1, "The report for " + name);
    // Let table be not editable
    tab.setComponentAt(lastTab - 1, newReport);
    populateTable(template, name, newReport);
  }

  private void createTable(int totalNumberOfRows) {
    DefaultTableModel reportTableModel =
        new DefaultTableModel(null, dateRange) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };
    reportTable = new JTable(reportTableModel);
    // First initialize the table with all empty values so that later can
    // override those values by row and column
    for (int c = 0; c < totalNumberOfRows; c++) {
      reportTableModel.addRow(emptyValue);
    }
    // Set width of each column
    reportTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    for (int i = 0; i < dateRange.length; i++) {
      column = reportTable.getColumnModel().getColumn(i);
      column.setPreferredWidth(200);
    }
    // Let the order of each column cannot be changed
    reportTable.getTableHeader().setReorderingAllowed(false);
    reportTable.setFillsViewportHeight(true);
  }

  private void populateTable(String[] fieldNames, String name, JPanel report) {
    int n = dateRange.length - 1;
    String[] sampleRange = new String[n];
    System.arraycopy(dateRange, 1, sampleRange, 0, n);
    int totalNumberOfRows = 0;
    fields = new String[fieldNames.length][][];
    HashMap<String, String> infoMap = (new FormArray()).getInfoMap();
    for (int index = 0; index < fieldNames.length; index++) {
     // System.out.println(fieldNames[index]);
      this.fields[index] = TableConstructor.getTopTableFromRanges(sampleRange,
          "InfoForum", infoMap.get(fieldNames[index]), 5);
      //System.out.println("Number: "+this.fields[index].length);
      totalNumberOfRows = totalNumberOfRows + this.fields[index].length + 3;
    }
    totalNumberOfRows--;
    createTable(totalNumberOfRows);
    //System.out.println("Number:" + totalNumberOfRows);

    JScrollPane scrollPane = new JScrollPane();

    // int topLanguageRow, col, perferredLanguageRow, referRow;

    // Let table be not editable

    int totRow = 0;
    int numFields = 0;
    System.out.println(fieldNames.length);
    while (numFields < fieldNames.length) {
      // Initialize the tile of first part of the report (Top 10 Languages)
      reportTable.setValueAt("Top " + fieldNames[numFields] + " results", totRow, 0);
      totRow++;
      // Put related data into the table by given a range of number of rows
      for (int row = 0; row < fields[numFields].length; row++) {
        for (int col = 0; col < dateRange.length; col++) {
          reportTable.setValueAt(fields[numFields][row][col], totRow, col);
        }
        totRow++;
      }
      // Initialize total counts of top languages
      reportTable.setValueAt(totalCounts, totRow, 0);
      addTotalCountsData(totRow, totalCountsTestForTopLanguagesValues);
      totRow = totRow + 2;
      numFields++;
    }
    scrollPane.setViewportView(reportTable);
    report.add(scrollPane, BorderLayout.CENTER);
    numFields++;


  }

  private void addTotalCountsData(int rowNumber, String[][] data) {
    for (int column = 1; column < dateRange.length; column++) {
      reportTable.setValueAt(data[0][column], rowNumber, column);
    }
  }

  public int setReportValue(int start, String[][] inputData) {
    int startRowNumber, endRowNumber, col, index = 0;
    int upTo = start + inputData.length + 2;
    // plus 2 because there is a title for each part and there is a total count
    // from last part of report
    start += 2;
    for (startRowNumber = start; startRowNumber < upTo; startRowNumber++) {
      for (col = 0; col < dateRange.length; col++) {
        reportTable.setValueAt(inputData[index][col], startRowNumber, col);
      }
      index++;
    }
    endRowNumber = startRowNumber;
    return endRowNumber;
  }

  protected void launchTableGui() {
    try {
      Table frame = new Table();
      frame.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setPlus() {
    tab.addTab(null, null, null, "Click to add another report");
    lastTab++;
    tab.setTabComponentAt(lastTab, plus);
  }

  public static void main(String[] args) {
    ReportTab frame = new ReportTab();
    frame.runReportTab();
    frame.setVisible(true);
  }
}

