package com.ssii;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame implements ActionListener, KeyListener{
    private static final long serialVersionUID = 1L;
    //主界面控件
    JScrollPane main_JScrollPane;
    JTextArea main_JTextArea;

    JMenuBar jMenuBar_main;
    JMenu help_main;
    JMenuItem instructions,author,version;

    ServerSocket server;
    Socket client;

    String clientIP_String = "",serverIP_String = "";
    int port_int = 20015;  //默认端口

    BufferedReader br;
    BufferedWriter bw;
    InputStream data_from_client;
    OutputStream data_to_client;

    //聊天界面控件
    JFrame chatJFrame;
    JPanel main_JPanel,button_JPanel;
    JScrollPane message_JScrollPane,edit_JScrollPane;
    JTextArea message_JTextArea,edit_JTextArea;
    JButton submit,reset;

    JMenuBar jMenuBar;
    JMenu help;
    JMenuItem clientIP,serverIP,port;

    String data_from_client_String = "",data_edit_JTA = "";  //显示框和输入框的内容
    boolean started = false;  //是否已连接


    public Server(){
        super("Consultation System Server by zifangsky");
        setSize(350,500);
        setLocation(800, 70);

        main_JScrollPane = new JScrollPane();
        main_JTextArea = new JTextArea(15,10);
        main_JTextArea.setLineWrap(true);  //激活自动换行功能
        main_JTextArea.setWrapStyleWord(true);  //激活自动换行功能
        main_JTextArea.setEditable(false);
        main_JScrollPane.getViewport().add(main_JTextArea);

        jMenuBar_main = new JMenuBar();
        help_main = new JMenu("帮助");
        instructions = new JMenuItem("说明");
        author = new JMenuItem("作者");
        version = new JMenuItem("版本号");
        jMenuBar_main.add(help_main);
        help_main.add(instructions);
        help_main.add(author);
        help_main.add(version);
        instructions.addActionListener(this);
        author.addActionListener(this);
        version.addActionListener(this);

        add(main_JScrollPane);
        setJMenuBar(jMenuBar_main);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            serverIP_String = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            System.out.println("获取服务端IP失败");
            e1.printStackTrace();
        }
        //从配置文件config.txt中读取端口
        try {
            BufferedReader readconfig_br = new BufferedReader(new FileReader(new File("D:/Documents/Downloads/Consultation-System-master/Consultation-System-master/Consultation System Server/src/com/zifangsky/www/config.txt")));
            String temp;
            //读取端口
            temp = readconfig_br.readLine();  //第一行不要
            if((temp = readconfig_br.readLine().trim()) != null){
                port_int = Integer.parseInt(temp);
            }
            readconfig_br.close();
            server = new ServerSocket(port_int);
            //一直监听来自客户端的请求
            while(true){
                client = server.accept();
                if(client != null && started == false){
                    clientIP_String = client.getInetAddress().toString();
                    started = true;  //表示已连接
                    main_JTextArea.append("Client["+clientIP_String+"]已经上线了！！！\n\n");
                    handleClient();	 //处理连接后的操作
                }
            }
        } catch (IOException e) {
            System.out.println("启动服务端失败");
            e.printStackTrace();
        }
    }

    //客户端连接到服务段后，新建聊天界面以及处理连接
    private void handleClient() {
        try {
            //从客户端接收数据
            data_from_client = client.getInputStream();
            br = new BufferedReader(new InputStreamReader(data_from_client));

            //向客户端发送数据
            data_to_client = client.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(data_to_client));

            //连接成功后，返回欢迎信息
            bw.write("您好，欢迎您连接到'Consultation System'服务端"+"\r\n\r\n");
            bw.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //聊天界面
        chatJFrame = new JFrame();
        chatJFrame.setTitle("Chat whih "+clientIP);
        chatJFrame.setSize(350,650);
        chatJFrame.setLocation(800, 70);

        //面板，界面
        main_JPanel = new JPanel();
        button_JPanel = new JPanel();
        message_JScrollPane = new JScrollPane();
        edit_JScrollPane = new JScrollPane();
        message_JTextArea = new JTextArea(24,10);
        edit_JTextArea = new JTextArea(10,10);
        submit = new JButton("发送");
        reset = new JButton("清空");

        main_JPanel.setLayout(new BorderLayout());
        main_JPanel.add(message_JScrollPane,BorderLayout.NORTH);
        main_JPanel.add(button_JPanel,BorderLayout.CENTER);
        main_JPanel.add(edit_JScrollPane,BorderLayout.SOUTH);

        message_JScrollPane.getViewport().add(message_JTextArea);
        edit_JScrollPane.getViewport().add(edit_JTextArea);

        message_JTextArea.setEditable(false);  //设置消息显示框不可编辑
        message_JTextArea.setLineWrap(true);  //激活自动换行功能
        edit_JTextArea.setLineWrap(true);
        message_JTextArea.setWrapStyleWord(true);
        edit_JTextArea.setWrapStyleWord(true);

        button_JPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        button_JPanel.add(submit);
        button_JPanel.add(reset);

        jMenuBar = new JMenuBar();
        help = new JMenu("帮助");
        clientIP = new JMenuItem("客户端IP");
        serverIP = new JMenuItem("服务端IP");
        port = new JMenuItem("端口号");
        jMenuBar.add(help);
        help.add(clientIP);
        help.add(serverIP);
        help.add(port);


        chatJFrame.add(main_JPanel);
        chatJFrame.setJMenuBar(jMenuBar);
        chatJFrame.setVisible(true);
        chatJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //注册点击事件
        submit.addActionListener(this);
        reset.addActionListener(this);
        clientIP.addActionListener(this);
        serverIP.addActionListener(this);
        port.addActionListener(this);

        //增加键盘事件
        edit_JTextArea.addKeyListener(this);

        try {
            while((data_from_client_String = br.readLine()) != null){
                //去掉字符串末尾的\r\n
                String test = data_from_client_String.substring(0,data_from_client_String.length());
                if(test.equals("") == false){
                    if(test.equals("q")){  //如果是 q ，服务端断开与客户端的连接
                        disConnect();
                        started = false;  //连接已断开
                        chatJFrame.dispose();
                        main_JTextArea.append("Client["+clientIP_String+"]已经下线了！！！\n\n");
                        break;
                    }
                    else if(test.equals("qq")){  //如果是 qq ，服务端断开与客户端的连接并退出服务
                        disConnect();
                        System.exit(0);
                        break;
                    }
                    else{
                        message_JTextArea.append("Client["+clientIP_String+"]:\n"+data_from_client_String + "\n\n");
                        //显示最新消息
                        message_JTextArea.selectAll();
                        message_JTextArea.setCaretPosition(message_JTextArea.getSelectionEnd());
                    }

                }

            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"已经与客户端连接断开","提示:",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    //将发送信息的操作抽象出来
    private void submitMessage() {
        data_edit_JTA = edit_JTextArea.getText().trim();
        try {
            if(data_edit_JTA.equals("") == false && started){
                bw.write(data_edit_JTA+"\r\n\r\n");  //加入“\r\n\r\n”，可以使服务端程序认为HTTP头已经结束，可以处理了
                bw.flush();
                message_JTextArea.append("Server["+serverIP_String+"]:\n"+data_edit_JTA+"\n\n");

                //显示最新消息
                message_JTextArea.selectAll();
                message_JTextArea.setCaretPosition(message_JTextArea.getSelectionEnd());

                edit_JTextArea.setText("");  //提交后编辑框设空
            }
            edit_JTextArea.requestFocus();  //获得焦点
        } catch (IOException e1) {
            System.out.println("消息发送失败");
            e1.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == instructions){
            JOptionPane.showMessageDialog(this,"1,程序所用端口号保存在配置文件：\n src/com/zifangsky/www/config.txt 可修改\n2,服务端先启动,然后等客户端也启动后自动打开聊天界面\n3,聊天界面中,按下ENTER键可发送信息\n","说明:",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == author){
            JOptionPane.showMessageDialog(this,"zifangsky","作者:",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == version){
            JOptionPane.showMessageDialog(this,"V1.0.2","版本号:",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == submit){
            submitMessage();
        }
        else if(e.getSource() == reset){
            edit_JTextArea.setText("");
            edit_JTextArea.requestFocus();  //获得焦点
        }
        else if(e.getSource() == clientIP){
            JOptionPane.showMessageDialog(this,clientIP_String,"客户端IP:",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == serverIP){
            JOptionPane.showMessageDialog(this,serverIP_String,"服务端IP:",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == port){
            JOptionPane.showMessageDialog(this,port_int,"监听端口:",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void disConnect(){
        try {
            br.close();
            bw.close();
            client.close();

        } catch (IOException e1) {
            System.out.println("断开操作失败");
            e1.printStackTrace();
        }
    }


    public void keyPressed(KeyEvent arg0) {
        if(arg0.getKeyCode() == KeyEvent.VK_ENTER){  //按下ENTER键，发送消息
            submitMessage();
        }
    }


    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }


    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {
        new Server();

    }
}