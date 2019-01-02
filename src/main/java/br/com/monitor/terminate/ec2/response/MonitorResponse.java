package br.com.monitor.terminate.ec2.response;

import com.amazonaws.services.ec2.model.InstanceStateChange;
import com.amazonaws.services.ec2.model.Reservation;

import java.util.List;

public class MonitorResponse {

    private List<InstanceStateChange> instanceStateChanges;
    private Integer status;

    public MonitorResponse(List<InstanceStateChange> instanceStateChanges, Integer status){
        this.instanceStateChanges = instanceStateChanges;
        this.status = status;
    }

    public List<InstanceStateChange> getInstanceStateChanges() {
        return instanceStateChanges;
    }

    public void setInstanceStateChanges(List<InstanceStateChange> instanceStateChanges) {
        this.instanceStateChanges = instanceStateChanges;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
