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
	// public JTextField connectIPInput = new JTextField();
	// public JTextField distanceInput = new JTextField();
	// public JTextField targetIPInput = new JTextField();
	// public JTextField messageInput = new JTextField();
	// public JLabel outputLabel = new JLabel("");
	
	public JTextField localHost;
	public JButton sendDistance = new JButton("连接");
	public JButton sendMessage = new JButton("发送");
	public JTextField neighbour = new HintTextField("邻居IP地址");
	public JTextField neighbourDis = new HintTextField("距离");
	public JTextField otherHost = new HintTextField("其他主机IP");
	public JTextField message = new HintTextField("发送消息");
	public JFrame frame = new JFrame();
	public JTextArea logInfomation = new JTextArea("");
	public JPanel jPanel = new JPanel();
	public GridBagLayout gridBagLayout = new GridBagLayout();
	public Host host;
	
	public UI(Host _host) {
		host = _host;
		localHost = new HintTextField(host.localIP);
		go();
	}

	public void go() {
		jPanel.setBackground(Color.DARK_GRAY);
		jPanel.setVisible(true);
		jPanel.setLayout(gridBagLayout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		Font font = new Font("Microsoft Yahei", Font.ITALIC, 18);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.weightx = 0;
		localHost.setColumns(9);
		localHost.setEnabled(false);
		localHost.setFont(font);
		localHost.setBorder(BorderFactory.createEtchedBorder(new Color(195, 211,
				230), new Color(195, 211, 230)));
		gridBagLayout.setConstraints(localHost, constraints);
		jPanel.add(localHost);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		JTextField blank = new JTextField("");
		blank.setEnabled(false);
		gridBagLayout.setConstraints(blank, constraints);
		jPanel.add(blank);

		neighbour.setColumns(9);
		neighbour.setBorder(BorderFactory.createEtchedBorder(new Color(195, 211,
				230), new Color(195, 211, 230)));
		neighbourDis.setFont(font);
		neighbourDis.setColumns(16);
		neighbourDis.setBorder(BorderFactory.createEtchedBorder(new Color(195, 211,
				230), new Color(195, 211, 230)));
		neighbour.setFont(font);
		constraints.gridwidth = 1;
		constraints.weightx = 2;
		gridBagLayout.setConstraints(neighbour, constraints);
		jPanel.add(neighbour);
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		gridBagLayout.setConstraints(neighbourDis, constraints);
		jPanel.add(neighbourDis);

		sendDistance.setFont(new Font("Microsoft Yahei", Font.BOLD, 18));
		constraints.gridwidth = 3;
		constraints.weightx = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(sendDistance, constraints);
		jPanel.add(sendDistance);

		constraints.gridwidth = GridBagConstraints.REMAINDER;
		JTextField blank1 = new JTextField("");
		blank.setEnabled(false);
		gridBagLayout.setConstraints(blank1, constraints);
		jPanel.add(blank1);

		otherHost.setColumns(9);
		otherHost.setBorder(BorderFactory.createEtchedBorder(new Color(195, 211,
				230), new Color(195, 211, 230)));
		otherHost.setFont(font);
		message.setFont(font);
		message.setBorder(BorderFactory.createEtchedBorder(new Color(195, 211,
				230), new Color(195, 211, 230)));
		message.setColumns(16);
		constraints.gridwidth = 1;
		constraints.weightx = 2;
		gridBagLayout.setConstraints(otherHost, constraints);
		jPanel.add(otherHost);
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		gridBagLayout.setConstraints(message, constraints);
		jPanel.add(message);

		sendMessage.setFont(new Font("Microsoft Yahei", Font.BOLD, 18));
		constraints.gridwidth = 3;
		constraints.weightx = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(sendMessage, constraints);
		jPanel.add(sendMessage);

		logInfomation.setLineWrap(true);
		logInfomation.setBorder(BorderFactory.createEtchedBorder(new Color(195, 211,
				230), new Color(195, 211, 230)));
		logInfomation.setFont(font);
		logInfomation.setWrapStyleWord(true);
		constraints.weighty = 1;
		constraints.weightx = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(logInfomation, constraints);
		jPanel.add(logInfomation);
		frame.add(jPanel);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((int)(dimension.width * 0.3), (int)(dimension.height * 0.9));
		frame.setLocation((int)(dimension.width*0.35), 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("IP路由");

		sendDistance.addActionListener(new BtnListen1());
		sendMessage.addActionListener(new BtnListen2());

		frame.setVisible(true);


		// Font font = new Font("Arial", Font.BOLD, 25);
		// connectIPInput.setFont(font);
		// connectIPInput.setHorizontalAlignment(SwingConstants.CENTER);
		// distanceInput.setFont(font);
		// distanceInput.setHorizontalAlignment(SwingConstants.CENTER);
		// targetIPInput.setFont(font);
		// targetIPInput.setHorizontalAlignment(SwingConstants.CENTER);
		// messageInput.setFont(font);
		// messageInput.setHorizontalAlignment(SwingConstants.CENTER);
		// JLabel equal = new JLabel("=");
		// equal.setFont(font);
		// equal.setBorder(BorderFactory.createEtchedBorder(new Color(195, 211,
		// 		230), new Color(195, 211, 230)));
		// equal.setHorizontalAlignment(SwingConstants.CENTER);

		// JFrame frame = new JFrame("Route!");
		// GridLayout gl = new GridLayout(3, 3, 10, 10);
		// frame.setLayout(gl);
		// frame.add(connectIPInput);
		// frame.add(distanceInput);
		// JButton btn1 = new JButton("connect");
		// btn1.addActionListener(new BtnListen1());
		// btn1.setFont(font);
		// frame.add(btn1);

		// frame.add(targetIPInput);
		// frame.add(messageInput);
		// JButton btn2 = new JButton("send");
		// btn2.addActionListener(new BtnListen2());
		// btn2.setFont(font);
		// frame.add(btn2);

		// font = font.deriveFont(2);
		// outputLabel.setFont(font);
		// outputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// frame.add(outputLabel);

		// frame.setMinimumSize(new Dimension(900, 300));
		// // frame.setBounds(0, 0, 10, 10);
		// frame.pack();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setVisible(true);
	}

	class BtnListen1 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String IP = neighbour.getText();
			String _distance = neighbourDis.getText();
			if (neighbour.getText().equals("")
					|| neighbourDis.getText().equals("")) {
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
			String targetIP = otherHost.getText();
			String msg = message.getText();
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

class HintTextField extends JTextField implements FocusListener {
	private final String hint;
	private boolean showingHint;

	public HintTextField(final String hint) {
		super(hint);
		this.hint = hint;
		this.showingHint = true;
		super.addFocusListener(this);
	}
	@Override
	public void focusGained(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText("");
			showingHint = false;
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText(hint);
			showingHint = true;
		}
	}
	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
}
