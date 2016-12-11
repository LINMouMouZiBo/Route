import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Utils.MsgPacket;

public class UI {
	public JTextField connectIPInput = new JTextField();
	public JTextField distanceInput = new JTextField();
	public JTextField targetIPInput = new JTextField();
	public JTextField messageInput = new JTextField();
	public JLabel outputLabel = new JLabel("");
	
	public Host host;
	
	public UI(Host _host) {
		host = _host;
		go();
	}

	public void go() {
		Font font = new Font("Arial", Font.BOLD, 25);
		connectIPInput.setFont(font);
		connectIPInput.setHorizontalAlignment(SwingConstants.CENTER);
		distanceInput.setFont(font);
		distanceInput.setHorizontalAlignment(SwingConstants.CENTER);
		targetIPInput.setFont(font);
		targetIPInput.setHorizontalAlignment(SwingConstants.CENTER);
		messageInput.setFont(font);
		messageInput.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel equal = new JLabel("=");
		equal.setFont(font);
		equal.setBorder(BorderFactory.createEtchedBorder(new Color(195, 211,
				230), new Color(195, 211, 230)));
		equal.setHorizontalAlignment(SwingConstants.CENTER);

		JFrame frame = new JFrame("Route!");
		GridLayout gl = new GridLayout(3, 3, 10, 10);
		frame.setLayout(gl);
		frame.add(connectIPInput);
		frame.add(distanceInput);
		JButton btn1 = new JButton("connect");
		btn1.addActionListener(new BtnListen1());
		btn1.setFont(font);
		frame.add(btn1);

		frame.add(targetIPInput);
		frame.add(messageInput);
		JButton btn2 = new JButton("send");
		btn2.addActionListener(new BtnListen2());
		btn2.setFont(font);
		frame.add(btn2);

		font = font.deriveFont(2);
		outputLabel.setFont(font);
		outputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(outputLabel);

		frame.setMinimumSize(new Dimension(900, 300));
		// frame.setBounds(0, 0, 10, 10);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	class BtnListen1 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String IP = connectIPInput.getText();
			String _distance = distanceInput.getText();
			if (connectIPInput.getText().equals("")
					|| distanceInput.getText().equals("")) {
				JOptionPane.showMessageDialog(null,
						"not input number to perfrom!");
			} else {
				try {
					int dis = Integer.parseInt(_distance);
					host.creartConnet(IP, dis);
					// JOptionPane.showMessageDialog(null,
					// "Your have not decided which operation to perform!");
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(null,
							"The Input is not a number!");
				}
			}
		}
	}

	class BtnListen2 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String targetIP = targetIPInput.getText();
			String msg = messageInput.getText();
			if (targetIP.equals("") || msg.equals("")) {
				JOptionPane.showMessageDialog(null,
						"not input number to perfrom!");
			} else {
				MsgPacket msgPacket = new MsgPacket(msg, 1, host.localIP, targetIP);
				host.sendMessagePacket(msgPacket);
			}
		}
	}
}
