package com.example.AirlineBackend.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "Aircrafts")
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String aircraftID;
    private int lastBusinessRow;
    private int lastComfortRow;
    private int totalBusinessColumns;
    private int totalRegularColumns;
    private int rowsNumber;
    private int columnsNumber;
    @OneToOne
    @JoinColumn(name = "flightNumber", referencedColumnName = "flightNumber")
    private Flight flight;

    public Aircraft(String aircraftID, int lastBusinessRow, int lastComfortRow, int totalBusinessColumns, int totalRegularColumns, int rows, int columns){
        this.aircraftID = aircraftID;
        this.lastBusinessRow = lastBusinessRow;
        this.lastComfortRow = lastComfortRow;
        this.totalBusinessColumns = totalBusinessColumns;
        this.totalRegularColumns = totalRegularColumns;
        this.rowsNumber = rows;
        this.columnsNumber = columns;
    }

    public Aircraft() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getAircraftID() {
        return aircraftID;
    }

    public void setAircraftID(String aircraftID) {
        this.aircraftID = aircraftID;
    }

    public int getLastBusinessRow() {
        return lastBusinessRow;
    }

    public void setLastBusinessRow(int lastBusinessRow) {
        this.lastBusinessRow = lastBusinessRow;
    }

    public int getLastComfortRow() {
        return lastComfortRow;
    }

    public void setLastComfortRow(int lastComfortRow) {
        this.lastComfortRow = lastComfortRow;
    }

    public int getTotalBusinessColumns() {
        return totalBusinessColumns;
    }

    public void setTotalBusinessColumns(int totalBusinessColumns) {
        this.totalBusinessColumns = totalBusinessColumns;
    }

    public int getTotalRegularColumns() {
        return totalRegularColumns;
    }

    public void setTotalRegularColumns(int totalRegularColumns) {
        this.totalRegularColumns = totalRegularColumns;
    }

    public int getRows() {
        return rowsNumber;
    }

    public void setRows(int rows) {
        this.rowsNumber = rows;
    }

    public int getColumns() {
        return columnsNumber;
    }

    public void setColumns(int columns) {
        this.columnsNumber = columns;
    }

    public int getRowsNumber() {
        return rowsNumber;
    }

    public void setRowsNumber(int rowsNumber) {
        this.rowsNumber = rowsNumber;
    }

    public int getColumnsNumber() {
        return columnsNumber;
    }

    public void setColumnsNumber(int columnsNumber) {
        this.columnsNumber = columnsNumber;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
