
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChartManager implements ActionListener {

	CreateChart cc = new CreateChart();

	JButton barchart_button;
	JButton linechart_button;
	JButton piechart_button;
	
	JPanel chartpanel;

	ChartManager() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		chartpanel = new JPanel();

		barchart_button = new JButton("BarChart");	// ��ư ����
		linechart_button = new JButton("LineChart"); 
		piechart_button = new JButton("PieChart"); 
		
		barchart_button.addActionListener(this); // ��ư �̺�Ʈ ���
		linechart_button.addActionListener(this);
		piechart_button.addActionListener(this);

		chartpanel.setLayout(new GridLayout(4, 1));
		chartpanel.add(barchart_button);
		chartpanel.add(linechart_button);
		chartpanel.add(piechart_button);
		
		frame.add(chartpanel, BorderLayout.WEST);

		// Dimension frameSize = frame.getSize(); // ������ ������
		Dimension screenSize = 
				Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������
		frame.setLocation(
				(screenSize.width) / 3, 
				(screenSize.height) / 3); // ������ ��ġ ����

		frame.setSize(800, 400);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object obj = arg0.getSource();

		if (obj == barchart_button) {

			cc.drawBarChart();

		} else if (obj == linechart_button) {
			
			cc.drawLineChart();
		}
		else if (obj == piechart_button) {
			
			cc.drawPieChart();
		}

	}
}
