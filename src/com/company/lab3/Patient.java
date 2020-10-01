package com.company.lab3;


public class Patient {
    private String name;
    private String sickness;

    Patient(String name, String sickness) {
        this.name = name;
        this.sickness = sickness;
    }

    Patient(String name) {
        this.name = name;
    }

    protected String getName() {
        return this.name;
    }

    protected String getSickness() {
        return this.sickness;
    }

    protected boolean isSick() {
        return this.sickness != null;
    }

    protected void takeMedication(Medicine medicine) {
        if (medicine.getTreatmentName().equals(this.sickness)) {
            this.sickness = null;
        }

    }

}
