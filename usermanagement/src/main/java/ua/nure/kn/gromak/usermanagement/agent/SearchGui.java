package ua.nure.kn.gromak.usermanagement.agent;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import ua.nure.kn.gromak.usermanagement.gui.UserTableModel;
import ua.nure.kn.gromak.usermanagement.util.Messages;


public class SearchGui extends JFrame {
    private SearchAgent agent;
    private JPanel contentPanel;
    private JPanel tablePanel;
    private JTable table;

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Searcher");
        this.setContentPane(getContentPanel());
    }

    public SearchGui(SearchAgent agent) {
        this.agent = agent;
        initialize();
    }

    private JTable getTable() {
        if (table == null) {
            table = new JTable(new UserTableModel(new LinkedList()));
        }
        return table;
    }

    private JPanel getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JPanel(new BorderLayout());
            tablePanel.add(getTable(), BorderLayout.CENTER);
        }
        return tablePanel;
    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getSearchPanel(), BorderLayout.NORTH);
            contentPanel.add(new JScrollPane(getTablePanel()), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    private JPanel getSearchPanel() {
        return new SearchPanel(agent);
    }

    public static void main(String[] args) {
        SearchGui gui = new SearchGui(null);
        gui.setVisible(true);
    }

    class SearchPanel extends JPanel implements ActionListener {
        SearchAgent agent;
        private JPanel buttonPanel;
        private JPanel fieldPanel;
        private JButton cancelButton;
        private JButton searchButton;
        private JTextField firstNameField;
        private JTextField dateOfBirthField;
        private JTextField lastNameField;

        private void initialize() {
            this.setName("addPanel"); //$NON-NLS-1$
            this.setLayout(new BorderLayout());
            this.add(getFieldPanel(), BorderLayout.NORTH);
        }

        public SearchPanel(SearchAgent agent) {
            this.agent = agent;
            initialize();
        }

        private JPanel getButtonPanel() {
            if (buttonPanel == null) {
                buttonPanel = new JPanel();
                buttonPanel.add(getSearchButton(), null);
                buttonPanel.add(getCancelButton(), null);
            }
            return buttonPanel;
        }

        private JButton getSearchButton() {
            if (searchButton == null) {
                searchButton = new JButton();
                searchButton.setText("Search"); //$NON-NLS-1$
                searchButton.setName("okayButton"); //$NON-NLS-1$
                searchButton.setActionCommand("okay"); //$NON-NLS-1$
                searchButton.addActionListener(this);
            }
            return searchButton;
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

        private JPanel getFieldPanel() {
            if (fieldPanel == null) {
                fieldPanel = new JPanel();
                fieldPanel.setLayout(new GridLayout(2, 3));
                addLabeledField(fieldPanel, "FirstName ", getFirstNameField());
                fieldPanel.add(getSearchButton());
                addLabeledField(fieldPanel, "LastName ", getLastNameField());
                fieldPanel.add(getCancelButton());
            }
            return fieldPanel;
        }

        protected JTextField getLastNameField() {
            if (lastNameField == null) {
                lastNameField = new JTextField();
                lastNameField.setName("lastNameField"); //$NON-NLS-1$
            }
            return lastNameField;
        }

        private void addLabeledField(JPanel panel, String labelText,
                                     JTextField textField) {
            JLabel label = new JLabel(labelText);
            label.setLabelFor(textField);
            panel.add(label);
            panel.add(textField);
        }

        protected JTextField getFirstNameField() {
            if (firstNameField == null) {
                firstNameField = new JTextField();
                firstNameField.setName("firstNameField"); //$NON-NLS-1$
            }
            return firstNameField;
        }

        protected void doAction(ActionEvent e) {
            if ("okay".equalsIgnoreCase(e.getActionCommand())) {
                String firstName = getFirstNameField().getText();
                String lastName = getLastNameField().getText();
                try {
                    clearUsers();
                    agent.search(firstName, lastName);
                } catch (SearchException e1) {
                    throw new RuntimeException(e1);
                }
            }
            clearFields();
        }

        public void actionPerformed(ActionEvent e) {
            doAction(e);
        }

        private void clearFields() {
            getFirstNameField().setText("");
            getLastNameField().setText("");
        }
    }

    public void addUsers(Collection users) {
        System.out.println("addUsers: " + users);
        UserTableModel model = (UserTableModel) getTable().getModel();
        model.addUsers(users);
        this.repaint();
    }

    private void clearUsers() {
        System.out.println("clearUsers: ");
        UserTableModel model = (UserTableModel) getTable().getModel();
        model.clearUsers();
        this.repaint();
    }
}
