/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.DESKeySpec;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ProgressMonitor;
import javax.swing.Timer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class Editor extends JFrame 
{
static	Image myimage = null ;
 static	int row = 0 ;
 static int col = 0 ;
 	JButton New = new JButton(" New ") ;
	JButton Open = new JButton(" Open ") ;
	JButton Save = new JButton(" Save ") ;
	JButton Find = new JButton(" Find ");
       // JButton Browse = new JButton(" Browser ");
       // JButton AutoSugges = new JButton(" AutoSuggestion ");
	JButton m_btncolor = new JButton("COLOR");
	JButton Image = new JButton("Image");
	static JTextField m_statusbar = new JTextField();
	static JLabel marquee = new JLabel("Jai Shree Ram");
	JToggleButton wrap = new JToggleButton("WRAP");
	JToggleButton bold = new JToggleButton("BOLD");
	JToggleButton italic = new JToggleButton("ITALIC");
	static JTextField hindi = new JTextField("");
	static StringBuffer sb = new StringBuffer("");
	static Connection conn = null ;
	JToggleButton toglebutton = new JToggleButton("Click Here To Change Text Color");
	JPopupMenu pop = new JPopupMenu();
	JDialog m_dlgcolor ;
	static int speed = 4 ;
	static boolean thread = true ;
	static	Timer timer = null ;
	/*static{
		try{
			System.out.println("Ram");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			conn = DriverManager.getConnection("jdbc:odbc:DATA");
			Statement stmt = conn.createStatement();
			String SelectEmployeeSQL =
				"Select  EngW , HinW from Words";
			ResultSet rset = stmt.executeQuery(SelectEmployeeSQL);
			while(rset.next()){
				sb.append(rset.getString(1)).append(" = ").append(rset.getString(2)).append("##");
			}

		}catch(Exception e){
			e.printStackTrace();

		}
	}*/

	

	JPanel[] array = new JPanel[500];
	JPanel m_pnlcolorpanel = new JPanel(new GridLayout(6,45));
	public   ArrayList<TabButton> list = new ArrayList<TabButton>();
	ArrayList<JLabel> lab = new ArrayList<JLabel>();
	ArrayList<String> filepath = new ArrayList<String>();
	static int nooftab = 0 ;
	protected JComboBox m_cbFonts;
	protected JComboBox m_cbSizes;
	private  Container con ;
	protected UndoManager m_undoManager = new UndoManager();
	static int currenttab = 0 ;

	static  JTextArea currenttextarea = new JTextArea();
	JTabbedPane tab = new JTabbedPane(); 
	
	JPanel panel  =new JPanel();
	Clipboard clip = getToolkit().getSystemClipboard(); 
	protected JToolBar m_toolBar = new JToolBar();
	public void addListener(){


		toglebutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(toglebutton.isSelected()) 
					toglebutton.setLabel("Click Here To Change Background Color");
				else
					toglebutton.setLabel("Click Here To Change Text Color");


			}
		});

		tab.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {				
				if(tab.getSelectedIndex()==-1)
					return ;
				currenttab =	tab.getSelectedIndex();
				System.out.println("currenttab = "+currenttab + " " + tab.getTitleAt(tab.getSelectedIndex()));
				currenttextarea =(JTextArea)((JViewport)((JScrollPane)((JPanel)tab.getComponentAt(tab.getSelectedIndex())).getComponent(0)).getComponent(0)).getComponent(0);
				currenttextarea.requestFocus();
				m_statusbar.setText("");
			}
		});	 

		m_pnlcolorpanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if((JPanel)me.getComponent() instanceof JPanel ){
					//System.out.println(   ((JPanel)me.getComponent()).getComponentAt(me.getX(), me.getY()).getBackground());
					if(toglebutton.isSelected())
					{
						currenttextarea.setForeground(   ((JPanel)me.getComponent()).getComponentAt(me.getX(), me.getY()).getBackground());	

					}
					else{
						currenttextarea.setBackground(   ((JPanel)me.getComponent()).getComponentAt(me.getX(), me.getY()).getBackground());
					}}}
		});
		New.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onaddNewTab();

			}
		});
		Open.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onOpen();

			}
		});
		Image.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
		      
			FileDialog fd = new FileDialog(Editor.this, "select Image",FileDialog.LOAD);
				fd.show();
				if (fd.getFile()!=null)
				{
			 String	filename = fd.getDirectory() + fd.getFile();
			myimage =   Toolkit.getDefaultToolkit().createImage(filename);
			      onaddNewTab();      
				}
		}}
		);
		
		Find.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onFind();

			}
		});

		Save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onSave();

			}
		});

		m_btncolor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onSetColor();	
			}
		});

		m_cbSizes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSetBoldOrItalicOrFont();

			}
		}); 

		m_cbFonts.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onSetBoldOrItalicOrFont();
			}
		});
		wrap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onWrap();
			}
		});
		bold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                          //  Font f = new Font("Arial",Font.BOLD,34);
                            
				onSetBoldOrItalicOrFont();
			}
		});
		italic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSetBoldOrItalicOrFont();
			}
		});
	}

	private void onWrap(){

		currenttextarea.setWrapStyleWord(wrap.isSelected());	
		currenttextarea.setLineWrap(wrap.isSelected());
	}
	private void onSetBoldOrItalicOrFont(){
		Font font  = null;
		if(italic.isSelected()&&bold.isSelected())
			font	= new Font(m_cbFonts.getSelectedItem().toString(),Font.BOLD+Font.ITALIC ,new Integer(m_cbSizes.getSelectedItem().toString().trim()).intValue() );
		else if(bold.isSelected())
			font	= new Font(m_cbFonts.getSelectedItem().toString(),Font.BOLD ,new Integer(m_cbSizes.getSelectedItem().toString().trim()).intValue() );
		else if(italic.isSelected())
			font	= new Font(m_cbFonts.getSelectedItem().toString(),Font.ITALIC ,new Integer(m_cbSizes.getSelectedItem().toString().trim()).intValue() );
		else
			font  = new Font(m_cbFonts.getSelectedItem().toString(),Font.PLAIN ,new Integer(m_cbSizes.getSelectedItem().toString().trim()).intValue() );		
		currenttextarea.setFont(font);
	}


	Editor()
	{
	
		
		hindi.setFont( new Font("Kundli",Font.BOLD,22));
		setLayout(new BorderLayout(1,1));
		m_dlgcolor  = new JDialog(this,"Choose Color");
		m_dlgcolor.setLayout(new BorderLayout());
		m_statusbar.setBackground(Color.PINK);
		
		
		for(int i = 0 ; i< 100 ; i++){
			array[i]= new JPanel();
			int r = (int)Math.floor(Math.random() * 256);
			int g = (int)Math.floor(Math.random() * 256);
			int b = (int)Math.floor(Math.random() * 256);
			array[i].setBackground( new Color(r,g,b));
		    m_pnlcolorpanel.add(array[i]);
		}
		pop.add(new JMenuItem("cut"));
		pop.add(new JMenuItem("copy"));
		pop.add(new JMenuItem("paste"));

		m_toolBar.add(New);
		m_toolBar.add(Open);
		m_toolBar.add(Save);
		m_toolBar.add(Find);
           //     m_toolBar.add(Browse);
             //   m_toolBar.add(AutoSugges);
		add(m_toolBar,BorderLayout.NORTH);
		m_dlgcolor.setLocation(500, 50);
		mylistener mylist = new mylistener();
		tab.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		add(tab,BorderLayout.CENTER);
		add(m_statusbar,BorderLayout.SOUTH);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem n = new JMenuItem("New");
		JMenuItem o = new JMenuItem("Open");
		JMenuItem s = new JMenuItem("Save");
		JMenuItem e = new JMenuItem("Exit");
		fileMenu.setMnemonic('F');
		n.addActionListener(new New());
		fileMenu.add(n);
		o.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onOpen();

			}
		});
		fileMenu.add(o);
		GraphicsEnvironment ge = GraphicsEnvironment.
		getLocalGraphicsEnvironment();
		String[] fontNames = ge.getAvailableFontFamilyNames();
		m_toolBar.addSeparator();
		m_cbFonts = new JComboBox(fontNames);
		m_cbFonts.setMaximumSize(m_cbFonts.getPreferredSize());
		m_cbFonts.setEditable(true);
		m_toolBar.add(m_cbFonts);
		m_toolBar.addSeparator();
		m_cbSizes = new JComboBox(new String[] {" 10 ", " 12 ", " 14 ", " 16 " , " 18 ", " 20 ", " 22 ",  " 24 ", " 26 "," 28 ", " 36 ", " 48 ", " 72 "});
		m_cbSizes.setMaximumSize(m_cbSizes.getPreferredSize());
		m_cbSizes.setEditable(true);
		m_cbSizes.setSelectedIndex(9);
		m_toolBar.add(m_cbSizes);
		m_toolBar.addSeparator();
		m_toolBar.add(wrap);
		m_toolBar.add(m_btncolor);
		m_toolBar.add(bold);
		m_toolBar.add(italic);
//		m_toolBar.add(Image);
		wrap.setSelected(true);
		m_toolBar.addSeparator();
		marquee.setFont( new Font("Arial",Font.BOLD,22));
//		m_toolBar.add(marquee);
//		m_toolBar.add(hindi);
		addListener();
		onaddNewTab();
		onWrap();

		s.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				onSave();

			}
		});
		fileMenu.add(s);
		e.addActionListener(new Exit());
		fileMenu.add(e);
		menuBar.add(fileMenu);
		JMenu editMenu = new JMenu("Edit");
		JMenuItem cut = new JMenuItem("Cut");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");
		editMenu.setMnemonic('E');
		cut.addActionListener(new Cut());
		editMenu.add(cut);
		copy.addActionListener(new Copy());
		editMenu.add(copy);
		paste.addActionListener(new Paste());
		editMenu.add(paste);
		menuBar.add(editMenu);

		
		
		
		
		JMenu dictionaryMenu = new JMenu("Dictionary");
		dictionaryMenu.setMnemonic('D');
		JMenuItem  radiobtn0 = new JRadioButtonMenuItem("0x");radiobtn0.addActionListener(new fonListner());
		JMenuItem  radiobtn1 = new JRadioButtonMenuItem("1x");radiobtn1.addActionListener(new fonListner());
		JMenuItem  radiobtn2 = new JRadioButtonMenuItem("2x");radiobtn2.addActionListener(new fonListner());
		JMenuItem  radiobtn3 = new JRadioButtonMenuItem("4x");radiobtn3.addActionListener(new fonListner());
		JMenuItem  radiobtn4 = new JRadioButtonMenuItem("8x");radiobtn4.addActionListener(new fonListner());
		ButtonGroup group = new ButtonGroup();
		group.add(radiobtn0);group.add(radiobtn1);group.add(radiobtn2);group.add(radiobtn3);group.add(radiobtn4);
		dictionaryMenu.add(radiobtn0);dictionaryMenu.add(radiobtn1);dictionaryMenu.add(radiobtn2);dictionaryMenu.add(radiobtn3);dictionaryMenu.add(radiobtn4);
		radiobtn3.setSelected(true);
		setJMenuBar(menuBar);
		addWindowListener(mylist);
		menuBar.add(dictionaryMenu);
	
	JMenu security = new JMenu("Security");
	security.setMnemonic('u');
	JMenuItem encrypt = new JMenuItem("Encrypt");
	JMenuItem decrypt = new JMenuItem("Decrypt");
	security.add(encrypt);
	security.add(decrypt);
	menuBar.add(security);
        JMenu adv = new JMenu("Advance Features");
        JMenuItem browse = new JMenuItem("Browser");
	JMenuItem autosuggest = new JMenuItem("Auto Suggestion");
        JMenuItem tpdf = new JMenuItem("Text to PDF");
        JMenuItem ptdf = new JMenuItem("PDF to Text");
	adv.add(browse);
	adv.add(autosuggest);
        adv.add(tpdf);
        adv.add(ptdf);
        menuBar.add(adv);
        
        browse.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                       MiniBrowser browser = new MiniBrowser();
                      browser.show();
                      
                    }
                });
        autosuggest.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                     new AutoCompleteTest();
                    }
                });
         tpdf.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                     TextToPDFConverter.selectTextFiles();
                    }
                });
          ptdf.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                     PDFToTextConverter.selectPDFFiles();
                    }
                });
    encrypt.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent arg0) {
	onEncrypt();	
	}
});
	decrypt.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			onDecrypt();
		}
	});
             


	}

	
	
	
	class mylistener extends WindowAdapter
	{
		public void windowClosing (WindowEvent e)
		{
			display();
			if(tab.getTabCount()>0)
			{
				onFinish(tab.getSelectedIndex());
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
			else
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		
	}



	class fonListner implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			speed = Integer.parseInt(e.getActionCommand().substring(0, 1));

			timer.stop();timer =null ;
			timer = new Timer(speed*1000, new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Editor.marquee.setText(sb.toString().split("##")[++i].split("=")[0]+" ");
					Editor.hindi.setText(     sb.toString().split("##")[i].split(" = ")[1]);

				}
			});


			if(speed==0)
			{
				timer.stop();
				Editor.marquee.setText("");
				Editor.hindi.setText("");
				return ;
			}
			timer.start();	

		}
	};



	class New implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			onaddNewTab();



		}
	}
	boolean flag ;
	void undo(){
		flag = true ;
		try {
			if(m_undoManager.canUndo())
			{	m_undoManager.undo();System.out.println("undo2");}

			if(currenttextarea.getText().length()==0&&m_undoManager.canUndo()&&flag )
			{
				System.out.println("undo1");
				flag =false ;
				undo();
			}
		}
		catch (CannotRedoException cre) {
			//  cre.printStackTrace(); 
		}


	}
	void redo(){
		System.out.println("redo");
		flag = true ;
		
		try {
			if(m_undoManager.canRedo())
			{
				System.out.println("redo2");
				m_undoManager.redo();
			
			}
			if(currenttextarea.getText().length()==0&&m_undoManager.canRedo()&&flag )
			{
				System.out.println("redo1");
				flag =false ;
				redo();
			}
	
		
		
		}
		catch (CannotRedoException cre) { 
		}


	}

	void display(){

		for(int i = 0 ;i< list.size();i++){
			System.out.println("Tabindex list  i = "+i+" " + list.get(i).tabindex );

		}
		for(int i = 0 ;i< lab.size();i++){
			System.out.println(  "lab = "+lab.get(i).getText());}


		for(int i = 0 ;i< lab.size();i++){
			System.out.print(  "--"+filepath.get(i));}
	}

	void onaddNewTab(){

		JPanel panel = new JPanel(new GridLayout(1,1));
		JTextArea  textarea = new JTextArea();
		 textarea.setTabSize(8);
	 
		JScrollPane jsp = new JScrollPane(textarea);
		textarea.getDocument().addUndoableEditListener(
				new UndoableEditListener() {
					public void undoableEditHappened(UndoableEditEvent e) {
						m_undoManager.addEdit(e.getEdit());
					}
				});


		textarea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
			//		System.out.println("Key code"+ke.getKeyCode()+"  "+KeyStroke.getKeyStrokeForEvent(ke));
				if(ke.getKeyCode()==90)
					undo();
				else	if(ke.getKeyCode()==89)
					redo();
				else	if(KeyStroke.getKeyStrokeForEvent(ke)==KeyStroke.getKeyStroke("ctrl S"))
					onSave();
				else	if(KeyStroke.getKeyStrokeForEvent(ke)==KeyStroke.getKeyStroke("ctrl O"))
					onOpen();
				else	if(KeyStroke.getKeyStrokeForEvent(ke)==KeyStroke.getKeyStroke("ctrl N"))
					onaddNewTab();
				else if(KeyStroke.getKeyStrokeForEvent(ke)==KeyStroke.getKeyStroke("ctrl F"))
					onFind();
				else if(KeyStroke.getKeyStrokeForEvent(ke)==KeyStroke.getKeyStroke("ctrl D"))
					onDelete();
				else if(KeyStroke.getKeyStrokeForEvent(ke)==KeyStroke.getKeyStroke("alt UP"))
					//shiftUp(true);
					
					onPressAlt(true);
				else if(KeyStroke.getKeyStrokeForEvent(ke)==KeyStroke.getKeyStroke("alt DOWN"))
					onPressAlt(false);		
				else if(KeyStroke.getKeyStrokeForEvent(ke)==KeyStroke.getKeyStroke("TAB"))
					onTab();	
				
			
				
				else if(ke.getModifiers()==KeyEvent.ALT_MASK)
				{
					if(ke.getKeyCode()==39&&(tab.getSelectedIndex()<tab.getTabCount()-1))
						tab.setSelectedIndex(tab.getSelectedIndex()+1);
					if(ke.getKeyCode()==37&&tab.getSelectedIndex()>0)
						tab.setSelectedIndex(tab.getSelectedIndex()-1);
				}
			}
		});

		textarea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent ce) {
				 try{
					 JTextArea myta = (JTextArea)(ce.getSource());
					 row = myta.getLineOfOffset(myta.getCaretPosition());
					 col = myta.getCaretPosition() - myta.getLineStartOffset(row);
					 m_statusbar.setText(" "+(row+1)+" : "+col);
				
				 }catch(Exception e){
					 e.printStackTrace();
				 }
			}
		});
		textarea.setCaretColor(Color.WHITE);
		textarea.setBackground(Color.BLUE);
		textarea.setForeground(Color.WHITE);
		panel.add(jsp);

		tab.addTab("Untitled"+(++nooftab) ,panel);
		tab.setSelectedIndex(tab.getTabCount()-1);
		TabButton temp =new TabButton(tab, tab.getTabCount()-1){
			@Override
			public void onClose(int i) {
				onFinish(i);
			}		
		};
		list.add(tab.getTabCount()-1,temp );
		JPanel pan = new JPanel();
		JLabel label = new JLabel(tab.getTitleAt(tab.getTabCount()-1));
		lab.add(label);
		filepath.add(tab.getTitleAt(tab.getTabCount()-1));
		pan.add(label);
		pan.setLayout((new FlowLayout(FlowLayout.LEFT, 0, 0)));
		pan.add(temp);
		pan.setOpaque(false);
		display();
		textarea.requestFocus();
		tab.setTabComponentAt(tab.getTabCount()-1,pan );
		m_cbFonts.setSelectedItem("Arial");
		onSetBoldOrItalicOrFont();
	}



	void onUpdate(int index){
		display();
		list.remove(index);
		lab.remove(index);
		filepath.remove(index);
		for( int i = index ;i < list.size();i++ ){
			--list.get(i).tabindex; 
		}
		System.out.println("=====");
		display();
	}	
	void onFinish(int j){

		System.out.println("onFinish"+j);

		
		Object[] obj = {"Saving File :",lab.get(j).getText() ,""};
		Object stringArray[] = {"No","Yes","Cancel"};

		
		int option = JOptionPane.showOptionDialog(null, obj, "Jai Shree Ram",
		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, stringArray, stringArray[0]);
    
		System.out.println("option"+option);
		if(option==0&&j!=-1)
		{
			
			tab.remove(j);
			onUpdate( j);
			System.out.println("removing index "+j);
		if(j==0)
		nooftab=0;
		}
		
		if(option==1){
			JTextArea jt =	(JTextArea)((JViewport)  ((JScrollPane)((JPanel)tab.getComponentAt(j)).getComponent(0)).getComponent(0)).getComponent(0);	
			onSave();
		}
		if(option==2)
		return ;
	//	if(option==3){
	//	setPassword(j);
	//	tab.remove(j);
	///	onUpdate( j);
	//	System.out.println("removing index "+j);
	//	if(j==0)
	//	nooftab=0;
	//	}
	///	int i = JOptionPane.showConfirmDialog(this, "Do u want save before closing "+lab.get(j).getText(),lab.get(j).getText(),JOptionPane.YES_NO_CANCEL_OPTION); 

		//if(i==0){    			 
			//	if(!((JTextArea)((JPanel)tab.getComponentAt(tab.getSelectedIndex())).getComponent(0)).getText().trim().equals(""))

			//JTextArea jt =	(JTextArea)((JViewport)  ((JScrollPane)((JPanel)tab.getComponentAt(j)).getComponent(0)).getComponent(0)).getComponent(0);		
			//	if(!jt.getText().trim().equals(""))
			//onSave();
		//}
		//	}
	//	if(i!=2&&j!=-1){
	//		tab.remove(j);
	//		onUpdate( j);
	//		System.out.println("removing index "+j);
	//	}
	}

	void setPassword(int tabindex){
	//	System.out.println("on Set Password");
	//	display();
		
//	JPasswordField	pwd = new JPasswordField(25);
		 
	//	if(	JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.OK_CANCEL_OPTION) !=0  ) 
	//	 	return ;
	//	String psd = new String(pwd.getPassword());
	//	System.out.println("passworddis="+psd);
	//	if(psd==null||psd.trim().length()<=0)
	//	{
	//		JOptionPane.showMessageDialog(this, "Invalid Password");
	//		return ;
	//	}	
	//	onSave();
	//	System.out.println("paaaaaaaaaaaath of "+filepath.get(tabindex));
	// Encryptor enkrip = new Encryptor();
//	 enkrip.openFile();
	//	 Encryptor.aksi();
	}
	
	
	
	void onTab(){
		try{
		 int dotLineno =	  currenttextarea.getLineOfOffset(currenttextarea.getCaret().getDot());
		 int markLineno =     currenttextarea.getLineOfOffset(currenttextarea.getCaret().getMark());
	 System.out.println("dotLineno ="+dotLineno +" markLineno ="+ markLineno);
	if(dotLineno!=markLineno){
		
	int smallline = dotLineno<markLineno?dotLineno:markLineno;
	int myMaxLine = dotLineno>markLineno?dotLineno:markLineno;
	System.out.println("smallline="+smallline+"myMaxLine"+myMaxLine);
		 if(markLineno<dotLineno)				//To Select Area
			{
				 currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(markLineno));
				 currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(dotLineno));
			}
			 else if(dotLineno<markLineno){
				 currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(dotLineno));
				 currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(markLineno));
			 }
	String seltext = currenttextarea.getSelectedText();
	String upText ="";
	if(smallline!=0)
	upText = currenttextarea.getText().substring(0,currenttextarea.getLineEndOffset(smallline-1) );
	
	String	downText="";
	if(currenttextarea.getLineEndOffset(myMaxLine) != currenttextarea.getText().length())
	downText = currenttextarea.getText().substring(currenttextarea.getLineStartOffset(myMaxLine+1),currenttextarea.getText().length());
	else
	seltext=seltext+"\n\t";	
	System.out.println("onTab"+seltext);
	System.out.println("downText"+downText);
	String rep = "" +(seltext.replace("\n", "\n\t"));
	System.out.println("rep"+rep.substring(0, rep.lastIndexOf("\n\t"))+"ram");
	StringBuffer sb = new StringBuffer(upText+rep.substring(0, rep.lastIndexOf("\n\t"))+"\n"+downText);
	System.out.println("==>"+sb.toString());
	currenttextarea.setText(sb.toString());
 	currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(smallline));
//	currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(myMaxLine));

	}}catch (Exception e) {
e.printStackTrace();
	}
		
		
	}
		
	
	
	
	
	void onPressAlt(boolean isUp)
	{
		
		StringBuffer text ;
		
		try{ 
		 
		 int dotLineno =	  currenttextarea.getLineOfOffset(currenttextarea.getCaret().getDot());
		 int markLineno =     currenttextarea.getLineOfOffset(currenttextarea.getCaret().getMark());
	 System.out.println("dotLineno ="+dotLineno +" markLineno ="+ markLineno+"   "+isUp);
			 
			
		 		
	 
	 int myLestLine = dotLineno;
		if(dotLineno<markLineno)
		myLestLine=dotLineno;
		else if(markLineno<dotLineno)
		myLestLine=markLineno;
		System.out.println("myLestLine ="+myLestLine);
		if(isUp&&myLestLine-1<0){
		System.out.println("UpLimit");
			return ;
		} 
		
		 
		 
		 
		 if(markLineno<dotLineno)				//To Select Area
			{
				 currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(markLineno));
				 currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(dotLineno));
			
			}
			 else if(dotLineno<markLineno){
				 currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(dotLineno));
				 currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(markLineno));
					
			 }
				 else
			 {
				 currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(dotLineno));
				 currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(dotLineno));
			 }
	
		
	String	 seltext = currenttextarea.getSelectedText();

if(seltext==null||seltext.equals(""))
	return ;
	
	if(!seltext.endsWith("\n"))
		seltext = seltext+"\n";
	
	text =    new StringBuffer(currenttextarea.getText());
		 
			 
			
				
				
		
				
				int myMaxLine = dotLineno;
					if(dotLineno<markLineno)
					myMaxLine=markLineno;
					else if(markLineno<dotLineno)
					myMaxLine=dotLineno;
				
					System.out.println("aa"+currenttextarea.getLineEndOffset(myMaxLine));
				
					System.out.println("bb"+String.valueOf(currenttextarea.getText().length()));
					 if(isUp)
					 {
						 String downText ="";
					if(currenttextarea.getLineEndOffset(myMaxLine) == currenttextarea.getText().length())
						downText = "";
						else					
					 downText = currenttextarea.getText().substring(currenttextarea.getLineStartOffset(myMaxLine+1),currenttextarea.getText().length());
					String upText = currenttextarea.getText().substring(0,currenttextarea.getLineStartOffset(myLestLine-1) );
			String upLineText = currenttextarea.getText().substring(currenttextarea.getLineStartOffset(myLestLine-1),currenttextarea.getLineEndOffset(myLestLine-1));
		
			StringBuffer setText = new StringBuffer();
			 
	setText.append(upText).append(seltext).append(upLineText).append(downText);
	
	System.out.println("uptext="+upText+"\nselText"+seltext+"\nuplineText"+upLineText+"\ndownText"+downText);
	currenttextarea.setText(setText.toString());
	
			
	currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(myLestLine-1));
	currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(myMaxLine-1)-1);
	//currenttextarea.setCaretPosition(currenttextarea.getLineEndOffset(myMaxLine-1));
	System.out.println("aaaaaaaaa"+(myLestLine-1)+  "\nbbbbbbbb"+myMaxLine);	 
	
		 }
					 
 else{
System.out.println("onDownram");
String downText =null;

System.out.println("myMaxLine"+myMaxLine);


if(currenttextarea.getLineEndOffset(myMaxLine) ==currenttextarea.getText().length())
return ;


if(currenttextarea.getLineEndOffset(myMaxLine+1) ==currenttextarea.getText().length())
	downText = null;
	else					
 downText = currenttextarea.getText().substring(currenttextarea.getLineStartOffset(myMaxLine+2),currenttextarea.getText().length());
String upText = currenttextarea.getText().substring(0,currenttextarea.getLineStartOffset(myLestLine) );
String upLineText = currenttextarea.getText().substring(currenttextarea.getLineStartOffset(myMaxLine+1),currenttextarea.getLineEndOffset(myMaxLine+1));

if(!upLineText.endsWith("\n"))
	upLineText = upLineText +"\n";


StringBuffer setText = new StringBuffer();

if(downText==null)
	setText.append(upText).append(upLineText).append(seltext);
else
	setText.append(upText).append(upLineText).append(seltext).append(downText);

System.out.println("muptext="+upText+"\nselText"+seltext+"\nuplineText"+upLineText+"\ndownText"+downText);
currenttextarea.setText(setText.toString());


currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(myLestLine+1));
currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(myMaxLine+1)-1);
//currenttextarea.setCaretPosition(currenttextarea.getLineEndOffset(myMaxLine-1));
System.out.println("aaaaaaaaa"+(myLestLine-1)+  "\nbbbbbbbb"+myMaxLine);	 

				 
						 
						 
						 
					 }			
					 
					 
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e);
		}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 void	shiftUp(boolean up){
	 try {
		 
		 
		 
		 
 int dotLineno =	  currenttextarea.getLineOfOffset(currenttextarea.getCaret().getDot());
 int markLineno =     currenttextarea.getLineOfOffset(currenttextarea.getCaret().getMark());
 System.out.println("dotLineno ="+dotLineno +" markLineno ="+ markLineno+"   "+up);
	 
	
 int myLestLine = dotLineno;
	if(dotLineno<markLineno)
	myLestLine=dotLineno;
	else if(markLineno<dotLineno)
	myLestLine=markLineno;
	System.out.println("myy"+myLestLine);
	if(up&&myLestLine-1<0){
	System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		return ;
	}

	 int new1 = dotLineno;
		if(dotLineno<markLineno)
		new1=markLineno;
		else if(markLineno<dotLineno)
		new1=dotLineno;
	System.out.println("lineno."+new1);
	
 
 
 
 if(markLineno<dotLineno)				//To Select Area
	{
		 currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(markLineno));
		 currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(dotLineno));
	
	}
	 else if(dotLineno<markLineno){
		 currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(dotLineno));
		 currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(markLineno));
			
	 }
		 else
	 {
		 currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(dotLineno));
		 currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(dotLineno));
	 }
	 
	 
	 
	 
	 
		if(currenttextarea.getDocument()!=null){
			
			int start = currenttextarea.getSelectionStart();
		    int end = currenttextarea.getSelectionEnd();
		    System.out.println("\n SelectionStartIndex = "+start+"   "+"\n SelectionEndIndex = "+end);
		    String selectedText = currenttextarea.getSelectedText();
		    String startText = currenttextarea.getText().substring(0,start);
		    String endText = currenttextarea.getText().substring(end,currenttextarea.getText().length());
	    	
		    if(!up)				//If Down
		    {
		    	if(end>=currenttextarea.getText().length())
		    	{
		    		System.out.println("buzzzzzzer");
		    		return;
		    		}
		    }
		    
		    
		    if( selectedText==null)
		    	return ;
		    System.out.println("pppp"+startText+endText);
		    currenttextarea.setText(startText+endText);

	    	if(up){
	    		System.out.println("upoffset"+currenttextarea.getLineStartOffset(myLestLine-1));
		    	currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(myLestLine-1));
		    	System.out.println("start="+currenttextarea.getText().substring(0, currenttextarea.getCaretPosition()));
		    	System.out.println("End"+currenttextarea.getText().substring(currenttextarea.getCaretPosition(),currenttextarea.getText().length()));
		    	if(currenttextarea.getText().substring(currenttextarea.getCaretPosition(),currenttextarea.getText().length())==null)
		    		return ;
	          String stText = currenttextarea.getText().substring(0, currenttextarea.getCaretPosition());
	          String enText = currenttextarea.getText().substring(currenttextarea.getCaretPosition(),currenttextarea.getText().length());
	          
	          if(!selectedText.endsWith("\n"))
		    	selectedText = selectedText+"\n";
	            System.out.println("Final"+currenttextarea.getText().substring(0, currenttextarea.getCaretPosition())+selectedText+currenttextarea.getText().substring(currenttextarea.getCaretPosition(),currenttextarea.getText().length()));
		    	currenttextarea.setText(stText+selectedText+currenttextarea.getText().substring(currenttextarea.getCaretPosition(),currenttextarea.getText().length()));
		    	currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(myLestLine-1));
		    	
		    	
	    	}	else{
	    			System.out.println("new1==>"+new1);
	    			//System.out.println("downoffset==="+currenttextarea.getLineEndOffset(new1));
	    			if(dotLineno==markLineno)
	    			currenttextarea.setCaretPosition(currenttextarea.getLineEndOffset(new1));
	    			else
	    		{
	    				currenttextarea.setCaretPosition(currenttextarea.getLineEndOffset(new1-1));
	    		
	    		}		
	    	//		currenttextarea.setCaretPosition(currenttextarea.getLineEndOffset(new1-1));
		    		System.out.println("startDown="+currenttextarea.getText().substring(0, currenttextarea.getCaretPosition()));
			    	System.out.println("Selected text Down="+selectedText);
		    		System.out.println("End Text Down"+currenttextarea.getText().substring(currenttextarea.getCaretPosition(),currenttextarea.getText().length()));
			    	String stText = currenttextarea.getText().substring(0, currenttextarea.getCaretPosition());
			          String enText = currenttextarea.getText().substring(currenttextarea.getCaretPosition(),currenttextarea.getText().length());
			          if(!stText.endsWith("\n"))
			        	  stText = stText+"\n";
				        
			          currenttextarea.setText(stText+selectedText+currenttextarea.getText().substring(currenttextarea.getCaretPosition(),currenttextarea.getText().length()));
			        
			          
			          currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(new1+1));    	
		    		
	    		
	    			
	    		
	    		
	    	}	    
}
		
	 //currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(row));
} catch (BadLocationException e) {
JOptionPane.showMessageDialog(this, "Exception"+e);
	e.printStackTrace();
}

	 
	
}

	
	private void onDelete(){
		System.out.println("onDelete");
		try {
			 System.out.println("Row = "+row+" Col = "+col);
			 currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(row));
			 currenttextarea.moveCaretPosition(currenttextarea.getLineEndOffset(row));
			int temp = row ;
			// System.out.println(currenttextarea.getSelectedText());
			if(currenttextarea.getDocument()!=null){
			    int start = currenttextarea.getSelectionStart();
			    int end = currenttextarea.getSelectionEnd();
			    String startText = currenttextarea.getText().substring(0,start);
			    String endText = currenttextarea.getText().substring(end,currenttextarea.getText().length());
			    currenttextarea.setText(startText + endText);
			 //   System.out.println("offset"+currenttextarea.getLineStartOffset(temp-1));
			   if(temp==0)
				   return ;
			    currenttextarea.setCaretPosition(currenttextarea.getLineStartOffset(temp-1));
				}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	void onFind()
	{

 JFindReplace jfindReplace=new JFindReplace(this,currenttextarea,currenttextarea.getCaretPosition(),currenttextarea.getSelectedText());
  		
	}

	void onOpen(){
		
		 
		
//		if(JOptionPane.showConfirmDialog(null,"Press Yes to Open Encrypted File","Open..",JOptionPane.YES_NO_OPTION)==0)
//		{
//			
//		//    Decryptor dekrip = new Decryptor();
//		 //   dekrip.openFile();
//		  //  Decryptor dekip = new Decryptor();
//		    
//		    Decryptor.aksi();
//			if(true)
//				return ;
//			
//			
//	 
//			JPasswordField	pwd = new JPasswordField(25);
//			FileDialog fd = new FileDialog(Editor.this, "select File",FileDialog.LOAD);
//			fd.show();
//			
//			if(fd.getFile()==null)
//			return ;
//			
//			if(	JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.OK_CANCEL_OPTION) !=0  ) 
//			 	return ;
//			
//			
//			String psd = new String(pwd.getPassword());
//			System.out.println("passworddis="+psd);
//			if(psd==null||psd.trim().length()<=0)
//			{
//				JOptionPane.showMessageDialog(this, "Invalid Password");
//				return ;
//			}
//			if (fd.getFile()!=null)
//			{
//		//		String	filename = fd.getDirectory() + fd.getFile();
//		///		Decryptor dekrip = new Decryptor(filename,psd);
//		//		Decryptor.aksi();
//			//	ReadFile(fd.getDirectory()+"temp.txt");
//			}
//			return ;
//		}
		
		FileDialog fd = new FileDialog(Editor.this, "select File",FileDialog.LOAD);
		fd.show();
		if (fd.getFile()!=null)
		{
			String	filename = fd.getDirectory() + fd.getFile();
			ReadFile(filename );
		}
	}


	void onSetColor(){
		JPanel m_pnlbutton = new JPanel(new BorderLayout());

		m_pnlbutton.add(toglebutton,BorderLayout.CENTER);



		m_dlgcolor.add(m_pnlbutton,BorderLayout.NORTH);
		m_dlgcolor.add(m_pnlcolorpanel, BorderLayout.CENTER);
		currenttextarea.setWrapStyleWord(m_btncolor.isSelected());
		m_dlgcolor.setSize(350,200);
		m_dlgcolor.show();
	}
	
	
	
	 void onEncrypt(){
			System.out.println("onEncrypt");
			 Encryptor enkrip = new Encryptor();
			// enkrip.openFile();
				 Encryptor.aksi();
		}
	void onDecrypt(){
		System.out.println("onDecrypt");
	    Decryptor dekrip = new Decryptor();
	    //dekrip.openFile();
	    Decryptor.aksi();
	}
	 
	 
	 
	void onSave(){
		String filename = filepath.get(tab.getSelectedIndex());

		//	JOptionPane.showMessageDialog(null, "Saving File "+ filename+" path "+ new File(filename).getAbsolutePath()+ " isExists "+new File(filename).exists());
		if(!new File(filename).exists())
		{
			FileDialog fd = new FileDialog(Editor.this,"Save File",FileDialog.SAVE);
			fd.show();
			if (fd.getFile()!=null)
			{
				filename = fd.getDirectory() + fd.getFile();
			}
			else return ;
		}
		try
		{
			DataOutputStream d = new DataOutputStream(new FileOutputStream(filename));
			JTextArea jt =	(JTextArea)((JViewport)  ((JScrollPane)((JPanel)tab.getComponentAt(tab.getSelectedIndex())).getComponent(0)).getComponent(0)).getComponent(0);
			String line = jt.getText();
			BufferedReader br = new BufferedReader(new StringReader(line));
			while((line = br.readLine())!=null)
			{
				d.writeBytes(line + "\r\n");
			}
			d.close();
			filepath.set(tab.getSelectedIndex(),filename);
			//	lab.get(tab.getSelectedIndex()).setText(file);

			String str[]= filename.split(File.separator+File.separator);
			lab.get(tab.getSelectedIndex()).setText(str[str.length-1]);
			//JOptionPane.showMessageDialog(null,"Saving File"+str[str.length-1]+ new File(filename).getAbsolutePath() );


			//	tab.setTitleAt(tab.getSelectedIndex(), file);
			//	tab.setTabComponentAt(tab.getSelectedIndex(),new ButtonTabComponent(tab,tab.getSelectedIndex()) );
			//	((TabButton)(list.get(tab.getSelectedIndex()))).setText("OM");
			//	tab.setTitleAt(tab.getSelectedIndex(), "ram");
			//	System.out.println("sll"+tab.getSelectedIndex()+file);
			display();
		}
		catch(Exception ex)
		{

			System.out.println("File not found"+ex);
			JOptionPane.showMessageDialog(null, "Exception on Save "+ex);
			ex.printStackTrace();
		}

	}


	@Override
	public void setTitle(String arg0) {
		super.setTitle("");
	}
	class Exit implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	void ReadFile(String filename)
	{
		BufferedReader d;
		StringBuffer sb = new StringBuffer();
		try
		{
			d = new BufferedReader(new FileReader(filename));
			String line;
			while((line=d.readLine())!=null)
				sb.append(line + "\n");


			//tab.setTitleAt(tab.getSelectedIndex(), fname);
			//	tab.setTabComponentAt(tab.getSelectedIndex(),new ButtonTabComponent(tab,tab.getSelectedIndex()) );
			System.out.println(filename);
			if(tab.getTabCount()==0)
				onaddNewTab();
			filepath.set(tab.getSelectedIndex(),filename);
			String str[]= filename.split(File.separator+File.separator);
			lab.get(tab.getSelectedIndex()).setText(str[str.length-1]);
			currenttextarea.setText(sb.toString());
			d.close();
		}
		catch(Exception fe)
		{
			System.out.println("Exception in Reading File"+fe);
		}

	}

	class Cut implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JTextArea tx = ((JTextArea)((JPanel)tab.getComponentAt(tab.getSelectedIndex())).getComponent(0));
			String sel = tx.getSelectedText();
			StringSelection ss = new StringSelection(sel);
			clip.setContents(ss,ss);
			tx.replaceRange(" ",tx.getSelectionStart(),tx.getSelectionEnd());
		}
	}

	class Copy implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{



			String sel = ((JTextArea)((JPanel)tab.getComponentAt(tab.getSelectedIndex())).getComponent(0)).getSelectedText();
			StringSelection clipString = new StringSelection(sel);
			clip.setContents(clipString,clipString);
		}
	}

	class Paste implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Transferable cliptran = clip.getContents(Editor.this);
			try
			{
				JTextArea tx = ((JTextArea)((JPanel)tab.getComponentAt(tab.getSelectedIndex())).getComponent(0));
				String sel = (String) cliptran.getTransferData(DataFlavor.stringFlavor);
				tx.replaceRange(sel,tx.getSelectionStart(),tx.getSelectionEnd());
			}
			catch(Exception exc)
			{
				System.out.println("not string flavour");
			}
		}
	}

	static int i = 0 ;
	static Font font = new Font("Kundli", Font.BOLD, 14 );
	public static void main(String args[])
	{
		final   JFrame f = new Editor();

		f.setExtendedState(Frame.MAXIMIZED_BOTH);


f.addWindowListener(new WindowAdapter() {
	@Override
	public void windowClosing(WindowEvent arg0) {
		super.windowClosing(arg0);
		System.out.println("closing");

		return ;
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		super.windowDeactivated(arg0);
 
	}
});
		f.setVisible(true);
		f.setTitle("");
		f.show();
		timer = new Timer(speed*1000, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Editor.marquee.setText(sb.toString().split("##")[++i].split("=")[0]+" ");
				Editor.hindi.setText(     sb.toString().split("##")[i].split(" = ")[1]);
			}
		});
		timer.start();
		currenttextarea.requestFocus();
	}

	static	String parse(String find){
		if(find!=null&&find.equals(""))
			return null ;
		try{
			FileInputStream fis = new FileInputStream("c:\\MohitEditor.xml");
			int size = fis.available();
			byte[] data = new byte[size];
			fis.read(data);
			String str = new String(data);
			InputStream m_inputStreamXMLData = new ByteArrayInputStream(str.getBytes("UTF-8"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setIgnoringComments(true);
			dbf.setIgnoringElementContentWhitespace(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(m_inputStreamXMLData);
			Node node = doc.getDocumentElement();
			NodeList n =     node.getChildNodes();  
			boolean found  = false ;	    
			int ii = 0 ;
			for (; ii < n.getLength(); ii++) {
				System.out.println(	n.item(ii).getNodeName()+"    "+find.toUpperCase().substring(0,1));
				if(n.item(ii).getNodeName().equals( find.toUpperCase().substring(0,1)))
				{
					found =true ; break ;
				}	
			}
			Node A =	n.item(ii);
			NodeList nA = 		A.getChildNodes();
			for (int i = 0; i < nA.getLength(); i++) {
				System.out.println(nA.item(i).getAttributes().getNamedItem("key").getTextContent());	
				if(find.equalsIgnoreCase(nA.item(i).getAttributes().getNamedItem("key").getTextContent() )          )
					return  nA.item(i).getTextContent();
			}
		}catch(Exception e){

			e.printStackTrace();
		}
		return null ;
	}}
  

class TabButton extends JButton implements ActionListener {
	JTabbedPane pane ;
	int tabindex = -1 ;

	int   getTabindex (){


		return tabindex ;
	}

	public TabButton(JTabbedPane tab , int index) {
		tabindex = index ;
		pane =tab ;
		int size = 17;
		setPreferredSize(new Dimension(size, size));
		setToolTipText("close this tab");
		setUI(new BasicButtonUI());
		setContentAreaFilled(false);
		setFocusable(false);
		setBorder(BorderFactory.createEtchedBorder()); 
		setBorderPainted(false);
		addActionListener(this);
	}

	public void onClose(int i ){ }


	public void actionPerformed(ActionEvent e) {

		Component com[] = ((TabButton)e.getSource()).getComponents();
		System.out.println("Action Perform size ="+com.length);

		for(int i = 0 ; i < com.length;i++)
			System.out.println(com[i].getClass());
		onClose(((TabButton)e.getSource()).tabindex);
	}

	public void updateUI() {
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		setBackground(Color.pink);
		if (getModel().isPressed()) {
			g2.translate(1, 1);
		}
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		if (getModel().isRollover()) {
			g2.setColor(Color.MAGENTA);
		}
		int delta = 6;
		g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
		g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
		g2.dispose();
	}
	private final static MouseListener buttonMouseListener = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}

	};
}

class Encryptor   {
	public static void aksi()
    {
	    fd= new FileDialog( dummyFrame, "Browse the file to be encrypted");
	    fd.setVisible(true);
	    if(fd==null||fd.getDirectory()==null||fd.getFile()==null)
	    return ;
	    inputFile=fd.getDirectory()+fd.getFile();
	    if(inputFile==null||inputFile.trim().length()<=0)
	    return ;	
	    matchPassword();
	    createKey();
    }
		
	public static void matchPassword()
	{
			pwd1 = new JPasswordField(25);
			if(0!= JOptionPane.showConfirmDialog(null, pwd1,"Enter Password",JOptionPane.OK_CANCEL_OPTION)){
				return ;
			} 
			outputFile = JOptionPane.showInputDialog(dummyFrame,"Enter name of the output encrypted file");
			if(outputFile==null||outputFile.equals(""))
			return ;
			passWord1=new String(pwd1.getPassword());
			manageKeystrengthMethod();
			passByte=passWord1.getBytes();
			createExtensionForOutputFile();
	}
	
	
	public static void  createExtensionForOutputFile()
	{
		inputfileName=fd.getFile();
		int i=0;
	i=inputfileName.lastIndexOf(".");
	System.out.println("fd"+fd.getDirectory());
	outputFile=  fd.getDirectory() +outputFile+inputfileName.substring(i,inputfileName.length());
	System.out.println("Path of Ecrypted output file==>"+outputFile);
	}
	
	public static void createKey()
	{

		try
		{
			keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec dspec= new DESKeySpec(passByte);
			key = keyFactory.generateSecret(dspec);
			//System.out.println("key is :"+key);	
			createCipher();
			encryptFile();
			}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}
	public static void manageKeystrengthMethod()
	{
		if(passWord1.length()<8)
		{
			int counter=passWord1.length();
			while(counter<8)
			{
				passWord1+='@';
				counter++;
			}
		}
	}
	public  static void  createCipher()
	{
		try
		{
		cipher=Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE,key);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public static void  encryptFile() throws IOException, ShortBufferException, IllegalBlockSizeException, BadPaddingException
	{
		
		int blockSize = cipher.getBlockSize();
	    int outputSize = cipher.getOutputSize(blockSize);
	    byte[] inBytes = new byte[blockSize];
	    byte[] outBytes = new byte[outputSize];
	    in= new FileInputStream(inputFile);
	    BufferedInputStream inStream = new BufferedInputStream(in);
	    inputFileLength=in.available();
	    System.out.println("outputFile"+fd.getDirectory()+outputFile);
	    out=new FileOutputStream(outputFile);
	    int inLength = 0;;
	    boolean more = true;
	    
	    while (more)
	      {
	         inLength = inStream.read(inBytes);
	         if (inLength == blockSize)
	         {
	            int outLength 
	               = cipher.update(inBytes, 0, blockSize, outBytes);
	            out.write(outBytes, 0, outLength);
	             
	         }
	         else more = false;         
	      }
	      if (inLength > 0)
	         outBytes = cipher.doFinal(inBytes, 0, inLength);
	      else
	         outBytes = cipher.doFinal();
             JOptionPane.showMessageDialog(dummyFrame, "Encrypted Succesfully");
	      System.out.println("sa"+outBytes.length);
	      out.write(outBytes);
		
	}


	
	
	private static JFrame dummyFrame = new JFrame();
	private static FileDialog fd;
	public static String inputFile;
	private static String passWord1;
	private static String passWord2;
	private  static byte[] passByte;
	private  static SecretKeyFactory keyFactory;
	private  static Key key;
	private static JPasswordField pwd1;
	private static JPasswordField pwd2;
	private static Cipher cipher;
	private static String outputFile;
	private static InputStream in;
	private static OutputStream out;
	private static ProgressMonitor pMonitor;
	private static int inputFileLength;
	private static String inputfileName;

	
}
















///////////////////////////////
 
 class Decryptor {
	    
		public static void aksi()
	    {
			
		    fd= new FileDialog( dummyFrame, "Browse the file to be decrypted");
		    fd.setVisible(true);
		    if(fd==null||fd.getDirectory()==null||fd.getFile()==null)
		    return ;
		    inputFile=fd.getDirectory()+fd.getFile();
			if(inputFile==null||inputFile.trim().length()<=0)
			return ;
			matchPassword();
			createKey();
	        }

		
		private static void matchPassword()
		{
				pwd1 = new JPasswordField(25);
				if(0 !=JOptionPane.showConfirmDialog(null, pwd1,"Enter Password",JOptionPane.OK_CANCEL_OPTION))
			{
				return ;
				
			} 
				outputFile = JOptionPane.showInputDialog(dummyFrame,"Enter name of the output decrypted file");
				passWord1=new String(pwd1.getPassword());
				
				
				manageKeystrengthMethod();
				passByte=passWord1.getBytes();
				createExtensionForOutputFile();
			}
		
		private static void  createExtensionForOutputFile()
		{
			inputfileName=fd.getFile();
			int i=0;
			//System.out.println("input file is "+inputfileName);		
		//System.out.println("extensuon  is :	"+inputfileName.indexOf("."));	
		i=inputfileName.lastIndexOf(".");
		
		outputFile=fd.getDirectory()+outputFile+inputfileName.substring(i,inputfileName.length());
		System.out.println("Decryption outputFile==>"+outputFile);
		}
		
		
		private static void createKey()
		{

			try
			{
				keyFactory = SecretKeyFactory.getInstance("DES");
				DESKeySpec dspec= new DESKeySpec(passByte);
				key = keyFactory.generateSecret(dspec);
				 	
				createCipher();
				decryptFile();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		private static void manageKeystrengthMethod()
		{
			if(passWord1.length()<8)
			{
				int counter=passWord1.length();
				//System.out.println("length is"+passWord1.length());
				while(counter<8)
				{
					passWord1+='@';
					counter++;
				}
				//System.out.println("length is"+passWord1.length()+  "Password1 is:"+passWord1);
			}
		}
		private  static void  createCipher()
		{
			try
			{
			cipher=Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE,key);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		private static void  decryptFile() throws IOException, ShortBufferException, IllegalBlockSizeException, BadPaddingException
		{
			
			int blockSize = cipher.getBlockSize();
		    int outputSize = cipher.getOutputSize(blockSize);
		    byte[] inBytes = new byte[blockSize];
		    byte[] outBytes = new byte[outputSize];
		    System.out.println("inputFile = "+inputFile);
		    in= new FileInputStream(inputFile);
		//    out=new FileOutputStream("D:\\PBEDes\\dist\\tteemmpp.txt");
		    out=new FileOutputStream(outputFile);
		 //   ProgressMonitorInputStream progressIn = new ProgressMonitorInputStream(dummyFrame,"Decrypting file...",in);
		    
		    BufferedInputStream inStream = new BufferedInputStream(in);
		    int inLength = 0;;
		    boolean more = true;
		    while (more)
		      {
		         inLength = inStream.read(inBytes);
		         if (inLength == blockSize)
		         {
		            int outLength 
		               = cipher.update(inBytes, 0, blockSize, outBytes);
		            out.write(outBytes, 0, outLength);
		           // System.out.println(outLength);
		         }
		         else more = false;         
		      }
		      if (inLength > 0)
		         outBytes = cipher.doFinal(inBytes, 0, inLength);
		      else
		         outBytes = cipher.doFinal();
	            JOptionPane.showMessageDialog(dummyFrame, "Decripted Succesfully");
		      System.out.println("Decrypt byte"+outBytes.length);
		      out.write(outBytes);
			
		      out.close();
		}
		private static JFrame dummyFrame = new JFrame();
		private static FileDialog fd;
		public static String inputFile;
		private static String passWord1;
		private static String passWord2;
		private static byte[] passByte; 
		private static SecretKeyFactory keyFactory;
		private static Key key;
		private static JPasswordField pwd1;
		private static JPasswordField pwd2;
		private static Cipher cipher;
		private static String outputFile;
		private static InputStream in;
		private static OutputStream out;
		private static String inputfileName;


}
 
 
 
 
 

   class JFindReplace extends JDialog implements ActionListener{

 	private JTextField m_txtFind;
 	private JTextField m_txtReplace;
 	private JButton m_btnFind;
 	private JButton m_btnReplace;
 	private JButton m_btnReplaceAll;
 	private JButton m_btnClose;
 	private JLabel m_lblFind,m_lblReplace;
 	private JCheckBox m_chkMatchCase,m_chkMatchEntireWordOnly;
 	private JCheckBox m_chkSearchBackWards,m_chkWrapAround;
 	private JTextArea currenttextarea;
 	private Integer m_intPosition=null;
 	final  Color  HILIT_COLOR = Color.LIGHT_GRAY;
	final Highlighter hiliter= new DefaultHighlighter();;
	final Highlighter.HighlightPainter painter= new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);
	private JLabel m_lblresult ;
 	
 	/**
 	 * @param args
 	 */

 	public JFindReplace(Editor editor , JTextArea m_Text,Integer pos,String strFind){
 		super(editor); 
 		currenttextarea=m_Text;
 		 m_intPosition =pos;
  		setBounds(100, 100,530,280);
 		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 		setTitle(" Find and Replace");
 		setLocationRelativeTo(editor);
 		initComponents();
 		show();
 	}

 	public void actionPerformed(ActionEvent e) {
 		// TODO Auto-generated method stub
 		if(e.getSource()==m_btnFind)
 			onFind(m_intPosition.intValue() );
 		else if(e.getSource()==m_btnClose)
 			onClose();
 	}

 	private void onFind( int pos ){
 		String find = m_txtFind.getText();
 		boolean found = false ;
		if(find==null||find.equals(""))
			return ;
		System.out.println("pos"+pos);
		System.out.println("find ="+find);


		currenttextarea.setHighlighter(hiliter);
		hiliter.removeAllHighlights();
		
		if(m_chkMatchCase.isSelected()){
		String content = currenttextarea.getText();
		int index = content.indexOf(find, pos);
		if(index>=0){
			int end = index + find.length();
			try {
				hiliter.addHighlight(index, end, painter);
			m_intPosition=end;
			found =true ;
			} catch (BadLocationException e) {
				e.printStackTrace();
			}

		}else {
			hiliter.removeAllHighlights();
			if(!found)
			{
				m_lblresult.setText("Not Found");
				if(m_chkWrapAround.isSelected())
				{
				m_intPosition=0;
				m_lblresult.setText("");
			//	onFind(m_intPosition);
				}
			}
		
		
		}
		}else{
			String content = currenttextarea.getText();
			int index = content.indexOf(find.toLowerCase(), pos);
			if(index>=0){
				int end = index + find.length();
				try {
					hiliter.addHighlight(index, end, painter);
				m_intPosition=end;
				found =true ;
				} catch (BadLocationException e) {
					e.printStackTrace();
				}

			}else {
				hiliter.removeAllHighlights();
			}
			if(!found){
			int index2 = content.indexOf(find.toUpperCase(), pos);
			if(index2>=0){
				int end = index2 + find.length();
				try {
					hiliter.addHighlight(index2, end, painter);
				m_intPosition=end;
				found =true ;
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
			else {
				hiliter.removeAllHighlights();
			}}
				if(!found)
					
				{
					m_lblresult.setText("Not Found");
					if(m_chkWrapAround.isSelected())
					{
					m_intPosition=0;
					m_lblresult.setText("");
				//	onFind(m_intPosition);
					}
				}
			
			
			}

 	}

 	private void onClose(){
 		dispose();
 	}

 	private void initComponents(){
 		getContentPane().setLayout(null);

 		m_lblFind=new JLabel("Search for");
 		m_lblFind.setBounds(10, 10, 100, 20);
 		add(m_lblFind);

 		m_txtFind=new JTextField();
 		m_txtFind.setBounds(100, 10, 300, 20);
 		add(m_txtFind);

 		m_lblReplace=new JLabel("Replace with");
 		m_lblReplace.setBounds(10, 40, 100, 20);
 		add(m_lblReplace);

 		m_txtReplace=new JTextField();
 		m_txtReplace.setBounds(100, 40, 300, 20);
 		add(m_txtReplace);

 		m_chkMatchCase=new JCheckBox("Match case");
 		m_chkMatchCase.setBounds(10, 80,100, 20);
 		m_chkMatchCase.addActionListener(this);
 		add(m_chkMatchCase);

 		m_lblresult= new JLabel("Result");
 		m_lblresult.setBounds(300, 80,100, 20);
 		add(m_lblresult);
 		
 		m_chkMatchEntireWordOnly=new JCheckBox("Match entire word only");
 		m_chkMatchEntireWordOnly.setBounds(10, 110,170, 20);
 		m_chkMatchEntireWordOnly.addActionListener(this);
 		add(m_chkMatchEntireWordOnly);

 		m_chkSearchBackWards=new JCheckBox("Search backwards");
 		m_chkSearchBackWards.setBounds(10, 140,140, 20);
 		m_chkSearchBackWards.addActionListener(this);
 		add(m_chkSearchBackWards);

 		m_chkWrapAround=new JCheckBox("Wrap around");
 		m_chkWrapAround.setBounds(10, 170, 110, 20);
 		m_chkWrapAround.addActionListener(this);
 		add(m_chkWrapAround);

 		m_btnFind=new JButton("Find");
 		m_btnFind.setBounds(10,220,110, 20);
 		m_btnFind.addActionListener(this);
 		add(m_btnFind);

 		m_btnReplace=new JButton("Replace");
 		m_btnReplace.setBounds(140,220,110, 20);
 		m_btnReplace.addActionListener(this);
 		add(m_btnReplace);

 		m_btnReplaceAll=new JButton("Replace All");
 		m_btnReplaceAll.setBounds(270,220,110, 20);
 		m_btnReplaceAll.addActionListener(this);
 		add(m_btnReplaceAll);

 		m_btnClose=new JButton("Close");
 		m_btnClose.setBounds(400,220,110, 20);
 		m_btnClose.addActionListener(this);
 		add(m_btnClose);
 	}
 }
 