import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph extends JPanel {
	//creates a chart and a series of coordinates 
    private JFreeChart chart;
    private XYSeries series;
    
    //graph constructor
    public Graph(String title, String x, String y) {
    	//sets up series and collection
    	series = new XYSeries("Data");
    	XYSeriesCollection dataset = new XYSeriesCollection(series);
    	//sets chart = to a linechart with x y coordinates and title
    	chart = ChartFactory.createXYLineChart(title, x, y, dataset, PlotOrientation.VERTICAL,true,true,false);
    	//sets up plotting
    	XYPlot plot = (XYPlot) chart.getPlot();
    	NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
    	yAxis.setAutoRange(true);
    	
    	XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
    	plot.setRenderer(renderer);
    	//creates a panel to put the graph on
    	ChartPanel panel = new ChartPanel(chart);
    	add(panel);
    	//creates a timer to call updatesData method
        Timer timer = new Timer(1000, new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		updateData();
        	}
        });
    	timer.start();
}

    //there's a small issue here, but it's much better than printing all points
    public void updateData() {
    	//if log is not empty it will print a point
    	if(!log.cpuData.isEmpty()) {
    	double cpuValue = log.cpuData.get(log.cpuData.size() - 1);
        series.add(series.getItemCount(), cpuValue);
    	//if there are more than or = to 10 points it will remove 1 then print one
    	if(series.getItemCount() >= 10) {
    		series.remove(0);
    		cpuValue = log.cpuData.get(log.cpuData.size() - 1);
            series.add(series.getItemCount(), cpuValue);
    	}

    	chart.fireChartChanged();
    }
}
}

