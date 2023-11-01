import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ResourceMonitorGUI {
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable processTable;
    private JTable Jtable2;
    private DefaultTableModel table2;
    private ScheduledExecutorService exe;
    private Graph cpuChart;
    private memGraph memChart;
    
    public ResourceMonitorGUI() throws IOException, InterruptedException {
    	//just basic jframe junk
        frame = new JFrame("System Resource Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        //creating a table with columns
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Resources: ");
        tableModel.addColumn("CPU: ");
        tableModel.addColumn("Memory: ");
        //allows for resizing of the table
        processTable = new JTable(tableModel);
        processTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        frame.add(new JScrollPane(processTable));
        
        table2 = new DefaultTableModel();
        table2.addColumn("Storage: " + ResourceCalls.storage() + " GB free out of " + ResourceCalls.totalstore() + " GB");
        if(ResourceCalls.netconnection() == 0) {
        	table2.addColumn("Internet Connection Status: Connected");
        }
        else {
        	table2.addColumn("Internet Connection Status: Disconnected");
        }
        Jtable2 = new JTable(table2);
        Jtable2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        cpuChart = new Graph("CPU Usage", "Time", "CPU (%)");
        memChart = new memGraph("Memory Usage", "Time", "Memory (%)");
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.add(cpuChart, BorderLayout.WEST);
        chartContainer.add(memChart,BorderLayout.EAST);
        
        frame.add(new JScrollPane(processTable), BorderLayout.NORTH);
        frame.add(chartContainer, BorderLayout.SOUTH);
        frame.add(new JScrollPane(Jtable2), BorderLayout.CENTER);
        
        exe = Executors.newScheduledThreadPool(1);
    }
        
    //starts the timer and sets jframe to visible
    public void start() {
        frame.setVisible(true);
        updateExe();
    }
    
    private void updateExe() {
    	exe.schedule(this::update, 1, TimeUnit.SECONDS);
    }
    //where the magic happens
    private void update() {
        // clears the rows
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        // adds rows with relevant info
        tableModel.addRow(new Object[]{"Usage (%)", ResourceCalls.cpu(), ResourceCalls.memory()});
        tableModel.addRow(new Object[] {"Free (%)", ResourceCalls.cpufree(), ResourceCalls.memoryfree()});
        
        updateExe();
    }
    //boring main method that starts the gui
    public static void main(String[] args) throws IOException, InterruptedException {
    	
    	ResourceMonitorGUI gui = new ResourceMonitorGUI();
        gui.start();
            
        
    }
}
