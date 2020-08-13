package ui;

import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserForm extends JPanel implements ActionListener {
    JTextField nameField;
    JLabel nameLabel;

    JTextField jobField;
    JLabel jobLabel;


    // action buttons

    JButton newButton;
    JButton saveButton;
    JButton updateButton;
    JButton deleteButton;
    CustomListener listener;

    private User user;

    UserForm(CustomListener listener) {
        super();
        this.listener = listener;
        this.setLayout(null);
        this.setSize(350, 400);
        this.setBackground(Color.decode("#78C1FF"));
        this.setOpaque(true);
        this.init();
    }

    void init() {
        this.nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(30, 80, 60, 30);
        this.add(nameLabel);

        this.nameField = new JTextField();
        nameField.setBounds(100, 80, 160, 30);
        this.add(nameField);

        this.jobLabel = new JLabel("Job: ");
        jobLabel.setBounds(30, 124, 60, 30);
        this.add(jobLabel);

        this.jobField = new JTextField();
        jobField.setBounds(100, 124, 160, 30);
        this.add(jobField);


        this.newButton = this.getButton("New", "#0000FF");
        this.newButton.setBounds(10, 180, 70, 30);
        this.newButton.addActionListener(this);
        this.add(newButton);

        this.saveButton = this.getButton("Save", "#0000FF");
        this.saveButton.setBounds(90, 180, 80, 30);
        this.saveButton.addActionListener(this);
        this.add(saveButton);

        this.updateButton =this.getButton("Update", "#008080");
        this.updateButton.setBounds(180, 180, 90, 30);
        this.updateButton.addActionListener(this);
        this.add(updateButton);

        // this.updateButton.se
        this.deleteButton = this.getButton("Delete", "#D43F3A");
        this.deleteButton.setBounds(280, 180, 80, 30);
        this.deleteButton.addActionListener(this);
        this.add(deleteButton);

    }

    public void setUser(User user) {
        this.user = user;
        this.nameField.setText(this.user.getFirst_name() + " " + this.user.getLast_name());
        this.jobField.setText(this.user.getJob());
    }

    JButton getButton(String labelText, String color) {
        JButton button = new JButton(labelText);
        button.setBackground(Color.decode(color));
        button.setForeground(Color.white);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.newButton){
            this.nameField.setText("");
            this.jobField.setText("");
        } else if (actionEvent.getSource() == this.saveButton){
            String data = "{\"name\":" + "\"" + nameField.getText() + "\"" + "," + "\"job\":" + "\"" + jobField.getText() + "\"" + "}";
            this.listener.dispatchAction(data, "Create");
        } else if (actionEvent.getSource() == this.updateButton){
            String data = this.user.getId() + ";{\"name\":" + "\"" + nameField.getText() + "\"" + "," + "\"job\":" + "\"" + jobField.getText() + "\"" + "}";
            this.listener.dispatchAction(data, "Update");
        } else if (actionEvent.getSource() == this.deleteButton){
            this.listener.dispatchAction(user.getId(), "Delete");
        }
    }
}
