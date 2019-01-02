package br.com.monitor.stop.ec2.handler;



import br.com.monitor.stop.ec2.response.MonitorResponse;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StopEC2Handler implements RequestHandler<Map<String,Object>, MonitorResponse> {

    private final static String AMI_ID = "ami-03295f3eb51971483";

    public static void main (String args []) {

        new StopEC2Handler().handleRequest(null, null);

    }

    public MonitorResponse handleRequest(Map<String, Object> input, Context context) {

        final AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("ec2.us-east-1.amazonaws.com", "us-east-1");

        AmazonEC2 amazonEC2 = AmazonEC2ClientBuilder.standard().withEndpointConfiguration(endpoint).build();
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        DescribeInstancesResult response = amazonEC2.describeInstances(request);

        List<String> instanceIds = new ArrayList<>();

        for(Reservation reservation : response.getReservations()){
            for(Instance instance : reservation.getInstances()){
                if(instance.getImageId().equals(AMI_ID))
                   instanceIds.add(instance.getInstanceId());
            }
        }

        TerminateInstancesRequest terminateInstancesRequest = new TerminateInstancesRequest();
        terminateInstancesRequest.withInstanceIds(instanceIds);

        if(!instanceIds.isEmpty()){
            TerminateInstancesResult terminateResponse = amazonEC2.terminateInstances(terminateInstancesRequest);
            return new MonitorResponse(terminateResponse.getTerminatingInstances(),terminateResponse.getSdkHttpMetadata().getHttpStatusCode());
        }

        return new MonitorResponse(new ArrayList<>(),404);
    }
}
