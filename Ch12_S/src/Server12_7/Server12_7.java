package Server12_7;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

class Server12_7 extends Frame implements ActionListener, Runnable {
	TalkRoom TalkRecord = new TalkRoom();
	TextField TalkInput = new TextField();
	ServerSocket SSocket;
	Socket socket;
	static int port;
	static String outfilename;
	DataOutputStream outstream;
	DataInputStream instream;
	FileWriter fw;

	public Server12_7() {
		super("Server");
		try {
			SSocket = new ServerSocket(port);
			System.out
					.println("Server is created and waiting Client to connect...");

			socket = SSocket.accept();
			System.out.println("Client IP = "
					+ socket.getInetAddress().getHostAddress());

			outstream = new DataOutputStream(socket.getOutputStream());
			instream = new DataInputStream(socket.getInputStream());
			fw = new FileWriter(outfilename);

			add(TalkRecord, BorderLayout.CENTER);
			add(TalkInput, BorderLayout.SOUTH);
			TalkInput.addActionListener(this);

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					System.exit(0);
				}
			});

			setSize(500, 350);
			setVisible(true);
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == TalkInput) {
			try {
				outstream.writeUTF("Server> " + TalkInput.getText());
				TalkRecord.PrintTalk(TalkInput.getText(), Color.red);
				fw.write(TalkInput.getText() + "\r\n");
				fw.flush();
			} catch (Exception e) {
				TalkRecord.PrintTalk("Connection is Interrupted", Color.green);
			}
			TalkInput.setText(null);
		}
	}

	public void run() {
		try {
			while (true) {
				String NetTransferLine = instream.readUTF();
				TalkRecord.PrintTalk(NetTransferLine, Color.black);
				fw.write(NetTransferLine + "\r\n");
				fw.flush();
			}
		} catch (Exception e) {
			TalkRecord.PrintTalk("Connection is Interrupted!", Color.green);
		}
	}

	public static void main(String args[]) {
		if (args.length < 2) {
			System.out.println("USAGE: java Server12_7 [port] [outfilename]");
			System.exit(1);
		}

		port = Integer.parseInt(args[0]);
		outfilename = args[1];
		Server12_7 ServerStart = new Server12_7();
	}
}

class TalkRoom extends Component {
	Vector PastLine = new Vector();
	Vector NewLine = new Vector();

	synchronized void PrintTalk(String s, Color c) {
		NewLine.addElement(new SaveLine(s, c));
		repaint();
	}

	public void paint(Graphics g) {
		synchronized (this) {
			while (NewLine.size() > 0) {
				PastLine.addElement(NewLine.elementAt(0));
				NewLine.removeElementAt(0);
			}
			while (PastLine.size() > 40) {
				PastLine.removeElementAt(0);
			}
		}
		FontMetrics fontM = g.getFontMetrics();
		int margin = fontM.getHeight() / 2;
		int w = getSize().width;
		int y = getSize().height - fontM.getHeight() - margin;

		for (int i = PastLine.size() - 1; i >= 0; i--) {
			SaveLine ShowLine = (SaveLine) PastLine.elementAt(i);
			g.setColor(ShowLine.clr);
			g.setFont(new Font(ShowLine.str, Font.BOLD, 12));
			g.drawString(ShowLine.str, margin, y + fontM.getAscent());
			y -= fontM.getHeight();
		}
	}
}

class SaveLine {
	String str;
	Color clr;

	SaveLine(String str, Color clr) {
		this.str = str;
		this.clr = clr;
	}
}
