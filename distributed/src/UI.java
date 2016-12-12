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

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import Utils.MsgPacket;

public class UI {
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
		//得到IP
		localHost = new HintTextField(host.localIP);
		go();
	}

	/**
	 * 添加UI部件，容器:GridBagLayout
	 *
	 * @param
	 */
	public void go() {
		//设置panel样式
		jPanel.setBackground(Color.DARK_GRAY);
		jPanel.setVisible(true);
		jPanel.setLayout(gridBagLayout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		Font font = new Font("Microsoft Yahei", Font.ITALIC, 18);

		//设置每一个部件的样式(宽度、边框、字体等)
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
		logInfomation.setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
		logInfomation.setWrapStyleWord(true);
		constraints.weighty = 1;
		constraints.weightx = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(logInfomation, constraints);
		jPanel.add(logInfomation);
		frame.add(jPanel);

		//设置frame样式(适配屏幕、标题等)
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((int)(dimension.width * 0.3), (int)(dimension.height * 0.9));
		frame.setLocation((int)(dimension.width*0.35), 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("IP路由");

		sendDistance.addActionListener(new BtnListen1());
		sendMessage.addActionListener(new BtnListen2());

		frame.setVisible(true);
	}


	/**
	 * 添加与邻居Host连接的监听器
	 */
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
					host.createConnet(IP, dis);
					// JOptionPane.showMessageDialog(null,
					// "Your have not decided which operation to perform!");
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(null,
							"The Input is not a number!");
				}
			}
		}
	}

	/**
	 * 添加于某一Host通讯监听器
	 */
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

	/**
	 * 设置主机IP
	 *
	 * @param IP:用户输入主机地址
	 */
	public void setLocalIP(String IP) {
		localHost.setText(IP);
	}
}

class HintTextField extends JTextField implements FocusListener {
	private final String hint;
	private boolean showingHint;

	/**
	 * 新建带有提示信息的textField
	 *
	 * @param hint:提示信息
	 */
	public HintTextField(final String hint) {
		super(hint);
		this.hint = hint;
		this.showingHint = true;
		super.addFocusListener(this);
	}

	/**
	 * 监听鼠标位置
	 *
	 * @param e:鼠标焦点获得事件
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText("");
			showingHint = false;
		}
	}

	/**
	 * 监听鼠标位置
	 *
	 * @param e:鼠标焦点失去时间
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText(hint);
			showingHint = true;
		}
	}

	/**
	 * 获取textField内容
	 */
	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
}
