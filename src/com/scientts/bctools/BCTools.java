package com.scientts.bctools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.scientts.bctools.parsers.CheckEventParser;
import com.scientts.bctools.parsers.CommandParser;
import com.scientts.bctools.parsers.FinishChipParser;
import com.scientts.bctools.parsers.GetCardParser;
import com.scientts.bctools.parsers.GoOnChipParser;
import com.scientts.bctools.parsers.TabelaCertificadosParser;
import com.scientts.bctools.parsers.TabelaParametrosAIDParser;


@SuppressWarnings("serial")
public class BCTools extends JFrame {

	JTextArea resultArea;
	JTextField commandText;
	JComboBox<CommandParser> actionBox;
	
	public BCTools() {
		createUserInterface();
	}
	
	protected void createUserInterface() {
		setLocation(100, 100);
		setPreferredSize(new Dimension(900, 600));
		setResizable(true);
		setTitle("BCTools Versão 1.0.0");
		setDefaultCloseOperation(EXIT_ON_CLOSE);   
		
		setLayout(new BorderLayout(5,5));
		
		createCommandPanel();
		createResultPanel();
	
		pack();
		setVisible(true);		
	}
	
	protected void createCommandPanel() {
		JPanel commandPanel = new JPanel();		
		commandPanel.setBorder(BorderFactory.createTitledBorder("Entrada de Dados"));
		commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		SpringLayout l1 = new SpringLayout();
		panel1.setLayout(l1);
		
		JLabel label1 = new JLabel("Dados:");
		panel1.add(label1);
		l1.putConstraint(SpringLayout.WEST, label1, 5, SpringLayout.WEST, panel1);
		l1.putConstraint(SpringLayout.NORTH, label1, 5, SpringLayout.NORTH, panel1);
		
		commandText = new JTextField();
		panel1.add(commandText);
		l1.putConstraint(SpringLayout.WEST, commandText, 5, SpringLayout.EAST, label1);
		l1.putConstraint(SpringLayout.NORTH, commandText, 5, SpringLayout.NORTH, panel1);
		l1.putConstraint(SpringLayout.EAST, panel1, 5, SpringLayout.EAST, commandText );
		l1.putConstraint(SpringLayout.SOUTH, panel1, 5, SpringLayout.SOUTH, commandText );

		
		JPanel panel2 = new JPanel();
		SpringLayout l2 = new SpringLayout();
		panel2.setLayout(l2);
		
		CommandParser [] parsers = {
				new TabelaParametrosAIDParser("Tabelas de Parâmetros x AID"),
				new TabelaCertificadosParser("Tabelas de Certificados"),
				new CheckEventParser("CheckEvent Input", CommandParser.INPUT_EXPRESSION),
				new CheckEventParser("CheckEvent Output", CommandParser.OUTPUT_EXPRESSION),
				new GetCardParser("GetCard Input", CommandParser.INPUT_EXPRESSION),
				new GetCardParser("GetCard Output", CommandParser.OUTPUT_EXPRESSION),
				new GoOnChipParser("GoOnChip Input", CommandParser.INPUT_EXPRESSION),
				new GoOnChipParser("GoOnChip Output", CommandParser.OUTPUT_EXPRESSION),
				new FinishChipParser("FinishChip Input", CommandParser.INPUT_EXPRESSION),
				new FinishChipParser("FinishChipOutput", CommandParser.OUTPUT_EXPRESSION)
		};
		actionBox = new JComboBox<CommandParser>(parsers);
		panel2.add(actionBox);
		
		JButton actionButton = new JButton("Executar");
		panel2.add(actionButton);
		l2.putConstraint(SpringLayout.WEST, actionBox, 5, SpringLayout.WEST, panel2);
		l2.putConstraint(SpringLayout.NORTH, actionBox, 5, SpringLayout.NORTH, panel2);

		l2.putConstraint(SpringLayout.WEST, actionButton, 5, SpringLayout.EAST, actionBox);
		l2.putConstraint(SpringLayout.NORTH, actionButton, 5, SpringLayout.NORTH, panel2);
		l2.putConstraint(SpringLayout.EAST, panel2, 5, SpringLayout.EAST, actionButton );
		l2.putConstraint(SpringLayout.SOUTH, panel2, 5, SpringLayout.SOUTH, actionButton );
		
		actionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parseCommand();
				
			}
		});

		
		commandPanel.add(panel1);
		commandPanel.add(panel2);
		add(commandPanel, BorderLayout.PAGE_START);
	}
	
	protected void createResultPanel() {
		resultArea = new JTextArea();
		Font font = new Font("Arial", Font.BOLD, 14);
		resultArea.setFont(font);
		
		JScrollPane scrollPane = new JScrollPane(resultArea);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Resultado"));
		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void parseCommand() {
		CommandParser parser = (CommandParser)actionBox.getSelectedItem();
		resultArea.append(parser.parse(commandText.getText()));
		resultArea.append("\n");
		resultArea.append("-----------------------------------------------\n");
		if (commandText.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Não há dados de entrada");
		}
	}
}