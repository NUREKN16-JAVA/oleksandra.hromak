package ua.nure.kn.gromak.usermanagement.gui;

import ua.nure.kn.gromak.usermanagement.User;
import ua.nure.kn.gromak.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

public class DetailsPanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;
    private final User user;
    private final String dateOfBirth;
    private JPanel buttonPanel;
    private JPanel fieldPanel;
    private JButton okayButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    private JTextField idField;
    private Color backgrColor = Color.WHITE;

    public DetailsPanel(MainFrame mainFrame) {
        parentFrame = mainFrame;
        user = parentFrame.getSelectedUser();
        dateOfBirth = DateFormat.getDateInstance().format(user.getDateOfBirth());
        initialize();
    }

    private void initialize() {
        this.setName("DetailsPanel"); //$NON-NLS-1$
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);

    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkayButton());
        }
        return buttonPanel;
    }

    private JButton getOkayButton() {
        if (okayButton == null) {
            okayButton = new JButton();
            okayButton.setText("OK"); //$NON-NLS-1$
            okayButton.setName("okayButton"); //$NON-NLS-1$
            okayButton.setActionCommand("okay"); //$NON-NLS-1$
            okayButton.addActionListener(this);
        }
        return okayButton;
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField"); //$NON-NLS-1$
            firstNameField.setText(user.getFirstName());
        }
        return firstNameField;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField"); //$NON-NLS-1$
            lastNameField.setText(user.getLastName());
        }
        return lastNameField;
    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField"); //$NON-NLS-1$
            dateOfBirthField.setText(dateOfBirth);
        }
        return dateOfBirthField;
    }

    private JTextField getIdField() {
        if (idField == null) {
            idField = new JTextField();
            idField.setName("idField"); //$NON-NLS-1$
            idField.setText(String.valueOf(user.getId()));
        }
        return idField;
    }

    protected JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, Messages.getString("DetailsPanel.first_name"), getFirstNameField()); //$NON-NLS-1$
            addLabeledField(fieldPanel, Messages.getString("DetailsPanel.last_name"), getLastNameField()); //$NON-NLS-1$
            addLabeledField(fieldPanel, Messages.getString("DetailsPanel.date_of_birth"), getDateOfBirthField()); //$NON-NLS-1$
        }
        return fieldPanel;
    }

    protected void clearFields() {
        getFirstNameField().setText("");
        getFirstNameField().setBackground(backgrColor);

        getLastNameField().setText("");
        getLastNameField().setBackground(backgrColor);

        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(backgrColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clearFields();
        this.setVisible(false);
        parentFrame.showBrowsePanel();
    }

    protected void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }
}
