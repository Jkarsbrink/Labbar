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

    public String getName() {
        return name;
    }

    public String getSickness() {
        return sickness;
    }

    public boolean isSick() {
        return this.sickness != null;
    }

    public void takeMedication(Medicine medicine) {
        if (medicine.getTreatmentName().equals(this.sickness)) {
            this.sickness = null;
        }


    }

}
