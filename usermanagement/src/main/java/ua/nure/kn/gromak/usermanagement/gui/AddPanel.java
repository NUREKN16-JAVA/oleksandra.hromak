package ua.nure.kn.gromak.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn.gromak.usermanagement.User;
import ua.nure.kn.gromak.usermanagement.db.DatabaseException;
import ua.nure.kn.gromak.usermanagement.util.Messages;

public class AddPanel extends JPanel implements ActionListener {
    protected MainFrame parent;
    private Color backgrColor;
    private JPanel buttonPanel;
    private JPanel fieldPanel;
    private JButton okayButton;
    private JButton cancelButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    private static final String TITLE_ERROR = "Error";

    public AddPanel(MainFrame parent) {
        this.parent = parent;
        initialize();
    }

    private void initialize() {
        this.setName("addPanel");
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkayButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"), getDateOfBirthField());
        }
        return fieldPanel;
    }

    private JButton getOkayButton() {
        if (okayButton == null) {
            okayButton = new JButton();
            okayButton.setText(Messages.getString("AddPanel.okay")); //$NON-NLS-1$
            okayButton.setName("okayButton"); //$NON-NLS-1$
            okayButton.setActionCommand("okay");  //$NON-NLS-1$
            okayButton.addActionListener(this);
        }
        return okayButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText(Messages.getString("AddPanel.cancel")); //$NON-NLS-1$
            cancelButton.setName("cancelButton");  //$NON-NLS-1$
            cancelButton.setActionCommand("cancel");  //$NON-NLS-1$
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    protected JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");  //$NON-NLS-1$
        }
        return firstNameField;
    }

    protected JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    protected JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    protected void clearFields() {
        getFirstNameField().setText("");
        getFirstNameField().setBackground(backgrColor);
        getLastNameField().setText("");
        getLastNameField().setBackground(backgrColor);
        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(backgrColor);
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    public void actionPerformed(ActionEvent e){
        if ("okay".equalsIgnoreCase(e.getActionCommand())) {
            User user = new User(1L, firstName, lastName, now);
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            DateFormat format = DateFormat.getDateInstance();
            try {
                Date date = format.parse(getDateOfBirthField().getText());
                user.setDateOfBirth(date);
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getDao().create(user );
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
        clearFields();
        this.setVisible(false);
        ((MainFrame) parent).showBrowsePanel();
    }

}