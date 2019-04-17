import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.data.jdbc.JDBCPieDataset;

public class CreateChart {

	DBmanager db = new DBmanager();

	String bar_Title = "�� ������ Ŀ�� �Ǹž�";
	String bar_x_axis = "����";
	String bar_y_axis = "�Ǹž�(���� : ����)";
	String bar_query = 
			"SELECT d_region, sum(d_sales) AS ����_��_�Ǹŷ� "
			+ "FROM Cafesales_region "
			+ "GROUP BY d_region";
	
	
	String line_Title ="������ ���� Ŀ�� �Ǹž� ";
	String line_x_axis ="��";
	String line_y_axis ="�Ǹž�(���� : ����)";	
	String line_query = 
			"SELECT k1.d_month, k1.����,  k2.����, k3.����,k4.����,k5.�Ｚ ,k6.���, k7.����\r\n" + 
			"FROM  \r\n" + 
			"                (SELECT d_month,sum(d_sales) AS ����  \r\n" + 
			"                    FROM Cafesales_region  \r\n" + 
			"                    WHERE d_region LIKE '����'  \r\n" + 
			"                    GROUP BY d_month)k1,\r\n" + 
			"                ( SELECT d_month,sum(d_sales)AS ����\r\n" + 
			"                    FROM Cafesales_region\r\n" + 
			"                WHERE d_region LIKE '����'  \r\n" + 
			"                GROUP BY d_month)k2,  \r\n" + 
			"                (SELECT d_month,sum(d_sales) AS ����  \r\n" + 
			"                FROM Cafesales_region \r\n" + 
			"                WHERE d_region LIKE '����'  \r\n" + 
			"                GROUP BY d_month)k3 ,\r\n" + 
			"                (SELECT d_month,sum(d_sales) AS ����  \r\n" + 
			"                FROM Cafesales_region \r\n" + 
			"                WHERE d_region LIKE '����'  \r\n" + 
			"                GROUP BY d_month)k4,\r\n" + 
			"                (SELECT d_month,sum(d_sales) AS �Ｚ  \r\n" + 
			"                FROM Cafesales_region \r\n" + 
			"                WHERE d_region LIKE '�Ｚ'  \r\n" + 
			"                GROUP BY d_month)k5,\r\n" + 
			"                (SELECT d_month,sum(d_sales) AS ���  \r\n" + 
			"                FROM Cafesales_region \r\n" + 
			"                WHERE d_region LIKE '���'  \r\n" + 
			"                GROUP BY d_month)k6,\r\n" + 
			"                (SELECT d_month,sum(d_sales) AS ����  \r\n" + 
			"                FROM Cafesales_region \r\n" + 
			"                WHERE d_region LIKE '����'  \r\n" + 
			"                GROUP BY d_month)k7\r\n" + 
			"WHERE  k1.d_month  = k2.d_month   \r\n" + 
			"AND k1.d_month  = k3.d_month  \r\n" + 
			"AND k1.d_month  = k4.d_month  \r\n" + 
			"AND k1.d_month  = k5.d_month  \r\n" + 
			"AND k1.d_month  = k6.d_month  \r\n" + 
			"AND k1.d_month  = k7.d_month  \r\n" + 
			"ORDER BY k1.d_month";
	
	String pie_Title = "�� Ŀ�������� �Ǹŷ�";
	String pie_query ="SELECT  d_name, SUM(d_count) AS �Ŵ��Ǹŷ� "
			+ "FROM cafasales_menu "
			+ "GROUP BY  d_name ";
			
	public void drawBarChart() {

		JFrame frame = new JFrame();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFreeChart barChart = inputBarChartdataset(PlotOrientation.VERTICAL);// ��Ʈ ����
		BufferedImage image = barChart.createBufferedImage(750, 450); // �̹������� ����

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(image));
		frame.getContentPane().add(label);

		//Dimension frameSize = frame.getSize(); // ������ ������
		Dimension screenSize = 
				Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������
		frame.setLocation(
				((screenSize.width)/5), 
				((screenSize.height)/5)); // ���� ��Ʈ ������ ��ġ 


		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true); //���� ��Ʈ ������ ���
	}

	private JFreeChart inputBarChartdataset
	(PlotOrientation aOrientation) 
	{
		
		JFreeChart barChart_temp = null;
		CategoryDataset barDataset;
		try {
			barDataset = new JDBCCategoryDataset(db.conn, bar_query);;
			barChart_temp = ChartFactory.createBarChart
					(bar_Title, // title
					bar_x_axis, // x-axis label
					bar_y_axis, // y-axis label
					barDataset, aOrientation, 
					true, // legend displayed
					true, // tooltips displayed
					false); // no URLs
			
			//���� ���� (�̼����� ȸ��)
			barChart_temp.getPlot().setBackgroundPaint(Color.WHITE);
			//barChart_temp.getPlot().setBackgroundPaint(new Color(0,0,0));
			
			//�۾� �� ���� //(�̼����� ������)
			//barChart_temp.getTitle().setPaint(Color.RED); //���� �� ����
			//barChart_temp.getLegend().setItemPaint(Color.orange); //���� �� ����
			
			// �ѱ� ��Ʈ ���� ����
			barChart_temp.getTitle().setFont(new Font("����", Font.BOLD, 25));
			barChart_temp.getLegend().setItemFont(new Font("����", Font.BOLD, 20));
			CategoryPlot plot = barChart_temp.getCategoryPlot();
			plot.getDomainAxis().setLabelFont(new Font("����", Font.BOLD, 15));
			plot.getDomainAxis().setTickLabelFont(new Font("����", Font.BOLD, 15));
			plot.getRangeAxis().setLabelFont(new Font("����", Font.BOLD, 15));
			plot.getRangeAxis().setTickLabelFont(new Font("����", Font.BOLD, 15));

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return barChart_temp;
	}

	public void drawLineChart() {

		JFrame frame = new JFrame();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFreeChart lineChart = inputLinedataset(PlotOrientation.VERTICAL);// ��Ʈ ����
		BufferedImage image = lineChart.createBufferedImage(750, 450);// �̹������� ����
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(image));
		frame.getContentPane().add(label);

		
		//Dimension frameSize = frame.getSize(); // ������ ������
		Dimension screenSize = 
				Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������
		frame.setLocation(
				(screenSize.width)/5, 
				(screenSize.height)/5); // �� ��Ʈ ������ ��ġ

		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true); //�� ��Ʈ ������ ���
	}

	private JFreeChart inputLinedataset
	(PlotOrientation aOrientation) 
	{
		JFreeChart lineChart_temp = null;
		CategoryDataset lineDataset;
		try {
			lineDataset = new JDBCCategoryDataset(db.conn, line_query);
			lineChart_temp = ChartFactory.createLineChart
					(line_Title, // title
					line_x_axis, // x-axis label
					line_y_axis, // y-axis label
					lineDataset, aOrientation, 
					true, // legend displayed
					true, // tooltips displayed
					false); // no URLs

			//���� ���� (�̼����� ȸ��)
			//lineChart_temp.getPlot().setBackgroundPaint(Color.WHITE);
			lineChart_temp.getPlot().setBackgroundPaint(new Color(0,0,0));
			
			//�۾� �� ���� //(�̼����� ������)
			//lineChart_temp.getTitle().setPaint(Color.RED); //���� �� ����
			//lineChart_temp.getLegend().setItemPaint(Color.orange); //���� �� ����
			
			// �ѱ� ��Ʈ ���� ����
			lineChart_temp.getTitle().setFont(new Font("����", Font.BOLD, 25));
			lineChart_temp.getLegend().setItemFont(new Font("����", Font.BOLD, 20));
			CategoryPlot plot = lineChart_temp.getCategoryPlot();
			plot.getDomainAxis().setLabelFont(new Font("����", Font.BOLD, 15));
			plot.getDomainAxis().setTickLabelFont(new Font("����", Font.BOLD, 15));
			plot.getRangeAxis().setLabelFont(new Font("����", Font.BOLD, 15));
			plot.getRangeAxis().setTickLabelFont(new Font("����", Font.BOLD, 15));

	       
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return lineChart_temp;
	}
	
	public void drawPieChart() {
		JFrame frame = new JFrame();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFreeChart pieChart = inputPieChartdataset(PlotOrientation.VERTICAL);// ��Ʈ ����
		BufferedImage image = pieChart.createBufferedImage(750, 450); // �̹������� ����

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(image));
		frame.getContentPane().add(label);

		//Dimension frameSize = frame.getSize(); // ������ ������
		Dimension screenSize = 
				Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������
		frame.setLocation(
				((screenSize.width)/5), 
				((screenSize.height)/5)); // ���� ��Ʈ ������ ��ġ 


		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true); //���� ��Ʈ ������ ���
	}

	private JFreeChart inputPieChartdataset
	(PlotOrientation aOrientation) 
	{
		JFreeChart pieChart_temp = null;
		PieDataset pieDataset;
		try {
			pieDataset = new JDBCPieDataset(db.conn, pie_query);
			pieChart_temp =
					ChartFactory.createPieChart
					( pie_Title, // title
					pieDataset,
					true,      // legend displayed
					true,      // tooltips displayed
					false );   // no URLs
		
			//���� ���� (�̼����� ȸ��)
			pieChart_temp.getPlot().setBackgroundPaint(Color.WHITE);
			//pieChart_temp.getPlot().setBackgroundPaint(new Color(0,0,0));
			
			PiePlot plot = (PiePlot) pieChart_temp.getPlot();
			
			//�۾� �� ���� //(�̼����� ������)
			//pieChart_temp.getTitle().setPaint(Color.RED); //���� �� ����
			//pieChart_temp.getLegend().setItemPaint(Color.orange); //���� �� ����
			//plot.setLabelPaint(Color.RED); //���� ��Ʈ �� �� �� ����
			
			// �ѱ� ��Ʈ ���� ����
			pieChart_temp.getTitle().setFont(new Font("����", Font.BOLD,25));
			pieChart_temp.getLegend().setItemFont(new Font("����", Font.BOLD, 20));
			plot.setLabelFont(new Font("����", Font.BOLD,15)); //���� ��Ʈ �� �� ��Ʈ ����
			
	       
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return pieChart_temp;
	}

}
