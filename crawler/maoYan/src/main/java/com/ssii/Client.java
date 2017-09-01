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
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener, KeyListener{
    /**
     * @author zifangksy
     * @date 2015-05-05
     * @version 1.0.0
     * @Default encoding UTF-8
     *
     * 更新：
     * 	1,将原本设计的服务端多线程改成了单线程（主要是多线程在这儿没什么实际意义）
     * 	2,增加了保存有服务端IP和端口的配置文件config.txt
     * 	3,增加了键盘监听，按下ENTER键可以发送信息
     * 	4,修改了一部分细节和bug
     * @date 2015-05-09
     * @version 1.0.2
     * */
    private static final long serialVersionUID = 1L;
    JPanel main_JPanel,button_JPanel;
    JScrollPane message_JScrollPane,edit_JScrollPane;
    JTextArea message_JTextArea,edit_JTextArea;
    JButton submit,reset;

    JMenuBar jMenuBar;
    JMenu conn_JMenu,help_JMenu;
    JMenuItem exit,hostIP,serverIP,port,instructions,author,version;

    Socket client;

    InetAddress hostIP_String;  //客户端IP
    String serverIP_String = "127.0.0.1";  //默认服务端IP
    int port_int = 20015;  //默认端口
    boolean connected = false;  //是否与服务端相连
    BufferedReader br;
    BufferedWriter bw;
    InputStream data_from_server;
    OutputStream data_to_server;

    String data_from_server_String = "",data_edit_JTA = "";  //显示框和输入框的内容

    public Client(){
        super("Consultation System Client by zifangsky");
        setSize(350,650);
        setLocation(400, 70);

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
        message_JTextArea.setWrapStyleWord(true);  //激活自动换行功能
        edit_JTextArea.setWrapStyleWord(true);

        button_JPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        button_JPanel.add(submit);
        button_JPanel.add(reset);

        //菜单
        jMenuBar = new JMenuBar();
        conn_JMenu = new JMenu("连接");
        help_JMenu = new JMenu("帮助");
        exit = new JMenuItem("退出");
        hostIP = new JMenuItem("主机IP");
        serverIP = new JMenuItem("服务端IP");
        port = new JMenuItem("端口号");
        instructions = new JMenuItem("说明");
        author = new JMenuItem("作者");
        version = new JMenuItem("版本号");
        jMenuBar.add(conn_JMenu);
        jMenuBar.add(help_JMenu);
        conn_JMenu.add(exit);
        help_JMenu.add(hostIP);
        help_JMenu.add(serverIP);
        help_JMenu.add(port);
        help_JMenu.add(instructions);
        help_JMenu.add(author);
        help_JMenu.add(version);

        add(main_JPanel);
        setJMenuBar(jMenuBar);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //注册点击事件
        submit.addActionListener(this);
        reset.addActionListener(this);
        exit.addActionListener(this);
        hostIP.addActionListener(this);
        serverIP.addActionListener(this);
        port.addActionListener(this);
        instructions.addActionListener(this);
        author.addActionListener(this);
        version.addActionListener(this);

        //增加键盘事件
        edit_JTextArea.addKeyListener(this);

        try {
            hostIP_String = InetAddress.getLocalHost();  //得到客户端的IP
        } catch (UnknownHostException e) {
            System.out.println("获取客户端IP失败");
            e.printStackTrace();
        }

        try {
            //从配置文件config.txt中读取服务端IP和端口
            BufferedReader readconfig_br = new BufferedReader(new FileReader(new File("D:/Documents/Downloads/Consultation-System-master/Consultation-System-master/Consultation System Client/src/com/zifangsky/www/config.txt")));
            String temp;
            //读取服务端IP
            temp = readconfig_br.readLine();  //第一行不要
            if((temp = readconfig_br.readLine().trim()) != null){
                serverIP_String = temp;
            }
            //读取端口
            temp = readconfig_br.readLine();  //第三行不要
            if((temp = readconfig_br.readLine().trim()) != null){
                port_int = Integer.parseInt(temp);
            }
            readconfig_br.close();
            client = new Socket(serverIP_String,port_int);
            connected = true;  //已连接
            //从服务端接收数据
            data_from_server = client.getInputStream();
            br = new BufferedReader(new InputStreamReader(data_from_server));

            //向服务端发送数据
            data_to_server = client.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(data_to_server));

            while((data_from_server_String = br.readLine()) != null){
                //去掉字符串末尾的\r\n
                String test = data_from_server_String.substring(0,data_from_server_String.length());
                if(test.equals("") == false){
                    message_JTextArea.append("Server["+serverIP_String+"]:\n"+data_from_server_String + "\n\n");
                    //显示最新消息
                    message_JTextArea.selectAll();
                    message_JTextArea.setCaretPosition(message_JTextArea.getSelectionEnd());
                }
            }
        } catch (IOException ee) {
            JOptionPane.showMessageDialog(this,"未搜索到服务端，请重试","提示:",JOptionPane.ERROR_MESSAGE);
            ee.printStackTrace();
        }

    }

    //将发送信息的操作抽象出来
    private void submitMessage() {
        if(connected == false){
            JOptionPane.showMessageDialog(this,"已经与服务端连接断开","提示:",JOptionPane.ERROR_MESSAGE);
        }
        else{
            data_edit_JTA = edit_JTextArea.getText().trim();
            try {
                if(data_edit_JTA.equals("") == false){
                    if(data_edit_JTA.equals("q")){
                        bw.write("q"+"\r\n\r\n");
                        bw.flush();
                        br.close();
                        bw.close();
                        System.exit(0);
                    }
                    else if(data_edit_JTA.equals("qq")){
                        bw.write("qq"+"\r\n\r\n");
                        bw.flush();
                        br.close();
                        bw.close();
                        System.exit(0);
                    }
                    else{
                        bw.write(data_edit_JTA+"\r\n\r\n");  //加入“\r\n\r\n”，可以使服务端程序认为HTTP头已经结束，可以处理了
                        bw.flush();
                        message_JTextArea.append("Client["+hostIP_String.getHostAddress()+"]:\n"+data_edit_JTA+"\n\n");

                        //显示最新消息
                        message_JTextArea.selectAll();
                        message_JTextArea.setCaretPosition(message_JTextArea.getSelectionEnd());

                        edit_JTextArea.setText("");  //提交后编辑框设空
                    }
                }
                edit_JTextArea.requestFocus();  //获得焦点
            } catch (IOException e1) {
                System.out.println("消息发送失败");
                e1.printStackTrace();
            }

        }

    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            submitMessage();
        }
        else if(e.getSource() == reset){
            edit_JTextArea.setText("");
            edit_JTextArea.requestFocus();  //获得焦点
        }
        else if(e.getSource() == exit){
            if(connected){
                try {
                    bw.write("qq"+"\r\n\r\n");
                    bw.flush();
                    br.close();
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            System.exit(0);
        }
        else if(e.getSource() == hostIP){
            JOptionPane.showMessageDialog(this,hostIP_String.getHostAddress(),"客户端IP:",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == serverIP){
            JOptionPane.showMessageDialog(this,serverIP_String,"服务端IP:",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == port){
            JOptionPane.showMessageDialog(this,port_int,"监听端口:",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == instructions){
            JOptionPane.showMessageDialog(this,"1,程序所用服务端IP和端口号保存在配置文件：\n src/com/zifangsky/www/config.txt 可修改\n2,服务端先启动,然后等客户端也启动后自动打开聊天界面\n3,聊天界面中,按下ENTER键可发送信息\n","说明:",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == author){
            JOptionPane.showMessageDialog(this,"zifangsky","作者:",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == version){
            JOptionPane.showMessageDialog(this,"V1.0.2","版本号:",JOptionPane.INFORMATION_MESSAGE);
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
        new Client();

    }
}
