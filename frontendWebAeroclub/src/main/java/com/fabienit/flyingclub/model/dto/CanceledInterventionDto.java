package com.fabienit.flyingclub.model.dto;

public class CanceledInterventionDto {


    private boolean canceled;

    public CanceledInterventionDto() {
    }

    public CanceledInterventionDto(boolean canceled) {
        this.canceled = canceled;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public String toString() {
        return "CanceledInterventionDto{" +
                "canceled=" + canceled +
                '}';
    }
}
