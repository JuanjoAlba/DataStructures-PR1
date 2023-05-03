package uoc.ds.pr.model;

import uoc.ds.pr.UniversityEvents;

import java.time.LocalDate;

public class EventRequest {

    private String requestId;
    private String eventId;
    private String entityId;
    private String description;
    private UniversityEvents.InstallationType installationType;
    private byte resources;
    private int maxAttendee;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean allowRegister;
    private UniversityEvents.Status status = UniversityEvents.Status.PENDING;
    private LocalDate approvedOrRejectedDate;
    private String rejectedReason;

    public EventRequest(String id, String eventId, String entityId, String description, UniversityEvents.InstallationType installationType, byte resources, int max, LocalDate startDate, LocalDate endDate, boolean allowRegister) {
        setRequestId(id);
        setEventId(eventId);
        setEntityId(entityId);
        setDescription(description);
        setInstallationType(installationType);
        setResources(resources);
        setMaxAttendee(max);
        setStartDate(startDate);
        setEndDate(endDate);
        setAllowRegister(allowRegister);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UniversityEvents.InstallationType getInstallationType() {
        return installationType;
    }

    public void setInstallationType(UniversityEvents.InstallationType installationType) {
        this.installationType = installationType;
    }

    public byte getResources() {
        return resources;
    }

    public void setResources(byte resources) {
        this.resources = resources;
    }

    public int getMaxAttendee() {
        return maxAttendee;
    }

    public void setMaxAttendee(int maxAttendee) {
        this.maxAttendee = maxAttendee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isAllowRegister() {
        return allowRegister;
    }

    public void setAllowRegister(boolean allowRegister) {
        this.allowRegister = allowRegister;
    }

    public void setStatus(UniversityEvents.Status status) {
        this.status = status;
    }

    public LocalDate getApprovedOrRejectedDate() {
        return approvedOrRejectedDate;
    }

    public void setApprovedOrRejectedDate(LocalDate approvedOrRejectedDate) {
        this.approvedOrRejectedDate = approvedOrRejectedDate;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }
}
