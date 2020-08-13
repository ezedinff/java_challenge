package ui;

import models.User;
import org.json.simple.JSONObject;
import services.Api;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class MyContentPane extends JPanel implements CustomListener {
    UserForm userFormPanel;
    DataTable dataTablePanel;
    String data[][] = {};
    private Api api;
    MyContentPane() throws Exception {
        super();
        this.setLayout(new BorderLayout());
        this.userFormPanel = new UserForm(this);
        this.add(userFormPanel);

        this.dataTablePanel = new DataTable(this);
        this.add(dataTablePanel, BorderLayout.EAST);

        this.api = new Api();
        this.getData();
    }


    void getData() {
        try{;String d[][] = {};
           List<User> users = this.api.getUsers();
           d = new String[users.size()][3];
           for(int i = 0; i < users.size(); i++) {
               d[i][0] = users.get(i).getId();
               d[i][1] = users.get(i).getFirst_name() + " " + users.get(i).getLast_name();
               d[i][2] = " "; // because job is not available in response object
           }
           this.dataTablePanel.setData(d);
       } catch (Exception io) {
            System.err.println(io);
       }
    }

    @Override
    public void dispatchAction(String data, String actionType) {
        // action types are create, update, delete
        if(actionType.equals("dataSelected")) {
            String d[] = data.split(";");
            String fN = d[1].split(" ")[0];
            String lN = d[1].split(" ").length >= 2 ? d[1].split(" ")[1] : "";
            User u = new User(d[0],fN, lN, "", "");
            u.setJob(d[2]);
            this.userFormPanel.setUser(u);
        } else if (actionType.equals("Create")) {
            try{
                JSONObject d = (JSONObject) this.api.createUser(data);
                this.dataTablePanel.getDtm().addRow(new Object[]{d.get("id"), d.get("name"), d.get("job")});
            } catch (Exception e) {}
        } else if (actionType.equals("Update")) {
            try{
                String temp[] = data.split(";");
                JSONObject d = (JSONObject) this.api.updateUser(temp[1], temp[0]);
                this.dataTablePanel.getDtm().addRow(new Object[]{d.get("id"), d.get("name"), d.get("job")});
            } catch (Exception e) {}
        } else if (actionType.equals("Delete")) {
            System.out.println(data);
            try{
                String d[][] = {};
                List<User> users = this.api.getUsers();
                d = new String[users.size()][3];
                String da[][] = this.dataTablePanel.getData();
                for(int i = 0, k = 0; i < da.length; i++) {
                    if (!da[i][0].equals(data) && da[i][0] != null) {
                        d[k][0] = da[i][0];
                        d[k][1] = da[i][1];
                        d[k][2] = da[i][2]; // because job is not available in response object
                        k++;
                    }
                }
                this.dataTablePanel.setData(d);
                this.api.deleteUser(data);
            } catch (Exception e) {
                //System.out.println(e);
            }
        }
    }
}
