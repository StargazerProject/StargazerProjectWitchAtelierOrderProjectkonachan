package com.pisual.liliaui.ui;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;


public class ConsoleTextArea extends JTextArea {
	private static ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/pisualcells"+ "/ui/pisualcellssystembigcgcells.png");
	
	 public ConsoleTextArea(InputStream[] inStreams) {
	        for(int i = 0; i < inStreams.length; ++i)
	            startConsoleReaderThread(inStreams[i]);
	    }
	 
	    public ConsoleTextArea() throws IOException {
	        final LoopedStreams ls = new LoopedStreams();
	        PrintStream ps = new PrintStream(ls.getOutputStream());
	        System.setOut(ps);
	        System.setErr(ps);
	        startConsoleReaderThread(ls.getInputStream());
			this.setOpaque(false);
			this.setFont(new Font("思源黑體", 0, 13));  
			this.setForeground(Color.black);
	    }
	    
	    private void startConsoleReaderThread(InputStream inStream) {
	            final BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
	            new Thread(new Runnable() {
	                public void run() {
	                    StringBuffer sb = new StringBuffer();
	                    try {
	                        String s;
	                        Document doc = getDocument();
	                        while((s = br.readLine()) != null) {
	                           	/**判断最大长度 清空**/
	                        	if(getText().length()>50000)
	                        	{
	                        		setText("");
	                        	}
	                            boolean caretAtEnd = false;
	                            caretAtEnd = getCaretPosition() == doc.getLength() ?
	                                true : false;
	                            sb.setLength(0);
	                            int lenght = s.length();
	                            if(lenght>90)
	                            {
	                            	String tempSOne = s.substring(0, 90);
	                            	String tempSTwo = s.substring(90, lenght);
	                            append(sb.append(" ").append(tempSOne).append('\n').toString());
	                            sb = new StringBuffer();
	                            	append(sb.append(" ").append(tempSTwo).append('\n').toString());
	                            }
	                            else
	                            {
		                        append(sb.append(" ").append(s).append('\n').toString());
	                            }

	                            if(caretAtEnd)
	                            setCaretPosition(doc.getLength());
	                        }
	                    }
	                    catch(IOException e) {
	                    	System.err.println(e.getMessage());
	                    }
	                }
	            }).start();
	        }
}
