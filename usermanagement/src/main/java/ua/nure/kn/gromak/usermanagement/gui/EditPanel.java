package ua.nure.kn.gromak.usermanagement.gui;

import ua.nure.kn.gromak.usermanagement.User;
import ua.nure.kn.gromak.usermanagement.db.DatabaseException;
import ua.nure.kn.gromak.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class EditPanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;
    private JPanel buttonPanel;
    private JButton cancelButton;
    private JButton okayButton;
    private JPanel fieldPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    private Color backgrColor;
    private User user;
    private static final String TITLE_ERROR = "Error";


    public EditPanel(MainFrame mainFrame) {
        parentFrame = mainFrame;
        user = parentFrame.getSelectedUser();
        initialize();
        backgrColor = this.getBackground();
    }

    private void initialize() {
        this.setName("editPanel"); //$NON-NLS-1$
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

    protected JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField()); //$NON-NLS-1$
            addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField()); //$NON-NLS-1$
            addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"), getDateOfBirthField()); //$NON-NLS-1$
        }
        return fieldPanel;
    }

    private JButton getOkayButton() {
        if (okayButton == null) {
            okayButton = new JButton();
            okayButton.setText(Messages.getString("AddPanel.okay")); //$NON-NLS-1$
            okayButton.setName("okayButton"); //$NON-NLS-1$
            okayButton.setActionCommand("okay"); //$NON-NLS-1$
            okayButton.addActionListener(this);
        }
        return okayButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText(Messages.getString("AddPanel.cancel")); //$NON-NLS-1$
            cancelButton.setName("cancelButton"); //$NON-NLS-1$
            cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField"); //$NON-NLS-1$
        }
        return firstNameField;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField"); //$NON-NLS-1$
        }
        return lastNameField;
    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField"); //$NON-NLS-1$
        }
        return dateOfBirthField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("okay".equalsIgnoreCase(e.getActionCommand())) {
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            SimpleDateFormat dataFormat = new SimpleDateFormat();
            try {
                user.setDateOfBirth(dataFormat.parse(getDateOfBirthField().getText()));
            } catch (Exception ex) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parentFrame.getDao().update(user);
            } catch (DatabaseException de) {
                JOptionPane.showMessageDialog(this, de.getMessage(), TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
        clearTextFields();
        this.setVisible(false);
        parentFrame.showBrowsePanel();
    }

    private void clearTextFields() {
        getFirstNameField().setText("");
        getFirstNameField().setBackground(backgrColor);

        getLastNameField().setText("");
        getLastNameField().setBackground(backgrColor);

        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(backgrColor);
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel();
        label.setText(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

}
