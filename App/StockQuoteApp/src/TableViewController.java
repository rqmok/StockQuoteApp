import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.ArrayList;

public class TableViewController extends Controller {

    private TableView<Monitor> tableView;

    public TableViewController(ArrayList<String> fieldNames) {

        tableView = new TableView<>();

        // Construct Table Columns
        ArrayList<TableColumn<Monitor, String>> columns = new ArrayList<>();

        // Create columns from field names
        for (String fieldName : fieldNames) {
            TableColumn<Monitor, String> column = new TableColumn<>(fieldName);
            //column.setMinWidth(200);
            columns.add(column);
        }

        for (int i = 0; i < columns.size(); i++) {
            TableColumn<Monitor, String> column = columns.get(i);
            final int columnIndex = i;
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Monitor, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Monitor, String> param) {
                    ArrayList<String> quoteData = param.getValue().getStock().getLastStockData().getQuoteData();
                    String ret;

                    if (columnIndex > quoteData.size() - 1) {
                        ret = "N/A";
                    } else {
                        ret = quoteData.get(columnIndex);
                    }

                    return new SimpleStringProperty(ret);
                }
            });
            tableView.getColumns().add(column);
        }
    }

    public void update(ArrayList<Monitor> monitors) {
        if (monitors == null) {
            return;
        }

        tableView.getItems().clear();

        ObservableList<Monitor> data = FXCollections.observableArrayList();

        for (Monitor monitor : monitors) {
            if (monitor.getMonitorType() == Monitor.monitorTypes.TABLE_MONITOR) {
                data.add(monitor);
            }
        }

        tableView.setItems(data);
    }

    public TableView<Monitor> getTableView() {
        return tableView;
    }

    public ArrayList<Monitor> getSelectedMonitors() {
        ArrayList<Monitor> selectedMonitors = new ArrayList<>();

        for (Monitor monitor : tableView.getSelectionModel().getSelectedItems()) {
            selectedMonitors.add(monitor);
        }

        return selectedMonitors;
    }

}
