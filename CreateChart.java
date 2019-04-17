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

	String bar_Title = "각 지점별 커피 판매액";
	String bar_x_axis = "지역";
	String bar_y_axis = "판매액(단위 : 만원)";
	String bar_query = 
			"SELECT d_region, sum(d_sales) AS 지점_별_판매량 "
			+ "FROM Cafesales_region "
			+ "GROUP BY d_region";
	
	
	String line_Title ="지점별 월별 커피 판매액 ";
	String line_x_axis ="월";
	String line_y_axis ="판매액(단위 : 만원)";	
	String line_query = 
			"SELECT k1.d_month, k1.역삼,  k2.강남, k3.논현,k4.선릉,k5.삼성 ,k6.잠실, k7.양재\r\n" + 
			"FROM  \r\n" + 
			"                (SELECT d_month,sum(d_sales) AS 역삼  \r\n" + 
			"                    FROM Cafesales_region  \r\n" + 
			"                    WHERE d_region LIKE '역삼'  \r\n" + 
			"                    GROUP BY d_month)k1,\r\n" + 
			"                ( SELECT d_month,sum(d_sales)AS 강남\r\n" + 
			"                    FROM Cafesales_region\r\n" + 
			"                WHERE d_region LIKE '강남'  \r\n" + 
			"                GROUP BY d_month)k2,  \r\n" + 
			"                (SELECT d_month,sum(d_sales) AS 논현  \r\n" + 
			"                FROM Cafesales_region \r\n" + 
			"                WHERE d_region LIKE '논현'  \r\n" + 
			"                GROUP BY d_month)k3 ,\r\n" + 
			"                (SELECT d_month,sum(d_sales) AS 선릉  \r\n" + 
			"                FROM Cafesales_region \r\n" + 
			"                WHERE d_region LIKE '선릉'  \r\n" + 
			"                GROUP BY d_month)k4,\r\n" + 
			"                (SELECT d_month,sum(d_sales) AS 삼성  \r\n" + 
			"                FROM Cafesales_region \r\n" + 
			"                WHERE d_region LIKE '삼성'  \r\n" + 
			"                GROUP BY d_month)k5,\r\n" + 
			"                (SELECT d_month,sum(d_sales) AS 잠실  \r\n" + 
			"                FROM Cafesales_region \r\n" + 
			"                WHERE d_region LIKE '잠실'  \r\n" + 
			"                GROUP BY d_month)k6,\r\n" + 
			"                (SELECT d_month,sum(d_sales) AS 양재  \r\n" + 
			"                FROM Cafesales_region \r\n" + 
			"                WHERE d_region LIKE '양재'  \r\n" + 
			"                GROUP BY d_month)k7\r\n" + 
			"WHERE  k1.d_month  = k2.d_month   \r\n" + 
			"AND k1.d_month  = k3.d_month  \r\n" + 
			"AND k1.d_month  = k4.d_month  \r\n" + 
			"AND k1.d_month  = k5.d_month  \r\n" + 
			"AND k1.d_month  = k6.d_month  \r\n" + 
			"AND k1.d_month  = k7.d_month  \r\n" + 
			"ORDER BY k1.d_month";
	
	String pie_Title = "각 커피종류별 판매량";
	String pie_query ="SELECT  d_name, SUM(d_count) AS 매뉴판매량 "
			+ "FROM cafasales_menu "
			+ "GROUP BY  d_name ";
			
	public void drawBarChart() {

		JFrame frame = new JFrame();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFreeChart barChart = inputBarChartdataset(PlotOrientation.VERTICAL);// 차트 생성
		BufferedImage image = barChart.createBufferedImage(750, 450); // 이미지파일 생성

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(image));
		frame.getContentPane().add(label);

		//Dimension frameSize = frame.getSize(); // 프레임 사이즈
		Dimension screenSize = 
				Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		frame.setLocation(
				((screenSize.width)/5), 
				((screenSize.height)/5)); // 막대 차트 프레임 위치 


		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true); //막대 차트 프레임 출력
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
			
			//배경색 설정 (미설정시 회색)
			barChart_temp.getPlot().setBackgroundPaint(Color.WHITE);
			//barChart_temp.getPlot().setBackgroundPaint(new Color(0,0,0));
			
			//글씨 색 설정 //(미설정시 검정색)
			//barChart_temp.getTitle().setPaint(Color.RED); //제목 색 설정
			//barChart_temp.getLegend().setItemPaint(Color.orange); //범주 색 설정
			
			// 한글 폰트 깨짐 방지
			barChart_temp.getTitle().setFont(new Font("굴림", Font.BOLD, 25));
			barChart_temp.getLegend().setItemFont(new Font("굴림", Font.BOLD, 20));
			CategoryPlot plot = barChart_temp.getCategoryPlot();
			plot.getDomainAxis().setLabelFont(new Font("굴림", Font.BOLD, 15));
			plot.getDomainAxis().setTickLabelFont(new Font("굴림", Font.BOLD, 15));
			plot.getRangeAxis().setLabelFont(new Font("굴림", Font.BOLD, 15));
			plot.getRangeAxis().setTickLabelFont(new Font("굴림", Font.BOLD, 15));

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return barChart_temp;
	}

	public void drawLineChart() {

		JFrame frame = new JFrame();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFreeChart lineChart = inputLinedataset(PlotOrientation.VERTICAL);// 차트 생성
		BufferedImage image = lineChart.createBufferedImage(750, 450);// 이미지파일 생성
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(image));
		frame.getContentPane().add(label);

		
		//Dimension frameSize = frame.getSize(); // 프레임 사이즈
		Dimension screenSize = 
				Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		frame.setLocation(
				(screenSize.width)/5, 
				(screenSize.height)/5); // 선 차트 프레임 위치

		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true); //선 차트 프레임 출력
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

			//배경색 설정 (미설정시 회색)
			//lineChart_temp.getPlot().setBackgroundPaint(Color.WHITE);
			lineChart_temp.getPlot().setBackgroundPaint(new Color(0,0,0));
			
			//글씨 색 설정 //(미설정시 검정색)
			//lineChart_temp.getTitle().setPaint(Color.RED); //제목 색 설정
			//lineChart_temp.getLegend().setItemPaint(Color.orange); //범주 색 설정
			
			// 한글 폰트 깨짐 방지
			lineChart_temp.getTitle().setFont(new Font("굴림", Font.BOLD, 25));
			lineChart_temp.getLegend().setItemFont(new Font("굴림", Font.BOLD, 20));
			CategoryPlot plot = lineChart_temp.getCategoryPlot();
			plot.getDomainAxis().setLabelFont(new Font("굴림", Font.BOLD, 15));
			plot.getDomainAxis().setTickLabelFont(new Font("굴림", Font.BOLD, 15));
			plot.getRangeAxis().setLabelFont(new Font("굴림", Font.BOLD, 15));
			plot.getRangeAxis().setTickLabelFont(new Font("굴림", Font.BOLD, 15));

	       
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return lineChart_temp;
	}
	
	public void drawPieChart() {
		JFrame frame = new JFrame();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFreeChart pieChart = inputPieChartdataset(PlotOrientation.VERTICAL);// 차트 생성
		BufferedImage image = pieChart.createBufferedImage(750, 450); // 이미지파일 생성

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(image));
		frame.getContentPane().add(label);

		//Dimension frameSize = frame.getSize(); // 프레임 사이즈
		Dimension screenSize = 
				Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		frame.setLocation(
				((screenSize.width)/5), 
				((screenSize.height)/5)); // 파이 차트 프레임 위치 


		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true); //파이 차트 프레임 출력
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
		
			//배경색 설정 (미설정시 회색)
			pieChart_temp.getPlot().setBackgroundPaint(Color.WHITE);
			//pieChart_temp.getPlot().setBackgroundPaint(new Color(0,0,0));
			
			PiePlot plot = (PiePlot) pieChart_temp.getPlot();
			
			//글씨 색 설정 //(미설정시 검정색)
			//pieChart_temp.getTitle().setPaint(Color.RED); //제목 색 설정
			//pieChart_temp.getLegend().setItemPaint(Color.orange); //범주 색 설정
			//plot.setLabelPaint(Color.RED); //파이 차트 내 라벨 색 설정
			
			// 한글 폰트 깨짐 방지
			pieChart_temp.getTitle().setFont(new Font("굴림", Font.BOLD,25));
			pieChart_temp.getLegend().setItemFont(new Font("굴림", Font.BOLD, 20));
			plot.setLabelFont(new Font("굴림", Font.BOLD,15)); //파이 차트 내 라벨 폰트 설정
			
	       
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return pieChart_temp;
	}

}
