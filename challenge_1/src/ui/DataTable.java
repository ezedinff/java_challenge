package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class DataTable extends JPanel implements ListSelectionListener {
    JTable jt;
    JScrollPane sp;
    CustomListener listener;
    String columns[]={"ID","NAME","Job"};
    private String data[][] = {};
    DefaultTableModel dtm;
    DataTable(CustomListener listener) {
        super();
        this.listener = listener;
        this.setLayout(new BorderLayout());
        this.setSize(500, 400);
        this.init(this.data);
    }

    void init(String data[][]) {
        dtm = new DefaultTableModel();
        for(String column: this.columns) {
            dtm.addColumn(column);
        }
        this.jt =new JTable();
        this.jt.setModel(dtm);
        this.jt.setSize(500,400);
        this.jt.getSelectionModel().addListSelectionListener(this);
        this.sp =new JScrollPane(jt);
        this.add(sp, BorderLayout.CENTER);
    }

    public void setData(String[][] data)
    {
        this.data = data;
        this.dtm.getDataVector().removeAllElements();
        for (String[] d : this.data){
            System.out.println(Arrays.toString(d));
            if (d[0] != null) {
                this.dtm.addRow(new Object[]{d[0], d[1], d[2]});
            }
        }
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }

    public String[][] getData() {
        return data;
    }

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        if (!listSelectionEvent.getValueIsAdjusting()) { ;
            if (this.jt.getSelectedRow() < this.data.length) {
                StringBuilder data = new StringBuilder();
                for(int i = 0; i < this.dtm.getColumnCount(); i++) {
                    data.append(this.data[this.jt.getSelectedRow()][i]);
                    if (i != (this.columns.length - 1)) {
                        data.append(";"); // separated by ;
                    }
                }
                this.listener.dispatchAction(data.toString(), "dataSelected");
            }
        }
    }
}
