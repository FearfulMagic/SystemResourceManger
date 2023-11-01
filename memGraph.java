import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer; // Import the Timer class from javax.swing

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class memGraph extends JPanel {
    private JFreeChart chart;
    private XYSeries series;
    
    public memGraph(String title, String x, String y) {
    	series = new XYSeries("Data");
    	XYSeriesCollection dataset = new XYSeriesCollection(series);
    	
    	chart = ChartFactory.createXYLineChart(title, x, y, dataset, PlotOrientation.VERTICAL,true,true,false);
    	
    	XYPlot plot = (XYPlot) chart.getPlot();
    	NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
    	yAxis.setAutoRange(true);
    	
    	XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
    	plot.setRenderer(renderer);
    	
    	ChartPanel panel = new ChartPanel(chart);
    	add(panel);
    	
        Timer timer = new Timer(1000, new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		updateData();
        	}
        });
    	timer.start();
}


    public void updateData() {
    	if(!log.MemUseData.isEmpty()) {
    	double memValue = log.MemUseData.get(log.MemUseData.size() - 1);
        series.add(series.getItemCount(), memValue);
        
    	if( series.getItemCount() >= 10) {
    		series.remove(0);
    		memValue = log.MemUseData.get(log.MemUseData.size() - 1);
            series.add(series.getItemCount(), memValue);
    	}

    	chart.fireChartChanged();
    }
}
}
