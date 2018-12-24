package ua.nure.kn.gromak.usermanagement.gui;

import ua.nure.kn.gromak.usermanagement.User;
import ua.nure.kn.gromak.usermanagement.db.DatabaseException;
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
    private JButton cancelButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    private Color backgrColor = Color.WHITE;
    private static final String TITLE_ERROR = "Error";

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

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText(Messages.getString("AddPanel.cancel"));
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
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

    public void showUser(Long id){
        try {
            User user = parentFrame.getDao().find(id);
            getFirstNameField().setText(user.getFirstName());
            getLastNameField().setText(user.getLastName());
            getDateOfBirthField().setText(DateFormat.getDateInstance().format(user.getDateOfBirth()));
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            parentFrame.showBrowsePanel();
        }
    }
}
