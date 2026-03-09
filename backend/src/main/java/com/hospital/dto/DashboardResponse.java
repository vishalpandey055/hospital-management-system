package com.hospital.dto;

public class DashboardResponse {

    private long totalPatients;
    private long totalDoctors;
    private long totalAppointments;
    private long completedAppointments;

    public DashboardResponse(long totalPatients,
                             long totalDoctors,
                             long totalAppointments,
                             long completedAppointments) {
        this.totalPatients = totalPatients;
        this.totalDoctors = totalDoctors;
        this.totalAppointments = totalAppointments;
        this.completedAppointments = completedAppointments;
    }

    public long getTotalPatients() {
        return totalPatients;
    }

    public long getTotalDoctors() {
        return totalDoctors;
    }

    public long getTotalAppointments() {
        return totalAppointments;
    }

    public long getCompletedAppointments() {
        return completedAppointments;
    }
}