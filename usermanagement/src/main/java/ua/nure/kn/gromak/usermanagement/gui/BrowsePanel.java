package ua.nure.kn.gromak.usermanagement.gui;

import ua.nure.kn.gromak.usermanagement.User;
import ua.nure.kn.gromak.usermanagement.db.DatabaseException;
import ua.nure.kn.gromak.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BrowsePanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private MainFrame parent;
    private JTable userTable;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JScrollPane tablePanel;
    private static final String TITLE_ERROR = "Error";

    public BrowsePanel(MainFrame frame) {
        parent = frame;
        initialize();
    }

    private void initialize() {
        this.setName("BrowsePanel");
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JTable getUserTable() {
        if (userTable == null) {
            userTable = new JTable();
            userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            userTable.setName("UserTable");
        }
        return userTable;
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getAddButton());
            buttonPanel.add(getEditButton());
            buttonPanel.add(getDeleteButton());
            buttonPanel.add(getDetailButton());
        }
        return buttonPanel;
    }

    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setText(Messages.getString("BrowsePanel.add"));
            addButton.setName("addButton");
            addButton.setActionCommand("add");
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JButton getEditButton() {
        if (editButton == null) {
            editButton = new JButton();
            editButton.setText(Messages.getString("BrowsePanel.edit"));
            editButton.setName("editButton");
            editButton.setActionCommand("edit");
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(Messages.getString("BrowsePanel.delete"));
            deleteButton.setName("deleteButton");
            deleteButton.setActionCommand("delete");
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getDetailButton() {
        if (detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText(Messages.getString("BrowsePanel.details"));
            detailsButton.setName("detailsButton");
            detailsButton.setActionCommand("details");
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    public User getSelectedUser() {
        int selectedRow = getUserTable().getSelectedRow();
        if (selectedRow == -1)
            return null;
        try {
            User user = parent.getDao().find((Long) getUserTable().getValueAt(selectedRow, 0));
            return user;
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if("add".equalsIgnoreCase(actionCommand)){
            this.setVisible(false);
            parent.showAddPanel();
        }

        if("edit".equalsIgnoreCase(actionCommand)){
            this.setVisible(false);
            parent.showEditPanel();
        }

        if ("delete".equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
            User user = getSelectedUser();
            int result = JOptionPane.showConfirmDialog(this, Messages.getString("deleteConfirmation") + user + "?");
            if (result == JOptionPane.YES_OPTION) {
                try {
                    parent.getDao().delete(getSelectedUser());
                    getUserTable().setModel(new UserTableModel(parent.getDao().findAll()));
                } catch (DatabaseException de) {
                    JOptionPane.showMessageDialog(this, de.getMessage(), TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if("details".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showDetailsPanel();
        }
    }

    public void initTable() {
        UserTableModel model;
        try {
            model = new UserTableModel(parent.getDao().findAll());
        } catch (DatabaseException e) {
            model = new UserTableModel(new ArrayList());
            JOptionPane.showMessageDialog(this, e.getMessage(), TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
        }
        getUserTable().setModel(model);
    }
}
